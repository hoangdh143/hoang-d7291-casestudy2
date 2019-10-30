package com.mitrais.serviceTest;

import com.mitrais.model.TransactionHistory;
import cucumber.api.java.Before;
import cucumber.api.java8.En;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class WithdrawalSteps implements En {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private List<TransactionHistory> transactionHistoryList = null;

    @Before
    public void setup() {
        final MockHttpServletRequestBuilder defaultRequestBuilder = get("/");
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .defaultRequest(defaultRequestBuilder)
                .alwaysDo(result -> setSessionBackOnRequestBuilder(defaultRequestBuilder, result.getRequest()))
                .build();
    }

    private void setSessionBackOnRequestBuilder(MockHttpServletRequestBuilder defaultRequestBuilder, MockHttpServletRequest request) {
        defaultRequestBuilder.session((MockHttpSession) request.getSession());
    }

    public WithdrawalSteps() {
        Given("^authenticated user has a balance of (\\d+)$", (Integer arg0) -> {
            File file = new File("Accounts.csv");
            MockMultipartFile firstFile = new MockMultipartFile("file", "Accounts.csv", "text/plain", FileUtils.readFileToByteArray(file));

            mockMvc.perform(multipart("/file_upload").file(firstFile))
                    .andExpect(status().isOk())
                    .andExpect(model().attribute("success", true));

            mockMvc.perform(get("/login"))
                    .andExpect(status().isOk());

            mockMvc.perform(post("/login")
                    .param("accountNumber", "811069")
                    .param("pin", "288818"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/transaction"));
        });

        When("^user performs a withdrawal of (\\d+)$", (Integer amount) -> {
            mockMvc.perform(post("/withdraw")
                    .param("amount", amount.toString()))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/withdraw/summary"));
        });

        Then("^the balance is (\\d+)$", (Integer arg0) -> {
            MvcResult result =  mockMvc.perform(get("/transaction_history")
                    .param("page", "1")
                    .param("size", "10")
            )
                    .andExpect(status().isOk())
                    .andReturn();

            if (result.getModelAndView() != null) {
                Map<String, Object> model = result.getModelAndView().getModel();
                transactionHistoryList = (List<TransactionHistory>) model.get("transactionHistoryList");
            }

            assertNotNull(transactionHistoryList);
            assertFalse(transactionHistoryList.isEmpty());

            TransactionHistory transactionHistory = transactionHistoryList.get(0);
            assertEquals(750, (int) transactionHistory.getBalance());

        });

        And("^user gets the list of transactions from newest to oldest$", () -> {
            List<TransactionHistory> sortedList = new ArrayList<>(transactionHistoryList);
            sortedList.sort(Comparator.comparing(TransactionHistory::getCreatedAt).reversed());

            assertEquals(transactionHistoryList, sortedList);
        });
    }
}
