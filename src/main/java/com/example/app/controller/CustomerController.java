package com.example.app.controller;

import com.example.app.model.Customer;
import com.example.app.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // Get all
    @GetMapping("/list")
    public String listCustomers(Model model) {

        // get customers from the service
        List<Customer> customers = customerService.getCustomers();

        // add the customers to the model
        model.addAttribute("customers", customers);

        return "list-customers";
    }

    // Add new
    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model){
        Customer theCustomer = new Customer();

        model.addAttribute("customer", theCustomer);

        return "customer-form";
    }

    // Save new
    @PostMapping("/saveCustomer")
    public String saveCustomer(@ModelAttribute("customer") Customer theCustomer){

        // save the customer using service
        customerService.saveCustomer(theCustomer);
        return "redirect:/customer/list";
    }

    // Update
    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("customerId") int id, Model model){
        Customer customer = customerService.getCustomer(id);

        model.addAttribute("customer", customer);

        return "customer-form";
    }

    // Delete
    @GetMapping("/delete")
    public String deleteCustomer(@RequestParam("customerId") int id){
        customerService.deleteCustomer(id);

        return "redirect:/customer/list";
    }

    // Search
    @GetMapping("/search")
    public String searchCustomer(@RequestParam("searchName") String searchName, Model model){
        List<Customer> list = customerService.searchCustomers(searchName);

        model.addAttribute("customers", list);

        return "list-customers";
    }
}
