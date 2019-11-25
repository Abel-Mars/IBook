package com.example.ibook;
import java.util.ArrayList;
import java.util.List;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.ibook.bookshell_frg;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private  BottomNavigationView b1;
    private bookshell_frg find_frangment;
    private bookstore_frg insert_frangment;
    private bookmy_frg  new_frangment;
    private Fragment[] fragments;
    private int lastSelectedPosition;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.option_init:
                Intent intents =new Intent(MainActivity.this,Init_Menu.class);
                startActivity(intents);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        judge();
        b1= (BottomNavigationView)findViewById(R.id.navigation);
        infrang();
    }
    public void judge(){
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
        else {
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int [] grantResults){
        //Log.d("in","first");
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Log.d("in","second");
                    //init();
                }else{
                    Log.d("in","third");
                    Toast.makeText(MainActivity.this,"已拒绝",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }
    public void infrang(){
        //b1.setOnNavigationItemSelectedListener(this);
        find_frangment = new bookshell_frg();
        new_frangment = new bookmy_frg();
        insert_frangment = new bookstore_frg();
        fragments = new Fragment[]{find_frangment, insert_frangment,new_frangment};
        lastSelectedPosition = 0;
        //默认提交第一个
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frangment_change, find_frangment)//添加
                .show(find_frangment)//展示
                .commit();//提交
        b1.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.find_all:
                        if (0 != lastSelectedPosition) {
                            setDefaultFragment(lastSelectedPosition, 0);
                            lastSelectedPosition = 0;
                        }
                        return true;
                    case R.id.insert_all:
                        if (1 != lastSelectedPosition) {
                            setDefaultFragment(lastSelectedPosition, 1);
                            lastSelectedPosition = 1;
                        }
                        return true;
                    case R.id.new_all:
                        if (2 != lastSelectedPosition) {
                            setDefaultFragment(lastSelectedPosition, 2);
                            lastSelectedPosition = 2;
                        }
                        return true;
                }
                return false;
            }
        });
    }
    private void setDefaultFragment(int lastIndex,int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastIndex]);
        if (!fragments[index].isAdded()) {
            transaction.add(R.id.frangment_change, fragments[index]);
        }
        //需要展示fragment下标的位置
        //commit：安排该事务的提交。这一承诺不会立即发生;它将被安排在主线程上，以便在线程准备好的时候完成。
        //commitAllowingStateLoss：与 commit类似，但允许在活动状态保存后执行提交。这是危险的，因为如果Activity需要从其状态恢复，
        // 那么提交就会丢失，因此，只有在用户可以意外地更改UI状态的情况下，才可以使用该提交
        transaction.show(fragments[index]).commit();
    }
}

