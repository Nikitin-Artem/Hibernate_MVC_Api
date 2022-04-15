package com.example.app.controller;

import com.example.app.dao.CustomerDAO;
import com.example.app.model.Customer;
import com.example.app.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/list")
    public String listCustomers(Model model) {

        // get customers from the service
        List<Customer> customers = customerService.getCustomer();

        // add the customers to the model
        model.addAttribute("customers", customers);

        return "list-customers";
    }

}
