package com.example.appmp3online.Acitivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.appmp3online.Fragment.FragmentLoginTab;
import com.example.appmp3online.Fragment.FragmentSignupTab;
import com.example.appmp3online.R;
import com.example.appmp3online.databinding.ActivityLoginBinding;

import java.util.ArrayList;


public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private ViewPagerAdapter viewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new FragmentLoginTab(),"Login");
        viewPagerAdapter.addFragment(new FragmentSignupTab(),"Signup");
        binding.viewPager.setAdapter(viewPagerAdapter);
        Animation animation = AnimationUtils.loadAnimation(LoginActivity.this,R.anim.animation_alpha);
        binding.imgLogo.setOnClickListener(view -> view.startAnimation(animation));
        onclickFloatButton();
    }

    private void onclickFloatButton() {
        binding.floatingFacbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.facebook.com/?stype=lo&jlou=Afe-8g3mSfxCGLZ_qxfqFH1FHYYbAt24wzp2vwAev1ZvCvzdCPA2A-4VQtUhAQZ3vxGZ8zkCrqXBM2fyLbnB0min0JMtKAhre8EjcrkC8TTpRw&smuh=56910&lh=Ac_9npYdqXkyi8d9fBY");
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }
        });
        binding.floatingGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://accounts.google.com/ServiceLogin/identifier?service=mail&passive=1209600&osid=1&continue=https%3A%2F%2Fmail.google.com%2Fmail%2Fu%2F0%2F&followup=https%3A%2F%2Fmail.google.com%2Fmail%2Fu%2F0%2F&emr=1&flowName=GlifWebSignIn&flowEntry=ServiceLogin");
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }
        });
        binding.floatingTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://twitter.com/?lang=vi");
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }
        });
    }

    public static class ViewPagerAdapter extends FragmentPagerAdapter{
        private final ArrayList<Fragment> fragments;
        private final ArrayList<String> titles;

        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
        public void addFragment(Fragment fragment , String title){
            fragments.add(fragment);
            titles.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }



}