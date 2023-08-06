package org.webshop;

public enum CustomerCategory {
    SINGLE(0), VIP(10);
    private final int discount;

    CustomerCategory(int discount) {
        this.discount = discount;
    }

    public int getDiscount() {
        return discount;
    }
}
