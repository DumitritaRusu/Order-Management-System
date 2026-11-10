package org.example.presentation.Controller;

import org.example.businessLayer.ClientBLL;
import org.example.businessLayer.OrderBLL;
import org.example.businessLayer.ProductBLL;
import org.example.model.Client;
import org.example.model.Product;

import javax.swing.*;
import java.awt.*;

/**
 * Panel pentru crearea si afisarea comenzilor.
 * Permite selectarea unui client, a unui produs si introducerea cantitatii.
 */
public class OrderPanel extends JPanel {

    private final OrderBLL orderBLL;
    private final ClientBLL clientBLL;
    private final ProductBLL productBLL;
    private final JLabel totalLabel;
    private final JComboBox<Client> clientComboBox;
    private final JComboBox<Product> productComboBox;

    private final JTextField quantityField;

    private final JTable orderTable;

    /**
     * Constructor pentru initializarea panel-ului.
     */
    public OrderPanel() {

        orderBLL = new OrderBLL();
        clientBLL = new ClientBLL();
        productBLL = new ProductBLL();

        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        formPanel.add(new JLabel("Client:"));
        clientComboBox = new JComboBox<>();
        formPanel.add(clientComboBox);

        formPanel.add(new JLabel("Product:"));
        productComboBox = new JComboBox<>();
        formPanel.add(productComboBox);

        formPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        formPanel.add(quantityField);

        JButton createOrderButton = new JButton("Create Order");

        formPanel.add(new JLabel(""));
        formPanel.add(createOrderButton);

        add(formPanel, BorderLayout.NORTH);

        orderTable = new JTable();
        totalLabel = new JLabel();
        add(new JScrollPane(orderTable), BorderLayout.CENTER);
        add(totalLabel, BorderLayout.SOUTH);
        createOrderButton.addActionListener(e -> createOrder());

        loadData();
        refreshTable();
    }

    /**
     * Incarca clientii si produsele in combo box-uri.
     * Metoda se poate apela cand utilizatorul intra in tab-ul Orders.
     */
    public void loadData() {

        clientComboBox.removeAllItems();
        productComboBox.removeAllItems();

        for (Client client : clientBLL.findAllClients()) {
            clientComboBox.addItem(client);
        }

        for (Product product : productBLL.findAvailableProducts()) {
            productComboBox.addItem(product);
        }
    }

    /**
     * Creeaza o comanda noua pentru clientul si produsul selectat.
     */
    private void createOrder() {

        try {

            Client client = (Client) clientComboBox.getSelectedItem();
            Product product = (Product) productComboBox.getSelectedItem();

            if (client == null) {
                JOptionPane.showMessageDialog(this,
                        "Nu exista client selectat");
                return;
            }

            if (product == null) {
                JOptionPane.showMessageDialog(this,
                        "Nu exista produs selectat");
                return;
            }

            int quantity = Integer.parseInt(quantityField.getText());

            orderBLL.createOrder(
                    client.getId(),
                    product.getId(),
                    quantity
            );

            JOptionPane.showMessageDialog(this,
                    "Comanda creata cu succes");

            quantityField.setText("");

            loadData();
            refreshTable();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this,
                    e.getMessage());
        }
    }

    /**
     * Reincarca comenzile in tabel.
     */
    public void refreshTable() {

        javax.swing.table.DefaultTableModel model =
                new javax.swing.table.DefaultTableModel();

        model.addColumn("Order Id");
        model.addColumn("Client");
        model.addColumn("Product");
        model.addColumn("Quantity");

        for (org.example.model.Order order : orderBLL.findAllOrders()) {

            org.example.model.Client client =
                    clientBLL.findClientById(order.getClientId());

            org.example.model.Product product =
                    productBLL.findProductById(order.getProductId());

            model.addRow(new Object[]{
                    order.getId(),
                    client.getName(),
                    product.getName(),
                    order.getQuantity()
            });
        }
        totalLabel.setText(
                "Total Orders Value: "
                        + orderBLL.getTotalOrdersValue()
        );
        orderTable.setModel(model);
    }
}