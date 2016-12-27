package com.example.android.goatchat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.*;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    public void openUserInbox(){
        Intent intent = new Intent(this, inboxActivity.class);
        intent.putExtra("username", "garbage");
        startActivity(intent);
        Log.d(Constants.LOG_TAG, "open user inbox");
    }

    public void pushFriendListView(){
        Intent intent = new Intent(this, ListOfFriends.class);
        intent.putExtra("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
        Log.d(Constants.LOG_TAG, "Open friend list view");

        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged( FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(Constants.LOG_TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Database db = Database.instance;
                    db.execute();
                    // openUserInbox();
                    pushFriendListView();

                } else {
                    // User is signed out
                   Log.d(Constants.LOG_TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        Log.d("password", passwordText);
        Log.d("email", emailText);
       // String emailText = "mh5234@truman.edu";
      //  String passwordText = "l1zard";
        createAccount(emailText,passwordText);
    }

    public void signInAccount(View view)
    {
        EditText email = (EditText)findViewById((R.id.email));
        String emailText = email.getText().toString();
        EditText password = (EditText)findViewById((R.id.password));
        String passwordText = password.getText().toString();
        Log.d("password", passwordText);
        Log.d("email", emailText);
        signIn(emailText, passwordText);
    }

    public void createAccount(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("STUF", "createUserWithEmail:onComplete:" + task.isSuccessful());

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

}
