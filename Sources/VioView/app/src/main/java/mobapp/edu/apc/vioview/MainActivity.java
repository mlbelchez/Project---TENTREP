package mobapp.edu.apc.vioview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import mobapp.edu.apc.vioview.models.Violation;

/**
 * Created by Caranto on 11/30/2016.
 */

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseUser student = FirebaseAuth.getInstance().getCurrentUser();

    private DatabaseReference mDatabase;

    private Button buttonSend;

    private EditText editTextDesc, editTextIssued, editTextVioType;
    private AutoCompleteTextView autoCompleteTextView;

    private ArrayList<String> usernames = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        buttonSend = (Button) findViewById(R.id.set_violation_button);
        editTextDesc = (EditText) findViewById(R.id.description_edit);
        editTextIssued = (EditText) findViewById(R.id.issued_edit);
        editTextVioType = (EditText) findViewById(R.id.vio_type_edit);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        ArrayAdapter<String> userAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                usernames);
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewUname);
        autoCompleteTextView.setAdapter(userAdapter);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                String desc = editTextDesc.getText().toString();
                String issued = editTextIssued.getText().toString();
                String vioType = editTextVioType.getText().toString();
                String username = autoCompleteTextView.getText().toString();

                addViolation(currentDateTimeString, desc, issued, vioType, username);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        mDatabase.child("usernames").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue(String.class);
                usernames.add(value);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addViolation(String date, String desc, String issued, String vioType, String username) {

        Violation vio = new Violation(date, desc, issued, vioType);

        mDatabase.child("records").child(username).push().setValue(vio);
        mDatabase.child("usernames").child(username).setValue(username);
        Toast.makeText(MainActivity.this, "Violation for " + username + " added.",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out:
                signOut();
                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        mAuth.signOut();
    }
}
