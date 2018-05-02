package ai.tact.qa.automation.utils;

import ai.tact.qa.automation.testcomponents.mobile.TactWelcomePage;

import com.paypal.selion.internal.platform.grid.WebDriverPlatform;
import com.paypal.selion.platform.asserts.SeLionAsserts;
import com.paypal.selion.platform.grid.Grid;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.Capabilities;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

public class DriverUtils {

    /**
     * check the current testing environment is iOS
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
     * check the current testing environment is Android
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
     * get the current testing environment type
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
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * hid the keyboard
     */
    public static void hideKeyboard(){
        AppiumDriver driver = (AppiumDriver)Grid.driver();
        driver.hideKeyboard();
        driver.swipe(201,290,201,264,10);
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
        }
        catch (IOException e) {
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
     * @param min
     * @param max
     * @return random number
     */
    public static int getRandomNumber ( int min, int max ) {
        int result = min;
        result = (int)((max)*Math.random() + min);
        return result;
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
        runCommand(command);
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
//			process.waitFor();
//		} catch (IOException | InterruptedException e) {
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return process;
    }

    //××××××××××//
    // Android //
    //××××××××××//
    /**
     * clear the chrome app data (Android Only)
     */
    public static void clearChromeData(){
        String command = "adb shell pm clear com.android.chrome";
        String line = null;

        try {
            Process process = DriverUtils.runCommand(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            if ((line = reader.readLine()) != null && line.equalsIgnoreCase("Success")) {
                System.out.println("Clear Chrome Data is done");
            }
            else {
                System.out.println("Clear Chrome Data is false");
            }
        }
        catch (IOException e) {
            e.getMessage();
        }
    }

    //××××××××××//
    // Android //
    //××××××××××//
    /**
     * reboot device
     */
    public static void rebootEmulator(){
        String command = "adb root";
        String line = null;

        try {
            Process process = DriverUtils.runCommand(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            if ((line = reader.readLine()) != null && line.equalsIgnoreCase("Success")) {
                System.out.println("Reboot emulator is done");
            }
            else {
                System.out.println("Reboot emulator is false");
            }
        }
        catch (IOException e) {
            e.getMessage();
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
                text.isEmpty() || text.equals("")) {
            System.out.println("Given a empty text");
            return true;
        }
        else {
            return false;
        }
    }

    public static Date currentDate(){

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
            case "month":
                dateInfo = new SimpleDateFormat("MMM").format(date);
                break;
            case "mm":
                dateInfo = new SimpleDateFormat("MM").format(date);
                break;
            case "date":
                dateInfo = new SimpleDateFormat("dd").format(date);
                break;
            case "hours":
                dateInfo = new SimpleDateFormat("hh").format(date);
                break;
            case "mins":
                dateInfo = new SimpleDateFormat("mm").format(date);
                break;
            default:
                SeLionAsserts.verifyFalse(true,"Please give a correct String " +
                        "(year|month|mm|date|hours|mins)");
        }
        return dateInfo;
    }

    /**
     * write the data into fileDir
     * @param file
     * @param data
     */
    public static void writeToFile(String file, String data){
        FileWriter fw = null;
        try {
            File f = new File(file);
            fw = new FileWriter(f);

        }
        catch (IOException e){
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        pw.println(data);
        pw.flush();
        try {
            fw.flush();
            pw.close();
            fw.close();
        }
        catch ( IOException e ){
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
}
