package ai.tact.qa.automation.utils.dataobjects;

public class WebUserInfor {
    private String tactAccount = null; //automation.AI.tactsf.s@gmail.com
    private String tactAccountPwd = null; //Tact0218
    private String tactSFAccount = null; //automation.AI.tactsf.s@gmail.com
    private String tactSFAccountPwd = null; //Tact0218
    private String tactGmailAccount = null; //automation.tact@gmail.com
    private String tactGmailAccountPwd = null; //Tact2018

    private String threadWebsiteUrl = null; //https://thread.id/service3/
    private String threadAccount = null; //automation.thread@gmail.com
    private String threadAccountPwd = null; //Tact0218
    private String threadSFAccount = null; //automation.AI.threadsf.s@gmail.com
    private String threadSFAccountPwd = null; //Tact0218
    private String threadGmailAccount = null; //automation.thread@gmail.com
    private String threadGmailAccountPwd = null; //Tact2018

    private String ciscoSparkWebsiteUrl = null; //https://teams.webex.com/signin
    private String ciscoSparkAccount = null; //automation.ciscoSpark@gmail.com
    private String ciscoSparkAccountPwd = null; //Tact0218
    private String ciscoSparkSFAccount = null; //automation.AI.ciscoSparksf.s@gmail.com
    private String ciscoSparkSFAccountPwd = null; //Tact0218
    private String ciscoSparkGmailAccount = null; //automation.ciscoSpark@gmail.com
    private String ciscoSparkGmailAccountPwd = null; //Tact2018

    private String alexaWebsiteUrl = null;  //https://developer.amazon.com/
    private String alexaAccount = null;  //automation.amazonAlexa@gmail.com
    private String alexaAccountPwd = null;  //Tact0218
    private String alexaSFAccount = null;  //automation.AI.amazonAlexaSF.s@gmail.com
    private String alexaSFAccountPwd = null;  //Tact0218
    private String alexaGmailAccount = null;  //automation.AmazonAlexa@gmail.com
    private String alexaGmailAccountPwd = null;  //Tact2018

    public WebUserInfor() {}

    public WebUserInfor(
            String threadWebsiteUrl,
            String threadAccount,           String threadAccountPwd,
            String threadSFAccount,         String threadSFAccountPwd,
            String threadGmailAccount,      String threadGmailAccountPwd,

            String ciscoSparkWebsiteUrl,
            String ciscoSparkAccount,       String ciscoSparkAccountPwd,
            String ciscoSparkSFAccount,     String ciscoSparkSFAccountPwd,
            String ciscoSparkGmailAccount,  String ciscoSparkGmailAccountPwd,

            String alexaWebsiteUrl,
            String alexaAccount,            String alexaAccountPwd,
            String alexaSFAccount,          String alexaSFAccountPwd,
            String alexaGmailAccount,       String alexaGmailAccountPwd
    ) {
        super();
        //Tact
        this.setTactAccount(tactAccount);
        this.setTactAccountPwd(tactAccountPwd);
        this.setTactSFAccount(tactSFAccount);
        this.setTactSFAccountPwd(tactSFAccountPwd);
        this.setTactAccountPwd(tactSFAccountPwd);
        this.setTactGmailAccount(tactGmailAccount);
        this.setTactGmailAccountPwd(tactGmailAccountPwd);
        //Thread
        this.setThreadWebsiteUrl(threadWebsiteUrl);
        this.setThreadAccount(threadAccount);
        this.setThreadAccountPwd(threadAccountPwd);
        this.setThreadSFAccount(threadSFAccount);
        this.setThreadSFAccountPwd(threadSFAccountPwd);
        this.setThreadAccountPwd(threadSFAccountPwd);
        this.setThreadGmailAccount(threadGmailAccount);
        this.setThreadGmailAccountPwd(threadGmailAccountPwd);
        //CiscoSpark
        this.setCiscoSparkWebsiteUrl(ciscoSparkWebsiteUrl);
        this.setCiscoSparkAccount(ciscoSparkAccount);
        this.setCiscoSparkAccountPwd(ciscoSparkAccountPwd);
        this.setCiscoSparkSFAccount(ciscoSparkSFAccount);
        this.setCiscoSparkSFAccountPwd(ciscoSparkSFAccountPwd);
        this.setCiscoSparkGmailAccount(ciscoSparkGmailAccount);
        this.setCiscoSparkGmailAccountPwd(ciscoSparkGmailAccountPwd);
        //Alexa
        this.setAlexaWebsiteUrl(alexaWebsiteUrl);
        this.setAlexaAccount(alexaAccount);
        this.setAlexaAccountPwd(alexaAccountPwd);
        this.setAlexaSFAccount(alexaSFAccount);
        this.setAlexaSFAccountPwd(alexaSFAccountPwd);
        this.setAlexaGmailAccount(alexaGmailAccount);
        this.setAlexaGmailAccountPwd(alexaGmailAccountPwd);
    }

