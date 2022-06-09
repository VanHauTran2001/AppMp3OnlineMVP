package com.example.appmp3online.Login.Signup;

import android.app.Activity;
import android.content.Context;

public interface ISignupPresenter {
    void onSucessfull(String mess);
    void onError(String mess);
    Context context();
    Activity activity();
}
