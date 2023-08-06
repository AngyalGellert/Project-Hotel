package org.webshop;

import java.time.LocalDateTime;
import java.util.*;

public class WebShop {
    private final Store store;
    private final CustomerService customerService;
    private final Set<Cart> carts;
    private final List<Order> orders;

    public WebShop(Store store, CustomerService customerService) {
        if (store == null) {
            throw new IllegalArgumentException("Store cannot be empty!");
        }
        if (customerService == null) {
            throw new IllegalArgumentException("Customer service cannot be empty!");
        }
        this.store = store;
        this.customerService = customerService;
        this.carts = new HashSet<>();
        this.orders = new ArrayList<>();
    }

    public void addProduct(Product product) {
        store.addProduct(product);
    }

    public void addCustomer(Customer customer) {
        customerService.addCustomer(customer);
    }

    public void beginShopping(String email) {
        for (Cart cart : carts) {
            if (cart.getCustomer().getEmail().equals(email)) {
                throw new IllegalArgumentException("Customer with e-mail address: " + email + " has already began shopping!");
            }
        }
        Customer customer = customerService.getCustomerByEmail(email);
        Cart cart = new Cart(customer);
        carts.add(cart);
    }

    public void addCartItem(String email, String barcode, int quantity) {
        Customer customer = customerService.getCustomerByEmail(email);
        Product product = store.getProductByBarcode(barcode);
        Cart tmp = null;

        for (Cart cart : carts) {
            if (cart.getCustomer().equals(customer)) {
                tmp = cart;
                cart.addCartItem(product, quantity);
            }
        }
        if (tmp == null) {
            throw new IllegalArgumentException("Customer with e-mail address " + email + " does not have an actual cart yet.");
        }
    }

    public void rejectCart(String email) {
        for (Cart cart : carts) {
            if (cart.getCustomer().getEmail().equals(email)) {
                carts.remove(cart);
            }
        }
    }

    public long order(String email, LocalDateTime dateTime) {

        long idTMP = 1;
        Cart cart = null;
        Customer customer = customerService.getCustomerByEmail(email);
        int customersOrders = customer.getFinishedOrders();

        for (Cart cart1 : carts) {
            if (cart1.getCustomer().equals(customer)) {
                cart = cart1;
            }
        }
        carts.remove(cart);
        if (cart == null) {
            throw new IllegalArgumentException("Customer with e-mail address "
                      + email + " does not have an actual cart yet.");
        }

        Order order = new Order(idTMP, dateTime,customer, cart.getProducts());
        orders.add(order);
        order.setId(orders.indexOf(order) + (long)1);

        customer.setFinishedOrders(customersOrders + 1);
        for (Product product : cart.getProducts().keySet()) {
            customer.getProductsBought().add(product);
        }

        if (customer.getFinishedOrders() >= 5) {
            customer.setCustomerToVip();
        }

        return order.getId();
    }

    public Set<Customer> getCustomersByProduct(String barcode) {
        Set<Customer> customerSet = new HashSet<>();
        Product product = store.getProductByBarcode(barcode);

        for (Order order : orders) {
           if (order.getCustomer().getProductsBought().contains(product)){
               customerSet.add(order.getCustomer());
           }
        }
        return customerSet;
    }

    public boolean hasCustomerBoughtProduct(String email, String barcode) {
        Product product = store.getProductByBarcode(barcode);
        Customer customer = customerService.getCustomerByEmail(email);
        return customer.getProductsBought().contains(product);
    }

    public Map<Long, Integer> getTotalAmounts() {
        Map<Long, Integer> ordersAmount = new HashMap<>();
        for (Order order : orders) {
            ordersAmount.putIfAbsent(order.getId(), order.getTotalAmount());
        }
        return ordersAmount;
    }

    public Customer getCustomerWithMaxTotalAmount() {

        if (orders.isEmpty()) {
            throw new IllegalArgumentException("No such customer.");
        }

        Customer customerMax = orders.get(0).getCustomer();

        for (Order order : orders) {
            if (totalSpentMoneyOfCustomer(order.getCustomer()) > totalSpentMoneyOfCustomer(customerMax)){
                customerMax = order.getCustomer();
            }
        }
        return customerMax;
    }

    private int totalSpentMoneyOfCustomer(Customer customer){
        int spentMoney = 0;
        for (Product product : customer.getProductsBought()) {
            spentMoney += product.getPrice();
        }
        return spentMoney;
    }

    public List<Order> listOrdersSortedByTotalAmounts() {
        List<Order> sortedByTotalAmounts = new ArrayList<>(orders);
        Collections.sort(sortedByTotalAmounts);
        return sortedByTotalAmounts;
    }

    public List<Order> listOrdersSortedByDate() {
        List<Order> sortedByDate = new ArrayList<>(orders);
        sortedByDate.sort(new OrderDateAndTimeComparator());
        return sortedByDate;
    }

    public Store getStore() {
        return store;
    }

    public CustomerService getCustomerService() {
        return customerService;
    }

    public Set<Cart> getCarts() {
        return new HashSet<>(carts);
    }

    public List<Order> getOrders() {
        return new ArrayList<>(orders);
    }
}