    //set - Tact
    public String getTactAccount() { return tactAccount; }
    public String getTactAccountPwd() { return tactAccountPwd; }
    public String getTactSFAccount() { return tactSFAccount; }
    public String getTactSFAccountPwd() { return tactSFAccountPwd; }
    public String getTactGmailAccount() { return tactGmailAccount; }
    public String getTactGmailAccountPwd() { return tactGmailAccountPwd; }

    //set - Thread
    public void setThreadWebsiteUrl(String threadWebsiteUrl) { this.threadWebsiteUrl = threadWebsiteUrl; }
    public void setThreadAccount(String threadAccount) { this.threadAccount=threadAccount; }
    public void setThreadAccountPwd(String threadAccountPwd) { this.threadAccountPwd=threadAccountPwd; }
    public void setThreadSFAccount(String threadSFAccount) { this.threadSFAccount=threadSFAccount; }
    public void setThreadSFAccountPwd(String threadSFAccountPwd) { this.threadSFAccountPwd=threadSFAccountPwd; }
    public void setThreadGmailAccount(String threadGmailAccount) { this.threadGmailAccount=threadGmailAccount; }
    public void setThreadGmailAccountPwd(String threadGmailAccountPwd) { this.threadGmailAccountPwd=threadGmailAccountPwd; }

    //set - Cisco Spark
    public void setCiscoSparkWebsiteUrl(String ciscoSparkWebsiteUrl) { this.ciscoSparkWebsiteUrl=ciscoSparkWebsiteUrl; }
    public void setCiscoSparkAccount(String ciscoSparkAccount) { this.ciscoSparkAccount=ciscoSparkAccount; }
    public void setCiscoSparkAccountPwd(String ciscoSparkAccountPwd) { this.ciscoSparkAccountPwd=ciscoSparkAccountPwd; }
    public void setCiscoSparkSFAccount(String ciscoSparkSFAccount) { this.ciscoSparkSFAccount=ciscoSparkSFAccount; }
    public void setCiscoSparkSFAccountPwd(String ciscoSparkSFAccountPwd) { this.ciscoSparkSFAccountPwd=ciscoSparkSFAccountPwd; }
    public void setCiscoSparkGmailAccount(String ciscoSparkGmailAccount) { this.ciscoSparkGmailAccount=ciscoSparkGmailAccount; }
    public void setCiscoSparkGmailAccountPwd(String ciscoSparkGmailAccountPwd) { this.ciscoSparkGmailAccountPwd=ciscoSparkGmailAccountPwd; }

    //set - Alexa
    public String getAlexaWebsiteUrl() { return alexaWebsiteUrl; }
    public String getAlexaAccount() { return alexaAccount; }
    public String getAlexaAccountPwd() { return alexaAccountPwd; }
    public String getAlexaSFAccount() { return alexaSFAccount; }
    public String getAlexaSFAccountPwd() { return alexaSFAccountPwd; }
    public String getAlexaGmailAccount() { return alexaGmailAccount; }
    public String getAlexaGmailAccountPwd() { return alexaGmailAccountPwd; }


    //get - Tact
    public void setTactAccount(String tactAccount) { this.tactAccount=tactAccount; }
    public void setTactAccountPwd(String tactAccountPwd) { this.tactAccountPwd=tactAccountPwd; }
    public void setTactSFAccount(String tactSFAccount) { this.tactSFAccount=tactSFAccount; }
    public void setTactSFAccountPwd(String tactSFAccountPwd) { this.tactSFAccountPwd=tactSFAccountPwd; }
    public void setTactGmailAccount(String tactGmailAccount) { this.tactGmailAccount=tactGmailAccount; }
    public void setTactGmailAccountPwd(String tactGmailAccountPwd) { this.tactGmailAccountPwd=tactGmailAccountPwd; }

