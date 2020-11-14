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


    View layout;
    ImageButton newTabButton;

    PageListFragment.PageListInterface parentActivity;

    public BrowserControlFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof BrowserControlFragment.ClickedNewTabButton){
            parentActivity = (PageListFragment.PageListInterface) context;
        }
        else{
            throw new RuntimeException("implement ClickedNewTabButton");
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
        layout = inflater.inflate(R.layout.fragment_browser_control, container, false);

        newTabButton = layout.findViewById(R.id.imageButton);
        newTabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ClickedNewTabButton) getActivity()).newTab();
            }
        });

        return layout;
    }

    interface ClickedNewTabButton{
        void newTab();
    }
}