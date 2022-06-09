package com.example.appmp3online.Presenter;

import android.app.ProgressDialog;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.example.appmp3online.Interface.ILogin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupPresenter {
    ILogin iLogin;

    public SignupPresenter(ILogin iLogin) {
        this.iLogin = iLogin;
    }
    public void onLoginSignup(String email,String user,String password,String confirmPassword,String phone){
        if (TextUtils.isEmpty(email)){
            iLogin.onError("Email can not be empty !");
        }else if(TextUtils.isEmpty(user)){
            iLogin.onError("User can not be empty !");
        }else if(TextUtils.isEmpty(password)){
            iLogin.onError("Password can not be empty !");
        }else if(TextUtils.isEmpty(confirmPassword)){
            iLogin.onError("Confirm Password can not be empty !");
        }else if(TextUtils.isEmpty(phone)){
            iLogin.onError("Phone number can not be empty !");
        }else {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            ProgressDialog progressDialog = new ProgressDialog(iLogin.context());;
            progressDialog.show();
            firebaseAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(iLogin.activity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()){
                                iLogin.onSucessfull("Register user successfully !");

                            }else {
                                iLogin.onError("Register user failed !");
                            }
                        }
                    });
        }

    }
}
