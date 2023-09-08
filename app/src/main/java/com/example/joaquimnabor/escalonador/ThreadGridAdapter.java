package com.example.joaquimnabor.escalonador;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by Joaquim Nabor on 01/04/2016.
 */
public class ThreadGridAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Thread> threadsList;
    private int flag;

    public ThreadGridAdapter(Context context, int flag, ArrayList<Thread> threadsList) {
        this.context = context;
        this.threadsList = threadsList;
        this.flag = flag;
    }

    @Override
    public int getCount() {
        return threadsList.size();
    }

    @Override
    public Object getItem(int position) {
        return threadsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ThreadActivity thread = ThreadActivity_.build(context);

        if (flag == 0) {
            thread.bindEmpty(threadsList.get(position).getId());
        } else {
            if (flag == 1) {
                thread.bindLTGReady(threadsList.get(position).getId(), threadsList.get(position).getRuntime(), threadsList.get(position).getDeadline());
            }
            if (flag == 2) {
                thread.bindLTGRunning(threadsList.get(position).getId(), threadsList.get(position).getRuntime(), threadsList.get(position).getDeadline());
            }
            if (flag == 3) {
                thread.bindLTGFinisheNAborted(threadsList.get(position).getId(), threadsList.get(position).getRuntime(), threadsList.get(position).getDeadline());
            }
            if (flag == 4) {
                thread.bindFPRRRunning(threadsList.get(position).getId(), threadsList.get(position).getRuntime(), threadsList.get(position).getPriority(), threadsList.get(position).getQuantum());
            }
            if (flag == 5) {
                thread.bindFPRRReadyPriorityX(threadsList.get(position).getId(), threadsList.get(position).getRuntime(), threadsList.get(position).getQuantum(), threadsList.get(position).getPriority());
            }
            if (flag == 6) {
                thread.bindFPRRFinished(threadsList.get(position).getId(), threadsList.get(position).getRuntime(), threadsList.get(position).getQuantum(), threadsList.get(position).getPriority());
            }
            if (flag == 7) {
                thread.bindIBAReady(threadsList.get(position).getId(), threadsList.get(position).getRuntime(), threadsList.get(position).getDeadline(), threadsList.get(position).getStart(), threadsList.get(position).getStop());
            }
        }
        return thread;
    }
}
