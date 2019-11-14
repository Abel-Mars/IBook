package com.example.ibook;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Book extends AppCompatActivity {

    String lujin;
    static int line_num=0;
    String book_neirong;
    static Map<Integer,String>map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        Bundle bundle = getIntent().getExtras();
        lujin = bundle.getString("lujin");
        File file=new File(lujin);
        Toast.makeText(Book.this,lujin,Toast.LENGTH_LONG).show();
        TextView t1=(TextView)findViewById(R.id.news_item_content_text_view);
        book_neirong =ReadTxtFile(lujin,1);
        t1.setText(book_neirong);
    }
    public static String ReadTxtFile(String strFilePath,int page_position)
    {
        String path = strFilePath;
        String content = ""; //文件内容字符串
        String show = null;
        //打开文件
        File file = new File(path);
        //如果path是传递过来的参数，可以做一个非目录的判断
        if (file.isDirectory())
        {
            Log.d("TestFile", "The File doesn't not exist.");
        }
        else
        {
            try {
                InputStream instream = new FileInputStream(file);
                if (instream != null)
                {
                    InputStreamReader inputreader = new InputStreamReader(instream,"GBK");
                    final BufferedReader buffreader = new BufferedReader(inputreader);
                    String line = null;
                    int line_p=1;
                    int page=1;
                    map = new HashMap<>();
                    while(buffreader.readLine()!=null){
                        line=buffreader.readLine();
                        content+= line + "\n";
                        line_p++;
                        if(line_p==30){
                            line_p=1;
                            map.put(page,content);
                            page++;
                            content=null;
                        }
                    }
                    //分行读取
                    /*for(int i=0;i<50;i++){
                        if((line = buffreader.readLine()) != null) {
                            content += line + "\n";
                        }
                    }*/

                    while (buffreader.readLine() != null) {
                        line_num++;
                    }
                    Log.i("this is a bog proplem",Integer.toString(line_num));
                    instream.close();
                }
            }
            catch (java.io.FileNotFoundException e)
            {
                Log.d("TestFile", "The File doesn't not exist.");
            }
            catch (IOException e)
            {
                Log.d("TestFile", e.getMessage());
            }
        }
        show=map.get(page_position);
        return show;
    }
}
