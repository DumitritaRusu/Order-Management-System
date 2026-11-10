package org.example.businessLayer;

import org.example.dataAccessLayer.ClientDAO;
import org.example.model.Client;

import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Clasa pentru logica de business a clientilor.
 */
public class ClientBLL {

    private final ClientDAO clientDAO;

    /**
     * Constructor pentru ClientBLL.
     */
    public ClientBLL() {
        clientDAO = new ClientDAO();
    }

    /**
     * Adauga un client dupa validare.
     * @param client clientul adaugat
     */
    public void insertClient(Client client) {
        if(client.getName() == null || client.getName().isEmpty()) {
            throw new IllegalArgumentException("Numele clientului nu poate fi gol");
        }
        String EMAIL_PATTERN =
                "^[A-Za-z0-9+_.-]+@(.+)$";

        Pattern pattern =
                Pattern.compile(EMAIL_PATTERN);

        if (!pattern.matcher(client.getEmail()).matches()) {

            throw new IllegalArgumentException(
                    "Email is not valid!"
            );
        }
        clientDAO.insert(client);
    }

    /**
     * Actualizeaza un client.
     * @param client clientul actualizat
     */
    public void updateClient(Client client) {
        clientDAO.update(client);
    }

    /**
     * Sterge un client dupa id.
     * @param id id-ul clientului
     */
    public void deleteClient(int id) {
        clientDAO.delete(id);
    }

    /**
     * Returneaza toti clientii.
     * @return lista de clienti
     */
    public List<Client> findAllClients() {
        return clientDAO.findAll();
    }

    /**
     * Cauta un client dupa id.
     * @param id id-ul clientului
     * @return clientul gasit
     */
    public Client findClientById(int id) {
        return clientDAO.findById(id);
    }

    /**
     * Returneaza clientii ordonati alfabetic.
     *
     * @return lista clientilor sortati
     */
    public List<Client> getSortedClients() {

        return clientDAO.findAll()
                .stream()
                .sorted(Comparator.comparing(Client::getName))
                .collect(Collectors.toList());
    }
}