package ai.tact.qa.automation.runner;

import ai.tact.qa.automation.utils.DriverUtils;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class AndroidGetDailyBuildCase {

    String appPath = null;

//    run it from cmd "mvn -Dtest=AndroidGetDailyBuildCase test"

    @Test
    public void runAndroidDailyBuild() {
        String fromFile = "https://rink.hockeyapp.net/api/2/apps/63a92edb44f544f6809959332a92d56f/app_versions/96?format=apk&avtoken=4351eb5e6b7606332c2ca4b81d80ed8bfb4ecd5d&download_origin=hockeyapp";
        String appName = "Applications/TactApplication-alpha-debug.apk";
        String buildVersion = "1705";

        String toFile = String.format("%s/%s", System.getProperty("user.dir"), appName);
        toFile = String.format("%s%s.%s", toFile.split("\\.")[0], buildVersion, toFile.split("\\.")[1] );
        System.out.println(toFile);
        DriverUtils.downloadFileFromUrl(fromFile, toFile);

        appPath = toFile;

    }

//    @Test
//    @Parameters({"apkDownloadURL", "appPath", "buildVersion"})
//    public void getBuild(String url, String appPath, String buildVersion){
//        String fromFile = url;
//        String toFile = String.format("%s/%s", System.getProperty("user.dir"), appPath);
//        System.out.println("https://rink.hockeyapp.net/api/2/apps/63a92edb44f544f6809959332a92d56f/app_versions/88?format=apk&avtoken=07ec02b7202e97012ab66eff6f11b95e6a4f3d7c\n" + fromFile);
//        System.out.println("appPath: " + toFile);
//
//        toFile = String.format("%s%s.%s", toFile.split("\\.")[0], buildVersion, toFile.split("\\.")[1] );
//        System.out.println(toFile);
//
//        DriverUtils.downloadFileFromUrl(fromFile, toFile);
//
//        appPath = toFile;
//
//        appPathDir = appPath;
//
//        DriverUtils.deleteReport();
//
//    }

    @Test(description = "start running Tact Assistant from iOS", alwaysRun = true, dependsOnMethods = "runAndroidDailyBuild")
    public void runDailyBuild() {
        //iOS
//        String mobilePlatform = "IOS";
//        String mobileDevice = "iphone:11.2";
//        String mobileDeviceType = "iPhone X";
//        String suiteXmlFile = "testNG.xml";

        //Android
        String mobilePlatform = "ANDROID";
        String mobileDevice = "android:8.1";
        String mobileDeviceType = "Android Emulator";
        String suiteXmlFile = "testNGAndr.xml";

        System.out.println(System.getProperty("user.dir"));
//        String currentDir = String.format("%s/%s", System.getProperty("user.dir"), "testNGAIMobile.xml");
//        String command = String.format("mvn test -DsuiteXmlFile=%s -DSELION_MOBILE_APP_PATH=%s", currentDir, appPathDir);
        String command = String.format("mvn test -DmobilePlatform=%s -DappPath=%s -DmobileDevice=\"%s\" -DmobileDeviceType=\"%s\" -DsuiteXmlFile=%s", mobilePlatform, appPath, mobileDevice, mobileDeviceType, suiteXmlFile);

        System.out.println("command " + command);

        DriverUtils.runCommand(new String[]{"bash", "-c", "adb root"});
        DriverUtils.sleep(10);

        DriverUtils.runCommand(new String[]{"bash", "-c", command});

    }
}
