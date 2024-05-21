package de.rest.RestApiProjekt;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
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
    @Path("/listeVonAllen")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Kunde> getKunden() {
        return kundenListe;
    }

    // GET: Kunde nach ID abrufen
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Kunde getKundeById(@PathParam("id") int id) {
        for (Kunde kunde : kundenListe) {
            if (kunde.getId() == id) {
                return kunde;
            }
        }
        return null;  // Kunde nicht gefunden
    }

    // POST: Neuen Kunden hinzufügen
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Kunde addKunde(Kunde kunde) {
        kundenListe.add(kunde);
        return kunde;
    }

    // PUT: Kunde aktualisieren
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Kunde updateKunde(@PathParam("id") int id, Kunde updatedKunde) {
        for (Kunde kunde : kundenListe) {
            if (kunde.getId() == id) {
                kunde.setName(updatedKunde.getName());
                kunde.setTelefonNr(updatedKunde.getTelefonNr());
                kunde.setArtikelList(updatedKunde.getArtikelList());
                return kunde;
            }
        }
        return null;  // Kunde nicht gefunden
    }

    // DELETE: Kunde löschen
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteKunde(@PathParam("id") int id) {
        for (Kunde kunde : kundenListe) {
            if (kunde.getId() == id) {
                kundenListe.remove(kunde);
                return "Kunde gelöscht!";
            }
        }
        return "Kunde nicht gefunden!";  // Kunde nicht gefunden
    }
}