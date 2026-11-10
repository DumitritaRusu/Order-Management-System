package org.example.presentation;

import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Clasa folosita pentru generarea unui tabel folosind reflection.
 */
public class ReflectionTableModel {

    /**
     * Creeaza un tabel pe baza unei liste de obiecte.
     * Coloanele sunt generate automat folosind reflection.
     *
     * @param objects lista de obiecte
     * @param <T> tipul obiectelor
     * @return modelul tabelului
     */
    public static <T> DefaultTableModel createTableModel(List<T> objects) {

        DefaultTableModel model = new DefaultTableModel();

        if (objects == null || objects.isEmpty()) {
            return model;
        }

        Class<?> clazz = objects.get(0).getClass();

        Field[] fields = clazz.getDeclaredFields();

        // Adauga coloanele tabelului
        for (Field field : fields) {
            model.addColumn(field.getName());
        }

        // Adauga liniile tabelului
        for (T object : objects) {

            Object[] row = new Object[fields.length];

            for (int i = 0; i < fields.length; i++) {

                try {

                    fields[i].setAccessible(true);
                    row[i] = fields[i].get(object);

                } catch (IllegalAccessException e) {

                    row[i] = null;
                }
            }

            model.addRow(row);
        }

        return model;
    }
}