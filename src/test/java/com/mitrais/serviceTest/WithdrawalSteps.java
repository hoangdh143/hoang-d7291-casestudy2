package com.mitrais.serviceTest;

import com.mitrais.model.Account;
import com.mitrais.repository.AccountRepository;
import com.mitrais.service.AccountService;
import cucumber.api.java8.En;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static io.restassured.RestAssured.given;

public class WithdrawalSteps implements En {
    private MockMvc mockMvc;

    public WithdrawalSteps() {
        Given("^authenticated user has a balance of (\\d+)$", (Integer arg0) -> {
            final RequestSpecification request = given();
            Response response = request.accept(ContentType.JSON).post("http://localhost:8080/login");
        });
        When("^user performs a withdrawal of (\\d+)$", (Integer arg0) -> {
        });
        Then("^the balance is (\\d+)$", (Integer arg0) -> {
        });
        And("^user gets the list of transactions from newest to oldest$", () -> {
        });
    }
}
