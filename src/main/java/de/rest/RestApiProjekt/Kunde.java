package de.rest.RestApiProjekt;

import java.util.List;

public class Kunde {
    private int id;
    private String name;
    private String telefonNr;
    private List<Artikel> artikelList;

    public Kunde(int id, String name, String telefonNr, List<Artikel> artikelList) {
        this.id = id;
        this.name = name;
        this.telefonNr = telefonNr;
        this.artikelList = artikelList;
    }

    public Kunde() {}

    public void addArtikel(Artikel artikel) throws ShopException {
        Validator.check(artikel == null, "Artikel nicht gefunden!");
        artikelList.add(artikel);
    }

    public void addAllArtikel(List<Artikel> artikelList) throws ShopException {
        Validator.check(artikelList == null, "Artikeln nicht gefunden!");
        this.artikelList.addAll(artikelList);
    }

    public void removeArtikel(Artikel artikel) throws ShopException {
        Validator.check(artikel == null, "Artikel nicht gefunden!");
        artikelList.remove(artikel);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTelefonNr(String telefonNr) {
        this.telefonNr = telefonNr;
    }

    public void setArtikelList(List<Artikel> artikelList) {
        this.artikelList = artikelList;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTelefonNr() {
        return telefonNr;
    }

    public List<Artikel> getArtikelList() {
        return artikelList;
    }

    public String toString() {
        return "Kunde\n" +
                "Name: " + name + "\nTelefonNr: " + telefonNr +
                "\nArtikeln: " + artikelList;
    }
}
