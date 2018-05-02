package ai.tact.qa.automation.utils;

import ai.tact.qa.automation.utils.dataobjects.UserInfor;

public class CustomPicoContainer {

    private static CustomPicoContainer customPicoContainer;
    private UserInfor userInfor;

    static {
        customPicoContainer = new CustomPicoContainer();
    }

    private  CustomPicoContainer() {}

    public static  CustomPicoContainer getInstance() {
        return customPicoContainer;
    }

    public void setUserInfor(UserInfor userInfor) {
        this.userInfor = userInfor;
    }

    public UserInfor getUserInfor() {
        return userInfor;
    }
}
