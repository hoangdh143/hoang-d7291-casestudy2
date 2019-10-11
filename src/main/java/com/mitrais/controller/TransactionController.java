package com.mitrais.controller;

import com.mitrais.model.Account;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/transaction")
public class TransactionController {
    @GetMapping
    public String getTransaction(Model model, HttpSession httpSession) {
        Account account = (Account) httpSession.getAttribute("account");
        model.addAttribute("account", account);
        return "Transaction";
    }
}
