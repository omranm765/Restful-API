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
    // DELETE: Einen Artikel löschen
    @DELETE
    @Path("/artikel/{artikelId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteArtikel(@PathParam("artikelId") int artikelId) {
        try {
            Artikel artikel = bestellung.getArtikelList().stream().filter(a -> a.getId() == artikelId).findFirst().orElse(null);
            bestellung.artikelLoeschen(artikel);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (ShopException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
    // DELETE: Einen Kunden löschen
    @DELETE
    @Path("/kunden/{kundeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteKunde(@PathParam("kundeId") int kundeId) {
        try {
            Kunde kunde = bestellung.getKundeList().stream().filter(k -> k.getId() == kundeId).findFirst().orElse(null);
            bestellung.kundeLoeschen(kunde);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (ShopException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
    // DELETE: Alle Artikel eines Kunden löschen
    @DELETE
    @Path("/kunden/{kundeId}/artikel")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteArtikelForKunde(@PathParam("kundeId") int kundeId) {
        try {
            Kunde kunde = bestellung.getKundeList().stream().filter(k -> k.getId() == kundeId).findFirst().orElse(null);
            if (kunde != null) {
                List<Artikel> artikelList = bestellung.getArtikelnByKey(kunde);
                for (Artikel artikel : artikelList) {
                    bestellung.removeArtikelFromListInMap(kunde, artikel);
                }
                return Response.status(Response.Status.NO_CONTENT).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Kunde nicht gefunden!").build();
            }
        } catch (ShopException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
