package com.alehp.ull_navigation.Activities;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alehp.ull_navigation.R;


public class LoginActivityULL extends AppCompatActivity implements View.OnClickListener {

    Button registerButton;
    Button loginButton;
    EditText emailText;
    EditText passText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ull_login);

        bindUI();


    }

    private boolean isValidEmail() {
        String email = emailText.getText().toString();

        if (TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return true;

        return false;
    }

    private boolean isValidPass() {
        String pass = passText.getText().toString();

        if (pass.length() > 5)
            return true;

        return false;
    }


    private void bindUI() {
        //settings
        registerButton = findViewById(R.id.registerButton);
        loginButton = findViewById(R.id.loginButton);
        emailText = findViewById(R.id.emailText);
        passText = findViewById(R.id.emailText);

        registerButton.setPaintFlags(registerButton.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        registerButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == loginButton.getId()) {

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("firstStart", true);
            startActivity(intent);
            finish();
        }
        else if (v.getId() == registerButton.getId())
            Toast.makeText(this, "register", Toast.LENGTH_LONG).show();


    }
}
