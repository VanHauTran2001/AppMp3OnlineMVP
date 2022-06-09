package com.example.appmp3online.Fragment;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.IBinder;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.bumptech.glide.Glide;
import com.example.appmp3online.Acitivity.MainActivity;
import com.example.appmp3online.Acitivity.MyBackgroupService;
import com.example.appmp3online.Model.BaiHat;
import com.example.appmp3online.R;
import com.example.appmp3online.databinding.FragmentPlayNhacBinding;

import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.concurrent.Executors;

public class FragmentPlayNhac extends Fragment {
    private FragmentPlayNhacBinding binding;
    private boolean isRunning = false;
    //Service
    private MyBackgroupService myService;
    private ServiceConnection serviceConnection;
    public static final String TAG = FragmentPlayNhac.class.getName();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_play_nhac,container,false);
        isRunning = true;
        connectService();
        startAsyn();
        customToolbar();
        evenClick();
        return binding.getRoot();
    }
    private void connectService() {
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                MyBackgroupService.MyLocalBinder binder = (MyBackgroupService.MyLocalBinder) iBinder;
                myService = binder.getService();
                myService.setPlaying(true);
                updateInfo();
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
            }
        };
        Intent intent = new Intent();
        intent.setClassName(getActivity(), MyBackgroupService.class.getName());
        getActivity().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }
    private void updateInfo() {
        if (myService == null) {
            return;
        }
        BaiHat currentSong = myService.getCurrentItem();
        if (currentSong == null) {
            return;
        }
        binding.txtTenBaiHatPlay.setText(currentSong.getNameSong());
        binding.txtCaSiPlay.setText(currentSong.getNameSinger());
        if (myService.isPlaying()) {
            Glide.with(binding.imgPlay)
                    .load(R.drawable.ic_pause)
                    .into(binding.imgPlay);
        } else {
            Glide.with(binding.imgPlay)
                    .load(R.drawable.iconplay)
                    .into(binding.imgPlay);
        }
    }
    public void startAsyn(){
        AsyncTask<Void , Integer , Void> asyncTask = new AsyncTask<Void, Integer, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                while (isRunning){
                    SystemClock.sleep(300);
                    if(myService==null || !myService.isPrepared()){
                        continue;
                    }
                    MediaPlayer mp = myService.getMediaPlayer();
                    publishProgress(mp.getDuration(),mp.getCurrentPosition());
                }
                return null;
            }
            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                binding.seekBarPlaynhac.setProgress(values[1]* 100 / values[0]);
                binding.txtTimeEnd.setText(new SimpleDateFormat("mm:ss").format(values[0]));
                binding.txtTimeStart.setText(new SimpleDateFormat("mm:ss").format(values[1]));
            }
        };

        asyncTask.executeOnExecutor(Executors.newFixedThreadPool(1));
    }
    private void evenClick() {
        binding.imgRepeat.setOnClickListener(v -> {
            myService.repeat();
            updateInfo();
        });
        binding.imgSuffe.setOnClickListener(v -> {
            myService.randum();
            updateInfo();
        });
        binding.seekBarPlaynhac.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser){
                    myService.getMediaPlayer().seekTo(myService.getMediaPlayer().getDuration()/100*progress);
                    binding.seekBarPlaynhac.setProgress(progress);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        binding.imgPlay.setOnClickListener(view -> {
            myService.pause();
            updateInfo();
        });
        binding.imgPreview.setOnClickListener(v -> {
            Glide.with(binding.imgPlay).load(R.drawable.iconplay).into(binding.imgPlay);
            myService.previous();
            updateInfo();
        });
        binding.imgNext.setOnClickListener(v -> {
            Glide.with(binding.imgPlay).load(R.drawable.iconplay).into(binding.imgPlay);
            myService.next();
            updateInfo();
        });
    }
    private void customToolbar() {

        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolbarPlayNhac);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbarPlayNhac.setTitle("");
        binding.toolbarPlayNhac.setNavigationOnClickListener(view -> {
            myService.getMediaPlayer().stop();
            getFragmentManager().popBackStack();
        });

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}