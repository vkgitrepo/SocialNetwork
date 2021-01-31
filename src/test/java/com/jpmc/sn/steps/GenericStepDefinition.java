package com.jpmc.sn.steps;

import io.cucumber.java.en.Given;
import io.restassured.RestAssured;

public class GenericStepDefinition extends BaseStepDefinition {

    @Given("URL is configured and request initialized")
    public void url_is_configured_and_request_initialized() {
        RestAssured.baseURI = BASE_URL;
        request = RestAssured.given();
    }

}
