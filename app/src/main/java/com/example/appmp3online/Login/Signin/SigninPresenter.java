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
    private Activity getActivity;

    public SigninPresenter(Activity getActivity) {
        this.getActivity = getActivity;
    }

    public void onLogin(String email, String passWord){
        if (TextUtils.isEmpty(email)){
            onError("Email can not be empty !");
        }else if(TextUtils.isEmpty(passWord)){
            onError("Password can not be empty !");
        }else {
            FirebaseAuth Auth = FirebaseAuth.getInstance();
            ProgressDialog progressDialog = new ProgressDialog(getActivity);;
            progressDialog.show();
            Auth.signInWithEmailAndPassword(email,passWord)
                    .addOnCompleteListener(getActivity, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()){
                                onSucessfull("Login successfully");
                                Intent intent =new Intent(getActivity, MainActivity.class);
                                getActivity.startActivity(intent);
                                getActivity.finishAffinity();
                            }else {
                                onError("Email or password not correct");
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
