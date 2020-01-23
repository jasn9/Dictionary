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

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private EditText emailText,passwordText;
    private TextView registerText;
    private FirebaseAuth auth;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        getSupportActionBar().hide();
        i = new Intent(getApplicationContext(),Dashboard.class);

        loginButton = (Button) findViewById(R.id.login);
        emailText = (EditText) findViewById(R.id.email);
        passwordText = (EditText) findViewById(R.id.password);
        registerText = (TextView) findViewById(R.id.registerText);
        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() != null)
        {
            //Toast.makeText(getApplicationContext(),auth.getCurrentUser().toString(),Toast.LENGTH_LONG).show();
            startActivity(i);
            finish();
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));

            }
        });

    }

    public void loginUser()
    {
        String email = emailText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();


        if((email==null) || (password==null))
        {
            Toast.makeText(getApplicationContext(),"Enter Credentials",Toast.LENGTH_SHORT).show();
        }
        else{
            auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                finish();
                                startActivity(i);

                            }
                            else{

                                Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

}
