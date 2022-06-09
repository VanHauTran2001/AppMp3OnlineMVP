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

    private Activity getActivity;
    public SignupPresenter(Activity getActivity) {
        this.getActivity = getActivity;
    }

    public void onLoginSignup(String email, String user, String password, String confirmPassword, String phone){
        if (TextUtils.isEmpty(email)){
            onError("Email can not be empty !");
        }else if(TextUtils.isEmpty(user)){
            onError("User can not be empty !");
        }else if(TextUtils.isEmpty(password)){
            onError("Password can not be empty !");
        }else if(TextUtils.isEmpty(confirmPassword)){
            onError("Confirm Password can not be empty !");
        }else if(TextUtils.isEmpty(phone)){
            onError("Phone number can not be empty !");
        }else {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            ProgressDialog progressDialog = new ProgressDialog(getActivity);;
            progressDialog.show();
            firebaseAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(getActivity, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()){
                                onSucessfull("Register user successfully !");

                            }else {
                                onError("Register user failed !");
                            }
                        }
                    });
        }

    }

    @Override
    public void onSucessfull(String mess) {
        Toast.makeText(getActivity, mess, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(String mess) {
        Toast.makeText(getActivity, mess, Toast.LENGTH_SHORT).show();
    }
}
