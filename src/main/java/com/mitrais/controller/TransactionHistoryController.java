package com.mitrais.controller;

import com.mitrais.model.Account;
import com.mitrais.model.TransactionHistory;
import com.mitrais.repository.TransactionHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/transaction_history")
public class TransactionHistoryController {
    private TransactionHistoryRepository transactionHistoryRepository;

    @Autowired
    public TransactionHistoryController(TransactionHistoryRepository transactionHistoryRepository) {
        this.transactionHistoryRepository = transactionHistoryRepository;
    }

    @GetMapping
    public String getTransactionList(Model model, HttpSession httpSession) {
        Account account = (Account) httpSession.getAttribute("account");
        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findAllByAccount_AccountNumber(account.getAccountNumber());
        model.addAttribute("transactionHistoryList", transactionHistoryList);
        return "TransactionHistory";
    }

    @PostMapping
    public String getTransactionListByDate(Model model, @ModelAttribute("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
                                           @ModelAttribute("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate, HttpSession httpSession) {
        Account account = (Account) httpSession.getAttribute("account");
        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findAllTransactionByDate(account.getAccountNumber(), fromDate, toDate);
        model.addAttribute("transactionHistoryList", transactionHistoryList);
        return "TransactionHistory";
    }

}
