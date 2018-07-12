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

        String assertString = "Soft Assert ";
        if (assertCommand.getMessage() != null && !assertCommand.getMessage().trim().isEmpty()) {
            assertString = String.format("[%s]", assertCommand.getMessage());
        }

        if (failedTest) {
            assertString += "failed in ";
        } else {
            assertString += "passed in ";
        }

        assertString += String.format("%s()\n", methodName);
        if (failedTest) {
            assertString += ExceptionUtils.getStackTrace(ex);
        }

        Reporter.log(assertString, true);
    }

    public void executeAssert(IAssert<?> iAssert) {
        try {
            iAssert.doAssert();
            this.onAssertSuccess(iAssert);
        } catch (AssertionError ex) {
            this.onAssertFailure(iAssert, ex);
            this.allErrors.put(ex, iAssert);
        }

    }

    public void assertAll() {
        if (!this.allErrors.isEmpty()) {
            String assertString;
            if (1 == this.allErrors.size()) {
                assertString = String.format("A soft assertion failure occurred [\n");
            } else {
                assertString = String.format("Multiple (%s) soft assertion failures occurred [\n", this.allErrors.size());
            }

            int counter = 0;
            Iterator iterator = this.allErrors.entrySet().iterator();

            while(iterator.hasNext()) {
                Map.Entry<AssertionError, IAssert<?>> eachEntry = (Map.Entry)iterator.next();
                AssertionError eachError = (AssertionError)eachEntry.getKey();
                ++counter;
                if (Reporter.getCurrentTestResult() != null) {
                    assertString += String.format("%s\t]", StringUtils.substringBetween(ExceptionUtils.getStackTrace(eachError), "\n", "\tat sun.reflect").replace("\t", "\t\t"));
                } else {
                    assertString += String.format("%s\t]", StringUtils.substringAfter(ExceptionUtils.getStackTrace(eachError), "\n").replace("\t", "\t\t"));
                }
            }

            if (Reporter.getCurrentTestResult() != null) {
                Reporter.getCurrentTestResult().setThrowable(new AssertionError(assertString));
                Reporter.getCurrentTestResult().setStatus(2);
            } else {
                throw new AssertionError(assertString);
            }
        }
    }
}
