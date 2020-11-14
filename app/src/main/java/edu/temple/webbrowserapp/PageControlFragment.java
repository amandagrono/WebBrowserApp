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


    View layout;
    EditText myEditText;
    ImageButton backButton;
    ImageButton nextButton;
    ImageButton searchButton;
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
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.fragment_page_control, container, false);

        searchButton = layout.findViewById(R.id.goButton);
        backButton = layout.findViewById(R.id.backButton);
        nextButton = layout.findViewById(R.id.forwardButton);
        myEditText = layout.findViewById(R.id.editText);

        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                url = myEditText.getText().toString();
                ((PageControlInterface) getActivity()).sendURL(url);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((PageControlInterface) getActivity()).goBack();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((PageControlInterface) getActivity()).goForward();
            }
        });

        return layout;
    }

    public void updateUrl(String url)
    {
        myEditText.setText(url);
    }

    interface PageControlInterface{
        void sendURL(String url);
        void goBack();
        void goForward();
    }
}