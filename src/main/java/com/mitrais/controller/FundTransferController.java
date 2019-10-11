package com.mitrais.controller;

import com.mitrais.model.Account;
import com.mitrais.model.TransferConfirmation;
import com.mitrais.model.TransferSummary;
import com.mitrais.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/transfer")
public class FundTransferController {
    static final String NUMERIC_STRING = "0123456789";
    private AccountService accountService;

    @Autowired
    public FundTransferController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getForm(Model model) {
        return "FundTransfer";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String processTransfer(Model model, @ModelAttribute("destinationAccount") String destinationAccount, @ModelAttribute("amount") Integer amount, HttpSession httpSession, RedirectAttributes redirectAttributes)
    {
        Account account = (Account) httpSession.getAttribute("account");
        TransferConfirmation transferConfirmation = new TransferConfirmation(account, destinationAccount, amount, createRandomString());
        redirectAttributes.addFlashAttribute("transferConfirmation", transferConfirmation);
        return "redirect:/transfer/confirm";
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public String confirmTransfer(Model model, @ModelAttribute("transferConfirmation") TransferConfirmation transferConfirmation, HttpSession httpSession) {
        httpSession.setAttribute("transferConfirmation", transferConfirmation);
        model.addAttribute("transferConfirmation", transferConfirmation);
        return "FundTransferConfirmation";
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public String confirmAndTransfer(Model model, HttpSession httpSession, RedirectAttributes redirectAttributes) {
        TransferConfirmation transferConfirmation = (TransferConfirmation) httpSession.getAttribute("transferConfirmation");
        try {
            TransferSummary transferSummary = accountService.transfer(transferConfirmation.getAccount().getAccountNumber(), transferConfirmation.getDestinationAccount(), transferConfirmation.getTransferAmount().toString(), transferConfirmation.getReferenceNumber());
            redirectAttributes.addFlashAttribute("transferSummary", transferSummary);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("transferConfirmation", transferConfirmation);
            return "FundTransferConfirmation";
        }
        return "redirect:/transfer/summary";
    }

    @RequestMapping(value = "/summary", method = RequestMethod.GET)
    public String transferSummary(Model model, @ModelAttribute("transferSummary") TransferSummary transferSummary) {
        model.addAttribute("transferSummary", transferSummary);
        return "FundTransferSummary";
    }

    private static String createRandomString() {
        int count = 6;
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * NUMERIC_STRING.length());
            builder.append(NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }


}
