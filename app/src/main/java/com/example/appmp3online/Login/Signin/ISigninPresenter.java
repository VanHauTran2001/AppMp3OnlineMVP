package com.example.appmp3online.Login.Signin;

import android.app.Activity;
import android.content.Context;

public interface ISigninPresenter {
    void onSucessfull(String mess);
    void onError(String mess);
    Context context();
    Activity activity();
}
