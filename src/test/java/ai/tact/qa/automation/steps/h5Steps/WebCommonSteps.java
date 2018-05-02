package ai.tact.qa.automation.steps.h5Steps;

import ai.tact.qa.automation.Helper.OpenHelper;
import ai.tact.qa.automation.testcomponents.h5.Alexa.AlexaLoginHomePage;
import ai.tact.qa.automation.testcomponents.h5.CiscoSpark.SparkLoginHomePage;
import ai.tact.qa.automation.testcomponents.h5.CiscoSpark.SparkNavigatePage;
import ai.tact.qa.automation.testcomponents.h5.Thread.ThreadLoginHomePage;
import ai.tact.qa.automation.testcomponents.h5.Thread.ThreadNavigatePage;
import ai.tact.qa.automation.utils.CustomPicoContainer;
import ai.tact.qa.automation.utils.DriverUtils;
import ai.tact.qa.automation.utils.LogUtil;
import com.paypal.selion.platform.grid.Grid;
import com.paypal.selion.platform.html.WebPage;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;
import cucumber.api.java8.En;

import java.util.logging.Level;
import java.util.logging.Logger;

public class WebCommonSteps implements En {

    private static final Logger log = LogUtil.setLoggerHandler(Level.ALL);

    public WebCommonSteps() {

        Given("^WebCommon: I launch browser and open \"([^\"]*)\"$", (String container) -> {
            log.info("^WebCommon: I launch browser and open " + container + "$");

            if (container.contains(".") || container.contains("|"))
            { }
            else {
                container = container.toLowerCase();
            }

            switch (container) {
                case "thread":
                    container = "www.thread.id/service3/";
                    break;
                case "cisco spark":
                    container = "teams.webex.com/signin";
                    break;
                case "amazon":
                    container = "developer.amazon.com/alexa";
                    break;
            }
            OpenHelper.OpenThread(container);
        });

        When("^WebCommon: I login with \"([^\"]*)\" user account and password$", (String channelName) -> {
            log.info("^WebCommon: I login with " + channelName + " user account and password$");
            ThreadNavigatePage threadNavigatePage = new ThreadNavigatePage();
            ThreadLoginHomePage threadLoginHomePage = new ThreadLoginHomePage();
            SparkLoginHomePage sparkLoginHomePage = new SparkLoginHomePage();
            AlexaLoginHomePage alexaLoginHomePage = new AlexaLoginHomePage();

            channelName = channelName.toLowerCase();
            String accountName = null;
            String accountPwd = null;

            switch (channelName) {
                case "thread":
                    accountName = CustomPicoContainer.getInstance().getWebUserInfor().getThreadAccount();    //"automation.thread@gmail.com"
                    accountPwd = CustomPicoContainer.getInstance().getWebUserInfor().getThreadAccountPwd();
                    log.info(channelName + " : " + accountName + "/" + accountPwd);

                    //user already login
                    if (!threadLoginHomePage.getThreadImage().isElementPresent()) {
                        WebPage page = (WebPage)threadNavigatePage.getThreadIconLink().clickAndExpectOneOf(threadLoginHomePage, threadNavigatePage);
                        if (page instanceof ThreadLoginHomePage) {
                            Grid.driver().switchTo().frame(threadLoginHomePage.getThreadImage().getLocator());
                            System.out.println("now in the login home page");
                            DriverUtils.sleep(5);
                            threadLoginHomePage.getEmailAddressTextField().type(accountName);
                            System.out.println("switch the frame and input the accountName");
                            DriverUtils.sleep(50);
                        }
                    }

                    //login homePage
                    WebDriverWaitUtils.waitUntilElementIsVisible(threadLoginHomePage.getThreadImage().getLocator());
                    //entry accountName && accountPwd && loginButton in the same page
                    threadLoginHomePage.getEmailAddressTextField().type(accountName);
                    threadLoginHomePage.getPwdTextField().type(accountPwd);
                    threadLoginHomePage.getLogInButton().click();

                    break;
                case "cisco spark":
                    accountName = CustomPicoContainer.getInstance().getWebUserInfor().getCiscoSparkAccount();   //automation.ciscoSpark@gmail.com
                    accountPwd = CustomPicoContainer.getInstance().getWebUserInfor().getCiscoSparkAccountPwd(); //Tact0218
                    log.info(channelName + " : " + accountName + "/" + accountPwd);

                    //login homePage
                    WebDriverWaitUtils.waitUntilElementIsVisible(sparkLoginHomePage.getSparkImage().getLocator());
                    //type accountName and click "Next" button
                    sparkLoginHomePage.getEmailAddressTextField().type(accountName);
                    sparkLoginHomePage.getNextButton().click();

                    //wait for pwd textField visible
                    WebDriverWaitUtils.waitUntilElementIsVisible(sparkLoginHomePage.getPwdTextField().getLocator());
                    //type pwd and click the "Sign In" button
                    sparkLoginHomePage.getPwdTextField().type(accountPwd);
                    sparkLoginHomePage.getSingInButton().click();

                    WebDriverWaitUtils.waitUntilElementIsVisible(sparkLoginHomePage.getSpinnerImage().getLocator());
                    System.out.println("the spin is visible now, then waiting for it invisible");

                    //waiting for spin invisible
                    WebDriverWaitUtils.waitUntilElementIsInvisible(sparkLoginHomePage.getSpinnerImage().getLocator());
                    System.out.println("the spin is invisible");

                    break;
                case "amazon":
                    accountName = CustomPicoContainer.getInstance().getWebUserInfor().getAlexaAccount();    //automation.amazonAlexa@gmail.com
                    accountPwd = CustomPicoContainer.getInstance().getWebUserInfor().getAlexaAccountPwd();  //Tact0218
                    log.info(channelName + " : " + accountName + "/" + accountPwd);

                    //amazon homePage to login page
                    alexaLoginHomePage.getSignInLink().click();
                    //login homePage
                    WebDriverWaitUtils.waitUntilElementIsVisible(alexaLoginHomePage.getSignInButton().getLocator());
                    //entry accountName && accountPwd && loginButton in the same page
                    alexaLoginHomePage.getEmailAddressTextField().type(accountName);
                    alexaLoginHomePage.getPwdTextField().type(accountPwd);
                    alexaLoginHomePage.getSignInButton().click();

                    break;
            }
        });
    }
}
