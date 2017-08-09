package mobapp.edu.apc.vioview;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Map;

import mobapp.edu.apc.vioview.models.Violation;

/**
 * Created by Caranto on 11/30/2016.
 */

public class ViewActivity extends AppCompatActivity {

    private DatabaseReference mFirebaseDatabaseReference;
    private DatabaseReference recordsRef;

    private FirebaseUser user;
    private FirebaseAuth mAuth;

    private ArrayList<Violation> vios;
    private ArrayList<String> items = new ArrayList<>();

    //private CustomRecyclerAdapter adapter;

    private RecyclerView recyclerView;
    private EditText searchUsername;
    private Button buttonSearch;

    private ProgressDialog loading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        user = FirebaseAuth.getInstance().getCurrentUser();

        loading = new ProgressDialog(this);
        loading.setTitle("Processing");
        loading.setMessage("Please wait...");

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        searchUsername = (EditText) findViewById(R.id.username_search);
        buttonSearch = (Button) findViewById(R.id.view_search_button);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.show();
                items.clear();
                String username = searchUsername.getText().toString();

                FirebaseRecyclerAdapter<Violation, MessageViewHolder> adapter =
                        new FirebaseRecyclerAdapter<Violation, MessageViewHolder>(
                                Violation.class,
                                R.layout.custom_item,
                                MessageViewHolder.class,
                                mFirebaseDatabaseReference.child("records").child(username)) {
                            @Override
                            protected void populateViewHolder(MessageViewHolder viewHolder, Violation model, int position) {
                                viewHolder.textViewDate.setText(model.getDate());
                                viewHolder.textViewType.setText(model.getType());
                                viewHolder.textViewDescription.setText(model.getDescription());
                                viewHolder.textViewIssuedBy.setText(model.getIssuedBy());
                            }
                        };
                recyclerView.setAdapter(adapter);
                Log.d("Fetch data:", "Success");
                loading.hide();
            }
        });

    }

    private void signOut() {
        mAuth.signOut();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewDate;
        private final TextView textViewDescription;
        private final TextView textViewIssuedBy;
        private final TextView textViewType;

        public MessageViewHolder(View itemView) {
            super(itemView);
            textViewDate = (TextView) itemView.findViewById(R.id.custom_date);
            textViewDescription = (TextView) itemView.findViewById(R.id.custom_description);
            textViewIssuedBy = (TextView) itemView.findViewById(R.id.custom_issuedby);
            textViewType = (TextView) itemView.findViewById(R.id.custom_type);
        }

        public void setTextViewDate(String textViewDate) {
            this.textViewDate.setText(textViewDate);
        }
        public void setTextViewDescription(String textViewDescription) {
            this.textViewDescription.setText(textViewDescription);
        }
        public void setTextViewIssuedBy(String textViewIssuedBy) {
            this.textViewIssuedBy.setText(textViewIssuedBy);
        }
        public void setTextViewType(String textViewType) {
            this.textViewType.setText(textViewType);
        }
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
            case R.id.refresh:
                break;
            case R.id.sign_out:
                signOut();
                Intent intent = new Intent(ViewActivity.this, SignInActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
