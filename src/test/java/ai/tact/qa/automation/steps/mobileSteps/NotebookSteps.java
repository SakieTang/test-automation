package ai.tact.qa.automation.steps.mobileSteps;

import ai.tact.qa.automation.testcomponents.mobile.TactContact.TactContactObjPage;
import ai.tact.qa.automation.testcomponents.mobile.TactNavigateTabBarPage;
import ai.tact.qa.automation.testcomponents.mobile.TactSearch.TactSearchContactsPage;
import ai.tact.qa.automation.testcomponents.mobile.TactSetting.NotebookPage;
import ai.tact.qa.automation.utils.DriverUtils;
import ai.tact.qa.automation.utils.LogUtil;
import com.paypal.selion.platform.grid.Grid;
import cucumber.api.PendingException;
import cucumber.api.java8.En;

import java.util.logging.Level;
import java.util.logging.Logger;

public class NotebookSteps implements En {

    private static final Logger log = LogUtil.setLoggerHandler(Level.ALL);

    public NotebookSteps() {
        Then("^Notebook: I search this \"(note|task)\" from Notebook and select it$", (String activityOption) -> {
            log.info("^Notebook: I search this " + activityOption + " from Notebook and select it$");
            NotebookPage notebookPage = new NotebookPage();

            String activityName = null;
            switch (activityOption) {
                case "note":
                    activityName = TactPinSteps.noteTitle;
                    break;
                case "task":
                    activityName = TactPinSteps.taskSubject;
                    break;
            }

            DriverUtils.clickOption(notebookPage.getTargetActivityNameLabel(), "activityName", activityName);
        });
        And("^Notebook: I click back icon back to more page$", () -> {
            log.info("^Notebook: I click back icon back to more page$");
            TactNavigateTabBarPage tactNavigateTabBarPage = new TactNavigateTabBarPage();

            String backLoc;

            if (DriverUtils.isIOS()) {
                backLoc = "//XCUIElementTypeNavigationBar/XCUIElementTypeButton[1]";
            } else {
                backLoc = "//android.widget.ImageButton[@content-desc='Navigate up']";
            }

            while (Grid.driver().findElementsByXPath(backLoc).size() != 0) {
                System.out.println("inside the loop, and still can see the back button");
                DriverUtils.sleep(0.5);
                Grid.driver().findElementByXPath(backLoc).click();
                if (Grid.driver().findElementsByXPath(tactNavigateTabBarPage.getTactContactsButton().getLocator()).size() != 0){
                    System.out.println("now I am in More page");
                    break;
                }
            }
//            if (DriverUtils.isAndroid()) {
//                while (Grid.driver().findElementsByXPath(backLoc).size() != 0) {
//                    System.out.println("inside the loop, and still can see the back button");
//                    DriverUtils.sleep(0.5);
//                    Grid.driver().findElementByXPath(backLoc).click();
//                }
//            } else {
//                Grid.driver().findElementByXPath(backLoc).click();
//            }
//            DriverUtils.sleep(200);
        });
    }
}
