package com.example.kitchsmart;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity1 extends AppCompatActivity {

    EditText etUserid,etPasswod;
    TextView tvAttempts;
    Button btnLogin;

    final String UserId = "kitchsmart" ,Password = "123";
    boolean isValid = false;
    int counter =5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);

        etUserid = findViewById(R.id.etUserid);
        etPasswod = findViewById(R.id.etPassword);
        tvAttempts = findViewById(R.id.tvAttempts);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                String inputUserid = etUserid.getText().toString();
                String inputPassword = etPasswod.getText().toString();

                if(inputUserid.isEmpty() || inputPassword.isEmpty()){
                    Toast.makeText(LoginActivity1.this, "Enter all the details!", Toast.LENGTH_SHORT).show();
                }

                else {
                    isValid = validate(inputUserid,inputPassword);

                    if(!isValid){
                        counter--;

                        Toast.makeText(LoginActivity1.this, "Incorret UserId or password!", Toast.LENGTH_SHORT).show();

                        tvAttempts.setText("No. of attempts remaining: " + counter);

                        if(counter == 0){
                            btnLogin.setEnabled(false);
                        }

                    }

                    else {
                        Toast.makeText(LoginActivity1.this, "Login Successful!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(LoginActivity1.this,MainActivity.class);
                        startActivity(intent);
                    }

                }

            }
        });

    }

    private boolean validate(String userId, String password){
        if(userId.equals(UserId) && password.equals(Password)){
            return true;
        }
        return false;
    }

}