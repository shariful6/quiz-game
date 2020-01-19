package com.shkhan.jobnpxs;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //for breaktime
    private static final long START_TIME_IN_MILLIS = 7200000; //600000   10 min  //7200000  120 min

    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis;
    private long mEndTime;


    TextView scorTV,userNameTV;
    TextView refereTV,breakTimeTV;
    private Button copyBtn,startTimeBtn,resetTimeBtn;

    CardView playCV,withdrawCV,rulseCV,supportCV;

    private AlertDialog.Builder builder_notice;


    private FirebaseAuth mAuth;
    DatabaseReference databaseReference,database;
    String uId;
    private FirebaseUser user;

    String noticeText;
    int rV;
    String value;

    ClipboardManager clipboardManager;



    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        value=getIntent().getStringExtra("value");

        databaseReference= FirebaseDatabase.getInstance().getReference("user");
        database= FirebaseDatabase.getInstance().getReference("notice");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        scorTV=findViewById(R.id.userBalanceID);
        userNameTV=findViewById(R.id.userNameID);

        playCV=findViewById(R.id.playCVID);
        withdrawCV=findViewById(R.id.cashOutCVID);


        copyBtn=findViewById(R.id.copyBtnID);
        refereTV=findViewById(R.id.refereCodeTVID);
        breakTimeTV=findViewById(R.id.breaktimeTVID);

        startTimeBtn=findViewById(R.id.st_ps_Btn);








        clipboardManager =(ClipboardManager)getSystemService(CLIPBOARD_SERVICE);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        uId = user.getUid();



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

            startBreak();
            progressDialogOpen();
            retriveName();
            retriveMyRefCode();
            retriveScore();
            retriveNotice();

        copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String txt=refereTV.getText().toString();
                if(!txt.equals(""))
                {
                    ClipData clipData = ClipData.newPlainText("txt",txt);
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(MainActivity.this, "Copied !", Toast.LENGTH_SHORT).show();

                }



            }
        });
        playCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,QuizActivity.class);
                startActivity(intent);
            }
        });

        withdrawCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String bal=scorTV.getText().toString();
                int npxs=Integer.parseInt(bal);

                Intent intent = new Intent(MainActivity.this,WithdrawActivity.class);
                intent.putExtra("npxs",bal);
                startActivity(intent);

            }
        });


        startTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBalance();
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_notification) {

            openDialog_notice();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.messageID) {
            sendMail();
            return true;
        }

        else if (id==R.id.rulesID)
        {
            Intent intent = new Intent(MainActivity.this,RulseActivity.class);
            startActivity(intent);
        }

        else if (id==R.id.supportID)
        {
            Intent intent = new Intent(MainActivity.this,SupportActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.shareID) {
            Intent myIntent=new Intent(Intent.ACTION_SEND);
            myIntent.setType("text/plain");
            String body="http://play.google.com/store/apps/details?id=com.shkhan.jobnpxs";
            myIntent.putExtra(Intent.EXTRA_TEXT,body);
            startActivity(Intent.createChooser(myIntent,"Share Using"));
            return true;

        } else if (id == R.id.rateID) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=" + getPackageName())));
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));

            }

            return true;
        }
        else if (id == R.id.policyID) {

           Intent intent = new Intent(MainActivity.this,PrivacyActivity.class);
           startActivity(intent);
        }


         else if (id == R.id.refereID)
           {
               Intent intent =new Intent(MainActivity.this,RefereActivity.class);
               startActivity(intent);

           }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void sendMail() {
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT,"Atom App Feedback");
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent,"Choose an email client"));
    }

    public void retriveScore()
    {
        databaseReference.child(uId).child("balance").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    String rScore = dataSnapshot.getValue(String.class);
                    scorTV.setText(rScore);
                    progressDialog.dismiss();
                   Toast.makeText(MainActivity.this, "Welcome Back !!", Toast.LENGTH_SHORT).show();
                }
                else{

                    scorTV.setText("0");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(MainActivity.this, "Failed!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void openDialog_notice()
    {
        builder_notice =new AlertDialog.Builder(MainActivity.this);

        builder_notice.setTitle("Notice");
        builder_notice.setMessage(noticeText);
        builder_notice.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog=builder_notice.create();
        alertDialog.show();

    }
    public  void retriveNotice()
    {

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                noticeText = dataSnapshot.getValue(String.class);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }

    public void retriveName()
    {
        databaseReference.child(uId).child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    String uName = dataSnapshot.getValue(String.class);
                    userNameTV.setText("Name: "+uName);
                }
                else{
                    scorTV.setText("Name");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(MainActivity.this, "Failed!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void retriveMyRefCode()
    {
        databaseReference.child(uId).child("myRefCode").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    String myRefcode = dataSnapshot.getValue(String.class);
                    refereTV.setText(myRefcode);
                }
                else{
                    scorTV.setText("Ref Code Null");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(MainActivity.this, "Failed!!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public  void startBreak()
    {
        int i2=Integer.parseInt(value);
        if (i2==1)
        {
            databaseReference.child(uId).child("view").setValue("0");
            databaseReference.child(uId).child("tempScore").setValue("0");

        }
        if (i2==2)
        {
            startTimeBtn.setEnabled(false);

        }
    }

    public  void addBalance()
    {
        String balance=scorTV.getText().toString();
        int b=Integer.parseInt(balance);
        int tb=b+150;
        String sb=String.valueOf(tb);
        databaseReference.child(uId).child("balance").setValue(sb);
        Toast.makeText(this, "Credit added Successfully", Toast.LENGTH_SHORT).show();
    }


    public  void  progressDialogOpen()
    {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    }

    //For detecting vpn
    public boolean vpn() {
        String iface = "";
        try {
            for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (networkInterface.isUp())
                    iface = networkInterface.getName();
                Log.d("DEBUG", "IFACE NAME: " + iface);
                if ( iface.contains("tun") || iface.contains("ppp") || iface.contains("pptp")) {
                    return true;
                }
            }
        } catch (SocketException e1) {
            e1.printStackTrace();
        }

        return false;
    }
    //

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("VPN Detected !!!");
        builder.setMessage("Close VPN and Try Again. Press ok to Exit");
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        return builder;
    }


    //For working break time  ////////////////////////

    private void startTimer() {

       // Toast.makeText(this, "Timer call", Toast.LENGTH_SHORT).show();
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                updateButtons();
            }
        }.start();

        mTimerRunning = true;
        updateButtons();
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        updateButtons();
    }

    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        updateButtons();
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        startTimeBtn.setText("Wait: "+timeLeftFormatted);
        if (minutes==0)
        {
            resetTimer();
            startTimeBtn.setText("Add Points");
        }

    }

    private void updateButtons() {
        if (mTimerRunning) {
           playCV.setEnabled(false);
           startTimeBtn.setEnabled(false);

        } else {
              playCV.setEnabled(true);
              startTimeBtn.setText("Add Points");

            if (mTimeLeftInMillis < 1000) {
               // mButtonStartPause.setVisibility(View.INVISIBLE);
            } else {
               // mButtonStartPause.setVisibility(View.VISIBLE);
            }

            if (mTimeLeftInMillis < START_TIME_IN_MILLIS) {
               // mButtonReset.setVisibility(View.VISIBLE);
            } else {
              //  mButtonReset.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("millisLeft", mTimeLeftInMillis);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime", mEndTime);

        editor.apply();

        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(vpn())
        {
            buildDialog(MainActivity.this).show();
        }

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        mTimeLeftInMillis = prefs.getLong("millisLeft", START_TIME_IN_MILLIS);
        mTimerRunning = prefs.getBoolean("timerRunning", false);

        updateCountDownText();
        updateButtons();

        if (mTimerRunning) {
            mEndTime = prefs.getLong("endTime", 0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();

            if (mTimeLeftInMillis < 0) {
                mTimeLeftInMillis = 0;
                mTimerRunning = false;
                updateCountDownText();
                updateButtons();
            } else {
                startTimer();
            }
        }
    }

}