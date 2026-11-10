package org.example.presentation;

import org.example.presentation.Controller.ClientPanel;
import org.example.presentation.Controller.OrderPanel;
import org.example.presentation.Controller.ProductPanel;

import javax.swing.*;

/**
 * Fereastra principala a aplicatiei.
 * Contine tab-uri pentru clienti, produse si comenzi.
 */
public class View extends JFrame {

    /**
     * Constructor pentru initializarea interfetei grafice.
     */
    public View() {

        setTitle("Orders Management");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        ClientPanel clientPanel = new ClientPanel();
        ProductPanel productPanel = new ProductPanel();
        OrderPanel orderPanel = new OrderPanel();

        tabbedPane.addTab("Clients", clientPanel);
        tabbedPane.addTab("Products", productPanel);
        tabbedPane.addTab("Orders", orderPanel);

        tabbedPane.addChangeListener(e -> {
            if (tabbedPane.getSelectedComponent() == orderPanel) {
                orderPanel.loadData();
                orderPanel.refreshTable();
            }

            if (tabbedPane.getSelectedComponent() == productPanel) {
                productPanel.refreshTable();
            }
            if (tabbedPane.getSelectedComponent() == clientPanel) {
                clientPanel.refreshTable();
            }

        });

        add(tabbedPane);
    }
}