package pomponiosimone.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import pomponiosimone.entities.Elemento;

import java.util.Optional;

public class OperazioniDao {
    private EntityManagerFactory emf;
    private EntityManager em;

    public OperazioniDao() {
        emf = Persistence.createEntityManagerFactory("unita-4-giorno-15");
        em = emf.createEntityManager();
    }

    // Aggiunta elemento catalogo
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

    // Rimozione di un elemento dal catalogo dato un codice ISBN
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

    // Ricerca di un elemento nel catalogo dato un codice ISBN
    public Optional<Elemento> cercaPerISBN(String codiceISBN) {
        Elemento elemento = em.find(Elemento.class, codiceISBN);
        if (elemento != null) {
            System.out.println("Elemento trovato: " + elemento);
        } else {
            System.out.println("Elemento non trovato con codice ISBN: " + codiceISBN);
        }
        return Optional.ofNullable(elemento);
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