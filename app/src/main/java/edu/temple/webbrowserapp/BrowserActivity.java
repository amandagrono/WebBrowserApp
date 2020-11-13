package edu.temple.webbrowserapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;

public class BrowserActivity extends AppCompatActivity implements PageControlFragment.goButtonClick, PageViewFragment.setEditTextURL{

    PageViewFragment pvf;
    PageControlFragment pcf;
    BrowserControlFragment bcf;
    PageListFragment plf;

    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        if(savedInstanceState != null){

            plf = (PageListFragment) getSupportFragmentManager().getFragment(savedInstanceState, "plfFragment");
            pvf = (PageViewFragment) getSupportFragmentManager().getFragment(savedInstanceState, "pvfFragment");
            pcf = (PageControlFragment) getSupportFragmentManager().getFragment(savedInstanceState, "cfFragment");
        }
        else {
            pvf = new PageViewFragment();
            pcf = new PageControlFragment();
            bcf = new BrowserControlFragment();
            plf = new PageListFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.page_control, pcf)
                    .add(R.id.page_display, pvf)
                    .add(R.id.browser_control, bcf)
                    .add(R.id.page_list, plf)
                    .addToBackStack(null)
                    .commit();
        }

    }
    @Override
    public void pressedGo(String url) {
        pvf.goToURL(url);
    }
    @Override
    public void pressedBack() {
        pvf.pressBack();
    }
    @Override
    public void pressedForward() {

        pvf.pressForward();
    }
    @Override
    public void setEditText(String url) {
        pcf.setEditTextURL(url);
    }

    @Override
    public void setListView(String title) {
        if(counter == 0){
            plf.addToList(title);
            counter++;
        }
        else {
            plf.changeList(counter - 1, title);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "pvfFragment", pvf);
        getSupportFragmentManager().putFragment(outState, "cfFragment", pcf);
        getSupportFragmentManager().putFragment(outState, "plfFragment", plf);

    }


}