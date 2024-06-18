package de.rest.RestApiProjekt;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/artikel")
public class ArtikelResource {

    private static List<Artikel> artikelListe = new ArrayList<>();

    static {
        artikelListe.add(new Artikel(101, "Artikel A", 19.99));
        artikelListe.add(new Artikel(102, "Artikel B", 29.99));
        artikelListe.add(new Artikel(103, "Artikel C", 39.99));
        artikelListe.add(new Artikel(104, "Artikel D", 49.99));
    }

    /**
     * Alle Artikel abrufen
     * @return Response Objekt
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Artikel> getArtikel() {
        return artikelListe;
    }

    /**
     * Artikel nach ID abrufen
     * @param id Id des Artikels
     * @return Response Objekt
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArtikelById(@PathParam("id") int id) {
        for (Artikel artikel : artikelListe) {
            if (artikel.getId() == id) {
                return Response.ok(artikel).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Artikel nicht gefunden!").build();
    }

    /**
     * Neuen Artikel hinzufügen
     * @param artikel Der Artikel zum hinzufügen
     * @return Response Objekt
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addArtikel(Artikel artikel) {
        artikelListe.add(artikel);
        return Response.status(Response.Status.CREATED).entity(artikel).build();
    }

    // PUT: Artikel aktualisieren
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateArtikel(@PathParam("id") int id, Artikel updatedArtikel) {
        Artikel artikel = artikelListe.stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElse(null);

        if (artikel != null) {
            artikel.setName(updatedArtikel.getName());
            artikel.setPreis(updatedArtikel.getPreis());
            return Response.ok(artikel).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Artikel nicht gefunden!").build();
        }
    }

    // DELETE: Artikel löschen
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteArtikel(@PathParam("id") int id) {
        for (Artikel artikel : artikelListe) {
            if (artikel.getId() == id) {
                artikelListe.remove(artikel);
                return Response.ok("Artikel gelöscht!").build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Artikel nicht gefunden!").build();
    }

    public static List<Artikel> getArtikelListe() {
        return artikelListe;
    }
}
