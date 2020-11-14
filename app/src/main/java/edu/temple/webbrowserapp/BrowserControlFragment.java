package edu.temple.webbrowserapp;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


public class BrowserControlFragment extends Fragment {


    View l;
    ImageButton newTabButton;

    PageListFragment.PageListInterface parentActivity;

    public BrowserControlFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof BrowserControlFragment.BrowserControlInterface){
            parentActivity = (PageListFragment.PageListInterface) context;
        }
        else{
            throw new RuntimeException("implement BrowserControlInterface");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        l = inflater.inflate(R.layout.fragment_browser_control, container, false);

        newTabButton = l.findViewById(R.id.imageButton);
        newTabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BrowserControlInterface) getActivity()).newTab();
            }
        });

        return l;
    }

    interface BrowserControlInterface{
        void newTab();
    }
}