package ai.tact.qa.automation.steps.mobileSteps;

import ai.tact.qa.automation.testcomponents.mobile.TactAlertsPopUpPage;
import ai.tact.qa.automation.testcomponents.mobile.TactContact.TactAddNewContactPage;
import ai.tact.qa.automation.testcomponents.mobile.TactContact.TactContactObjPage;
import ai.tact.qa.automation.testcomponents.mobile.TactContact.TactContactsMainPage;
import ai.tact.qa.automation.utils.LogUtil;
import ai.tact.qa.automation.utils.DriverUtils;

import com.paypal.selion.platform.grid.Grid;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;
import cucumber.api.PendingException;
import cucumber.api.java8.En;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AddDeleteContactSteps implements En {

    private static final Logger log = LogUtil.setLoggerHandler(Level.ALL);
    public static String contactName;

    public AddDeleteContactSteps() {

        When("^AddContact: I \"([^\"]*)\" save to Phone and \"([^\"]*)\" send to Salesforce$", (String saveToPhone, String sendToSF) -> {
            log.info("^AddContact: I " + saveToPhone + " save to Phone and " + sendToSF + " send to Salesforce$");
            TactAddNewContactPage tactAddNewContactPage = new TactAddNewContactPage();

            //Save to Phone
            if (Grid.driver().findElementsByXPath(tactAddNewContactPage.getSaveToIPhoneSwitch().getLocator()).size() !=0
                    && DriverUtils.isTextEmpty(saveToPhone))
            {
                tactAddNewContactPage.getSaveToIPhoneSwitch().changeValue();
            }
            if (DriverUtils.isTextEmpty(sendToSF))
            {
                tactAddNewContactPage.getSendToSaleforceSwitch().changeValue();
            }
        });
        When("^AddContact: I input a user name \"([^\"]*)\" and \"([^\"]*)\"$", (String contactName, String isSave) -> {
            log.info("^AddContact: I input user name " + contactName);
            TactAddNewContactPage tactAddNewContactPage = new TactAddNewContactPage();
            TactContactsMainPage tactContactsMainPage = new TactContactsMainPage();
            TactContactObjPage tactContactObjPage = new TactContactObjPage();
            TactAlertsPopUpPage tactAlertsPopUpPage = new TactAlertsPopUpPage();

            String fName = DriverUtils.get1stNFromFullName(contactName, false);
            String lName = DriverUtils.getLastNFromFullName(contactName, true);
            if (fName.isEmpty()) {
                this.contactName = lName;
            } else {
                this.contactName=String.format("%s %s", fName, lName);
                System.out.println(AddDeleteContactSteps.contactName);
            }

            if (!fName.isEmpty()) {
                tactAddNewContactPage.getFirstNameTextField().setText(fName);
            }
            tactAddNewContactPage.getLastNameTextField().setText(lName);

            if (!DriverUtils.isTextEmpty(isSave)) {
                tactAddNewContactPage.getSaveNewContactButton().tap();

                if (DriverUtils.isAndroid())
                {
                    tactContactObjPage.getGoBackToContactsMainPageButton().tap();
                }
            }
            else {
                log.info("w/o save");

                tactAddNewContactPage.getCancelAddNewContactButton().tap();
                if (DriverUtils.isAndroid())
                {
                    tactAlertsPopUpPage.getAndroidPopUpSureConfirmOKButton().tap();
                }
//                if (Grid.driver().findElementsById(tactAlertsPopUpPage.getAndroidPopUpSureConfirmOKButton().getLocator()).size() !=0){
//                    tactAlertsPopUpPage.getAndroidPopUpSureConfirmOKButton().tap();
//                }
            }



            WebDriverWaitUtils.waitUntilElementIsVisible(tactContactsMainPage.getTactContactsTitleLabel());
            log.info("save ? " + isSave);
            DriverUtils.sleep(10);
        });

        And("^AddContact: I create \"([^\"]*)\" time a \"([^\"]*)\" and \"([^\"]*)\" save to Phone and \"([^\"]*)\" send to Salesforce, with username \"([^\"]*)\" and \"([^\"]*)\"$", (String time, String userType, String saveToPhone, String sendToSF, String contactName, String isSave) -> {
            log.info("^AddContact: I create a " + time + " and "+ saveToPhone + " save to Phone and " + sendToSF + " send to Salesforce, with username " + contactName + " and " + isSave + "$");
            TactAddNewContactPage tactAddNewContactPage = new TactAddNewContactPage();
            TactContactsMainPage tactContactsMainPage = new TactContactsMainPage();

            int times = Integer.parseInt(time);

            //entry name
            String fName = null;
            String lName = contactName;
            if (contactName.contains(" ") && !contactName.contains(", ")) {
                fName = contactName.split(" ")[0];
                lName = contactName.split(" ")[1];
            }
            else if (contactName.contains(",") && !contactName.contains(", ")) {
                fName = contactName.split(",")[1];
                lName = contactName.split(",")[0];
            }
            else if (contactName.contains(", ")) {
                fName = contactName.split(", ")[1];
                lName = contactName.split(", ")[0];
            }

//            //switch to create contacts page
//            tactContactsMainPage.getContactsPlusIconButton().tap();
//            tactAddNewContactPage.getTactAddNewContactButton().tap();
//
//            //Save to Phone
//            if (Grid.driver().findElementsByXPath(tactAddNewContactPage.getSaveToIPhoneSwitch().getLocator()).size() !=0
//                    && DriverUtils.isTextEmpty(saveToPhone))
//            {
//                tactAddNewContactPage.getSaveToIPhoneSwitch().changeValue();
//            }
//            //Send to SF
//            if (DriverUtils.isTextEmpty(sendToSF))
//            {
//                tactAddNewContactPage.getSendToSaleforceSwitch().changeValue();
//            }
//            tactAddNewContactPage.getCancelAddNewContactButton().tap(tactContactsMainPage.getTactContactsTitleLabel());

            for (int i=0; i<times; i++) {

                System.out.println("Time: " + i);

                //switch to create contacts page
                tactContactsMainPage.getContactsPlusIconButton().tap();
                tactAddNewContactPage.getTactAddNewContactButton().tap();

//                //Save to Phone
//                if (Grid.driver().findElementsByXPath(tactAddNewContactPage.getSaveToIPhoneSwitch().getLocator()).size() !=0
//                        && DriverUtils.isTextEmpty(saveToPhone))
//                {
//                    tactAddNewContactPage.getSaveToIPhoneSwitch().changeValue();
//                }
//                //Send to SF
//                if (DriverUtils.isTextEmpty(sendToSF))
//                {
//                    tactAddNewContactPage.getSendToSaleforceSwitch().changeValue();
//                }

//                if (fName != null)
//                {
//                    tactAddNewContactPage.getFirstNameTextField().setText(fName);
//                }
                tactAddNewContactPage.getLastNameTextField().setText(lName);
                //try 1
//                tactAddNewContactPage.getLastNameTextField().sendKeys(lName);
////                //try 2
////                Grid.driver().findElementByXPath(tactAddNewContactPage.getLastNameTextField().getLocator()).sendKeys(lName);

                //save
                tactAddNewContactPage.getSaveNewContactButton().tap();
            }
        });

    }
}
