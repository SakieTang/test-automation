package ai.tact.qa.automation.steps.mobileSteps;

import ai.tact.qa.automation.asserts.TactAIAsserts;
import ai.tact.qa.automation.testcomponents.mobile.TactEmail.NewMessagePage;
import ai.tact.qa.automation.testcomponents.mobile.TactEmail.TactMailBoxesPage;
import ai.tact.qa.automation.testcomponents.mobile.TactNavigateTabBarPage;
import ai.tact.qa.automation.utils.CustomPicoContainer;
import ai.tact.qa.automation.utils.DriverUtils;
import ai.tact.qa.automation.utils.LogUtil;

import com.paypal.selion.platform.grid.Grid;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;
import cucumber.api.java8.En;
import org.testng.Assert;

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
            if (toEmail.equalsIgnoreCase("anotherPlatformExchangeEmail")) {
                if (DriverUtils.isIOS()) {
                    toEmail = CustomPicoContainer.getInstance().getUserInfor().getExchangeAndroidEmailAddress();
                }
                else {
                    toEmail = CustomPicoContainer.getInstance().getUserInfor().getExchangeIOSEmailAddress();
                }
            }
            else if (toEmail.equalsIgnoreCase("samePlatformDiffEmail")) {
                if (fromEmailType.equalsIgnoreCase("gmail")) {
                    toEmail = CustomPicoContainer.getInstance().getUserInfor().getExchangeIOSEmailAddress();
                }
                else {
                    toEmail = CustomPicoContainer.getInstance().getUserInfor().getGmailIOSEmailAddress();
                }
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

            DriverUtils.tapXY(188, 388);
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

            log.info("isSendEmail " + isSendEmail + "; isSaveDraftEmail " + isSaveDraftEmail + "\nloc " + getSubjectLoc());

            DriverUtils.scrollToTop();

            System.out.println("before Assert.assertTrue(Grid.driver().findElementsByXPath(getSubjectLoc()).size() != 0, " + (Grid.driver().findElementsByXPath(getSubjectLoc()).size() != 0) );
            Assert.assertTrue(Grid.driver().findElementsByXPath(getSubjectLoc()).size() != 0, "Did not find the expected email");
            DriverUtils.sleep(5);
        });
        When("^Email: I connect with \"([^\"]*)\" email account inside Email tab bar$", (String emailOption) -> {
            log.info("^Email: I connect with " + emailOption + " email account inside Email tab bar$");
            TactMailBoxesPage tactMailBoxesPage = new TactMailBoxesPage();

            if (Grid.driver().findElementsByXPath(tactMailBoxesPage.getExchangeGmailConnectedButton().getLocator()).size() == 0) {
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
        String emailSubjectLoc = "//*[contains(@name, 'subjectText')]";

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

    public String getEmailType(String emailAddress) {
        String emailType = emailAddress.split("@")[1].split("\\.")[0];
        log.info("email address: " + emailAddress + ", emailType: " + emailType);
        return emailType;
    }
}
