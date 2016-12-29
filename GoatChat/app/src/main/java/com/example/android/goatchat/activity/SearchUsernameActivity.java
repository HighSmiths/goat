package com.example.android.goatchat.activity;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.SearchView;

import com.example.android.goatchat.Constants;
import com.example.android.goatchat.R;

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

        SearchView searchView = (SearchView) findViewById(R.id.searchbar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d(Constants.LOG_TAG, "onQueryTextSubmit ");
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
