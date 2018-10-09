package ai.tact.qa.automation.steps.h5Steps;

import ai.tact.qa.automation.asserts.TactAIAsserts;
import ai.tact.qa.automation.testcomponents.h5.Thread.ThreadTimelinePage;
import ai.tact.qa.automation.utils.DriverUtils;
import ai.tact.qa.automation.utils.LogUtil;
import ai.tact.qa.automation.utils.dataobjects.Status;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;
import cucumber.api.java8.En;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadTimelineSteps implements En {

    private static final Logger log = LogUtil.setLoggerHandler(Level.ALL);

    private String dataRecord;
    private long botRespTime = 0;
    private String dateTimeStamp;

    public ThreadTimelineSteps() {

        And("^ThreadTimeline: I click \"(Tact)\" option$", (String option) -> {
            log.info("^ThreadTimeline: I click " + option + " option$");
            ThreadTimelinePage threadTimelinePage = new ThreadTimelinePage();

            WebDriverWaitUtils.waitUntilElementIsVisible(threadTimelinePage.getTactProBotImage().getLocator());
            threadTimelinePage.getTactProBotImage().click(threadTimelinePage.getSendMsgTextAreaTextField().getLocator());
        });
        When("^ThreadTimeline: I send \"([^\"]*)\" to \"(Thread|Thread dev1)\" Assistant and \"([^\"]*)\" verify sent msg$", (String inputText, String stage, String isVerify) -> {
            log.info("^ThreadTimeline: I send " + inputText + " to Assistant and " + isVerify + " verify sent msg$");
            ThreadTimelinePage threadTimelinePage = new ThreadTimelinePage();

            //save sent cmd to report.txt
            dataRecord = null;
            dataRecord = String.format("%s | %s | ", stage, inputText);
//            System.out.println(">>>>>dataRecord : " + dataRecord + "<<<<<<<,");
//            DriverUtils.writeToFile("target/aiTestingReport.txt", dataRecord, true);

            WebDriverWaitUtils.waitUntilElementIsVisible(threadTimelinePage.getThreadTimelineActiveLabel().getLocator());
            String inputTextString = String.format("%s\n",inputText);


            //previous Msg
            int sizeNum = threadTimelinePage.getTactBotMsgsLabel().getElements().size();
            threadTimelinePage.getSendMsgTextAreaTextField().type(inputTextString);
            long beginTime = System.currentTimeMillis();
            dataRecord += DriverUtils.getDateTimeDetailsStamp();
            long checkTime = beginTime;
            System.out.println(sizeNum + "<== beginTime ==> " + beginTime);
            while (threadTimelinePage.getTactBotMsgsLabel().getElements().size() == sizeNum){
                checkTime = System.currentTimeMillis();
                if ((checkTime - beginTime)/1000 > 60){
                    break;
                }
//                System.out.println(threadTimelinePage.getTactBotMsgsLabel().getElements().size() + "<== the size is the same");
            }
            long endTime = System.currentTimeMillis();
            botRespTime = endTime - beginTime;

            System.out.println("time " + botRespTime);

            int userSentMsgNum = threadTimelinePage.getMyMsgsLabel().getElements().size();
            String userSentMsg = threadTimelinePage.getMyMsgsLabel().getElements().get(userSentMsgNum-1).getText();

            if (isVerify.equalsIgnoreCase("with")) {
                log.info("start checking the sent msg");
                System.out.println("inputText " + inputText);
                System.out.println("userSentMsg " + userSentMsg);
                TactAIAsserts.assertEquals(userSentMsg, inputText, userSentMsg + " " + inputText + " should equal");
            }
        });
        Then("^ThreadTimeline: I check bot \"([^\"]*)\"$", (String responseText) -> {
            log.info("^ThreadTimeline: I check bot " + responseText + "$");
            ThreadTimelinePage threadTimelinePage = new ThreadTimelinePage();

            //get the latest display msg from bot
            int botReplyMstNum = threadTimelinePage.getTactBotMsgsLabel().getElements().size();
            String botReplyMsg = threadTimelinePage.getTactBotMsgsLabel().getElements().get(botReplyMstNum-1).getText();
            Status isPassed = Status.failed;

            System.out.println(botReplyMsg);

            //verify the bot reply msg
            //response values in expected list
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

            //record cmd info
            dataRecord = String.format("%s | %s | %sms\n", dataRecord, isPassed, botRespTime);
            if (dataRecord.split("\\|").length == 5) {
                System.out.println(">>>>>dataRecord : " + dataRecord + "<<<<<<<,");
                DriverUtils.writeToFile("target/aiTestingReport.txt", dataRecord, true);
            } else {
                System.out.println(">>>>>wrong dataRecord : " + dataRecord + "<<<<<<<,");
            }

        });
    }
}
