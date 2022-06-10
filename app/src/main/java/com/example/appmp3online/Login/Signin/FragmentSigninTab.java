package com.example.appmp3online.Login.Signin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.appmp3online.Acitivity.MainActivity;
import com.example.appmp3online.R;
import com.example.appmp3online.databinding.LoginTablayoutFragmentBinding;

public class FragmentSigninTab extends Fragment implements ISigninView {
    private LoginTablayoutFragmentBinding binding;
    private ISigninPresenter loginPresenter;
    private int passwordNotVisible = 1;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.login_tablayout_fragment,container,false);
        loginPresenter = new SigninPresenter( this);
        loginPresenter.onInit();
        return binding.getRoot();
    }

    @Override
    public void onClickListener() {
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customLogin();
            }
        });
        binding.imgCheckPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChecked();
            }
        });
    }

    @Override
    public void onSucessfull(String mess) {
        Toast.makeText(getActivity(), mess, Toast.LENGTH_SHORT).show();
        Intent intent =new Intent(getActivity(), MainActivity.class);
        getActivity().startActivity(intent);
        getActivity().finishAffinity();
    }

    @Override
    public void onError(String mess) {
        Toast.makeText(getActivity(),mess,Toast.LENGTH_SHORT).show();
    }


    public void customLogin() {
        String email = binding.edtEmail.getText().toString().trim();
        String passWord = binding.edtPassword.getText().toString().trim();
        loginPresenter.onLogin(email,passWord);
    }


    public void onChecked() {
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

    @Override
    public Context onContext() {
        return getActivity();
    }
}
