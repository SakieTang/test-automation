package ai.tact.qa.automation.utils.dataobjects;

import ai.tact.qa.automation.testcomponents.mobile.TactTimer.AndroidTimerPage;

import com.paypal.selion.platform.grid.Grid;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;

enum IsAMPM {
    AM, PM
}

public class AndroidClock {
    private static int hours;
    private static int mins;
    private static IsAMPM isAMPM;


    public AndroidClock(){ }

    private static void convertTime (String time) {
        String ampm = time.split(" ")[1];
        if (ampm.equalsIgnoreCase("am")) {
            isAMPM = IsAMPM.AM;
        }
        else if (ampm.equalsIgnoreCase("pm")) {
            isAMPM = IsAMPM.PM;
        }

        String hour = (time.split(" ")[0]).split(":")[0];
        String min = (time.split(" ")[0]).split(":")[1];

        hours = Integer.parseInt(hour);
        mins = Integer.parseInt(min);

        System.out.println( time + " ==> " + hours + ":" + mins + " " + isAMPM);
    }

    public static void changeDigitalClock( String time ){
        AndroidTimerPage androidTimerPage = new AndroidTimerPage();

        convertTime(time);

        //Make sure in digital Mode clock
        if (Grid.driver().findElementsByXPath(androidTimerPage.getDigitalClockModeTitleLabel().getLocator()).size() ==0 ){
            androidTimerPage.getSwitchClockModeButton().tap();
        }
        WebDriverWaitUtils.waitUntilElementIsVisible(androidTimerPage.getDigitalClockModeTitleLabel());

        //hours
        androidTimerPage.getDigitalHoursTextField().clearText();
        androidTimerPage.getDigitalHoursTextField().sendKeys(Integer.toString(hours));
        //mins
        androidTimerPage.getDigitalMinsTextField().clearText();
        androidTimerPage.getDigitalMinsTextField().sendKeys(Integer.toString(mins));
        //am,pm
        androidTimerPage.getDigitalampmButton().tap(androidTimerPage.getDigitalPMButton());
        if (isAMPM.equals(IsAMPM.AM)) {
            androidTimerPage.getDigitalAMButton().tap(androidTimerPage.getDigitalClockModeTitleLabel());
        }
        else {
            androidTimerPage.getDigitalPMButton().tap(androidTimerPage.getDigitalClockModeTitleLabel());
        }

        androidTimerPage.getTimerOKButton().tap();
    }

    public static void changeAnalogClock( String time ){
        AndroidTimerPage androidTimerPage = new AndroidTimerPage();
        convertTime(time);

        //Make sure in analog Mode clock
        if (Grid.driver().findElementsByXPath(androidTimerPage.getDigitalClockModeTitleLabel().getLocator()).size() != 0)
        {
            androidTimerPage.getSwitchClockModeButton().tap();
        }
        WebDriverWaitUtils.waitUntilElementIsVisible(androidTimerPage.getAnalogAMButton());

        //hours
//        List<WebElement> allElements = Grid.driver().findElementsByClassName(androidTimerPage.getAnalogHoursPickersButton().getLocator());
//        allElements.get(11).click();        //0:xx
//        allElements.get(hours-1).click();   //1==>0, 11==>10

        //"//android.widget.RadialTimePickerView.RadialPickerTouchHelper[@content-desc='number']"
        androidTimerPage.getAnalogHoursButton().tap();
        String hoursLoc = androidTimerPage.getAnalogPickerButton().getLocator().replaceAll("number", Integer.toString(hours));
        System.out.println("Hours loc : " + hoursLoc);
        Grid.driver().findElementByXPath(hoursLoc).click();

        //mins
        if (mins%5 != 0) {
            System.out.println("==> " + mins%5);
            sendTextToAnanlogMinsTextField();
        }
        else {
            System.out.println("==> " + mins%5);
            //"//android.widget.RadialTimePickerView.RadialPickerTouchHelper[@content-desc='number']"
            String minsLoc = androidTimerPage.getAnalogPickerButton().getLocator().replaceAll("number", Integer.toString(mins));
            System.out.println("Mins loc : " + minsLoc);
            Grid.driver().findElementByXPath(minsLoc).click();
        }

//        List<WebElement> allElements = Grid.driver().findElementsByClassName(androidTimerPage.getAnalogMinsPickersButton().getLocator());
//        allElements.get(45/5).click();  //:45 mins
//        allElements.get(0).click();     //:00

        //ampm
        if (isAMPM.equals(IsAMPM.AM)) {
            androidTimerPage.getAnalogAMButton().tap();
        }
        else {
            androidTimerPage.getAnalogPMButton().tap();
        }

        androidTimerPage.getTimerOKButton().tap();
    }

    public static void sendTextToAnanlogMinsTextField () {
        AndroidTimerPage androidTimerPage = new AndroidTimerPage();
        androidTimerPage.getAnalogMinsTextField().sendKeys(Integer.toString(mins));
    }

}
