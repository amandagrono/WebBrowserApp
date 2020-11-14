package edu.temple.webbrowserapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class PagerFragment extends Fragment {

    View l;
    ViewPager vp;

    ArrayList<PageViewFragment> pages;

    SwitchTab parentActivity;

    public PagerFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if(bundle != null){
            pages = bundle.getParcelableArrayList("listofpages");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        l = inflater.inflate(R.layout.fragment_pager, container, false);

        vp = l.findViewById(R.id.viewpager);

        if(savedInstanceState == null){
            pages.add(new PageViewFragment());
        }

        vp.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return pages.get(position);
            }

            @Override
            public int getCount() {
                return pages.size();
            }

            @Override
            public int getItemPosition(@NonNull Object object) {
                if (pages.contains(object))
                    return pages.indexOf(object);
                else
                    return POSITION_NONE;
            }
        });

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ((SwitchTab) getActivity()).switchTab(pages.get(position).webView.getUrl().toString());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return l;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof PagerFragment.SwitchTab)
        {
            parentActivity = (PagerFragment.SwitchTab) context;
        }
        else
        {
            throw new RuntimeException("implement SwitchTab");
        }
    }


    public interface SwitchTab{
        void switchTab(String url);
    }
}