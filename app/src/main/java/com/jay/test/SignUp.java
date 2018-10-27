package com.jay.test;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;


public class SignUp extends AppCompatActivity implements View.OnClickListener{

    EditText email_text, password_text;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        findViewById(R.id.signin).setOnClickListener(this);

        email_text = (EditText) findViewById(R.id.email);
        password_text = (EditText) findViewById(R.id.password);
        progressBar =(ProgressBar) findViewById(R.id.progress);
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.register).setOnClickListener(this);
    }



    private void register()
    {
        String email = email_text.getText().toString().trim();
        String password = password_text.getText().toString().trim();

        if (email.isEmpty()){
            email_text.setError("Email Needed");
            email_text.requestFocus();
            return;
        }


        if (email.isEmpty()){
            password_text.setError("Password Needed");
            password_text.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.VISIBLE);
                if (task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(), "User Registration Successful", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SignUp.this, ProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
                else if(task.getException() instanceof FirebaseAuthUserCollisionException)
                    Toast.makeText(getApplicationContext(),"User Already Exists",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"Some Error Occured",Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:
                register();
                break;

            case R.id.signin:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}
