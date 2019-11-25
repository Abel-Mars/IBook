package com.example.ibook;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.ibook.adapter.ScanViewAdapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
public class Book extends AppCompatActivity {
    ScanView scanview;
    ScanViewAdapter adapter;
    String lujin;
    static int line_num = 0;
    ProgressDialog progressDialog;
    static Handler handler;
    static List<String> pages,page_names;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog =new ProgressDialog(Book.this);
        progressDialog.setMessage("Loading.....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        setContentView(R.layout.activity_book2);
        Bundle bundle = getIntent().getExtras();
        lujin = bundle.getString("lujin");
        System.out.println("going the mehond");
        handler =new Handler(){
            @Override
            //当有消息发送出来的时候就执行Handler的这个方法
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                //只要执行到这里就关闭对话框
                progressDialog.dismiss();
            }
        };
        ReadTxtFile(lujin);
        int all_page_num = 0;
        all_page_num = line_num / 30;
        //Log.i("the number of page",Integer.toString(all_page_num));
        //Toast.makeText(Book.this, Integer.toString(all_page_num), Toast.LENGTH_LONG).show();
        scanview = (ScanView) findViewById(R.id.scanview);
        List<String> items = new ArrayList<String>();
        for (int i = 0; i < all_page_num; i++)
            items.add("第 " + (i + 1) + " 页");
        adapter = new ScanViewAdapter(this, items, pages);
        scanview.setAdapter(adapter);

    }

    public static void ReadTxtFile(String strFilePath) {
        String path = strFilePath;
        //文件内容字符串
        System.out.println("alredy the mehond");
        //打开文件
        final File file = new File(path);
        //如果path是传递过来的参数，可以做一个非目录的判断
       if (file.isDirectory())
        {
            Log.d("TestFile", "The File doesn't not exist.");
        }
        else {
          new Thread(new Runnable() {
               @Override
               public void run() {
                   try {
                       InputStream instream = new FileInputStream(file);
                       if (instream != null) {
                           InputStreamReader inputreader = new InputStreamReader(instream, "GBK");
                           final BufferedReader buffreader = new BufferedReader(inputreader);
                           int words_position=0;
                           String line = "",lines="";
                           String ll="";
                           final String RE = "([第].{1,5}[章])(.+)";
                           pages = new ArrayList<String>();
                           page_names= new ArrayList<String>();
                           while ((line = buffreader.readLine()) != null) {
                               if (line.matches(RE)) {
                                   //Log.i("zhangjieming",line);
                                   page_names.add(line);
                               }
                               // Log.i("this is size of line", Integer.toString(line.length()));
                               if (line.length() > 0) {
                                   int Line_line=line.length()/16;
                                   if(line.length()%16!=0){
                                       Line_line+=1;
                                   }
                                   lines += line;
                                    lines+="\n";
                                   if (lines.length() <260) {
                                       continue;
                                   } else {
                                       if(lines.length()>=260&&lines.length()<520){
                                           ll+= lines.substring(0, 260-words_position);
                                           pages.add(ll);
                                           ll = lines.substring(260-words_position);
                                           words_position = ll.length();
                                           if(words_position>=260){
                                               ll=ll.substring(0,260);
                                               pages.add(ll);
                                               ll=ll.substring(260);
                                               words_position = ll.length();
                                           }
                                       }
                                      if(lines.length()>=520&&lines.length()<780){
                                           ll+= lines.substring(0, 260-words_position);
                                           pages.add(ll);
                                           ll = lines.substring(260-words_position,520-words_position);
                                           pages.add(ll);
                                           ll = lines.substring(520-words_position);
                                           words_position = ll.length();
                                          if(words_position>=260){
                                              ll=ll.substring(0,260);
                                              pages.add(ll);
                                              ll=ll.substring(260);
                                              words_position = ll.length();
                                          }
                                       }
                                       if(lines.length()>=780&&lines.length()<1040){
                                           ll+= lines.substring(0, 260-words_position);
                                           pages.add(ll);
                                           ll = lines.substring(260-words_position,520-words_position);
                                           pages.add(ll);
                                           ll = lines.substring(520-words_position,780-words_position);
                                           pages.add(ll);
                                           ll = lines.substring(780-words_position);
                                           words_position = ll.length();
                                           if(words_position>=260){
                                               ll=ll.substring(0,260);
                                               pages.add(ll);
                                               ll=ll.substring(260);
                                               words_position = ll.length();
                                           }
                                       }
                                       //Log.i("this is words",Integer.toString(words_position));
                                       lines = "";
                                   }
                               }
                               line_num++;
                           }
                           instream.close();
                       }
                   } catch (java.io.FileNotFoundException e) {
                       Log.d("TestFile", "The File doesn't not exist.");
                   } catch (IOException e) {
                       Log.d("TestFile", e.getMessage());
                   }
                   handler.sendEmptyMessage(0);
               }
           }).start();
       }
    }
}
