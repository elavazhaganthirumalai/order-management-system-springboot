package com.ela.oms.service.Impl;

import com.ela.oms.dto.customer.AddressDTO;
import com.ela.oms.dto.customer.CustomerRequestDTO;
import com.ela.oms.dto.customer.CustomerResponseDTO;
import com.ela.oms.entity.customer.Address;
import com.ela.oms.entity.customer.Customer;
import com.ela.oms.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

    @InjectMocks
    CustomerServiceImpl customerService;

    @Mock
    CustomerRepository customerRepository;

    @Test
    void createCustomerShouldCreateCustomerSuccessfully() {

        CustomerRequestDTO customerDTO = new CustomerRequestDTO();
        customerDTO.setFirstName("Ela");
        customerDTO.setLastName("T");
        customerDTO.setEmail("ela@test.com");
        customerDTO.setPhone("9876543210");

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setAddressLine1("Street 1");
        addressDTO.setCity("Chennai");
        addressDTO.setState("TN");
        addressDTO.setPostalCode("600001");
        addressDTO.setCountry("India");

        customerDTO.setAddress(addressDTO);

        Customer customer = customerService.mapToEntity(customerDTO);
        customer.setId(1L);

        Mockito.when(customerRepository.findByEmail(customerDTO.getEmail()))
                .thenReturn(Optional.empty());

        Mockito.when(customerRepository.save(any(Customer.class)))
                .thenReturn(customer);

        CustomerResponseDTO response =
                customerService.createCustomer(customerDTO);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Ela", response.getFirstName());
        assertEquals("ela@test.com", response.getEmail());

        Mockito.verify(customerRepository)
                .findByEmail("ela@test.com");
        Mockito.verify(customerRepository)
                .save(any(Customer.class));
    }

    @Test
    void getAllCustomersShouldReturnCustomers() {

        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstName("Ela");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstName("John");

        List<Customer> customers =
                List.of(customer1, customer2);

        Mockito.when(customerRepository.findAll())
                .thenReturn(customers);

        List<Customer> result =
                customerService.getAllCustomers();

        assertNotNull(result);
        assertEquals(2, result.size());

        Mockito.verify(customerRepository)
                .findAll();
    }

    @Test
    void getCustomerByIdShouldReturnCustomer() {

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Ela");

        Mockito.when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        Customer result =
                customerService.getCustomerById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Ela", result.getFirstName());

        Mockito.verify(customerRepository)
                .findById(1L);
    }

    @Test
    void getCustomerByIdShouldThrowException() {

        Mockito.when(customerRepository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(RuntimeException.class,
                        () -> customerService.getCustomerById(1L));

        assertEquals("Customer not found",
                exception.getMessage());

        Mockito.verify(customerRepository)
                .findById(1L);
    }

    @Test
    void updateCustomerShouldUpdateSuccessfully() {

        Customer existingCustomer = new Customer();
        existingCustomer.setId(1L);
        existingCustomer.setFirstName("Old");

        Customer updatedCustomer = new Customer();
        updatedCustomer.setFirstName("New");
        updatedCustomer.setLastName("User");
        updatedCustomer.setPhone("9999999999");

        Mockito.when(customerRepository.findById(1L))
                .thenReturn(Optional.of(existingCustomer));

        Mockito.when(customerRepository.save(any(Customer.class)))
                .thenReturn(existingCustomer);

        Customer result =
                customerService.updateCustomer(1L, updatedCustomer);

        assertNotNull(result);
        assertEquals("New", result.getFirstName());
        assertEquals("User", result.getLastName());

        Mockito.verify(customerRepository)
                .findById(1L);

        Mockito.verify(customerRepository)
                .save(any(Customer.class));
    }

    @Test
    void deleteCustomerShouldDeleteSuccessfully() {

        customerService.deleteCustomer(1L);

        Mockito.verify(customerRepository)
                .deleteById(1L);
    }

    @Test
    void mapToEntityShouldReturnCustomer() {

        CustomerRequestDTO dto = new CustomerRequestDTO();
        dto.setFirstName("Ela");
        dto.setLastName("T");
        dto.setEmail("ela@test.com");
        dto.setPhone("9876543210");

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setAddressLine1("Street 1");
        addressDTO.setCity("Chennai");
        addressDTO.setState("TN");
        addressDTO.setPostalCode("600001");
        addressDTO.setCountry("India");

        dto.setAddress(addressDTO);

        Customer customer =
                customerService.mapToEntity(dto);

        assertNotNull(customer);
        assertEquals("Ela", customer.getFirstName());
        assertEquals("T", customer.getLastName());
        assertEquals("ela@test.com", customer.getEmail());
        assertEquals("Chennai",
                customer.getAddress().getCity());
    }
}