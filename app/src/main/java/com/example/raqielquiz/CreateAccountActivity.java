package com.example.raqielquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.raqielquiz.databinding.ActivityCreateAccountBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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
        if(!isValidated){
            return;
        }

        createAccountInFirebase(email, password);

    }

     void createAccountInFirebase(String email, String password) {
        changeInProgress(true);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(CreateAccountActivity.this,
        new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(@NonNull Task<AuthResult> task){
                if (task.isSuccessful()){
                    Toast.makeText(CreateAccountActivity.this, "Conta criada com sucesso, confirme no seu email", Toast.LENGTH_SHORT).show();
                    firebaseAuth.getCurrentUser().sendEmailVerification();
                    firebaseAuth.signOut();
                    finish();
                }else {

                    Toast.makeText(CreateAccountActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        }
        );
    }

    void changeInProgress(boolean inProgress){
       if(inProgress){
           mBinding.progressBar.setVisibility(View.VISIBLE);
           mBinding.createAccountButton.setVisibility(View.GONE);
       }else{
           mBinding.progressBar.setVisibility(View.GONE);
           mBinding.createAccountButton.setVisibility(View.VISIBLE);
       }
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