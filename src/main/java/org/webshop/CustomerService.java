package org.webshop;

import java.util.*;

public class CustomerService {
    private final Set<Customer> customers;
    public CustomerService() {
        this.customers = new HashSet<>();
    }

    public void addCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be empty.");
        }
        if (customers.contains(customer)) {
            throw new IllegalArgumentException("Customer with e-mail address: " + customer.getEmail() + " is already registered.");
        } else {
            customers.add(customer);
        }
    }

    public Customer getCustomerByEmail(String email) {
        Customer tmp = null;
        for (Customer customer : customers) {
            if (customer.getEmail().equals(email)) {
                tmp = customer;
            }
        }
        if (tmp == null) {
            throw new IllegalArgumentException("No customer with e-mail address: " + email);
        }
        return tmp;
    }

    public List<Customer> listCustomersByCategoryGiven (CustomerCategory category) {

        Customer tmp;
        List<Customer> sortedByCategory = new ArrayList<>();

        for (Customer customer : customers) {
            if (customer.getCategory().equals(category)) {
                tmp = customer;
                sortedByCategory.add(tmp);
            }
        }
        return sortedByCategory;
    }

    public List<Customer> listCustomersSortedByEmail() {

        List<Customer> sortedByEmail = new ArrayList<>();
        sortedByEmail.addAll(customers);
        Collections.sort(sortedByEmail);
        return sortedByEmail;
    }

    public List<String> listCustomerNamesSorted() {

        List<String> sortedByName = new ArrayList<>();
        for (Customer customer : customers) {
            sortedByName.add(customer.getName());
        }
        Collections.sort(sortedByName);
        return sortedByName;
    }

    public Set<Customer> getCustomers() {
        return new HashSet<>(customers);
    }
}
