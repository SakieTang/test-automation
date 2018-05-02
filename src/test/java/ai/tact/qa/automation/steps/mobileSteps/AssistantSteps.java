package ai.tact.qa.automation.steps.mobileSteps;

import ai.tact.qa.automation.asserts.TactAIAsserts;
import ai.tact.qa.automation.testcomponents.mobile.TactAssistant.TactAssistantPage;
import ai.tact.qa.automation.utils.DriverUtils;
import ai.tact.qa.automation.utils.LogUtil;
import ai.tact.qa.automation.utils.dataobjects.Status;
import com.paypal.selion.platform.grid.Grid;
import com.paypal.selion.platform.mobile.elements.MobileElement;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;
import cucumber.api.java8.En;
import org.openqa.selenium.WebElement;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AssistantSteps implements En {

    private static final Logger log = LogUtil.setLoggerHandler(Level.ALL);

    private String dataRecord;
    private long botRespTime = 0;

    public AssistantSteps() {

        When("^Assistant: I send \"([^\"]*)\" to \"(Tact|Tact dev1)\" Assistant and \"([^\"]*)\" verify sent msg$", (String inputText, String stage, String isVerify) -> {
            log.info("^Assistant: I send " + inputText + " to Assistant and verify it$");
            TactAssistantPage tactAssistantPage = new TactAssistantPage();

            //save sent cmd to report.txt
            dataRecord = String.format("%s | %s | ", stage, inputText);
            System.out.println("typeDataRecord : " + dataRecord + "<<<<<<<,");
            DriverUtils.writeToFile("target/aiTestingReport.txt", dataRecord, true);

            WebDriverWaitUtils.waitUntilElementIsVisible(tactAssistantPage.getTactAssistantTitleLabel());

            //previous Msg
            MobileElement mobileElement = tactAssistantPage.getDisplayTextListLabel();

            String previousMsg = getLabelTextAtIndex(mobileElement, labelCount(mobileElement) - 1);
            int beforeSendMsgNum = labelCount(mobileElement);
            log.info("previousMsg ==> " + previousMsg);

            //sent msg to Assistant
            tactAssistantPage.getAssistantTypeInTextField().sendKeys(inputText);
            tactAssistantPage.getAssistantSendButton().tap();

            //start time
//            1s=1000ms，1 ms=1000μs，1μs=1000ns
            long beginTime = System.currentTimeMillis();
            long checkTime = beginTime;

            //(System.currentTimeMillis() - checkTime)/1000000 < 120   1s = 1000 000 μs  check 120s => 2mins
            while ( !isBotReply(getEleXCoordinate(tactAssistantPage.getDisplayTextListLabel(), labelCount(tactAssistantPage.getDisplayTextListLabel()) -1 ))
                    ){
                checkTime = System.currentTimeMillis();
                if ((checkTime - beginTime)/1000 > 60) {
                    break;
                }
            }
            long endTime = System.currentTimeMillis();
            botRespTime = (endTime - beginTime);
            System.out.println("differ : " + (checkTime - beginTime)/1000 );
            System.out.println("time ms : " + botRespTime);
            System.out.println("time  s : " + botRespTime/1000);

            //get current display msg
            mobileElement = tactAssistantPage.getDisplayTextListLabel();
            String currentMsg = getLabelTextAtIndex(mobileElement, labelCount(mobileElement) - 1);
            int afterSendMsgNum = labelCount(mobileElement);

            //check the msg sent from
            if (isBotReply(getEleXCoordinate(mobileElement, labelCount(mobileElement) -1 ))) {
                currentMsg = getLabelTextAtIndex(mobileElement, labelCount(mobileElement) - 2 );
                afterSendMsgNum = labelCount(mobileElement);
//                System.out.println("Bot already replied and msg is " + currentMsg);
            }

            //verify the sent msg
            if (isVerify.equalsIgnoreCase("with")) {
                log.info("start checking the sent msg");
                System.out.println("currentMsg " + currentMsg);
                System.out.println("inputText " + inputText);
                TactAIAsserts.assertEquals(currentMsg, inputText, currentMsg + " " + inputText + " should equal");
            }

            //verify the details
            int diff = beforeSendMsgNum - afterSendMsgNum;

            if  (diff == 0 || !isBotReply(getEleXCoordinate(tactAssistantPage.getDisplayTextListLabel(), labelCount(tactAssistantPage.getDisplayTextListLabel()) -1 )) ) {
                log.warning("There is sth wrong w/ your app, the msg does not sent out.");
            }
            DriverUtils.tapXY(188,406);
        });
         Then("^Assistant: I check bot \"([^\"]*)\"$", (String responseText) -> {
            log.info("^Assistant: I check bot " + responseText + "$");
             TactAssistantPage tactAssistantPage = new TactAssistantPage();

            //get the latest display msg
             MobileElement mobileElement = tactAssistantPage.getDisplayTextListLabel();
            String currentMsg = getLabelTextAtIndex(mobileElement, labelCount(mobileElement) -1 );
            Status isPassed = Status.failed;

            //check whether it is the bot reply
            if (isBotReply( getEleXCoordinate(mobileElement, labelCount(mobileElement) - 1) )) {
                currentMsg = getLabelTextAtIndex(mobileElement, labelCount(mobileElement) - 1);
            }
//            System.out.println("currentMsg : " + currentMsg + "\nresponseText : " + responseText);

            //verify the bot reply msg
            //response values in expected list
            if (responseText.isEmpty()) {
                //
                //opptyName, closeDate, stage and probability
                if (currentMsg.contains("Summary") && currentMsg.contains("Close Date") &&
                        currentMsg.contains("Stage") && currentMsg.contains("Probability")) {
                    isPassed = Status.passed;
                } else {
                    System.out.println("it is fail by \n" +
                            "currentMsg.contains(\"Summary\") && " +
                            "currentMsg.contains(\"Close Date\") && currentMsg.contains(\"Stage\") " +
                            "&& currentMsg.contains(\"Probability\")");
                    isPassed = Status.failed;
                }
            } else {
                String[] responseList = responseText.split("; ");
                isPassed = Status.failed;
                for (String s : responseList) {
                    if (currentMsg.equals(s)) {
                        isPassed = Status.passed;
                        break;
                    }
                }
            }

            //record cmd info
            dataRecord = String.format("%s | %sms\n", isPassed, botRespTime);
            System.out.println("ResultDataRecord : " + dataRecord + "<<<<<<<,");
            DriverUtils.writeToFile("target/aiTestingReport.txt", dataRecord, true);


        });
        When("^Assistant: I send \"([^\"]*)\" to Assistant$", (String inputText) -> {
            log.info("^Assistant: I send " + inputText + " to Assistant$");
            TactAssistantPage tactAssistantPage = new TactAssistantPage();

            WebDriverWaitUtils.waitUntilElementIsVisible(tactAssistantPage.getTactAssistantTitleLabel());

            int beforeSendMsgNum = labelCount(tactAssistantPage.getDisplayTextListLabel());
            String previousMsg = getLabelTextAtIndex( tactAssistantPage.getDisplayTextListLabel(),beforeSendMsgNum-1);

            log.info("beforeSendMsgNum ==> " + beforeSendMsgNum + "\nmsg : " + previousMsg);

            tactAssistantPage.getAssistantTypeInTextField().sendKeys(inputText);
            tactAssistantPage.getAssistantSendButton().tap();

            int afterSendMsgNum = labelCount(tactAssistantPage.getDisplayTextListLabel());
            String currentMsg = getLabelTextAtIndex(tactAssistantPage.getDisplayTextListLabel(),afterSendMsgNum-1);

            log.info("afterSendMsgNum ==> " + afterSendMsgNum + "\nmsg: " + currentMsg);
        });

        When("^Assistant: I send \"([^\"]*)\" to Assistant, and then disconnect with wifi and (with|without) verify sent msg$", (String inputText, String isVerify) -> {
            log.info("^Assistant: I send " + inputText + " to Assistant, and then disconnect and with wifi$");
            TactAssistantPage tactAssistantPage = new TactAssistantPage();

            WebDriverWaitUtils.waitUntilElementIsVisible(tactAssistantPage.getTactAssistantTitleLabel());

            //previous Msg
            MobileElement mobileElement = tactAssistantPage.getDisplayTextListLabel();
            String previousMsg = getLabelTextAtIndex(mobileElement, labelCount(mobileElement) - 1);
            log.info("previousMsg ==> " + previousMsg);

            //sent msg to Assistant
            tactAssistantPage.getAssistantTypeInTextField().sendKeys(inputText);
            tactAssistantPage.getAssistantSendButton().tap();

            //turn off wifi
            DriverUtils.turnOffWifi();

            //verify the sent msg
            if (isVerify.equalsIgnoreCase("with")) {
                log.info("start checking the sent msg");
                mobileElement = tactAssistantPage.getDisplayTextListLabel();
                String currentMsg = getLabelTextAtIndex(mobileElement, labelCount(mobileElement) - 1);
                TactAIAsserts.assertEquals(currentMsg, inputText, "They should equal");
            }
        });
        And("^Assistant: I check the un-deliver error msg$", () -> {
            log.info("^Assistant: I check the un-deliver error msg$");
            TactAssistantPage tactAssistantPage = new TactAssistantPage();

            if (!isBotReply( getEleXCoordinate(tactAssistantPage.getDisplayTextListLabel(), labelCount(tactAssistantPage.getDisplayTextListLabel())-1) )) {
                WebDriverWaitUtils.waitUntilElementIsVisible(tactAssistantPage.getErrorMsgTextListLabel());

                //get the error msg
                MobileElement mobileElement = tactAssistantPage.getErrorMsgTextListLabel();
                String errorMsg = getLabelTextAtIndex(mobileElement, labelCount(mobileElement)-1);

                //verify the error msg
                TactAIAsserts.assertEquals(errorMsg, "Message not delivered", "The Msg is not delivered, please resend or check your wifi");
            }
        });
    }

    protected int labelCount(MobileElement mobileElement) {
        return (Grid.driver().findElementsByXPath(mobileElement.getLocator())).size();
    }

    protected String getLabelTextAtIndex (MobileElement mobileElement, int index) {
        if (index < 0) {
            return null;
        } else {
            return (Grid.driver().findElementsByXPath(mobileElement.getLocator()).get(index)).getText();
        }
    }

    protected int getEleXCoordinate (MobileElement eleLocation, int index) {
        try {
            WebElement ele = Grid.driver().findElementsByXPath(eleLocation.getLocator()).get(index);
            return ele.getRect().getX();
        } catch ( IndexOutOfBoundsException e ) {
            log.warning("warning : IndexOutOfBoundsException");
            System.out.println("IndexOutOfBoundsException");
            return  0;
        }
    }

    protected boolean isBotReply (int xCoordinate) {
        int botReplyMsgXCoordinate = 22;
        if ( xCoordinate == botReplyMsgXCoordinate ) {

            return true;
        } else {
            return false;
        }
    }
}
