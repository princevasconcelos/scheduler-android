package com.example.joaquimnabor.escalonador;

import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.GridView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


@EActivity(R.layout.activity_ltg)
public class LTGActivity extends AppCompatActivity {

    @Extra("processadores")
    String processadores;

    @Extra("processos")
    String processos;

    @ViewById
    GridView runningView;

    @ViewById
    GridView readyView;

    @ViewById
    GridView finishedNAbortedView;

    @ViewById
    Button startButton;

    ArrayList<Thread> runningsList = new ArrayList<>();
    ArrayList<Thread> readyList = new ArrayList<>();
    ArrayList<Thread> finishedNAbortedList = new ArrayList<>();

    int openCore = 0;
    int coreSize;
    int threadSize;

    final Timer timer = new Timer();
    Random random;

    @Click
    void newThread() {
        int randomRuntime = getRandomRuntime();
        int randomDeadline = getRandomDeadline();
        int proc = Integer.parseInt(processadores);

        synchronized (this) {
            readyList.add(new Thread(threadSize + 1, randomRuntime, randomDeadline));
            threadSize++;
            sortDeadline(readyList);

            if (runningsList.size() < proc) {
                runningsList.add(readyList.get(0));
                readyList.remove(0);
            }
        }
    }

    @Click
    void startButton() {
        startButton.setEnabled(false);
        fillRunningView();
        doEverySecond();
    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
    }

    @AfterViews
    void init()  {

        coreSize = Integer.parseInt(processadores);
        threadSize = Integer.parseInt(processos);
        startButton.setEnabled(true);
        random = new Random();

        for (int i = 1; i < threadSize +1; i++) {
            int randomRuntime = getRandomRuntime();
            int randomDeadline = getRandomDeadline();
            readyList.add(new Thread(i, randomRuntime, randomDeadline));
        }

        for (int j = 1; j < coreSize +1; j++) {
            runningsList.add(new Thread(j));
        }

        runningView.setAdapter(new ThreadGridAdapter(this, 0, runningsList));
        updateReadyView(readyList);
    }

    void fillRunningView() {
        synchronized (this) {
            sortDeadline(readyList);

            if (threadSize <= coreSize) {
                for (int i = 0; i < threadSize; i++) {
                    runningsList.add(i, readyList.get(0));
                    runningsList.remove(i+1);
                    readyList.remove(0);
                }
            } else {
                for (int i = 0; i < coreSize; i++) {
                    runningsList.add(i, readyList.get(0));
                    runningsList.remove(i+1);
                    readyList.remove(0);
                }
            }

            updateRunningView(runningsList);
            updateReadyView(readyList);
        }
    }

    void sortDeadline(ArrayList<Thread> readyList) {
        for (int i = 0; i < readyList.size(); i++) {
            Thread menor = readyList.get(i);
            for (int j = 0; j < readyList.size(); j++) {
                if (readyList.get(j).getDeadline() > menor.getDeadline()) {
                    Thread aux = menor;
                    menor = readyList.get(j);
                    readyList.set(j, aux);
                }
                readyList.set(i, menor);
            }
        }
    }

    void checkOpenCore(){
        if (openCore > 0) {
            if (readyList.size() > 0) {
                while (runningsList.size() < coreSize && readyList.size() > 0) {
                    runningsList.add(readyList.get(0));
                    openCore--;
                    readyList.remove(0);
                }
            }
        }
    }

    @UiThread
    void decreaseRunningViewRuntime() {
        synchronized (this) {
            int openCoreId;
            for (Iterator<Thread> i = runningsList.iterator(); i.hasNext();) {
                Thread t = i.next();
                if (t.getRuntime() == 1) {
                    t.setRuntime(t.getRuntime() - 1);
                    finishedNAbortedList.add(t);
                    i.remove();
                    openCore++;
                } else {
                    t.setRuntime(t.getRuntime() - 1);
                }
            }
            checkOpenCore();

            updateRunningView(runningsList);
            updateReadyView(readyList);
            updateFinishedView(finishedNAbortedList);
        }
    }

    @UiThread
    void decreaseReadyViewDeadlineAndRuntime() {
        synchronized (this) {
            for (Iterator<Thread> i = readyList.iterator(); i.hasNext();) {
                Thread t = i.next();
                if (t.getDeadline() == 1 || t.getRuntime() == 1) {
                    t.setDeadline(t.getDeadline() - 1);
                    t.setRuntime(t.getRuntime() - 1);
                    finishedNAbortedList.add(t);
                    i.remove();
                } else {
                    t.setDeadline(t.getDeadline() - 1);
                    t.setRuntime(t.getRuntime() - 1);
                }
            }
        }
    }

    void doEverySecond() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                synchronized (this) {
                    if (readyList.size() > 0) {
                        decreaseReadyViewDeadlineAndRuntime();
                    }

                    if (runningsList.size() > 0) {
                        decreaseRunningViewRuntime();
                    }
                }
            }
        }, 1000, 1000);
    }

    void updateReadyView(ArrayList<Thread> readyList) {
        ThreadGridAdapter readyAdapter = new ThreadGridAdapter(this, 1, readyList);
        readyView.setAdapter(readyAdapter);
        readyAdapter.notifyDataSetChanged();
    }

    void updateRunningView(ArrayList<Thread> runningsList) {
        ThreadGridAdapter runningAdapter = new ThreadGridAdapter(this, 2, runningsList);
        runningView.setAdapter(runningAdapter);
        runningAdapter.notifyDataSetChanged();
    }

    void updateFinishedView(ArrayList<Thread> finishedNAbortedList) {
        ThreadGridAdapter finishedAdapter = new ThreadGridAdapter(this, 3, finishedNAbortedList);
        finishedNAbortedView.setAdapter(finishedAdapter);
        finishedAdapter.notifyDataSetChanged();
    }

    int getRandomRuntime() {
        return random.nextInt(31-1) + 1;
    }

    int getRandomDeadline() {
        return random.nextInt(21-1) + 1;
    }
}