package com.example.appmp3online.Acitivity;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.appmp3online.Fragment.FragmentListNhac;
import com.example.appmp3online.R;
import com.example.appmp3online.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity{
    private ActivityMainBinding binding;

    //Model : Chứa dữ liệu , nó chịu tránh nghiệm xử lý logic và giao tiếp với CSDL và các lớp mạng

    //View : Thành phần tương tác trực tiếp với người dùng như XML , Activity,Fragment.
    //Nó hiển thị thông tin cho người dùng nhìn thấy ,không bao gồm bất kì xử lý nào

    //Presenter :Sẽ nhận dữ liệu của người dùng thông qua view , xử lý dữ liệu của người dùng
    //với sự trợ giúp của Model và trả kêt quả về View . Presenter giao tiếp với View qua Interface

    //Khi người dùng tương tác với View, Presenter tiếp nhận tương tác người dùng và update Model.
    // Khi Model được update hay có thay đổi, Presenter lấy dữ liệu từ Model
    // , định dạng và đưa tới View để hiển thị.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        replaceFragment(new FragmentListNhac());

    }


    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.framLayout,fragment);
        transaction.commit();
    }
}