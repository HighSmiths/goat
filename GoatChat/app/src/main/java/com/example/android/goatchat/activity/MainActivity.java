package com.example.android.goatchat.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.*;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.goatchat.Constants;
import com.example.android.goatchat.Database;
import com.example.android.goatchat.R;
import com.example.android.goatchat.SwipeAdapter;
import com.example.android.goatchat.callback.GetUsersCallback;
import com.example.android.goatchat.models.User;
import com.example.android.goatchat.util.IabHelper;
import com.example.android.goatchat.util.IabResult;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.facebook.FacebookSdk;

import java.net.URL;
import java.util.Map;

public  class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private CallbackManager callbackManager;

    IabHelper mHelper;

    private AccessToken accessToken;



    //{{Main Activity Life cycle
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manageFirebaseAuth();
        accessFirebaseThroughFB();
        setupBilling();

    }
    //}}




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    //  onCreate helper methods
    // Instantiate firebaseAuthentication listenet
    public void manageFirebaseAuth(){
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(Constants.LOG_TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    openButtonManager();

                } else {
                    // User is signed out
                    Log.d(Constants.LOG_TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    //manage facebook access callback
    public void accessFirebaseThroughFB() {
        FacebookSdk.sdkInitialize(getApplicationContext());
       // accessToken = AccessToken.getCurrentAccessToken();
        callbackManager = CallbackManager.Factory.create();
        setContentView(com.example.android.goatchat.R.layout.activity_main);
        LoginButton loginButton = (LoginButton) findViewById(R.id.connectWithFbButton);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Log.d("suc", "CEst");
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
        FacebookSdk.sdkInitialize(getApplicationContext());
    }
    //}}


    //{{FORMAT CHECKER
    public boolean validCreation(String em)  //TODO
    {
        return true; //TODO check if username is on database yet
    }
    public boolean emailIsValid(String em)
    {
        return em.contains("@")&&(em.contains(".com")||em.contains(".edu"));
    }

    public boolean passwordIsValid(String ps)
    {
        return ps.length() >4;
    }
    //}}

    //{{Email Login
    public void signInViaEmail(View view)
    {
        EditText email = (EditText)findViewById((R.id.email));
        String emailText = email.getText().toString();
        EditText password = (EditText)findViewById((R.id.password));
        String passwordText = password.getText().toString();
        if(emailIsValid(emailText)) {
            if(passwordIsValid(passwordText)) {
                signIn(emailText, passwordText);
            }
            else {
                Log.d("login", "password to short");  //TODO TOAST
                Toast.makeText(getBaseContext(), Constants.PASSWORD_TOO_SHORT, Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Log.d("login","Faulty Email");
            Toast.makeText(getBaseContext(), Constants.EMAIL_INVALID, Toast.LENGTH_SHORT).show();
        }
    }

    public void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("STUF", "createUserWithEmail:onComplete:" + task.isSuccessful());
                        if (task.isSuccessful()) {
                            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            String email = mAuth.getCurrentUser().getEmail();
                        }

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(Constants.LOG_TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(getBaseContext(), Constants.CANT_SIGN_IN, Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    public void createAccountViaEmail(View view)
    {
        EditText email = (EditText)findViewById((R.id.email));
        String emailText = email.getText().toString();
        EditText password = (EditText)findViewById((R.id.password));
        String passwordText = password.getText().toString();
        if(emailIsValid(emailText)) {
            if(passwordIsValid(passwordText)) {
                createAccount(emailText,passwordText);
            }
            else {
                Log.d("login", "password to short");
                Toast.makeText(getBaseContext(), Constants.PASSWORD_TOO_SHORT, Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Log.d("login","Faulty Email");
            Toast.makeText(getBaseContext(), Constants.EMAIL_INVALID, Toast.LENGTH_SHORT).show();
        }

    }

    public void createAccount(String email, String password) {
        try {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Log.d(Constants.LOG_TAG, "Failed to Create Account");
                                Toast.makeText(getBaseContext(), Constants.CANT_CREATE_ACCOUNT, Toast.LENGTH_SHORT).show();
                            }
                            else {
                                String uid = mAuth.getCurrentUser().getUid();
                                String email = mAuth.getCurrentUser().getEmail();
                                if(validCreation(email))
                                {
                                    Database.instance.createNewUser(uid, email);
                                }
                            }


                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                        }
                    });
        }
        catch (Exception e)
        {
            Log.d(Constants.LOG_TAG, "That won't work");
        }
    }
    //}}

    //Sign in via facebook
    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());

        final Activity act = this;
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("TAG", "signInWithCredential:onComplete:" + task.isSuccessful());
                        String uid = mAuth.getCurrentUser().getUid();
                        String email = FirebaseAuth.getInstance().getCurrentUser().getProviderData().get(0).getDisplayName();
                        //Uri profpic = FirebaseAuth.getInstance().getCurrentUser().getProviderData().get(0).getPhotoUrl();

                        try {
                            Uri imageUri = FirebaseAuth.getInstance().getCurrentUser().getProviderData().get(0).getPhotoUrl();
                            Log.d(Constants.LOG_TAG,"uri"+ imageUri);
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(act.getContentResolver(), imageUri);
                            Database.instance.createNewUser(uid, email, bitmap);
                        }
                        catch(Exception e)
                        {
                            Log.d(Constants.LOG_TAG, "not even close"+e.toString());
                            Database.instance.createNewUser(uid, email);

                        }



                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "signInWithCredential", task.getException());
                            Toast.makeText(getBaseContext(), Constants.CANT_SIGN_IN, Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    //Go to Logged in Screen
    public void openButtonManager() {
        Log.d(Constants.LOG_TAG, "Firebase user auth"+FirebaseAuth.getInstance().getCurrentUser().getProviderData().get(0).getDisplayName());
        Intent intent = new Intent(this, ScreenManagerActivity.class);
        intent.putExtra("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
        startActivity(intent);
    }

    private void setupBilling() {

        String base64EncodedPublicKey = Constants.LICENSE_KEY;

        // compute your public key and store it in base64EncodedPublicKey
        mHelper = new IabHelper(this, base64EncodedPublicKey);


        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    // Oh no, there was a problem.
                    Log.d(Constants.LOG_TAG, "Problem setting up In-app Billing: " + result);
                } else {
                    Log.d(Constants.LOG_TAG, "In-app Billing set up:" + result);
                }


            }
        });
    }

}
