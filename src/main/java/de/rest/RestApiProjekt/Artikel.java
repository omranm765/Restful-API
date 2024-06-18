package de.rest.RestApiProjekt;

public class Artikel {
    private int id;

    private String name;
    private double preis;

    public Artikel(int id, String name, double preis) {
        this.id = id;
        this.name = name;
        this.preis = preis;
    }
    public Artikel (){

    }

    public void setPreis(double preis) {
        this.preis = preis;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPreis() {
        return preis;
    }

    public String toString(){
        return "Name: " + name + "\nPreis: " + preis;
    }
}
