package ai.tact.qa.automation.utils;
        
        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        
        import org.testng.annotations.Test;
        import org.apache.commons.exec.CommandLine;
        import org.apache.commons.exec.DefaultExecuteResultHandler;
        import org.apache.commons.exec.DefaultExecutor;
        
        
        public class Appium {

            public Appium() {	}

        
        //    Appium configuration
        //    Start_Appium  :
        //    {Android: "D:\\Appium\\node_modules\\.bin\\appium.cmd", iOS: "/Applications/Appium.app/Contents/Resources/app/node_modules/appium/build/lib/main.js"}
        //    Appium_URL    :
        //    {Android: "http://0.0.0.0", iOS: "http://0.0.0.0"}
        //    Appium_URL_PORT:
        //    {Android: "4723", iOS: "4723"}
        
        
            /**
      * stop Appium server
      */
            public static void stopServer() {
                String[] str = null;
                String temp = null;
                String line = null;
                String command = null;
                String port = "4723";
                System.out.println("port is : " + port);
        
                        command = "pgrep node";
                System.out.println("cmd : " + command);
        
                        try {
            //            str = DriverUtils.runCommand(command).split(" ");
                    //            temp = str[str.length1];
                                        Process process = DriverUtils.runCommand(command);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        while ((line = reader.readLine()) != null) {
                                System.out.println("line : " + line.toString());
                                str = line.toString().split(" ");
                                temp = str[str.length];
                                System.out.println("process ID : " + temp);
                                //kill running Appium process
                                        command = "kill 9 " + temp;
                                System.out.println("cmd : " + command);
                
                                        DriverUtils.runCommand(command);
                                DriverUtils.sleep(5);
                            }
                        process.waitFor();
                    } catch (IOException | InterruptedException e) {
                        e.getMessage();
                    }
        
                        if (!Appium.checkIfServerIsRunnning(port))
                    {
                                System.out.println(" Appium stoped ");
                }
            }

            /**
      * restart Appium : stop it 1st, then start it again.
      */
            public static void restartAppium(String address, String port, String bootstrapPort) {
                Appium.stopServer();
                Appium.startServer(address, port, bootstrapPort);
            }

            /**
      * restart Appium : stop it 1st, then start it again.
      */
            public static void restartAppium() {
                Appium.stopServer();
                Appium.startServer();
            }

            /**
      *
      * @param port
      * @return boolean whether it starts or not
      */
            public static boolean checkIfServerIsRunnning(String port) {
        
                        boolean isServerRunning = false;
                String command = null;
        
                
                                command = "pgrep node";
                System.out.println("cmd : " + command);
        
                        try {
                        Process process = DriverUtils.runCommand(command);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        if (reader.readLine() != null) {
                                System.out.println("The Appium is still running");
                                isServerRunning = true;
                            }
                        else {
                                System.out.println("The Appium already stopped");
                                isServerRunning = false;
                            }
                    }
                catch (IOException e) {
                        e.getMessage();
                    }
                return isServerRunning;
            }

            /**
      * start Appium Server from command line
      */
            public static void startServer(String address, String port, String bootstrapPort) {
                String commandStart;
                String startAppiumDir;
                CommandLine cmd;
        
                        commandStart = "/usr/local/bin/node";
                startAppiumDir = "/Applications/Appium.app/Contents/Resources/app/node_modules/appium/build/lib/main.js";
                cmd = new CommandLine(commandStart);
                cmd.addArgument(startAppiumDir);
        
                        String dir = System.getProperty("user.dir") + "//testoutput//RuntimeReporter//appiumLog.log";
                System.out.println("dir : " + dir);
        
                        cmd.addArgument("address");
                cmd.addArgument(address);
                cmd.addArgument("port");
                cmd.addArgument(port);
        
                        //callbackPort
                                cmd.addArgument("cp");
                cmd.addArgument(port);
                //bootstrapPort
                        cmd.addArgument("bp");
                cmd.addArgument(bootstrapPort);
        
                        cmd.addArgument("sessionoverride");
                cmd.addArgument("loglevel");
                cmd.addArgument("info");
                cmd.addArgument("log");
                cmd.addArgument(dir);
        
                        System.out.println("cmd : " + cmd.toString());
        
                        DefaultExecuteResultHandler handler = new DefaultExecuteResultHandler();
                DefaultExecutor executor = new DefaultExecutor();
                executor.setExitValue(1);
                try {
                        executor.execute(cmd, handler);
                        Thread.sleep(10000);
                    }
                catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
        
                    }

            /**
      * start Appium Server from command line
      */
            public static void startServer() {
                String commandStart;
                String startAppiumDir;
                CommandLine cmd;
        
                        commandStart = "/usr/local/bin/node";
                startAppiumDir = "/Applications/Appium.app/Contents/Resources/app/node_modules/appium/build/lib/main.js";
                cmd = new CommandLine(commandStart);
                cmd.addArgument(startAppiumDir);
        
                        String dir = System.getProperty("user.dir") + "//testoutput//RuntimeReporter//appiumLog.log";
                System.out.println("dir : " + dir);
        
                        cmd.addArgument("sessionoverride");
                cmd.addArgument("loglevel");
                cmd.addArgument("info");
                cmd.addArgument("log");
                cmd.addArgument(dir);
        
                        System.out.println("cmd : " + cmd.toString());
        
                        DefaultExecuteResultHandler handler = new DefaultExecuteResultHandler();
                DefaultExecutor executor = new DefaultExecutor();
                executor.setExitValue(1);
                try {
                        executor.execute(cmd, handler);
                        Thread.sleep(10000);
                    }
                catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
        
                    }

        
            @Test
    public void testUser() {
        
                        Appium.restartAppium("127.0.0.0","4723", "4724");
                if (!Appium.checkIfServerIsRunnning("4723")) {
                        System.out.println("Appium does not run");
                        Appium.startServer("127.0.0.0","4723","4724");
                    }
                else {
                        System.out.println("Appium does run");
                    }
        
                        DriverUtils.clearChromeData();
        
                        Appium.stopServer();
                System.out.println("Appium stoped");
        
                        if (!Appium.checkIfServerIsRunnning("4723")) {
                        System.out.println("Appium does not run");
                    }
                else {
                        System.out.println("Appium does run");
                    }
                DriverUtils.sleep(60);
            }

        }

