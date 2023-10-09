package com.lucas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/customers")
public class Main {
    private final CustomerRepository customerRepository;

    public Main(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping
    public List<Customer> getCustomer(){
        return customerRepository.findAll();
    }

    @PostMapping
    public void addCustomer(@RequestBody NewCustomerRequest request){
        Customer customer=new Customer();
        customer.setName(request.name());
        customer.setAge(request.age());
        customer.setEmail(request.email());
        customerRepository.save(customer);
    }
    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Integer id){
        customerRepository.deleteById(id);
    }
    @PutMapping( "{customerId}")
    public void updateAnomaly(@PathVariable("customerId") Integer id, @RequestBody NewCustomerRequest newcustomer) {
        if(customerRepository.findById(id).isPresent()){
            Customer customer = customerRepository.findById(id).get();
            customer.setEmail(newcustomer.email());
            customer.setAge(newcustomer.age());
            customer.setName(newcustomer.name());
            customerRepository.save(customer);
        }
    }
}
