package com.shkhan.jobnpxs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ResultActivity extends AppCompatActivity {
    TextView qsn,pointsTV,earnMSG,balTextBox;
    Button playAgain;

    String tScore;

    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    String uId;
    private FirebaseUser user;


    private AdView madView;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        setTitle("Results");

        databaseReference= FirebaseDatabase.getInstance().getReference("user");

        qsn=findViewById(R.id.totalQsnID);
        pointsTV=findViewById(R.id.totalPointsID);
        playAgain=findViewById(R.id.playAgainBtnID);
        earnMSG=findViewById(R.id.earnMsgID);
        balTextBox=findViewById(R.id.balTVID);

        madView=findViewById(R.id.adView);

        //  String id= Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);//device id

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        uId = user.getUid();

        retriveTempScore();
        retriveScore();

           //ads start
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
                Toast.makeText(ResultActivity.this, "Don't Click !!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                Toast.makeText(ResultActivity.this, "Wrong Task !!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

        });
        //ads end


        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(ResultActivity.this,MainActivity.class);
                intent.putExtra("value","1");
                startActivity(intent);
                finish();
            }
        });
    }

    public void retriveTempScore()
    {
        databaseReference.child(uId).child("tempScore").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    tScore = dataSnapshot.getValue(String.class);
                    pointsTV.setText("Correct Answer: "+tScore);
                }
                else{

                    pointsTV.setText("Correct Answer: 0");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(ResultActivity.this, "Failed!!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void retriveScore()
    {
        databaseReference.child(uId).child("balance").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    String sc = dataSnapshot.getValue(String.class);
                    balTextBox.setText(sc);
                }
                else{
                    balTextBox.setText("0");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(ResultActivity.this, "Failed!!", Toast.LENGTH_SHORT).show();
            }
        });
    }



}