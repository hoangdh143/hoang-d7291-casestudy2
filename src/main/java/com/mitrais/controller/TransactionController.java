package com.mitrais.controller;

import com.mitrais.model.Account;
import com.mitrais.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/transaction")
public class TransactionController {
    @GetMapping
    public String getTransaction(Model model, HttpSession httpSession, Authentication authentication) {
        Account account = ((CustomUserDetails) authentication.getPrincipal()).getAccount();
        httpSession.setAttribute("account", account);
        model.addAttribute("account", account);
        if (account != null) model.addAttribute("username", account.getName());
        return "transaction";
    }
}
