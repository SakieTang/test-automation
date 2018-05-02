package ai.tact.qa.automation.steps.h5Steps;

import ai.tact.qa.automation.asserts.TactAIAsserts;
import ai.tact.qa.automation.testcomponents.h5.Alexa.AlexaTestPage;
import ai.tact.qa.automation.testcomponents.h5.CiscoSpark.SparkHomePage;
import ai.tact.qa.automation.utils.CustomPicoContainer;
import ai.tact.qa.automation.utils.DriverUtils;
import ai.tact.qa.automation.utils.LogUtil;
import ai.tact.qa.automation.utils.dataobjects.AlexaResponseInfo;
import ai.tact.qa.automation.utils.dataobjects.Status;
import com.paypal.selion.platform.grid.Grid;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;
import cucumber.api.PendingException;
import cucumber.api.java8.En;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.awt.*;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AlexaTestSteps implements En {

    private static final Logger log = LogUtil.setLoggerHandler(Level.ALL);

    private String cmd = null;
    private String dataRecord;
    private String stage;
    private String input;
    private long botRespTime = 0;
    private Status isPassed = Status.failed;

    public AlexaTestSteps() {

        And("^AlexaTest: I click \"(testEnabledSkill)\" option$", (String option) -> {
            log.info("^AlexaTest: I click " + option + " option$");
            AlexaTestPage alexaTestPage = new AlexaTestPage();

            WebDriverWaitUtils.waitUntilElementIsVisible(alexaTestPage.getTestSkillLabel().getLocator());
        });
        When("^AlexaTest: I \"([^\"]*)\" cancel session$", (String isCancel) -> {
            log.info("^AlexaTest: I cancel session$");

            //end and active session
            if (isCancel.equalsIgnoreCase("true"))
            {
                activeTact();
                endTactSession();
            }
//            activeTact();

        });
        Then("^AlexaTest: I send \"([^\"]*)\" to \"(Alexa|Alexa dev1)\" Assistant and \"([^\"]*)\" verify send msg$", (String cmd, String stage, String isVerify) -> {
            log.info("^AlexaTest: I send " + cmd + " to " + stage + " Assistant and " + isVerify + " verify send msg$");
            AlexaTestPage alexaTestPage = new AlexaTestPage();
            this.cmd = cmd;
            this.stage = stage;

            //get input String
            Hashtable<String, Object> allCmdInfo = CustomPicoContainer.getInstance().getAlexaResponseInfos();
            input = String.format("%s", ((AlexaResponseInfo) allCmdInfo.get(cmd)).getInput());

//            //save sent cmd to report.txt
//            dataRecord = String.format("%s  | %s | ", stage, input);
//            System.out.println(">>>>>dataRecord : " + dataRecord + "<<<<<<<,");
//            DriverUtils.writeToFile("target/aiTestingReport.txt", dataRecord, true);

            WebDriverWaitUtils.waitUntilElementIsVisible(alexaTestPage.getSendMsgTextAreaTextField().getLocator());
            String inputTextString = String.format("%s\n",input);

            System.out.println("locator : " + alexaTestPage.getSendMsgTextAreaTextField().getLocator());

            //send Msg
            alexaTestPage.getSendMsgTextAreaTextField().type(inputTextString);
            System.out.println("input ==> " + inputTextString);
            long beginTime = System.currentTimeMillis();
            long checkTime = beginTime;
            WebDriverWaitUtils.waitUntilElementIsVisible(alexaTestPage.getMyMsgSpinnerLabel().getLocator());
            while ( Grid.driver().findElementsByCssSelector(alexaTestPage.getTacBotActiveReplyMsgLabel().getLocator().substring(4)).size() == 0 ){
                checkTime = System.currentTimeMillis();
                if ((checkTime - beginTime)/1000 > 60){
                    break;
                }
            }
            long endTime = System.currentTimeMillis();
            botRespTime = endTime - beginTime;

            System.out.println("time " + botRespTime);
        });
        And("^AlexaTest: I \"([^\"]*)\" check bot \"(jSonOutputSpeechSSML|jSonOutputCardContent|jSonOutputRepromptSSML|jSonOutputShouldEndSession)\" response$", (String isCheck, String outputType) -> {
            log.info("^AlexaTest: I " + isCheck + " check bot " + outputType + " response$");
            AlexaTestPage alexaTestPage = new AlexaTestPage();
            WebDriverWaitUtils.waitUntilElementIsVisible(alexaTestPage.getJSonOutputSpeechSSMLLabel().getLocator());

            Hashtable<String, Object> allUsers = CustomPicoContainer.getInstance().getAlexaResponseInfos();
            String expectedOutput = "";
            String jSonOutput = "";
            isPassed = Status.failed;

            switch (outputType) {
                case "jSonOutputSpeechSSML":
                    expectedOutput= String.format("%s", ((AlexaResponseInfo) allUsers.get(cmd)).getOutputSpeechSSML()).replaceAll(":\\\\", ":" );

                    if (alexaTestPage.getJSonOutputSpeechSSMLLabel().getElements().size() > 1) {
                        //resize the screen
                        if (DriverUtils.getCurrentBrowserScreenWidth() < DriverUtils.getCurrentMonitorScreenWidth()) {
                            DriverUtils.resizeBrowserToMonitorSize();
                        }

                        System.out.println("more than one element");
                        List<WebElement> jSonOutputSpeechSSMLS=alexaTestPage.getJSonOutputSpeechSSMLLabel().getElements();
                        jSonOutput=getListElemenetText(jSonOutputSpeechSSMLS, "\\\"");
                    } else {
                        System.out.println("only one element");
                        jSonOutput=alexaTestPage.getJSonOutputSpeechSSMLLabel().getText();
                    }
                    System.out.println("expectedOutput : " + expectedOutput + "\njSonOutputSpeechSSML : " + jSonOutput);
                    break;
                case "jSonOutputCardContent":
                    expectedOutput=String.format("%s", ((AlexaResponseInfo) allUsers.get(cmd)).getCardContent()).replaceAll(":\\\\", ":" );

                    if (alexaTestPage.getJSonOutputCardContentLabel().getElements().size() > 1) {
                        System.out.println("more than one element");
                        List<WebElement> jSonOutputCardContentS=alexaTestPage.getJSonOutputCardContentLabel().getElements();
                        jSonOutput=getListElemenetText(jSonOutputCardContentS, "\\n");
                    } else {
                        System.out.println("only one element");
                        jSonOutput=alexaTestPage.getJSonOutputCardContentLabel().getText();
                    }
                    System.out.println("\njSonOutputCardContent : " + jSonOutput);
                    break;
                case "jSonOutputRepromptSSML":
                    System.out.println("before if jSonOutputRepromptSSML check");
                    if (!cmd.equalsIgnoreCase("endSession")) {
                        expectedOutput=String.format("%s", ((AlexaResponseInfo) allUsers.get(cmd)).getRepromptSSML()).replaceAll(":\\\\", ":" );
                        if (alexaTestPage.getJSonOutputRepromptSSMLLabel().getElements().size() > 1) {
                            System.out.println("more than one element");
                            List<WebElement> jSonOutputRepromptSSMLS=alexaTestPage.getJSonOutputRepromptSSMLLabel().getElements();
                            jSonOutput=getListElemenetText(jSonOutputRepromptSSMLS, "\\\"");
                        } else {
                            System.out.println("only one element");
                            jSonOutput=alexaTestPage.getJSonOutputRepromptSSMLLabel().getText();
                        }
                        System.out.println("\njSonOutputRepromptSSML : " + jSonOutput);
                    }
                    System.out.println("after if jSonOutputRepromptSSML check");
                    break;
                case "jSonOutputShouldEndSession":
                    expectedOutput=String.valueOf(((AlexaResponseInfo) allUsers.get(cmd)).getShouldEndSession());

                    if (cmd.equalsIgnoreCase("endSession")) {
                        //use cancel to end session
                        List<WebElement> jSonOutputEndTactSkillShouldEndSessionLabelS=alexaTestPage.getJSonOutputEndTactSkillShouldEndSessionLabel().getElements();
                        jSonOutput=getListElemenetText(jSonOutputEndTactSkillShouldEndSessionLabelS, "\\\"");
                    } else {
                        List<WebElement> jSonOutputShouldEndSessionLabelS=alexaTestPage.getJSonOutputShouldEndSessionLabel().getElements();
                        jSonOutput=getListElemenetText(jSonOutputShouldEndSessionLabelS, "\\\"");
                    }
                    System.out.println("\njSonOutputShouldEndSession : " + jSonOutput);
                    break;
            }

            if (isCheck.equalsIgnoreCase("true")) {
                isPassed = Status.failed;
                if (jSonOutput.contains(expectedOutput)) {  //expectedOutput.contains(jSonOutput)
                    isPassed = Status.passed;
                } else if ( outputType.equalsIgnoreCase("jSonOutputCardContent") && jSonOutput.replaceAll("\\n","\\n\\n").contains(expectedOutput)) {
                    isPassed=Status.passed;
                } else {
                    isPassed = Status.failed;
                }

                //record cmd info
                if (cmd.equalsIgnoreCase("endReportBug")) {
                    input = input + "-" + cmd;
                }

                dataRecord = String.format("%s  | %s - %s | %s | %sms\n", stage, input, outputType, isPassed, botRespTime);
                DriverUtils.writeToFile("target/aiTestingReport.txt", dataRecord, true);
            }

        });
        And("^AlexaTest: I check bot response text$", () -> {
            log.info("^AlexaTest: I check bot response text$");
            AlexaTestPage alexaTestPage = new AlexaTestPage();

            WebDriverWaitUtils.waitUntilElementIsVisible(alexaTestPage.getJSonOutputSpeechSSMLLabel().getLocator());

            Hashtable<String, Object> allUsers = CustomPicoContainer.getInstance().getAlexaResponseInfos();
            String expectedOutputSpeechSSML = String.format("%s", ((AlexaResponseInfo) allUsers.get(cmd)).getOutputSpeechSSML()).replaceAll(":\\\\", ":" );
            String expectedCardContent = String.format("%s", ((AlexaResponseInfo) allUsers.get(cmd)).getCardContent()).replaceAll(":\\\\", ":" );
            String expectedRepromptSSML = String.format("%s", ((AlexaResponseInfo) allUsers.get(cmd)).getRepromptSSML()).replaceAll(":\\\\", ":" );
            String expectedShouldEndSession = String.valueOf (((AlexaResponseInfo) allUsers.get(cmd)).getShouldEndSession());

            System.out.println("\nexpectedOutputSpeechSSML : " + expectedOutputSpeechSSML);
            System.out.println("\nexpectedCardContent : " + expectedCardContent);
            System.out.println("\nexpectedRepromptSSML : " + expectedRepromptSSML);
            System.out.println("\nexpectedShouldEndSession : " + expectedShouldEndSession);

            /**
             * check  bot
             */
            //get the latest display msg from bot
            String tactBotMsgsLabelLocator;
            int botReplyMstNum;
            String botReplyMsg;
            String jSonOutputSpeechSSML;
            String jSonOutputCardContent;
            String jSonOutputRepromptSSML;
            String jSonOutputShouldEndSession;
            Status isPassed = Status.failed;

            //get bot msg
            botReplyMsg=alexaTestPage.getTacBotActiveReplyMsgLabel().getText();
            System.out.println("\nbotReplyMsg : " + botReplyMsg);
            //get jSon bot Speech
            jSonOutputSpeechSSML = "";
            if (alexaTestPage.getJSonOutputSpeechSSMLLabel().getElements().size() > 1) {
                //resize the screen
                if (DriverUtils.getCurrentBrowserScreenWidth() < DriverUtils.getCurrentMonitorScreenWidth()) {
                    DriverUtils.resizeBrowserToMonitorSize();
                }

                System.out.println("more than one element");
                List<WebElement> jSonOutputSpeechSSMLS = alexaTestPage.getJSonOutputSpeechSSMLLabel().getElements();
                jSonOutputSpeechSSML = getListElemenetText(jSonOutputSpeechSSMLS, "\\\"");
            } else {
                System.out.println("only one element");
                jSonOutputSpeechSSML = alexaTestPage.getJSonOutputSpeechSSMLLabel().getText();
            }
            System.out.println("\njSonOutputSpeechSSML : " + jSonOutputSpeechSSML);

            //get jSon bot card
            jSonOutputCardContent = "";
            if (alexaTestPage.getJSonOutputCardContentLabel().getElements().size() > 1) {
                System.out.println("more than one element");
                List<WebElement> jSonOutputCardContentS = alexaTestPage.getJSonOutputCardContentLabel().getElements();
                jSonOutputCardContent = getListElemenetText(jSonOutputCardContentS, "\\n\\n");
            } else {
                System.out.println("only one element");
                jSonOutputCardContent = alexaTestPage.getJSonOutputCardContentLabel().getText();
            }
            System.out.println("\njSonOutputCardContent : " + jSonOutputCardContent);

            //get jSon rePrompt
            jSonOutputRepromptSSML = "";
            if (!cmd.equalsIgnoreCase("endSession")) {
                if (alexaTestPage.getJSonOutputRepromptSSMLLabel().getElements().size() > 1) {
                    System.out.println("more than one element");
                    List<WebElement> jSonOutputRepromptSSMLS = alexaTestPage.getJSonOutputRepromptSSMLLabel().getElements();
                    jSonOutputRepromptSSML = getListElemenetText(jSonOutputRepromptSSMLS, "\\\"");
                } else {
                    System.out.println("only one element");
                    jSonOutputRepromptSSML = alexaTestPage.getJSonOutputRepromptSSMLLabel().getText();
                }
                System.out.println("\njSonOutputRepromptSSML : " + jSonOutputRepromptSSML);
            }

            //get jSon endSession
            jSonOutputShouldEndSession = "";
            if (cmd.equalsIgnoreCase("endSession")) {
                //use cancel to end session
                jSonOutputShouldEndSession = alexaTestPage.getJSonOutputEndTactSkillShouldEndSessionLabel().getText();
            } else {
                jSonOutputShouldEndSession = alexaTestPage.getJSonOutputShouldEndSessionLabel().getText();
            }
            System.out.println("\njSonOutputShouldEndSession : " + jSonOutputShouldEndSession);

            /**
             *
             String jSonOutputCardContent;
             String jSonOutputRepromptSSML;
             String jSonOutputShouldEndSession;
             Status isPassed = Status.failed;
             */


            //check jSonOutputSpeechSSML value
            isPassed = Status.failed;
            if (jSonOutputSpeechSSML.contains(expectedOutputSpeechSSML)) {  //expectedOutputSpeechSSML.contains(jSonOutputSpeechSSML)
                isPassed = Status.passed;
            } else {
                isPassed = Status.failed;
            }

            //check jSonOutputCardContent value
            isPassed = Status.failed;
            if (jSonOutputCardContent.contains(expectedCardContent)) {  //expectedCardContent.contains(jSonOutputCardContent)
                isPassed = Status.passed;
            } else if (jSonOutputCardContent.replaceAll("\\n","\\n\\n").contains(expectedCardContent)) {
                isPassed=Status.passed;
            } else {
                isPassed = Status.failed;
            }

            //check jSonOutputRepromptSSML value
            if (!cmd.equalsIgnoreCase("endSession")) {
                isPassed = Status.failed;
                if (jSonOutputRepromptSSML.contains(expectedRepromptSSML)) {  //expectedRepromptSSML.contains(jSonOutputRepromptSSML)
                    isPassed = Status.passed;
                } else {
                    isPassed = Status.failed;
                }
            }

            //check jSonOutputShouldEndSession value
            isPassed = Status.failed;
            if (jSonOutputShouldEndSession.contains(String.valueOf(expectedShouldEndSession))) {  //expectedShouldEndSession.contains(jSonOutputShouldEndSession)
                isPassed = Status.passed;
            } else {
                isPassed = Status.failed;
            }

            //record cmd info
            dataRecord = String.format("%s | %sms\n", isPassed, botRespTime);
            System.out.println(">>>>>dataRecord : " + dataRecord + "<<<<<<<,");
            DriverUtils.writeToFile("target/aiTestingReport.txt", dataRecord, true);
        });
        When("^AlexaTest: I send \"([^\"]*)\" to \"(Alexa|Alexa dev1)\" Assistant and check bot response$", (String cmd, String stage) -> {
            log.info("^AlexaTest: I send " + cmd + " to " + stage + " Assistant and check bot response$");
            AlexaTestPage alexaTestPage = new AlexaTestPage();

            Hashtable<String, Object> allCmdInfo = CustomPicoContainer.getInstance().getAlexaResponseInfos();
            String input = String.format("%s", ((AlexaResponseInfo) allCmdInfo.get(cmd)).getInput());

            String outputSpeechSSML = String.format("%s", ((AlexaResponseInfo) allCmdInfo.get(cmd)).getOutputSpeechSSML()); //<speak>Welcome to Tact. How can I help you?</speak>
            String cardContent = String.format("%s", ((AlexaResponseInfo) allCmdInfo.get(cmd)).getCardContent());
            String repromptSSML = String.format("%s", ((AlexaResponseInfo) allCmdInfo.get(cmd)).getRepromptSSML());
            boolean shouldEndSession = ((AlexaResponseInfo) allCmdInfo.get(cmd)).getShouldEndSession();

            System.out.println(input );
            System.out.println(outputSpeechSSML );
            System.out.println(cardContent );
            System.out.println(repromptSSML );
            System.out.println(shouldEndSession );

            //save sent cmd to report.txt
            dataRecord = String.format("%s  | %s | ", stage, input);
            System.out.println(">>>>>dataRecord : " + dataRecord + "<<<<<<<,");
            DriverUtils.writeToFile("target/aiTestingReport.txt", dataRecord, true);

            WebDriverWaitUtils.waitUntilElementIsVisible(alexaTestPage.getSendMsgTextAreaTextField().getLocator());
            String inputTextString = String.format("%s\n",input);

            System.out.println("locator : " + alexaTestPage.getSendMsgTextAreaTextField().getLocator());

            //send Msg
            alexaTestPage.getSendMsgTextAreaTextField().type(inputTextString);
            System.out.println("input ==> " + inputTextString);
            long beginTime = System.currentTimeMillis();
            long checkTime = beginTime;
            WebDriverWaitUtils.waitUntilElementIsVisible(alexaTestPage.getMyMsgSpinnerLabel().getLocator());
            while ( Grid.driver().findElementsByCssSelector(alexaTestPage.getTacBotActiveReplyMsgLabel().getLocator().substring(4)).size() == 0 ){
                checkTime = System.currentTimeMillis();
                if ((checkTime - beginTime)/1000 > 60){
                    break;
                }
            }
            long endTime = System.currentTimeMillis();
            botRespTime = endTime - beginTime;

            System.out.println("time " + botRespTime);

        });
        Then("^AlexaTesting: I send \"([^\"]*)\" to \"(Alexa|Alexa dev1)\" Assistant and check bot$", (String cmd, String stage) -> {
            log.info("^AlexaTest: I send " + cmd + " to " + stage + " Assistant and check bot$");
            AlexaTestPage alexaTestPage = new AlexaTestPage();

            Hashtable<String, Object> allUsers = CustomPicoContainer.getInstance().getAlexaResponseInfos();
            String input = String.format("%s", ((AlexaResponseInfo) allUsers.get(cmd)).getInput());
            String expectedOutputSpeechSSML = String.format("%s", ((AlexaResponseInfo) allUsers.get(cmd)).getOutputSpeechSSML());
            String expectedCardContent = String.format("%s", ((AlexaResponseInfo) allUsers.get(cmd)).getCardContent());
            String expectedRepromptSSML = String.format("%s", ((AlexaResponseInfo) allUsers.get(cmd)).getRepromptSSML());
            boolean expectedShouldEndSession = ((AlexaResponseInfo) allUsers.get(cmd)).getShouldEndSession();

            //save sent cmd to report.txt
            dataRecord = String.format("%s  | %s | ", stage, input);
            System.out.println(">>>>>dataRecord : " + dataRecord + "<<<<<<<,");
            DriverUtils.writeToFile("target/aiTestingReport.txt", dataRecord, true);

            WebDriverWaitUtils.waitUntilElementIsVisible(alexaTestPage.getSendMsgTextAreaTextField().getLocator());
            String inputTextString = String.format("%s\n",input);

            System.out.println("locator : " + alexaTestPage.getSendMsgTextAreaTextField().getLocator());

            //send Msg
            alexaTestPage.getSendMsgTextAreaTextField().type(inputTextString);
            System.out.println("input ==> " + inputTextString);
            long beginTime = System.currentTimeMillis();
            long checkTime = beginTime;
            WebDriverWaitUtils.waitUntilElementIsVisible(alexaTestPage.getMyMsgSpinnerLabel().getLocator());
            while ( Grid.driver().findElementsByCssSelector(alexaTestPage.getTacBotActiveReplyMsgLabel().getLocator().substring(4)).size() == 0 ){
                checkTime = System.currentTimeMillis();
                if ((checkTime - beginTime)/1000 > 60){
                    break;
                }
            }
            long endTime = System.currentTimeMillis();
            botRespTime = endTime - beginTime;

            System.out.println("time " + botRespTime);
        });
        When("^AlexaTest: I \"(do|don't)\" cancel session, then send \"([^\"]*)\" to \"(Alexa|Alexa dev1)\" Assistant and \"([^\"]*)\" verify sent msg$", (String isCancel, String inputText, String stage, String isVerify) -> {
            log.info("^AlexaTest: I send " + inputText + " to Alexa Assistant and " + isVerify + " verify sent msg$");
            AlexaTestPage alexaTestPage = new AlexaTestPage();

            //end and active session
            if (isCancel.equalsIgnoreCase("do"))
            {
                activeTact();
                endTactSession();
            }
            activeTact();

            //save sent cmd to report.txt
            dataRecord = String.format("%s  | %s | ", stage, inputText);
            System.out.println(">>>>>dataRecord : " + dataRecord + "<<<<<<<,");
            DriverUtils.writeToFile("target/aiTestingReport.txt", dataRecord, true);

            WebDriverWaitUtils.waitUntilElementIsVisible(alexaTestPage.getSendMsgTextAreaTextField().getLocator());
            String inputTextString = String.format("%s\n",inputText);

            System.out.println("locator : " + alexaTestPage.getSendMsgTextAreaTextField().getLocator());

            //send Msg
            alexaTestPage.getSendMsgTextAreaTextField().type(inputTextString);
            System.out.println("input ==> " + inputTextString);
            long beginTime = System.currentTimeMillis();
            long checkTime = beginTime;
            WebDriverWaitUtils.waitUntilElementIsVisible(alexaTestPage.getMyMsgSpinnerLabel().getLocator());
            while ( Grid.driver().findElementsByCssSelector(alexaTestPage.getTacBotActiveReplyMsgLabel().getLocator().substring(4)).size() == 0 ){
                checkTime = System.currentTimeMillis();
                if ((checkTime - beginTime)/1000 > 60){
                    break;
                }
            }
            long endTime = System.currentTimeMillis();
            botRespTime = endTime - beginTime;

            System.out.println("time " + botRespTime);

            int userSentMsgNum = alexaTestPage.getMyMsgsListLabel().getElements().size();
            String userSentMsg = alexaTestPage.getMyMsgsListLabel().getElements().get(userSentMsgNum-1).getText();

            if (isVerify.equalsIgnoreCase("with")) {
                log.info("start checking the sent msg");
                System.out.println("inputText " + inputText);
                System.out.println("userSentMsg " + userSentMsg);
                DriverUtils.sleep(60);
                TactAIAsserts.assertEquals(userSentMsg, inputText.toLowerCase(), userSentMsg + " " + inputText + " should equal");
            }
        });
    }

    private void endTactSession(){
        AlexaTestPage alexaTestPage = new AlexaTestPage();
        String reponseList = "cancelling; Goodbye.; Bye; See you later!";

        alexaTestPage.getSendMsgTextAreaTextField().type("cancel\n");
        WebDriverWaitUtils.waitUntilElementIsInvisible(alexaTestPage.getMyMsgSpinnerLabel().getLocator());
        WebDriverWaitUtils.waitUntilElementIsVisible(alexaTestPage.getTacBotActiveReplyMsgLabel().getLocator());

        System.out.println("output : " + alexaTestPage.getTacBotActiveReplyMsgLabel().getText());

        while ( !reponseList.contains(alexaTestPage.getTacBotActiveReplyMsgLabel().getText()) ) {
            alexaTestPage.getSendMsgTextAreaTextField().type("cancel\n");
            WebDriverWaitUtils.waitUntilElementIsInvisible(alexaTestPage.getMyMsgSpinnerLabel().getLocator());
            WebDriverWaitUtils.waitUntilElementIsVisible(alexaTestPage.getTacBotActiveReplyMsgLabel().getLocator());
        }

        System.out.println("cancel the Tact session");
    }

    private void activeTact(){
        AlexaTestPage alexaTestPage = new AlexaTestPage();
        alexaTestPage.getSendMsgTextAreaTextField().type("ask Tact\n");
        WebDriverWaitUtils.waitUntilElementIsInvisible(alexaTestPage.getMyMsgSpinnerLabel().getLocator());
        System.out.println("Active Tact seesion");
    }

    private String getListElemenetText(List<WebElement> elements, String connector) {

        String textString = "";
        JavascriptExecutor js = (JavascriptExecutor) Grid.driver();
        //connector : "\\\""  "\\n\\n"

        for (int i = 0; i < elements.size(); i++) {
            int scrollPix = 50;
            while (!elements.get(i).isDisplayed()) {
                String s=String.format("%s.animate({ scrollLeft: \"%dpx\" })", "$(\"div[id='right'] > div[class='ace_scroller']\")", scrollPix);
                js.executeScript(s);
                scrollPix += 50;
            }
            System.out.println(">>>>>>>>> " + elements.get(i).getText());
            if (i==0) {
                textString = elements.get(i).getText();
            } else {
                textString=textString + connector + elements.get(i).getText();
            }
        }

        return textString;
    }
}
