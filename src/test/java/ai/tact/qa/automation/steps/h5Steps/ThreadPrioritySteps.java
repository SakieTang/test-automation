package ai.tact.qa.automation.steps.h5Steps;

import ai.tact.qa.automation.testcomponents.h5.Thread.ThreadPriorityPage;
import ai.tact.qa.automation.utils.DriverUtils;
import ai.tact.qa.automation.utils.LogUtil;
import com.paypal.selion.platform.grid.Grid;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;
import cucumber.api.PendingException;
import cucumber.api.java8.En;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadPrioritySteps implements En {

    private static final Logger log = LogUtil.setLoggerHandler(Level.ALL);

    public ThreadPrioritySteps() {
        And("^ThreadPriority: I click \"([^\"]*)\" times dismiss$", (String timeString) -> {
            log.info("^ThreadPriority: I click " + timeString + " times dismiss$");
            ThreadPriorityPage threadPriorityPage = new ThreadPriorityPage();
            int time;
            int number;
            int size;
            time = number = size = 0;

//            WebDriverWaitUtils.waitUntilElementIsVisible(threadPriorityPage.getDismissButton().getLocator());
            if (!timeString.equalsIgnoreCase("all")) {
                time = Integer.parseInt(timeString);
            }
            System.out.println("time " + time);
            WebDriverWaitUtils.waitUntilElementIsVisible(threadPriorityPage.getMentionNameLabel().getLocator());


            //scoll down the mention container
            System.out.println("start to scroll");
            WebElement endOfContainer = threadPriorityPage.getEndOfMentionContainer().getElement();
            int scrollPix = 100;
            JavascriptExecutor js = (JavascriptExecutor) Grid.driver();
            while (!endOfContainer.isDisplayed()) {
                String s=String.format("%s.animate({ scrollTop: \"%dpx\" })", "$(\"div[class='tracking-list-container']\")", scrollPix);
                js.executeScript(s);
                scrollPix += 500;
            }

            number = threadPriorityPage.getDismissButton().getElements().size();
            size = number;
            System.out.println("number : "  + number);


            long startTime = System.currentTimeMillis();
            while (number > 0 ) {
                Actions mouse = new Actions(Grid.driver());
                WebElement element = threadPriorityPage.getDismissButton().getElements().get(number-1);
                mouse.moveToElement(element).perform();
                element.click();
                DriverUtils.sleep(0.5);
                number--;

            }
            long endTime = System.currentTimeMillis();

            System.out.println("After click one element \n **nmber : "  + number);
            System.out.println( (endTime-startTime)/1000 + " s" );
        });
    }
}
