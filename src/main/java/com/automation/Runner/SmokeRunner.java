package com.automation.Runner;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import static com.automation.utils.PropertyUtils.loadProperties;

@CucumberOptions(tags = "@smoke", plugin = {"pretty", "html:target/cucumber-report",
        "json:target/cucumber.json"}, glue = "com.automation.steps",
        features = "./src/main/features/schedule-event.feature")
public class SmokeRunner extends AbstractTestNGCucumberTests {

    @BeforeSuite
    public void beforeScenario() {
        loadProperties();
    }

    @AfterSuite
    public void afterScenario() {
        // Add listeners for reporting
    }
}
