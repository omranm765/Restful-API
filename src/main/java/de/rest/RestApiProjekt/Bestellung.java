package de.rest.RestApiProjekt;

import java.util.*;

public class Bestellung {
    private List<Artikel> artikelList;
    private List<Kunde> kundeList;
    private Map<Kunde, List<Artikel>> kundeListMap;

    public Bestellung() {
        artikelList = new ArrayList<>();
        kundeList = new ArrayList<>();
        kundeListMap = new HashMap<>();
    }

    public void addListOfArtikelToKunde(Kunde kunde, List<Artikel> artikelList) throws ShopException {
        Validator.check(kunde == null, "Kunde nicht gefunden!");
        kunde.addAllArtikel(artikelList);
        kundeListMap.put(kunde, kunde.getArtikelList());
    }

    public void addArtikelToKunde(Kunde kunde, Artikel artikel) throws ShopException {
        Validator.check(kunde == null, "Kunde nicht gefunden!");
        Validator.check(artikel == null, "Artikel nicht gefunden!");
        kunde.addArtikel(artikel);
        kundeListMap.put(kunde, kunde.getArtikelList());
    }

    public List<Artikel> getArtikelnByKey(Kunde kunde) {
        return kundeListMap.getOrDefault(kunde, new ArrayList<>());
    }

    public List<Kunde> getKundenByValue(List<Artikel> artikelList) {
        List<Kunde> kundenMitGleicherListe = new ArrayList<>();
        for (Map.Entry<Kunde, List<Artikel>> entry : kundeListMap.entrySet()) {
            if (entry.getValue().equals(artikelList)) {
                kundenMitGleicherListe.add(entry.getKey());
            }
        }
        return kundenMitGleicherListe;
    }

    public List<Kunde> getKundenByArtikel(Artikel artikel) {
        List<Kunde> kunden = new ArrayList<>();
        for (Map.Entry<Kunde, List<Artikel>> entry : kundeListMap.entrySet()) {
            if (entry.getValue().contains(artikel)) {
                kunden.add(entry.getKey());
            }
        }
        return kunden;
    }

    public void removeArtikelFromListInMap(Kunde kunde, Artikel artikel) throws ShopException {
        Validator.check(kunde == null, "Kunde nicht gefunden!");
        Validator.check(artikel == null, "Artikel nicht gefunden!");
        kunde.removeArtikel(artikel);
        List<Artikel> updatedArtikelList = new ArrayList<>(kunde.getArtikelList());
        kundeListMap.put(kunde, updatedArtikelList);
        System.out.println(kunde);
    }

    public void addArtikel(Artikel artikel) throws ShopException {
        Validator.check(artikel == null, "Artikel nicht gefunden!");
        artikelList.add(artikel);
    }

    public void artikelLoeschen(Artikel artikel) throws ShopException {
        Validator.check(artikel == null, "Artikel nicht gefunden!");
        artikelList.removeIf(a -> a.getId() == artikel.getId());
    }

    public void addKunde(Kunde kunde) throws ShopException {
        Validator.check(kunde == null, "Kunde nicht gefunden!");
        kundeList.add(kunde);
    }

    public void kundeLoeschen(Kunde kunde) throws ShopException {
        Validator.check(kunde == null, "Kunde nicht gefunden!");
        kundeList.removeIf(k -> k.getId() == kunde.getId());
    }

    public List<Artikel> getArtikelList() {
        return artikelList;
    }

    public void setArtikelList(List<Artikel> artikelList) {
        this.artikelList = artikelList;
    }

    public List<Kunde> getKundeList() {
        return kundeList;
    }

    public void setKundeList(List<Kunde> kundeList) {
        this.kundeList = kundeList;
    }

    public Map<Kunde, List<Artikel>> getKundeListMap() {
        return kundeListMap;
    }

    public void setKundeListMap(Map<Kunde, List<Artikel>> kundeListMap) {
        this.kundeListMap = kundeListMap;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Bestellung:\n");
        builder.append("Kunde-Liste Map:\n");
        for (Map.Entry<Kunde, List<Artikel>> entry : kundeListMap.entrySet()) {
            builder.append("Kunde: ").append(entry.getKey().toString()).append("\n");
            builder.append("Artikel: ");
            for (Artikel artikel : entry.getValue()) {
                builder.append(artikel.toString()).append(", ");
            }
            builder.append("\n");
        }

        return builder.toString();
    }
}
