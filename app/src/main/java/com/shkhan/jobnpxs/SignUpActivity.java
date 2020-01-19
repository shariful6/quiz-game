package com.shkhan.jobnpxs;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class SignUpActivity extends AppCompatActivity {
    TextView gotoLogin;
    EditText nameET,emailET,passwordET,referET;
    Button signUpBtn;

    ProgressBar progressBar;

    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;

    String uId;
    String email;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        this.setTitle("SignUp");

        databaseReference= FirebaseDatabase.getInstance().getReference("user");

        gotoLogin=findViewById(R.id.gotoLoginTVID);
        nameET=findViewById(R.id.nameETID);
        emailET=findViewById(R.id.emailETID);
        passwordET=findViewById(R.id.passwordETID);
        referET=findViewById(R.id.referETID);
        signUpBtn=findViewById(R.id.signUpBtnID);


        mAuth = FirebaseAuth.getInstance();

        progressBar=findViewById(R.id.progressbarID);



        gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this,SignInActivity.class);
                startActivity(intent);
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

    }


    private void register() {
        email=emailET.getText().toString().trim();
        String password=passwordET.getText().toString().trim();

        if(email.isEmpty())
        {
            emailET.setError("Enter an email address");
            emailET.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            emailET.setError("Enter a valid email address");
            emailET.requestFocus();
            return;
        }

        //checking the validity of the password
        if(password.isEmpty())
        {
            passwordET.setError("Enter a password");
            passwordET.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {

                            User user = new User(SignUpActivity.this);
                            user.setEmail(email);

                            saveUserInfo();

                            finish();
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(SignUpActivity.this, "SignUp Successful!!", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(SignUpActivity.this,MainActivity.class);
                            intent.putExtra("value","2");
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            if(task.getException() instanceof FirebaseAuthUserCollisionException)
                            {
                                Toast.makeText(getApplicationContext(), "User is already registered !!", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Error : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            //Toast.makeText(SignUpActivity.this, "Failed To SignUp !!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void saveUserInfo(){
        final Random myRandom = new Random();

        String userName=nameET.getText().toString();
        String referCode=referET.getText().toString();

        String myRefCode=String.valueOf(myRandom.nextInt(900000000));

        user = mAuth.getCurrentUser();
        uId = user.getUid();
        databaseReference.child(uId).child("name").setValue(userName);
        databaseReference.child(uId).child("referCode").setValue(referCode);
        databaseReference.child(uId).child("myRefCode").setValue(myRefCode);
        databaseReference.child(uId).child("balance").setValue("500");

    }




}