    //get - Thread
    public String getThreadWebsiteUrl() { return threadWebsiteUrl; }
    public String getThreadAccount() { return threadAccount; }
    public String getThreadAccountPwd() { return threadAccountPwd; }
    public String getThreadSFAccount() { return threadSFAccount; }
    public String getThreadSFAccountPwd() { return threadSFAccountPwd; }
    public String getThreadGmailAccount() { return threadGmailAccount; }
    public String getThreadGmailAccountPwd() { return threadGmailAccountPwd; }

    //get - Cisco Spark
    public String getCiscoSparkWebsiteUrl() { return ciscoSparkWebsiteUrl; }
    public String getCiscoSparkAccount() { return ciscoSparkAccount; }
    public String getCiscoSparkAccountPwd() { return ciscoSparkAccountPwd; }
    public String getCiscoSparkSFAccount() { return ciscoSparkSFAccount; }
    public String getCiscoSparkSFAccountPwd() { return ciscoSparkSFAccountPwd; }
    public String getCiscoSparkGmailAccount() { return ciscoSparkGmailAccount; }
    public String getCiscoSparkGmailAccountPwd() { return ciscoSparkGmailAccountPwd; }

    //get - Alexa
    public void setAlexaWebsiteUrl(String alexaWebsiteUrl) { this.alexaWebsiteUrl=alexaWebsiteUrl; }
    public void setAlexaAccount(String alexaAccount) { this.alexaAccount=alexaAccount; }
    public void setAlexaAccountPwd(String alexaAccountPwd) { this.alexaAccountPwd=alexaAccountPwd; }
    public void setAlexaSFAccount(String alexaSFAccount) { this.alexaSFAccount=alexaSFAccount; }
    public void setAlexaSFAccountPwd(String alexaSFAccountPwd) { this.alexaSFAccountPwd=alexaSFAccountPwd; }
    public void setAlexaGmailAccount(String alexaGmailAccount) { this.alexaGmailAccount=alexaGmailAccount; }
    public void setAlexaGmailAccountPwd(String alexaGmailAccountPwd) { this.alexaGmailAccountPwd=alexaGmailAccountPwd; }

    @Override
    public String toString() {
        return "WebUserInfor{" +
                //Tact
                "tactAccount='" + getTactAccount() + '\'' +
                ", tactAccountPwd='" + getTactAccountPwd() + '\'' +
                ", tactSFAccount='" + getTactSFAccount() + '\'' +
                ", tactSFAccountPwd='" + getTactSFAccountPwd() + '\'' +
                ", tactGmailAccount='" + getTactGmailAccount() + '\'' +
                ", tactGmailAccountPwd='" + getTactGmailAccountPwd() + '\'' +
                //Thread
                ", threadWebsiteUrl='" + getThreadWebsiteUrl() + '\'' +
                ", threadAccount='" + getThreadAccount() + '\'' +
                ", threadAccountPwd='" + getThreadAccountPwd() + '\'' +
                ", threadSFAccount='" + getThreadSFAccount() + '\'' +
                ", threadSFAccountPwd='" + getThreadSFAccountPwd() + '\'' +
                ", threadGmailAccount='" + getThreadGmailAccount() + '\'' +
                ", threadGmailAccountPwd='" + getThreadGmailAccountPwd() + '\'' +
                //Cisco Spark
                ", ciscoSparkWebsiteUrl='" + getCiscoSparkWebsiteUrl() + '\'' +
                ", ciscoSparkAccount='" + getCiscoSparkAccount() + '\'' +
                ", ciscoSparkAccountPwd='" + getCiscoSparkAccountPwd() + '\'' +
                ", ciscoSparkSFAccount='" + getCiscoSparkSFAccount() + '\'' +
                ", ciscoSparkSFAccountPwd='" + getCiscoSparkSFAccountPwd() + '\'' +
                ", ciscoSparkGmailAccount='" + getCiscoSparkGmailAccount() + '\'' +
                ", ciscoSparkGmailAccountPwd='" + getCiscoSparkGmailAccountPwd() + '\'' +
                //Alexa
                ", alexaWebsiteUrl='" + getAlexaWebsiteUrl() + '\'' +
                ", alexaAccount='" + getAlexaAccount() + '\'' +
                ", alexaAccountPwd='" + getAlexaAccountPwd() + '\'' +
                ", alexaSFAccount='" + getAlexaSFAccount() + '\'' +
                ", alexaSFAccountPwd='" + getAlexaSFAccountPwd() + '\'' +
                ", alexaGmailAccount='" + getAlexaGmailAccount() + '\'' +
                ", alexaGmailAccountPwd='" + getAlexaGmailAccountPwd() +

                '}';
    }
}
