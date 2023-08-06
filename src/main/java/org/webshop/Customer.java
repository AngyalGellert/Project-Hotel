package org.webshop;

import java.util.*;

public class Customer implements Comparable <Customer> {

    private String name;
    private String email;
    private CustomerCategory category;
    private int finishedOrders;
    private List<Product> productsBought;

    public Customer(String name, String email) {
        nameAndEmailValidator(name,email);
        this.category = CustomerCategory.SINGLE;
        this.finishedOrders = 0;
        this.productsBought = new ArrayList<>();
    }

    public void nameAndEmailValidator(String name,String email){
        if (name == null || name.isBlank() || email == null || email.isBlank())
        {throw new IllegalArgumentException("Name or e-mail address cannot be empty!");}
        this.name = name;
        this.email = email;
    }

    public void setCustomerToVip() {
        category = CustomerCategory.VIP;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public CustomerCategory getCategory() {
        return category;
    }

    public int getFinishedOrders() {
        return finishedOrders;
    }

    public void setFinishedOrders(int finishedOrders) {
        this.finishedOrders = finishedOrders;
    }

    public List<Product> getProductsBought() {
        return productsBought;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(email, customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public int compareTo(Customer other) {
        return this.email.compareTo(other.email);
    }

}
