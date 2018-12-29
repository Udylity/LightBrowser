package com.udylity.lightbrowser;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.os.Build;
import android.view.WindowManager;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkfullscreen();
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new WebBrowser())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                Intent settingsIntent = new Intent(this, BrowserSettings.class);
                startActivity(settingsIntent);
                return true;
            case R.id.refresh:
//                WebBrowser.mBrowser.reload();
                return true;
            case R.id.forward:
                if (WebBrowser.mBrowser.canGoForward()) {
                    WebBrowser.mBrowser.goForward();
                }
                return true;
            case R.id.back:
                if (WebBrowser.mBrowser.canGoBack()) {
                    WebBrowser.mBrowser.goBack();
                }
                return true;
            case R.id.clearhistory:
                WebBrowser.mBrowser.clearHistory();
                return true;
        }
        if(item.getItemId() != R.id.action_settings || item.getItemId() != R.id.clearhistory ){
            WebBrowser.mBrowser.reload();
            WebBrowser.mAddresbar.setText(WebBrowser.mBrowser.getUrl());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && WebBrowser.mBrowser.canGoBack()) {
            WebBrowser.mBrowser.goBack();
            WebBrowser.mBrowser.reload();
            WebBrowser.mAddresbar.setText(WebBrowser.mBrowser.getUrl());
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("thewebsite", WebBrowser.mBrowser.getUrl());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        WebBrowser.mBrowser.loadUrl(savedInstanceState.getString("thewebsite"));
    }

    private void checkfullscreen() {
        SharedPreferences getSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(getBaseContext());
        boolean fullscreen = getSharedPrefs.getBoolean("fullscreen", false);
        if (fullscreen == true) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
                getWindow().addFlags(View.SYSTEM_UI_FLAG_FULLSCREEN);
                getWindow().addFlags(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                getWindow().addFlags(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            }else{
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
        }
    }

}
