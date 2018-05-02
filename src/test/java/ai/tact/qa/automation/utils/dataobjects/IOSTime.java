package ai.tact.qa.automation.utils.dataobjects;

import ai.tact.qa.automation.testcomponents.mobile.TactTimer.IOSDateTimePage;
import ai.tact.qa.automation.utils.DriverUtils;

import com.paypal.selion.platform.grid.Grid;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;
import org.openqa.selenium.WebElement;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.time.Month;

public class IOSTime {

    private static String expectDayMonth;   //Mar 2
    private static String expectDay;
    private static Month expectMonth;
    private static String expectYear;
    private static String expectHours;
    private static String expectMins;
    private static IsAMPM expectIsAMPM;

    private static String displayDayMonth;
    private static Month displayMonth;
    private static String displayDate;
    private static String displayYear;


    public IOSTime(){}

    //select date and time
    public static void changeDateAndTime (String date, String expectTime){
        IOSDateTimePage iOSDateTimePage = new IOSDateTimePage();
        WebDriverWaitUtils.waitUntilElementIsVisible(iOSDateTimePage.getDateTimeDoneLabel());

        displayDayMonth();

        if (date.equalsIgnoreCase("today")) {
            getCurrentDayMonth();
        }
        else {
            convertDayMonth(date);
        }
        convertTime(expectTime);

        List<WebElement> allElements = Grid.driver().findElementsByClassName(iOSDateTimePage.getDateTimePicker().getLocator());
        //select day and month
        int loop = expectMonth.getValue() - displayMonth.getValue();
        String sendKey;
        int month = displayMonth.getValue();
        if (loop > 0)
        {
            while (loop != 0) {
                sendKey = getMonthForInt(month).substring(0, 3) + " " + 28;
                System.out.println("loop ==> " + loop + "/n allElements.get(0)  ==> " + allElements.get(0).getText());
                allElements.get(0).sendKeys(sendKey);
                month++;
                loop--;
            }
        }
        if (loop < 0)
        {
            while (loop!=0) {
                sendKey = getMonthForInt(month).substring(0, 3) + " " + 1;
                System.out.println("month ==> " + month + "//loop ==> " + loop + "/n allElements.get(0)  ==> " + allElements.get(0).getText());
                allElements.get(0).sendKeys(sendKey);
                month--;
                loop++;
            }
        }
        System.out.println("allElements.get(0) ==> " + allElements.get(0).getText());
        allElements.get(0).sendKeys(expectDayMonth);
        //select Time
        System.out.println("allElements.get(1) ==> " + allElements.get(1).getText());
        allElements.get(1).sendKeys(expectHours);
        System.out.println("allElements.get(2) ==> " + allElements.get(2).getText());
        allElements.get(2).sendKeys(expectMins);
        System.out.println("allElements.get(3) ==> " + allElements.get(3).getText());
        allElements.get(3).sendKeys(expectIsAMPM.toString());

        System.out.println(expectDayMonth + " " + expectHours + " " + expectMins + " " + expectIsAMPM );
        DriverUtils.sleep(2);

        WebDriverWaitUtils.waitUntilElementIsVisible(iOSDateTimePage.getDateTimeDoneButton());
        Grid.driver().findElementByXPath(iOSDateTimePage.getDateTimeDoneButton().getLocator()).click();
    }

    public static void changeDate (String date){
        IOSDateTimePage iOSDateTimePage = new IOSDateTimePage();
        WebDriverWaitUtils.waitUntilElementIsVisible(iOSDateTimePage.getDateTimeDoneLabel());

        displayDayMonth();
        if (date.equalsIgnoreCase("today")) {
            getCurrentDayMonth();
        }
        else {
            convertDayMonth(date);
        }

        List<WebElement> allElements = Grid.driver().findElementsByClassName(iOSDateTimePage.getDateTimePicker().getLocator());
        //select day and month
        int loop = expectMonth.getValue() - displayMonth.getValue();
        System.out.println("loop " + loop);
        String sendKey;
        int month = displayMonth.getValue();
        //select month
        while (loop > 0) {
            sendKey = getMonthForInt(month);
            System.out.println("send key " + sendKey);
            allElements.get(1).sendKeys(sendKey);
            month++;
            loop--;
        }
        while (loop < 0) {
            sendKey = getMonthForInt(month).substring(0, 3) + " " + 1;
            allElements.get(1).sendKeys(sendKey);
            month--;
            loop++;
        }
        allElements.get(0).sendKeys(expectDay);
        System.out.println("expectDay : " + expectDay + " ==== expectMonth : " + expectMonth );
        DriverUtils.sleep(2);

        System.out.println(expectDayMonth );

        WebDriverWaitUtils.waitUntilElementIsVisible(iOSDateTimePage.getDateTimeDoneButton());
        DriverUtils.sleep(2);
        iOSDateTimePage.getDateTimeDoneButton().tap();
        if (Grid.driver().findElementsByXPath(iOSDateTimePage.getDateTimeDoneButton().getLocator()).size() != 0) {
            iOSDateTimePage.getDateTimeDoneButton().tap();
        }
    }

