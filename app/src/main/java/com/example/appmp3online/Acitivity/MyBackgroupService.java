package com.example.appmp3online.Acitivity;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;
import com.example.appmp3online.Model.BaiHat;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class MyBackgroupService extends Service implements MediaPlayer.OnBufferingUpdateListener,MediaPlayer.OnCompletionListener,MediaPlayer.OnPreparedListener {
    private final MyLocalBinder myLocalBinder = new MyLocalBinder();
    private List<BaiHat> baiHatList = new ArrayList<>();
    private int currentPosition = -1;
    private boolean isPrepared = false;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private boolean isPlaying =false;

    public List<BaiHat> getBaiHatList() {
        return baiHatList;
    }

    public void setBaiHatList(List<BaiHat> baiHatList) {
        this.baiHatList = baiHatList;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public boolean isPrepared() {
        return isPrepared;
    }

    public void setPrepared(boolean prepared) {
        isPrepared = prepared;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myLocalBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("dl", "On create");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    public void onClickItem(int position){
        isPrepared = false;
        if (!baiHatList.get(position).getLinkSong().equals("")){
            currentPosition = position;
            play(position);
            return;
        }
        getLinkMp3(baiHatList.get(position).getLinkSong(),position);
        currentPosition = position;
    }
    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {

    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        next();
    }

    public int sizeItemMusicOnline() {
        if (baiHatList == null) {
            return 0;
        }
        return baiHatList.size();
    }

    public BaiHat getData(int position) {
        return baiHatList.get(position);
    }
    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayer.start();
        isPrepared = true;
    }

    public void play(int position){
        try {
        if (mediaPlayer !=null){
            mediaPlayer.release();
        }
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setDataSource(this,Uri.parse(baiHatList.get(position).getLinkSong()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.prepareAsync();
        isPlaying = true;

    }

    public void previous(){
        isPrepared = false;
        if (currentPosition <= 0){
            return;
        }
        currentPosition -= 1;
        play(currentPosition);
    }

    public void pause() {
       if (mediaPlayer !=null){
           if (mediaPlayer.isPlaying()) {
               isPlaying = false;
               mediaPlayer.pause();
           } else {
               isPlaying = true;
               mediaPlayer.start();
           }
       }

    }
    public void repeat(){
        isPrepared = false;
        if(baiHatList.get(currentPosition).getLinkSong()==null || baiHatList.get(currentPosition).getLinkSong().equals("")){
            getLinkMp3(baiHatList.get(currentPosition).getLinkSong(),currentPosition);
        }else {
            play(currentPosition);
        }
    }
    public void randum(){
        isPrepared = false;
        Random random = new Random();
        int index = random.nextInt(baiHatList.size());
        if (index == currentPosition) {
            currentPosition = index - 1;
        }
        currentPosition = index;
        if (baiHatList.get(currentPosition).getLinkSong() == null || baiHatList.get(currentPosition)
                .getLinkSong().equals("")
        ) {
            getLinkMp3(baiHatList.get(currentPosition).getLinkSong(), currentPosition);
        } else {
            play(currentPosition);
        }
    }
    public void next() {
        isPrepared = false;
        if (currentPosition == baiHatList.size() - 1) {
            return;
        }

        currentPosition = currentPosition + 1;
        if (baiHatList.get(currentPosition).getLinkSong() == null || baiHatList.get(currentPosition)
                .getLinkSong().equals("")
        ) {
            getLinkMp3(baiHatList.get(currentPosition).getLinkSong(), currentPosition);
        } else {
            play(currentPosition);
        }
    }
    public BaiHat getCurrentItem() {
        return baiHatList.get(currentPosition);
    }

    public void getLinkMp3(String linkSong, int position) {
        AsyncTask<String, Void, String> asyncTask = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String linkMusic = "";
                try {
                    Document c  = Jsoup.connect(strings[0]).get();

                    Elements els =
                            Objects.requireNonNull(c.select("div.tab-content").first()).select("a.download_item");
                    if (els.size() >= 2) {
                        linkMusic = els.get(1).attr("href");
                    } else {
                        linkMusic = els.get(0).attr("href");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                return linkMusic;
            }

            @Override
            protected void onPostExecute(String result) {
                baiHatList.get(position).setLinkSong(result.toString());
                if (result != null) {
                    play(position);
                }
                super.onPostExecute(result);
            }
        };
        asyncTask.execute(linkSong);
    }

    public boolean checkEmpty() {
        return baiHatList == null || baiHatList.size() == 0;
    }

    public class MyLocalBinder extends Binder {
        public MyBackgroupService getService(){
            return MyBackgroupService.this;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
