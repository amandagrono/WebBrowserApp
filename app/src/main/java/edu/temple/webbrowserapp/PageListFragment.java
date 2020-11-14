package edu.temple.webbrowserapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class PageListFragment extends Fragment {

    private ArrayList<PageViewFragment> tabs;

    View l;
    ListView lv;

    PageListInterface parentActivity;

    public PageListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof PageListInterface){
            parentActivity = (PageListInterface) context;
        }
        else{
            throw new RuntimeException("implement PageListFragment");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if(bundle != null){
            tabs = bundle.getParcelableArrayList("listofpages");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        l = inflater.inflate(R.layout.fragment_page_list, container, false);


        lv = l.findViewById(R.id.listView);

        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, tabs){
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView tabName = new TextView(getContext());
                tabName.setText(tabs.get(position).webView.getTitle());
                return tabName;
            }
        };
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                parentActivity.itemPicked(position);
            }
        });

        return l;
    }

    interface PageListInterface{
        void itemPicked(int position);
    }


}