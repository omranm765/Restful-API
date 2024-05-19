package de.rest.RestApiProjekt;

import java.util.*;

public class Bestellung {
    private List<Artikel> artikelList;
    private List<Kunde> kundeList;
    private Map<Kunde, List<Artikel>> kundeListMap;

    public Bestellung(){
        artikelList = new ArrayList<>();
        kundeList = new ArrayList<>();
        kundeListMap = new HashMap<>();
    }

    public void addListOfArtikelToKunde(Kunde kunde, List<Artikel> artikelList) throws ShopException {
        Validator.check(kunde == null, "Kunde nicht gefunden!" );
        kunde.addAllArtikel(artikelList);
        kundeListMap.put(kunde, kunde.getArtikelList());
    }

    public void addArtikelToKunde(Kunde kunde, Artikel artikel) throws ShopException {
        Validator.check(kunde == null, "Kunde nicht gefunden!" );
        Validator.check(artikel == null, "Artikel nicht gefunden!" );
        kunde.addArtikel(artikel);
        kundeListMap.put(kunde, kunde.getArtikelList());
    }

    public List<Artikel> getArtikelnByKey(Kunde kunde){
        return kundeListMap.get(kunde);
    }

    public Kunde getKundeByValue(List<Artikel> artikelList){
        for (Map.Entry<Kunde, List<Artikel>> entry : kundeListMap.entrySet()) {
            if(new HashSet<>(entry.getValue()).containsAll(artikelList)){
                return entry.getKey();
            }
        }
        return null;
    }

    public void removeArtikelFromListInMap(Kunde kunde, Artikel artikel) throws ShopException {
        /*artikelList = getArtikelnByKey(kunde);
        artikelLoeschen(artikel);*/
        kunde.removeArtikel(artikel);
    }

    public void addArtikel(Artikel artikel) throws ShopException {
        Validator.check(artikel == null, "Artikel nicht gefunden!" );
        artikelList.add(artikel);
    }

    public void artikelLoeschen(Artikel artikel) throws ShopException {
        Validator.check(artikel == null, "Artikel nicht gefunden!" );

        artikelList.removeIf(a -> a.getId() == artikel.getId());
    }

    public void addKunde(Kunde kunde) throws ShopException {
        Validator.check(kunde == null, "Kunde nicht gefunden!" );
        kundeList.add(kunde);
    }

    public void kundeLoeschen(Kunde kunde) throws ShopException {
        Validator.check(kunde == null, "Kunde nicht gefunden!" );
        for (Kunde k: kundeList){
            if (k.getId() == kunde.getId()){
                kundeList.remove(k);
            }
        }
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


        // Kunde-Liste Map
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

    public static void main(String[] args) throws ShopException {
        Artikel artikel1 = new Artikel(1, "Moh Artikel1", 10);
        Artikel artikel2 = new Artikel(2, "Moh Artikel2", 20);
        List<Artikel> artikelList = new ArrayList<>();
        Kunde kunde = new Kunde(1, "Moh san", "0122142134", artikelList);
        Bestellung bestellung = new Bestellung();
        kunde.addArtikel(artikel1);
        kunde.addArtikel(artikel2);
        bestellung.addArtikel(artikel1);
        bestellung.addArtikel(artikel2);
        bestellung.addKunde(kunde);
        bestellung.addListOfArtikelToKunde(kunde, bestellung.getArtikelList());
        System.out.println(bestellung);
        bestellung.removeArtikelFromListInMap(kunde, artikel1);
        System.out.println(bestellung);
        System.out.println("KundebyValue:\n" + bestellung.getKundeByValue(artikelList));
    }
}
