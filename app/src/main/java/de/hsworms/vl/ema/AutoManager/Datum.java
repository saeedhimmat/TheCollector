package de.hsworms.vl.ema.AutoManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Datum {

        private int tag;
        private int month;
        private int year;

        public Datum(int mTag, int mMonth, int mYear) {
            this.month = mMonth;
            this.tag = mTag;
            this.year = mYear;

        }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }



    public String GetTime(int tag,int month,int year){
            return (String.valueOf(tag)+"."+String.valueOf(month+1)+"."+String.valueOf(year));

    }



    }


