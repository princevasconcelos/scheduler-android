package com.example.joaquimnabor.escalonador;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

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

@EActivity(R.layout.activity_fprr)
public class FPRRActivity extends AppCompatActivity {

    @Extra("processadores")
    String processadores;

    @Extra("processos")
    String processos;

    @Extra("quantum")
    String quantum;

    @ViewById
    TextView textView1;

    @ViewById
    TextView textView2;

    @ViewById
    TextView textView3;

    @ViewById
    TextView textView4;

    @ViewById
    TextView textViewFinished;

    @ViewById
    GridView runningView;

    @ViewById
    GridView readyViewPriority1;

    @ViewById
    GridView readyViewPriority2;

    @ViewById
    GridView readyViewPriority3;

    @ViewById
    GridView readyViewPriority4;

    @ViewById
    GridView finishedView;

    @ViewById
    Button startButton;

    @ViewById
    EditText newQuantum;

    @Click
    void changeQuantum() {
        int quantum = Integer.parseInt(newQuantum.getText().toString());
        quantumValue = quantum;
        //So muda o quantum dos novos processos e quando o processo volta pra readylist
    }

    @Click
    void newThreadButton() {
        if (qtdProcessos < qtdProcessadores) {
            if (newThreadFlag == 4) {
                Thread t = new Thread(qtdProcessos+1,getRandomRuntime(),4, quantumValue);
                runningsList.add(qtdProcessos, t);
                runningsList.remove(qtdProcessos + 1);
                qtdProcessos++;
                newThreadFlag = 1;
            } else {
                if (newThreadFlag == 1) {
                    Thread t = new Thread(qtdProcessos+1,getRandomRuntime(),1, 4*quantumValue);
                    runningsList.add(qtdProcessos, t);
                    runningsList.remove(qtdProcessos + 1);
                    qtdProcessos++;
                    newThreadFlag = 2;
                } else {
                    if (newThreadFlag == 2) {
                        Thread t = new Thread(qtdProcessos+1,getRandomRuntime(),2, 3* quantumValue);
                        runningsList.add(qtdProcessos, t);
                        runningsList.remove(qtdProcessos + 1);
                        qtdProcessos++;
                        newThreadFlag = 3;
                    } else {
                        if (newThreadFlag == 3) {
                            Thread t = new Thread(qtdProcessos+1,getRandomRuntime(),3, 2*quantumValue);
                            runningsList.add(qtdProcessos, t);
                            runningsList.remove(qtdProcessos + 1);
                            qtdProcessos++;
                            newThreadFlag = 4;
                        }

                    }
                }
            }
        } else {
            if (newThreadFlag == 4) {
                priorityList4.add(new Thread(qtdProcessos + 1, getRandomRuntime(), 4, quantumValue));
                qtdProcessos++;
                newThreadFlag = 1;
            } else {
                if (newThreadFlag == 1) {
                    priorityList1.add(new Thread(qtdProcessos+1,getRandomRuntime(),1, 4*quantumValue));
                    qtdProcessos++;
                    newThreadFlag = 2;
                } else {
                    if (newThreadFlag == 3) {
                        priorityList3.add(new Thread(qtdProcessos + 1, getRandomRuntime(), 3, 2*quantumValue));
                        qtdProcessos++;
                        newThreadFlag = 4;
                    } else {
                        if (newThreadFlag == 2) {
                            priorityList2.add(new Thread(qtdProcessos+1,getRandomRuntime(),2, 3*quantumValue));
                            qtdProcessos++;
                            newThreadFlag = 3;
                        }

                    }
                }
            }
        }

//levar para a running list
//se qtd processos > qtd cores -> open core

    }

//    if (newThreadFlag == 4) {
//        priorityList1.add(new Thread(qtdProcessos+1,getRandomRuntime(),1, 4*quantumValue));
//        qtdProcessos++;
//        newThreadFlag = 1;
//    } else {
//        if (newThreadFlag == 1) {
//            priorityList2.add(new Thread(qtdProcessos+1,getRandomRuntime(),2, 3*quantumValue));
//            qtdProcessos++;
//            newThreadFlag = 2;
//        } else {
//            if (newThreadFlag == 2) {
//                priorityList3.add(new Thread(qtdProcessos+1,getRandomRuntime(),3, 2* quantumValue));
//                qtdProcessos++;
//                newThreadFlag = 3;
//            } else {
//                if (newThreadFlag == 3) {
//                    priorityList4.add(new Thread(qtdProcessos+1,getRandomRuntime(),4, quantumValue));
//                    qtdProcessos++;
//                    newThreadFlag = 4;
//                }
//            }
//        }
//    }


