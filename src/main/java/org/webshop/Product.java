package org.webshop;

import java.util.Objects;

public class Product implements Comparable<Product> {

    private final String barcode;
    private final String name;
    private final int price;
    private final ProductCategory category;

    public Product(String barcode, String name, int price, ProductCategory category) {
        this.barcode = barcodeValidator(barcode);
        this.name = nameValidator(name);
        this.price = priceValidator(price);
        this.category = productCategoryValidator(category);
    }

    public static String barcodeValidator (String barcode){
        if ((barcode == null) || barcode.isBlank())
        {throw new IllegalArgumentException("Barcode cannot be empty!");}
        return barcode;
    }
    public static String nameValidator(String name){
        if (name == null || name.isBlank())
        {throw new IllegalArgumentException("Name cannot be empty!");}
        return name;
    }

    public static int priceValidator (int price){
        if (price < 0)
        {throw new IllegalArgumentException("Price must be 0 or positive!");}
        return price;
    }

    public static ProductCategory productCategoryValidator (ProductCategory category){
        if (category == null)
        {throw new IllegalArgumentException("Category cannot be empty!");}
        return category;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public ProductCategory getCategory() {
        return category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(barcode, product.barcode);
    }
    @Override
    public int hashCode() {
        return Objects.hash(barcode);
    }

    @Override
    public int compareTo(Product o) {
        return price-o.price;
    }

}
