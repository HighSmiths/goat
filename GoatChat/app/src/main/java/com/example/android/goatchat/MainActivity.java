package com.example.android.goatchat;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.appevents.AppEventsLogger;
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
import android.view.LayoutInflater;

public  class MainActivity extends AppCompatActivity{

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
   // private View view;
    private CallbackManager callbackManager;

    private LoginButton fbLoginButton;

    public void openButtonManager(){
        Intent intent = new Intent(this, ScreenManagerActivity.class);
        intent.putExtra("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Database.instance.addFriendForUserWithUID("Gn6YHvwr5yMgLsTKRLrdoul6hw52", "P9ZbZsni4OR0YPU9qiVDTeqFxO92");
        //TODO fix temp message id
        Database.instance.createMessage("TEMP","Gn6YHvwr5yMgLsTKRLrdoul6hw52", "P9ZbZsni4OR0YPU9qiVDTeqFxO92", true);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged( FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(Constants.LOG_TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    openButtonManager();

                } else {
                    // User is signed out
                   Log.d(Constants.LOG_TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(com.example.android.goatchat.R.layout.activity_main);
        LoginButton loginButton = (LoginButton) findViewById(R.id.connectWithFbButton);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Log.d("suc","CEst");
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

    public void createNewAccount(View view)
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
            }
        }
        else{
            Log.d("login","Faulty Email");
        }
        //createAccount(emailText,passwordText);
    }

    public boolean emailIsValid(String em)
    {
        return em.contains("@")&&(em.contains(".com")||em.contains(".edu"));
    }
    public boolean passwordIsValid(String ps)
    {
        return ps.length() >4;
    }

    public void signInAccount(View view)
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
                Log.d("login", "password to short");
            }
        }
        else{
            Log.d("login","Faulty Email");
        }
    }

    public void createAccount(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("STUF", "createUserWithEmail:onComplete:" + task.isSuccessful());
                        String uid = mAuth.getCurrentUser().getUid();
                        String email = mAuth.getCurrentUser().getEmail();
                        Database.instance.createNewUserRecordInFirebase(uid, email);

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                           Log.d("PRoblem", "uh oh");
                        }

                        // ...
                    }
                });
    }

    public void stubSign(View view){
        Log.d("you are","in the main frame");
        signIn("mh5234@truman.edu", "l1zard");
    }
    public void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(Constants.LOG_TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(Constants.LOG_TAG, "signInWithEmail:failed", task.getException());
                        }

                        // ...
                    }
                });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("tag", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("TAG", "signInWithCredential:onComplete:" + task.isSuccessful());

                        String uid = mAuth.getCurrentUser().getUid();
                        String email = mAuth.getCurrentUser().getEmail();
                        Database.instance.createNewUserRecordInFirebase(uid, email);
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "signInWithCredential", task.getException());
                        }

                        // ...
                    }
                });
    }






}
