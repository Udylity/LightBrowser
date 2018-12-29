package com.udylity.lightbrowser;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class BrowserViewClient extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView v, String url){
        v.loadUrl(url);
        WebBrowser.mAddresbar.setText(url);
        return true;
    }
}
