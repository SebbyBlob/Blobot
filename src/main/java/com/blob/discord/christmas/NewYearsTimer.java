package com.blob.discord.christmas;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;

public class NewYearsTimer {

    public void timerTask() throws ParseException {
        DateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        dateFormatter.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
        Date date = dateFormatter.parse("00:00:00 01-01-2021");

        Timer timer = new Timer();
        timer.schedule(new NewYearsTaskRunner(), date);
    }

}
