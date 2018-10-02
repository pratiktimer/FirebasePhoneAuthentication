package prateektimer.com.firebasephoneauthentication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class multiplayer extends AppCompatActivity implements View.OnClickListener {

    public static RelativeLayout loginLayout;
    public static SharedPreferences sharedPrefs;
    private static FragmentManager fragmentManager;
    String goto2;
    private int REQUEST_INVITE = 90;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT <= 23) {
            setTheme(R.style.Theme_AppCompat_NoActionBar);

        } else {
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer);
        goto2 = getIntent().getStringExtra("message");

findViewById(R.id.logout).setOnClickListener(this);

        // On close icon click finish activity
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.logout:
                FirebaseAuth mauth=FirebaseAuth.getInstance();
                FirebaseUser user=mauth.getCurrentUser();
                if(user!=null)
                    mauth.signOut();
                startActivity(new Intent(this,Login.class));
                finish();
                break;
        }
    }
}




