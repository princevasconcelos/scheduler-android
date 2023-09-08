package com.example.joaquimnabor.escalonador;

/**
 * Created by Joaquim Nabor on 01/04/2016.
 */
public class Thread {

    private int id;
    private int runtime;
    private int deadline;
    private int quantum;
    private int start;
    private int stop;
    private int priority;

    public Thread(int id) {
        this.id = id;
    }

    public Thread(int id, int runtime, int deadline) {
        this.id = id;
        this.runtime = runtime;
        this.deadline = deadline;
    }

    public Thread(int id, int runtime, int priority, int quantum) {
        this.id = id;
        this.quantum = quantum;
        this.runtime = runtime;
        this.priority = priority;
    }

    public Thread(int id, int runtime, int deadline, int start, int stop) {
        this.id = id;
        this.runtime = runtime;
        this.deadline = deadline;
        this.start = start;
        this.stop = stop;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public int getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    public int getQuantum() {
        return quantum;
    }

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getStop() {
        return stop;
    }

    public void setStop(int stop) {
        this.stop = stop;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
