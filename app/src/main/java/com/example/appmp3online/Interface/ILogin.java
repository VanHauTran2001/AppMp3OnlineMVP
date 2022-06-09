package com.example.appmp3online.Interface;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public interface ILogin {
    void onSucessfull(String mess);
    void onError(String mess);
    Context context();
    Activity activity();
}
