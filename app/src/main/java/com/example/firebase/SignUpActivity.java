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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    EditText emailSignUp, passwordSignUp;
    Button signUpButton;
    TextView signIntextView;
    ProgressBar progressBar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        mAuth = FirebaseAuth.getInstance();


        emailSignUp = (EditText) findViewById(R.id.signUpEmailEditText);
        passwordSignUp = (EditText) findViewById(R.id.signUpPasswordEditText);
        signUpButton = (Button) findViewById(R.id.signUpButton);
        signIntextView = (TextView) findViewById(R.id.signInTextView);
        progressBar = (ProgressBar) findViewById(R.id.progressBarSignUp);


        signUpButton.setOnClickListener(this);
        signIntextView.setOnClickListener(this);
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.signUpButton :
                userRegestered();
                break;

            case R.id.signInTextView:
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }

    }

    public void userRegestered() {
        String email = emailSignUp.getText().toString().trim();
        String password = passwordSignUp.getText().toString().trim();
        progressBar.setVisibility(View.VISIBLE);
        if (email.isEmpty()) {
            emailSignUp.setError("Please! Enter your email");
            emailSignUp.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailSignUp.setError("Enter a valid Email ");
            emailSignUp.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            passwordSignUp.setError("Enter your Password");
            passwordSignUp.requestFocus();
            return;
        }
        if (password.length() < 6) {
            passwordSignUp.setError("Enter minimum 6 digit");
            passwordSignUp.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(getApplicationContext(), "Sign Up success.", Toast.LENGTH_LONG).show();

                } else {
                    // If sign in fails, display a message to the user.
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "This mail already Regestered", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Sign Up failed.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


    }

}
