package org.example.businessLayer;

import org.example.dataAccessLayer.ProductDAO;
import org.example.model.Product;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clasa pentru logica de business a produselor.
 */
public class ProductBLL {

    private final ProductDAO productDAO;

    /**
     * Constructor pentru ProductBLL.
     */
    public ProductBLL() {
        productDAO = new ProductDAO();
    }

    /**
     * Adauga un produs dupa validare.
     * @param product produsul adaugat
     */
    public void insertProduct(Product product) {
        if(product.getName() == null || product.getName().isEmpty()) {
            throw new IllegalArgumentException("Numele produsului nu poate fi gol");
        }

        if(product.getPrice() < 0) {
            throw new IllegalArgumentException("Pretul nu poate fi negativ");
        }

        if(product.getStock() < 0) {
            throw new IllegalArgumentException("Stocul nu poate fi negativ");
        }

        productDAO.insert(product);
    }

    /**
     * Actualizeaza un produs.
     * @param product produsul actualizat
     */
    public void updateProduct(Product product) {
        productDAO.update(product);
    }

    /**
     * Sterge un produs dupa id.
     * @param id id-ul produsului
     */
    public void deleteProduct(int id) {
        productDAO.delete(id);
    }

    /**
     * Returneaza toate produsele.
     * @return lista de produse
     */
    public List<Product> findAllProducts() {
        return productDAO.findAll();
    }

    /**
     * Cauta un produs dupa id.
     * @param id id-ul produsului
     * @return produsul gasit
     */
    public Product findProductById(int id) {
        return productDAO.findById(id);
    }

    /**
     * Returneaza produsele care au stoc disponibil.
     * Foloseste stream si lambda.
     * @return lista produselor disponibile
     */
    public List<Product> findAvailableProducts() {
        return productDAO.findAll()
                .stream()
                .filter(product -> product.getStock() > 0)
                .collect(Collectors.toList());
    }

    /**
     * Returneaza produsele ordonate alfabetic dupa nume.
     * Foloseste stream-uri si lambda expressions.
     *
     * @return lista produselor sortate
     */
    public List<Product> getSortedProducts() {

        return productDAO.findAll()
                .stream()
                .sorted(Comparator.comparing(Product::getName))
                .collect(Collectors.toList());
    }

}