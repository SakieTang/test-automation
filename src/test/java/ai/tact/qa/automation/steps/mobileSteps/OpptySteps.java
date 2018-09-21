package ai.tact.qa.automation.steps.mobileSteps;

import ai.tact.qa.automation.testcomponents.mobile.TactOpportunity.TactOpptiesMainPage;
import ai.tact.qa.automation.testcomponents.mobile.TactSearch.TactSearchOpptyPage;
import ai.tact.qa.automation.utils.DriverUtils;
import ai.tact.qa.automation.utils.LogUtil;
import com.paypal.selion.platform.grid.Grid;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;
import cucumber.api.java8.En;

import java.util.logging.Level;
import java.util.logging.Logger;

public class OpptySteps implements En {

    private static final Logger log = LogUtil.setLoggerHandler(Level.ALL);

    public OpptySteps() {

        Then("^Oppty: I go to create a new Oppty page$", () -> {
            log.info("^Oppty: I go to create a new Oppty page$");
            TactOpptiesMainPage tactOpptiesMainPage = new TactOpptiesMainPage();

            tactOpptiesMainPage.getOpportunitiesPlusIconButton().tap();
            DriverUtils.sleep(5);
        });

        When("^Oppty: I search one oppty \"([^\"]*)\" from opportunities list and select it$", (String opptyName) -> {
            log.info("^Oppty: I search one oppty " + opptyName + " from opportunities list and select it$");
            TactOpptiesMainPage tactOpptiesMainPage = new TactOpptiesMainPage();
            TactSearchOpptyPage tactSearchOpptyPage = new TactSearchOpptyPage();

            WebDriverWaitUtils.waitUntilElementIsVisible(tactOpptiesMainPage.getTactOpportunitiesDropDownButton());

            if (opptyName.equalsIgnoreCase("oppty_edited_Tact") ||
                    opptyName.equalsIgnoreCase("oppty_created_Tact")) {
                opptyName=AddEditDeleteOpptySteps.opportunityName;
                System.out.println("oppty name " + opptyName);
            }
            DriverUtils.slideDown();

            //
            if (DriverUtils.isAndroid())
            {
                DriverUtils.sleep(0.5);
                WebDriverWaitUtils.waitUntilElementIsVisible(tactSearchOpptyPage.getAndroidOpptyTabSearchIconButton().getLocator());
                tactSearchOpptyPage.getAndroidOpptyTabSearchIconButton().tap();
                System.out.println("loc : " + tactSearchOpptyPage.getAndroidOpptyTabSearchIconButton().getLocator());
                DriverUtils.sleep(2);
                if ((Grid.driver().findElementsByXPath(tactSearchOpptyPage.getAndroidOpptyTabSearchIconButton().getLocator())).size() != 0) {
                    System.out.println("did not click it, need to re-click");
                    DriverUtils.tapXY(1356,182, 200,2);
                    DriverUtils.sleep(5);
                }
                WebDriverWaitUtils.waitUntilElementIsVisible(tactSearchOpptyPage.getSearchTextField());
            }

            tactSearchOpptyPage.getSearchTextField().setText(opptyName);

            DriverUtils.clickOption(tactSearchOpptyPage.getNameEditTextField(), "opptyName", opptyName);

        });
    }
}
