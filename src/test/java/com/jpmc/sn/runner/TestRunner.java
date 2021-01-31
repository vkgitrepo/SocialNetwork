package com.jpmc.sn.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/",
        glue = "com.jpmc.sn.steps",
        plugin={"json:target/cucumber.json","pretty","html:target/cucumber-reports"},
        strict = true,
        monochrome = true

        //Enable below line to execute Smoke Tests
        //,tags = {"@Smoke"}

        //Enable below line to execute Feature level tags
        //,tags = {"@User"}

        //Enable below line to execute scenarios with Entity Type and action level tags
        //,tags = {"@Add","@User"}

)

public class TestRunner {

}
