package pomponiosimone;

import pomponiosimone.DAO.OperazioniDao;
import pomponiosimone.entities.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class Application {

    public static void main(String[] args) {
        OperazioniDao catalogoService = new OperazioniDao();


        // Aggiunta di un libro
        Libro libro1 = new Libro("850679", "Harry Potter", 2000, 2012, "J.K. Rowling", "Fantasy");
        catalogoService.aggiungiElemento(libro1);

        Utente utente1 = new Utente("luca", "Rossi", LocalDate.of(2003, 5, 15), "TESS123");

        // Aggiunta di riviste
        Rivista rivista1 = new Rivista("204060", "Pianura Padana", 2023, 120, Rivista.Periodicita.MENSILE);
        Rivista rivista2 = new Rivista("5060", "Sgarbi e co", 2023, 190, Rivista.Periodicita.MENSILE);
        catalogoService.aggiungiElemento(rivista1);
        catalogoService.aggiungiElemento(rivista2);

        // Ricerca per ISBN
        System.out.println("Ricerca per ISBN '850679':");
        Optional<Elemento> elementoTrovato = catalogoService.cercaPerISBN("850679");
        System.out.println(elementoTrovato != null ? "Elemento trovato: " + elementoTrovato : "Elemento non trovato.");

        // Ricerca per anno di pubblicazione
        System.out.println("Ricerca per anno di pubblicazione 2023:");
        List<Elemento> elementi2023 = catalogoService.cercaPerAnnoPubblicazione(2023);
        System.out.println("Elementi trovati: " + elementi2023);

        // Ricerca per autore
        System.out.println("Ricerca per autore 'J.K. Rowling':");
        List<Elemento> libriAutore = catalogoService.cercaPerAutore("J.K. Rowling");
        System.out.println("Libri trovati: " + libriAutore);

        // Ricerca per titolo
        System.out.println("Ricerca per titolo 'Sgarbi':");
        List<Elemento> elementiTitolo = catalogoService.cercaPerTitolo("Sgarbi");
        System.out.println("Elementi trovati: " + elementiTitolo);

        // Rimozione di una rivista
        System.out.println("Rimozione della rivista con ISBN '204060':");
        catalogoService.rimuoviElemento("204060");

        // Ricerca di tutti i prestiti scaduti e non ancora restituiti
        System.out.println("Ricerca di prestiti scaduti:");
        List<Prestito> prestitiScaduti = catalogoService.cercaPrestitiScaduti();
        System.out.println("Prestiti scaduti trovati: " + prestitiScaduti);

        // Chiusura dell'EntityManager
        catalogoService.chiusuraEm();
    }
}

