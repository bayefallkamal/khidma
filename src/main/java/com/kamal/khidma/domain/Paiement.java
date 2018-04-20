package com.kamal.khidma.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Paiement.
 */
@Entity
@Table(name = "paiement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Paiement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_paiement")
    private Instant datePaiement;

    @Column(name = "montant")
    private Double montant;

    @Column(name = "type_paiement")
    private String typePaiement;

    @Column(name = "mois")
    private String mois;

    @Column(name = "nom_payeur")
    private String nomPayeur;

    @Column(name = "tel_payeur")
    private String telPayeur;

    @ManyToOne
    private Eleve eleve;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDatePaiement() {
        return datePaiement;
    }

    public Paiement datePaiement(Instant datePaiement) {
        this.datePaiement = datePaiement;
        return this;
    }

    public void setDatePaiement(Instant datePaiement) {
        this.datePaiement = datePaiement;
    }

    public Double getMontant() {
        return montant;
    }

    public Paiement montant(Double montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public String getTypePaiement() {
        return typePaiement;
    }

    public Paiement typePaiement(String typePaiement) {
        this.typePaiement = typePaiement;
        return this;
    }

    public void setTypePaiement(String typePaiement) {
        this.typePaiement = typePaiement;
    }

    public String getMois() {
        return mois;
    }

    public Paiement mois(String mois) {
        this.mois = mois;
        return this;
    }

    public void setMois(String mois) {
        this.mois = mois;
    }

    public String getNomPayeur() {
        return nomPayeur;
    }

    public Paiement nomPayeur(String nomPayeur) {
        this.nomPayeur = nomPayeur;
        return this;
    }

    public void setNomPayeur(String nomPayeur) {
        this.nomPayeur = nomPayeur;
    }

    public String getTelPayeur() {
        return telPayeur;
    }

    public Paiement telPayeur(String telPayeur) {
        this.telPayeur = telPayeur;
        return this;
    }

    public void setTelPayeur(String telPayeur) {
        this.telPayeur = telPayeur;
    }

    public Eleve getEleve() {
        return eleve;
    }

    public Paiement eleve(Eleve eleve) {
        this.eleve = eleve;
        return this;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Paiement paiement = (Paiement) o;
        if (paiement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paiement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Paiement{" +
            "id=" + getId() +
            ", datePaiement='" + getDatePaiement() + "'" +
            ", montant=" + getMontant() +
            ", typePaiement='" + getTypePaiement() + "'" +
            ", mois='" + getMois() + "'" +
            ", nomPayeur='" + getNomPayeur() + "'" +
            ", telPayeur='" + getTelPayeur() + "'" +
            "}";
    }
}