    private static void currentDate(){

        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();

        System.out.println("date : " + date);

        displayMonth = Month.of( AndroidDate.shortNameToNumber(new SimpleDateFormat("MMM").format(date)) );
        displayDate = new SimpleDateFormat("dd").format(date);
        displayYear = new SimpleDateFormat("yyyy").format(date);

        System.out.println("current date " + displayMonth.getValue() + " " + displayDate + " " + displayYear);
    }

    public static String getCurrentDate(){

        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();

        System.out.println("date : " + date);

        displayMonth = Month.of( AndroidDate.shortNameToNumber(new SimpleDateFormat("MMM").format(date)) );
        displayDate = new SimpleDateFormat("dd").format(date);
        displayYear = new SimpleDateFormat("yyyy").format(date);

        return displayMonth.getValue() + "/" + displayDate + "/" + displayYear;
    }

    private static void convertTime(String expectTime){
        //12:25 am
        expectHours = expectTime.split(" ")[0].split(":")[0];
        expectMins = expectTime.split(" ")[0].split(":")[1];
        expectIsAMPM = IsAMPM.valueOf(expectTime.split(" ")[1].toUpperCase());
    }

    //Sat, Sep 1
    private static void displayDayMonth(){
        IOSDateTimePage iOSDateTimePage = new IOSDateTimePage();
        List<WebElement> allElements = Grid.driver().findElementsByClassName(iOSDateTimePage.getDateTimePicker().getLocator());
        String date = allElements.get(0).getText();
        System.out.println("display date " + date);
        if (date.equalsIgnoreCase("Today")) {

            displayYear = DriverUtils.currentDateInfo("year");
            displayDate = DriverUtils.currentDateInfo("date");
            displayMonth = Month.of( AndroidDate.shortNameToNumber(DriverUtils.currentDateInfo("month")) );

        }
        else if (date.contains(",")) {  //xx mmm dd, hh
            displayDayMonth = date.split(", ")[1];
            displayMonth = Month.of( AndroidDate.shortNameToNumber(displayDayMonth.split(" ")[0]) );
            displayDate = displayDayMonth.split(" ")[1];

            System.out.println(displayDate + "/" + displayMonth);
        }
        else {
            displayDate = date.split(" ")[1];   //xxx dd
            String month = allElements.get(1).getText();
            System.out.println("display month " + month);
            displayMonth = Month.of( AndroidDate.shortNameToNumber(month.substring(0,3)) );

            displayYear = allElements.get(2).getText();

            System.out.println(displayDate + "/" + displayMonth + "/" + displayYear);
        }
    }

    //10/10/2018   | w/o      | Jan 1, 2019
    private static void convertDayMonth(String date){
        if(date.contains("/")){
            expectMonth = Month.of( (Integer.parseInt(date.split("/")[0])) );
            expectDay = date.split("/")[1];
            expectYear = date.split("/")[2];
            expectDayMonth = expectMonth + " " + expectDay;
        }
        else {
            expectDayMonth = date.split(", ")[0];
            expectMonth = Month.of( AndroidDate.shortNameToNumber(expectDayMonth.split(" ")[0]) );
            expectDay = expectDayMonth.split(" ")[1];
            expectYear = date.split(", ")[1];
        }
        System.out.println("expect date " + expectMonth +  " " + expectDay + " " + expectYear);
    }

    private static void getCurrentDayMonth(){
        Date date = DriverUtils.currentDate();

        expectYear = new SimpleDateFormat("yyyy").format(date);
        expectDay = new SimpleDateFormat("dd").format(date);
        expectMonth = Month.of( AndroidDate.shortNameToNumber(new SimpleDateFormat("MMM").format(date)) );
    }

    private static String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 1 && num <= 12)
        {
            month = months[num-1];
        }
        return month;
    }

    public static void main(String[] arg){
        IsAMPM isAMPM = IsAMPM.AM;
        System.out.println( isAMPM + " >>>> " + isAMPM.toString());
        convertDayMonth("10/12/2019");

        System.out.println(System.getProperty("user.dir"));

    }
}
