package ph.edu.apc.myapplication.ui.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import ph.edu.apc.myapplication.R;
import ph.edu.apc.myapplication.activity_signin;
import ph.edu.apc.myapplication.models.User;
import ph.edu.apc.myapplication.nav_forum;
import ph.edu.apc.myapplication.utils.BaseActivity;
import ph.edu.apc.myapplication.utils.FirebaseUtils;

/**
 * Created by Aira Joyce on 8/13/2017.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private static final int RC_SIGN_IN = 9001;

    private EditText etEmail, etPassword, etCpassword;
    private Button bSignup;
    private TextView tvSignin;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_signup);

        findViewById(R.id.bSignupGoogle).setOnClickListener(this);
        //SIGN UP USER
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(this, nav_forum.class));
        }

        etEmail=(EditText)findViewById(R.id.etEmail);
        etPassword=(EditText)findViewById(R.id.etPassword);
        etCpassword =(EditText)findViewById(R.id.etCpassword);
        bSignup =(Button)findViewById(R.id.bSignup);
        tvSignin = (TextView)findViewById(R.id.tvSignin);

        progressDialog = new ProgressDialog(this);
        bSignup.setOnClickListener(this);
        tvSignin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bSignupGoogle:
                showProgressDialog();
                signIn();
        }

        if(v == bSignup){
            SignupUser();

        }
        if(v == tvSignin){
            Intent i = new Intent(RegisterActivity.this,activity_signin.class);
            startActivity(i);

        }
    }
    //Sign in using gmail
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RC_SIGN_IN) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                if (result.isSuccess()) {
                    GoogleSignInAccount account = result.getSignInAccount();
                    firebaseAuthWithGoogle(account);
                } else {
                    dismissProgressDialog();
                }
            } else {
                dismissProgressDialog();
            }
        } else {
            dismissProgressDialog();
        }
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Let's create the models quick(I know! it's weird time in this tutorial
                            //To be building it
                            User user = new User();
//                            String photoUrl = null;
//                            if (account.getPhotoUrl() != null) {
//                                user.setPhotoUrl(account.getPhotoUrl().toString());
//                            }

                            user.setEmail(account.getEmail());
                            user.setUser(account.getDisplayName());
                            user.setUid(mAuth.getCurrentUser().getUid());

                            FirebaseUtils.getUserRef(account.getEmail().replace(".", ","))
                                    .setValue(user, new DatabaseReference.CompletionListener() {
                                        @Override
                                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                            mFirebaseUser = mAuth.getCurrentUser();
                                            finish();
                                        }
                                    });
                        } else {
                            dismissProgressDialog();
                        }
                    }
                });
    }

    //Sign up User
    private void SignupUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String ConfirmPwd = etCpassword.getText().toString();

        if(TextUtils.isEmpty(email)){
            //if email is empty
            Toast.makeText(this,"Please enter your email address", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            //if password is empty
            Toast.makeText(this,"Please enter Password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(this, "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(ConfirmPwd)) {
            Toast.makeText(this, "Password not match with Confirm Password", Toast.LENGTH_SHORT).show();
            return;
        }
        //if validation is okay
        //show progress dialog
        progressDialog.setMessage("Creating User Account...");
        progressDialog.show();
        //creating user
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Account Successfully Created ", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(), nav_forum.class));
                }else{
                    Toast.makeText(RegisterActivity.this, "Account Could not registered.. Please try again", Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });
    }
}
