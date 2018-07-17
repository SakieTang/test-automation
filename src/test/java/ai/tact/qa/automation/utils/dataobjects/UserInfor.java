package ai.tact.qa.automation.utils.dataobjects;

public class UserInfor {
    private String salesforceIOSAccountName = null;
    private String salesforceIOSEmailAddress = null;
    private String saleforceIOSPwd = null;

    private String salesforceAndroidAccountName = null;
    private String salesforceAndroidEmailAddress = null;
    private String salesforceAndroidPwd = null;
    private String salesforceRecoverPwdKeyword = null;
    private String salesforcePhone = null;

    private String exchangeIOSEmailAddress = null;
    private String exchangeIOSEmailPwd = null;
    private String exchangeAndroidEmailAddress = null;
    private String exchangeAndroidEmailPwd = null;
    private String exchangeServer = null;

    private String linkedInIOSEmailAddress = null;
    private String linkedInAndroidEmailAddress = null;
    private String linkedInPwd = null;

    private String gmailIOSEmailAddress = null;
    private String gmailAndroidEmailAddress = null;
    private String gmailPwd = null;

    public UserInfor() { }

    //set - SF
    public void setSalesforceIOSAccountName(String salesforceIOSAccountName) { this.salesforceIOSAccountName = salesforceIOSAccountName; }
    public void setSalesforceIOSEmailAddress(String salesforceIOSEmailAddress) { this.salesforceIOSEmailAddress = salesforceIOSEmailAddress; }
    public void setSaleforceIOSPwd(String salesforceIOSPwd) { this.saleforceIOSPwd = salesforceIOSPwd; }

    public void setSalesforceAndroidAccountName(String salesforceAndroidAccountName) { this.salesforceAndroidAccountName = salesforceAndroidAccountName; }
    public void setSalesforceAndroidEmailAddress(String salesforceAndroidEmailAddress) { this.salesforceAndroidEmailAddress = salesforceAndroidEmailAddress; }
    public void setSalesforceAndroidPwd(String saleforceAndroidPwd) { this.salesforceAndroidPwd = saleforceAndroidPwd; }

    public void setSalesforceRecoverPwdKeyword(String salesforceRecoverPwdKeyword) { this.salesforceRecoverPwdKeyword = salesforceRecoverPwdKeyword; }
    public void setSalesforcePhone(String salesforcePhone) { this.salesforcePhone = salesforcePhone; }


    //set - Exchange
    public void setExchangeIOSEmailAddress(String exchangeIOSEmailAddress) { this.exchangeIOSEmailAddress = exchangeIOSEmailAddress; }
    public void setExchangeIOSEmailPwd(String exchangeIOSEmailPwd) { this.exchangeIOSEmailPwd = exchangeIOSEmailPwd; }

    public void setExchangeAndroidEmailAddress(String exchangeAndroidEmailAddress) { this.exchangeAndroidEmailAddress = exchangeAndroidEmailAddress; }
    public void setExchangeAndroidEmailPwd(String exchangeAndroidEmailPwd) { this.exchangeAndroidEmailPwd = exchangeAndroidEmailPwd; }

    public void setExchangeServer(String exchangeServer) { this.exchangeServer = exchangeServer; }


    //set - LinkedIn
    public void setLinkedInIOSEmailAddress(String linkedInIOSEmailAddress) { this.linkedInIOSEmailAddress = linkedInIOSEmailAddress; }
    public void setLinkedInAndroidEmailAddress(String linkedInAndroidEmailAddress) { this.linkedInAndroidEmailAddress = linkedInAndroidEmailAddress; }
    public void setLinkedInPwd(String linkedInPwd) { this.linkedInPwd = linkedInPwd; }


    //set - Gmail
    public void setGmailIOSEmailAddress(String gmailIOSEmailAddress) { this.gmailIOSEmailAddress = gmailIOSEmailAddress; }
    public void setGmailAndroidEmailAddress(String gmailAndroidEmailAddress) { this.gmailAndroidEmailAddress = gmailAndroidEmailAddress; }
    public void setGmailPwd(String gmailPwd) { this.gmailPwd = gmailPwd; }


    //get - SF
    public String getSalesforceIOSAccountName() { return salesforceIOSAccountName; }
    public String getSalesforceIOSEmailAddress() { return salesforceIOSEmailAddress; }
    public String getSaleforceIOSPwd() { return saleforceIOSPwd; }

    public String getSalesforceAndroidAccountName() { return salesforceAndroidAccountName; }
    public String getSalesforceAndroidEmailAddress() { return salesforceAndroidEmailAddress; }
    public String getSalesforceAndroidPwd() { return salesforceAndroidPwd; }

    public String getSalesforceRecoverPwdKeyword() { return salesforceRecoverPwdKeyword; }
    public String getSalesforcePhone() { return salesforcePhone; }


    //get - Exchange
    public String getExchangeIOSEmailAddress() { return exchangeIOSEmailAddress; }
    public String getExchangeIOSEmailPwd() { return exchangeIOSEmailPwd; }

    public String getExchangeAndroidEmailAddress() { return exchangeAndroidEmailAddress; }
    public String getExchangeAndroidEmailPwd() { return exchangeAndroidEmailPwd; }

    public String getExchangeServer() { return exchangeServer; }


    //get - LinkedIn
    public String getLinkedInIOSEmailAddress() { return linkedInIOSEmailAddress; }
    public String getLinkedInAndroidEmailAddress() { return linkedInAndroidEmailAddress; }
    public String getLinkedInPwd() { return linkedInPwd; }


    //get - Gmail
    public String getGmailIOSEmailAddress() { return gmailIOSEmailAddress; }
    public String getGmailAndroidEmailAddress() { return gmailAndroidEmailAddress; }
    public String getGmailPwd() { return gmailPwd; }

    @Override
    public String toString() {
        return "UserInfor{" +
                //SF
                "salesforceIOSAccountName='" + salesforceIOSAccountName + '\'' +
                ", salesforceIOSEmailAddress='" + salesforceIOSEmailAddress + '\'' +
                ", saleforceIOSPwd='" + saleforceIOSPwd + "\'" +

                ", salesforceAndroidAccountName='" + salesforceAndroidAccountName + '\'' +
                ", salesforceAndroidEmailAddress='" + salesforceAndroidEmailAddress + '\'' +
                ", salesforceAndroidPwd='" + salesforceAndroidPwd + "\'" +

                ", salesforceRecoverPwdKeyword='" + salesforceRecoverPwdKeyword + '\'' +
                ", salesforcePhone='" + salesforcePhone + '\'' +

                //Exchange
                ", exchangeIOSEmailAddress='" + exchangeIOSEmailAddress + '\'' +
                ", exchangeIOSEmailPwd='" + exchangeIOSEmailPwd + '\'' +
                ", exchangeAndroidEmailAddress='" + exchangeAndroidEmailAddress + '\'' +
                ", exchangeAndroidEmailPwd='" + exchangeAndroidEmailPwd + '\'' +
                ", exchangeServer='" + exchangeServer + "\'" +

                //LinkedIn
                ", linkedInIOSEmailAddress='" + linkedInIOSEmailAddress + "\'" +
                ", linkedInAndroidEmailAddress='" + linkedInAndroidEmailAddress + "\'" +
                ", linkedInPwd='" + linkedInPwd + "\'" +

                //Gmail
                ", gmailIOSEmailAddress='" + gmailIOSEmailAddress + '\'' +
                ", gmailAndroidEmailAddress='" + gmailAndroidEmailAddress + '\'' +
                ", gmailPwd='" + gmailPwd + '\'' +
                '}';
    }
}
