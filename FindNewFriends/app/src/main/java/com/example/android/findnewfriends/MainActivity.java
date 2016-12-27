package com.example.android.findnewfriends;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Car> myCars = new ArrayList<Car>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateCarList();
        populateListView();
    }

    private void populateCarList(){
        myCars.add(new Car("Max Highsmith", 33, R.drawable.blank_user, "Needs more goats"));
        myCars.add(new Car("Michael Highsmith", 12, R.drawable.blank_user, "Dude...More goats"));
        myCars.add(new Car("Michael Zhao", 600, R.drawable.blank_user, "Goattastic"));
        myCars.add(new Car("John Park", 200, R.drawable.blank_user, "Pretty Goat"));
        myCars.add(new Car("P dizzle", 12, R.drawable.blank_user, "Needs more goats"));
    }


    private void populateListView(){
        ArrayAdapter<Car> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.carsListView);
        list.setAdapter(adapter);

    }

    private class MyListAdapter extends ArrayAdapter<Car>{
        public MyListAdapter(){
            super(MainActivity.this, R.layout.item_view, myCars);
        }

        //this overrides ArrayAdapter's getView
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.item_view, parent, false);
            }

            //Find the car to work with
            Car currentCar = myCars.get(position);

            //Fill the view
            ImageView imageView = (ImageView)itemView.findViewById(R.id.item_icon);
            imageView.setImageResource(currentCar.getIconID());

            //Make:
            TextView makeText = (TextView) itemView.findViewById(R.id.item_txtMake);
            makeText.setText(currentCar.getMake());

            //Year:
            TextView yearText = (TextView) itemView.findViewById(R.id.item_txtYear);
            yearText.setText("" + currentCar.getYear());

            //Condition:
            TextView conditionText = (TextView) itemView.findViewById(R.id.item_txtCondition);
            conditionText.setText(currentCar.getCondition());

            return itemView;
        }

        }

    }
