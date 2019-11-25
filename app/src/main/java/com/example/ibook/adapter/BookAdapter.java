package com.example.ibook.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;

import com.example.ibook.tool.FileBean;
import com.example.ibook.R;

import java.io.File;
import java.util.List;
public class BookAdapter extends ArrayAdapter<FileBean> {
    private int resourceId;
    int choss=0;
    public BookAdapter(Context context, int textViewResourceId, List<FileBean> obj) {
        super(context, textViewResourceId, obj);
        resourceId = textViewResourceId;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final FileBean books = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        TextView lu = (TextView) view.findViewById(R.id.lujin);
        String lujin = books.toString();
        File file=new File(lujin);
        lu.setText(file.getName());
        return view;
    }
}