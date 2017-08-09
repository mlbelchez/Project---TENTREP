package ph.edu.apc.mytenant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Aira Joyce on 12/3/2016.
 */

public class Header_Activity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private TextView tvUserEmail;
    @Override
    protected void onCreate(Bundle savedInstancesState) {
        super.onCreate(savedInstancesState);
        setContentView(R.layout.content_);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, Signin_Activity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        tvUserEmail=(TextView) findViewById(R.id.tvUserEmail);
        tvUserEmail.setText("Welcome "+ user.getEmail() );
    }
}
