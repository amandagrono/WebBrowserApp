package edu.temple.webbrowserapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import java.util.concurrent.TimeUnit;

public class PageViewFragment extends Fragment{

    public PageViewFragment() {
        // Required empty public constructor
    }

    WebView webView;
    setEditTextURL parentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View l = inflater.inflate(R.layout.fragment_page_view, container, false);

        webView = l.findViewById(R.id.webView);
        webView.setWebViewClient(new MyBrowser());
        if(savedInstanceState == null) {
            webView.loadUrl("https://google.com");
        }
        else{
            webView.restoreState(savedInstanceState);
        }

        return l;

    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putString("CurrentUrl", webView.getUrl());
        webView.saveState(outState);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null) {
            webView.restoreState(savedInstanceState);
        }
    }

    public void onAttach(@NonNull Context context){

        super.onAttach(context);
        if(context instanceof PageViewFragment.setEditTextURL){
            parentActivity = (setEditTextURL) context;
        }
        else{
            throw new ClassCastException(context.toString() + "must implement setEditTextUrl");
        }


    }


    public void goToURL(String url){
        webView.loadUrl(checkURL(url));

    }

    private String checkURL(String url){
        if(url.toLowerCase().contains("https://www.")){
            return url;
        }
        else if(url.toLowerCase().contains("https://")){
            return url;
        }
        else{
            return "https://www." + url;
        }
    }
    public void pressBack(){
        webView.goBack();


    }
    public void pressForward(){
        webView.goForward();

    }
    interface setEditTextURL{
        void setEditText(String url);
    }

    private class MyBrowser extends WebViewClient{
        @Override
        public void onPageFinished(WebView view, String url){
            parentActivity.setEditText(webView.getUrl());
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request){
            webView.loadUrl(request.getUrl().toString());
            return true;
        }
    }



}