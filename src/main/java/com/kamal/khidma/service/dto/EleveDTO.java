package com.kamal.khidma.service.dto;


import java.time.Instant;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Eleve entity.
 */
public class EleveDTO implements Serializable {

    private Long id;

    private String firstName;

    private String lastName;

    private Instant dateNaiss;

    private String lieuNaiss;

    private Instant dateEntree;

    @Lob
    private byte[] photo;
    private String photoContentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Instant getDateNaiss() {
        return dateNaiss;
    }

    public void setDateNaiss(Instant dateNaiss) {
        this.dateNaiss = dateNaiss;
    }

    public String getLieuNaiss() {
        return lieuNaiss;
    }

    public void setLieuNaiss(String lieuNaiss) {
        this.lieuNaiss = lieuNaiss;
    }

    public Instant getDateEntree() {
        return dateEntree;
    }

    public void setDateEntree(Instant dateEntree) {
        this.dateEntree = dateEntree;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EleveDTO eleveDTO = (EleveDTO) o;
        if(eleveDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eleveDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EleveDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", dateNaiss='" + getDateNaiss() + "'" +
            ", lieuNaiss='" + getLieuNaiss() + "'" +
            ", dateEntree='" + getDateEntree() + "'" +
            ", photo='" + getPhoto() + "'" +
            "}";
    }
}
