package com.automation.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

public class AssertionUtils {

    static Logger logger = LogManager.getLogger(AssertionUtils.class);

    public static <T> void assertThat(String msg, T actual, Matcher<? super T> matcher) {
        assertThat("", actual, matcher);
    }

    public static <T> void assertThat(String msg, String actual, Matcher<String> matcher) {
        if (!verifyThat(msg, actual, matcher)) {
            throw new AssertionError();
        }
        logger.log(Level.ALL, "Assertion is getting executed... !!");
    }

    public static <T> boolean verifyThat(String msgAssertion, String actual, Matcher<String> matcher) {
        boolean result = matcher.matches(actual);
        Description description = new StringDescription();
        description.appendText(msgAssertion).appendText("\nExpected: ").appendDescriptionOf(matcher)
                .appendText("\n     Actual: ");

        matcher.describeMismatch(actual, description);
        String msg = description.toString();
        if (msg.endsWith("Actual: ")) {
            msg = String.format(msg + "%s", actual);
        }
        msg = msg.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        return result;
    }
}