    void ownCore() {
        int i = 0;
        while (i < qtdProcessos){
            if (i < qtdProcessos) {
                runningsList.add(i, priorityList1.get(0));
                runningsList.remove(i + 1);
                priorityList1.remove(0);
                i++;
            }
            if (i < qtdProcessos) {
                runningsList.add(i, priorityList2.get(0));
                runningsList.remove(i + 1);
                priorityList2.remove(0);
                i++;
            }
            if (i < qtdProcessos) {
                runningsList.add(i, priorityList3.get(0));
                runningsList.remove(i+1);
                priorityList3.remove(0);
                i++;
            }
            if (i < qtdProcessos) {
                runningsList.add(i, priorityList4.get(0));
                runningsList.remove(i+1);
                priorityList4.remove(0);
                i++;
            }
        }
    }

    @Click
    void startButton() {
        startButton.setEnabled(false);
        if (caseFlag == 1) {
            fillRunningView();
        } else {
            ownCore();
        }
        doEverySecond();
    }

    void doEverySecond() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                if (runningsList.size() > 0) {
                    checkRuntimeAndQuantum();
                }

                if (caseFlag == 1) {
                    checkOpenCores();
                }

                refreshData();

            }
        }, 1000, 1000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
    }

    ArrayList<Thread> runningsList = new ArrayList<>();
    ArrayList<Thread> priorityList1 = new ArrayList<>();
    ArrayList<Thread> priorityList2 = new ArrayList<>();
    ArrayList<Thread> priorityList3 = new ArrayList<>();
    ArrayList<Thread> priorityList4 = new ArrayList<>();
    ArrayList<Thread> finishedList = new ArrayList<>();

    int lastThreadPosition = 0;
    int lastListFlag = 0;
    int openCore = 0;
    int qtdProcessadores;
    int qtdProcessos;
    int quantumValue;
    int caseFlag;
    int newThreadFlag;

    final Timer timer = new Timer();
    Random random;

    @AfterViews
    void init()  {

        textView1.setBackgroundColor(Color.CYAN);
        textView2.setBackgroundColor(Color.MAGENTA);
        textView3.setBackgroundColor(Color.BLUE);
        textView4.setBackgroundColor(Color.YELLOW);
        textViewFinished.setBackgroundColor(Color.GREEN);
        qtdProcessadores = Integer.parseInt(processadores);
        qtdProcessos = Integer.parseInt(processos);
        quantumValue = Integer.parseInt(quantum);
        startButton.setEnabled(true);
        random = new Random();
        int randomRuntime;

        if (qtdProcessos > qtdProcessadores) {
            caseFlag = 1;
        } else {
            caseFlag = 2;
        }

        int i = 1;
        while (i < qtdProcessos+1) {
            if (i < qtdProcessos+1) {
                randomRuntime = getRandomRuntime();
                priorityList1.add(new Thread(i, randomRuntime, 1, 4 * quantumValue));
                i++;
                newThreadFlag = 2;
            }
            if (i < qtdProcessos+1) {
                randomRuntime = getRandomRuntime();
                priorityList2.add(new Thread(i, randomRuntime, 2, 3 * quantumValue));
                i++;
                newThreadFlag = 3;
            }
            if (i < qtdProcessos+1) {
                randomRuntime = getRandomRuntime();
                priorityList3.add(new Thread(i, randomRuntime, 3, 2 * quantumValue));
                i++;
                newThreadFlag = 4;
            }
            if (i < qtdProcessos+1) {
                randomRuntime = getRandomRuntime();
                priorityList4.add(new Thread(i, randomRuntime, 4, quantumValue));
                i++;
                newThreadFlag = 1;
            }
        }

        for (int j = 1; j < qtdProcessadores+1; j++) {
            runningsList.add(new Thread(j));
            openCore++;
        }

        refreshViews();
    }

    void fillRunningView() {

        while (openCore > 0) {
            if (priorityList1.size() > 0 && openCore > 0) {
                runningsList.remove(lastThreadPosition);
                runningsList.add(lastThreadPosition, priorityList1.get(0));
                priorityList1.remove(0);
                openCore--;
                lastThreadPosition++;
                lastListFlag = 1;
            }
            if (priorityList2.size() > 0 && openCore > 0) {
                runningsList.remove(lastThreadPosition);
                runningsList.add(lastThreadPosition, priorityList2.get(0));
                priorityList2.remove(0);
                openCore--;
                lastThreadPosition++;
                lastListFlag = 2;
            }
            if (priorityList3.size() > 0 && openCore > 0) {
                runningsList.remove(lastThreadPosition);
                runningsList.add(lastThreadPosition, priorityList3.get(0));
                priorityList3.remove(0);
                openCore--;
                lastThreadPosition++;
                lastListFlag = 3;
            }
            if (priorityList4.size() > 0 && openCore > 0) {
                runningsList.remove(lastThreadPosition);
                runningsList.add(lastThreadPosition, priorityList4.get(0));
                priorityList4.remove(0);
                openCore--;
                lastThreadPosition++;
                lastListFlag = 4;
            }
        }
    }

    @UiThread
    void checkRuntimeAndQuantum() {
        decreaseRuntimeAndQuantum(runningsList);
    }

    @UiThread
    void checkOpenCores() {
        verifyOpenCores();
    }

    @UiThread
    void refreshData() {
        refreshViews();
    }
    void decreaseRuntimeAndQuantum(ArrayList<Thread> list) {
        synchronized (this) {
            for (Iterator<Thread> i = list.iterator(); i.hasNext(); ) {
                Thread t = i.next();
                if (t.getRuntime() == 1) {
                    t.setRuntime(t.getRuntime() - 1);
                    t.setQuantum(t.getQuantum() - 1);
                    finishedList.add(t);
                    i.remove();
                    openCore++;
                } else {
                    if (t.getQuantum() == 1) {
                        t.setRuntime(t.getRuntime() - 1);
                        if (t.getPriority() == 1) {
                            t.setQuantum(4 * quantumValue);
                            if (caseFlag == 1) {
                                priorityList1.add(t);
                                i.remove();
                                openCore++;
                            }
                        } else {
                            if (t.getPriority() == 2) {
                                t.setQuantum(3 * quantumValue);
                                if (caseFlag == 1) {
                                    priorityList2.add(t);
                                    i.remove();
                                    openCore++;
                                }
                            }
                            if (t.getPriority() == 3) {
                                t.setQuantum(2 * quantumValue);
                                if (caseFlag == 1) {
                                    priorityList3.add(t);
                                    i.remove();
                                    openCore++;
                                }
                            }
                            if (t.getPriority() == 4) {
                                t.setQuantum(quantumValue);
                                if (caseFlag == 1) {
                                    priorityList4.add(t);
                                    i.remove();
                                    openCore++;
                                }
                            }
                        }
                    } else {
                        t.setRuntime(t.getRuntime() - 1);
                        t.setQuantum(t.getQuantum() - 1);
                    }
                }
            }
        }
    }


    void verifyOpenCores(){
        synchronized (this) {
            int start;
            if (lastListFlag == 4) {
                start = 1;
            } else {
                start = lastListFlag+1;
            }
            while (openCore > 0 && (!priorityList1.isEmpty() || !priorityList2.isEmpty() || !priorityList3.isEmpty() || !priorityList4.isEmpty())) { //&& runningsList.size() <= qtdProcessadores
                if (priorityList1.size() > 0) {
                    if (start == 1 && openCore > 0) {
                        runningsList.add(0, priorityList1.get(0));
                        priorityList1.remove(0);
                        openCore--;
                        lastListFlag = 1;
                        start = 2;
                    }
                } else {
                    start = 2;
                }
                if (priorityList2.size() > 0) {
                    if (start == 2 && openCore > 0) {
                        runningsList.add(0, priorityList2.get(0));
                        priorityList2.remove(0);
                        openCore--;
                        lastListFlag = 2;
                        start = 3;
                    }
                } else {
                    start = 3;
                }

                if (priorityList3.size() > 0) {
                    if (start == 3 && openCore > 0) {
                        runningsList.add(0, priorityList3.get(0));
                        priorityList3.remove(0);
                        openCore--;
                        lastListFlag = 3;
                        start = 4;
                    }
                } else {
                    start = 4;
                }
                if (priorityList4.size() > 0) {
                    if (start == 4 && openCore > 0) {
                        runningsList.add(0, priorityList4.get(0));
                        priorityList4.remove(0);
                        openCore--;
                        lastListFlag = 4;
                        start = 1;
                    }
                } else {
                    start = 1;
                }
            }
        }
    }

    void refreshViews() {
        synchronized (this) {
            updateRunningView(runningsList);
            updateReadyView(priorityList1, readyViewPriority1);
            updateReadyView(priorityList2, readyViewPriority2);
            updateReadyView(priorityList3, readyViewPriority3);
            updateReadyView(priorityList4, readyViewPriority4);
            updateFinishedView(finishedList);
        }
    }

    void updateReadyView(ArrayList<Thread> readyList, GridView readyView) {
        ThreadGridAdapter readyAdapter = new ThreadGridAdapter(this, 5, readyList);
        readyView.setAdapter(readyAdapter);
        readyAdapter.notifyDataSetChanged();
    }

    void updateRunningView(ArrayList<Thread> runningsList) {
        ThreadGridAdapter runningAdapter = new ThreadGridAdapter(this, 4, runningsList);
        runningView.setAdapter(runningAdapter);
        runningAdapter.notifyDataSetChanged();
    }

    void updateFinishedView(ArrayList<Thread> finishedList) {
        ThreadGridAdapter finishedAdapter = new ThreadGridAdapter(this, 6, finishedList);
        finishedView.setAdapter(finishedAdapter);
        finishedAdapter.notifyDataSetChanged();
    }

    int getRandomRuntime() {
        return random.nextInt(31-1) + 5;
    }
}
