package com.example.appmp3online.Login.Signin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.example.appmp3online.Acitivity.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SigninPresenter {
    ISigninPresenter iSignin;

    public SigninPresenter(ISigninPresenter iSignin) {
        this.iSignin = iSignin;
    }

    public void onLogin(String email, String passWord){
        if (TextUtils.isEmpty(email)){
            iSignin.onError("Email can not be empty !");
        }else if(TextUtils.isEmpty(passWord)){
            iSignin.onError("Password can not be empty !");
        }else {
            FirebaseAuth Auth = FirebaseAuth.getInstance();
            ProgressDialog progressDialog = new ProgressDialog(iSignin.context());;
            progressDialog.show();
            Auth.signInWithEmailAndPassword(email,passWord)
                    .addOnCompleteListener(iSignin.activity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()){
                                iSignin.onSucessfull("Login successfully");
                                Intent intent =new Intent(iSignin.activity(), MainActivity.class);
                                iSignin.activity().startActivity(intent);
                                iSignin.activity().finishAffinity();
                            }else {
                                iSignin.onError("Email or password not correct");
                            }
                        }
                    });
        }
    }

}
