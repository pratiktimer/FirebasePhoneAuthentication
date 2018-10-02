package prateektimer.com.firebasephoneauthentication;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




public class Login extends AppCompatActivity {
    FirebaseInstanceId instanceID;
    public static String token_id;
    boolean checkgoing = false;
   public static boolean firstlogin=false;
    FirebaseDatabase database;

    DatabaseReference users;
    String nametheimage, getprofile,username2;
    private static FragmentManager fragmentManager;
    public static String catname;
    private static int RC_SIGN_IN = 123;
    List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.PhoneBuilder().build());
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    long p;
    boolean useralreadyexist=false;
    public Login() {
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");
     //   contacts = database.getReference("Contact");
    }
    private void getuserinfo() {
        FirebaseUser user = mAuth.getCurrentUser();

        final String getEmailId = user.getPhoneNumber();
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot zoneSnapshot : dataSnapshot.getChildren()) {
                    if (getEmailId.equals(zoneSnapshot.child("phonenumber").getValue(String.class))) {
                        nametheimage = (zoneSnapshot.child("phonenumber").getValue(String.class));
                        //	Toast.makeText(getActivity(),""+nametheimage,+Toast.LENGTH_SHORT).show();
                        getprofile = zoneSnapshot.child("getprofile").getValue(String.class);
                        //Toast.makeText(getActivity(),""+zoneSnapshot.child("fullname").getValue(String.class),Toast.LENGTH_SHORT).show();
                        username2=zoneSnapshot.child("fullname").getValue(String.class);

                    }

                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void updateStatusBarColor(String color){
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor(color));
        }
        // Color must be in hexadecimal fromat
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        //	Window window = getWindow();
        //	window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //	window.setStatusBarColor(Color.parseColor(color));

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (android.os.Build.VERSION.SDK_INT < 23) {
            setTheme(R.style.Theme_AppCompat_NoActionBar);


        } else {
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

      //  mAuth.signOut();
        firstlogin = false;

//        LocalBroadcastManager.getInstance(this).registerReceiver(tokenReceiver,
//                new IntentFilter("tokenReceiver"));
//        mAuth.addAuthStateListener(mAuthListener);

        fragmentManager = getSupportFragmentManager();
       // if (Helper.hasPermissions(Login.this) == false) {

   //    } else {
            if (savedInstanceState == null) {

                mAuth = FirebaseAuth.getInstance();
                //TextUtils.isEmpty(username2) | TextUtils.isEmpty(getprofile)
                //  mAuthListener = new FirebaseAuth.AuthStateListener() {
                //    @Override
                //  public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mAuth.getCurrentUser() != null) {
                    //

                    getuserinfo();
                    if(username2==null) {
                        replaceuser_profile_fragment();
                        //getActivity().overridePendingTransition(R.anim.right_enter, R.anim.left_out);
                    }
                    else{

                            startActivity(new Intent(Login.this, multiplayer.class));
                    }
//                    if (!TextUtils.isEmpty(username2) |!TextUtils.isEmpty(getprofile)) {
//                        startActivity(new Intent(Login.this, multiplayer.class));
////                        replaceuser_profile_fragment();
//                       return; // or break, continue, throw
//                    } else
//                    {
//                        if (Settings_Fragment.comingfromsettings == true)
//                            replaceuser_profile_fragment();
//                        else
//                        startActivity(new Intent(Login.this, multiplayer.class));
//                    }



                } else {
                    firstlogin = true;
                    if (android.os.Build.VERSION.SDK_INT <= 23) {

                        startActivityForResult(
                                AuthUI.getInstance()
                                        .createSignInIntentBuilder()
                                        .setAvailableProviders((
                                                        providers
                                                )
                                        ).setTheme(R.style.Theme_AppCompat_NoActionBar)
                                        .build(),
                                RC_SIGN_IN);
                    } else {
                        firstlogin = true;
                        startActivityForResult(
                                AuthUI.getInstance()
                                        .createSignInIntentBuilder()
                                        .setAvailableProviders((
                                                        providers
                                                )
                                        ).setTheme(R.style.mystyle)
                                        .build(),
                                RC_SIGN_IN);
                    }

                }

            }

