package ai.tact.qa.automation.steps.mobileSteps;

import ai.tact.qa.automation.testcomponents.mobile.TactContact.TactContactObjPage;
import ai.tact.qa.automation.testcomponents.mobile.TactNavigateTabBarPage;
import ai.tact.qa.automation.testcomponents.mobile.TactSearch.TactSearchContactsPage;
import ai.tact.qa.automation.testcomponents.mobile.TactSetting.NotebookPage;
import ai.tact.qa.automation.utils.DriverUtils;
import ai.tact.qa.automation.utils.LogUtil;
import com.paypal.selion.platform.grid.Grid;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;
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
        And("^Notebook: I click back icon back to notebook page$", () -> {
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
                if (Grid.driver().findElementsByXPath(tactNavigateTabBarPage.getNotebookButton().getLocator()).size() != 0){
                    System.out.println("now I am in notebook page");
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
        Then("^Notebook: I veirfy deleted \"(note|task)\" from Notebook page$", (String activityOption) -> {
            log.info("^Notebook: I veirfy deleted " + activityOption + " from Notebook page$");
            NotebookPage notebookPage = new NotebookPage();
            long beginTime = System.currentTimeMillis();
            System.out.println("beginTime " + beginTime);

            String activityName = null;
            switch (activityOption) {
                case "note":
                    activityName = TactPinSteps.noteTitle;
                    break;
                case "task":
                    activityName = TactPinSteps.taskSubject;
                    break;
            }

//            String stageLoc = notebookPage.getTargetActivityNameLabel().getLocator().replace("activityName", activityName);
//            System.out.println("stageLoc ==> " + stageLoc);
//            WebDriverWaitUtils.waitUntilElementIsInvisible(stageLoc);
//            long endTime = System.currentTimeMillis();
//            System.out.println("endTime " + endTime);
//
//            long time = (endTime - beginTime)/(1000*1000);
//            System.out.println("sync time " + time );

            System.out.println("activity is gone");
            DriverUtils.sleep(30);
        });
    }
}
