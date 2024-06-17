package de.rest.RestApiProjekt;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.ArrayList;

@Path("/kunden")
public class KundenResource {

    private static List<Kunde> kundenListe = new ArrayList<>();

    static {
        // Beispiel-Kunden hinzufügen
        List<Artikel> artikelList1 = new ArrayList<>();
        artikelList1.add(new Artikel(101, "Artikel A", 19.99));
        artikelList1.add(new Artikel(102, "Artikel B", 29.99));
        kundenListe.add(new Kunde(1, "Max Mustermann", "123456789", artikelList1));

        List<Artikel> artikelList2 = new ArrayList<>();
        artikelList2.add(new Artikel(103, "Artikel C", 39.99));
        artikelList2.add(new Artikel(104, "Artikel D", 49.99));
        kundenListe.add(new Kunde(2, "Erika Musterfrau", "987654321", artikelList2));
    }


    // GET: Alle Kunden abrufen
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKunden() {
        return Response.ok(kundenListe).build();
    }

    // GET: Kunde nach ID abrufen
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKundeById(@PathParam("id") int id) {
        for (Kunde kunde : kundenListe) {
            if (kunde.getId() == id) {
                return Response.ok(kunde).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Kunde nicht gefunden!").build();
    }

    // POST: Neuen Kunden hinzufügen
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addKunde(Kunde kunde) {
        kundenListe.add(kunde);
        return Response.status(Response.Status.CREATED).entity(kunde).build();
    }

    // PUT: Kunde aktualisieren
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateKunde(@PathParam("id") int id, Kunde updatedKunde) {
        for (Kunde kunde : kundenListe) {
            if (kunde.getId() == id) {
                kunde.setName(updatedKunde.getName());
                kunde.setTelefonNr(updatedKunde.getTelefonNr());
                kunde.setArtikelList(updatedKunde.getArtikelList());
                return Response.ok(kunde).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Kunde nicht gefunden!").build();
    }

    // DELETE: Kunde löschen
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteKunde(@PathParam("id") int id) {
        for (Kunde kunde : kundenListe) {
            if (kunde.getId() == id) {
                kundenListe.remove(kunde);
                return Response.ok("Kunde gelöscht!").build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Kunde nicht gefunden").build();
    }
}