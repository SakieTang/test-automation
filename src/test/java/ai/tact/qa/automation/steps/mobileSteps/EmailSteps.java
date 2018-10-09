package ai.tact.qa.automation.steps.mobileSteps;

import ai.tact.qa.automation.asserts.TactAIAsserts;
import ai.tact.qa.automation.testcomponents.mobile.TactEmail.NewMessagePage;
import ai.tact.qa.automation.testcomponents.mobile.TactEmail.TactMailBoxesPage;
import ai.tact.qa.automation.testcomponents.mobile.TactEmail.ViewEmailPage;
import ai.tact.qa.automation.testcomponents.mobile.TactNavigateTabBarPage;
import ai.tact.qa.automation.utils.CustomPicoContainer;
import ai.tact.qa.automation.utils.DriverUtils;
import ai.tact.qa.automation.utils.LogUtil;

import com.paypal.selion.platform.grid.Grid;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;
import cucumber.api.PendingException;
import cucumber.api.java8.En;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmailSteps implements En {

    private boolean isSendEmail = false;
    private boolean isSaveDraftEmail = false;
    private String sendEmailSubjectText = "";

    private static final Logger log = LogUtil.setLoggerHandler(Level.ALL);

    public EmailSteps() {

        When("^Email: I switch to \"([^\"]*)\" mailType, \"([^\"]*)\" option and \"([^\"]*)\" create a new email$", (String mailType, String option, String isCreate) -> {
            log.info("^Email: I switch to " + mailType + " mailType, " + option + " option and " + isCreate + " send a new email$");
            TactMailBoxesPage tactMailBoxesPage = new TactMailBoxesPage();
            NewMessagePage newMessagePage = new NewMessagePage();

            WebDriverWaitUtils.waitUntilElementIsVisible(tactMailBoxesPage.getMailBoxesTitleLabel());

            if (mailType.equalsIgnoreCase("Exchange")) {
                log.info("email type : " + mailType);

                switch (option) {
                    case "Inbox":
                        tactMailBoxesPage.getExchangeOrGmailInboxsButton().tap();
                        break;
                    case "Archive":
                        tactMailBoxesPage.getExchangeArchiveButton().tap();
                        break;
                    case "Deleted Items":
                        tactMailBoxesPage.getExchangeDeletedItemsButton().tap();
                        break;
                    case "Junk Email":
                        tactMailBoxesPage.getExchangeJunkEmailButton().tap();
                        break;
                    case "Sent Items":
                        tactMailBoxesPage.getSentItemsButton().tap();
                        break;
                    default:
                        TactAIAsserts.verifyFalse(true, "Please give a correct String " +
                                "(Inbox|Archive|Deleted Items|Junk Email|Sent Items)");
                }
            }
            else if (mailType.equalsIgnoreCase("Google") || mailType.equalsIgnoreCase("gmail")){
                log.info("email type is : " + mailType);
                DriverUtils.scrollToBottom();

                switch (option) {
                    case "Inbox":
                        String inboxButtonLoc = tactMailBoxesPage.getExchangeOrGmailInboxsButton().getLocator();
                        if (Grid.driver().findElementsByXPath(inboxButtonLoc).size()==1) {
                            tactMailBoxesPage.getExchangeOrGmailInboxsButton().tap();
                        } else {
                            Grid.driver().findElementsByXPath(inboxButtonLoc).get(1).click();
                        }
                        break;
                    case "Sent":
                        String gmailSentButtonLoc = tactMailBoxesPage.getAllOrGmailSentButton().getLocator();
                        if (Grid.driver().findElementsByXPath(gmailSentButtonLoc).size()==1){
                            tactMailBoxesPage.getAllOrGmailSentButton().tap();
                        } else {
                            Grid.driver().findElementsByXPath(gmailSentButtonLoc).get(1).click();
                        }
                        break;
                    default:
                        TactAIAsserts.verifyFalse(true, "Please give a correct String " +
                                "(Inbox|Sent)");
                }
            }
            else {
                switch (option) {
                    case "All Inboxes":
                        tactMailBoxesPage.getAllInboxedButton().tap();
                        break;
                    case "Priority":
                        tactMailBoxesPage.getPriorityButton().tap();
                        break;
                    case "Sent":
                        tactMailBoxesPage.getAllOrGmailSentButton().tap();
                        break;
                    case "Tracked":
                        tactMailBoxesPage.getTrackedButton().tap();
                        break;
                    case "Drafts":
                        tactMailBoxesPage.getDraftsButton().tap();
                        break;
                    default:
                        TactAIAsserts.verifyFalse(true, "Please give a correct String " +
                                "(All Inboxes|Priority|Sent|Tracked|Drafts)");
                }
            }
            WebDriverWaitUtils.waitUntilElementIsVisible(tactMailBoxesPage.getComposeButton());

            if (!DriverUtils.isTextEmpty(isCreate))
            {
                tactMailBoxesPage.getComposeButton().tap(newMessagePage.getNewMessageTitleLabel());
            }
        });
        Then("^Email: I create a simply email To \"([^\"]*)\", Subject \"([^\"]*)\" and body \"([^\"]*)\"$", (String toEmail, String subject, String body) -> {
            log.info("^Email: I create a simply email To " + toEmail + ", Subject " + subject + " and body " + body + "$");
            NewMessagePage newMessagePage = new NewMessagePage();

            WebDriverWaitUtils.waitUntilElementIsVisible(newMessagePage.getNewMessageTitleLabel());

            String sendEmailAddress = newMessagePage.getFromLabel().getValue();
            String fromEmailType = getEmailType(sendEmailAddress);

            //To:
            if (toEmail.equalsIgnoreCase("samePlatformDiffEmail")) {
                if (fromEmailType.equalsIgnoreCase("gmail")) {
                    toEmail = CustomPicoContainer.getInstance().getUser().getExchangeEmailAddress();
                    //CustomPicoContainer.getInstance().getUserInfor().getExchangeIOSEmailAddress();
                }
                else {
                    toEmail = CustomPicoContainer.getInstance().getUser().getGmailEmailAddress();
                    //CustomPicoContainer.getInstance().getUserInfor().getGmailIOSEmailAddress();
                }
            } else {
                toEmail = toEmail;
            }
            String toEmailType = getEmailType(toEmail);
            newMessagePage.getToNewMessageTextField().sendKeys(toEmail + "\n");

            //Subject
            String dateMonthTime = DriverUtils.currentDateInfo("mm") + "/" + DriverUtils.currentDateInfo("date") +
                    "/" + DriverUtils.currentDateInfo("hours") + DriverUtils.currentDateInfo("mins");
//            String currentUser;
//            if(DriverUtils.isIOS()){
//                currentUser = CustomPicoContainer.userInfor.getSalesforceIOSAccountName();
//            } else {
//                currentUser = CustomPicoContainer.userInfor.getSalesforceAndroidAccountName();
//            }
//            currentUser = currentUser.split("\\.")[1];
            String currentOSType = DriverUtils.getCurrentMobileOSType();
            sendEmailSubjectText = dateMonthTime + "_" + currentOSType + "_" + fromEmailType + "_TactAPP_to_" + toEmailType + "_subject";
            if (!DriverUtils.isTextEmpty(subject))
            {
                sendEmailSubjectText = sendEmailSubjectText + "_" + subject;
            }
            log.info("subject text : " + sendEmailSubjectText);
            newMessagePage.getSubjectNewMessageTextField().sendKeys(sendEmailSubjectText);

            DriverUtils.tapXY(188, 388, 200,2);
            newMessagePage.getBodyTextField().sendKeys(body);

        });
        And("^Email: I \"([^\"]*)\" send email and \"([^\"]*)\" save draft$", (String isSend, String isSaveDraft) -> {
            log.info("^Email: I " + isSend + " send email and " + isSaveDraft + " save draft$");
            NewMessagePage newMessagePage = new NewMessagePage();
            TactNavigateTabBarPage tactNavigateTabBarPage = new TactNavigateTabBarPage();

            if(!DriverUtils.isTextEmpty(isSend)) {
                isSendEmail = true;
                log.info("send new message");
                newMessagePage.getSendNewMessageButton().tap();
            }
            else {
                isSendEmail = false;
                log.info("Do not send new message");
                newMessagePage.getCancelNewMessageButton().tap(newMessagePage.getCancelSaveDraftButton());
                if (DriverUtils.isTextEmpty(isSaveDraft)) {
                    isSaveDraftEmail = false;
                    log.info("Delete Draft");
                    newMessagePage.getCancelDeleteDraftButton().tap();
                }
                else {
                    isSaveDraftEmail = true;
                    log.info("Save Draft");
                    newMessagePage.getCancelSaveDraftButton().tap();
                }
            }
            WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getTactEmailButton());
        });
        And("^Email: I verify the email$", () -> {
            log.info("^Email: I verify the send email$");

            log.info("isSendEmail " + isSendEmail + "; isSaveDraftEmail " + isSaveDraftEmail + "\nloc " + getEmailFieldLoc("emailSubject", sendEmailSubjectText));

            DriverUtils.scrollToTop();

            System.out.println("before Assert.assertTrue(Grid.driver().findElementsByXPath(getEmailFieldLoc(\"emailSubject\", sendEmailSubjectText)).size() != 0, " + (Grid.driver().findElementsByXPath(getEmailFieldLoc("emailSubject", sendEmailSubjectText)).size() != 0) );
            Assert.assertTrue(Grid.driver().findElementsByXPath(getEmailFieldLoc("emailSubject", sendEmailSubjectText)).size() != 0, "Did not find the expected email");
            DriverUtils.sleep(5);
        });
        When("^Email: I connect with \"([^\"]*)\" email account inside Email tab bar$", (String emailOption) -> {
            log.info("^Email: I connect with " + emailOption + " email account inside Email tab bar$");
            TactMailBoxesPage tactMailBoxesPage = new TactMailBoxesPage();

            if (DriverUtils.isIOS() && Grid.driver().findElementsByXPath(tactMailBoxesPage.getExchangeGmailConnectedButton().getLocator()).size() == 0) {
                String connectButtonLoc = tactMailBoxesPage.getExchangeGmailConnectButton().getLocator();

                if (emailOption.equalsIgnoreCase("exchange")) {
                    Grid.driver().findElementsByXPath(connectButtonLoc).get(0).click();
                }
                else {
                    Grid.driver().findElementsByXPath(connectButtonLoc).get(1).click();
                }
            }
            else {
                tactMailBoxesPage.getExchangeGmailConnectButton().tap();
            }
        });
        And("^Email: I verify the email field \"(emailFrom|emailDate|emailSubject|emailBody)\" with \"([^\"]*)\"$", (String emailField, String fieldValue) -> {
            log.info("^Email: I verify the email field " + emailField + " with " + fieldValue + "$");

            Assert.assertTrue(Grid.driver().findElementsByXPath(getEmailFieldLoc(emailField, fieldValue)).size() != 0, "Did not find the expected email");
            DriverUtils.sleep(3);

        });
        Then("^Email: I send an empty subject email$", () -> {
            log.info("^Email: I send an empty subject email$");

            NewMessagePage newMessagePage = new NewMessagePage();

            newMessagePage.getSendNewMessageButton().tap(newMessagePage.getEmptySubjectLabel());
            newMessagePage.getSendEmptySubjectNewMsgButton().tap();

        });
        When("^Email: I \"([^\"]*)\" swipe the email with \"([^\"]*)\" subject$", (String swipe, String emailSubjectValue) -> {
            log.info("^Email: I " + swipe + " swipe the email with " + emailSubjectValue + " subject$");

            /**
             import java.time.Duration;
             import static io.appium.java_client.touch.offset.PointOption.point;
             import static io.appium.java_client.touch.WaitOptions.waitOptions;

             public void swipe(int x_start, int y_start, int x_stop, int y_stop, int duration) {
             new TouchAction(driver).press(point(x_start, y_start)) .waitAction(waitOptions(ofSeconds(duration))).moveTo(point(x_stop, y_stop)) .release() .perform();



             String elementXpath = "//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]//XCUIElementTypeTable[1]/XCUIElementTypeCell[1]";
             WebElement rowToSlide = driver.findElement(By.xpath(elementXpath));
             TouchAction swipe = new TouchAction(driver).press(rowToSlide, 250 , 147) .waitAction(-200).moveTo(rowToSlide, 200, 147).release();
             swipe.perform();
             }
             */

            String element = "//XCUIElementTypeStaticText[@name=\"Signature\"]/parent::*";
            WebElement rowToSlide = Grid.driver().findElementByXPath(element);
            TouchAction swipeRight = new TouchAction((AppiumDriver)Grid.driver()).press(rowToSlide, 48,190).waitAction(-200).moveTo(rowToSlide, 188,190).release();
            swipeRight.perform();



        });
        Then("^Email: I check the email shows in two weeks$", () -> {
            log.info("^Email: I check the email shows in two weeks$");

            TactMailBoxesPage tactMailBoxesPage = new TactMailBoxesPage();
            ViewEmailPage viewEmailPage = new ViewEmailPage();

            DriverUtils.scrollToBottom();
            DriverUtils.scrollToBottom();
            int size = Grid.driver().findElementsByXPath(tactMailBoxesPage.getEmailTableElement().getLocator()).size();
            log.info("size : " + size);
            Grid.driver().findElementsByXPath(tactMailBoxesPage.getEmailTableElement().getLocator()).get(size-1).click();
            WebDriverWaitUtils.waitUntilElementIsVisible(viewEmailPage.getPinButton());

            String dateStr = viewEmailPage.getViewTimeLabel().getValue();
            Date date = null;

            if (DriverUtils.isIOS()){   //iOS  - September 19, 2018 at 8:31 PM
                try {
                    date=new SimpleDateFormat("MMM dd, yyyy 'at' hh:mm aaa").parse(dateStr);
                } catch (Exception e){
                    log.info(e.getMessage());
                }
            } else {                    //Android - 9/20/18 05:49 PM
                try {
                    date = new SimpleDateFormat("MM/dd/yy hh:mm aaa").parse(dateStr);
                } catch (Exception e){
                    log.info(e.getMessage());
                }
            }
            System.out.println(dateStr + "=> " + date);

            Assert.assertTrue(checkTheDate(date), "true msg");
            viewEmailPage.getBackToMailListPageButton().tap();
            DriverUtils.sleep(10);
        });

    }

    public boolean isGmailType(String email){
        if (email.contains("gmail")) {
            return true;
        }
        else {
            return false;
        }
    }

    public String getSubjectLoc() {
        TactMailBoxesPage tactMailBoxesPage = new TactMailBoxesPage();
        String emailSubjectLoc = null;
        if (DriverUtils.isIOS()){
            emailSubjectLoc = "//*[contains(@name, 'subjectText')]";
        } else {
            emailSubjectLoc = "//*[contains(@text, 'subjectText')]";
        }

        //only show 38 chars
        if (sendEmailSubjectText.length() > 30) {
            //38 chars + ...
            emailSubjectLoc = emailSubjectLoc.replaceAll("subjectText",sendEmailSubjectText.substring(0,30));
        }
        else {
            emailSubjectLoc = emailSubjectLoc.replaceAll("subjectText", sendEmailSubjectText);
        }
        log.info("emailSub Loc" + emailSubjectLoc);
        return emailSubjectLoc;
    }

    public String getEmailFieldLoc(String emailField, String fieldValue) {
        TactMailBoxesPage tactMailBoxesPage = new TactMailBoxesPage();
        String emailFieldLoc = null;

        switch(emailField) {
            case "emailFrom":
                emailFieldLoc = tactMailBoxesPage.getEmailFromLabel().getLocator();
                emailFieldLoc = emailFieldLoc.replaceAll("fromText", fieldValue);
                break;
            case "emailDate":
                emailFieldLoc = tactMailBoxesPage.getEmailDateLabel().getLocator();
                emailFieldLoc = emailFieldLoc.replaceAll("dateText", fieldValue);
                break;
            case "emailSubject":
                emailFieldLoc = tactMailBoxesPage.getEmailSubjectLabel().getLocator();
                emailFieldLoc = emailFieldLoc.replaceAll("subjectText", fieldValue);

                //only show 38 chars
                if (DriverUtils.isIOS() && fieldValue.length() > 30) {
                    //38 chars + ...
                    emailFieldLoc = emailFieldLoc.replaceAll(fieldValue,fieldValue.substring(0,30));
                }

                break;
            case "emailBody":
                emailFieldLoc = tactMailBoxesPage.getEmailTextBodyLabel().getLocator();
                emailFieldLoc = emailFieldLoc.replaceAll("textBodyText", fieldValue);
                break;
        }

        return emailFieldLoc;
    }

    public String getEmailType(String emailAddress) {
        String emailType = emailAddress.split("@")[1].split("\\.")[0];
        log.info("email address: " + emailAddress + ", emailType: " + emailType);
        return emailType;
    }

    public boolean checkTheDate(Date date) {
        boolean result = false;

        String today = DriverUtils.currentDateInfo("month") + " " +
                DriverUtils.currentDateInfo("dd") + " " +
                DriverUtils.currentDateInfo("yyyy");
        Date todayDate = null;

        try {
            todayDate = new SimpleDateFormat("MMM dd yyyy").parse(today);
            System.out.println(today + " => " + todayDate);
        } catch (Exception e){
            log.info(e.getMessage());
        }
        Date date16 = DriverUtils.dateDiff(todayDate, -15);
        System.out.println("date16 => " + date16);
        result = date16.before(date);

        return result;
    }
}
