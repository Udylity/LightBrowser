package com.udylity.lightbrowser;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

public class WebBrowser extends Fragment implements View.OnClickListener {

    public static WebView mBrowser;
    public static EditText mAddresbar;
    private static Button go;
    private static String theWebsite;

    public WebBrowser() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mBrowser = (WebView) rootView.findViewById(R.id.webviewbrowser);
        mAddresbar = (EditText) rootView.findViewById(R.id.addressbar);
        go = (Button) rootView.findViewById(R.id.gotoaddress);

        go.setOnClickListener(this);

        setupWebView();

        return rootView;
    }

    private void setupWebView() {
        mBrowser.getSettings().setSupportMultipleWindows(true);
        mBrowser.getSettings().setBuiltInZoomControls(true);
        mBrowser.getSettings().setJavaScriptEnabled(true);
        mBrowser.getSettings().setLoadWithOverviewMode(true);
        mBrowser.getSettings().setUseWideViewPort(true);
        mBrowser.getSettings().getSavePassword();

        mBrowser.setWebViewClient(new BrowserViewClient());
        try {
            mBrowser.loadUrl("https://www.google.com");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gotoaddress:

                theWebsite = mAddresbar.getText().toString();
                theWebsite = theWebsite.trim();
                checkFrontUrl();

                // hiding keyboard
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mAddresbar.getWindowToken(), 0);
                break;
        }
    }

    private void checkFrontUrl(){
        if (theWebsite.startsWith("http://www.")) {
            checkBackUrl();
        } else if (theWebsite.startsWith("https://www.")) {
            checkBackUrl();
        } else if (theWebsite.startsWith("www.")) {
            theWebsite = "https://" + theWebsite;
            checkBackUrl();
        } else {
            theWebsite = "https://www." + theWebsite;
            checkBackUrl();
        }
    }

    private void checkBackUrl() {
        if (theWebsite.contains(".com") || theWebsite.contains(".org")
                || theWebsite.contains(".edu") || theWebsite.endsWith(".html")
                || theWebsite.endsWith(".gov")) {
            mBrowser.loadUrl(theWebsite);
        } else {
            theWebsite = theWebsite + ".com";
            mBrowser.loadUrl(theWebsite);
        }
    }
    public static void updateAddressBar(){
        mAddresbar.setText(mBrowser.getUrl());
    }

}

