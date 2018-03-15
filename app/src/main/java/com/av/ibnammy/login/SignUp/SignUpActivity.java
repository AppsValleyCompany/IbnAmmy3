package com.av.ibnammy.login.SignUp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.av.ibnammy.R;
import com.av.ibnammy.databinding.ActivitySignUpBinding;
import com.av.ibnammy.login.LoginActivity;

public class SignUpActivity extends AppCompatActivity implements SignUpContract.SignUpView {

    SignUpContract.SignUpPresenter presenter;
    ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= DataBindingUtil.setContentView(this,R.layout.activity_sign_up);
        presenter= new SignUpPresenterImp(this);

        binding.signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone=binding.phoneEt.getText().toString();
                String pass=binding.passwordEt.getText().toString();
                String repeat=binding.repeatPasswordEt.getText().toString();
                presenter.onSignUpClicked(phone,pass,repeat,1);
            }
        });

       binding.btnHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void showProgress() {
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        binding.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String error) {
        Toast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void moveToLoginScreen() {
        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
    }

    @Override
    public void setUsernameError(String e) {
        binding.inputLayoutName.setError(e);
    }

    @Override
    public void setPasswordError(String e) {
        binding.inputLayoutPassword.setError(e);
    }

    @Override
    public void setRepeatPasswordError(String e) {
        binding.inputLayoutRepeatPassword.setError(e);
    }
}
