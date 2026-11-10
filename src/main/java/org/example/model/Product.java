package org.example.model;

/**
 * Clasa care reprezinta un produs din sistem.
 */
public class Product {

    private int id;
    private String name;
    private double price;
    private int stock;

    /**
     * Constructor fara parametri.
     * Pentru reflection.
     */
    public Product() {
    }

    /**
     * Constructor pentru initializarea unui produs.
     * @param name numele produsului
     * @param price pretul produsului
     * @param stock stocul disponibil
     */
    public Product(String name, double price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    /**
     * Returneaza id-ul produsului.
     * @return id-ul
     */
    public int getId() {
        return id;
    }

    /**
     * Seteaza id-ul produsului.
     * @param id noul id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returneaza numele produsului.
     * @return numele
     */
    public String getName() {
        return name;
    }

    /**
     * Seteaza numele produsului.
     * @param name noul nume
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returneaza pretul produsului.
     * @return pretul
     */
    public double getPrice() {
        return price;
    }

    /**
     * Seteaza pretul produsului.
     * @param price noul pret
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Returneaza stocul produsului.
     * @return stocul
     */
    public int getStock() {
        return stock;
    }

    /**
     * Seteaza stocul produsului.
     * @param stock noul stoc
     */
    public void setStock(int stock) {
        this.stock = stock;
    }
    @Override
    public String toString() {
        return name;
    }
}