package com.example.appmp3online.Fragment;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appmp3online.Acitivity.LoginActivity;
import com.example.appmp3online.Acitivity.MyBackgroupService;
import com.example.appmp3online.Adapter.AdapterBaiHat;
import com.example.appmp3online.Model.BaiHat;
import com.example.appmp3online.R;
import com.example.appmp3online.databinding.FragmentListNhacBinding;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FragmentListNhac extends Fragment implements AdapterBaiHat.IListen, TextWatcher {
    private FragmentListNhacBinding binding;
    private MyBackgroupService mService;
    private ExecutorService executorService;
    private ServiceConnection connection;
    private ArrayList<BaiHat> baiHatArrayList = new ArrayList<>();
    private AdapterBaiHat adapterBaiHat;
    boolean isBound = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_list_nhac,container,false);
        viewLoading();
        binding.imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtSearch = binding.edtSearch.getText().toString().trim();
                searchSong(txtSearch.trim().replace(" ","+"));
            }
        });
        adapterBaiHat = new AdapterBaiHat(this);
        mService = new MyBackgroupService();
        configRecylerView();
        connectService();
        binding.imgBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        return binding.getRoot();
    }
        private void viewLoading(){
        binding.recylerBaiHat.setVisibility(View.GONE);
        binding.swiperefresh.setVisibility(View.VISIBLE);
    }
    private void hideLoading(){
        binding.recylerBaiHat.setVisibility(View.VISIBLE);
        binding.swiperefresh.setVisibility(View.GONE);
    }
    private void searchSong(String keySearch) {
        if (executorService !=null && executorService.isShutdown()){
            executorService.shutdown();
        }
        executorService = Executors.newFixedThreadPool(1);
        String urlSearch = "https://chiasenhac.vn/tim-kiem?q="+keySearch;
        configRecylerView();
        new DowloadTask().execute(urlSearch);
    }
    private void connectService(){
        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                MyBackgroupService.MyLocalBinder binder = (MyBackgroupService.MyLocalBinder) iBinder;
                mService = binder.getService();

                isBound = true;
                if (mService.checkEmpty()){
                    searchSong("ai+chung+tinh+duoc+mai");
                }else {
                    binding.recylerBaiHat.getAdapter().notifyDataSetChanged();
                    if (mService.getBaiHatList() != null){
                        hideLoading();
                    }
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                isBound = false;
            }
        };
        Intent intent = new Intent();
        intent.setClassName(getActivity(),MyBackgroupService.class.getName());
        getActivity().bindService(intent,connection, Context.BIND_AUTO_CREATE);
    }
        private void configRecylerView() {
        adapterBaiHat = new AdapterBaiHat(this);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        binding.recylerBaiHat.setLayoutManager(linearLayoutManager);
        binding.recylerBaiHat.setAdapter(adapterBaiHat);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        searchSong(editable.toString().replace("  ", " ").replace(" ","+"));
    }

    @Override
    public int getCount() {
        if (mService==null){
            return 0;
        }
        return mService.sizeItemMusicOnline();
    }

    @Override
    public BaiHat getData(int position) {
        return mService.getData(position);
    }

    @Override
    public void onClickBaiHat(int position) {
        mService.onClickItem(position);
        replaceFragment(new FragmentPlayNhac());
    }

    @Override
    public Context onContext() {
        return null;
    }


    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.framLayout,fragment);
        transaction.addToBackStack(FragmentPlayNhac.TAG);
        transaction.commit();
    }
    @SuppressLint("StaticFieldLeak")
    private class DowloadTask extends AsyncTask<String,Void,ArrayList<BaiHat>> {

        @Override
        protected ArrayList<BaiHat> doInBackground(String... strings) {
            Document document;
            baiHatArrayList = new ArrayList<>();
            try {
                document = Jsoup.connect(strings[0]).get();
                Elements elements = Objects.requireNonNull(document.select("div.tab-content").first()).select("ul.list_music");
                for (Element e : elements) {
                    Elements elements1 = e.select("li.media");
                    for (Element e1 : elements1) {
                        String name = Objects.requireNonNull(e1.select("a.search_title").first()).attr("title");
                        String singer = e1.select("div.media-body").select("div.author").text();
                        String link  = Objects.requireNonNull(e1.select("a.search_title").first()).attr("href");
                        Document document1 = Jsoup.connect(link).get();
                        Element elementLink = document1.select("div.tab-content").first();
                        String linkMP3 = elementLink.select("a.download_item").attr("href");
                        baiHatArrayList.add(new BaiHat(name,singer,linkMP3));

                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }
            return baiHatArrayList;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void onPostExecute(ArrayList<BaiHat> baiHats) {
            super.onPostExecute(baiHats);
            executorService = null;
            if (baiHats !=null){
                mService.setBaiHatList(baiHats);
            }
            binding.recylerBaiHat.getAdapter().notifyDataSetChanged();
            hideLoading();


        }
    }
    

    @Override
    public void onDestroy() {
        mService.unbindService(connection);
        super.onDestroy();
    }
}