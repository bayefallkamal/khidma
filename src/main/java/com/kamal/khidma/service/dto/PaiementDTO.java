package com.kamal.khidma.service.dto;


import java.time.Instant;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Paiement entity.
 */
public class PaiementDTO implements Serializable {

    private Long id;

    private Instant datePaiement;

    private Double montant;

    private String typePaiement;

    private String mois;

    private String nomPayeur;

    private String telPayeur;

    private Long eleveId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(Instant datePaiement) {
        this.datePaiement = datePaiement;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public String getTypePaiement() {
        return typePaiement;
    }

    public void setTypePaiement(String typePaiement) {
        this.typePaiement = typePaiement;
    }

    public String getMois() {
        return mois;
    }

    public void setMois(String mois) {
        this.mois = mois;
    }

    public String getNomPayeur() {
        return nomPayeur;
    }

    public void setNomPayeur(String nomPayeur) {
        this.nomPayeur = nomPayeur;
    }

    public String getTelPayeur() {
        return telPayeur;
    }

    public void setTelPayeur(String telPayeur) {
        this.telPayeur = telPayeur;
    }

    public Long getEleveId() {
        return eleveId;
    }

    public void setEleveId(Long eleveId) {
        this.eleveId = eleveId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PaiementDTO paiementDTO = (PaiementDTO) o;
        if(paiementDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paiementDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PaiementDTO{" +
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
