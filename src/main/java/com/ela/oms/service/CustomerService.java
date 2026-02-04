package com.ela.oms.service;

import com.ela.oms.dto.customer.CustomerRequestDTO;
import com.ela.oms.dto.customer.CustomerResponseDTO;
import com.ela.oms.entity.customer.Customer;

import java.util.List;


public interface CustomerService {

    CustomerResponseDTO createCustomer(CustomerRequestDTO customerDTO);

    List<Customer> getAllCustomers();

    Customer getCustomerById(Long id);

    Customer updateCustomer(Long id, Customer customer);

    void deleteCustomer(Long id);
}
