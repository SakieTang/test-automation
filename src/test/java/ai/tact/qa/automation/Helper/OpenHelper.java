package ai.tact.qa.automation.Helper;

import com.paypal.selion.platform.grid.Grid;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;
import com.paypal.selion.reports.runtime.SeLionReporter;
import org.openqa.selenium.WebDriver;



public class OpenHelper {

    public static void  OpenThread(String container) {

        //Thread        https://thread.id/service3/     Thread account:automation.thread@gmail.com/Tact0218
        //Cisco spark   https://teams.webex.com/signin  Cisco Spark account: automation.ciscoSpark@gmail.com/Tact0218

        //https://thread.id/service3/
//        String testContainer = "thread.id/service3/";

        String url = String.format("%s/#", "https://" + container );

        System.out.println("url " + url);

        Grid.open(url);
        System.out.println("title " + Grid.driver().getTitle());
        SeLionReporter.log(Grid.driver().getTitle(), true, true);
    }

    public static void  OpenSales(String testContainer) {

        String url = String.format("%s/sales/", "https://www." + testContainer + ".dev");

        Grid.open(url);
        SeLionReporter.log(Grid.driver().getTitle(), true, true);
    }

//    public static void  OpenH5Product(String testContainer,String productID) {
//
//        H5ProductDetailsPage productDetailsPage = new H5ProductDetailsPage();
//
//        String url = String.format("%s/sales/" + productID + ".html", "https://www." + testContainer + ".dev");
//
//        //open the URL
//        Grid.open(url);
//        WebDriverWaitUtils.waitUntilElementIsPresent(productDetailsPage.getBuyNowButton().getLocator());
//
//        SeLionReporter.log(Grid.driver().getTitle(), true, true);
//    }
//
//    public static void  OpenPCProduct(String testContainer,String productID) {
//
//        ProductDetailsPage productDetailsPage = new ProductDetailsPage();
//
//        String url = String.format("%s/sales/" + productID + ".html", "https://www." + testContainer + ".dev");
//
//        //open the URL
//        Grid.open(url);
//        WebDriverWaitUtils.waitUntilElementIsPresent(productDetailsPage.getBuyButton().getLocator());
//
//        SeLionReporter.log(Grid.driver().getTitle(), true, true);
//    }
//
//    public static void OpenH5FengQiang(String testContainer){
//        H5FengQiangPage h5FengqiangPage = new H5FengQiangPage();
//        String url = String.format("https://m." + testContainer +"/sales/fengqiang/index");
//
//        Grid.open(url);
//        WebDriverWaitUtils.waitUntilElementIsPresent(h5FengqiangPage.getTodayTabLabel().getLocator());
//        SeLionReporter.log(Grid.driver().getTitle(), true, true);
//    }
}

