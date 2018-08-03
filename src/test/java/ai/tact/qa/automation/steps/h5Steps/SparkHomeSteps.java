package ai.tact.qa.automation.steps.h5Steps;

import ai.tact.qa.automation.asserts.TactAIAsserts;
import ai.tact.qa.automation.testcomponents.h5.CiscoSpark.SparkHomePage;
import ai.tact.qa.automation.testcomponents.h5.Thread.ThreadTimelinePage;
import ai.tact.qa.automation.utils.DriverUtils;
import ai.tact.qa.automation.utils.LogUtil;
import ai.tact.qa.automation.utils.dataobjects.Status;
import com.paypal.selion.platform.grid.Grid;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;
import cucumber.api.java8.En;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SparkHomeSteps implements En {

    private static final Logger log = LogUtil.setLoggerHandler(Level.ALL);

    private String dataRecord;
    private long botRespTime = 0;
    private String dateTimeStamp;

    public SparkHomeSteps() {

        And("^SparkHome: I click \"(TactProdBot)\" option$", (String option) -> {
            log.info("^SparkHome: I click " + option + " option$");
            SparkHomePage sparkHomePage = new SparkHomePage();

            WebDriverWaitUtils.waitUntilElementIsVisible(sparkHomePage.getAccountButton().getLocator());

            sparkHomePage.getTactProdBotButton().click();
            System.out.println("after click prod bot button");
            DriverUtils.sleep(10);
            System.out.println("after 10 sec wait");
            DriverUtils.sleep(5);
        });
        When("^SparkHome: I send \"([^\"]*)\" to \"(Spark|Spark dev1)\" Assistant and \"([^\"]*)\" verify sent msg$", (String inputText, String stage, String isVerify) -> {
            log.info("^SparkHome: I send " + inputText + " to " + stage + " Assistant and " + isVerify + " verify sent msg$");
            SparkHomePage sparkHomePage = new SparkHomePage();

            //save sent cmd to report.txt
            dataRecord = String.format("%s | %s | ", stage, inputText);
            System.out.println(">>>>>dataRecord : " + dataRecord + "<<<<<<<,");
            DriverUtils.writeToFile("target/aiTestingReport.txt", dataRecord, true);

            WebDriverWaitUtils.waitUntilElementIsVisible(sparkHomePage.getSendMsgTextAreaTextField().getLocator());
            String inputTextString = String.format("%s\n",inputText);


            //send Msg
            String tactBotMsgsLabelLocator = sparkHomePage.getTactBotMsgsLabel().getLocator();
            if (inputText.contains("What is the latest on") && !inputText.contains("xyz")) {
                tactBotMsgsLabelLocator = sparkHomePage.getTactBotMsgsReplyListLabel().getLocator();
            }
            System.out.println("tactBotMsgsLabelLocator ==> " + tactBotMsgsLabelLocator);
            sparkHomePage.getSendMsgTextAreaTextField().type(inputTextString);
            int sizeNum = Grid.driver().findElementsByXPath(tactBotMsgsLabelLocator).size();
            long beginTime = System.currentTimeMillis();
            dataRecord = DriverUtils.getDateTimeDetailsStamp();
            long checkTime = beginTime;
            System.out.println(sizeNum + "<== beginTime ==> " + beginTime);
            while (Grid.driver().findElementsByXPath(tactBotMsgsLabelLocator).size() == sizeNum){
                checkTime = System.currentTimeMillis();
                if ( (checkTime - beginTime)/1000 > 60 ){
                    break;
                }
                System.out.println(Grid.driver().findElementsByXPath(tactBotMsgsLabelLocator).size() + "<== the size is the same");
            }
            long endTime = System.currentTimeMillis();
            botRespTime = endTime - beginTime;

            System.out.println("time " + botRespTime);

            String tactUserMsgsLabelLocator = sparkHomePage.getMyMsgsLabel().getLocator();
            List<WebElement> webMyMsgEleS = Grid.driver().findElementsByXPath(tactUserMsgsLabelLocator);
            int mySentMsgNum = webMyMsgEleS.size();
            String userSentMsg = webMyMsgEleS.get( mySentMsgNum - 1 ).getText();

            if (isVerify.equalsIgnoreCase("with")) {
                log.info("start checking the sent msg");
                System.out.println("inputText " + inputText);
                System.out.println("userSentMsg " + userSentMsg);
                TactAIAsserts.assertEquals(userSentMsg, inputText, userSentMsg + " " + inputText + " should equal");
            }
        });
        Then("^SparkHome: I check bot \"([^\"]*)\"$", (String responseText) -> {
            log.info("^SparkHome: I check bot " + responseText + "$");
            SparkHomePage sparkHomePage = new SparkHomePage();

            //get the latest display msg from bot
            String tactBotMsgsLabelLocator;
            int botReplyMstNum;
            String botReplyMsg;
            Status isPassed = Status.failed;

            //verify the bot reply msg
            //response values in expected list
            if (responseText.isEmpty()) {
                //respnseText
                tactBotMsgsLabelLocator = sparkHomePage.getTactBotMsgsReplyListLabel().getLocator();
                botReplyMstNum = Grid.driver().findElementsByXPath(tactBotMsgsLabelLocator).size();
                botReplyMsg = Grid.driver().findElementsByXPath(tactBotMsgsLabelLocator).get(botReplyMstNum-1).getText();

                System.out.println("reply \n" + botReplyMsg);

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
                //respnseText
                tactBotMsgsLabelLocator = sparkHomePage.getTactBotMsgsLabel().getLocator();
                botReplyMstNum = Grid.driver().findElementsByXPath(tactBotMsgsLabelLocator).size();
                botReplyMsg = Grid.driver().findElementsByXPath(tactBotMsgsLabelLocator).get(botReplyMstNum-1).getText();

                System.out.println("reply \n" + botReplyMsg);

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
            System.out.println(">>>>>dataRecord : " + dataRecord + "<<<<<<<,");
            DriverUtils.writeToFile("target/aiTestingReport.txt", dataRecord, true);

        });
    }

}
