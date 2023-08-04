package com.rohanmaharaj.owntry.privatesurfer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    EditText urlInput;
    ImageView clearView, webBack, webForeward, webRefresh, webShare;
    WebView webView;
    ProgressBar progrsBr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        urlInput = findViewById(R.id.urlInput);
        clearView = findViewById(R.id.cancelIcon);
        webView = findViewById(R.id.webView);
        progrsBr = findViewById(R.id.progessBar);
        webBack = findViewById(R.id.webBack);
        webForeward = findViewById(R.id.webForward);
        webRefresh = findViewById(R.id.webRefresh);
        webShare = findViewById(R.id.webShare);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);

        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progrsBr.setProgress(newProgress);
            }
        });

        webBack.setOnClickListener(view -> {
            if(webView.canGoBack()){
                webView.goBack();
            }
        });
        webForeward.setOnClickListener(view -> {
            if(webView.canGoForward()){
                webView.goForward();
            }
        });
        webRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.reload();
            }
        });
        webShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareBtn = new Intent(Intent.ACTION_VIEW);
                shareBtn.setAction(Intent.ACTION_SEND);
                shareBtn.putExtra(Intent.EXTRA_TEXT, webView.getUrl());
                shareBtn.setType("text/plain");
                startActivity(shareBtn);
            }
        });

        loadMyUrl("https://www.yt1s.com");
        urlInput.setOnEditorActionListener((textView, i, keyEvent) -> {
            if(i == EditorInfo.IME_ACTION_GO || i == EditorInfo.IME_ACTION_DONE){
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(urlInput.getWindowToken(), 0);
                loadMyUrl(urlInput.getText().toString());
                return true;
            }
            return false;
        });

        clearView.setOnClickListener(view -> urlInput.setText(""));

    }

    public void loadMyUrl(String url){
        //checking if it in weburl form if yes open it or else search it in google
        boolean checkUrl = Patterns.WEB_URL.matcher(url).matches();
        if(checkUrl){
            webView.loadUrl(url);
        }else {
            webView.loadUrl("https://www.google.com/search?q="+url);
        }
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }else {
            super.onBackPressed();
        }
    }

    class MyWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            urlInput.setText(webView.getUrl());
            super.onPageStarted(view, url, favicon);
            progrsBr.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progrsBr.setVisibility(View.GONE);
        }
    }
}