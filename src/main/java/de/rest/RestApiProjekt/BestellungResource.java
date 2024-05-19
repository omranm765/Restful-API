package de.rest.RestApiProjekt;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import java.util.ArrayList;

@Path("/bestellung")
public class BestellungResource {

    private static Bestellung bestellung = new Bestellung();

    // GET: Alle Artikel abrufen
    @GET
    @Path("/artikel")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Artikel> getArtikelList() {
        return bestellung.getArtikelList();
    }

    // POST: Neuen Artikel hinzufügen
    @POST
    @Path("/artikel")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Artikel addArtikel(Artikel artikel) throws ShopException {
        bestellung.addArtikel(artikel);
        return artikel;
    }

    // GET: Alle Kunden abrufen
    @GET
    @Path("/kunden")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Kunde> getKundeList() {
        return bestellung.getKundeList();
    }

    // POST: Neuen Kunden hinzufügen
    @POST
    @Path("/kunden")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Kunde addKunde(Kunde kunde) throws ShopException {
        bestellung.addKunde(kunde);
        return kunde;
    }

    // POST: Artikel zu einem Kunden hinzufügen
    @POST
    @Path("/kunden/{kundeId}/artikel")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Artikel> addArtikelToKunde(@PathParam("kundeId") int kundeId, List<Artikel> artikelList) throws ShopException {
        Kunde kunde = bestellung.getKundeList().stream().filter(k -> k.getId() == kundeId).findFirst().orElse(null);
        if (kunde != null) {
            bestellung.addListOfArtikelToKunde(kunde, artikelList);
        }
        return artikelList;
    }

    // GET: Artikel für einen bestimmten Kunden abrufen
    @GET
    @Path("/kunden/{kundeId}/artikel")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Artikel> getArtikelForKunde(@PathParam("kundeId") int kundeId) {
        Kunde kunde = bestellung.getKundeList().stream().filter(k -> k.getId() == kundeId).findFirst().orElse(null);
        if (kunde != null) {
            return bestellung.getArtikelnByKey(kunde);
        }
        return new ArrayList<>();
    }
}
