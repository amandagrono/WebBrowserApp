package edu.temple.webbrowserapp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;


public class PageControlFragment extends Fragment {


    public PageControlFragment() {
        // Required empty public constructor
    }

    private goButtonClick browserActivity;
    EditText editText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View l = inflater.inflate(R.layout.fragment_page_control, container, false);

        ImageButton goButton = l.findViewById(R.id.goButton);
        ImageButton backButton = l.findViewById(R.id.backButton);
        ImageButton forwardButton = l.findViewById(R.id.forwardButton);
        editText = l.findViewById(R.id.editText);


        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = editText.getText().toString();
                browserActivity.pressedGo(url);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                browserActivity.pressedBack();
            }

        });
        forwardButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                browserActivity.pressedForward();
            }
        });

        return l;
    }
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString("editText", editText.getText().toString());

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null){
            editText.setText(savedInstanceState.getString("editText"));
        }
    }

    @Override
    public void onAttach(Context context){

        super.onAttach(context);
        if(context instanceof goButtonClick){
            browserActivity = (goButtonClick) context;
        }
        else{
            throw new ClassCastException(context.toString() + "must implement goButtonClick");
        }


    }
    public void setEditTextURL(String url){
        editText.setText(url);

    }
    interface goButtonClick{
        void pressedGo(String url);
        void pressedBack();
        void pressedForward();
    }
}