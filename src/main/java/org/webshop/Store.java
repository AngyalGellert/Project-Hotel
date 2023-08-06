package org.webshop;

import java.util.*;

public class Store {
    private final Set<Product> products;

    public Store() {
        this.products = new HashSet<>();
    }

    public Product getProductByBarcode(String barcode) {
        Product tmp = null;
        for (Product product : products) {
            if (product.getBarcode().equals(barcode)) {
                tmp = product;
            }
        }
        if (tmp == null) {
            throw new IllegalArgumentException("No product with barcode: " + barcode);
        }
        return tmp;
    }

    public void addProduct(Product lego) {
        if (lego == null) {
            throw new IllegalArgumentException("Product cannot be empty!");
        }
        if (products.contains(lego)) {
            throw new IllegalArgumentException("Product with barcode: " + lego.getBarcode() + " is already registered.");
        } else {
            products.add(lego);
        }
    }

    public Product getCheapestProductByCategory(ProductCategory toy) {
        Product tmp;
        List<Product> productsByType = new ArrayList<>();

        for (Product product : products) {
            if (product.getCategory().equals(toy)) {
                tmp = product;
                productsByType.add(tmp);
            }
        }

        Product cheapestProduct = productsByType.get(0);
        for (Product product : productsByType) {
            if (product.getPrice() < cheapestProduct.getPrice()) {
                cheapestProduct = product;
            }
        }
        return cheapestProduct;
    }

    public List<Product> listProductsSortedByPrice() {
        List<Product> sortedByPrice = new ArrayList<>();
        sortedByPrice.addAll(products);
        Collections.sort(sortedByPrice);

        return sortedByPrice;
    }

    public List<Product> listProductsSortedByName() {
        List<Product> sortedByPrice = new ArrayList<>();
        sortedByPrice.addAll(products);
        sortedByPrice.sort(new ProductNameComparator());
        return sortedByPrice;

    }

    public Map<ProductCategory, List<Product>> getProductsByCategory() {

        Map<ProductCategory, List<Product>> productCategoryListMap = new HashMap<>();

//                        Egy féle megoldás, hosszabb

//        productCategoryListMap.put(ProductCategory.TOY, new ArrayList<>());
//        productCategoryListMap.put(ProductCategory.BOOK, new ArrayList<>());
//        productCategoryListMap.put(ProductCategory.CLOTHING, new ArrayList<>());
//
//        for (Product product : products) {
//            if (product.getCategory().equals(ProductCategory.CLOTHING)) {
//                productCategoryListMap.get(ProductCategory.CLOTHING).add(product);
//            } else if (product.getCategory().equals(ProductCategory.BOOK)) {
//                productCategoryListMap.get(ProductCategory.BOOK).add(product);
//            } else if (product.getCategory().equals(ProductCategory.TOY)) {
//                productCategoryListMap.get(ProductCategory.TOY).add(product);
//            }
//        }

        for (Product product : products) {
            productCategoryListMap.putIfAbsent(product.getCategory(), new ArrayList<>());
            for (Map.Entry<ProductCategory, List<Product>> listEntry : productCategoryListMap.entrySet()) {
                if (listEntry.getKey().equals(product.getCategory())) {
                    listEntry.getValue().add(product);
                }
            }
        }
        return productCategoryListMap;
    }
    public Set<Product> getProducts() {
        return new HashSet<>(products);
    }
}
