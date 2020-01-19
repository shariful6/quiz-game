package com.shkhan.jobnpxs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class WithdrawActivity extends AppCompatActivity {

    TextView balanceTV;
    EditText walletET,npxsET;
    Button submitBtn;


    private FirebaseAuth mAuth;
    DatabaseReference databaseReference,database;
    String uId;
    private FirebaseUser user;


    private AdView madView;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        setTitle("Transfer Balance");

        databaseReference= FirebaseDatabase.getInstance().getReference("Withdrawlist");
        database=FirebaseDatabase.getInstance().getReference("user");

        balanceTV=findViewById(R.id.balenceWithdrawID);
        walletET=findViewById(R.id.walletETID);
        npxsET=findViewById(R.id.amountETID);
        submitBtn=findViewById(R.id.submitBtnID);

        madView=findViewById(R.id.adView);


        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        uId = user.getUid();


        final String value=getIntent().getStringExtra("npxs");
        balanceTV.setText(value);


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
                Toast.makeText(WithdrawActivity.this, "Don't Click !!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                Toast.makeText(WithdrawActivity.this, "Wrong Task !!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

        });

        retriveWithdrawStatus();

         submitBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               String tvAmount=balanceTV.getText().toString();
               String walletAddress=walletET.getText().toString();
               String wa=npxsET.getText().toString();

               if (walletAddress.isEmpty())
               {
                   walletET.setError("Enter wallet Addrees");
                   walletET.requestFocus();
                   return;
               }
               if (wa.isEmpty())
               {
                   npxsET.setError("Enter Amount");
                   npxsET.requestFocus();
                   return;
               }

               else {
                    int total_amount=Integer.parseInt(tvAmount);
                    int withdraw_amount=Integer.parseInt(wa);
                    int t_amount=total_amount-withdraw_amount;

                     if (withdraw_amount<6500)
                     {
                         Toast.makeText(WithdrawActivity.this, "Minimum withdraw 6500 NPXS", Toast.LENGTH_SHORT).show();
                     }
                   else if (withdraw_amount>total_amount)
                    {
                         Toast.makeText(WithdrawActivity.this, "Insufficiant Balance !!", Toast.LENGTH_SHORT).show();
                    }

                     else
                     {
                         String rest_amount=String.valueOf(t_amount);
                         databaseReference.child(uId).child("wallet").setValue(walletAddress);
                         databaseReference.child(uId).child("Amount").setValue(wa);
                         database.child(uId).child("balance").setValue(rest_amount);

                         balanceTV.setText(rest_amount);
                         walletET.setText("");
                         npxsET.setText("");
                         Toast.makeText(WithdrawActivity.this, "Withdraw Request Send !", Toast.LENGTH_SHORT).show();

                     }

               }

           }
       });
    }

        public void retriveWithdrawStatus()
        {
            databaseReference.child(uId).child("Amount").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists())
                    {
                        String status = dataSnapshot.getValue(String.class);
                        submitBtn.setEnabled(false);
                        submitBtn.setText("Withdraw Pending");

                    }
                    else
                        {
                           submitBtn.setEnabled(true);
                           submitBtn.setText("Submit");
                        }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Toast.makeText(WithdrawActivity.this, "Failed!!", Toast.LENGTH_SHORT).show();
                }
            });
        }


}
