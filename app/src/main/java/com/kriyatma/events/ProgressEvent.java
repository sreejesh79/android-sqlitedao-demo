package com.kriyatma.events;

/**
 * Created by sreejeshpillai on 26/05/15.
 */
public class ProgressEvent extends Event {

    public static final String PROGRESS = "progress";

    private  int progress = 0;
    private int total = 0;

    public ProgressEvent(String type,int progress,int total){
        super(type);
        this.progress = progress;
        this.total = total;
    }

    public void setProgress(int value){
        progress = value;
    }

    public int getProgress() {
        return progress;
    }

    public int getTotal(){
        return total;
    }

}
