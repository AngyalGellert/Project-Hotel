package org.webshop;

import java.util.Comparator;

public class ProductNameComparator implements Comparator<Product> {
    @Override
    public int compare(Product first, Product second) {
        return first.getName().compareTo(second.getName());
    }
}
