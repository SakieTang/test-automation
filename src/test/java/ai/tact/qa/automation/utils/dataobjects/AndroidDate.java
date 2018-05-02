package ai.tact.qa.automation.utils.dataobjects;

import ai.tact.qa.automation.testcomponents.mobile.TactTimer.AndroidDatePage;
import ai.tact.qa.automation.utils.DriverUtils;

import com.paypal.selion.platform.grid.Grid;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

public class AndroidDate {

    private static AndroidDatePage androidDatePage = new AndroidDatePage();

    private static Month currentMonth = Month.of( shortNameToNumber(DriverUtils.currentDateInfo("month")) );
    private static int currentDate = Integer.parseInt(DriverUtils.currentDateInfo("date"));
    private static int currentYear = Integer.parseInt(DriverUtils.currentDateInfo("year"));

    private static Month displayMonth;
    private static int displayDay;
    private static int displayYear;

    private static Month expectMonth;
    private static int expectDay;
    private static int expectYear;

    final static String DATE_FORMAT1 = "MMM dd, yyyy";
    final static String DATE_FORMAT2 = "MM/dd/yyyy";

    public AndroidDate(){}

    /**
     * //Mar 29, 2018 |  03/29/2018
     * @param date
     */
    public static void changeDate(String date){

        displayDate();
        splitExpectedDate(date);

        AndroidDate androidDate = new AndroidDate();

        //select Year
        if (androidDate.displayYear!=expectYear && androidDate.displayYear-2 <= expectYear && expectYear <= androidDate.displayYear+4)
        {
            androidDatePage.getDisplayYesrButton().tap();
            //yearElementsButton ==> //android.widget.TextView[@text='selectedYear']
            String yearLoc = androidDatePage.getYearElementsButton().getLocator().replaceAll("selectedYear", Integer.toString(expectYear));
            WebDriverWaitUtils.waitUntilElementIsVisible(yearLoc);
            Grid.driver().findElementByXPath(yearLoc).click();
            WebDriverWaitUtils.waitUntilElementIsVisible(androidDatePage.getDatePickerHeaderTitleLabel());
        }
        //select Month
        int difMonth = expectMonth.getValue() - androidDate.displayMonth.getValue();
        System.out.println("************  dif " + difMonth);
        if (difMonth < 0) {            //click < icon
            int clickTime = -difMonth;
            while (clickTime != 0) {
                androidDatePage.getPreviousMonthButton().tap();
                clickTime--;
            }
        }
        else if (difMonth > 0) {     //click > icon
            int clickTime = difMonth;
            while (clickTime != 0) {
                androidDatePage.getNextMonthButton().tap();
                clickTime--;
            }
        }
        //select Day     selectDayButton ==> //android.view.View[@text='selectedDay']
        String dayLoc = androidDatePage.getSelectDayButton().getLocator().replaceAll("selectedDay", Integer.toString(expectDay));
        Grid.driver().findElementByXPath(dayLoc).click();

        System.out.println("Selected " + date + " is done.");
        DriverUtils.sleep(1);
        androidDatePage.getDateOKButton().tap();

    }

    private static void displayDate(){
        AndroidDate androidDate = new AndroidDate();
        androidDate.displayYear = Integer.parseInt(androidDatePage.getDisplayYesrButton().getValue());
        //Sat, Mar 10  ==> Mar 10
        String dayMonth = androidDatePage.getDisplayMonthDayLabel().getValue().split(", ")[1];
        displayDay = Integer.parseInt(dayMonth.split(" ")[1]);
        displayMonth = Month.of( shortNameToNumber((dayMonth.split(" ")[0])) );

        System.out.println("Display date " + displayMonth + " " + displayDay + " " + displayYear);
    }

    //Mar 29, 2018 |  03/29/2018
    private static void splitExpectedDate(String date){
        if (date.contains("/")) {    //03/29/2018
            expectMonth = Month.of( Integer.parseInt(date.split("/")[0]) );

            expectDay = Integer.parseInt(date.split("/")[1]);

            expectYear = Integer.parseInt(date.split("/")[2]);
        }
        else {
            String dayMonth;       // Mar 29, 2018
            if (date.contains(", ")) {  //", "
                dayMonth = date.split(", ")[0];
                expectYear = Integer.parseInt(date.split(", ")[1]);
            } else { //","
                dayMonth = date.split(",")[0];
                expectYear = Integer.parseInt(date.split(",")[1]);
            }
            expectDay = Integer.parseInt(dayMonth.split(" ")[1]);
            expectMonth = Month.of( shortNameToNumber(dayMonth.split(" ")[0].substring(0,3)) );
        }
        System.out.println("expect date : " + expectMonth + " " + expectDay + " " + expectYear);
    }

    private static String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 1 && num <= 12 ) {
            month = months[num-1];
        }
        return month;
    }

    public static boolean isDateValid(String date){
        if (date.contains("/")) {
            try {
                DateFormat df = new SimpleDateFormat(DATE_FORMAT2);
                df.setLenient(false);
                df.parse(date);
                return true;
            }
            catch (ParseException e){
                return false;
            }
        }
        else {
            try {
                DateFormat df = new SimpleDateFormat(DATE_FORMAT1);
                df.setLenient(false);
                df.parse(date);
                return true;
            }
            catch (ParseException e) {
                return false;
            }
        }
    }

    public static int shortNameToNumber(String shortName) {
        shortName = shortName.toLowerCase();
        switch (shortName) {
            case "jan": return 1;
            case "feb": return 2;
            case "mar": return 3;
            case "apr": return 4;
            case "may": return 5;
            case "jun": return 6;
            case "jul": return 7;
            case "aug": return 8;
            case "sep": return 9;
            case "oct": return 10;
            case "nov": return 11;
            case "dec": return 12;
            default: return 1;
        }
    }

    public static void main(String[] args){

        splitExpectedDate("10/2/2018");
        System.out.println(Month.of( Integer.parseInt("10") ));

        int i = 1-3;
        System.out.println(1-3 + " " + (-i) );


        //Mar 29, 2018 |  03/29/2018
        System.out.println("valid " + isDateValid("03/29/2018"));
        System.out.println("valid " + isDateValid("Mar 29, 2018"));
        System.out.println("valid " + isDateValid("028/2./2"));
        System.out.println("valid " + isDateValid("Mar 29,2018"));


        String dateMonth = DriverUtils.currentDateInfo("mm") + "/" + DriverUtils.currentDateInfo("date");
        System.out.println("dateMonth ==> " + dateMonth);

        String currentUser;
        if(DriverUtils.isIOS()) {
            currentUser = "automation.tactSF.s@gmail.com";
        }
        else {
            currentUser = "automation.tactAndrSF.s@gmail.com";
        }

        System.out.println(currentUser + " === " + currentUser.split("\\.").length);

        //currentUser = currentUser.split("\\.")[1];

        String s = currentUser.split("@")[1].split("\\.")[0];

        System.out.println("user " + currentUser + " ======= " + s);

        s = "12345678901234567890123456789012345678abcdefg";

        String s1 = s.substring(0,38);
        System.out.println( s.length() + " s : " + s1 + "==" + s1.length());

        //Month.valueOf(new SimpleDateFormat("MMM").format(date));
        Month m = Month.of( 04 );
        System.out.println(" apr ==> " + m.getDisplayName(TextStyle.SHORT, Locale.ENGLISH));

        m = Month.valueOf("December".toUpperCase());
        System.out.println("December ==> " + (Month.DECEMBER.getValue() - Month.JANUARY.getValue()));

    }

}
