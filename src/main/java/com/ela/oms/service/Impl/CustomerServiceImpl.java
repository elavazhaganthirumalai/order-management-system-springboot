package com.ela.oms.service.Impl;

import com.ela.oms.dto.customer.AddressDTO;
import com.ela.oms.dto.customer.CustomerRequestDTO;
import com.ela.oms.dto.customer.CustomerResponseDTO;
import com.ela.oms.entity.customer.Address;
import com.ela.oms.entity.customer.Customer;
import com.ela.oms.exception.DuplicateResourceException;
import com.ela.oms.repository.CustomerRepository;
import com.ela.oms.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public CustomerResponseDTO createCustomer(CustomerRequestDTO customerDTO) {
        Customer customer = mapToEntity(customerDTO);
        if (customerRepository.findByEmail(customer.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Email already exists");
        }
        Address address = customer.getAddress();

        if (address != null) {
            address.setCustomer(customer);
        }

        return mapToResponseDTO(customerRepository.save(customer));
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    @Override
    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        Customer existing = getCustomerById(id);

        existing.setFirstName(updatedCustomer.getFirstName());
        existing.setLastName(updatedCustomer.getLastName());
        existing.setPhone(updatedCustomer.getPhone());

        return customerRepository.save(existing);
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    private Customer mapToEntity(CustomerRequestDTO dto) {

        Customer customer = new Customer();
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());

        Address address = new Address();
        address.setAddressLine1(dto.getAddress().getAddressLine1());
        address.setAddressLine2(dto.getAddress().getAddressLine2());
        address.setCity(dto.getAddress().getCity());
        address.setState(dto.getAddress().getState());
        address.setPostalCode(dto.getAddress().getPostalCode());
        address.setCountry(dto.getAddress().getCountry());

        customer.setAddress(address);
        address.setCustomer(customer);

        return customer;
    }

    private CustomerResponseDTO mapToResponseDTO(Customer customer) {

        AddressDTO addr = new AddressDTO();
        addr.setAddressLine1(customer.getAddress().getAddressLine1());
        addr.setCity(customer.getAddress().getCity());
        addr.setState(customer.getAddress().getState());
        addr.setPostalCode(customer.getAddress().getPostalCode());
        addr.setCountry(customer.getAddress().getCountry());

        CustomerResponseDTO dto = new CustomerResponseDTO();
        dto.setId(customer.getId());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        dto.setCreatedAt(customer.getCreatedAt());
        dto.setAddress(addr);

        return dto;
    }

}
