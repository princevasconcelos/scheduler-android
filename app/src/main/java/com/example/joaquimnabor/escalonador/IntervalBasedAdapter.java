package com.example.joaquimnabor.escalonador;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by Joaquim Nabor on 08/04/2016.
 */
public class IntervalBasedAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ArrayList<Thread>> list;

    public IntervalBasedAdapter(Context context, ArrayList<ArrayList<Thread>> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return null;
    }
}
