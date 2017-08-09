package mobapp.edu.apc.vioview;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "Debug";

    // firebase instance variables
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText editTextEmail, editTextPassword;
    //private Button buttonSignIn, buttonCreateAccount;

    private ProgressDialog loading;
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextEmail = (EditText) findViewById(R.id.email_text);
        editTextPassword = (EditText) findViewById(R.id.password_text);

        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.create_account_button).setOnClickListener(this);

        loading = new ProgressDialog(this);
        loading.setTitle("Processing");
        loading.setMessage("Please wait...");

        // to track if the user is signed in or signed out
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user!=null) {
                    // user is signed in
                    Log.d(TAG,"onAuthStateChanged:signed_in:" + user.getUid());
                    Toast.makeText(SignInActivity.this, "You are signed in.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignInActivity.this, MenuActivity.class);
                    startActivity(intent);
                } else {
                    // user is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    Toast.makeText(SignInActivity.this, "Please sign in to continue", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void createAccount(String email, String password) {
        if (!validateForm()) {
            return;
        }

        loading.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // sign in fails
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignInActivity.this, "Authentication Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                        loading.dismiss();
                    }
                });
    }

    private void signIn(String email, String password) {
        if (!validateForm()) {
            return;
        }

        loading.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // sign in fails
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                        loading.dismiss();
                    }
                });
    }

    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(editTextEmail.getText().toString())) {
            editTextEmail.setError("Required");
            result = false;
        } else {
            editTextEmail.setError(null);
        }

        if (TextUtils.isEmpty(editTextPassword.getText().toString())) {
            editTextPassword.setError("Required");
            result = false;
        } else {
            editTextPassword.setError(null);
        }

        return result;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                signIn(editTextEmail.getText().toString(), editTextPassword.getText().toString());
                break;
            case R.id.create_account_button:
                createAccount(editTextEmail.getText().toString(), editTextPassword.getText().toString());
                break;
        }
    }
}
