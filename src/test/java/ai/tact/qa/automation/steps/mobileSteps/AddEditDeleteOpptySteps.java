package ai.tact.qa.automation.steps.mobileSteps;

import ai.tact.qa.automation.asserts.TactAIAsserts;
import ai.tact.qa.automation.testcomponents.mobile.TactOpportunity.TactAddNewOpptyPage;
import ai.tact.qa.automation.testcomponents.mobile.TactOpportunity.TactOpptiesMainPage;
import ai.tact.qa.automation.testcomponents.mobile.TactOpportunity.TactOpptyObjPage;
import ai.tact.qa.automation.testcomponents.mobile.TactSearch.TactSearchAccountPage;
import ai.tact.qa.automation.utils.DriverUtils;
import ai.tact.qa.automation.utils.LogUtil;
import ai.tact.qa.automation.utils.dataobjects.AndroidDate;
import ai.tact.qa.automation.utils.dataobjects.FieldDataType;
import ai.tact.qa.automation.utils.dataobjects.IOSTime;
import com.paypal.selion.platform.grid.Grid;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;
import cucumber.api.PendingException;
import cucumber.api.java8.En;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AddEditDeleteOpptySteps implements En {

    private boolean isEdit;

    private static final Logger log = LogUtil.setLoggerHandler(Level.ALL);
    private AndroidDate androidDate = new AndroidDate();

    public static String opportunityName;

    public AddEditDeleteOpptySteps() {

        When("^AddEditOppty: I do action \"([^\"]*)\" required oppty information with \"([^\"]*)\" opptyName, \"([^\"]*)\" closeDate, \"([^\"]*)\" stage and \"([^\"]*)\" probability$", (String action, String opptyName, String closeDate, String stage, String probability) -> {
            log.info("^AddEditOppty: I do action " + action + " required oppty information with " + opptyName + " opptyName, " + closeDate + " closeDate, " + stage + " stage and " + probability + " probability$");
            TactAddNewOpptyPage tactAddNewOpptyPage = new TactAddNewOpptyPage();

            if (action.equalsIgnoreCase("edit")){
                isEdit = true;
                WebDriverWaitUtils.waitUntilElementIsVisible(tactAddNewOpptyPage.getEditOpptyTitleLabel()); //09/17  edit oppty
            } else {
                WebDriverWaitUtils.waitUntilElementIsVisible(tactAddNewOpptyPage.getNewOpptyTitleLabel());
            }
            opportunityName = String.format("%s%s", DriverUtils.getTimeDateStamp(), opptyName);

            //Opportunity Name - Text(120)
            DriverUtils.inputTextField(isEdit, tactAddNewOpptyPage.getOpptyNameTextField(), FieldDataType.randomAscii, 120, opportunityName, true);
            if (DriverUtils.isIOS()) {
                tactAddNewOpptyPage.getAmountTextField().tap();
            }

            //Close Date        10/10/2018    Oct 2, 2018
//            if (DriverUtils.isIOS()) {
//                DriverUtils.scrollToBottom();
                DriverUtils.workaroundHideKeyboard();
//            } else {
//                DriverUtils.slideDown();
//            }

            if (DriverUtils.isIOS() && !isEdit) {
                tactAddNewOpptyPage.getCloseDateButton().tap();
                IOSTime.changeDate(closeDate);        //cannot find element, need UI team support
            } else if (DriverUtils.isAndroid()) {
                tactAddNewOpptyPage.getCloseDateButton().tap();
                androidDate.changeDate(closeDate);
            }

            //Stage
            // Prospecting - 10%       Qualification - 10%
            // Needs Analysis -20%       Value Proposition - 50%
            // Id. Decision Makers - 60%       Perception Analysis - 70%
            // Proposal/Price Quote - 75%        Negotiation/Review - 90%
            // Closed Won - 100%       Closed Lost - 0%
            if (DriverUtils.isAndroid()){
                System.out.println("before slide");
                DriverUtils.sleep(5);
                DriverUtils.slideDown();
                DriverUtils.sleep(5);
                DriverUtils.slideUP();
                DriverUtils.sleep(5);
            }
            tactAddNewOpptyPage.getStageButton().tap();
            DriverUtils.sleep(0.5);
            if (DriverUtils.isIOS()) {
                WebDriverWaitUtils.waitUntilElementIsVisible(tactAddNewOpptyPage.getStageTitleLabel());
            }
            DriverUtils.clickOption(tactAddNewOpptyPage.getStageOptionTextField(), "stageOptionText", stage);

            if (isEdit){
                WebDriverWaitUtils.waitUntilElementIsVisible(tactAddNewOpptyPage.getEditOpptyTitleLabel());
            } else {
                WebDriverWaitUtils.waitUntilElementIsVisible(tactAddNewOpptyPage.getNewOpptyTitleLabel());
            }

            //Probability - Percent(3, 0)
            if (!DriverUtils.isTextEmpty(probability) && DriverUtils.isAndroid())
            {
                System.out.println("it is not empty");
                tactAddNewOpptyPage.getProbabilityTextField().setText(probability);
            } else {
                System.out.println("it is empty");
            }
            System.out.println("after file the text");
//            DriverUtils.sleep(10);
        });

        And("^AddEditOppty: I do action \"([^\"]*)\" more oppty information with \"([^\"]*)\" isPrivate, \"([^\"]*)\" accountType, \"([^\"]*)\" leadSource, \"([^\"]*)\" amount and \"([^\"]*)\" next step$", (String action, String isPrivate, String accountType, String leadSource, String amount, String nextStep) -> {
            log.info("^AddEditOppty: I do action " + action + " more oppty information with " + isPrivate + " isPrivate, " + accountType + " accountType, " + leadSource + " leadSource, " + amount + " amount and " + nextStep + " next step$");
            TactAddNewOpptyPage tactAddNewOpptyPage = new TactAddNewOpptyPage();

            if (action.equalsIgnoreCase("edit")){
                isEdit = true;
            }

            DriverUtils.scrollToTop();
            // isPrivate
            if (Boolean.getBoolean(isPrivate))
            {
                tactAddNewOpptyPage.getPrivateSwitch().changeValue();
            }

            // accountType
            /**
            * Existing Customer - Upgrade       * Existing Customer - Replacement
            * Existing Customer - Downgrade     * New Customer
            */
            if (!DriverUtils.isTextEmpty(accountType)) {
                tactAddNewOpptyPage.getTypeButton().tap();
                DriverUtils.sleep(1);
                if (DriverUtils.isIOS()) {
                    WebDriverWaitUtils.waitUntilElementIsVisible(tactAddNewOpptyPage.getTypeTitleLabel());
                }
                DriverUtils.clickOption(tactAddNewOpptyPage.getTypeOptionTextField(), "typeOptionText", accountType);
            }

            // leadSource
            /**
            * Web                  * Phone Inquiry         * Partner Referral
            * Purchased List       * Other
            */
            if (!DriverUtils.isTextEmpty(leadSource)) {
                tactAddNewOpptyPage.getLeadSourceButton().tap();        //Android 09/16 edit oppty
                DriverUtils.sleep(1);
                if (DriverUtils.isIOS()) {
                    WebDriverWaitUtils.waitUntilElementIsVisible(tactAddNewOpptyPage.getLeadSourceTitleLabel());
                }
                DriverUtils.clickOption(tactAddNewOpptyPage.getLeadSourceOptionTextField(), "leadSourceOptionText", leadSource);
                if (isEdit) {
                    WebDriverWaitUtils.waitUntilElementIsVisible(tactAddNewOpptyPage.getEditOpptyTitleLabel());
                } else {
                    WebDriverWaitUtils.waitUntilElementIsVisible(tactAddNewOpptyPage.getNewOpptyTitleLabel());
                }
            }

            // amount - Currency(16, 2)
            DriverUtils.inputTextField(isEdit, tactAddNewOpptyPage.getAmountTextField(), FieldDataType.randomNumeric, 16, amount, true);

            DriverUtils.workaroundHideKeyboard();

            // nextStep - Text(255)
            DriverUtils.inputTextField(isEdit, tactAddNewOpptyPage.getNextStepTextField(), FieldDataType.randomAscii, 255, nextStep, true);
        });

        And("^AddEditOppty: I do action \"([^\"]*)\" Additional Information with \"([^\"]*)\" orderNumber, \"([^\"]*)\" mainCompetitor, \"([^\"]*)\" currentGenerator, \"([^\"]*)\" deliveryInstallationStatus and \"([^\"]*)\" tracking number$", (String action, String orderNumber, String mainCompetitor, String currentGenerator, String deliveryInstallationStatus, String trackingNum) -> {
            log.info("^AddEditOppty: I do action " + action + " Additional Information with " + orderNumber + " orderNumber, " + mainCompetitor + " mainCompetitor, " + currentGenerator + " currentGenerator, " + deliveryInstallationStatus + " deliveryInstallationStatus and " + trackingNum + " tracking number$");
            TactAddNewOpptyPage tactAddNewOpptyPage = new TactAddNewOpptyPage();

            if (action.equalsIgnoreCase("edit")){
                isEdit = true;
                WebDriverWaitUtils.waitUntilElementIsVisible(tactAddNewOpptyPage.getEditOpptyTitleLabel());
                DriverUtils.workaroundHideKeyboard();
            } else {
                WebDriverWaitUtils.waitUntilElementIsVisible(tactAddNewOpptyPage.getNewOpptyTitleLabel());
            }
            DriverUtils.scrollToBottom();

            // orderNumber - Text(8)
            DriverUtils.inputTextField(isEdit, tactAddNewOpptyPage.getAddInfoOlderNumberTextField(), FieldDataType.randomAscii, 8, orderNumber, true);

            if (DriverUtils.isIOS() && isEdit) {
                DriverUtils.workaroundHideKeyboard();
            }

            // mainCompetitor - Text(100)
            DriverUtils.inputTextField(isEdit, tactAddNewOpptyPage.getAddInfoMainCompetitorTextField(), FieldDataType.randomAscii, 100, mainCompetitor, true);

            DriverUtils.workaroundHideKeyboard();
            DriverUtils.sleep(5);

            // currentGenerator -Text(100)
            DriverUtils.inputTextField(isEdit, tactAddNewOpptyPage.getAddInfoCurrentGeneratorTextField(), FieldDataType.randomAscii, 100, currentGenerator, true);

            // deliveryInstallationStatus
            /**
             * In progress      * Yet to begin      * Completed
             */
            tactAddNewOpptyPage.getAddInfoDeliveryInstallationStatusButton().tap();
            DriverUtils.sleep(1);
            if (DriverUtils.isIOS()) {
                WebDriverWaitUtils.waitUntilElementIsVisible(tactAddNewOpptyPage.getDeliveryInstallationStatusTitleLabel());
            }
            DriverUtils.clickOption(tactAddNewOpptyPage.getDeliveryInstallationStatusOptionTextField(), "deliveryInstallationStatusOptionText", deliveryInstallationStatus);
            if (isEdit){
                WebDriverWaitUtils.waitUntilElementIsVisible(tactAddNewOpptyPage.getEditOpptyTitleLabel());
            } else {
                WebDriverWaitUtils.waitUntilElementIsVisible(tactAddNewOpptyPage.getNewOpptyTitleLabel());
            }

            // tracking number - Text(12)
            DriverUtils.scrollToBottom();
            DriverUtils.inputTextField(isEdit, tactAddNewOpptyPage.getAddInfoTrackingNumberTextField(), FieldDataType.randomAscii, 12, trackingNum, true);

        });
        And("^AddEditOppty: I do action \"([^\"]*)\" DescriptionInfo with \"([^\"]*)\" description$", (String action, String description) -> {
            log.info("^AddEditOppty: I do action " + action + " DescriptionInfo with " + description + " description$");
            TactAddNewOpptyPage tactAddNewOpptyPage = new TactAddNewOpptyPage();

            if (action.equalsIgnoreCase("edit")){
                isEdit = true;
            }

            // Description - Long Text Area(32000)
//            if (DriverUtils.isIOS() && !isEdit) {
            if (DriverUtils.isIOS()) {
                DriverUtils.workaroundHideKeyboard();
            }
//            else {
                DriverUtils.scrollToBottom();
//            }
            // 8800 ok       8801 no  create from SF - check in Tact
            // 32000 ok - in prototype
            if (description.contains("TextLength") && Integer.parseInt(description.substring(10)) > 8800 && DriverUtils.isIOS()){
                description = "TextLength100";
            }
            DriverUtils.inputTextField(isEdit, tactAddNewOpptyPage.getDescriptionTextField(), FieldDataType.randomAscii, 32000, description, false);

            System.out.println("finish texting");
            DriverUtils.sleep(10);
        });
        And("^Contacts: I search one account \"([^\"]*)\" and select it$", (String accountName) -> {
            log.info("^Contacts: I search one account " + accountName + " and select it$");
            TactAddNewOpptyPage tactAddNewOpptyPage = new TactAddNewOpptyPage();
            TactSearchAccountPage tactSearchAccountPage = new TactSearchAccountPage();

            if (!DriverUtils.isTextEmpty(accountName)) {
                // search the name from search field
                WebDriverWaitUtils.waitUntilElementIsVisible(tactAddNewOpptyPage.getAccountButton());
                tactAddNewOpptyPage.getAccountButton().tap(tactSearchAccountPage.getAccountSearchTitleLabel());
//            if (DriverUtils.isAndroid())
//            {
//                DriverUtils.sleep(0.5);
//                WebDriverWaitUtils.waitUntilElementIsVisible(tactSearchContactsPage.getAndroidContactsTabSearchIconButton());
//                tactSearchContactsPage.getAndroidContactsTabSearchIconButton().tap();
//                WebDriverWaitUtils.waitUntilElementIsVisible(tactSearchContactsPage.getSearchAllContactsTextField());
//            }
                //element id changed <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
                System.out.println("name : " + accountName);
                DriverUtils.slideDown();
                tactSearchAccountPage.getSearchTextField().sendKeys(accountName);

                //get the name location, and click it
                log.info("name :" + accountName);
                DriverUtils.clickOption(tactSearchAccountPage.getNameEditTextField(), "accountNameOptionText", accountName);
            }
        });
        And("^AddOppty: I \"([^\"]*)\" save the new opportunity$", (String isSave) -> {
            log.info("^AddOppty: I " + isSave + " save the new opportunity$");
            TactOpptiesMainPage tactOpptiesMainPage = new TactOpptiesMainPage();
            TactAddNewOpptyPage tactAddNewOpptyPage = new TactAddNewOpptyPage();

            boolean isSaveBoolean = Boolean.parseBoolean(isSave);
            if (isSaveBoolean)
            {
                tactAddNewOpptyPage.getSaveNewOpptyButton().tap();
                TactAIAsserts.assertEquals(tactOpptiesMainPage.getTactOpportunitiesDropDownButton(), tactOpptiesMainPage.getTactOpportunitiesDropDownButton(), "Should go back to Opportunities main page");
//                TactAIAsserts.assertNotEquals(tactAddNewOpptyPage.getNewOpptyTitleLabel(), tactAddNewOpptyPage.getNewOpptyTitleLabel(), "There is an error in your oppty");
            }
//            if (!opportunityName.contains("created")) {
//                System.out.println("finish eidt");
//                DriverUtils.sleep(30);
//            }

            if (DriverUtils.isAndroid()) {
                DriverUtils.sleep(2);
                System.out.println("in Android");
                String backLoc = "//android.widget.ImageButton[@content-desc='Navigate up']";
                Grid.driver().findElementByXPath(backLoc).click();
            }
//
//            System.out.println("is it saved?");
//            DriverUtils.sleep(20);
        });
        Then("^AddOppty: I check the oppty value$", () -> {
            log.info("^AddOppty: I check the oppty value$");

            String opptyDes = "//XCUIElementTypeStaticText[@name='Description']/following-sibling::XCUIElementTypeTextView";

            DriverUtils.scrollToBottom();


        });
        Then("^EditOppty: I start to edit$", () -> {
            log.info("^EditOppty: I start to edit$");
            TactOpptyObjPage tactOpptyObjPage = new TactOpptyObjPage();

            WebDriverWaitUtils.waitUntilElementIsVisible(tactOpptyObjPage.getEditOpptyObjButton());
            tactOpptyObjPage.getEditOpptyObjButton().tap();

        });

        Then("^EditOppty: I edit required oppty information with \"([^\"]*)\" opptyName$", (String arg0) -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
    }
}
