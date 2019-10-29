package com.mitrais.serviceTest;

import cucumber.api.java.Before;
import cucumber.api.java8.En;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class WithdrawalSteps implements En {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    public WithdrawalSteps() {
        Given("^authenticated user has a balance of (\\d+)$", (Integer arg0) -> {
            mockMvc.perform(get("/login")).andExpect(status().isOk());
            mockMvc.perform(post("/login")
                    .param("accountNumber", "811069")
                    .param("pin", "288818"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(MockMvcResultMatchers.redirectedUrl("/transaction"));
        });
        When("^user performs a withdrawal of (\\d+)$", (Integer amount) -> {
            mockMvc.perform(post("/withdraw")
                    .param("amount", amount.toString()))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/withdraw/summary"));
        });
        Then("^the balance is (\\d+)$", (Integer arg0) -> {
        });
        And("^user gets the list of transactions from newest to oldest$", () -> {
        });
    }
}
