package com.ctp.swissknife;

/**
 * Created by CTP on 1/31/2017.
 */

public class Stopwatch {

    private long startTime = 0;
    private boolean running = false;
    private long current =0;


    public void start() {
        this.startTime = System.currentTimeMillis();
        this.running = true;
    }


    public void stop() {
        current = System.currentTimeMillis() - startTime;
        this.running = false;
    }

    public void resume() {
        if(current==0)
            start();
        this.running = true;
        this.startTime = System.currentTimeMillis() - current;
    }

    public void reset(){
        this.running=false;
        this.current=0;
    }

    // elaspsed time in milliseconds
    public long getElapsedTime() {
        if (running) {
            return (((System.currentTimeMillis() - startTime))/10)%100;
        }
        return (((System.currentTimeMillis() - startTime))/10)%100;
    }


    // elaspsed time in seconds
    public long getElapsedTimeSecs() {
        if (running) {
            return ((System.currentTimeMillis() - startTime) / 1000)%60;
        }
        return ((System.currentTimeMillis() - startTime) / 1000)%60;
    }

    public long getElapsedTimeMin() {

        if (running) {
            return (((System.currentTimeMillis() - startTime) / 1000) / 60 ) % 60;
        }
        return (((System.currentTimeMillis() - startTime) / 1000) / 60 ) % 60;
    }

    //elaspsed time in hours
    public long getElapsedTimeHour() {

        if (running) {
            return ((((System.currentTimeMillis() - startTime) / 1000) / 60 ) / 60);
        }
        return ((((System.currentTimeMillis() - startTime) / 1000) / 60 ) / 60);
    }

    public String toString() {
        String hour,min,sec,ms;
        if(getElapsedTimeHour()<10)
            hour="0"+getElapsedTimeHour();
        else
            hour=""+getElapsedTimeHour();
        if(getElapsedTimeMin()<10)
            min="0"+getElapsedTimeMin();
        else
            min=""+getElapsedTimeMin();
        if(getElapsedTimeSecs()<10)
            sec="0"+getElapsedTimeSecs();
        else
            sec=""+getElapsedTimeSecs();
        if(getElapsedTime()<10)
            ms="0"+getElapsedTime();
        else
            ms=""+getElapsedTime();

        return  hour+ ":" + min + ":"
                + sec + ":" + ms;
    }

    }


