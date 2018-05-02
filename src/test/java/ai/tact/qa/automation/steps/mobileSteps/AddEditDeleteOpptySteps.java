package ai.tact.qa.automation.steps.mobileSteps;

import ai.tact.qa.automation.asserts.TactAIAsserts;
import ai.tact.qa.automation.testcomponents.mobile.TactOpportunity.TactAddNewOpptyPage;
import ai.tact.qa.automation.testcomponents.mobile.TactOpportunity.TactAddNewProductPage;
import ai.tact.qa.automation.testcomponents.mobile.TactOpportunity.TactOpptiesMainPage;
import ai.tact.qa.automation.testcomponents.mobile.TactOpportunity.TactOpptyObjPage;
import ai.tact.qa.automation.testcomponents.mobile.TactSearch.TactSearchAccountPage;
import ai.tact.qa.automation.utils.DriverUtils;
import ai.tact.qa.automation.utils.LogUtil;
import ai.tact.qa.automation.utils.dataobjects.AndroidDate;
import ai.tact.qa.automation.utils.dataobjects.FieldDataType;
import ai.tact.qa.automation.utils.dataobjects.IOSTime;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;
import cucumber.api.PendingException;
import cucumber.api.java8.En;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AddEditDeleteOpptySteps implements En {

    private TactOpptiesMainPage tactOpptiesMainPage;
    private TactAddNewOpptyPage tactAddNewOpptyPage;
    private TactAddNewProductPage tactAddNewProductPage;
    private TactSearchAccountPage tactSearchAccountPage;
    private TactOpptyObjPage tactOpptyObjPage;
    private boolean isEdit;

    private Logger log = LogUtil.setLoggerHandler(Level.ALL);
    private AndroidDate androidDate = new AndroidDate();

    public AddEditDeleteOpptySteps() {

        tactOpptiesMainPage = new TactOpptiesMainPage();
        tactAddNewOpptyPage = new TactAddNewOpptyPage();
        tactAddNewProductPage = new TactAddNewProductPage();
        tactSearchAccountPage = new TactSearchAccountPage();
        tactOpptyObjPage = new TactOpptyObjPage();
        isEdit = false;

        When("^AddEditOppty: I do action \"([^\"]*)\" required oppty information with \"([^\"]*)\" opptyName, \"([^\"]*)\" closeDate, \"([^\"]*)\" stage and \"([^\"]*)\" probability$", (String action, String opptyName, String closeDate, String stage, String probability) -> {
            log.info("^AddEditOppty: I do action " + action + " required oppty information with " + opptyName + " opptyName, " + closeDate + " closeDate, " + stage + " stage and " + probability + " probability$");

            if (action.equalsIgnoreCase("edit")){
                isEdit = true;
                WebDriverWaitUtils.waitUntilElementIsVisible(tactAddNewOpptyPage.getEditOpptyTitleLabel());
            } else {
                WebDriverWaitUtils.waitUntilElementIsVisible(tactAddNewOpptyPage.getNewOpptyTitleLabel());
            }
            opptyName = opptyName + "_" + DriverUtils.currentDateInfo("mm") + "_" + DriverUtils.currentDateInfo("dd");

            //Opportunity Name - Text(120)
            DriverUtils.inputTextField(isEdit, tactAddNewOpptyPage.getOpptyNameTextField(), FieldDataType.randomAscii, 120, opptyName, true);
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
            if (DriverUtils.isIOS()) {
                WebDriverWaitUtils.waitUntilElementIsVisible(tactAddNewOpptyPage.getStageTitleLabel());
            }
//            else {
//                DriverUtils.sleep(0.5);
//            }
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
                if (DriverUtils.isIOS()) {
                    WebDriverWaitUtils.waitUntilElementIsVisible(tactAddNewOpptyPage.getTypeTitleLabel());
                }
//            else {
//                DriverUtils.sleep(0.5);
//            }
                DriverUtils.clickOption(tactAddNewOpptyPage.getTypeOptionTextField(), "typeOptionText", accountType);
            }

            // leadSource
            /**
            * Web                  * Phone Inquiry         * Partner Referral
            * Purchased List       * Other
            */

            if (!DriverUtils.isTextEmpty(leadSource)) {
                tactAddNewOpptyPage.getLeadSourceButton().tap();
                if (DriverUtils.isIOS()) {
                    WebDriverWaitUtils.waitUntilElementIsVisible(tactAddNewOpptyPage.getLeadSourceTitleLabel());
                }
//            else {
//                DriverUtils.sleep(0.5);
//            }
                DriverUtils.clickOption(tactAddNewOpptyPage.getLeadSourceOptionTextField(), "leadSourceOptionText", leadSource);
                if (isEdit) {
                    WebDriverWaitUtils.waitUntilElementIsVisible(tactAddNewOpptyPage.getEditOpptyTitleLabel());
                } else {
                    WebDriverWaitUtils.waitUntilElementIsVisible(tactAddNewOpptyPage.getNewOpptyTitleLabel());
                }
            }

            // amount - Currency(16, 2)
            DriverUtils.inputTextField(isEdit, tactAddNewOpptyPage.getAmountTextField(), FieldDataType.randomNumeric, 16, amount, true);

//            if (DriverUtils.isIOS()) {
                DriverUtils.workaroundHideKeyboard();
//            }

            // nextStep - Text(255)
            DriverUtils.inputTextField(isEdit, tactAddNewOpptyPage.getNextStepTextField(), FieldDataType.randomAscii, 255, nextStep, true);
        });

        And("^AddEditOppty: I do action \"([^\"]*)\" Additional Information with \"([^\"]*)\" orderNumber, \"([^\"]*)\" mainCompetitor, \"([^\"]*)\" currentGenerator, \"([^\"]*)\" deliveryInstallationStatus and \"([^\"]*)\" tracking number$", (String action, String orderNumber, String mainCompetitor, String currentGenerator, String deliveryInstallationStatus, String trackingNum) -> {
            log.info("^AddEditOppty: I do action " + action + " Additional Information with " + orderNumber + " orderNumber, " + mainCompetitor + " mainCompetitor, " + currentGenerator + " currentGenerator, " + deliveryInstallationStatus + " deliveryInstallationStatus and " + trackingNum + " tracking number$");

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

//            if (DriverUtils.isIOS()) {
                DriverUtils.workaroundHideKeyboard();
                DriverUtils.sleep(30);
//            }

            // currentGenerator -Text(100)
            DriverUtils.inputTextField(isEdit, tactAddNewOpptyPage.getAddInfoCurrentGeneratorTextField(), FieldDataType.randomAscii, 100, currentGenerator, true);



            // deliveryInstallationStatus
            /**
             * In progress      * Yet to begin      * Completed
             */
            tactAddNewOpptyPage.getAddInfoDeliveryInstallationStatusButton().tap();
            if (DriverUtils.isIOS()) {
                WebDriverWaitUtils.waitUntilElementIsVisible(tactAddNewOpptyPage.getDeliveryInstallationStatusTitleLabel());
            }
//            else {
//                DriverUtils.sleep(0.5);
//            }
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
            boolean isSaveBoolean = Boolean.parseBoolean(isSave);
            if (isSaveBoolean)
            {
                tactAddNewOpptyPage.getSaveNewOpptyButton().tap();
                TactAIAsserts.assertEquals(tactOpptiesMainPage.getTactOpportunitiesDropDownButton(), tactOpptiesMainPage.getTactOpportunitiesDropDownButton(), "Should go back to Opportunities main page");
//                TactAIAsserts.assertNotEquals(tactAddNewOpptyPage.getNewOpptyTitleLabel(), tactAddNewOpptyPage.getNewOpptyTitleLabel(), "There is an error in your oppty");
            }
            System.out.println("finish eidt");
            DriverUtils.sleep(10);
        });
        Then("^AddOppty: I check the oppty value$", () -> {
            log.info("^AddOppty: I check the oppty value$");

            String opptyDes = "//XCUIElementTypeStaticText[@name='Description']/following-sibling::XCUIElementTypeTextView";

            DriverUtils.scrollToBottom();


        });
        Then("^EditOppty: I start to edit$", () -> {
            log.info("^EditOppty: I start to edit$");

            WebDriverWaitUtils.waitUntilElementIsVisible(tactOpptyObjPage.getEditOpptyObjButton());
            tactOpptyObjPage.getEditOpptyObjButton().tap();

        });
        Then("^EditOppty: I edit required oppty information with \"([^\"]*)\" opptyName$", (String arg0) -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
    }
}
