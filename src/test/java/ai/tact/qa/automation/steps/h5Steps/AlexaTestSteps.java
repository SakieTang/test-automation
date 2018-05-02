package ai.tact.qa.automation.steps.h5Steps;

import ai.tact.qa.automation.asserts.TactAIAsserts;
import ai.tact.qa.automation.testcomponents.h5.Alexa.AlexaTestPage;
import ai.tact.qa.automation.testcomponents.h5.CiscoSpark.SparkHomePage;
import ai.tact.qa.automation.utils.DriverUtils;
import ai.tact.qa.automation.utils.LogUtil;
import ai.tact.qa.automation.utils.dataobjects.Status;
import com.paypal.selion.platform.grid.Grid;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;
import cucumber.api.java8.En;
import org.openqa.selenium.WebElement;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AlexaTestSteps implements En {

    private AlexaTestPage alexaTestPage ;

    private Logger log = LogUtil.setLoggerHandler(Level.ALL);

    private WebElement webElement;
    private String inputText;
    private String dataRecord;
    private long botRespTime = 0;

    public AlexaTestSteps() {

        alexaTestPage = new AlexaTestPage();

        And("^AlexaTest: I click \"(testEnabledSkill)\" option$", (String option) -> {
            log.info("^AlexaTest: I click " + option + " option$");

            WebDriverWaitUtils.waitUntilElementIsVisible(alexaTestPage.getTestSkillLabel().getLocator());
        });
        When("^AlexaTest: I send \"([^\"]*)\" to \"(Alexa|Alexa dev1)\" Assistant and \"([^\"]*)\" verify sent msg$", (String inputText, String stage, String isVerify) -> {
            log.info("^AlexaTest: I send " + inputText + " to Alexa Assistant and " + isVerify + " verify sent msg$");

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
            WebDriverWaitUtils.waitUntilElementIsVisible(alexaTestPage.getTacBotActiveReplyMsgLabel().getLocator());
//            while ( alexaTestPage.getTacBotActiveReplyMsgLabel().getElements().size() == 0 ){
//                checkTime = System.currentTimeMillis();
//                if ( (checkTime - beginTime)/1000 > 120 ){
//                    break;
//                }
//            }
            long endTime = System.currentTimeMillis();
            botRespTime = endTime - beginTime;

            System.out.println("time " + botRespTime);

            int userSentMsgNum = alexaTestPage.getMyMsgsListLabel().getElements().size();
            String userSentMsg = alexaTestPage.getMyMsgsListLabel().getElements().get(userSentMsgNum-1).getText();

            if ( isVerify.equalsIgnoreCase("with") ) {
                log.info("start checking the sent msg");
                System.out.println("inputText " + inputText);
                System.out.println("userSentMsg " + userSentMsg);
                TactAIAsserts.assertEquals(userSentMsg, inputText, userSentMsg + " " + inputText + " should equal");
            }
        });
        Then("^AlexaTest: I check bot \"([^\"]*)\"$", (String responseText) -> {
            log.info("^AlexaTest: I check bot " + responseText + "$");

            //get the latest display msg from bot
            String tactBotMsgsLabelLocator;
            int botReplyMstNum;
            String botReplyMsg;
            Status isPassed = Status.failed;

            //get the latest display msg from bot
            if (Grid.driver().findElementsByXPath(alexaTestPage.getTacBotActiveReplyMsgLabel().getLocator()).size()!=0) {
                //get bot msg
                botReplyMsg = alexaTestPage.getTacBotActiveReplyMsgLabel().getText();

                //Verify
                if (responseText.isEmpty()) {
                    //
                    //opptyName, closeDate, stage and probability
                    if (botReplyMsg.contains("Summary") && botReplyMsg.contains("Close Date") &&
                            botReplyMsg.contains("Stage") && botReplyMsg.contains("Probability")) {
                        isPassed = Status.passed;
                    } else {
                        System.out.println("it is fail by \n" +
                                "currentMsg.contains(\"deals\") && currentMsg.contains(\"Summary\") && " +
                                "currentMsg.contains(\"Close Date\") && currentMsg.contains(\"Stage\") " +
                                "&& currentMsg.contains(\"Probability\")");
                        isPassed = Status.failed;
                    }
                } else {
                    String[] responseList = responseText.split("; ");
                    isPassed = Status.failed;
                    for (String s : responseList) {
                        if (botReplyMsg.equals(s)) {
                            isPassed = Status.passed;
                            break;
                        }
                    }
                }
            } else {
                isPassed = Status.failed;
                botRespTime = 0;
            }

            //record cmd info
            dataRecord = String.format("%s | %sms\n", isPassed, botRespTime);
//            String dataRecord = inputText + " | " + isPassed + " | " + botRespTime + "ms";
            System.out.println(">>>>>dataRecord : " + dataRecord + "<<<<<<<,");
            DriverUtils.writeToFile("target/aiTestingReport.txt", dataRecord, true);

        });
    }
}
