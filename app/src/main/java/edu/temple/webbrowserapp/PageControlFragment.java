package edu.temple.webbrowserapp;

import android.content.Context;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

public class PageControlFragment extends Fragment {


    View l;
    EditText editText;
    ImageButton backButton;
    ImageButton nextButton;
    ImageButton goButton;
    PageControlInterface parentActivity;

    String url;

    public PageControlFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof PageControlInterface)
        {
            parentActivity = (PageControlInterface) context;
        }
        else
        {
            throw new RuntimeException("implement PageControlInterface");
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        l= inflater.inflate(R.layout.fragment_page_control, container, false);

        goButton = l.findViewById(R.id.goButton);
        backButton = l.findViewById(R.id.backButton);
        nextButton = l.findViewById(R.id.forwardButton);
        editText = l.findViewById(R.id.editText);

        goButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                url = editText.getText().toString();
                ((PageControlInterface) getActivity()).pressedGo(url);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((PageControlInterface) getActivity()).pressedBack();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((PageControlInterface) getActivity()).pressedForward();
            }
        });

        return l;
    }

    public void updateUrl(String url)
    {
        editText.setText(url);
    }

    interface PageControlInterface{
        void pressedGo(String url);
        void pressedBack();
        void pressedForward();
    }
}