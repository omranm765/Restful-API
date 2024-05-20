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

    public List<Kunde> getKundenByValue(List<Artikel> artikelList){
        List<Kunde> kundenMitGleicherListe = new ArrayList<>();
        for (Map.Entry<Kunde, List<Artikel>> entry : kundeListMap.entrySet()) {
            if(entry.getValue().equals(artikelList)){
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
        kunde.removeArtikel(artikel);
        kundeListMap.put(kunde, new ArrayList<>(kunde.getArtikelList()));
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
        /*Artikel artikel1 = new Artikel(1, "Moh Artikel1", 10);
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
    */
        // Erstellen von Artikeln
        Artikel artikel1 = new Artikel(1, "Artikel1", 10.0);
        Artikel artikel2 = new Artikel(2, "Artikel2", 20.0);
        Artikel artikel3 = new Artikel(3, "Artikel3", 30.0);

        // Erstellen von Kunden
        List<Artikel> artikelList1 = new ArrayList<>();
        List<Artikel> artikelList2 = new ArrayList<>();
        List<Artikel> artikelList3 = new ArrayList<>();

        Kunde kunde1 = new Kunde(1, "Kunde1", "0123456789", artikelList1);
        Kunde kunde2 = new Kunde(2, "Kunde2", "0987654321", artikelList2);
        Kunde kunde3 = new Kunde(3, "Kunde3", "1122334455", artikelList3);

        Bestellung bestellung = new Bestellung();

        // Hinzufügen von Artikeln zur Bestellung
        bestellung.addArtikel(artikel1);
        bestellung.addArtikel(artikel2);
        bestellung.addArtikel(artikel3);

        // Hinzufügen von Kunden zur Bestellung
        bestellung.addKunde(kunde1);
        bestellung.addKunde(kunde2);
        bestellung.addKunde(kunde3);

        // Hinzufügen von Artikeln zu Kunden
        bestellung.addArtikelToKunde(kunde1, artikel1);
        bestellung.addArtikelToKunde(kunde1, artikel2);
        bestellung.addArtikelToKunde(kunde2, artikel2);
        bestellung.addArtikelToKunde(kunde2, artikel3);
        bestellung.addArtikelToKunde(kunde3, artikel1);
        bestellung.addArtikelToKunde(kunde3, artikel3);

        // Ausgabe aller Kunden, die denselben Artikel bestellt haben
        System.out.println("Kunden, die Artikel2 bestellt haben:");
        List<Kunde> kundenArtikel2 = bestellung.getKundenByArtikel(artikel2);
        for (Kunde kunde : kundenArtikel2) {
            System.out.println(kunde);
        }

        // Ausgabe aller Artikel, die von diesen Kunden bestellt wurden
        System.out.println("\nArtikel, die von diesen Kunden bestellt wurden:");
        Set<Artikel> artikelnVonKunden = new HashSet<>();
        for (Kunde kunde : kundenArtikel2) {
            artikelnVonKunden.addAll(bestellung.getArtikelnByKey(kunde));
        }
        for (Artikel artikel : artikelnVonKunden) {
            System.out.println(artikel);
        }

        // Testen der Methode getKundenByValue für Kunden mit gleicher Artikel-Liste
        System.out.println("\nKunden mit derselben Liste von Artikeln:");
        List<Kunde> kundenMitGleicherListe = bestellung.getKundenByValue(artikelList2);
        for (Kunde kunde : kundenMitGleicherListe) {
            System.out.println(kunde);
            System.out.println("Anzahl der Kunden: " + kundenMitGleicherListe.size());
        }

    }
}
