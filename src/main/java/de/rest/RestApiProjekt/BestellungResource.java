package de.rest.RestApiProjekt;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.ArrayList;

@Path("/bestellung")
public class BestellungResource {

    private static Bestellung bestellung = new Bestellung();

    static {

        Kunde maxMustermann = new Kunde(1, "Max Mustermann", "123456789", new ArrayList<>());
        Artikel artikelA = new Artikel(101, "Artikel A", 19.99);
        Artikel artikelB = new Artikel(102, "Artikel B", 29.99);
        try {
            bestellung.addKunde(maxMustermann);
            bestellung.addArtikelToKunde(maxMustermann, artikelA);
            bestellung.addArtikelToKunde(maxMustermann, artikelB);
        } catch (ShopException e) {
            throw new RuntimeException(e);
        }
    }

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
    public Response deleteArtikelById(@PathParam("artikelId") int artikelId) {
        try {
            Artikel artikel = bestellung.getArtikelList().stream()
                    .filter(a -> a.getId() == artikelId)
                    .findFirst()
                    .orElse(null);
            if (artikel == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Artikel nicht gefunden!").build();
            }
            bestellung.artikelLoeschen(artikel);
            bestellung.getKundeListMap().forEach((kunde, artikelList) -> {
                artikelList.removeIf(a -> a.getId() == artikelId);
                kunde.setArtikelList(new ArrayList<>(artikelList));
            });
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (ShopException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    // DELETE: Einen Kunden löschen
    @DELETE
    @Path("/kunden/{kundeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteKundeById(@PathParam("kundeId") int kundeId) {
        try {
            Kunde kunde = bestellung.getKundeList().stream()
                    .filter(k -> k.getId() == kundeId)
                    .findFirst()
                    .orElse(null);
            if (kunde == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Kunde nicht gefunden!").build();
            }
            bestellung.kundeLoeschen(kunde);
            bestellung.getKundeListMap().remove(kunde);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (ShopException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    // DELETE: Artikel eines Kunden löschen
    @DELETE
    @Path("/kunden/{kundeId}/artikel/{artikelId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteArtikelForKunde(@PathParam("kundeId") int kundeId, @PathParam("artikelId") int artikelId) {
        try {
            Kunde kunde = bestellung.getKundeList().stream()
                    .filter(k -> k.getId() == kundeId)
                    .findFirst()
                    .orElse(null);
            if (kunde == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Kunde nicht gefunden!").build();
            }
            List<Artikel> artikelList = bestellung.getArtikelnByKey(kunde);
            if (artikelList != null) {
                Artikel artikelToRemove = artikelList.stream()
                        .filter(a -> a.getId() == artikelId)
                        .findFirst()
                        .orElse(null);
                if (artikelToRemove != null) {
                    bestellung.removeArtikelFromListInMap(kunde, artikelToRemove);
                } else {
                    return Response.status(Response.Status.NOT_FOUND).entity("Artikel nicht gefunden!").build();
                }
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Artikel-Liste nicht gefunden!").build();
            }
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (ShopException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
