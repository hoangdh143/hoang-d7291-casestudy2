package com.mitrais.controller;

import com.mitrais.model.Account;
import com.mitrais.model.TransactionSummary;
import com.mitrais.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
@RequestMapping("/withdraw")
public class WithdrawController {
    private AccountService accountService;

    @Autowired
    public WithdrawController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public String withdraw() {
        return "withdraw";
    }

    @PostMapping
    public String summary(Model model, @RequestParam("amount") Integer amount, HttpSession httpSession, RedirectAttributes redirectAttributes) {
        Account account = (Account) httpSession.getAttribute("account");
        try {
                TransactionSummary transactionSummary = accountService.deduct(account.getAccountNumber(), amount);
                redirectAttributes.addFlashAttribute("transactionSummary", transactionSummary);
                return "redirect:/withdraw/summary";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "withdraw";
        }
    }

    @GetMapping("/summary")
    public String withdrawSummary(Model model, @ModelAttribute("transactionSummary") TransactionSummary transactionSummary) {
        return "summary";
    }
}
