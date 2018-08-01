package ai.tact.qa.automation.utils;

import ai.tact.qa.automation.utils.dataobjects.UserInfor;
import ai.tact.qa.automation.utils.dataobjects.WebUserInfor;

public class CustomPicoContainer {

    private static CustomPicoContainer customPicoContainer;
    private UserInfor userInfor;
    private WebUserInfor webUserInfor;

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

    //getter
    public UserInfor getUserInfor() { return userInfor; }
    public WebUserInfor getWebUserInfor() { return webUserInfor; }
}
