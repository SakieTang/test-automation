package ai.tact.qa.automation.utils;

import ai.tact.qa.automation.testcomponents.mobile.TactWelcomePage;

import ai.tact.qa.automation.utils.dataobjects.FieldDataType;
import com.paypal.selion.internal.platform.grid.WebDriverPlatform;
import com.paypal.selion.platform.asserts.SeLionAsserts;
import com.paypal.selion.platform.grid.Grid;
import com.paypal.selion.platform.mobile.elements.MobileElement;
import com.paypal.selion.platform.mobile.elements.MobileTextField;
import io.appium.java_client.AppiumDriver;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.Capabilities;

import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class DriverUtils {

    /**
     * check the current UserInformation environment is iOS
     * @return boolean
     */
    public static boolean isIOS() {
        TactWelcomePage tactWelcomePage = new TactWelcomePage();
        if (tactWelcomePage.getPlatform().equals(WebDriverPlatform.IOS)) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * check the current UserInformation environment is Android
     * @return boolean
     */
    public static boolean isAndroid() {
        TactWelcomePage tactWelcomePage = new TactWelcomePage();
        if (tactWelcomePage.getPlatform().equals(WebDriverPlatform.ANDROID)) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * get the current UserInformation environment type
     * @return String
     */
    public static String getCurrentMobileOSType() {
        TactWelcomePage tactWelcomePage = new TactWelcomePage();
        if (tactWelcomePage.getPlatform().equals(WebDriverPlatform.ANDROID)) {
            return WebDriverPlatform.ANDROID.toString();
        }
        else {
            return WebDriverPlatform.IOS.toString();
        }
    }

    /**
     * sleep for * sec
     * @param sec
     */
    public static void sleep(double sec) {
        try {
            Thread.sleep((long) (sec*1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * hide the Android keyboard
     */
    public static void hideAndroidKeyboard(){
        AppiumDriver driver = (AppiumDriver)Grid.driver();
        driver.hideKeyboard();
        driver.swipe(201,290,201,264,10);
    }

    public static void workaroundHideKeyboard() {
        AppiumDriver driver = (AppiumDriver) Grid.driver();
        int x = driver.manage().window().getSize().width;
        int y = driver.manage().window().getSize().height;
        driver.swipe(x/2, y/3, x/2, y/2, 0);
        sleep(0.5);
    }

    /**
     * hide the iOS keyboard
     */
    public static void hideIOSKeyboard() {
        String cmd = "tell application \"Simulator\"\n" +
                        "activate\n" +
                     "end tell";
        String[] args1 = {"osascript", "-e", cmd};
//        runCommand(args1);
        System.out.println("finished the 1st cmd : " + cmd);
        sleep(10);

        cmd = //"activate application \"Simulator\"\n" +
                    "tell app \"System Events\"\n" +
                        "to keystroke \"k\" using {shift down, command down}\n" +
                    "delay (random number from 0.5 to 5)\n" +
                    "end tell";
        String [] args2 = {"osascript", "-e", cmd};
//        runCommand(args2);
        try {
            Process process = Runtime.getRuntime().exec(args2);
        } catch (IOException e) {
            e.printStackTrace();
        }



        System.out.println("finished the 2nd cd : " + cmd);

//        Process process = null;
//        try {
//            Process process = Runtime.getRuntime().exec(args);
//            process.waitFor();
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }

        sleep(3);
    }

    /**
     * For Android check whether the keyboard display or not
     * @return true/false
     */
    public static boolean isAndroidKeyboardDisplay () {
        boolean isDisplay = false;
        String result = null;
        try {
            Process p = Runtime.getRuntime().exec("adb shell dumpsys input_method | grep mInputShown | cut -d ' ' -f4 | cut -d '=' -f2" );
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            result = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (result.equals("true"))
        {
            isDisplay = true;
        }

        return isDisplay;
    }

    /**
     * convert the current context to Webview
     */
    public static void convertToWebviewDriver(){
        DriverUtils.sleep(5);
        System.out.println("start convert to Webview");

        Set<String> contexts = ((AppiumDriver)Grid.driver()).getContextHandles();

        if (contexts.size() == 1)
        {
            System.out.println("Does not get Webview, need to try one more time");
            contexts = ((AppiumDriver)Grid.driver()).getContextHandles();
        }

        //iOS_WEBVIEW_24440.1/ WEBVIEW_246622.1
        for (String context : contexts) {
            System.out.println("context : ==> " + context);
            if (!context.equals("NATIVE_APP"))
            {
                ((AppiumDriver)Grid.driver()).context(context);
                break;
            }
        }
        System.out.println("Current context is " + ((AppiumDriver) Grid.driver()).getContext());
    }

    /**
     * convert the current context to NATIVE_APP
     */
    public static void convertToNativeAPPDriver(){
        DriverUtils.sleep(2);
        System.out.println("start convert to NATIVE_APP");
        System.out.println("current context is " + ((AppiumDriver)Grid.driver()).getContext() );
        ((AppiumDriver) Grid.driver()).context("NATIVE_APP");

        String currentContext = ((AppiumDriver)Grid.driver()).getContext();
        System.out.println("current context is " + ((AppiumDriver)Grid.driver()).getContext() );
        SeLionAsserts.assertEquals( currentContext,"NATIVE_APP", "switch back to NATIVE_APP !!");
    }

    /**
     * get random number
     * int  4 bytes -2,147,483,648 to 2,147,483,647
     * @param origin
     * @param bound
     * @return random number
     */
    public static int getRandomNumber ( int origin, int bound ) {

        return ThreadLocalRandom.current().nextInt(origin,bound);
    }

    /**
     * get random long number
     * long 8 bytes -9,223,372,036,854,775,808 to +9,223,372,036,854,775,807
     * @param longLength
     * @return random long number
     */
    public static long getRandomLongNumberInGivenLength ( int longLength ) {

        long min = (long)Math.pow(10, (longLength-1));
        long bound = (long)Math.pow(10, longLength);
        long value = ThreadLocalRandom.current().nextLong(min, bound);

        return value;
    }

    /***
     * slideUp 1/4 screen
     */
    public static void slideUP (){
        AppiumDriver driver = (AppiumDriver) Grid.driver();
        int x = driver.manage().window().getSize().width;
        int y = driver.manage().window().getSize().height;
        driver.swipe(x/2, y/3*2, x/2, y/3*1, 0);
        sleep(0.5);
    }

    /***
     * slideDown 1/4 screen
     */
    public static void slideDown (){
        AppiumDriver driver = (AppiumDriver) Grid.driver();
        int x = driver.manage().window().getSize().width;
        int y = driver.manage().window().getSize().height;
        driver.swipe(x/2, y/3*1, x/2, y/3*2, 0);
        sleep(0.5);
    }
    /***
     * slideLeft 1/2 screen
     */
    public static void slideLeft ( ){
        AppiumDriver driver = (AppiumDriver) Grid.driver();
        int x = driver.manage().window().getSize().width;
        int y = driver.manage().window().getSize().height;
        driver.swipe(x/4*3, y/2, x/4*1, y/2, 0);
        sleep(0.5);
    }
    /***
     * slideRight 1/2 screen
     */
    public static void slideRight ( ){
        AppiumDriver driver = (AppiumDriver) Grid.driver();
        int x = driver.manage().window().getSize().width;
        int y = driver.manage().window().getSize().height;
        driver.swipe(x/4*1, y/2, x/4*3, y/2, 0);
        sleep(0.5);
    }

    /**
     * scroll to top
     * @return
     */
    public static void scrollToTop () {
        int loop = 0;
        while ( loop <= 3) {
            DriverUtils.slideDown();
            loop++;
        }
    }

    /**
     * scroll to bottom
     * @return
     */
    public static void scrollToBottom () {
        //滚到最下方
        int loop = 0;
        while ( loop <= 3) {
            DriverUtils.slideUP();
            loop++;
        }
        DriverUtils.sleep(1);
    }

    /**
     * scroll specific location
     * @param startx
     * @param starty
     * @param endx
     * @param endy
     * @return
     */
    public static void scrollTo ( int startx, int starty, int endx, int endy) {
        AppiumDriver driver = (AppiumDriver) Grid.driver();
        driver.swipe(startx, starty, endx, endy, 100);
    }

    /**
     * tap x y
     * @param x
     * @param y
     * @return
     */
    public static void tapXY ( int x, int y ) {
        AppiumDriver driver = (AppiumDriver) Grid.driver();
        driver.tap(1, x, y, 200);
        sleep(2);
    }

    //××××××××××//
    // Android //
    //××××××××××//
    /**
     * return previous page when in the webpage view (Android Only)
     * @param
     * @return
     */
    public static void tapAndroidHardwareBackBtn() {
        String command = "adb shell input keyevent 4";
        runCommand(new String[]{"bash", "-c", command});
        sleep(5);
    }

    //××××××××××//
    // Android //
    //××××××××××//
    /**
     * tap device HOME key button (Android Only)
     */
    public static void tapAndroidHardwareHomeBtn(){
        String command = "adb shell input keyevent KEYCODE_HOME";
        runCommand(command);
        sleep(5);
    }

    /**
     * run the android command without return value
     * @param command
     * @return Process
     */
    public static Process runCommand (String command) {
        Process process = null;

        try {
            process = Runtime.getRuntime().exec(command);
			process.waitFor();
		} catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return process;
    }

    /**
     * run the android command without return value
     * @param command
     * @return Process
     */
    public static Process runCommand (String[] command) {
        Process process = null;

        try {
            process = Runtime.getRuntime().exec(command);
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return process;
    }

    public static String getRunCommandReturn (String command) {
        Process process = null;
        Scanner s = null;

        try {
            process = Runtime.getRuntime().exec(command);
            s = new Scanner(process.getInputStream()).useDelimiter("\\n");
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return s.hasNext() ? s.next() : "";
    }

    public static String getRunCommandReturn (String[] command) {
        Process process = null;
        Scanner s = null;

        try {
            process = Runtime.getRuntime().exec(command);
            s = new Scanner(process.getInputStream()).useDelimiter("\\n");
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return s.hasNext() ? s.next() : "";
    }

    //××××××××××//
    // Android //
    //××××××××××//
    /**
     * clear the chrome app data (Android Only)
     */
    public static void clearChromeData(){
        String command = "adb shell pm clear com.android.chrome";
//        String line = null;

        String result = DriverUtils.getRunCommandReturn(command);
        if (result.equalsIgnoreCase("Success")) {
            System.out.println("Clear Chrome Data is done");
        }
        else {
            System.out.println("Clear Chrome Data is false");
        }
//
//        try {
//            Process process = DriverUtils.runCommand(command);
//            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            if ((line = reader.readLine()) != null && line.equalsIgnoreCase("Success")) {
//                System.out.println("Clear Chrome Data is done");
//            }
//            else {
//                System.out.println("Clear Chrome Data is false");
//            }
//        }
//        catch (IOException e) {
//            e.getMessage();
//        }
    }

    //××××××××××//
    // Android //
    //××××××××××//
    /**
     * reboot device
     */
    public static void rebootEmulator(){
        String command = "adb root";

        String result = DriverUtils.getRunCommandReturn(command);
        if (result.equalsIgnoreCase("Success")) {
            System.out.println("Reboot emulator is done");
        }
        else {
            System.out.println("Reboot emulator is false");
        }
    }

    /**
     * check the text is empty or not
     * @param text
     * @return true or false
     */
    public static boolean isTextEmpty (String text){

        if (text.equalsIgnoreCase("no") || text.equalsIgnoreCase("w/o") ||
                text.equalsIgnoreCase("without") || text.equalsIgnoreCase("not") ||
                text.equalsIgnoreCase("don't") || text.equalsIgnoreCase("do not") ||
                text.isEmpty() || text.equals("") || text.equalsIgnoreCase("none")) {
            System.out.println("Given a empty text");
            return true;
        }
        else {
            return false;
        }
    }

    public static Date currentDate(){

        //format: Tue May 15 11:30:30 PDT 2018
        return new Date();
    }

    /**
     * get current Date info (year, month/mm, date, hours, mins)
     * @param infor
     * @return
     */
    public static String currentDateInfo(String infor) {
        Date date = currentDate();
        String dateInfo = "";

//        String mom = DriverUtils.currentDateInfo("month");
//        System.out.println("MONTH ==> " + mom);  Apr - String
//        Month momm = Month.valueOf(mom);
//        System.out.println(momm);  Apr - Month
//        System.out.println(momm.showNum());  4 - int

        switch (infor) {
            case "year":
                dateInfo = new SimpleDateFormat("yyyy").format(date);
                break;
            case "yyyy":
                dateInfo = new SimpleDateFormat("yyyy").format(date);
                break;
            case "month":
                dateInfo = new SimpleDateFormat("MMM").format(date);
                break;
            case "mm":
                dateInfo = new SimpleDateFormat("MM").format(date);
                break;
            case "date":
                dateInfo = new SimpleDateFormat("dd").format(date);
                break;
            case "dd":
                dateInfo = new SimpleDateFormat("dd").format(date);
                break;
            case "hours":
                dateInfo = new SimpleDateFormat("hh").format(date);
                break;
            case "hh":
                dateInfo = new SimpleDateFormat("hh").format(date);
                break;
            case "24hh":
                dateInfo = new SimpleDateFormat("kk").format(date);
                break;
            case "mins":
                dateInfo = new SimpleDateFormat("mm").format(date);
                break;
            default:
                SeLionAsserts.verifyFalse(true,"Please give a correct String " +
                        "(year|yyyy|month|mm|date|dd|hours|mins)");
        }
        return dateInfo;
    }

    /**
     * get Time-Date stamp
     * @return
     */
    public static String getTimeDateStamp () {
        Date date = currentDate();
        String stamp = new SimpleDateFormat("kkmmMMdd").format(date);

        return stamp;
    }

    /**
     * get Date-Time stamp
     */
    public static String getDateTimeDetailsStamp () {
        Date date = DriverUtils.currentDate();
        String stamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date);

        return stamp;
    }

    /**
     * write the data into fileDir
     * @param file
     * @param data
     */
    public static void writeToFile(String file, String data, boolean isAppand){
//        FileWriter fw = null;
//        try {
//            File f = new File(file);
//            if (isAppand) {
//                fw = new FileWriter(f, isAppand);
//            } else {
//                fw = new FileWriter(f);
//            }
//
//        }
//        catch (IOException e){
//            e.printStackTrace();
//        }
//        PrintWriter pw = new PrintWriter(fw);
//        pw.println(data);
//        pw.flush();
//        try {
//            fw.flush();
//            pw.close();
//            fw.close();
//        }
//        catch ( IOException e ){
//            e.printStackTrace();
//        }
        try {
            RandomAccessFile randomFile = new RandomAccessFile(file, "rw" );
            long fileLength = randomFile.length();

            if (isAppand) {
//                System.out.println("+++++++is Appand+++++++" + fileLength);
                randomFile.seek(fileLength);
            }
            System.out.println(fileLength + " : ******" + data + "******");
//            randomFile.writeBytes(data + "\r\n");
            randomFile.writeBytes(data);
            randomFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * get the appName from TestNG properties - app path
     * @return String appName
     */
    public static String getAppName() {
        Capabilities caps = Grid.driver().getCapabilities();
        Object o = caps.getCapability("app");
        String[] array = o.toString().split("/");
        String appName = (array[array.length-1]).split("\\.")[0];
        System.out.println("apptype: " + appName);

        return appName;
    }

    /**
     * get the app information from the testNG properties file
     * @return String appCameFrom (iOS - app_store, local_build. Android - Play_store, local_build)
     */
    public static String getAppFrom() {
        String appName = getAppName();
        if (isIOS() && getAppName().equals("Tact")) {
            return "App_store";
        }
        else if (isAndroid() && getAppName().contains("release")) {
            return "Play_store";
        }
        else {
            return "local_build";
        }
    }

    /**
     * launch the App
     */
    public static void relaunchApp() {
        ((AppiumDriver)Grid.driver()).launchApp();
    }

    public static void main(String[] args){
        tapAndroidHardwareHomeBtn();
    }


    /**
     * clickOption
     * @param location
     * @param replaceString
     * @param option
     */
    public static void clickOption(MobileElement location, String replaceString, String option){
        String stageLoc = location.getLocator().replace(replaceString, option);
        System.out.println("stageLoc ==> " + stageLoc);
        if (Grid.driver().findElementsByXPath(stageLoc).size()==0){
            slideUP();
        }
        Grid.driver().findElementByXPath(stageLoc).click();
        sleep(0.5);
    }

    /**
     * check field Data Type
     * randomNumeric - 6116388799
     * randomAlphabetic - msWGGKDFQe
     * randomAlphanumeric - rwis1DSWnm
     * randomAscii - w:vjD@D+uJ
     * @param location
     * @param type
     * @param maxLength
     * @param text
     */
    public static void inputTextField(boolean isEdit, MobileTextField location, FieldDataType type, int maxLength, String text, boolean isVerify){
        String randomText = text;
        int expectNumLength = -1;

        if (text.contains("TextLength")) {
            //get the expectNum
            expectNumLength = Integer.parseInt(text.substring(10));

            //base on type get the value
            /**
             * randomNumeric - 6116388799
             * randomAlphabetic - msWGGKDFQe
             * randomAlphanumeric - rwis1DSWnm
             * randomAscii - w:vjD@D+uJ
             */
            switch (type) {
                case randomNumeric:      //6116388799
                    randomText = String.valueOf(DriverUtils.getRandomLongNumberInGivenLength(expectNumLength));
                    break;
                case randomAlphabetic:   //msWGGKDFQe
                    randomText = RandomStringUtils.randomAlphabetic(expectNumLength);
                    break;
                case randomAlphanumeric: //rwis1DSWnm
                    randomText = RandomStringUtils.randomAlphanumeric(expectNumLength);
                    break;
                case randomAscii:        //w:vjD@D+uJ
                    randomText = RandomStringUtils.randomAscii(expectNumLength);
                    break;
                default:
                    randomText = RandomStringUtils.randomAlphanumeric(expectNumLength);
            }
        }

        System.out.println(location.toString() + " input randomText : " + randomText);
        if (isEdit) {
            location.clearText();
        }
        location.sendKeys(randomText);

        String getDisplayText = location.getValue();
        System.out.println("getDisplayText ==> " + getDisplayText);

        if (isVerify) {
            if (expectNumLength > maxLength) {
                SeLionAsserts.assertNotEquals(randomText, getDisplayText, "They should not equals");
            } else {
                SeLionAsserts.assertEquals(randomText, getDisplayText, "They should equal");
            }
        } else {
            System.out.println("No need to verify the input textField " + location.toString());
        }

//        sleep(30);

        System.out.println("finish type");

    }

    public static void turnOffWifi(){
        runCommand("networksetup -setairportpower en0 off");
    }

    public static void turnOnWifi() { runCommand("networksetup -setairportpower en0 on"); }

    /**
     * get current monitor screen size
     * @return Dimension
     */
    public static Dimension getCurrentMonitorScreenSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    /**
     * get current monitor screen width
     * @return double
     */
    public static double getCurrentMonitorScreenWidth() {
        return getCurrentMonitorScreenSize().getWidth();
    }

    /**
     * get current monitor screen height
     * @return double
     */
    public static double getCurrentMonitorScreenHeight() {
        return getCurrentMonitorScreenSize().getHeight();
    }

    /**
     * get current browser screen size
     * @return org.openqa.selenium.Dimension
     */
    public static org.openqa.selenium.Dimension getCurrentBrowserScreenSize() {
        return Grid.driver().manage().window().getSize();
    }

    /**
     * get current browser screen width
     * @return double
     */
    public static double getCurrentBrowserScreenWidth() {
        return getCurrentBrowserScreenSize().getWidth();
    }

    /**
     * get current browser screen height
     * @return double
     */
    public static double getCurrentBrowserScreenHeight() {
        return getCurrentBrowserScreenSize().getHeight();
    }

    /**
     * resize browser
     * @param width
     * @param height
     */
    public static void resizeBrowserSize(int width, int height) {
        org.openqa.selenium.Dimension newScreenSize=new org.openqa.selenium.Dimension(width, height);
        Grid.driver().manage().window().setSize(newScreenSize);
    }

    /**
     *
     */
    public static void resizeBrowserToMonitorSize() {
        double monitorWidth = DriverUtils.getCurrentMonitorScreenWidth();
        double monitorHeight = DriverUtils.getCurrentMonitorScreenHeight();
        String monitorSizeString = String.format("monitor size : %f x %f", monitorWidth, monitorHeight);
        System.out.println(monitorSizeString);
        DriverUtils.resizeBrowserSize((int)monitorWidth, (int)monitorHeight);
    }

    /**
     * get format firstName lastName
     * @param unformatName
     * @return String
     */
    public static String convertToFormatName(String unformatName) {
        String name = unformatName;

        String[] parts = name.split(",\\s?");
        if (parts.length > 1)
        {
            name = parts[1] + " " + parts[0];
        }
        System.out.println("name inside convertToFormatName :" + name);

        return name;
    }

    /**
     * get the 1st name from given full name
     * @param fullName
     * @return String
     */
    public static String get1stNFromFullName (String fullName, boolean addStamp) {
        String [] parts;
        String firstName;

        if (fullName.matches("\\w+,\\s?\\w+")) {
            parts = fullName.split(",\\s?");
            firstName = parts[1];
        } else if (fullName.matches("\\w+\\s+\\w+")) {
            parts = fullName.split("\\s+");
            firstName = parts[0];
        } else {
            firstName = "";
        }

        if (addStamp) {
            firstName = String.format("%s%s", getTimeDateStamp(), firstName);
        }

        return firstName;
    }

    /**
     * get the family name from given full name
     * @param fullName
     * @return String
     */
    public static String getLastNFromFullName (String fullName, boolean addStamp) {
        String [] parts;
        String lastName;

        if (fullName.matches("\\w+,\\s?\\w+")) {
            parts = fullName.split(",\\s?");
            lastName = parts[0];
        } else if (fullName.matches("\\w+\\s+\\w+")) {
            parts = fullName.split("\\s+");
            lastName = parts[1];
        } else {
            lastName = fullName;
        }

        if (addStamp) {
            lastName = String.format("%s%s", getTimeDateStamp(), lastName );
        }

        return lastName;
    }

    /**
     * Add a time stamp before the name
     * @param name
     * @param addStamp
     * @return
     */
    public static String getNameWithStamp (String name, boolean addStamp) {
        String resultName = "";

        if (addStamp) {
            resultName = String.format("%s_%s", getTimeDateStamp(), name);
        }
        return resultName;
    }
}
