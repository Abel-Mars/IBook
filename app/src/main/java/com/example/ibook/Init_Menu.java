package com.example.ibook;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.litepal.LitePal;
import org.litepal.exceptions.DataSupportException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Init_Menu extends AppCompatActivity {
    BookAdapter adapter;
    static List<FileBean> allfile=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init__menu);
        files_init();
        ListView lv =(ListView)findViewById(R.id.lv);
        adapter = new BookAdapter(Init_Menu.this, R.layout.books, allfile);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FileBean file1=allfile.get(position);
                List<FileBean>filesss= LitePal.where("file_lujin=?",file1.toString()).find(FileBean.class);
                for(FileBean file_new:filesss){
                    if(file_new.getChoose()==1){
                        file_new.setExst(0);
                       // Log.i("insert??","sucsessful");
                        Toast.makeText(Init_Menu.this,"已经添加到书架",Toast.LENGTH_LONG).show();
                    }
                    else {
                        file_new.setChoose(1);
                        Toast.makeText(Init_Menu.this,"成功添加到书架",Toast.LENGTH_LONG).show();
                    }
                    file_new.save();
                }
                Intent intent = new Intent(Init_Menu.this,MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }
    public void in_database(){
        for(FileBean files :getFilesByType(0)) {
            //Log.i("files1","something do"+files.toString());
           allfile =LitePal.findAll(FileBean.class);
            for(int i=0;i<allfile.size();i++){
               // Log.i("files","something do"+allfile.get(i).toString());
                if(allfile.get(i).toString().equals(files.toString())){
                  //  Log.i("files2","something do");
                    files.setExst(allfile.get(i).getExst());
                    files.setChoose(allfile.get(i).getChoose());
                    allfile.get(i).delete();
                    files.save();
                }
                else{
                    files.save();
                }
            }
        }
    }
    public void files_init(){
        //LitePal.deleteAll(FileBean.class);
        in_database();
        allfile.clear();
        allfile=LitePal.findAll(FileBean.class);
    }
    public ArrayList<FileBean> getFilesByType(int fileType) {
        ArrayList<FileBean> files = new ArrayList<FileBean>();
        // 扫描files文件库
        Cursor c = null;
        try {
            c = Init_Menu.this.getContentResolver().query(MediaStore.Files.getContentUri("external"), new String[]{"_id", "_data", "_size"}, null, null, null);
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
