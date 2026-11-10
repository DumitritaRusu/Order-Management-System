package org.example.model;

/**
 * Clasa pentru comenzi.
 */
public class Order {

    private int id;
    private int clientId;
    private int productId;
    private int quantity;

    public Order() {
    }

    /**
     * Constructor pentru comanda.
     *
     * @param clientId id-ul clientului
     * @param productId id-ul produsului
     * @param quantity cantitatea comandata
     */
    public Order(int clientId, int productId, int quantity) {
        this.clientId = clientId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}