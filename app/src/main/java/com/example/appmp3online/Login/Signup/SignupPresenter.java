package com.example.appmp3online.Login.Signup;

import android.app.ProgressDialog;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupPresenter {
    ISignupPresenter iSignup;

    public SignupPresenter(ISignupPresenter iSignup) {
        this.iSignup = iSignup;
    }

    public void onLoginSignup(String email, String user, String password, String confirmPassword, String phone){
        if (TextUtils.isEmpty(email)){
            iSignup.onError("Email can not be empty !");
        }else if(TextUtils.isEmpty(user)){
            iSignup.onError("User can not be empty !");
        }else if(TextUtils.isEmpty(password)){
            iSignup.onError("Password can not be empty !");
        }else if(TextUtils.isEmpty(confirmPassword)){
            iSignup.onError("Confirm Password can not be empty !");
        }else if(TextUtils.isEmpty(phone)){
            iSignup.onError("Phone number can not be empty !");
        }else {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            ProgressDialog progressDialog = new ProgressDialog(iSignup.context());;
            progressDialog.show();
            firebaseAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(iSignup.activity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()){
                                iSignup.onSucessfull("Register user successfully !");

                            }else {
                                iSignup.onError("Register user failed !");
                            }
                        }
                    });
        }

    }
}
