package ai.tact.qa.automation.steps.mobileSteps;

import ai.tact.qa.automation.testcomponents.mobile.TactSetting.NotebookPage;
import ai.tact.qa.automation.utils.DriverUtils;
import ai.tact.qa.automation.utils.LogUtil;
import cucumber.api.java8.En;

import java.util.logging.Level;
import java.util.logging.Logger;

public class NotebookSteps implements En {

    private static final Logger log = LogUtil.setLoggerHandler(Level.ALL);

    public NotebookSteps() {
        Then("^Notebook: I search this \"(note|log|task|event)\" from Notebook and select it$", (String activityOption) -> {
            log.info("^Notebook: I search this " + activityOption + " from Notebook and select it$");
            NotebookPage notebookPage = new NotebookPage();

            String activityName = null;
            switch (activityOption) {
                case "note":
                    activityName = TactPinSteps.noteTitle;
                    break;
                case "log":
                    activityName = TactPinSteps.logSubject;
                    break;
                case "task":
                    activityName = TactPinSteps.taskSubject;
                    break;
                case "event":
                    activityName = TactPinSteps.eventSubject;
                    break;
            }

            DriverUtils.clickOption(notebookPage.getTargetActivityNameLabel(), "activityName", activityName);
        });
        Then("^Notebook: I veirfy deleted \"(note|log|task|event)\" from Notebook page$", (String activityOption) -> {
            log.info("^Notebook: I veirfy deleted " + activityOption + " from Notebook page$");
            NotebookPage notebookPage = new NotebookPage();
            long beginTime = System.currentTimeMillis();
            System.out.println("beginTime " + beginTime);

            String activityName = null;
            switch (activityOption) {
                case "note":
                    activityName = TactPinSteps.noteTitle;
                    break;
                case "log":
                    activityName = TactPinSteps.logSubject;
                    break;
                case "task":
                    activityName = TactPinSteps.taskSubject;
                    break;
                case "event":
                    activityName = TactPinSteps.eventSubject;
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
            DriverUtils.sleep(5);
        });
    }
}
