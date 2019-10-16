package com.mitrais.controller;

import com.mitrais.model.Account;
import com.mitrais.model.TransactionHistory;
import com.mitrais.repository.TransactionHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/transaction_history")
public class TransactionHistoryController {
    static final int PAGE_SIZE = 10;
    private TransactionHistoryRepository transactionHistoryRepository;

    @Autowired
    public TransactionHistoryController(TransactionHistoryRepository transactionHistoryRepository) {
        this.transactionHistoryRepository = transactionHistoryRepository;
    }

    @GetMapping
    public String getTransactionList(Model model, HttpSession httpSession, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size,
                                     @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<LocalDate> date) {
        final int currentPage = page.orElse(1);
        final int pageSize = size.orElse(10);

        Account account = (Account) httpSession.getAttribute("account");
        if (account != null) {
            model.addAttribute("username", account.getName());
            Pageable pageable = PageRequest.of(currentPage - 1, pageSize);

            List<TransactionHistory> transactionHistoryList;
            int totalPages;
            if (!date.isPresent()) {
                transactionHistoryList = transactionHistoryRepository.findAllByAccountNumber(account.getAccountNumber(), pageable);
                totalPages =  transactionHistoryRepository.countByAccountNumber(account.getAccountNumber())/pageSize + 1;
            } else {
                transactionHistoryList = transactionHistoryRepository.findAllTransactionByDate(account.getAccountNumber(), date.get(), pageable);
                totalPages =  transactionHistoryRepository.countByAccountNumberAndDate(account.getAccountNumber(), date.get())/pageSize + 1;
                model.addAttribute("date", date.get());
            }
            model.addAttribute("transactionHistoryList", transactionHistoryList);
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("pageNumbers", IntStream.rangeClosed(1, totalPages).toArray());
        }
        return "transaction-history";
    }

    @PostMapping
    public String getTransactionListByDate(Model model, @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date, HttpSession httpSession) {
        Account account = (Account) httpSession.getAttribute("account");
        if (account != null) {
            model.addAttribute("username", account.getName());

            Pageable page = PageRequest.of(0, PAGE_SIZE);
            List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findAllTransactionByDate(account.getAccountNumber(), date, page);
            model.addAttribute("transactionHistoryList", transactionHistoryList);
        }
        return "transaction-history";
    }

}
