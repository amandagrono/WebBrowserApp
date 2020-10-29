package edu.temple.webbrowserapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

public class BrowserActivity extends AppCompatActivity implements ControlFragment.goButtonClick, PageViewFragment.setEditTextURL{

    PageViewFragment pvf;
    ControlFragment cf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);



        if(savedInstanceState != null){
            pvf = (PageViewFragment) getSupportFragmentManager().getFragment(savedInstanceState, "pvfFragment");
            cf = (ControlFragment) getSupportFragmentManager().getFragment(savedInstanceState, "cfFragment");
        }
        else {
            pvf = new PageViewFragment();
            cf = new ControlFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.control_layout, cf)
                    .add(R.id.pageLayout, pvf)
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
        cf.setEditTextURL(url);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "pvfFragment", pvf);
        getSupportFragmentManager().putFragment(outState, "cfFragment", cf);
    }
}