package com.example.ibook;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.ibook.Book;
import com.example.ibook.MainActivity;
import com.example.ibook.NewsAdapter;
import com.example.ibook.R;
import com.example.ibook.tool.FileBean;
import org.litepal.LitePal;
import java.util.ArrayList;
import java.util.List;
public class bookshell_frg extends Fragment{
    List<FileBean> filess=new ArrayList<>();
    RecyclerView rv;
    NewsAdapter newsAdapter;
    public void refresh(List<FileBean> filess){
        rv=(RecyclerView)getActivity().findViewById(R.id.rv);
        rv.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        newsAdapter = new NewsAdapter(filess);
        rv.setAdapter(newsAdapter);
        rv.setItemAnimator(new DefaultItemAnimator());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view =inflater.inflate(R.layout.bookshell_frg,container,false);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        filess= LitePal.where("choose = ?and exst=? ","1","0").find(FileBean.class);
        refresh(filess);
        newsAdapter.setOnItemClickListener(new NewsAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                FileBean files=filess.get(position);
                String lujin=files.toString();
                Bundle bundle =new Bundle();
                bundle.putString("lujin", lujin);
                Intent intent = new Intent(getActivity(), Book.class);
                intent.putExtras(bundle);
                //finish();
                startActivity(intent);

            }
        });
        newsAdapter.setOnItemLongClickListener(new NewsAdapter.onRecyclerItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, final int position) {
                Log.i("delete1",filess.get(position).toString());
                AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());
                builder.setTitle("删除警告");
                builder.setMessage("确认删除，即将从书架删除！");
                builder.setCancelable(false);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        List<FileBean>l_files=LitePal.where("file_lujin=?",filess.get(position).toString()).find(FileBean.class);
                        for(FileBean f:l_files){
                            f.setChoose(0);
                            f.save();
                        }
                        newsAdapter.removeItem(position);

                        Toast.makeText(getActivity(),"删除成功",Toast.LENGTH_LONG).show();
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
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //LitePal.getDatabase();
        //new_data();
    }
    /*@Override
    public void onHiddenChanged(boolean hidden){
        if (hidden){
            return;
        }else {
            new_data();
        }
    }*/
}
