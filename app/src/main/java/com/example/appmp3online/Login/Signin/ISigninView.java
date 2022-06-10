package com.example.appmp3online.Login.Signin;

import android.content.Context;

public interface ISigninView {
    void onClickListener();
    void onSucessfull(String mess);
    void onError(String mess);
    Context onContext();
}
