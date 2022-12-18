package com.example.raqielquiz;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import com.example.raqielquiz.databinding.ActivityCreateAccountBinding;

public class CreateAccountActivity extends AppCompatActivity {

    ActivityCreateAccountBinding mBinding;


    @Override
     protected void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.createAccountButton.setOnClickListener(v-> createAccount());
        mBinding.loginCadastrado.setOnClickListener(v-> finish());


    }

    void createAccount() {
        String email= mBinding.emailEditText.getText().toString();
        String password= mBinding.passwordEditText.getText().toString();
        String confirmPassword= mBinding.confirmPasswordEditText.getText().toString();

        boolean isValidated = validateData(email, password, confirmPassword);

    }

    boolean validateData(String email, String password, String confirmPassword){

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mBinding.emailEditText.setError("Email invalido");
            return false;
        }

        if (password.length()<6){
            mBinding.passwordEditText.setError("A senha deve conter mais de 6 caracteres");
            return false;
        }
        if (!password.equals(confirmPassword)){
            mBinding.confirmPasswordEditText.setError("As senhas não coincidem");
            return false;
        }
        return true;
    }
}