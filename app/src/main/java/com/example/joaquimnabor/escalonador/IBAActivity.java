package com.example.joaquimnabor.escalonador;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Random;

@EActivity(R.layout.activity_iba)
public class IBAActivity extends AppCompatActivity {

    @Extra("processadores")
    String processadores;

    @Extra("processos")
    String processos;

    @ViewById
    GridView readyView;

    ArrayList<ArrayList<Thread>> allReadyLists = new ArrayList<>();
    ArrayList<Thread> readyList = new ArrayList<>();
    ArrayList<Thread> resultList = new ArrayList<>();

    Random random;
    int coresCount;
    int threadsCount;

    int getReadyListSize() {
        int size = 0;
        for (ArrayList<Thread> item: allReadyLists) {
            size += item.size();
        }
        return size;
    }

    @AfterViews
    void init() {
        random = new Random();
        coresCount = Integer.parseInt(processadores);
        threadsCount = Integer.parseInt(processos);

        for (int i = 0; i < coresCount; i++) {
            allReadyLists.add(new ArrayList<Thread>());
        }

        int aux = 0;
        while (getReadyListSize() <= threadsCount) {
            if (aux == coresCount) {
                aux = 0;
            } else {
                allReadyLists.get(aux).add(new Thread(aux,getRandomRuntime(),getRandomDeadline(),getRandomStartTime(),getRandomEndTime()));
                aux++;
            }
        }

        readyView.setAdapter(new IntervalBasedAdapter(this, allReadyLists));

//        for (int i = 0; i < threadsCount; i++) {
//            allReadyLists.get(i).add(new Thread(i,getRandomRuntime(),getRandomDeadline(),getRandomStartTime(),getRandomEndTime()));
//        }
//        int i = 0;
//
//
//
//        for (int i = 0; i < threadsCount; i++) {
//            int randomStartTime = getRandomStartTime();
//            int randomEndTime = getRandomEndTime();
//
//            if (randomStartTime < randomEndTime) {
//                if (i <= threadsCount) {
//                    allReadyLists.get(i).add(new Thread(i+1, getRandomRuntime(), getRandomDeadline(), getRandomStartTime(), getRandomRuntime()));
//                    i++;
//                }
//            }
//        }
//
//        int size  = 0;
    }

//    void updateReadyView(ArrayList<Thread> readyList) {
//        ThreadGridAdapter readyAdapter = new ThreadGridAdapter(this, 7, readyList);
//        readyView.setAdapter(readyAdapter);
//        readyAdapter.notifyDataSetChanged();
//    }
//
//    void updateRunningView(ArrayList<Thread> runningsList) {
//        ThreadGridAdapter runningAdapter = new ThreadGridAdapter(this, 2, runningsList);
//        runningView.setAdapter(runningAdapter);
//        runningAdapter.notifyDataSetChanged();
//    }

    int getRandomStartTime() {return random.nextInt(25 -1) +1;}

    int getRandomEndTime() {return random.nextInt(25 -1) +1;}

    int getRandomRuntime() {
        return random.nextInt(31-1) + 1;
    }

    int getRandomDeadline() {
        return random.nextInt(21-1) + 1;
    }
}
