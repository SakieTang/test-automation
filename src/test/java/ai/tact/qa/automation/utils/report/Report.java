package ai.tact.qa.automation.utils.report;

import ai.tact.qa.automation.utils.dataobjects.Status;

import java.util.List;

public class Report {

    private String appInfo;
    private List<Feature> features;

    public String getAppInfo() {return appInfo;}
    public List<Feature> getFeatures() {return features;}

    public void setAppInfo(String appInfo) { this.appInfo = appInfo;}
    public void setFeatures(List<Feature> features) {this.features = features;}

    public class Feature {
        private String featureName;
        private List<Case> cases;

        public String getFeatureName() {
            return featureName;
        }
        public List<Case> getCases() {
            return cases;
        }

        public void setFeatureName(String featureName) {
            this.featureName = featureName;
        }
        public void setCases(List<Case> cases) {
            this.cases = cases;
        }

        public class Case {
            private String caseName;
            private Result result;

            public String getCaseName() {
                return caseName;
            }
            public Result getResult() {
                return result;
            }

            public void setCaseName(String caseName) {
                this.caseName = caseName;
            }
            public void setResult(Result result) {
                this.result = result;
            }

        }

        public class Result {
            private int time;
            private Status status;

            public int getTime() {
                return time;
            }
            public Status getStatus() {
                return status;
            }

            public void setTime(int time) {
                this.time = time;
            }
            public void setStatus(Status status) {
                this.status = status;
            }
        }
    }
}



