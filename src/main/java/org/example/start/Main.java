package org.example.start;

import org.example.presentation.View;

import javax.swing.*;

/**
 * Clasa principala a aplicatiei.
 */
public class Main {

    /**
     * Metoda principala.
     * @param args argumentele programului
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() ->
                new View().setVisible(true)
        );
    }
}