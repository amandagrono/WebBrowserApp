package edu.temple.webbrowserapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;


public class BrowserActivity extends AppCompatActivity implements
        PageControlFragment.PageControlInterface,
        PageViewFragment.PageViewInterface,
        BrowserControlFragment.BrowserControlInterface,
        PageListFragment.PageListInterface,
        PagerFragment.SwitchTab {


    PageControlFragment pcf;
    BrowserControlFragment bcf;
    PageListFragment plf;
    PagerFragment pf;

    ArrayList<PageViewFragment> tabs;
    ArrayList<Bookmark> bookmarks;




    FragmentManager fm;

    int page = 0;

    public static String filename = "bookmarks.ser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);



        loadData();


        if(savedInstanceState == null)
        {
            tabs = new ArrayList<>();
        }
        else
        {
            tabs = savedInstanceState.getParcelableArrayList("tabs");
            page = savedInstanceState.getInt("page");
        }

        pcf = new PageControlFragment();
        bcf = new BrowserControlFragment();
        plf = new PageListFragment();
        fm = getSupportFragmentManager();

        File file = getFilesDir();


        //find out if page_control has a saved instance, if not create a new one

        if(fm.findFragmentById(R.id.page_control) == null){
            fm.beginTransaction()
                    .add(R.id.page_control, pcf)
                    .addToBackStack(null)
                    .commit();
        }
        else {
            pcf = (PageControlFragment) getSupportFragmentManager().findFragmentById(R.id.page_control);
        }

        //find out if page_display has a saved instance, if not create a new one
        if(fm.findFragmentById(R.id.page_display) == null){
            pf = new PagerFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("listofpages",  tabs);
            pf.setArguments(bundle);

            fm.beginTransaction()
                    .add(R.id.page_display, pf)
                    .addToBackStack(null)
                    .commit();
        }
        else
        {   pf = (PagerFragment) getSupportFragmentManager().findFragmentById(R.id.page_display); }

        //find out if browser control has a saved instance, if not create a new one
        if(fm.findFragmentById(R.id.browser_control) == null){
            fm.beginTransaction()
                    .add(R.id.browser_control, bcf)
                    .addToBackStack(null)
                    .commit();
        }
        else
        {   bcf = (BrowserControlFragment) getSupportFragmentManager().findFragmentById(R.id.browser_control); }

        //find out if page_list has a saved instance, if not create a new one
        if( (findViewById(R.id.page_list) != null)){
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("listofpages",  tabs);
            plf.setArguments(bundle);

            fm.beginTransaction()
                    .add(R.id.page_list, plf)
                    .addToBackStack(null)
                    .commit();
        }
        else
        {   plf = (PageListFragment) getSupportFragmentManager().findFragmentById(R.id.page_list); }
    }

    @Override
    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(bookmarks);
        editor.putString("bookmarks", json);
        editor.apply();
    }
    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("bookmarks", null);
        Type type = new TypeToken<ArrayList<Bookmark>>() {}.getType();
        bookmarks = gson.fromJson(json, type);

        if(bookmarks == null){
            bookmarks = new ArrayList<>();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outstate){
        super.onSaveInstanceState(outstate);
        outstate.putParcelableArrayList("tabs", tabs);
        outstate.putInt("page", page);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        savedInstanceState.getParcelableArrayList("tabs");
    }

    @Override
    public void pressedGo(String url) {
        FragmentStatePagerAdapter adapter = (FragmentStatePagerAdapter) pf.vp.getAdapter();
        int whichIndex = pf.vp.getCurrentItem();
        PageViewFragment currentPvf = (PageViewFragment) adapter.getItem(whichIndex);
        currentPvf.pressedGo(url);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void pressedBack() {
        FragmentStatePagerAdapter adapter = (FragmentStatePagerAdapter) pf.vp.getAdapter();
        int whichIndex = pf.vp.getCurrentItem();
        PageViewFragment currentPvf = (PageViewFragment) adapter.getItem(whichIndex);
        currentPvf.pressedBack();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void pressedForward() {
        FragmentStatePagerAdapter adapter = (FragmentStatePagerAdapter) pf.vp.getAdapter();
        int whichIndex = pf.vp.getCurrentItem();
        PageViewFragment currentPvf = (PageViewFragment) adapter.getItem(whichIndex);
        currentPvf.pressedForward();
        adapter.notifyDataSetChanged();
    }
    @Override
    public void itemPicked(int position) {
        pf.vp.setCurrentItem(position);
    }
    @Override
    public void updateURL(String url) {

        pcf.updateUrl(url);
        changeTitle();
    }

    @Override
    public void newTab() {
        tabs.add( new PageViewFragment());

        Objects.requireNonNull(pf.vp.getAdapter()).notifyDataSetChanged();
        pf.vp.setCurrentItem(tabs.size() - 1);

    }

    @Override
    public void saveBookmark() {

        Bookmark bookmark = new Bookmark(pf.pages.get(pf.vp.getCurrentItem()).webView.getUrl(), pf.pages.get(pf.vp.getCurrentItem()).webView.getTitle());

        Log.d("message", "URL in saveBookmark() is: " + bookmark.getUrl());

        saveData();

        if(!bookmarks.contains(bookmark))
            bookmarks.add(bookmark);
    }

    @Override
    public void viewBookmarks() {
        Intent intent = new Intent(this, BookmarkActivity.class);
        intent.putExtra("BOOKMARKS", bookmarks);

        startActivityForResult( intent , 2);
    }


    @Override
    public void switchTab(String url) {
        changeTitle();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("message lol", "Request Code: " + requestCode);
        Log.d("message lol", "Result Code: " + resultCode);

        saveData();

        Bundle bundle = new Bundle();
        bundle = data.getBundleExtra("BUNDLE");

        super.onActivityResult(requestCode, resultCode, data);
        bookmarks = bundle.getParcelableArrayList("RETURN_BOOKMARKS");
        if(resultCode == 11) {
            Bookmark bookmark = (Bookmark) bundle.getParcelable("BOOKMARK");
            String url = bookmark.getUrl();
            pressedGo(url);
        }

    }

    public void changeTitle()
    {
        PagerAdapter adapter = pf.vp.getAdapter();
        int whichFragmentIndex = pf.vp.getCurrentItem();
        FragmentStatePagerAdapter pa = (FragmentStatePagerAdapter) adapter;
        PageViewFragment currentpvf = (PageViewFragment) pa.getItem(whichFragmentIndex);

        if (getSupportActionBar() != null) getSupportActionBar().setTitle(currentpvf.webView.getTitle());

        if((findViewById(R.id.page_list) != null))
        {
            if(plf.lv != null)
            {
                ArrayAdapter arradapter = (ArrayAdapter) plf.lv.getAdapter();
                arradapter.notifyDataSetChanged();
            }
        }

        String newURL = currentpvf.webView.getUrl();
        pcf.updateUrl(newURL);
    }


}