package ai.tact.qa.automation.Helper;

import com.paypal.selion.platform.grid.Grid;
import com.paypal.selion.reports.runtime.SeLionReporter;

public class OpenHelper {

    public static void  OpenWebsite(String container) {

        String url = String.format("%s/#", "https://" + container );

        System.out.println("url " + url);

        Grid.driver().get(url); //Both work

        System.out.println("platform == " + Grid.getWebTestSession().getPlatform());
        System.out.println("platform == " + Grid.getWebTestSession().getBrowser());
        System.out.println("platform == " + Grid.getWebTestSession().getAdditionalCapabilities());
        System.out.println("platform == " + Grid.getWebTestSession().isStarted());
        System.out.println("platform == " + Grid.getWebTestSession().getBrowserHeight());
        System.out.println("platform == " + Grid.getWebTestSession().getBrowserWidth());

        System.out.println("title " + Grid.driver().getTitle());
        SeLionReporter.log(Grid.driver().getTitle(), true, true);
    }

    public static void  OpenSales(String testContainer) {

        String url = String.format("%s/sales/", "https://www." + testContainer + ".dev");

        Grid.open(url);
        SeLionReporter.log(Grid.driver().getTitle(), true, true);
    }
}

