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

    private ThreadLoginHomePage threadLoginHomePage;
    private ThreadNavigatePage threadNavigatePage;
    private SparkLoginHomePage sparkLoginHomePage;
    private SparkNavigatePage sparkNavigatePage;
    private AlexaLoginHomePage alexaLoginHomePage;

    private Logger log = LogUtil.setLoggerHandler(Level.ALL);

    public WebCommonSteps() {

        threadLoginHomePage = new ThreadLoginHomePage();
        threadNavigatePage = new ThreadNavigatePage();

        sparkLoginHomePage = new SparkLoginHomePage();
        sparkNavigatePage = new SparkNavigatePage();

        alexaLoginHomePage = new AlexaLoginHomePage();

        Given("^WebCommon: I launch browser and open \"([^\"]*)\"$", (String container) -> {
            log.info("^WebCommon: I launch browser and open " + container + "$");

            //Thread        : https://thread.id/service3/
            //Cisco Spark   : https://teams.webex.com/signin
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
            channelName = channelName.toLowerCase();
            String accountName = null;
            String accountPwd = null;

            switch (channelName) {
                case "thread":
                    accountName = CustomPicoContainer.getInstance().getWebUserInfor().getThreadAccount();
                    accountPwd = CustomPicoContainer.getInstance().getWebUserInfor().getThreadAccountPwd();
                    //automation.thread@gmail.com/Tact0218
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
                    accountName = CustomPicoContainer.getInstance().getWebUserInfor().getCiscoSparkAccount();
                    accountPwd = CustomPicoContainer.getInstance().getWebUserInfor().getCiscoSparkAccountPwd();
                    //automation.ciscoSpark@gmail.com/Tact0218
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
                    accountName = CustomPicoContainer.getInstance().getWebUserInfor().getAlexaAccount();
                    accountPwd = CustomPicoContainer.getInstance().getWebUserInfor().getAlexaAccountPwd();
                    //automation.amazonAlexa@gmail.com/Tact0218
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

        //        //品质优选链接点击
        //        String javascript = "arguments[0].scrollIntoView(true);";
        //        ((JavascriptExecutor) Grid.driver()).executeScript(javascript, h5FengQiangPage.getYouXuanLink().getElement());


        //Table
//        List<WebElement> cells = Grid.driver().findElements(By.className("UIATableCell"));
//        assertEquals(9, cells.size());
//        // get the 1st mountain
//        WebElement first = cells.get(0);
//        first.click();


        //
//        WebPage page = (WebPage) headerPage.getLoginButton().clickAndExpectOneOf(loginPopUpPage,loginPage);
//
//        if(page instanceof SalesLoginPopUpPage){
//            Grid.driver().switchTo().frame(loginPopUpPage.getIframeLabel().getLocator());
//        }
    }
}
