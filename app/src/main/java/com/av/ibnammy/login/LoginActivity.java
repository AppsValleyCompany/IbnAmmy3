package com.av.ibnammy.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.av.ibnammy.R;
import com.av.ibnammy.activities.MainActivityAya;
import com.av.ibnammy.databinding.ActivityLoginBinding;
import com.av.ibnammy.login.SignUp.SignUpActivity;
import com.av.ibnammy.updateUserData.UpdateDataActivity;
import com.av.ibnammy.utils.CommonUtils;
import com.av.ibnammy.utils.Constants;
import com.google.firebase.iid.FirebaseInstanceId;



public class LoginActivity extends AppCompatActivity implements LoginContract.LoginView {

    private LoginPresenterImpl presenter;
    ActivityLoginBinding binding;
    AlertDialog dialog;
  //  SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_login);
        presenter=new LoginPresenterImpl(this);
        binding.goSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.goToSignupClicked();
            }
        });
        binding.signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone=binding.phoneEt.getText().toString();
                String pass=  binding.passwordEt.getText().toString();
            //    presenter.isSavePassChecked=binding.saveCredsCb.isChecked();
                presenter.onLoginClicked(phone,pass,getDeviceToken());
            }
        });
        binding.tvForgetPasswordLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String phone= binding.phoneEt.getText().toString();
                if(phone.isEmpty()||phone.equals("")){
                    showError("رجاء ادخال رقم الهاتف لحسابك اولا.");
                }
                else
                  showForgetPopup(phone);
            }
        });
        presenter.onAttach(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
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
    public void showError(String error) {
        Toast.makeText(this,error,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void moveToHomeScreen(Bundle bundle) {
        if(bundle!=null&&!bundle.getString("fName").equals("")){
            startActivity(new Intent(LoginActivity.this, MainActivityAya.class).putExtra("Login_Data",bundle));
        }
        else
            startActivity(new Intent(LoginActivity.this, UpdateDataActivity.class));
    }

    @Override
    public void moveToSignupScreen() {
        startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
    }

    @Override
    public void setUsernameError(String e) {
        binding.inputLayoutPhone.setError(e);
    }

    @Override
    public void setPasswordError(String e) {
        binding.inputLayoutPassword.setError(e);
    }

    @Override
    public void saveCredentials(Bundle b){
        SharedPreferences pref = getSharedPreferences(Constants.PREF_NAME,  Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
          //if(binding.saveCredsCb.isChecked()){
            boolean save= binding.saveCredsCb.isChecked();
            String phone = binding.phoneEt.getText().toString();
            String password=binding.passwordEt.getText().toString();
            editor.putString(Constants.PHONE_KEY,phone);
            editor.putString(Constants.PASSWORD_KEY,password);
            /********/
            String id=b.getString("id");
            String img=b.getString("img");
            String lat=b.getString("lat");
            String lng=b.getString("lng");

            editor.putString(Constants.USER_ID,id);
            editor.putString(Constants.USER_IMG,img);
            editor.putString(Constants.USER_LAT,lat);
            editor.putString(Constants.USER_LNG,lng);
            editor.putBoolean(Constants.SAVE_CREDS_BOOL,save);
            editor.apply();
      //   }
      //   else {
         //   pref.edit().clear().apply();
       //  }
    }

    @Override
    public void loadCredentials() {
       SharedPreferences pref = getSharedPreferences(Constants.PREF_NAME,  Context.MODE_PRIVATE);
        if(pref!=null){
            boolean save=pref.getBoolean(Constants.SAVE_CREDS_BOOL,false);
            if(save) {
                String phone = pref.getString(Constants.PHONE_KEY, "null");
                String passsword = pref.getString(Constants.PASSWORD_KEY, "null");
                if (!phone.equals("null")){
                    binding.phoneEt.setText(phone);}
                if (!passsword.equals("null")){
                    binding.passwordEt.setText(passsword);}
                    binding.saveCredsCb.setChecked(save);
            }
        }
    }
    @Override
    public void showForgetPopup(final String phone){
        View view = getLayoutInflater().inflate(R.layout.forget_password_popup, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
       // LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        //builder.setView(inflater.inflate(R.layout.forget_password_popup, null));

        dialog=builder.setView(view).create();
        final ProgressBar progress_bar_popup=view.findViewById(R.id.progress_bar_popup);
        final EditText emailET=view.findViewById(R.id.et_email_forget_password);
        Button continu=view.findViewById(R.id.btn_continue_forget_popup);
        continu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email= emailET.getText().toString();
               if(CommonUtils.isEmailValid(email)){
                   progress_bar_popup.setVisibility(View.VISIBLE);
                   presenter.requestForgetPasswordFromModel(phone,email);
               }
               else
                    showError("الايميل غير صحيح.");
            }
        });
        Button cancel=view.findViewById(R.id.btn_cancel_forget_popup);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void enableSignInButton() {
        binding.signinBtn.setEnabled(true);
    }

    @Override
    public void disableSignInButton() {
        binding.signinBtn.setEnabled(false);
    }

    @Override
    public String getDeviceToken() {
        return FirebaseInstanceId.getInstance().getToken();
    }

    @Override
    public void dismissForgetPopup(){
        dialog.dismiss();
    }



}
