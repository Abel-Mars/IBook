package com.example.ibook;

import android.content.Context;
import android.content.res.AssetManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class ScanViewAdapter extends PageAdapter
{
    Context context;
    List<String> items;
    AssetManager am;
    List<String> pages;
    public ScanViewAdapter(Context context, List<String> items,List<String> pages)
    {
        this.context = context;
        this.items = items;
        this.pages = pages;
        am = context.getAssets();
    }

    public void addContent(View view, int position)
    {
        TextView content = (TextView) view.findViewById(R.id.content);
        TextView tv = (TextView) view.findViewById(R.id.index);
        if ((position - 1) < 0 || (position - 1) >= getCount())
            return;
        content.setText(pages.get(position-1));
        tv.setText(items.get(position - 1));
    }

    public int getCount()
    {
        return items.size();
    }

    public View getView()
    {
        View view = LayoutInflater.from(context).inflate(R.layout.page_layout,
                null);
        return view;
    }
}