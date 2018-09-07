package ai.tact.qa.automation.steps.h5Steps;

import ai.tact.qa.automation.Helper.OpenHelper;
import ai.tact.qa.automation.testcomponents.h5.Alexa.AlexaLoginHomePage;
import ai.tact.qa.automation.testcomponents.h5.CiscoSpark.SparkLoginHomePage;
import ai.tact.qa.automation.testcomponents.h5.Thread.ThreadLoginHomePage;
import ai.tact.qa.automation.testcomponents.h5.Thread.ThreadNavigatePage;
import ai.tact.qa.automation.utils.CustomPicoContainer;
import ai.tact.qa.automation.utils.DriverUtils;
import ai.tact.qa.automation.utils.LogUtil;
import com.paypal.selion.platform.grid.Grid;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;
import cucumber.api.java8.En;

import java.util.logging.Level;
import java.util.logging.Logger;

public class WebCommonSteps implements En {

    private static final Logger log = LogUtil.setLoggerHandler(Level.ALL);

    public WebCommonSteps() {

        Given("^WebCommon: I launch browser and open \"(Thread|Cisco Spark|Amazon|[^\"]*)\"$", (String container) -> {
            log.info("^WebCommon: I launch browser and open " + container + "$");
            int i = 0;
            System.out.println("**************" + i++);

            if (container.contains(".") || container.contains("|"))
            { }
            else {
                container = container.toLowerCase();
            }
            System.out.println("**************" + i++);

            switch (container) {
                case "Thread":
                    container = "www.thread.id/service3/";
                    break;
                case "Cisco Spark":
                    container = "teams.webex.com/signin";
                    break;
                case "Amazon":
                    container = "developer.amazon.com/alexa";
                    break;
            }

//            System.out.println("#########web : " + "https://teams.webex.com/signin/#");
////            Grid.driver().get("https://teams.webex.com/signin/#");
//            Grid.open("https://teams.webex.com/signin/#");
            OpenHelper.OpenWebsite(container);
        });

        When("^WebCommon: I login with \"(Thread|Cisco Spark|Amazon)\" user account \"([^\"]*)\" and password \"([^\"]*)\"$", (String channelName, String account, String pwd) -> {
            log.info("^WebCommon: I login with " + channelName + " user account and password$");
            ThreadNavigatePage threadNavigatePage = new ThreadNavigatePage();
            ThreadLoginHomePage threadLoginHomePage = new ThreadLoginHomePage();
            SparkLoginHomePage sparkLoginHomePage = new SparkLoginHomePage();
            AlexaLoginHomePage alexaLoginHomePage = new AlexaLoginHomePage();
            String accountName = account;
            String accountPwd = pwd;

            if (DriverUtils.isTextEmpty(account)) {
                accountName=CustomPicoContainer.getInstance().getUser().getAiChannelAccount();
            }
            if (DriverUtils.isTextEmpty(pwd)) {
                accountPwd=CustomPicoContainer.getInstance().getUser().getAiChannelAccountPwd();
            }

            switch (channelName) {
                case "Thread":
                    log.info(channelName + " : " + accountName + "/" + accountPwd);

//                    //user already login
//                    if (!threadLoginHomePage.getThreadImage().isElementPresent()) {
//                        WebPage page = (WebPage)threadNavigatePage.getThreadIconLink().clickAndExpectOneOf(threadLoginHomePage, threadNavigatePage);
//                        if (page instanceof ThreadLoginHomePage) {
//                            Grid.driver().switchTo().frame(threadLoginHomePage.getThreadImage().getLocator());
//                            System.out.println("now in the login home page");
//                            DriverUtils.sleep(5);
//                            threadLoginHomePage.getEmailAddressTextField().type(accountName);
//                            System.out.println("switch the frame and input the accountName");
//                            System.out.println("let's start to wait");
//                            DriverUtils.sleep(50);
//                        }
//                    }

                    //login homePage
                    WebDriverWaitUtils.waitUntilElementIsVisible(threadLoginHomePage.getEmailAddressTextField().getLocator());//threadLoginHomePage.getThreadImage().getLocator());
                    System.out.println("find the getEmailAddressTextField");
                    DriverUtils.sleep(1);

                    //entry accountName && accountPwd && loginButton in the same page
                    threadLoginHomePage.getEmailAddressTextField().type(accountName);
                    threadLoginHomePage.getPwdTextField().type(accountPwd);
                    threadLoginHomePage.getLogInButton().click();

                    break;
                case "Cisco Spark":
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
                case "Amazon":
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
        And("^WebCommon: I close driver$", () -> {
            log.info("^WebCommon: I close driver$");

            Grid.driver().close();
        });
        Given("^WebCommon: I testing this msg$", () -> {
            System.out.println("^WebCommon: I testing this msg$");
            log.info("^WebCommon: I testing this msg$");
        });
    }
}
