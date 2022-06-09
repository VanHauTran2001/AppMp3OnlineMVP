package com.example.appmp3online.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.appmp3online.Acitivity.MainActivity;
import com.example.appmp3online.Interface.ILogin;
import com.example.appmp3online.Presenter.LoginPresenter;
import com.example.appmp3online.R;
import com.example.appmp3online.databinding.LoginTablayoutFragmentBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FragmentLoginTab extends Fragment implements ILogin {
    private LoginTablayoutFragmentBinding binding;
    private LoginPresenter loginPresenter;
    private int passwordNotVisible = 1;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.login_tablayout_fragment,container,false);
        loginPresenter = new LoginPresenter((ILogin) this);
        onClickLogin();
        binding.imgCheckPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChecked();
            }
        });
        return binding.getRoot();
    }

    private void onChecked() {
            if (passwordNotVisible == 1) {
                binding.imgCheckPass.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                binding.edtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                passwordNotVisible = 0;
            } else {
                binding.imgCheckPass.setImageResource(R.drawable.ic_check_on);
                binding.edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                passwordNotVisible = 1;
            }
            binding.edtPassword.setSelection(binding.edtPassword.length());
    }

    private void onClickLogin() {

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customLogin();
            }
        });
    }

    private void customLogin() {

        String email = binding.edtEmail.getText().toString().trim();
        String passWord = binding.edtPassword.getText().toString().trim();
        loginPresenter.onLogin(email,passWord);

    }

    @Override
    public void onSucessfull(String mess) {
        Toast.makeText(getActivity(),mess, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(String mess) {
        Toast.makeText(getActivity(),mess, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context context() {
        return getContext();
    }

    @Override
    public Activity activity() {
        return getActivity();
    }
}
