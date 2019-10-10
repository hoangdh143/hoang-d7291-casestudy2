package com.mitrais.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/fund_transfer")
public class FundTransferController {
    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String formOfTransfer(Model model) {
        return "FundTransfer";
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public String confirmTransfer(Model model) {
        return "FundTransferConfirmation";
    }

    @RequestMapping(value = "/summary", method = RequestMethod.GET)
    public String transferSummary(Model model) {
        return "FundTransferSummary";
    }


}
