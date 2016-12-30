package com.example.android.swipescreenpractice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PageFragment extends android.support.v4.app.Fragment {
    TextView textView;

    public PageFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.page_fragment_layout, container, false);

//        textView = (TextView)view.findViewById(R.id.textView);
//        Bundle bundle = getArguments();
//        String message = Integer.toString(bundle.getInt("count"));

//        textView.setText("This is the "+message+ "Swipe View Page");
        return view;
    }
}