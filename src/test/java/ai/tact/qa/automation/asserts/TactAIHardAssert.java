package ai.tact.qa.automation.asserts;

import org.testng.Reporter;
import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;

public final class TactAIHardAssert extends Assertion {
    public TactAIHardAssert() {
    }

    public void onAssertSuccess(IAssert<?> assertCommand) {
        this.showAssertInfo(assertCommand, "passed in ");
    }

    public void onAssertFailure(IAssert<?> assertCommand, AssertionError ex) {
        this.showAssertInfo(assertCommand, "failed in ");
    }

    private void showAssertInfo(IAssert<?> assertCommand, String msg) {
        String methodName = Reporter.getCurrentTestResult().getMethod().getMethodName();
        StringBuilder sb = new StringBuilder();
        sb.append("Assert ");
        if (assertCommand.getMessage() != null && !assertCommand.getMessage().trim().isEmpty()) {
            sb.append("[").append(assertCommand.getMessage()).append("] ");
        }

        sb.append(msg).append(methodName).append("()");
        Reporter.log(sb.toString(), true);
    }
}
