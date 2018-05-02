package ai.tact.qa.automation.asserts;

import org.testng.Reporter;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

public final class TactAIAsserts {
    private static TactAIHardAssert hardAssert = new TactAIHardAssert();
    private static TactAISoftAssert softAssert = new TactAISoftAssert();

    private TactAIAsserts() {
    }

    public static void assertTrue(boolean condition) {
        hardAssert.assertTrue(condition);
    }

    public static void assertFalse(boolean condition) {
        hardAssert.assertFalse(condition);
    }

    public static void assertEquals(Object actual, Object expected) {
        hardAssert.assertEquals(actual, expected);
    }

    public static void assertNotEquals(Object actual, Object expected) {
        hardAssert.assertNotEquals(actual, expected);
    }

    public static void assertNotEquals(Object actual, Object expected, String msg) {
        hardAssert.assertNotEquals(actual, expected, msg);
    }

    public static void assertNull(Object actual) {
        hardAssert.assertNull(actual);
    }

    public static void assertNull(Object actual, String msg) {
        hardAssert.assertNull(actual, msg);
    }

    public static void assertNotNull(Object actual) {
        hardAssert.assertNotNull(actual);
    }

    public static void assertNotNull(Object actual, String msg) {
        hardAssert.assertNotNull(actual, msg);
    }

    public static void verifyTrue(boolean condition, String msg) {
        try {
            System.out.println(condition + " =msg= " + msg);
            boolean cond = condition;
            String message = msg;
            getSoftAssertInContext().assertTrue(cond, message);
        } catch (NullPointerException e) {
            msg = "error msg";
            getSoftAssertInContext().assertTrue(condition, msg);
//            throw new IllegalStateException(e.getMessage());
        }
    }

    public static void verifyTrue(boolean condition) {
        getSoftAssertInContext().assertTrue(condition);
    }

    public static void verifyFalse(boolean condition, String msg) {
        getSoftAssertInContext().assertFalse(condition, msg);
    }

    public static void verifyFalse(boolean condition) {
        getSoftAssertInContext().assertFalse(condition);
    }

    public static void verifyEquals(Object actual, Object expected, String msg) {
        getSoftAssertInContext().assertEquals(actual, expected, msg);
    }

    public static void verifyEquals(Object actual, Object expected) {
        getSoftAssertInContext().assertEquals(actual, expected);
    }

    public static void verifyNotEquals(Object actual, Object expected) {
        getSoftAssertInContext().assertNotEquals(actual, expected);
    }

    public static void verifyNotEquals(Object actual, Object expected, String msg) {
        getSoftAssertInContext().assertNotEquals(actual, expected, msg);
    }

    public static void verifyNull(Object actual) {
        getSoftAssertInContext().assertNull(actual);
    }

    public static void verifyNull(Object actual, String msg) {
        getSoftAssertInContext().assertNull(actual, msg);
    }

    public static void verifyNotNull(Object actual) {
        getSoftAssertInContext().assertNotNull(actual);
    }

    public static void verifyNotNull(Object actual, String msg) {
        getSoftAssertInContext().assertNotNull(actual, msg);
    }

    public static void assertTrue(boolean condition, String message) {
        hardAssert.assertTrue(condition, message);
    }

    public static void assertFalse(boolean condition, String message) {
        hardAssert.assertFalse(condition, message);
    }

    public static void assertEquals(boolean actual, boolean expected) {
        hardAssert.assertEquals(actual, expected);
    }

    public static void assertEquals(Object[] actual, Object[] expected) {
        hardAssert.assertEquals(actual, expected);
    }

    public static void assertEquals(Object actual, Object expected, String message) {
        hardAssert.assertEquals(actual, expected, message);
    }

    public static void fail(String message) {
        hardAssert.fail(message);
    }

    public static void fail(Throwable e, String message) {
        hardAssert.fail(message, e);
    }

    private static TactAISoftAssert getSoftAssertInContext() {
        TactAISoftAssert sa;
        if (null == Reporter.getCurrentTestResult()) {
            sa = softAssert;
        } else {
            sa = (TactAISoftAssert)Reporter.getCurrentTestResult().getAttribute(TactAISoftAssert.SOFT_ASSERT_ATTRIBUTE_NAME);
        }

        return sa;
    }
}
