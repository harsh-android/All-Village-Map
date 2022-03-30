package com.appstudio.map.liveallvillagemap;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.wang.avi.AVLoadingIndicatorView;

public class privacy_policyactivity extends AppCompatActivity {

    private AVLoadingIndicatorView progressBar;
    private WebView web;
    private CardView card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policyactivity);

//        AdView adView = new AdView(this);
//        adView.setAdSize(AdSize.SMART_BANNER);
//        adView.setAdUnitId(getString(R.string.app_banner_id));
//
//        adView = findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);


        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        card=(CardView)findViewById(R.id.card);
        web = (WebView) findViewById(R.id.web);
        progressBar = (AVLoadingIndicatorView) findViewById(R.id.progressBar1);

        progressBar.setVisibility(View.VISIBLE);
        card.setVisibility(View.VISIBLE);
        web.getSettings().setJavaScriptEnabled(true);
        web.loadUrl("https://appvision4.blogspot.com/2019/03/appvision-privacy-policy-privacy-policy.html");
        web.getSettings().setDisplayZoomControls(true);
        web.getSettings().setBuiltInZoomControls(true);
        web.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        web.setWebViewClient(new myWebClient());

    }

    public class myWebClient extends WebViewClient {


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            progressBar.setVisibility(View.VISIBLE);
            card.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            return true;

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.GONE);
                    card.setVisibility(View.GONE);
                }
            }, 2000);

        }
    }
}
