package org.example.businessLayer;

import org.example.dataAccessLayer.BillDAO;
import org.example.dataAccessLayer.OrderDAO;
import org.example.dataAccessLayer.ProductDAO;
import org.example.model.Bill;
import org.example.model.Client;
import org.example.model.Order;
import org.example.model.Product;

import java.util.List;

/**
 * Clasa pentru logica comenzilor.
 */
public class OrderBLL {

    private final OrderDAO orderDAO;
    private final ProductDAO productDAO;
    private final ClientBLL clientBLL;
    private final BillDAO billDAO;

    /**
     * Constructor pentru OrderBLL.
     */
    public OrderBLL() {
        orderDAO = new OrderDAO();
        productDAO = new ProductDAO();
        clientBLL = new ClientBLL();
        billDAO = new BillDAO();
    }

    /**
     * Creeaza o comanda pentru un singur produs.
     *
     * @param clientId id-ul clientului
     * @param productId id-ul produsului
     * @param quantity cantitatea comandata
     */
    public void createOrder(int clientId, int productId, int quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException("Cantitatea trebuie sa fie pozitiva");
        }

        Client client = clientBLL.findClientById(clientId);

        if (client == null) {
            throw new IllegalArgumentException("Clientul nu exista");
        }

        Product product = productDAO.findById(productId);

        if (product == null) {
            throw new IllegalArgumentException("Produsul nu exista");
        }

        if (product.getStock() < quantity) {
            throw new IllegalArgumentException(
                    "Stoc insuficient pentru " + product.getName()
            );
        }

        double totalPrice = product.getPrice() * quantity;

        Order order = new Order(clientId, productId, quantity);

        orderDAO.insert(order);

        List<Order> orders = orderDAO.findAll();

        int orderId = orders.get(orders.size() - 1).getId(); //gaseste id ul ultimei comenzi inserate

        product.setStock(product.getStock() - quantity); // update la cantitate

        productDAO.update(product);

        Bill bill = new Bill(       //creeaza factura
                0,
                orderId,
                client.getName(),
                totalPrice
        );

        billDAO.insert(bill);
    }

    /**
     * Returneaza toate comenzile.
     *
     * @return lista comenzilor
     */
    public List<Order> findAllOrders() {
        return orderDAO.findAll();
    }
    /**
     * Calculeaza valoarea totala a tuturor comenzilor.
     * Foloseste stream-uri si lambda expressions.
     *
     * @return valoarea totala a comenzilor
     */
    public double getTotalOrdersValue() {

        return orderDAO.findAll()
                .stream()
                .map(order -> {

                    Product product =
                            productDAO.findById(
                                    order.getProductId()
                            );

                    return product.getPrice()
                            * order.getQuantity();
                })
                .mapToDouble(Double::doubleValue)
                .sum();
    }
}