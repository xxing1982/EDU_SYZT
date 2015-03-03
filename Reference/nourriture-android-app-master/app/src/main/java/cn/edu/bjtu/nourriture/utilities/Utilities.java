package cn.edu.bjtu.nourriture.utilities;

import java.util.Date;

/**
 * Created by Pavel Procházka on 04/01/15.
 */
public class Utilities {

    /**
     *  Count the elapsed time between now and moment created
     */
    public static String convertDateToTimeElapsedString(Date startDate, Date endDate){

        String timeElapsed = "";

        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        /*System.out.println("startDate : " + startDate);
        System.out.println("endDate : "+ endDate);
        System.out.println("different : " + different);*/

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        if(elapsedDays != 0){
            if (elapsedDays == 1)
                timeElapsed += elapsedDays + " day ago";
            else
                timeElapsed += elapsedDays + " days ago";
        }
        else if (elapsedDays == 0 && elapsedHours != 0 ){
            if (elapsedHours == 1)
                timeElapsed += elapsedHours + " hour ago";
            else
                timeElapsed += elapsedHours + " hours ago";
        }
        else if (elapsedHours == 0 && elapsedMinutes != 0 ){
            if (elapsedMinutes == 1)
                timeElapsed += elapsedMinutes + " minute ago";
            else
                timeElapsed += elapsedMinutes + " minutes ago";
        }
        else if (elapsedMinutes == 0){
            timeElapsed += "A few seconds ago";
        }

        return timeElapsed;
    }

}
