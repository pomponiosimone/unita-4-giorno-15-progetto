package pomponiosimone.entities;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
@DiscriminatorValue("RIVISTA")
public class Rivista extends Elemento {
    @Enumerated(EnumType.STRING)
    private Periodicita periodicita;

    // Costruttore
    public Rivista(String codiceISBN, String titolo, int annoPubblicazione, int numeroPagine, Periodicita periodicita) {
        super(codiceISBN, titolo, annoPubblicazione, numeroPagine);
        this.periodicita = periodicita;
    }

    public Rivista() {
    }

    // Getter e Setter
    public Periodicita getPeriodicita() {
        return periodicita;
    }

    public void setPeriodicita(Periodicita periodicita) {
        this.periodicita = periodicita;
    }

    // Override toString
    @Override
    public String toString() {
        return "Rivista{" +
                "periodicita=" + periodicita +
                ", " + super.toString() +
                '}';
    }

    public enum Periodicita {
        SETTIMANALE,
        MENSILE,
        SEMESTRALE
    }
}