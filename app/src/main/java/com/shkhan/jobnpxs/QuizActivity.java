package com.shkhan.jobnpxs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.CountDownTimer;
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
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class QuizActivity extends AppCompatActivity {


    private QuestionsLibrary mQuestionsLibrary=new QuestionsLibrary();

    private static final long START_TIME_IN_MILLIS = 6000;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;


    private static final long START_TIME_IN_MILLIS2 = 60000;  //60 seconds
    private CountDownTimer mCountDownTimer2;
    private boolean mTimerRunning2;
    private long mTimeLeftInMillis2 = START_TIME_IN_MILLIS2;


    TextView mScoreView;
    TextView mQuestionView;
    TextView viewTV;
    TextView invalidTV;

    Button buttonChoice1;
    Button buttonChoice2;
    Button buttonChoice3;
    Button buttonChoice4;
    Button NextBtn;

    String mAnswer;
    int mScore=0;
    int tempScore=0; //corect ans number
    int mQuestionNumber=0;
    int invalid_click=0;
    int click=0;

    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;

    String uId;
    private FirebaseUser user;

    String retriveViewNum;
    int v;
    String key;
    int flg=0;
    int flg2=0;
    int wrong_click=0;



    private AdView madView;
    //private RewardedVideoAd mRewardVideoAd;
    private InterstitialAd mInterstitialAd;
    private InterstitialAd mInterstitialAd1;
    private InterstitialAd mInterstitialAd2;
    private InterstitialAd mInterstitialAd3;
    private InterstitialAd mInterstitialAd4;
    private InterstitialAd mInterstitialAd5;
    private InterstitialAd mInterstitialAd6;
    private InterstitialAd mInterstitialAd7;
    private InterstitialAd mInterstitialAd8;
    private InterstitialAd mInterstitialAd9;
    private InterstitialAd mInterstitialAd0;


    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        setTitle("Play Zone");

        databaseReference= FirebaseDatabase.getInstance().getReference("user");

        mScoreView=findViewById(R.id.scoreID);
        mQuestionView=findViewById(R.id.questionViewID);
        buttonChoice1=findViewById(R.id.btn1ID);
        buttonChoice2=findViewById(R.id.btn2ID);
        buttonChoice3=findViewById(R.id.btn3ID);
        buttonChoice4=findViewById(R.id.btn4ID);
        NextBtn=findViewById(R.id.nextBtnID);
        madView=findViewById(R.id.adView);
        viewTV=findViewById(R.id.viewId);
        invalidTV=findViewById(R.id.invalidClickID);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        uId = user.getUid();

        retriveInvalidClick();
        retriveClick();

        MobileAds.initialize(this,"ca-app-pub-3940256099942544~3347511713"); //  App id//initialize mobile ad  ca-app-pub-9305948379021213~8626897422

        mInterstitialAd= new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");//interstitial ca-app-pub-9305948379021213/9493611759
        AdRequest adRequest = new AdRequest.Builder().build();//ad request
        madView.loadAd(adRequest);//load banner ads
        mInterstitialAd.loadAd(adRequest);

        mInterstitialAd1= new InterstitialAd(this);
        mInterstitialAd1.setAdUnitId("ca-app-pub-3940256099942544/1033173712");//interstitial  ca-app-pub-9305948379021213/5913104177
        AdRequest adRequest1 = new AdRequest.Builder().build();//ad request
        mInterstitialAd1.loadAd(adRequest1);

        mInterstitialAd2= new InterstitialAd(this);
        mInterstitialAd2.setAdUnitId("ca-app-pub-3940256099942544/1033173712");//interstitial  ca-app-pub-9305948379021213/4867971038
        AdRequest adRequest2 = new AdRequest.Builder().build();//ad request
        mInterstitialAd2.loadAd(adRequest2);

        mInterstitialAd3= new InterstitialAd(this);
        mInterstitialAd3.setAdUnitId("ca-app-pub-3940256099942544/1033173712");//interstitial  ca-app-pub-9305948379021213/6621155969
        AdRequest adRequest3 = new AdRequest.Builder().build();//ad request
        mInterstitialAd3.loadAd(adRequest3);

        mInterstitialAd4= new InterstitialAd(this);
        mInterstitialAd4.setAdUnitId("ca-app-pub-3940256099942544/1033173712");//interstitial   ca-app-pub-9305948379021213/2241807694
        AdRequest adRequest4 = new AdRequest.Builder().build();//ad request
        mInterstitialAd4.loadAd(adRequest4);

        mInterstitialAd5= new InterstitialAd(this);
        mInterstitialAd5.setAdUnitId("ca-app-pub-3940256099942544/1033173712");//interstitial ca-app-pub-9305948379021213/1973859166
        AdRequest adRequest5 = new AdRequest.Builder().build();//ad request
        mInterstitialAd5.loadAd(adRequest5);

        mInterstitialAd6= new InterstitialAd(this);
        mInterstitialAd6.setAdUnitId("ca-app-pub-3940256099942544/1033173712");//interstitial  ca-app-pub-9305948379021213/2107195882
        AdRequest adRequest6 = new AdRequest.Builder().build();//ad request
        mInterstitialAd6.loadAd(adRequest6);

        mInterstitialAd7= new InterstitialAd(this);
        mInterstitialAd7.setAdUnitId("ca-app-pub-3940256099942544/1033173712");//interstitial ca-app-pub-9305948379021213/1340909126
        AdRequest adRequest7 = new AdRequest.Builder().build();//ad request
        mInterstitialAd7.loadAd(adRequest7);

        mInterstitialAd8= new InterstitialAd(this);
        mInterstitialAd8.setAdUnitId("ca-app-pub-3940256099942544/1033173712");//interstitial ca-app-pub-9305948379021213/5010607119
        AdRequest adRequest8 = new AdRequest.Builder().build();//ad request
        mInterstitialAd8.loadAd(adRequest8);

        mInterstitialAd9= new InterstitialAd(this);
        mInterstitialAd9.setAdUnitId("ca-app-pub-3940256099942544/1033173712");//interstitial ca-app-pub-9305948379021213/8232500976
        AdRequest adRequest9 = new AdRequest.Builder().build();//ad request
        mInterstitialAd9.loadAd(adRequest9);

        mInterstitialAd0= new InterstitialAd(this);
        mInterstitialAd0.setAdUnitId("ca-app-pub-3940256099942544/1033173712");//interstitial  ca-app-pub-9305948379021213/7034614157
        AdRequest adRequest0 = new AdRequest.Builder().build();//ad request
        mInterstitialAd0.loadAd(adRequest0);



        mInterstitialAd.setAdListener(new AdListener() {  //interestitial ad listener
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

            public void onAdOpened() {
                // Code to be executed when the ad is displayed.


            }

            @Override
            public void onAdClicked() {
                invalid_click++;
                invalidTV.setText("Invalid Click: "+invalid_click);
                databaseReference.child(uId).child("invalid_click").setValue(String.valueOf(invalid_click));
            }

        });
        //ads end

        mInterstitialAd1.setAdListener(new AdListener() {  //interestitial ad listener
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd1.loadAd(new AdRequest.Builder().build());

            }

            public void onAdOpened() {
                // Code to be executed when the ad is displayed.


            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                invalid_click++;
                invalidTV.setText("Invalid Click: "+invalid_click);
                databaseReference.child(uId).child("invalid_click").setValue(String.valueOf(invalid_click));
            }

        });
        //ads end

        mInterstitialAd2.setAdListener(new AdListener() {  //interestitial ad listener
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd2.loadAd(new AdRequest.Builder().build());
            }

            public void onAdOpened() {
                // Code to be executed when the ad is displayed.


            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                invalid_click++;
                invalidTV.setText("Invalid Click: "+invalid_click);
                databaseReference.child(uId).child("invalid_click").setValue(String.valueOf(invalid_click));
            }

        });
        //ads end

        mInterstitialAd3.setAdListener(new AdListener() {  //interestitial ad listener
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd3.loadAd(new AdRequest.Builder().build());
            }

            public void onAdOpened() {
                // Code to be executed when the ad is displayed.


            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                invalid_click++;
                invalidTV.setText("Invalid Click: "+invalid_click);
                databaseReference.child(uId).child("invalid_click").setValue(String.valueOf(invalid_click));
            }

        });
        //ads end

        mInterstitialAd4.setAdListener(new AdListener() {  //interestitial ad listener
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd4.loadAd(new AdRequest.Builder().build());
            }

            public void onAdOpened() {
                // Code to be executed when the ad is displayed.


            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                invalid_click++;
                invalidTV.setText("Invalid Click: "+invalid_click);
                databaseReference.child(uId).child("invalid_click").setValue(String.valueOf(invalid_click));
            }

        });
        //ads end
        mInterstitialAd5.setAdListener(new AdListener() {  //interestitial ad listener
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd5.loadAd(new AdRequest.Builder().build());
            }

            public void onAdOpened() {
                // Code to be executed when the ad is displayed.


            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                invalid_click++;
                invalidTV.setText("Invalid Click: "+invalid_click);
                databaseReference.child(uId).child("invalid_click").setValue(String.valueOf(invalid_click));
            }

        });
        //ads end
        mInterstitialAd6.setAdListener(new AdListener() {  //interestitial ad listener
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd6.loadAd(new AdRequest.Builder().build());
            }

            public void onAdOpened() {
                // Code to be executed when the ad is displayed.


            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                invalid_click++;
                invalidTV.setText("Invalid Click: "+invalid_click);
                databaseReference.child(uId).child("invalid_click").setValue(String.valueOf(invalid_click));
            }

        });
        //ads end
        mInterstitialAd7.setAdListener(new AdListener() {  //interestitial ad listener
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd7.loadAd(new AdRequest.Builder().build());
            }

            public void onAdOpened() {
                // Code to be executed when the ad is displayed.


            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                invalid_click++;
                invalidTV.setText("Invalid Click: "+invalid_click);
                databaseReference.child(uId).child("invalid_click").setValue(String.valueOf(invalid_click));
            }

        });
        //ads end

        mInterstitialAd8.setAdListener(new AdListener() {  //interestitial ad listener
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd8.loadAd(new AdRequest.Builder().build());

            }

            public void onAdOpened() {
                // Code to be executed when the ad is displayed.


            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                invalid_click++;
                invalidTV.setText("Invalid Click: "+invalid_click);
                databaseReference.child(uId).child("invalid_click").setValue(String.valueOf(invalid_click));
            }

        });
        //ads end
        mInterstitialAd9.setAdListener(new AdListener() {  //interestitial ad listener
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd9.loadAd(new AdRequest.Builder().build());
            }

            public void onAdOpened() {
                // Code to be executed when the ad is displayed.


            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                invalid_click++;
                invalidTV.setText("Invalid Click: "+invalid_click);
                databaseReference.child(uId).child("invalid_click").setValue(String.valueOf(invalid_click));
            }

        });
        //ads end



        mInterstitialAd0.setAdListener(new AdListener() {  //interestitial ad listener
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                if (flg==1)
                {
                    Toast.makeText(QuizActivity.this, "Success !!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(QuizActivity.this, "Wrong task !!", Toast.LENGTH_SHORT).show();
                    databaseReference.child(uId).child("wrong_click").setValue(String.valueOf(wrong_click));
                }

            }

            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
                     buttonDisable();
            }

            @Override
            public void onAdClicked() {
                buttonEnable();
                click++;
                databaseReference.child(uId).child("total_click").setValue(String.valueOf(click));
                startTimerClick();
                updateCountDownTextClick();
            }

        });


        buttonChoice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(buttonChoice1.getText()==mAnswer)
                {

                    tempScore++;
                    //loadRewardVideoAd();
                    updateCorrectAnsNum();
                    updateQuestion();
                    Toast.makeText(QuizActivity.this, "Correct !!", Toast.LENGTH_SHORT).show();

                }
                else{
                   // loadRewardVideoAd();
                    Toast.makeText(QuizActivity.this, "Wrong !!", Toast.LENGTH_LONG).show();
                    updateQuestion();
                }


            }

        });
        buttonChoice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(buttonChoice2.getText()==mAnswer)
                {

                    tempScore++;
                    //loadRewardVideoAd();
                    updateCorrectAnsNum();
                    updateQuestion();
                    Toast.makeText(QuizActivity.this, "Correct !!", Toast.LENGTH_SHORT).show();
                }
                else{
                   // loadRewardVideoAd();
                    Toast.makeText(QuizActivity.this, "Wrong !!", Toast.LENGTH_LONG).show();
                    updateQuestion();
                }
            }
        });
        buttonChoice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(buttonChoice3.getText()==mAnswer)
                {

                    tempScore++;
                   // loadRewardVideoAd();
                    updateCorrectAnsNum();
                    updateQuestion();
                    Toast.makeText(QuizActivity.this, "Correct !!", Toast.LENGTH_SHORT).show();
                }
                else{
                   // loadRewardVideoAd();
                    Toast.makeText(QuizActivity.this, "Wrong !!", Toast.LENGTH_LONG).show();
                    updateQuestion();
                }
            }
        });
        buttonChoice4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(buttonChoice4.getText()==mAnswer)
                {

                    tempScore++;
                   // loadRewardVideoAd();
                    updateCorrectAnsNum();
                    updateQuestion();
                    Toast.makeText(QuizActivity.this, "Correct !!", Toast.LENGTH_SHORT).show();
                }
                else{
                   // loadRewardVideoAd();
                    Toast.makeText(QuizActivity.this, "Wrong !!", Toast.LENGTH_LONG).show();
                    updateQuestion();
                }
            }
        });

        NextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateQuestion();

            }
        });



    }


    private void updateQuestion()
    {

        if(mQuestionNumber<=50)//number should be less than total question
        {
            mQuestionView.setText(mQuestionsLibrary.getQuestion(mQuestionNumber));
            buttonChoice1.setText(mQuestionsLibrary.getChoice1(mQuestionNumber));
            buttonChoice2.setText(mQuestionsLibrary.getChoice2(mQuestionNumber));
            buttonChoice3.setText(mQuestionsLibrary.getChoice3(mQuestionNumber));
            buttonChoice4.setText(mQuestionsLibrary.getChoice4(mQuestionNumber));
            mAnswer=mQuestionsLibrary.getCorrectAnswer(mQuestionNumber);

            if (invalid_click==3)
            {
                buttonDisable();
            }

                  switch (mQuestionNumber)
                  {

                      case 1:

                              if (mInterstitialAd.isLoaded())
                              {
                                  mInterstitialAd.show();
                                  Toast.makeText(this, "Wait 5 Seconds !!", Toast.LENGTH_SHORT).show();
                              }
                          break;
                      case 3:

                          if (mInterstitialAd1.isLoaded())
                          {
                              mInterstitialAd1.show();
                              Toast.makeText(this, "Wait 5 Seconds !!", Toast.LENGTH_SHORT).show();

                          }
                          break;

                      case 5:
                          if (mInterstitialAd2.isLoaded())
                          {
                              mInterstitialAd2.show();
                              Toast.makeText(this, "Wait 5 Seconds !!", Toast.LENGTH_SHORT).show();

                          }
                          break;

                      case 7:
                          if (mInterstitialAd3.isLoaded())
                          {
                              mInterstitialAd3.show();
                              Toast.makeText(this, "Wait 5 Seconds !!", Toast.LENGTH_SHORT).show();

                          }
                          break;
                      case 9:
                          if (mInterstitialAd4.isLoaded())
                          {
                              mInterstitialAd4.show();
                              Toast.makeText(this, "Wait 5 Seconds !!", Toast.LENGTH_SHORT).show();

                          }
                          break;

                      case 11:
                          if (mInterstitialAd5.isLoaded())
                          {
                              mInterstitialAd5.show();
                              Toast.makeText(this, "Wait 5 Seconds !!", Toast.LENGTH_SHORT).show();

                          }
                          break;

                      case 13:
                          if (mInterstitialAd6.isLoaded())
                          {
                              mInterstitialAd6.show();
                              Toast.makeText(this, "Wait 5 Seconds !!", Toast.LENGTH_SHORT).show();

                          }
                          break;

                      case 15:
                          if (mInterstitialAd7.isLoaded())
                          {
                              mInterstitialAd7.show();
                              Toast.makeText(this, "Wait 5 Seconds !!", Toast.LENGTH_SHORT).show();

                          }
                          break;

                      case 17:
                          if (mInterstitialAd8.isLoaded())
                          {
                              mInterstitialAd8.show();
                              Toast.makeText(this, "Wait 5 Seconds !!", Toast.LENGTH_SHORT).show();

                          }
                          break;

                      case 19:
                          if (mInterstitialAd9.isLoaded())
                          {
                              mInterstitialAd9.show();
                              Toast.makeText(this, "Wait 5 Seconds !!", Toast.LENGTH_SHORT).show();

                          }
                          break;

                      case 21:
                          if (mInterstitialAd.isLoaded())
                          {
                              mInterstitialAd.show();
                              Toast.makeText(this, "Wait 5 Seconds !!", Toast.LENGTH_SHORT).show();

                          }
                          break;

                      case 23:
                          if (mInterstitialAd1.isLoaded())
                          {
                              mInterstitialAd1.show();
                              Toast.makeText(this, "Wait 5 Seconds !!", Toast.LENGTH_SHORT).show();

                          }
                          break;

                      case 25:
                          if (mInterstitialAd2.isLoaded())
                          {
                              mInterstitialAd2.show();
                              Toast.makeText(this, "Wait 5 Seconds !!", Toast.LENGTH_SHORT).show();

                          }

                          break;

                      case 27:
                          if (mInterstitialAd3.isLoaded())
                          {
                              mInterstitialAd3.show();
                              Toast.makeText(this, "Wait 5 Seconds !!", Toast.LENGTH_SHORT).show();

                          }
                          break;

                      case 29:
                          if (mInterstitialAd4.isLoaded())
                          {
                              mInterstitialAd4.show();
                              Toast.makeText(this, "Wait 5 Seconds !!", Toast.LENGTH_SHORT).show();

                          }
                          break;

                      case 31:
                          if (mInterstitialAd5.isLoaded())
                          {
                              mInterstitialAd5.show();
                              Toast.makeText(this, "Wait 5 Seconds !!", Toast.LENGTH_SHORT).show();

                          }
                          break;

                      case 33:
                          if (mInterstitialAd6.isLoaded())
                          {
                              mInterstitialAd6.show();
                              Toast.makeText(this, "Wait 5 Seconds !!", Toast.LENGTH_SHORT).show();

                          }
                          break;

                      case 35:
                          if (mInterstitialAd7.isLoaded())
                          {
                              mInterstitialAd7.show();
                              Toast.makeText(this, "Wait 5 Seconds !!", Toast.LENGTH_SHORT).show();

                          }
                          break;
                      case 37:
                          if (mInterstitialAd8.isLoaded())
                          {
                              mInterstitialAd8.show();
                              Toast.makeText(this, "Wait 5 Seconds !!", Toast.LENGTH_SHORT).show();

                          }
                          break;

                      case 39:
                         if (mInterstitialAd9.isLoaded())
                         {
                          mInterstitialAd9.show();
                             Toast.makeText(this, "Wait 5 Seconds !!", Toast.LENGTH_SHORT).show();

                         }
                         break;

                      case 41:
                          if (mInterstitialAd1.isLoaded())
                          {
                              mInterstitialAd1.show();
                              Toast.makeText(this, "Wait 5 Seconds !!", Toast.LENGTH_SHORT).show();

                          }
                          break;
                      case 43:
                          if (mInterstitialAd2.isLoaded())
                          {
                              mInterstitialAd2.show();
                              Toast.makeText(this, "Wait 5 Seconds !!", Toast.LENGTH_SHORT).show();

                          }
                          break;
                      case 45:
                          if (mInterstitialAd3.isLoaded())
                          {
                              mInterstitialAd3.show();
                              Toast.makeText(this, "Wait 5 Seconds !!", Toast.LENGTH_SHORT).show();

                          }
                          break;
                      case 47:
                          if (mInterstitialAd4.isLoaded())
                          {
                              mInterstitialAd4.show();
                              startTimer();
                              updateCountDownText();

                          }
                          break;
                      case 50:

                          if (mInterstitialAd0.isLoaded())
                          {
                              Toast.makeText(this, "Click on the Ad and wait 60 seconds", Toast.LENGTH_LONG).show();
                              mInterstitialAd0.show();
                          }
                          break;

                            default:
                             break;
                       }

            viewTV.setText("View: "+mQuestionNumber);
            mQuestionNumber++;

        }
        else
        {
            if (flg==1)
            {
                Toast.makeText(this, "End all Questions !!!", Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(QuizActivity.this,ResultActivity.class);
                startActivity(intent);
                finish();

            }
            else
            {
                Toast.makeText(this, "Do not perform task accurately..Play again !!", Toast.LENGTH_LONG).show();
                Intent intent =new Intent(QuizActivity.this,MainActivity.class);
                intent.putExtra("value","2");
                startActivity(intent);
                finish();
            }

        }
    }

        private void updateCorrectAnsNum() {
        String tScore=String.valueOf(tempScore);
        mScoreView.setText("Correct Ans: "+tempScore);
        databaseReference.child(uId).child("tempScore").setValue(tScore);

    }



    public void retriveInvalidClick()
    {
        databaseReference.child(uId).child("invalid_click").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    String tScore = dataSnapshot.getValue(String.class);
                    int ts=Integer.parseInt(tScore);
                    invalid_click=ts;
                    invalidTV.setText("Invalid Click: "+invalid_click);

                }
                else
                {
                    invalid_click=0;
                    invalidTV.setText("Invalid Click: "+invalid_click);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(QuizActivity.this, "Failed!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void retriveClick()
    {
        databaseReference.child(uId).child("total_click").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    String tclick = dataSnapshot.getValue(String.class);
                    int tc=Integer.parseInt(tclick);
                    click=tc;
                }
                else
                {
                    click=0;
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(QuizActivity.this, "Failed!!", Toast.LENGTH_SHORT).show();
            }
        });
    }



    public  void  progressDialogOpen()
    {
        progressDialog = new ProgressDialog(QuizActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    }



    public void buttonDisable()
    {
       buttonChoice1.setEnabled(false);
       buttonChoice2.setEnabled(false);
       buttonChoice3.setEnabled(false);
       buttonChoice4.setEnabled(false);
       NextBtn.setEnabled(false);
    }
    public void buttonEnable()
    {
        buttonChoice1.setEnabled(true);
        buttonChoice2.setEnabled(true);
        buttonChoice3.setEnabled(true);
        buttonChoice4.setEnabled(true);
        NextBtn.setEnabled(true);
    }


    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                mTimeLeftInMillis = START_TIME_IN_MILLIS;
            }
        }.start();

        // mTimerRunning = true;
    }

    private void updateCountDownText() {
        // int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d",seconds);
        Toast.makeText(this, "Please wait: "+timeLeftFormatted, Toast.LENGTH_SHORT).show();
        //tv.setText(timeLeftFormatted);
        if(seconds==00)
        {
            mTimeLeftInMillis = START_TIME_IN_MILLIS;
        }
    }




    private void startTimerClick() {  //for click event
        mCountDownTimer2 = new CountDownTimer(mTimeLeftInMillis2, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis2 = millisUntilFinished;
                updateCountDownTextClick();
            }

            @Override
            public void onFinish() {
                mTimerRunning2 = false;

            }
        }.start();
        // mTimerRunning = true;
    }

    private void updateCountDownTextClick() {
        // int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis2 / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d",seconds);
        Toast.makeText(this, "Please wait: "+timeLeftFormatted, Toast.LENGTH_SHORT).show();
        if(seconds==0||seconds==1) {
            flg = 1;
            Toast.makeText(this, "Close Ads !!", Toast.LENGTH_LONG).show();
            mTimeLeftInMillis2 = START_TIME_IN_MILLIS2;
        }
        else
        {
            flg=0;
        }


    }


}

