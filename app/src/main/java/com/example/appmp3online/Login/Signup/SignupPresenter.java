package com.example.appmp3online.Login.Signup;

import android.app.Activity;
import android.app.ProgressDialog;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupPresenter implements ISignupPresenter{
    private ISignupView view;

    public SignupPresenter(ISignupView view) {
        this.view = view;
    }

    public void onLoginSignup(String email, String user, String password, String confirmPassword, String phone){
        if (TextUtils.isEmpty(email)){
            view.onError("Email can not be empty !");
        }else if(TextUtils.isEmpty(user)){
            view.onError("User can not be empty !");
        }else if(TextUtils.isEmpty(password)){
            view.onError("Password can not be empty !");
        }else if(TextUtils.isEmpty(confirmPassword)){
            view.onError("Confirm Password can not be empty !");
        }else if(TextUtils.isEmpty(phone)){
            view.onError("Phone number can not be empty !");
        }else {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            ProgressDialog progressDialog = new ProgressDialog(view.onContext());;
            progressDialog.show();
            firebaseAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener((Activity) view.onContext(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()){
                                view.onSucessfull("Register user successfully !");

                            }else {
                                view.onError("Register user failed !");
                            }
                        }
                    });
        }

    }

    @Override
    public void onUnit() {
        view.onClickListener();
    }

}
