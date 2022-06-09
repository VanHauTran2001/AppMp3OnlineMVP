package com.example.appmp3online.Presenter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.example.appmp3online.Acitivity.MainActivity;
import com.example.appmp3online.Interface.ILogin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.Executor;

public class LoginPresenter {
    ILogin iLogin;

    public LoginPresenter(ILogin iLogin) {
        this.iLogin = iLogin;
    }

    public void onLogin(String email, String passWord){
        if (TextUtils.isEmpty(email)){
            iLogin.onError("Email can not be empty !");
        }else if(TextUtils.isEmpty(passWord)){
            iLogin.onError("Password can not be empty !");
        }else {
            FirebaseAuth Auth = FirebaseAuth.getInstance();
            ProgressDialog progressDialog = new ProgressDialog(iLogin.context());;
            progressDialog.show();
            Auth.signInWithEmailAndPassword(email,passWord)
                    .addOnCompleteListener(iLogin.activity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()){
                                iLogin.onSucessfull("Login successfully");
                                Intent intent =new Intent(iLogin.activity(), MainActivity.class);
                                iLogin.activity().startActivity(intent);
                                iLogin.activity().finishAffinity();
                            }else {
                                iLogin.onError("Email or password not correct");
                            }
                        }
                    });
        }
    }

}
