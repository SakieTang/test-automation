package ai.tact.qa.automation.utils.report;

import ai.tact.qa.automation.utils.DriverUtils;
import ai.tact.qa.automation.utils.LogUtil;
import ai.tact.qa.automation.utils.dataobjects.Status;
import static ai.tact.qa.automation.utils.dataobjects.Status.*;

import com.paypal.selion.internal.platform.grid.WebDriverPlatform;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import java.util.Scanner;

public class GenerateReport {

    private static final Logger log = LogUtil.setLoggerHandler(Level.ALL);

    public static void getReport(WebDriverPlatform platform) {
        writeNewReport(platform);
    }


    public static void writeNewReport(WebDriverPlatform platform) {

        FileWriter fileWriter = null;
        FileWriter fileWriterTxt = null;
        String fileName = "androidReport";
        String filesDir = "target/report/android";

        if (platform == WebDriverPlatform.IOS) {
            fileName = "iOSReport";
            //target/report/ios
            filesDir = "target/report/ios";
        }
        else {
            fileName = "androidReport";
            //target/report/android
            filesDir = "target/report/android";
        }
        try {
            File file = new File("target/report/" + fileName + ".json");
            fileWriter = new FileWriter(file);

            File txtFile = new File("target/report/" + fileName + ".txt");
            fileWriterTxt = new FileWriter(txtFile);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        //Json
        List<Report> reports = new ArrayList<Report>();
        Report report = new Report();
        report.setAppInfo(getAppVersion(platform));
        //txt
        PrintWriter pw = new PrintWriter(fileWriterTxt);
        pw.println(getAppVersion(platform));

        //Json
        List<Report.Feature> features = new ArrayList<Report.Feature>();

        //folder dir
        File folder = new File(filesDir);

        File[] listOfFiles = folder.listFiles();
        System.out.println("number of files : " + listOfFiles.length);

        for (File f : listOfFiles) {
            if (f.isFile() && f.toString().contains("json"))
            {
                Report.Feature feature = report.new Feature();
                System.out.println(" *** File : " + f.getName());
                feature = readFeatureData(f.getPath(), pw, feature);
                features = combineDupFeatureCases(features, feature);
            }
        }

        report.setFeatures(features);
        reports.add(report);

        List<Report.Feature> list = report.getFeatures();
        for (Report.Feature f : list) {
            System.out.println("name : " + f.getFeatureName());
        }

        //prettyPrint
        Gson g = new Gson();
        String s = g.toJson(reports);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(s);
        String prettyJsonString = gson.toJson(jsonElement);

        PrintWriter pwJSON = new PrintWriter(fileWriter);
        pwJSON.println(prettyJsonString);

//        //uglyJsonString
//        Gson g = new Gson();
//        String s = g.toJson(reports);
//
//        PrintWriter pwJSON = new PrintWriter(fileWriter);
//        pwJSON.println(s);

        try {
            fileWriter.flush();
            fileWriterTxt.flush();
            pw.close();
            pwJSON.close();
            fileWriter.close();
            fileWriterTxt.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    //AI generate html
    public static void generateAIHtml() {
        /**
         - expected reply - passed
         - wrong reply - failed
         - no reply - alarm
         */
        String aiFileDir = "target/aiTestingReport.txt";
        FileWriter fileWriterHTML = null;

        try {
            String tempReportDir = System.getProperty("user.dir") + "/src/test/java/ai/tact/qa/automation/utils/report/tempAIReport.html";
            String report = loadFileFromResources(tempReportDir);
            String today = String.format("%s%s%s - %s:%s", DriverUtils.currentDateInfo("yyyy")
                    , DriverUtils.currentDateInfo("mm")
                    , DriverUtils.currentDateInfo("dd")
                    , DriverUtils.currentDateInfo("24hh")
                    , DriverUtils.currentDateInfo("mins"));

            report = report.replace("{TODAY}", today);

            report = report.replace("{RESULT}", aiReportsToHtmlTable(aiFileDir));

            System.out.println("\nreport : \n" + report);

            //html
            File htmlFile = new File("target/aiReport.html");
            fileWriterHTML = new FileWriter(htmlFile);

            PrintWriter pw = new PrintWriter(fileWriterHTML);
            pw.println(report);


            //close file
            fileWriterHTML.flush();
            pw.close();
            fileWriterHTML.close();
        }
        catch (FileNotFoundException e) {

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Mobile generate html
    public static void generteHtml(){

        String iosFileDir     = "target/report/iosReport.json";
//        String androidFileDir = "target/androidReport.json";
        String androidFileDir = "target/report/androidReport.json";
        String tempFileDir = "src/test/java/ai/tact/qa/automation/utils/report/tempReport.json";

        //check whether the file exists or not, if not replace with temp.json file
        if (!new File(iosFileDir).exists())
        {
            iosFileDir = tempFileDir;
        }
        if (!new File(androidFileDir).exists())
        {
            System.out.println("android report is not here");
            androidFileDir = tempFileDir;
        }

        JSONParser parser = new JSONParser();
        FileWriter fileWriterHTML = null;

        try {
            JSONObject iOSObj = (JSONObject)((JSONArray) parser.parse(new FileReader(iosFileDir))).get(0);
            JSONObject androidObj = (JSONObject)((JSONArray) parser.parse(new FileReader(androidFileDir))).get(0);

            Gson g = new Gson();
            Report iOSReport = g.fromJson(iOSObj.toString(), Report.class);
            Report androidReport = g.fromJson(androidObj.toString(), Report.class);

            System.out.println("ios : " + iOSObj.toString().getClass());
            System.out.println("and : " + iOSObj.toJSONString().getClass());

            String tempReportDir = System.getProperty("user.dir") + "/src/test/java/ai/tact/qa/automation/utils/report/tempReport.html";
            String report = loadFileFromResources(tempReportDir);
            String today = String.format("%s%s%s", DriverUtils.currentDateInfo("yyyy")
                    , DriverUtils.currentDateInfo("mm")
                    , DriverUtils.currentDateInfo("dd"));

            report = report.replace("{TODAY}", today);
            report = report.replace("{IOSVERSION}", iOSReport.getAppInfo());
            System.out.println("androidReport.getAppInfo() ==> " + androidReport.getAppInfo());
            report = report.replace("{ANDROIDVERSION}", androidReport.getAppInfo());

            report = report.replace("{RESULT}", reportsToHtmlTable(iOSReport, androidReport) );

            System.out.println("\nreport : \n" + report);

            //html
            File htmlFile = new File("target/report.html");
            fileWriterHTML = new FileWriter(htmlFile);

            PrintWriter pw = new PrintWriter(fileWriterHTML);
            pw.println(report);


            //close file
            fileWriterHTML.flush();
            pw.close();
            fileWriterHTML.close();

        }
        catch (FileNotFoundException e) {

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    //report Mobile platforms html table
    public static String reportsToHtmlTable(Report iosReport, Report android) {
        List<Report.Feature> features = iosReport.getFeatures();
        List<Report.Feature> androidFeaturesRest = android.getFeatures();

        // maybe sort the features list here.

        String html = "";

        //table body
        for (Report.Feature iosFeature : features) {
            boolean bfirst = true;

            List<Report.Feature> androidFeatures = android.getFeatures();
            Report.Feature androidFeature = null;
            for (Report.Feature tmpFeature : androidFeatures) {
                if (tmpFeature.getFeatureName().equals(iosFeature.getFeatureName()))
                {
                    androidFeature = tmpFeature;
                }
            }
            html += featureToHtml(iosFeature, androidFeature);
            androidFeaturesRest.remove(androidFeature);
        }

        for (Report.Feature androidFeature : androidFeaturesRest) {
            html += featureToHtml("ios", androidFeature);
        }

        return html;
    }

    //report AI channels html table
    public static String aiReportsToHtmlTable(String aiFileDir) {
        File file = new File(aiFileDir);
        String html = "";

        try {
            Scanner scanner = new Scanner(file);

            while(scanner.hasNextLine()){
                String cmdResultLine = scanner.nextLine();

                String cmd = cmdResultLine.split(" \\| ")[1];
//                if (cmd.equalsIgnoreCase("What is the latest on xyz deals?")){
//                    continue;
//                }

                String line = "<tr>";
                line += "<td class='channel-name'>" + cmdResultLine.split("\\|")[0] + "</td>";
                line += "<td class='command-name'>" + cmdResultLine.split(" \\| ")[1] + "</td>";
                line += "<td class='command-name'>" + cmdResultLine.split("\\| ")[2] + "</td>"; //time-date stamp
                String cmdResult = cmdResultLine.split(" \\| ")[3];
                String cmdTime = cmdResultLine.split(" \\| ")[4];
//                System.out.println("cmd " + cmdResultLine.split(" \\| ")[0]);
//                System.out.println("cmdResult " + cmdResult);
                System.out.println("cmdTime " + cmdTime);

                int time = Integer.parseInt(cmdTime.replaceAll("ms",""));

                if (cmdResult.equals("passed")) {
                    System.out.println(">>>>passed");
                    line += "<td class='passed'>" + Status.passed + "</td>";
                }
                else if (cmdResult.equals("failed")) {
                    line += "<td class='failed'>" + Status.failed + "</td>";
                    time = 0;
                }
                else {

                    System.out.println(">>>>alarm");
                    line += "<td class='alarm'>" + Status.alarm + "</td>";
                }

                if (time >= 5000) {
                    line += "<td class='alarm'>" + time + "</td>";
                }
                else if (time ==0) {
                    line += "<td class='failed'>" + time + "</td>";
                }
                else {
                    line += "<td class='passed'>" + time + "</td>";
                }

                line += "</tr> \n";
                html += line ;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return html;
    }

    public static String featureToHtml(String ios, Report.Feature androidFeature) {
        String html = "";

        Map<String, Report.Feature.Result> androidReults=new HashMap<>();
        for (Report.Feature.Case androidCase : androidFeature.getCases()) {
            androidReults.put(androidCase.getCaseName(), androidCase.getResult());
        }

        for (Map.Entry<String, Report.Feature.Result> entry : androidReults.entrySet()) {
            String line = "<tr>";
            // line += "<td>" + iosCase.getPriority() + "</td>";
            line += "<td class='feature-name'>" + androidFeature.getFeatureName() + "</td>";
            line += "<td class='case-name'>" + entry.getKey() + "</td>";
            line += "<td class='na'>n/a</td>";

            if (entry.getValue().getStatus().equals(Status.passed)) {
                line += "<td class='passed'><font color=\"green\">" + Status.passed + "</font></td>";
            }
            else if (entry.getValue().getStatus().equals(Status.failed)) {
                line += "<td class='failed'>" + Status.failed + "</td>";
            }
            else {
                line += "<td class='skipped'>" + Status.skipped + "</td>";
            }

            //<td class='feature-name'>Feature</td><td class='case-name'>case</td><td class='failed'><font color="red">failed</font></td><td class='skipped'><font color=\"blue\">skipped</font></td>
//            line += "<td>" + entry.getValue().getStatus() + "</td>";
            line += "</tr> \n";
            html += line ;
        }
        return html;
    }

    public static String featureToHtml(Report.Feature iosFeature, Report.Feature androidFeature) {

        String html = "";

        List<Report.Feature.Case> iosCases=iosFeature.getCases();

        Map<String, Report.Feature.Result> androidReults=new HashMap<>();
        if (androidFeature != null && androidFeature.getCases() != null) {
            for (Report.Feature.Case androidCase : androidFeature.getCases()) {
                androidReults.put(androidCase.getCaseName(), androidCase.getResult());
            }
        }

        for (Report.Feature.Case iosCase : iosCases) {
            String line="<tr>";
            // line += "<td>" + iosCase.getPriority() + "</td>";
            line+="<td class='feature-name'>" + iosFeature.getFeatureName() + "</td>";
            if (iosCase.getCaseName().equalsIgnoreCase("View Android email fields (From, Date, Subtitle, Body)")){
                line+="<td class='case-name'><b>" + iosCase.getCaseName() + "</b></td>";
            } else {
                line+="<td class='case-name'>" + iosCase.getCaseName() + "</td>";
            }

            if (iosCase.getResult() != null) {
                if (iosCase.getResult().getStatus().equals(Status.passed)) {
                    line+="<td class='passed'>" + Status.passed + "</td>";
                } else if (iosCase.getResult().getStatus().equals(Status.failed)) {
                    line+="<td class='failed'>" + Status.failed + "</td>";
                } else {
                    line+="<td class='skipped'>" + Status.skipped + "</td>";
                }
//                line += "<td>" + iosCase.getResult().getStatus() + "</td>";
            } else {
                line+="<td class='na'>n/a</td>";
            }

            if (androidReults.containsKey(iosCase.getCaseName())) {
                Status androidResult=androidReults.get(iosCase.getCaseName()).getStatus();
                if (androidResult.equals(Status.passed)) {
                    line+="<td class='passed'>" + Status.passed + "</td>";
                } else if (androidResult.equals(Status.failed)) {
                    line+="<td class='failed'>" + Status.failed + "</td>";
                } else {
                    line+="<td class='skipped'>" + Status.skipped + "</td>";
                }
//                line += "<td>" + androidReults.get(iosCase.getCaseName()).getStatus() + "</td>";
                androidReults.remove(iosCase.getCaseName());
            } else {
                line+="<td class='na'>n/a</td>";
            }
            line+="</tr> \n";
            html+=line;
        }


        for (Map.Entry<String, Report.Feature.Result> entry : androidReults.entrySet()) {
            String line="<tr>";
            // line += "<td>" + iosCase.getPriority() + "</td>";
            line+="<td class='feature-name'>" + iosFeature.getFeatureName() + "</td>";
            if (entry.getKey().equalsIgnoreCase("View Android email fields (From, Date, Subtitle, Body)")) {
                line+="<td class='case-name'><b>" + entry.getKey() + "</b></td>";
            } else {
                line+="<td class='case-name'>" + entry.getKey() + "</td>";
            }
            line+="<td class='na'>n/a</td>";

            if (entry.getValue().getStatus().equals(Status.passed)) {
                line+="<td class='passed'><font color=\"green\">" + Status.passed + "</font></td>";
            } else if (entry.getValue().getStatus().equals(Status.failed)) {
                line+="<td class='failed'>" + Status.failed + "</td>";
            } else {
                line+="<td class='skipped'>" + Status.skipped + "</td>";
            }

            //<td class='feature-name'>Feature</td><td class='case-name'>case</td><td class='failed'><font color="red">failed</font></td><td class='skipped'><font color=\"blue\">skipped</font></td>
//            line += "<td>" + entry.getValue().getStatus() + "</td>";
            line+="</tr> \n";
            html+=line;
        }

        return html;
    }


    public static Report.Feature readFeatureData(String filePath, PrintWriter pw, Report.Feature feature){
        List<Report.Feature.Case> cases = new ArrayList<Report.Feature.Case>();

        JSONParser parser = new JSONParser();
        String caseResult = "passed";

        try {
            JSONArray array = (JSONArray) parser.parse(new FileReader( filePath ));
            JSONObject jsonObject = (JSONObject)array.get(0);
            //featureName
            String featureName =  (String) jsonObject.get("name");
            feature.setFeatureName(featureName);

            JSONArray elements = (JSONArray)jsonObject.get("elements");

            for (Object testCaseObj : elements) {
                Report.Feature.Case testCase = feature.new Case();
                Report.Feature.Result result = feature.new Result();

                JSONObject testCaseJSONObj = (JSONObject) testCaseObj;

                //testCaseName
                String testCaseName = (String)testCaseJSONObj.get("name");
                testCase.setCaseName(testCaseName);
                pw.println(">>>>>> test case : ---" + testCaseName + "---");

                JSONArray steps = (JSONArray) testCaseJSONObj.get("steps");
                int duringTime = 0;
                for (Object testStepObj : steps)
                {
                    JSONObject testStep = (JSONObject) testStepObj;

                    String stepName = (String)testStep.get("name");
                    String stepResult = (String)((JSONObject)testStep.get("result")).get("status");
                    String stepTime;
                    if (!stepResult.equalsIgnoreCase("passed") && !caseResult.equalsIgnoreCase("failed")){
                        caseResult = stepResult;
                    }
                    else if (stepResult.equalsIgnoreCase("failed")){
                        caseResult = stepResult;
                    }
                    try {
                        stepTime = Long.toString(((long) ((JSONObject) testStep.get("result")).get("duration")) / 1000000000);
                    }
                    catch(NullPointerException e){
                        stepTime = "0";
                    }
                    duringTime = duringTime + Integer.parseInt(stepTime);

                    pw.println( stepResult + "\t:\t" + stepName );

                }
                pw.println( "RESULT: " + caseResult + " - time(s)\t:\t" + duringTime);
                pw.println("\n\n");

                result.setTime(duringTime);
                //passed, failed, skiped
                switch (caseResult) {
                    case "passed":
                        result.setStatus(Status.passed);
                        break;
                    case "Note":
                        result.setStatus(Status.failed);
                        break;
                    case "Task":
                        result.setStatus(Status.skipped);
                        break;
                    default:
                        result.setStatus(Status.skipped);
                }

                caseResult = "passed";

                //JSON
                testCase.setResult(result);
                cases.add(testCase);
            }
            feature.setCases(cases);

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return feature;
    }

    public static List<Report.Feature> combineDupFeatureCases(List<Report.Feature> features, Report.Feature newFeature) {
        if (features.size() == 0){
            features.add(newFeature);
        }
        else {
            int holder = -1;
            for (int i=0; i<features.size(); i++) {
                Report.Feature tempF = features.get(i);

                //combineCases
                if (tempF.getFeatureName().equals(newFeature.getFeatureName()))
                {
                    holder = i;
                    List<Report.Feature.Case> tempFCases = tempF.getCases();
                    List<Report.Feature.Case> newFCases = newFeature.getCases();
                    List<Report.Feature.Case> combineCases = new ArrayList<>();
                    combineCases.addAll(tempFCases);
                    combineCases.addAll(newFCases);

                    features.get(i).setCases(combineCases);
                    break;
                }
            }
            if (holder == -1 ) {
                features.add(newFeature);
            }
            else {
                holder = -1;
            }
        }
        return features;
    }


    public static void writeNewJsonReport(){

        List<Report> reports = new ArrayList<Report>();
        Report report = new Report();
        report.setAppInfo("IOS : Tact - 3.6");

        List<Report.Feature> features = new ArrayList<Report.Feature>();
        for (int j = 100; j<300; ) {

            Report.Feature feature = report.new Feature();
            feature.setFeatureName(Integer.toString(j) + " feature");

            List<Report.Feature.Case> cases = new ArrayList<Report.Feature.Case>();
            for (int i = 0; i < 2; i++) {
                Report.Feature.Case testCase = feature.new Case();
                Report.Feature.Result result = feature.new Result();

                testCase.setCaseName(Integer.toString(i+j) + " test");

                result.setTime(i+j);
                result.setStatus(passed);

                testCase.setResult(result);
                cases.add(testCase);
            }

            feature.setCases(cases);

            features.add(feature);

            j = j + 100;
        }

        report.setFeatures(features);
        reports.add(report);

        Gson gson = new Gson();
        String s = gson.toJson(reports);
        System.out.println(s);

        //folder dir
        File folder = new File("target/report");

        File[] listOfFiles = folder.listFiles();
        System.out.println("number of files : " + listOfFiles.length);

    }

    public static void printVersion(){
        File file = new File("target/osVersion.txt");
        try {
            Scanner sc = new Scanner(file);
            String s = sc.nextLine();
            String OS = s.split(" : ")[0];
            String appInfo = s.split(" : ")[1];
            System.out.println("|\t\t" + OS + "\t\t|\t\t" + appInfo + "\t\t|");

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static String getAppVersion(WebDriverPlatform platform){
        String appVersion = null;
        File file;
        if (platform == WebDriverPlatform.IOS) {
            file = new File("target/iosVersion.txt");
        }
        else {
            file = new File("target/androidVersion.txt");
        }
        try {
            Scanner sc = new Scanner(file);
            appVersion = sc.nextLine();
            String OS = appVersion.split(" : ")[0];
            String appInfo = appVersion.split(" : ")[1];
            System.out.println("|\t\t" + OS + "\t\t|\t\t" + appInfo + "\t\t|");

        }
        catch (IOException e){
            e.printStackTrace();
        }
        return appVersion;
    }

    public static void deleteAllJsonReport(String platform){
        String platformFolder = "";

        if (platform.equalsIgnoreCase("ios")){
            platformFolder = "ios";
        } else {
            platformFolder = "android";
        }

        File testDel = new File(String.format("target/report/%s/",platformFolder));

        if (testDel.exists()) {
            for (File file : testDel.listFiles()) {
                if (!file.isDirectory()) {
                    file.delete();
                }
            }
            System.out.println("deleted");
        } else {
            System.out.println("already deleted");
        }
    }

    public static void deleteAllJsonReport(){
        String platformFolder = "";

        if (DriverUtils.isIOS()){
            platformFolder = "ios";
        } else {
            platformFolder = "android";
        }

        //delete test case report
        File testDel = new File(String.format("target/report/%s/",platformFolder));

        if (testDel.exists()) {
            for (File file : testDel.listFiles()) {
                if (!file.isDirectory()) {
                    file.delete();
                }
            }
            System.out.println("deleted");
        } else {
            System.out.println("already deleted");
        }
    }

    public static void deleteAIReport()  {
        File testDel = new File("target/aiTestingReport.txt");

        if (testDel.exists())
        {
            testDel.delete();
            System.out.println("deleted");
        } else {
            System.out.println("already deleted");
        }
    }

    protected static String loadFileFromResources(String resourceFileDir){
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines( Paths.get(resourceFileDir), StandardCharsets.UTF_8))
        {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    public static void uploadReport (String reportName, boolean isUploadNow){
        String fileDir = String.format("%s/%s", System.getProperty("user.dir"),"target");
        String today = String.format("%s%s%s", DriverUtils.currentDateInfo("yyyy")
                , DriverUtils.currentDateInfo("mm")
                , DriverUtils.currentDateInfo("dd"));


        if (reportName.contains("2")){
            fileDir = String.format("%s/%s%s", fileDir, reportName.split("2")[0], reportName.split("2")[1]);
        } else {
            fileDir = String.format("%s/%s", fileDir, reportName);
        }
        log.info("fileDir" + fileDir);
        if ((new File(fileDir)).isFile()){
            String cmd = String.format("aws s3 cp %s s3://tact-automation-reports/%s/%s"
                    , fileDir, today, reportName);
            log.info("cmd ==> " + cmd);

            if (isUploadNow) {
                log.info(DriverUtils.getRunCommandReturn(new String[]{"bash", "-c", cmd}));
                System.out.println("upload is done");
            }
//            log.info(DriverUtils.getRunCommandReturn(cmd));
        } else {
            log.warning("Please check the file dir");
        }
    }

    public static void uploadAIReport(boolean isUploadNow){
        String fileDir = String.format("%s/%s", System.getProperty("user.dir"),"target");
        String today = String.format("%s%s%s", DriverUtils.currentDateInfo("yyyy")
                , DriverUtils.currentDateInfo("mm")
                , DriverUtils.currentDateInfo("dd"));
        String hour = DriverUtils.currentDateInfo("24hh");

        fileDir = String.format("%s/aiReport.html", fileDir);
        log.info("fileDir : " + fileDir);
        if ((new File(fileDir)).isFile()) {
            String cmd = String.format("aws s3 cp %s s3://tact-automation-reports/%s/aiReport-%s.html"
                    , fileDir, today, hour);
            log.info("cmd ==> " + cmd);

            if (isUploadNow){
//                /Users/Shared/Jenkins/home/workspace/qa-automation-ai or /Users/Shared/Jenkins/home/workspace/qa-automation-ios
                if (System.getProperty("user.dir").contains("sakie")){
                    System.out.println("now in local");
                    log.info(DriverUtils.getRunCommandReturn(new String[]{"bash", "-c", cmd}));
                } else {
//                    /Users/qa/Library/Python/2.7/bin//aws
                    cmd = String.format("%s%s", "/Users/qa/Library/Python/2.7/bin//", cmd);
                    System.out.println("now in Jenkins slave Mac mini cmd : " + cmd);
                    log.info(DriverUtils.getRunCommandReturn(cmd));
                }
                System.out.println("upload is done");
            }

        } else {
            log.warning("Please check the file dir");
        }
    }

    public static void main(String[] args) {
//        BasicConfigurator.configure();
//        getReport(WebDriverPlatform.ANDROID);
//        getReport(WebDriverPlatform.IOS);
//        GenerateReport.deleteAllJsonReport("ios");

//        //submit report
//        System.out.println(System.getProperty("user.dir"));

//        generteHtml();
//        uploadReport("report.html", false);
//        generateAIHtml();
//        uploadReport("aiReport.html", false);
//
//        uploadReport("report.html", false);
//        uploadReport("aiReport.html", false);

//        generateAIHtml();
//        uploadReport("aiReport2.html", true);

//        uploadAIReport();


//        String fromFile = "https://rink.hockeyapp.net/api/2/apps/63a92edb44f544f6809959332a92d56f/app_versions/85?format=apk&avtoken=478a9e794ef326d91fd367e401b902dfb2c92f76";
//        String toFile = String.format("%s/Applications/TactApplicationTesting.apk", System.getProperty("user.dir"));    //"/Users/sakie/workspace/automation/test-automation"
//
//        DriverUtils.deleteFile(toFile);
//
//        File testDel = new File(toFile);
//        if (!testDel.exists()){
//            System.out.println("the file already deleted");
//            try {
//                FileUtils.copyURLToFile(new URL(fromFile), new File(toFile), 10000, 10000);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }


    }
}
