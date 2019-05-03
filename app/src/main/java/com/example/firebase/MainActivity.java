package com.example.firebase;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText emailSignIn,passwordSignIN;
    Button signInButton;
    TextView textView;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      mAuth = FirebaseAuth.getInstance();
        emailSignIn =(EditText) findViewById(R.id.signInEmailEditText);
        passwordSignIN =(EditText) findViewById(R.id.signInPasswordEditText);
        signInButton = (Button) findViewById(R.id.signInButton);
        textView = (TextView) findViewById(R.id.signUpTextView);
        progressBar = findViewById(R.id.progressBarSignUp);

        signInButton.setOnClickListener(this);
        textView.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {


        switch (view.getId()){
            case R.id.signInButton:{
             //   userSignIn();

                break;
            }
            case R.id.signUpTextView:{
                Intent intent = new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(intent);

                break;
            }
        }

    }

    private void userSignIn() {
        String email = emailSignIn.getText().toString().trim();
        String password = passwordSignIN.getText().toString().trim();

        progressBar.setVisibility(View.VISIBLE);
        if (email.isEmpty()) {
            emailSignIn.setError("Please! Enter your email");
            emailSignIn.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailSignIn.setError("Enter a valid Email ");
            emailSignIn.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            passwordSignIN.setError("Enter your Password");
            passwordSignIN.requestFocus();
            return;
        }
        if (password.length() < 6) {
            passwordSignIN.setError("Enter minimum 6 digit");
            passwordSignIN.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    Intent intent = new Intent(MainActivity.this,NewActivity.class);
                   intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Toast.makeText(MainActivity.this,"Sign in successful",Toast.LENGTH_SHORT).show();
                    startActivity(intent);

                }else {
                    Toast.makeText(MainActivity.this,"Sign in unsuccessful",Toast.LENGTH_SHORT).show();

                }

            }
        });






    }
}
