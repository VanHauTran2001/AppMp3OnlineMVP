package com.example.appmp3online.Login.Signin;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.appmp3online.R;
import com.example.appmp3online.databinding.LoginTablayoutFragmentBinding;

public class FragmentSigninTab extends Fragment implements ISigninView {
    private LoginTablayoutFragmentBinding binding;
    private SigninPresenter loginPresenter;
    private int passwordNotVisible = 1;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.login_tablayout_fragment,container,false);
        loginPresenter = new SigninPresenter(getActivity());
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
        return binding.getRoot();
    }

    @Override
    public void customLogin() {
        String email = binding.edtEmail.getText().toString().trim();
        String passWord = binding.edtPassword.getText().toString().trim();
        loginPresenter.onLogin(email,passWord);
    }

    @Override
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
}
