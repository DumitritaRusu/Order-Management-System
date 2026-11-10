package org.example.presentation.Controller;

import org.example.businessLayer.ProductBLL;
import org.example.model.Product;
import org.example.presentation.ReflectionTableModel;

import javax.swing.*;
import java.awt.*;

/**
 * Panel pentru operatiile asupra produselor.
 */
public class ProductPanel extends JPanel {

    private final ProductBLL productBLL;

    private final JTable productTable;

    private final JTextField nameField;
    private final JTextField priceField;
    private final JTextField stockField;

    /**
     * Constructor pentru initializarea panel-ului.
     */
    public ProductPanel() {

        productBLL = new ProductBLL();

        setLayout(new BorderLayout());

        productTable = new JTable();

        refreshTable();

        JScrollPane scrollPane = new JScrollPane(productTable);

        add(scrollPane, BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Price:"));
        priceField = new JTextField();
        formPanel.add(priceField);

        formPanel.add(new JLabel("Stock:"));
        stockField = new JTextField();
        formPanel.add(stockField);

        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        formPanel.add(addButton);
        formPanel.add(updateButton);

        add(formPanel, BorderLayout.NORTH);
        add(deleteButton, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addProduct());
        updateButton.addActionListener(e -> updateProduct());
        deleteButton.addActionListener(e -> deleteProduct());

        productTable.getSelectionModel().addListSelectionListener(
                e -> fillFieldsFromSelectedRow()
        );
    }

    /**
     * Adauga un produs nou.
     */
    private void addProduct() {

        try {

            Product product = new Product(
                    nameField.getText(),
                    Double.parseDouble(priceField.getText()),
                    Integer.parseInt(stockField.getText())
            );

            productBLL.insertProduct(product);

            refreshTable();
            clearFields();

            JOptionPane.showMessageDialog(this,
                    "Produs adaugat cu succes");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this,
                    e.getMessage());
        }
    }

    /**
     * Actualizeaza produsul selectat din tabel.
     */
    private void updateProduct() {

        try {

            int selectedRow = productTable.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this,
                        "Selecteaza un produs din tabel");
                return;
            }

            int id = (int) productTable.getValueAt(selectedRow, 0);

            Product product = new Product(
                    nameField.getText(),
                    Double.parseDouble(priceField.getText()),
                    Integer.parseInt(stockField.getText())
            );

            product.setId(id);

            productBLL.updateProduct(product);

            refreshTable();
            clearFields();

            JOptionPane.showMessageDialog(this,
                    "Produs actualizat cu succes");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this,
                    e.getMessage());
        }
    }

    /**
     * Sterge produsul selectat din tabel.
     */
    private void deleteProduct() {

        try {

            int selectedRow = productTable.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this,
                        "Selecteaza un produs din tabel");
                return;
            }

            int id = (int) productTable.getValueAt(selectedRow, 0);

            productBLL.deleteProduct(id);

            refreshTable();
            clearFields();

            JOptionPane.showMessageDialog(this,
                    "Produs sters cu succes");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this,
                    e.getMessage());
        }
    }

    /**
     * Completeaza campurile formularului cu datele produsului selectat.
     */
    private void fillFieldsFromSelectedRow() {

        int selectedRow = productTable.getSelectedRow();

        if (selectedRow == -1) {
            return;
        }

        nameField.setText(String.valueOf(productTable.getValueAt(selectedRow, 1)));
        priceField.setText(String.valueOf(productTable.getValueAt(selectedRow, 2)));
        stockField.setText(String.valueOf(productTable.getValueAt(selectedRow, 3)));
    }

    /**
     * Goleste campurile formularului.
     */
    private void clearFields() {

        nameField.setText("");
        priceField.setText("");
        stockField.setText("");
    }

    /**
     * Reincarca datele in tabel.
     */
    public void refreshTable() {

        productTable.setModel(
                ReflectionTableModel.createTableModel(
                        productBLL.getSortedProducts()
                )
        );
    }
}