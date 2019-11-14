package com.example.ibook;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import java.io.File;
import java.util.List;
public class BookAdapter extends ArrayAdapter<FileBean> {
    private int resourceId;
    public BookAdapter(Context context, int textViewResourceId, List<FileBean> obj) {
        super(context, textViewResourceId, obj);
        resourceId = textViewResourceId;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FileBean books = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        TextView lu = (TextView) view.findViewById(R.id.lu_jin);
        //ToggleButton chose =(ToggleButton) view.findViewById(R.id.chose);
        String lujin = books.toString();
       /* chose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                } else {
                    // The toggle is disabled
                }
            }
        });*/
        File file=new File(lujin);
        lu.setText(file.getName());
        return view;
    }
}