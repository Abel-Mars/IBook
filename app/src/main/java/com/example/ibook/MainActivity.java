package com.example.ibook;
import java.util.ArrayList;
import java.util.List;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import org.litepal.LitePal;
public class MainActivity extends AppCompatActivity {

    List<FileBean> filess=new ArrayList<>();
    RecyclerView rv;
    NewsAdapter newsAdapter;

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
    public void refresh(List<FileBean> filess){
        rv=(RecyclerView)findViewById(R.id.rv);
        rv.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        newsAdapter = new NewsAdapter(filess);
        rv.setAdapter(newsAdapter);
        rv.setItemAnimator(new DefaultItemAnimator());
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        judge();
        filess= LitePal.where("choose = ?and exst=? ","1","0").find(FileBean.class);
        refresh(filess);
        newsAdapter.setOnItemClickListener(new NewsAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                FileBean files=filess.get(position);
                String lujin=files.toString();
                Bundle bundle =new Bundle();
                bundle.putString("lujin", lujin);
                Intent intent = new Intent(MainActivity.this,Book.class);
                intent.putExtras(bundle);
                //finish();
                startActivity(intent);

            }
        });
        newsAdapter.setOnItemLongClickListener(new NewsAdapter.onRecyclerItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, final int position) {
                Log.i("delete1",filess.get(position).toString());
                AlertDialog.Builder builder =new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("删除警告");
                builder.setMessage("确认删除，即将从书架删除！");
                builder.setCancelable(false);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Log.i("delete2",filess.get(position).toString());
                        List<FileBean>l_files=LitePal.where("file_lujin=?",filess.get(position).toString()).find(FileBean.class);
                        for(FileBean f:l_files){
                           // Log.i("tagggg","delete");
                            f.setChoose(0);
                            f.save();
                        }
                        newsAdapter.removeItem(position);

                        Toast.makeText(MainActivity.this,"删除成功",Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();

            }
        });
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
}

