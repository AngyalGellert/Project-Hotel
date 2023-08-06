package org.webshop;

import java.util.Comparator;

public class OrderDateAndTimeComparator implements Comparator<Order> {
    @Override
    public int compare(Order first, Order second) {
        return second.getTimeOfOrder().compareTo(first.getTimeOfOrder());
    }
}
