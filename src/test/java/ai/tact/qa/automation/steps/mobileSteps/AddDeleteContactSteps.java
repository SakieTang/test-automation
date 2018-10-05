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

            //Save or not save to Phone
            DriverUtils.switchButton(saveToPhone, tactAddNewContactPage.getSaveToIPhoneSwitch());
            //send or not send to Salesforce
            DriverUtils.switchButton(sendToSF, tactAddNewContactPage.getSendToSaleforceSwitch());
        });
        When("^AddContact: I input a user name \"([^\"]*)\"$", (String contactName) -> {
            log.info("^AddContact: I input user name " + contactName);
            TactAddNewContactPage tactAddNewContactPage = new TactAddNewContactPage();

            //entry name
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
            if (DriverUtils.isAndroid()) {
                DriverUtils.slideUP();
            }
            tactAddNewContactPage.getLastNameTextField().setText(lName);
        });

        When("^AddContact: I input phone \"([^\"]*)\", email \"([^\"]*)\"$", (String phoneNumber, String emailValue) -> {
            log.info("^AddContact: I input phone "+ phoneNumber + ", email " + emailValue + "$");
            TactAddNewContactPage tactAddNewContactPage = new TactAddNewContactPage();
            TactContactsMainPage tactContactsMainPage = new TactContactsMainPage();
            TactContactObjPage tactContactObjPage = new TactContactObjPage();
            TactAlertsPopUpPage tactAlertsPopUpPage = new TactAlertsPopUpPage();

            //phone
            if (!DriverUtils.isTextEmpty(phoneNumber)){
                if (phoneNumber.equals("randomNumber")) {
                    phoneNumber = getRandomPhoneNumber();
                }
                if (Grid.driver().findElementsByXPath(tactAddNewContactPage.getAddPhoneNumberButton().getLocator()).size()==0) {
                    DriverUtils.slideUP();
                    tactAddNewContactPage.getSfPhoneTextField().setText(phoneNumber);
                } else {
                    tactAddNewContactPage.getAddPhoneNumberButton().tap();
                    tactAddNewContactPage.getWorkPhoneTextField().setText(phoneNumber);
                }
            }

            //email
            if (!DriverUtils.isTextEmpty(emailValue)) {
                emailValue = String.format("%s.%s%s", this.contactName.split(" ")[0], this.contactName.split(" ")[1], emailValue );
                System.out.println("emailValue " + emailValue);
                if (Grid.driver().findElementsByXPath(tactAddNewContactPage.getAddEmailButton().getLocator()).size()==0) {
                    tactAddNewContactPage.getSfEmailTextField().setText(emailValue);
                } else {
                    tactAddNewContactPage.getAddEmailButton().tap();
                    tactAddNewContactPage.getAddEmailButton().tap();
                    tactAddNewContactPage.getHomeEmailTextField().setText(emailValue);
                }
            }
        });

        When("^AddContact: I save \"([^\"]*)\"$", (String isSave) -> {
            log.info("^AddContact: I save " + isSave + "$");
            TactAddNewContactPage tactAddNewContactPage = new TactAddNewContactPage();
            TactContactsMainPage tactContactsMainPage = new TactContactsMainPage();
            TactContactObjPage tactContactObjPage = new TactContactObjPage();
            TactAlertsPopUpPage tactAlertsPopUpPage = new TactAlertsPopUpPage();

            //save or not save
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
            }

            WebDriverWaitUtils.waitUntilElementIsVisible(tactContactsMainPage.getTactContactsTitleLabel());
            log.info("save ? " + isSave);
            DriverUtils.sleep(2);
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

            for (int i=0; i<times; i++) {

                System.out.println("Time: " + i);

                //switch to create contacts page
                tactContactsMainPage.getContactsPlusIconButton().tap();
                tactAddNewContactPage.getTactAddNewContactButton().tap();

                tactAddNewContactPage.getLastNameTextField().setText(lName);

                //save
                tactAddNewContactPage.getSaveNewContactButton().tap();
            }
        });
        Then("^AddContact: I input a user name \"([^\"]*)\" and \"([^\"]*)\"$", (String contactName, String isSave) -> {
            log.info("^AddContact: I input a user name " + contactName + " and " + isSave + "$");
            log.info("^AddContact: I input user name " + contactName);
            TactAddNewContactPage tactAddNewContactPage = new TactAddNewContactPage();
            TactContactsMainPage tactContactsMainPage = new TactContactsMainPage();
            TactContactObjPage tactContactObjPage = new TactContactObjPage();
            TactAlertsPopUpPage tactAlertsPopUpPage = new TactAlertsPopUpPage();

            //entry name
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
            if (DriverUtils.isAndroid()) {
                DriverUtils.slideUP();
            }
            tactAddNewContactPage.getLastNameTextField().setText(lName);

            //save or not save
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
            }

            WebDriverWaitUtils.waitUntilElementIsVisible(tactContactsMainPage.getTactContactsTitleLabel());
            log.info("save ? " + isSave);
            DriverUtils.sleep(10);
        });
    }

    private String getRandomPhoneNumber(){
        String randomNum = null;

        randomNum = String.format("65%s", AddDeleteContactSteps.contactName.split(" ")[1].substring(0,8));

        return randomNum;
    }
}
