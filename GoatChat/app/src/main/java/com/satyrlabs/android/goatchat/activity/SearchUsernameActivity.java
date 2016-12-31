package com.satyrlabs.android.goatchat.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.satyrlabs.android.goatchat.Constants;
import com.satyrlabs.android.goatchat.Database;
import com.satyrlabs.android.goatchat.R;
import com.satyrlabs.android.goatchat.callback.GetUsersCallback;
import com.satyrlabs.android.goatchat.models.User;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Map;

public class SearchUsernameActivity extends AppCompatActivity {


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*getMenuInflater().inflate(R.menu.options_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d(Constants.LOG_TAG, "onQueryTextSubmit ");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d(Constants.LOG_TAG, "onQueryTextChange ");
                return false;
            }
        });
*/
        return true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_username);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final TextView textView = (TextView) findViewById(R.id.fID);
        final Button button = (Button) findViewById(R.id.addFriend);
        SearchView searchView = (SearchView) findViewById(R.id.searchbar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d(Constants.LOG_TAG, "onQueryTextSubmit ");

                class helper implements GetUsersCallback {
                    @Override
                    public void execute(Map<String, User> users, boolean success) {
                        Log.d(Constants.LOG_TAG,users.toString());
                        if (success) {
                            for (User user : users.values()) {
                                textView.setText(user.username);
                            }
                            for (User user : users.values()) {
                                final User finUser = user;
                                button.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        Log.d("clicked", "friendbutton");
                                        Log.d(Constants.LOG_TAG, FirebaseAuth.getInstance().getCurrentUser().getUid() + "," + finUser.uid);
                                        //TODO fix temp message id
                                        Database.instance.addFriendForUserWithUID(FirebaseAuth.getInstance().getCurrentUser().getUid(), finUser.uid);
                                    }
                                });
                            }
                        } else {
                            Toast.makeText(getBaseContext(), Constants.USER_DOESNT_EXIST, Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                Database.instance.getUserWithUsername(s,new helper());
                return false;
            }


            @Override
            public boolean onQueryTextChange(String s) {
                doMySearch("test");
                return false;
            }

        });

        /*
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = "q";//intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
        */
    }
    public void doMySearch(String quer){
        Log.d(Constants.LOG_TAG,"calling");
    }

}
