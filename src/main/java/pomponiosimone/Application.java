package pomponiosimone;

import pomponiosimone.DAO.OperazioniDao;
import pomponiosimone.entities.Libro;
import pomponiosimone.entities.Rivista;

public class Application {

    public static void main(String[] args) {

        OperazioniDao catalogoService = new OperazioniDao();


        Libro libro = new Libro("155555555", "Harry Potter", 2000, 2012, "Harry", "Fantasy");
        catalogoService.aggiungiElemento(libro);


        Rivista rivista = new Rivista("204060", "Pianura Padana", 2023, 120, Rivista.Periodicita.MENSILE);
        Rivista rivista2 = new Rivista("5060", "Sgarbi e co", 2023, 190, Rivista.Periodicita.MENSILE);
        catalogoService.aggiungiElemento(rivista);
        catalogoService.aggiungiElemento(rivista2);


        catalogoService.cercaPerISBN("155555");
        catalogoService.cercaPerISBN("5060");

        catalogoService.rimuoviElemento("204060");


        catalogoService.chiusuraEm();
    }
}


