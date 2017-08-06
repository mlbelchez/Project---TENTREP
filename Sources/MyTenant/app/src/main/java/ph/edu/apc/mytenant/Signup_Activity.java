package ph.edu.apc.mytenant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Aira Joyce on 11/30/2016.
 */
public class Signup_Activity extends AppCompatActivity implements View.OnClickListener {

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

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(this, Content_Activity.class));
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
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(Signup_Activity.this, "Account Successfully Created ", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(), Content_Activity.class));
                        }else{
                            Toast.makeText(Signup_Activity.this, "Account Could not registered.. Please try again", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

     @Override
     public void onClick(View view){
         if(view == bSignup){
             SignupUser();
         }
        if(view == tvSignin){
            Intent i = new Intent(Signup_Activity.this,Signin_Activity.class);
            startActivity(i);

        }
     }
}
