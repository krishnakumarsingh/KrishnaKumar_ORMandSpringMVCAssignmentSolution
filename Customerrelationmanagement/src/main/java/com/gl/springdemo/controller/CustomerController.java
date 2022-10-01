package com.gl.springdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.gl.springdemo.*;
import com.gl.springdemo.entity.Customer;
import com.gl.springdemo.service.CustomerService;

@Controller
@RequestMapping("/")
public class CustomerController {

	// need to inject our customer service
	@Autowired
	private CustomerService customerService;

	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public String listCustomers(Model theModel) {

		// get customers from the service
		List<Customer> theCustomers = customerService.getCustomers();

		// add the customers to the model
		theModel.addAttribute("customers", theCustomers);

		return "list-customers";
	}

	@RequestMapping(value = { "/showFormForAdd" }, method = RequestMethod.GET)
	public String showFormForAdd(Model theModel) {

		// create model attribute to bind form data
		Customer theCustomer = new Customer();

		theModel.addAttribute("customer", theCustomer);

		return "customer-form";
	}

	@RequestMapping(value = { "/saveCustomer" }, method = RequestMethod.POST)
	public String saveCustomer(@ModelAttribute("customer") Customer theCustomer) {

		// save the customer using our service
		customerService.saveCustomer(theCustomer);

		return "redirect:/list";
	}

	@RequestMapping(value = { "/showFormForUpdate" }, method = RequestMethod.GET)
	public String showFormForUpdate(@RequestParam("customerId") int theId, Model theModel) {

		// get the customer from our service
		Customer theCustomer = customerService.getCustomer(theId);

		// set customer as a model attribute to pre-populate the form
		theModel.addAttribute("customer", theCustomer);

		// send over to our form
		return "customer-form";
	}

	@RequestMapping(value = { "/delete" }, method = RequestMethod.GET)
	public String deleteCustomer(@RequestParam("customerId") int theId) {

		// delete the customer
		customerService.deleteCustomer(theId);

		return "redirect:/list";
	}
}
