package com.dataflair.foundandlost.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dataflair.foundandlost.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailRegisterPatnaCollege extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText email, password;
    private Button signin;
    private TextView register;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_register_patna_college);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signin = findViewById(R.id.signin_button);
        register = findViewById(R.id.register);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

    }

    private void login() {
        String emailId = email.getText().toString().trim();
        String passwordId = password.getText().toString().trim();
//        if(emailId != "*_*@iitp.ac.in")
//        {
//            Toast.makeText(activity_register.this, "Write the IIT Patna Webmail email", Toast.LENGTH_LONG);
//            email.setText("");
//            return;
//        }
        mAuth.createUserWithEmailAndPassword(emailId,passwordId).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    sendVerificationEmail();
//                    Toast.makeText(activity_register.this, "User registered successfully", Toast.LENGTH_SHORT).show();

//                    startActivity(new Intent(activity_register.this, activity_email_signin.class));
                }else{
                    Toast.makeText(EmailRegisterPatnaCollege.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void sendVerificationEmail()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Toast.makeText(EmailRegisterPatnaCollege.this, "verification mail", Toast.LENGTH_SHORT);
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // email sent


                            // after email is sent just logout the user and finish this activity
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(EmailRegisterPatnaCollege.this, EmailSignIn.class));
                            finish();
                        }
                        else
                        {
                            // email not sent, so display message and restart the activity or do whatever you wish to do

                            //restart this activity
                            overridePendingTransition(0, 0);
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());

                        }
                    }
                });
    }
}