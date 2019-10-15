package com.mitrais.controller;

import com.mitrais.model.Account;
import com.mitrais.model.TransferConfirmation;
import com.mitrais.model.TransferSummary;
import com.mitrais.repository.AccountRepository;
import com.mitrais.service.AccountService;
import com.mitrais.validator.AccountNumberValidator;
import com.mitrais.validator.TransferAmountValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
@RequestMapping("/transfer")
public class FundTransferController {
    static final String NUMERIC_STRING = "0123456789";
    private AccountService accountService;
    private AccountRepository accountRepository;

    @Autowired
    public FundTransferController(AccountService accountService, AccountRepository accountRepository) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getForm(Model model) {
        return "fund-transfer";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String processTransfer(Model model, @ModelAttribute("destinationAccount") String destinationAccount, @ModelAttribute("amount") Integer amount,
                                  BindingResult bindingResult, HttpSession httpSession, RedirectAttributes redirectAttributes)
    {
        Account account = (Account) httpSession.getAttribute("account");

        new AccountNumberValidator().withAccount(account).withAccountRepo(accountRepository).validate(destinationAccount, bindingResult);
        new TransferAmountValidator().withAccount(account).validate(amount, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("error", Objects.requireNonNull(bindingResult.getGlobalError()).getDefaultMessage());
            return "fund-transfer";
        }

        TransferConfirmation transferConfirmation = new TransferConfirmation(account, destinationAccount, amount, createRandomString());
        redirectAttributes.addFlashAttribute("transferConfirmation", transferConfirmation);
        return "redirect:/transfer/confirm";
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public String confirmTransfer(Model model, @ModelAttribute("transferConfirmation") TransferConfirmation transferConfirmation, HttpSession httpSession) {
        httpSession.setAttribute("transferConfirmation", transferConfirmation);
        model.addAttribute("transferConfirmation", transferConfirmation);
        return "fund-transfer-confirmation";
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public String confirmAndTransfer(Model model, HttpSession httpSession, RedirectAttributes redirectAttributes) {
        TransferConfirmation transferConfirmation = (TransferConfirmation) httpSession.getAttribute("transferConfirmation");
        try {
            TransferSummary transferSummary = accountService.transfer(transferConfirmation.getAccount().getAccountNumber(), transferConfirmation.getDestinationAccount(), transferConfirmation.getTransferAmount(), transferConfirmation.getReferenceNumber());
            redirectAttributes.addFlashAttribute("transferSummary", transferSummary);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("transferConfirmation", transferConfirmation);
            return "fund-transfer-confirmation";
        }
        return "redirect:/transfer/summary";
    }

    @RequestMapping(value = "/summary", method = RequestMethod.GET)
    public String transferSummary(Model model, @ModelAttribute("transferSummary") TransferSummary transferSummary) {
        model.addAttribute("transferSummary", transferSummary);
        return "fund-transfer-summary";
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
