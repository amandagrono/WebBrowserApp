package edu.temple.webbrowserapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class PageListFragment extends Fragment {


    public PageListFragment() {
        // Required empty public constructor
    }

    ArrayList<String> arrayList;
    ListView lv;
    ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View l = inflater.inflate(R.layout.fragment_page_list, container, false);

        arrayList = new ArrayList<>();
        adapter = new ArrayAdapter(this.getContext(), R.layout.activity_listview, arrayList);
        lv = l.findViewById(R.id.listView);
        lv.setAdapter(adapter);

        return l;
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("arrayList", arrayList);


    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null){
            adapter = new ArrayAdapter(this.getContext(), R.layout.activity_listview, savedInstanceState.getStringArrayList("arrayList"));
            lv.setAdapter(adapter);
        }
    }

    void addToList(String title){
        arrayList.add(title);
        lv.setAdapter(new ArrayAdapter<String>(this.getContext(), R.layout.activity_listview, arrayList));
    }

    void changeList(int position, String title){
        arrayList.set(position, title);
        System.out.println(arrayList.toString() + "_______________________________________________________________________________________");
        lv.setAdapter(new ArrayAdapter<String>(this.getContext(), R.layout.activity_listview, arrayList));
    }

}