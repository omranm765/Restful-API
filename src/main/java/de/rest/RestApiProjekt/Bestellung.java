package de.rest.RestApiProjekt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Bestellung {
    private List<Artikel> artikelList;
    private List<Kunde> kundeList;
    private Map<Kunde, List<Artikel>> kundeListMap;

    public Bestellung(){
        artikelList = new ArrayList<>();
        kundeList = new ArrayList<>();
        kundeListMap = new HashMap<>();
    }

    public void addListOfArtikelToKunde(Kunde kunde, List<Artikel> artikelList){
        //Logischen-Denk Fehler
        //Überall überprüfen
        List<Artikel> list = kundeListMap.getOrDefault(kunde, new ArrayList<>());
        kundeListMap.put(kunde, list);
    }

    public List<Artikel> getArtikelByKey(Kunde kunde){
        return kundeListMap.get(kunde);
    }

    public Kunde getKundeByValue(List<Artikel> artikelList){
        for (Map.Entry<Kunde, List<Artikel>> entry : kundeListMap.entrySet()) {
            if(entry.getValue().containsAll(artikelList)){
                return entry.getKey();
            }
        }
        return null;
    }

    public void removeArtikelFromListInMap(Kunde kunde, Artikel artikel){
        artikelList = getArtikelByKey(kunde);
        artikelLoeschen(artikel);
    }

    public void addArtikel(Artikel artikel){
        if (artikel == null){

        }
        artikelList.add(artikel);
    }

    public void artikelLoeschen(Artikel artikel){
        if (artikel == null){

        }

        for (Artikel a: artikelList){
            if (a.getId() == artikel.getId()){
                artikelList.remove(a);
            }
        }
    }

    public void addKunde(Kunde kunde){
        if (kunde == null){

        }
        kundeList.add(kunde);
    }

    public void kundeLoeschen(Kunde kunde){
        if (kunde == null){

        }

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

        // Artikel
        for (Artikel artikel : artikelList) {
            builder.append(artikel.toString()).append("\n");
        }

        // Kunden
        for (Kunde kunde : kundeList) {
            builder.append(kunde.toString()).append("\n");
        }

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

    public static void main(String[] args) {
        Kunde kunde = new Kunde(1, "Moh san", "0122142134");
        Artikel artikel1 = new Artikel(1, "Moh Artikel1", 10);
        Artikel artikel2 = new Artikel(2, "Moh Artikel2", 20);
        Bestellung bestellung = new Bestellung();
        bestellung.addArtikel(artikel1);
        bestellung.addArtikel(artikel2);
        bestellung.addKunde(kunde);
        bestellung.addListOfArtikelToKunde(kunde, bestellung.getArtikelList());
        System.out.println(bestellung);
        bestellung.removeArtikelFromListInMap(kunde, artikel1);
        System.out.println(bestellung);
        System.out.println("KundebyValue:\n" + bestellung.getKundeByValue(bestellung.getArtikelList()));
    }
}
