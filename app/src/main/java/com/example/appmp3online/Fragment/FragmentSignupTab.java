package com.example.appmp3online.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.appmp3online.Interface.ILogin;
import com.example.appmp3online.Presenter.LoginPresenter;
import com.example.appmp3online.Presenter.SignupPresenter;
import com.example.appmp3online.R;
import com.example.appmp3online.databinding.SignupTablayoutFragmentBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class FragmentSignupTab extends Fragment implements ILogin {
    private SignupTablayoutFragmentBinding binding;
    private SignupPresenter signupPresenter;
    private ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.signup_tablayout_fragment,container,false);
        signupPresenter = new SignupPresenter((ILogin) this);
        progressDialog = new ProgressDialog(getContext());
        onClickSignup();
        return binding.getRoot();
    }

    private void onClickSignup() {
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
