package com.mitrais.controller;

import com.mitrais.exception.InvalidAccountException;
import com.mitrais.model.Account;
import com.mitrais.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping
public class AuthenticationController {
    private AccountService accountService;

    @Autowired
    public AuthenticationController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/login")
    public String loadLoginPage(HttpServletRequest req, HttpServletResponse resp, HttpSession session){
        session.setAttribute("error", null);
        return "Login";
    }

    @PostMapping("/login")
    public String login(Model model, HttpServletRequest req, HttpServletResponse resp, HttpSession httpSession) {
        String accountNumber = req.getParameter("accountNumber");
        String pin = req.getParameter("pin");
        try {
            Account account = accountService.validate(accountNumber, pin);
            httpSession.setAttribute("account", account);
            return "redirect:/transaction";
        } catch (InvalidAccountException e) {
            model.addAttribute("error", e.getMessage());
            return "Login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest req, HttpServletResponse resp, HttpSession session){
        session.invalidate();
        return "redirect:/login";
    }
}
