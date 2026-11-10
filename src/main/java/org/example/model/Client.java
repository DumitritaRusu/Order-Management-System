package org.example.model;

/**
 * Clasa care reprezinta un client din sistem.
 * Contine datele corespunzatoare tabelului Client din baza de date.
 */
public class Client {

    private int id;
    private String name;
    private String address;
    private String email;

    /**
     * Constructor fara parametri.
     * Pentru  reflection.
     */
    public Client() {
    }

    /**
     * Constructor pentru initializarea unui client.
     * @param name numele clientului
     * @param address adresa clientului
     * @param email email-ul clientului
     */
    public Client(String name, String address, String email) {
        this.name = name;
        this.address = address;
        this.email = email;
    }

    /**
     * Returneaza id-ul clientului.
     * @return id-ul
     */
    public int getId() {
        return id;
    }

    /**
     * Seteaza id-ul clientului.
     * @param id noul id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returneaza numele clientului.
     * @return numele
     */
    public String getName() {
        return name;
    }

    /**
     * Seteaza numele clientului.
     * @param name noul nume
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returneaza adresa clientului.
     * @return adresa
     */
    public String getAddress() {
        return address;
    }

    /**
     * Seteaza adresa clientului.
     * @param address noua adresa
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returneaza email-ul clientului.
     * @return email-ul
     */
    public String getEmail() {
        return email;
    }

    /**
     * Seteaza email-ul clientului.
     * @param email noul email
     */
    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    public String toString() {
        return name;
    }
}