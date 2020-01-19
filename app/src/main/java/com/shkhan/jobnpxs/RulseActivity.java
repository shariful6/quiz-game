package com.shkhan.jobnpxs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class RulseActivity extends AppCompatActivity {

    private AdView madView;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rulse);

        setTitle("Rulse");

        madView=findViewById(R.id.adView);

        MobileAds.initialize(this,"ca-app-pub-3940256099942544~3347511713"); //  App id//initialize mobile ad
        mInterstitialAd= new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");//interstitial
        AdRequest adRequest = new AdRequest.Builder().build();//ad request
        madView.loadAd(adRequest);//load banner ads
        mInterstitialAd.loadAd(adRequest);

        mInterstitialAd.setAdListener(new AdListener() {  //interestitial ad listener
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
            }
            @Override
            public void onAdLoaded() {

                mInterstitialAd.show();
            }
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
                Toast.makeText(RulseActivity.this, "Don't Click !!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                Toast.makeText(RulseActivity.this, "Wrong Task !!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

        });



    }



}
