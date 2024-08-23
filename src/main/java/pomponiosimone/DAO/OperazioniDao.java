package pomponiosimone.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import pomponiosimone.entities.Elemento;
import pomponiosimone.entities.Prestito;

import java.util.List;
import java.util.Optional;

public class OperazioniDao {
    private EntityManagerFactory emf;
    private EntityManager em;

    public OperazioniDao() {
        emf = Persistence.createEntityManagerFactory("unita-4-giorno-15");
        em = emf.createEntityManager();
    }

    //1) Aggiunta elemento catalogo
    public void aggiungiElemento(Elemento elemento) {
        Optional<Elemento> esistente = cercaPerISBN(elemento.getCodiceISBN());
        if (esistente.isPresent()) {
            System.out.println("Elemento con ISBN " + elemento.getCodiceISBN() + " esiste già nel catalogo.");
        } else {
            try {
                em.getTransaction().begin();
                em.persist(elemento);
                em.getTransaction().commit();
                System.out.println("Elemento aggiunto con successo: " + elemento);
            } catch (Exception e) {
                em.getTransaction().rollback();
                e.printStackTrace();
            }
        }
    }

    //2) Rimozione di un elemento dal catalogo dato un codice ISBN
    public void rimuoviElemento(String codiceISBN) {
        try {
            em.getTransaction().begin();
            Elemento elemento = em.find(Elemento.class, codiceISBN);
            if (elemento != null) {
                em.remove(elemento);
                System.out.println("Elemento rimosso dal catalogo: " + elemento);
            } else {
                System.out.println("Elemento non trovato con codice ISBN: " + codiceISBN);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    // 3) Ricerca di un elemento nel catalogo dato un codice ISBN
    public Optional<Elemento> cercaPerISBN(String codiceISBN) {
        Elemento elemento = em.find(Elemento.class, codiceISBN);
        return Optional.ofNullable(elemento);
    }

    // 4) Ricerca per anno di pubblicazione
    public List<Elemento> cercaPerAnnoPubblicazione(int anno) {
        TypedQuery<Elemento> query = em.createQuery("SELECT e FROM Elemento e WHERE e.annoPubblicazione = :anno", Elemento.class);
        query.setParameter("anno", anno);
        return query.getResultList();
    }

    // 5) Ricerca per autore
    public List<Elemento> cercaPerAutore(String autore) {
        TypedQuery<Elemento> query = em.createQuery("SELECT e FROM Libro e WHERE e.autore LIKE :autore", Elemento.class);
        query.setParameter("autore", "%" + autore + "%");
        return query.getResultList();
    }

    //6)  Ricerca per titolo o parte di esso
    public List<Elemento> cercaPerTitolo(String titolo) {
        TypedQuery<Elemento> query = em.createQuery("SELECT e FROM Elemento e WHERE e.titolo LIKE :titolo", Elemento.class);
        query.setParameter("titolo", "%" + titolo + "%");
        return query.getResultList();
    }

    //7) Ricerca degli elementi attualmente in prestito dato un numero di tessera utente
    public List<Elemento> cercaElementiInPrestito(String numeroTessera) {
        TypedQuery<Elemento> query = em.createQuery(
                "SELECT p.elemento FROM Prestito p WHERE p.utente.numeroTessera = :numeroTessera AND p.dataRestituzioneEffettiva IS NULL",
                Elemento.class);
        query.setParameter("numeroTessera", numeroTessera);
        return query.getResultList();
    }

    //8) Ricerca di tutti i prestiti scaduti e non ancora restituiti
    public List<Prestito> cercaPrestitiScaduti() {
        TypedQuery<Prestito> query = em.createQuery(
                "SELECT p FROM Prestito p WHERE p.dataRestituzioneEffettiva IS NULL AND p.dataRestituzionePrevista < CURRENT_DATE",
                Prestito.class);
        return query.getResultList();
    }

    // Chiusura dell'EntityManager e dell'EntityManagerFactory
    public void chiusuraEm() {
        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}