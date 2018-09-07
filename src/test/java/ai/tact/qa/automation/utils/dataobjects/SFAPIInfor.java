package ai.tact.qa.automation.utils.dataobjects;

public class SFAPIInfor {
    /**
     clientId:
     clientSecret:
     userName:
     password:
     */

    private String clientId = null;
    private String clientSecret = null;
    private String userName = null;
    private String password = null;

    public SFAPIInfor () {
    }

    /**
     * Get and Set properties
     */
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId=clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret=clientSecret;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName=userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password=password;
    }
}
