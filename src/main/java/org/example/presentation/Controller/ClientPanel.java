package org.example.presentation.Controller;

import org.example.businessLayer.ClientBLL;
import org.example.model.Client;
import org.example.presentation.ReflectionTableModel;

import javax.swing.*;
import java.awt.*;

/**
 * Panel pentru operatiile asupra clientilor.
 */
public class ClientPanel extends JPanel {

    private final ClientBLL clientBLL;

    private final JTable clientTable;

    private final JTextField nameField;
    private final JTextField addressField;
    private final JTextField emailField;

    /**
     * Constructor pentru initializarea panel-ului.
     */
    public ClientPanel() {

        clientBLL = new ClientBLL();

        setLayout(new BorderLayout());

        clientTable = new JTable();

        refreshTable();

        JScrollPane scrollPane = new JScrollPane(clientTable);

        add(scrollPane, BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Address:"));
        addressField = new JTextField();
        formPanel.add(addressField);

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);

        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        formPanel.add(addButton);
        formPanel.add(updateButton);

        add(formPanel, BorderLayout.NORTH);
        add(deleteButton, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addClient());
        updateButton.addActionListener(e -> updateClient());
        deleteButton.addActionListener(e -> deleteClient());

        clientTable.getSelectionModel().addListSelectionListener(e -> fillFieldsFromSelectedRow());
    }

    /**
     * Adauga un client nou.
     */
    private void addClient() {

        try {

            Client client = new Client(
                    nameField.getText(),
                    addressField.getText(),
                    emailField.getText()
            );

            clientBLL.insertClient(client);

            refreshTable();
            clearFields();

            JOptionPane.showMessageDialog(this,
                    "Client adaugat cu succes");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this,
                    e.getMessage());
        }
    }

    /**
     * Actualizeaza clientul selectat din tabel.
     */
    private void updateClient() {

        try {

            int selectedRow = clientTable.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this,
                        "Selecteaza un client din tabel");
                return;
            }

            int id = (int) clientTable.getValueAt(selectedRow, 0);

            Client client = new Client(
                    nameField.getText(),
                    addressField.getText(),
                    emailField.getText()
            );

            client.setId(id);

            clientBLL.updateClient(client);

            refreshTable();
            clearFields();

            JOptionPane.showMessageDialog(this,
                    "Client actualizat cu succes");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this,
                    e.getMessage());
        }
    }

    /**
     * Sterge clientul selectat din tabel.
     */
    private void deleteClient() {

        try {

            int selectedRow = clientTable.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this,
                        "Selecteaza un client din tabel");
                return;
            }

            int id = (int) clientTable.getValueAt(selectedRow, 0);

            clientBLL.deleteClient(id);

            refreshTable();
            clearFields();

            JOptionPane.showMessageDialog(this,
                    "Client sters cu succes");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this,
                    e.getMessage());
        }
    }

    /**
     * Completeaza campurile formularului cu datele clientului selectat.
     */
    private void fillFieldsFromSelectedRow() {

        int selectedRow = clientTable.getSelectedRow();

        if (selectedRow == -1) {
            return;
        }

        nameField.setText(String.valueOf(clientTable.getValueAt(selectedRow, 1)));
        addressField.setText(String.valueOf(clientTable.getValueAt(selectedRow, 2)));
        emailField.setText(String.valueOf(clientTable.getValueAt(selectedRow, 3)));
    }

    /**
     * Goleste campurile formularului.
     */
    private void clearFields() {

        nameField.setText("");
        addressField.setText("");
        emailField.setText("");
    }

    /**
     * Reincarca datele in tabel.
     */
    public void refreshTable() {

        clientTable.setModel(
                ReflectionTableModel.createTableModel(
                        clientBLL.getSortedClients()
                )
        );
    }
}