//                replace_user_phone();
//            fragmentManager
//                    .beginTransaction()
//                    .replace(R.id.frameContainer, new User_phone_signup(),
//                            Utils.User_phone).commit();
      //  }
    }



//        mAuth = FirebaseAuth.getInstance();
//
//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                if (mAuth.getCurrentUser() != null) {
//                    replaceuser_profile_fragment();
//                }
//                else{
//                    replace_user_phone();
//
//                    //if (savedInstanceState == null) {
//
//                //    }
//
//                }
//            }
//        };
//
//
//
//
//        ;





    @Override
    protected void onStart() {
//
        super.onStart();
     //   mAuthListener.onAuthStateChanged(mAuth);
    //
    }
    protected static void replaceuser_profile_fragment() {

        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.right_enter,R.anim.left_out)
                .replace(R.id.frameContainer, new Edit_Profile_Fragment(),
                        Utils.user_profile_fragment).commitAllowingStateLoss();
        //	position= (int) getIntent().getExtras().get("position");
        //	email=(String) getIntent().getExtras().get("email");
        //	if(position==1){
        //		Toast.makeText(MainActivity.this,"verify email",Toast.LENGTH_SHORT).show();
        //	}
    }

//    protected void replace_user_phone() {
//
//        fragmentManager
//                .beginTransaction()
//                .setCustomAnimations(R.anim.right_enter,R.anim.left_out)
//                .replace(R.id.frameContainer, new User_phone_signup(),
//                        Utils.User_phone).commit();
//        //	position= (int) getIntent().getExtras().get("position");
//        //	email=(String) getIntent().getExtras().get("email");
//        //	if(position==1){
//        //		Toast.makeText(MainActivity.this,"verify email",Toast.LENGTH_SHORT).show();
//        //	}
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Toast.makeText(Login.this,""+requestCode,+Toast.LENGTH_SHORT).show();

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode ==RESULT_OK) {
                // Successfully signed in

                mAuth=FirebaseAuth.getInstance();
                final FirebaseUser user = mAuth.getCurrentUser();



                if(user.getPhoneNumber()!=null){
                    users.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            p= dataSnapshot.getChildrenCount();
                          //  Toast.makeText(Login.this,""+p,Toast.LENGTH_SHORT).show();
                            for (DataSnapshot zoneSnapshot : dataSnapshot.getChildren()) {
                                if (user.getPhoneNumber().equals(zoneSnapshot.child("phonenumber").getValue(String.class))) {
                                  useralreadyexist=true;
                                }

                            }

                            if(useralreadyexist) {
                                token_id= FirebaseInstanceId.getInstance().getToken();
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                if(mAuth.getCurrentUser()!=null){
                                    ;}


                            }
                            else{

                                final User user1 = new User(user.getPhoneNumber(), "", "",user.getUid());

                                users.child(String.valueOf(p))
                                        .setValue(user1);
                                token_id= FirebaseInstanceId.getInstance().getToken();
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                if(mAuth.getCurrentUser()!=null){
                                    }

                            }


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    String user_id=mAuth.getCurrentUser().getUid();
                 //   signupfirestore(user_id);

                    replaceuser_profile_fragment();
                }
                else{

                }
                        //replaceuser_profile_fragment();
                // ...
            }
            else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                 // finishAffinity();
                    finish();
                    return;
                }


            }
        }
                  }






//    BroadcastReceiver tokenReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            token_id = intent.getStringExtra("token");
//            if(token_id != null)
//            {
//                mAuth=FirebaseAuth.getInstance();
//                FirebaseUser user2=mAuth.getCurrentUser();
//                if(user2!=null)
//                    signupfirestore(user2.getUid());
//            }
//
//        }
//    };
}
