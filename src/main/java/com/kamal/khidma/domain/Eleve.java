package com.kamal.khidma.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Eleve.
 */
@Entity
@Table(name = "eleve")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Eleve implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_naiss")
    private Instant dateNaiss;

    @Column(name = "lieu_naiss")
    private String lieuNaiss;

    @Column(name = "date_entree")
    private Instant dateEntree;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @OneToMany(mappedBy = "eleve")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Parent> parents = new HashSet<>();

    @OneToMany(mappedBy = "eleve")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Paiement> paiements = new HashSet<>();

    @ManyToMany(mappedBy = "eleves")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Enseignant> enseignants = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Eleve firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Eleve lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Instant getDateNaiss() {
        return dateNaiss;
    }

    public Eleve dateNaiss(Instant dateNaiss) {
        this.dateNaiss = dateNaiss;
        return this;
    }

    public void setDateNaiss(Instant dateNaiss) {
        this.dateNaiss = dateNaiss;
    }

    public String getLieuNaiss() {
        return lieuNaiss;
    }

    public Eleve lieuNaiss(String lieuNaiss) {
        this.lieuNaiss = lieuNaiss;
        return this;
    }

    public void setLieuNaiss(String lieuNaiss) {
        this.lieuNaiss = lieuNaiss;
    }

    public Instant getDateEntree() {
        return dateEntree;
    }

    public Eleve dateEntree(Instant dateEntree) {
        this.dateEntree = dateEntree;
        return this;
    }

    public void setDateEntree(Instant dateEntree) {
        this.dateEntree = dateEntree;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public Eleve photo(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public Eleve photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public Set<Parent> getParents() {
        return parents;
    }

    public Eleve parents(Set<Parent> parents) {
        this.parents = parents;
        return this;
    }

    public Eleve addParent(Parent parent) {
        this.parents.add(parent);
        parent.setEleve(this);
        return this;
    }

    public Eleve removeParent(Parent parent) {
        this.parents.remove(parent);
        parent.setEleve(null);
        return this;
    }

    public void setParents(Set<Parent> parents) {
        this.parents = parents;
    }

    public Set<Paiement> getPaiements() {
        return paiements;
    }

    public Eleve paiements(Set<Paiement> paiements) {
        this.paiements = paiements;
        return this;
    }

    public Eleve addPaiement(Paiement paiement) {
        this.paiements.add(paiement);
        paiement.setEleve(this);
        return this;
    }

    public Eleve removePaiement(Paiement paiement) {
        this.paiements.remove(paiement);
        paiement.setEleve(null);
        return this;
    }

    public void setPaiements(Set<Paiement> paiements) {
        this.paiements = paiements;
    }

    public Set<Enseignant> getEnseignants() {
        return enseignants;
    }

    public Eleve enseignants(Set<Enseignant> enseignants) {
        this.enseignants = enseignants;
        return this;
    }

    public Eleve addEnseignant(Enseignant enseignant) {
        this.enseignants.add(enseignant);
        enseignant.getEleves().add(this);
        return this;
    }

    public Eleve removeEnseignant(Enseignant enseignant) {
        this.enseignants.remove(enseignant);
        enseignant.getEleves().remove(this);
        return this;
    }

    public void setEnseignants(Set<Enseignant> enseignants) {
        this.enseignants = enseignants;
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
        Eleve eleve = (Eleve) o;
        if (eleve.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eleve.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Eleve{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", dateNaiss='" + getDateNaiss() + "'" +
            ", lieuNaiss='" + getLieuNaiss() + "'" +
            ", dateEntree='" + getDateEntree() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            "}";
    }
}
