package com.example.joaquimnabor.escalonador;

import android.content.Context;
import android.graphics.Color;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.activity_thread)
public class ThreadActivity extends RelativeLayout {

    @ViewById
    TextView deadline;

    @ViewById
    TextView quantum;

    @ViewById
    TextView priority;

    @ViewById
    TextView startTime;

    @ViewById
    TextView endTime;

    @ViewById
    TextView processTime;

    @ViewById
    TextView id;

    @ViewById
    TextView empty;

    @ViewById
    RelativeLayout relativeBackground;

    public ThreadActivity(Context context) {
        super(context);
    }

    void bindEmpty(int id) {
        this.id.setText("c"+id+ " Open");
        this.processTime.setText("");
        this.deadline.setText("");
        this.quantum.setText("");
        this.startTime.setText("");
        this.endTime.setText("");
        this.processTime.setText("");
        this.relativeBackground.setBackgroundColor(Color.LTGRAY);
    }

    void bindIBAReady(int id, int runtime, int deadline, int start, int end) {
        this.id.setText("p"+Integer.toString(id));
        this.processTime.setText(Integer.toString(runtime)+"s");
        this.deadline.setText("d="+Integer.toString(deadline));
        this.startTime.setText(Integer.toString(start)+":00");
        this.endTime.setText(Integer.toString(end)+":00");
        relativeBackground.setBackgroundColor(Color.YELLOW);

    }

    void bindFPRRReadyPriorityX(int id, int runtime, int quantum, int priority) {
        this.id.setText("p"+Integer.toString(id));
        this.processTime.setText(Integer.toString(runtime) + "s");
        this.quantum.setText("q=" + Integer.toString(quantum));
        if (priority == 1) {
            this.relativeBackground.setBackgroundColor(Color.CYAN);
        } else {
            if (priority == 2) {
                this.relativeBackground.setBackgroundColor(Color.MAGENTA);
            }
            if (priority == 3) {
                this.relativeBackground.setBackgroundColor(Color.BLUE);
            }
            if (priority == 4) {
                this.relativeBackground.setBackgroundColor(Color.YELLOW);
            }
        }
    }

    void bindFPRRFinished(int id, int runtime, int quantum, int priority) {
        this.id.setText("p"+Integer.toString(id));
        this.processTime.setText(Integer.toString(runtime) + "s");
        this.quantum.setText("q=" + Integer.toString(quantum));
        relativeBackground.setBackgroundColor(Color.GREEN);
        if (priority == 1) {
            this.id.setBackgroundColor(Color.CYAN);
            this.processTime.setBackgroundColor(Color.CYAN);
            this.quantum.setBackgroundColor(Color.CYAN);
        } else {
            if (priority == 2) {
                this.id.setBackgroundColor(Color.MAGENTA);
                this.processTime.setBackgroundColor(Color.MAGENTA);
                this.quantum.setBackgroundColor(Color.MAGENTA);
            }
            if (priority == 3) {
                this.id.setBackgroundColor(Color.BLUE);
                this.processTime.setBackgroundColor(Color.BLUE);
                this.quantum.setBackgroundColor(Color.BLUE);
            }
            if (priority == 4) {
                this.id.setBackgroundColor(Color.YELLOW);
                this.processTime.setBackgroundColor(Color.YELLOW);
                this.quantum.setBackgroundColor(Color.YELLOW);
            }
        }
    }

    void bindFPRRRunning(int id, int processTime, int priority, int quantum) {
        this.id.setText("p"+Integer.toString(id));
        this.processTime.setText(Integer.toString(processTime) + "s");
        this.quantum.setText("q=" + Integer.toString(quantum));
        if (quantum < 1) {
            relativeBackground.setBackgroundColor(Color.BLACK);
            this.id.setTextColor(Color.WHITE);
            this.id.setText("c" + id + " Open");
        }
        if (priority == 1) {
            this.relativeBackground.setBackgroundColor(Color.CYAN);
        } else {
            if (priority == 2) {
                this.relativeBackground.setBackgroundColor(Color.MAGENTA);
            }
            if (priority == 3) {
                this.relativeBackground.setBackgroundColor(Color.BLUE);
            }
            if (priority == 4) {
                this.relativeBackground.setBackgroundColor(Color.YELLOW);
            }
        }
    }

    void bindLTGReady(int id, int processTime, int deadline) {
        this.id.setText("p"+Integer.toString(id));
        this.processTime.setText(Integer.toString(processTime)+"s");
        this.deadline.setText("d="+Integer.toString(deadline));
        relativeBackground.setBackgroundColor(Color.YELLOW);
    }

    void bindLTGRunning(int id, int runtime, int deadline) {
        this.id.setText("p"+Integer.toString(id));
        this.processTime.setText(Integer.toString(runtime)+"s");
        relativeBackground.setBackgroundColor(Color.GREEN);
        if (deadline == 0) {
            relativeBackground.setBackgroundColor(Color.BLACK);
            this.id.setTextColor(Color.WHITE);
            this.id.setText("c" + id + " Open");
        }

    }

    void bindLTGFinisheNAborted(int id, int runtime, int deadline) {
        this.id.setText("p"+Integer.toString(id));
        this.processTime.setText(Integer.toString(runtime)+"s");
        this.deadline.setText("d=" + Integer.toString(deadline));
        if (runtime == 0) {
            relativeBackground.setBackgroundColor(Color.GREEN);
        }
        if (deadline == 0) {
            relativeBackground.setBackgroundColor(Color.RED);
        }
    }

    void bindIBA(int id, int processTime, int deadline, int startTime, int endTime) {
        this.id.setText(id);
        this.processTime.setText(processTime);
        this.deadline.setText(deadline);
        this.startTime.setText(startTime);
        this.endTime.setText(endTime);
    }

}
