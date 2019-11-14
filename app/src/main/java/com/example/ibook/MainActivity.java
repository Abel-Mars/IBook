package com.example.ibook;
import java.util.ArrayList;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
public class MainActivity extends AppCompatActivity {
    BookAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        judge();
        final ArrayList<FileBean> filess=getFilesByType(0);
        for(FileBean file:filess){
            Log.d("ABEL",file.toString());
        }
        ListView lv =(ListView)findViewById(R.id.lv);
        adapter = new BookAdapter(MainActivity.this, R.layout.books, filess);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FileBean files=filess.get(position);
                String lujin=files.toString();
                Bundle bundle =new Bundle();
                bundle.putString("lujin", lujin);
                Intent intent = new Intent(MainActivity.this,Book.class);
                intent.putExtras(bundle);
                //finish();
                startActivity(intent);
                //Toast.makeText(MainActivity.this,lujin,Toast.LENGTH_LONG).show();
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
                    Toast.makeText(this,"已拒绝",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }
    public ArrayList<FileBean> getFilesByType(int fileType) {
        ArrayList<FileBean> files = new ArrayList<FileBean>();
        // 扫描files文件库
        Cursor c = null;
        try {
            c = MainActivity.this.getContentResolver().query(MediaStore.Files.getContentUri("external"), new String[]{"_id", "_data", "_size"}, null, null, null);
            int dataindex = c.getColumnIndex(MediaStore.Files.FileColumns.DATA);
            int sizeindex = c.getColumnIndex(MediaStore.Files.FileColumns.SIZE);

            while (c.moveToNext()) {
                String path = c.getString(dataindex);
                if (FileUtils.getFileType(path) == fileType) {
                    if (!FileUtils.isExists(path)) {
                        continue;
                    }
                    long size = c.getLong(sizeindex);
                    FileBean fileBean = new FileBean(path);
                    files.add(fileBean);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return files;
    }
}

