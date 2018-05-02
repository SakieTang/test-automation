package ai.tact.qa.automation.utils;

import ai.tact.qa.automation.utils.dataobjects.AlexaResponseInfo;
import ai.tact.qa.automation.utils.dataobjects.UserInfor;
import ai.tact.qa.automation.utils.dataobjects.WebUserInfor;

import java.util.Hashtable;

public class CustomPicoContainer {

    private static CustomPicoContainer customPicoContainer;
    private UserInfor userInfor;
    private WebUserInfor webUserInfor;
    private Hashtable<String, Object> allAlexaResponse;

    static {
        customPicoContainer = new CustomPicoContainer();
    }

    private  CustomPicoContainer() {}

    public static  CustomPicoContainer getInstance() {
        return customPicoContainer;
    }

    //setter
    public void setUserInfor(UserInfor userInfor) { this.userInfor = userInfor; }
    public void setWebUserInfor(WebUserInfor webUserInfor) { this.webUserInfor = webUserInfor; }


    public void setAlexaResponseInfos(Hashtable<String, Object> allAlexaResponse) { this.allAlexaResponse = allAlexaResponse; }

    //getter
    public UserInfor getUserInfor() { return userInfor; }
    public WebUserInfor getWebUserInfor() { return webUserInfor; }
    public Hashtable<String, Object> getAlexaResponseInfos() { return allAlexaResponse; }
}
