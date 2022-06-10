package com.example.appmp3online.Login.Signin;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.example.appmp3online.Acitivity.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SigninPresenter implements ISigninPresenter{
    private ISigninView view;

    public SigninPresenter( ISigninView view) {
        this.view = view;
    }

    @Override
    public void onInit() {
        view.onClickListener();
    }

    @Override
    public void onLogin(String email, String password) {
        if (TextUtils.isEmpty(email)){
            view.onError("Email can not be empty !");
        }else if(TextUtils.isEmpty(password)){
            view.onError("Password can not be empty !");
        }else {
            FirebaseAuth Auth = FirebaseAuth.getInstance();
            ProgressDialog progressDialog = new ProgressDialog(view.onContext());;
            progressDialog.show();
            Auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener((Activity) view.onContext(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()){
                                view.onSucessfull("Login successfully !");

                            }else {
                                view.onError("Login failed !");
                            }
                        }
                    });
        }
    }


}
