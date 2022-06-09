package com.example.appmp3online.Login.Signup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.appmp3online.R;
import com.example.appmp3online.databinding.SignupTablayoutFragmentBinding;

public class FragmentSignupTab extends Fragment implements ISignupView {
    private SignupTablayoutFragmentBinding binding;
    private SignupPresenter signupPresenter;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.signup_tablayout_fragment,container,false);
        signupPresenter = new SignupPresenter(getActivity());
        onClickSignup();
        return binding.getRoot();
    }
    @Override
    public void onClickSignup() {
        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.edtEmail.getText().toString().trim();
                String user = binding.edtUser.getText().toString().trim();
                String password = binding.edtPassword.getText().toString().trim();
                String confirmPassword = binding.edtConfirmPassword.getText().toString().trim();
                String phone = binding.edtPhone.getText().toString().trim();
                signupPresenter.onLoginSignup(email,user,password,confirmPassword,phone);
            }
        });
    }
}
