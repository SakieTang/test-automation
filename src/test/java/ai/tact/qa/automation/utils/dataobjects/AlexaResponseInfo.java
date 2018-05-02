package ai.tact.qa.automation.utils.dataobjects;

public class AlexaResponseInfo {

    private String input = null;
    private String outputSpeechSSML = null;
    private String cardContent = null;
    private String repromptSSML = null;
    private boolean shouldEndSession = false;

    public AlexaResponseInfo() { }

    //set
    public void setInput(String input) { this.input = input; }
    public void setOutputSpeechSSML(String outputSpeechSSML) { this.outputSpeechSSML = outputSpeechSSML; }
    public void setCardContent(String cardContent) { this.cardContent = cardContent; }
    public void setRepromptSSML(String repromptSSML) { this.repromptSSML = repromptSSML; }
    public void setShouldEndSession(boolean shouldEndSession) { this.shouldEndSession = shouldEndSession; }

    //get
    public String getInput() { return input; }
    public String getOutputSpeechSSML() { return outputSpeechSSML; }
    public String getCardContent() { return cardContent; }
    public String getRepromptSSML() { return repromptSSML; }
    public boolean getShouldEndSession() { return shouldEndSession; }

    @Override
    public String toString() {
        return "AlexaResponse{" +
                "input='" + getInput() + '\'' +
                ", outputSpeechSSML='" + getOutputSpeechSSML() + '\'' +
                ", cardContent='" + getCardContent() + '\'' +
                ", repromptSSML='" + getRepromptSSML() + '\'' +
                ", shouldEndSession='" + getShouldEndSession() +
                '}';
    }
}