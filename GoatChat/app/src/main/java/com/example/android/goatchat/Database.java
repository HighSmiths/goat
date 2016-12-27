package com.example.android.goatchat;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.*;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.HashMap;


/**
 * Created by mz on 12/26/16.
 */

public class Database {
    public static Database instance = new Database();

    private FirebaseDatabase database;

    private Database() {
        database = FirebaseDatabase.getInstance();
    }

    public void setData(String data) {
        DatabaseReference myRef = database.getReference("message");
        myRef.setValue(data);
    }

    public void getUserWithUID(String userId) {

        database.getReference("users/" + userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                Log.d(Constants.LOG_TAG, user.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void createNewUserRecordInFirebase(String userId, String email) {
        User user = new User(userId, email);
        user.friends.put("id", userId);
        database.getReference("users/" + userId).setValue(user);

    }

    public void execute() {
        Log.d(Constants.LOG_TAG, "Executing DB methods");

        FirebaseUser curUser = FirebaseAuth.getInstance().getCurrentUser();

        String uid = curUser.getUid();
        String email = curUser.getEmail();
        Log.d(Constants.LOG_TAG, uid);
        Log.d(Constants.LOG_TAG, email);

        createNewUserRecordInFirebase(uid, email);

        getUserWithUID(uid);
    }
}
