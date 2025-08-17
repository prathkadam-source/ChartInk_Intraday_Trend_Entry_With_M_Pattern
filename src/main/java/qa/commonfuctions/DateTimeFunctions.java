package qa.commonfuctions;

import qa.utils.ReportUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class DateTimeFunctions {

    public static boolean compare_Date_Time(String currentAlertDateStr1, String prevAlertDateStr2) {

        ReportUtil.report(true, "INFO", "-- Function -- Starting -- Compare_Date_Time function", "");

        boolean status = Boolean.parseBoolean(null);
        String dateStr1 = currentAlertDateStr1;     // Format "Tue Jul 8 2025, 1:00 pm";
        String dateStr2 = prevAlertDateStr2;        //"Tue Jul 8 2025, 3:30 pm";

        try {
            //Format date to "EEE MMM dd yyyy, hh:mm a" before validation
            dateStr1 = DateTimeFunctions.update_Date_Time_Format(dateStr1);
            dateStr2 = DateTimeFunctions.update_Date_Time_Format(dateStr2);

            // Define the formatter for this format
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd yyyy, hh:mm a", Locale.ENGLISH);

            // Parse to LocalDateTime
            LocalDateTime dateTime1 = LocalDateTime.parse(dateStr1, formatter);
            LocalDateTime dateTime2 = LocalDateTime.parse(dateStr2, formatter);

            // Compare
            if (dateTime1.isAfter(dateTime2)) {
                System.out.println("dateTime1 is after dateTime2");
                ReportUtil.report(true, "INFO", "dateTime1 is after dateTime2 : ", "dateTime1 :" + dateTime1 + " ,dateTime2: " + dateTime2);
                status = true;
            } else if (dateTime1.isEqual(dateTime2)) {
                System.out.println("dateTime1 is equals to dateTime2");
                ReportUtil.report(true, "INFO", "dateTime1 is equals dateTime2 : ", "dateTime1 :" + dateTime1 + " ,dateTime2: " + dateTime2);
                status = false;
            } else if (dateTime1.isBefore(dateTime2)) {
                System.out.println("dateTime1 is before dateTime2");
                ReportUtil.report(true, "INFO", "dateTime1 is before dateTime2 : ", "dateTime1 :" + dateTime1 + " ,dateTime2: " + dateTime2);
//                status = true;
            }

        } catch (Exception e) {

            System.out.println("Compare_Date_Time: " + e.getMessage());
            ReportUtil.report(false, "FAIL", "Compare dates, ", e.getMessage());
        }

        ReportUtil.report(true, "INFO", "-- Function -- Ending -- Compare_Date_Time function", "");

        return status;
    }

    // Output Example: "Tue Jul 8 2025"
    public static String get_Now_Date_And_Day_In_Chartink_Time_Format() {
        ReportUtil.report(true, "INFO", "-- Function -- Starting -- Get_Now_Date_And_Day_In_Chartink_Time_Format function", "");
        String formattedDate = "";

        try {
            LocalDate today = LocalDate.now();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM d yyyy", Locale.ENGLISH);
            formattedDate = today.format(formatter);
            System.out.println(formattedDate);

        }    catch (Exception e) {

            System.out.println("Error: " + e.getMessage());
            ReportUtil.report(false, "FAIL", "Error, ", e.getMessage());
        }

        ReportUtil.report(true, "INFO", "-- Function -- Ending -- Compare_Date_Time function", "");

        return formattedDate;
    }

    public static String update_Date_Time_Format(String date_String) {
        ReportUtil.report(true, "INFO", "-- Function -- Starting -- update_Date_Time_Format function, ", "Format : EEE MMM dd yyyy, hh:mm a");

        String formattedDate = date_String;

        try {

            // Append zero before hour ,if hour is single digit
            String alert_Hour = formattedDate.split(", ")[1].split(":")[0];
            if (alert_Hour.length()== 1) {
                formattedDate = formattedDate.replace(alert_Hour + ":", "0" + alert_Hour + ":" );
            }

            // Append zero before Date ,if date is single digit
            String alert_Day = date_String.split(" ")[2];
            if (alert_Day.length()== 1) {
                formattedDate = formattedDate.replace(" "+ alert_Day + " ", " 0" + alert_Day + " " );
            }

            ReportUtil.report(true, "PASS", "Formatted date : ", formattedDate);

        }    catch (Exception e) {

            System.out.println("Error: " + e.getMessage());
            ReportUtil.report(false, "FAIL", "Error, ", e.getMessage());
        }

        ReportUtil.report(true, "INFO", "-- Function -- Ending -- update_Date_Time_Format function", "");

        return formattedDate;
    }

    public static void start_For_Loop_At_Specific_Time (LocalTime start_Time) {

        ReportUtil.report(true, "INFO", "-- Function -- Starting -- start_For_Loop_At_Specific_Time function, ", start_Time.toString() );

        LocalTime targetTime = start_Time;     //LocalTime.of(9, 15, 5);
        LocalTime now = LocalTime.now();

        // If the target time is in the past for today, you might want to skip or handle that
        if (now.isAfter(targetTime)) {
            System.out.println("It's already past the target time: " + targetTime);
            return;
        }

        // Calculate milliseconds to wait
        long millisToWait = ChronoUnit.MILLIS.between(now, targetTime);
        System.out.println("Waiting until " + targetTime + " to start...");

        try {
            Thread.sleep(millisToWait);
        } catch (InterruptedException e) {
            System.out.println("Wait interrupted: " + e.getMessage());
            System.out.println("Error: " + e.getMessage());
            ReportUtil.report(false, "FAIL", "Error, ", e.getMessage());
        }

        ReportUtil.report(true, "INFO", "-- Function -- Ending -- start_For_Loop_At_Specific_Time function", "");

    }

    //To ensure your Java for loop starts at the next multiple of 5 minutes 5 seconds, such as 9:25:05, even if the program is started at 9:23:00
    public static void loop_At_Precise_Time_Intervals () {

        ReportUtil.report(true, "INFO", "-- Function -- Starting -- loop_At_Precise_Time_Intervals function, ","For loop starts at the next multiple of 5 minutes 5 seconds, such as 9:25:05" );

        // Current time
        LocalDateTime now = LocalDateTime.now();

        // Calculate the next time that matches the pattern: mm % 5 == 0 and second == 5
        int minute = now.getMinute();
        int addMins = 5 - (minute % 5); // to get next multiple of 5
        LocalDateTime nextRunTime = now.withSecond(25).withNano(0).plusMinutes(addMins);
        //nextRunTime = nextRunTime.plusSeconds(5);

        long millisToWait = ChronoUnit.MILLIS.between(now, nextRunTime);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        System.out.println("Current Time: " + now.format(formatter));
        System.out.println("Next Execution Time: " + nextRunTime.format(formatter));
        System.out.println("Waiting for " + millisToWait / 1000 + " seconds...");
        ReportUtil.report(true, "INFO", "Waiting for " + millisToWait / 1000 + " seconds...", "");
        try {
            Thread.sleep(millisToWait);
        } catch (InterruptedException e) {
            System.out.println("Interrupted while waiting: " + e.getMessage());
            System.out.println("Error: " + e.getMessage());
            ReportUtil.report(false, "FAIL", "Error, ", e.getMessage());
        }

        ReportUtil.report(true, "INFO", "-- Function -- Ending -- loop_At_Precise_Time_Intervals function", "");

    }

}
