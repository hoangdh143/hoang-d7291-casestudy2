package com.mitrais.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/login")
public class LoginController {
    @PostMapping
    public String login(HttpServletRequest req, HttpServletResponse resp) {
        String username = req.getParameter("accountNumber");
        String pin = req.getParameter("pin");

        if (username.isEmpty() || pin.isEmpty()) {
            return "redirect:/login_error";
        } else {
            return "redirect:/login_success";
        }
    }
}
