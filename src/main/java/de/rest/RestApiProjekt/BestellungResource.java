package de.rest.RestApiProjekt;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
    public Response addArtikel(Artikel artikel) {
        try {
            bestellung.addArtikel(artikel);
            return Response.status(Response.Status.CREATED).entity(artikel).build();
        } catch (ShopException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
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
    public Response addKunde(Kunde kunde) {
        try {
            bestellung.addKunde(kunde);
            return Response.status(Response.Status.CREATED).entity(kunde).build();
        } catch (ShopException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    // POST: Artikel zu einem Kunden hinzufügen
    @POST
    @Path("/kunden/{kundeId}/artikel")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addArtikelToKunde(@PathParam("kundeId") int kundeId, List<Artikel> artikelList) {
        try {
            Kunde kunde = bestellung.getKundeList().stream().filter(k -> k.getId() == kundeId).findFirst().orElse(null);
            if (kunde != null) {
                bestellung.addListOfArtikelToKunde(kunde, artikelList);
                return Response.ok(artikelList).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Kunde nicht gefunden!").build();
            }
        } catch (ShopException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    // GET: Artikel für einen bestimmten Kunden abrufen
    @GET
    @Path("/kunden/{kundeId}/artikel")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArtikelForKunde(@PathParam("kundeId") int kundeId) {
        Kunde kunde = bestellung.getKundeList().stream().filter(k -> k.getId() == kundeId).findFirst().orElse(null);
        if (kunde != null) {
            return Response.ok(bestellung.getArtikelnByKey(kunde)).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Kunde nicht gefunden!").build();
        }
    }
}
