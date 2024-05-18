package de.rest.RestApiProjekt;

public class Kunde {
    private int id;
    private String name;
    private String teleforNr;

    public Kunde(int id, String name, String teleforNr) {
        this.id = id;
        this.name = name;
        this.teleforNr = teleforNr;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTeleforNr(String teleforNr) {
        this.teleforNr = teleforNr;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTeleforNr() {
        return teleforNr;
    }

    public String toString(){
        return "Kunde\n" +
                "Name: " + name + "\nTelefonNr: " + teleforNr;
    }
}
