package ai.tact.qa.automation.asserts;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;
import org.testng.collections.Maps;

import java.util.Iterator;
import java.util.Map;

public final class TactAISoftAssert extends Assertion {
    public static final String SOFT_ASSERT_ATTRIBUTE_NAME = TactAISoftAssert.class.getCanonicalName();
    private final Map<AssertionError, IAssert<?>> allErrors = Maps.newLinkedHashMap();

    public TactAISoftAssert() {
    }

    protected void doAssert(IAssert<?> assertCommand) {
        this.onBeforeAssert(assertCommand);

        try {
            this.executeAssert(assertCommand);
        } finally {
            this.onAfterAssert(assertCommand);
        }

    }

    public void onAssertSuccess(IAssert<?> assertCommand) {
        this.showAssertInfo(assertCommand, (AssertionError)null, false);
    }

    public void onAssertFailure(IAssert<?> assertCommand, AssertionError ex) {
        this.showAssertInfo(assertCommand, ex, true);
    }

    private void showAssertInfo(IAssert<?> assertCommand, AssertionError ex, boolean failedTest) {
        ITestResult testResult = Reporter.getCurrentTestResult();
        String methodName = "main";
        if (testResult != null) {
            methodName = testResult.getMethod().getMethodName();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Soft Assert ");
        if (assertCommand.getMessage() != null && !assertCommand.getMessage().trim().isEmpty()) {
            sb.append("[").append(assertCommand.getMessage()).append("] ");
        }

        if (failedTest) {
            sb.append("failed in ");
        } else {
            sb.append("passed in ");
        }

        sb.append(methodName).append("()\n");
        if (failedTest) {
            sb.append(ExceptionUtils.getStackTrace(ex));
        }

        Reporter.log(sb.toString(), true);
    }

    public void executeAssert(IAssert<?> a) {
        try {
            a.doAssert();
            this.onAssertSuccess(a);
        } catch (AssertionError var3) {
            this.onAssertFailure(a, var3);
            this.allErrors.put(var3, a);
        }

    }

    public void assertAll() {
        if (!this.allErrors.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            if (1 == this.allErrors.size()) {
                sb.append("A soft assertion failure occurred [\n");
            } else {
                sb.append("Multiple (").append(this.allErrors.size()).append(") soft assertion failures occurred [\n");
            }

            int counter = 0;
            Iterator var4 = this.allErrors.entrySet().iterator();

            while(var4.hasNext()) {
                Map.Entry<AssertionError, IAssert<?>> eachEntry = (Map.Entry)var4.next();
                AssertionError eachError = (AssertionError)eachEntry.getKey();
                StringBuilder var10000 = sb.append("\t");
                ++counter;
                var10000.append(counter).append(". ").append(ExceptionUtils.getRootCauseMessage(eachError)).append("\n");
                if (Reporter.getCurrentTestResult() != null) {
                    sb.append(StringUtils.substringBetween(ExceptionUtils.getStackTrace(eachError), "\n", "\tat sun.reflect").replace("\t", "\t\t"));
                } else {
                    sb.append(StringUtils.substringAfter(ExceptionUtils.getStackTrace(eachError), "\n").replace("\t", "\t\t"));
                }
            }

            sb.append("\t]");
            if (Reporter.getCurrentTestResult() != null) {
                Reporter.getCurrentTestResult().setThrowable(new AssertionError(sb.toString()));
                Reporter.getCurrentTestResult().setStatus(2);
            } else {
                throw new AssertionError(sb.toString());
            }
        }
    }
}
