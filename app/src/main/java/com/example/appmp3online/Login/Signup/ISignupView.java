package com.example.appmp3online.Login.Signup;

import android.content.Context;

public interface ISignupView {
    void onClickListener();
    void onSucessfull(String mess);
    void onError(String mess);
    Context onContext();
}
