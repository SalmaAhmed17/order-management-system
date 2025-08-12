package com.example.OMS.service;

import com.example.OMS.exceptions.ResourceNotFoundException;
import com.example.OMS.models.customer;
import com.example.OMS.models.invoice;
import com.example.OMS.repository.customerRepo;
import com.example.OMS.repository.invoiceRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class customerService {

    private final customerRepo cus_Repo;

    public customerService(customerRepo cus_Repo) {
        this.cus_Repo = cus_Repo;
    }

    public List<customer> getAllCustomers() {
        return cus_Repo.findAll();
    }

    public customer getCustomerById(Long id) {
        return cus_Repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
    }

    public void deleteCustomer(Long id) {
        if (!cus_Repo.existsById(id)) {
            throw new ResourceNotFoundException("Customer not found with id: " + id);
        }
        cus_Repo.deleteById(id);
    }
}
