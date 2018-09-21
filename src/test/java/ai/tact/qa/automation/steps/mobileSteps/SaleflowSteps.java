package ai.tact.qa.automation.steps.mobileSteps;

import ai.tact.qa.automation.testcomponents.mobile.TactSaleflowPage;
import ai.tact.qa.automation.utils.LogUtil;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;
import cucumber.api.PendingException;
import cucumber.api.java8.En;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SaleflowSteps implements En {

    private static final Logger log = LogUtil.setLoggerHandler(Level.ALL);

    public SaleflowSteps() {
        When("^Saleflow: I click \"(Log|Task|Note)\" activity option$", (String activityOption) -> {
            log.info("^Saleflow: I click " + activityOption + " activity option$");

            TactSaleflowPage tactSaleflowPage = new TactSaleflowPage();

            switch (activityOption) {
                case "Log":
                    WebDriverWaitUtils.waitUntilElementIsVisible(tactSaleflowPage.getSaleflowLogButton());
                    tactSaleflowPage.getSaleflowLogButton().tap();
                    break;
                case "Task":
                    WebDriverWaitUtils.waitUntilElementIsVisible(tactSaleflowPage.getSaleflowTaskButton());
                    tactSaleflowPage.getSaleflowTaskButton().tap();
                    break;
                case "Note":
                    WebDriverWaitUtils.waitUntilElementIsVisible(tactSaleflowPage.getSaleflowNoteButton());
                    tactSaleflowPage.getSaleflowNoteButton().tap();
                    break;

            }
        });
    }
}
