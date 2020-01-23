package com.example.dictionary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private Button registerButton;
    private EditText emailText,passwordText;
    private TextView loginText;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        registerButton = (Button) findViewById(R.id.register);
        emailText = (EditText) findViewById(R.id.email);
        passwordText = (EditText) findViewById(R.id.password);
        loginText = (TextView) findViewById(R.id.loginText);
        auth = FirebaseAuth.getInstance();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               registerUser();
            }
        });

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        });

    }

    public void registerUser()
    {
        String email = emailText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();


        if((email==null) || (password==null))
        {
            Toast.makeText(getApplicationContext(),"Enter Credentials",Toast.LENGTH_SHORT).show();
        }
        else{

        // when testing password = "asdfghjkl" as it requires complex one

            auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Register Success!!!",Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(getApplicationContext(),Dashboard.class));
                                finish();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Register Failed, please try again!!!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }

    }
}
