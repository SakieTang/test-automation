package ai.tact.qa.automation.utils;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Selenium {

    public Selenium() {}

    /**
     * stop Selenium server
     */
    public static void stopServer(String givenPort) {
        String[] str = null;
        String temp = null;
        String line = null;
        String command = null;
        String port = givenPort;
        System.out.println("port is : " + port);

        command = String.format("netstat -van tcp | grep %s | awk '{print $9}'", port);
        System.out.println("cmd : " + command);

        String processId = DriverUtils.getRunCommandReturn(new String[]{"bash", "-c", command});
        System.out.println("process Id " + processId);

        command = "kill -9 " + processId;
        System.out.println("kill cmd : " + command );
        DriverUtils.runCommand(new String[]{"bash", "-c", command});

//        try {
//            Process process = DriverUtils.runCommand(new String[] {"bash", "-c", command});//(command);
//            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            System.out.println("out side loop " + reader.readLine().toString());
//            while ((line = reader.readLine()) != null) {
//                System.out.println("line : " + line.toString());
//                str = line.toString().split(" ");
//                temp = str[str.length-1];
//                System.out.println("process ID : " + temp);
//                //kill running Selenium process
//                command = "kill -9 " + temp;
//                System.out.println("kill cmd : " + command);
//
//                DriverUtils.runCommand(new String[] {"bath","-c", command});
//                DriverUtils.sleep(5);
//            }
//            process.waitFor();
//        }
//        catch (IOException | InterruptedException e) {
//            e.getMessage();
//        }
        if (!Selenium.isSeleniumServerRunning(port))
        {
            System.out.println("------------------- Selenium stoped ----------------------");
        }
        else {
            System.out.println("");
        }
    }

    /**
     * stop Selenium server
     */
    public static void stopServer() {
        stopServer("4723");
    }

    /**
     * check Selenium server is running
     */
    public static boolean isSeleniumServerRunning (String givenPort) {
        String command = null;
        String port = givenPort;
        System.out.println("port is : " + port);

        command = String.format("netstat -van tcp | grep %s | awk '{print $9}'", port);
        System.out.println("cmd : " + command);

        String processID = DriverUtils.getRunCommandReturn(new String[] {"bash", "-c", command});
        if (processID.isEmpty()) {
            System.out.println("The Selenium already stopped");
            return false;
        } else {
            System.out.println("The Selenium is still running");
            return true;
        }
    }

    /**
     * start Selenium Server from command line
     */
    public static void startServer(String givenPort) {
        String cmd;
        String seleniumServerJar;
        String seleniuDir;
        String port = givenPort;
        CommandLine commandLine;

        commandLine = new CommandLine("java -jar");

        seleniumServerJar = "selenium-server-standalone-3.9.1.jar";
        seleniuDir = String.format("%s/%s", System.getProperty("user.dir"), seleniumServerJar);
        System.out.println("Selenium Jar dir : " + seleniuDir);

        commandLine.addArgument(seleniuDir);
        commandLine.addArgument("--port");
        commandLine.addArgument(port);

        System.out.println("cmd : " + commandLine.toString());

        DefaultExecuteResultHandler handler = new DefaultExecuteResultHandler();
        DefaultExecutor executor = new DefaultExecutor();
        executor.setExitValue(1);
        try {
            executor.execute(commandLine, handler);
            Thread.sleep(10000);
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * start Selenium Server from command line
     */
    public static void startServer() {
        startServer("4723");
    }

    /**
     * restart Selenium
     */
    public static void restartSelenium(String givenPort) {
        stopServer(givenPort);
        startServer(givenPort);
    }

    /**
     * restart Selenium
     */
    public static void restartSelenium() {
        restartSelenium("4723");
    }

    @Test
    public void testSeleniumServer() {
        DriverUtils.runCommand(new String[] {"bash", "-c", "java -jar /Users/sakie/workspace/automation/test-automation/selenium-server-standalone-3.9.1.jar -port 4723"});
    }
}
