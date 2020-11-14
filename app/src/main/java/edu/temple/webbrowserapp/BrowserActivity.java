package edu.temple.webbrowserapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import android.os.Bundle;
import android.widget.ArrayAdapter;


import java.util.ArrayList;

public class BrowserActivity extends AppCompatActivity implements
        PageControlFragment.PageControlInterface,
        PageViewFragment.updateURLInterface,
        BrowserControlFragment.ClickedNewTabButton,
        PageListFragment.PageListInterface,
        PagerFragment.SwitchTab {

    PageControlFragment pcf;
    BrowserControlFragment bcf;
    PageListFragment plf;
    PagerFragment pf;

    ArrayList<PageViewFragment> tabs;

    FragmentManager fm;

    int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

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

        if(fm.findFragmentById(R.id.page_control) == null){
            fm.beginTransaction()
                    .add(R.id.page_control, pcf)
                    .addToBackStack(null)
                    .commit();
        }
        else
        { pcf = (PageControlFragment) getSupportFragmentManager().findFragmentById(R.id.page_control); }

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

        if(fm.findFragmentById(R.id.browser_control) == null){
            fm.beginTransaction()
                    .add(R.id.browser_control, bcf)
                    .addToBackStack(null)
                    .commit();
        }
        else
        {   bcf = (BrowserControlFragment) getSupportFragmentManager().findFragmentById(R.id.browser_control); }

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
    public void sendURL(String url) {
        FragmentStatePagerAdapter adapter = (FragmentStatePagerAdapter) pf.vp.getAdapter();
        int whichFragmentIndex = pf.vp.getCurrentItem();
        PageViewFragment currentpvf = (PageViewFragment) adapter.getItem(whichFragmentIndex);
        currentpvf.pressedGo(url);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void goBack() {
        FragmentStatePagerAdapter adapter = (FragmentStatePagerAdapter) pf.vp.getAdapter();
        int whichFragmentIndex = pf.vp.getCurrentItem();
        PageViewFragment currentpvf = (PageViewFragment) adapter.getItem(whichFragmentIndex);
        currentpvf.pressedBack();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void goForward() {
        FragmentStatePagerAdapter adapter = (FragmentStatePagerAdapter) pf.vp.getAdapter();
        int whichFragmentIndex = pf.vp.getCurrentItem();
        PageViewFragment currentpvf = (PageViewFragment) adapter.getItem(whichFragmentIndex);
        currentpvf.pressedForward();
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

        pf.vp.getAdapter().notifyDataSetChanged();
        pf.vp.setCurrentItem(tabs.size() - 1);

    }



    @Override
    public void switchTab(String url) {


        changeTitle();
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