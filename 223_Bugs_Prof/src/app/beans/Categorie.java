/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.beans;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ramalhom
 */
@Entity
@Table(name = "t_categorie")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Categorie.findAll", query = "SELECT c FROM Categorie c"),
    @NamedQuery(name = "Categorie.findByPKCategorie", query = "SELECT c FROM Categorie c WHERE c.pKCategorie = :pKCategorie"),
    @NamedQuery(name = "Categorie.findByNom", query = "SELECT c FROM Categorie c WHERE c.nom = :nom")})
public class Categorie implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PK_Categorie")
    private Integer pKCategorie;
    @Column(name = "Nom")
    private String nom;
    @OneToMany(mappedBy = "fKCategorie")
    private List<Bug> bugList;

    public Categorie() {
    }

    public Categorie(Integer pKCategorie) {
        this.pKCategorie = pKCategorie;
    }

    public Integer getPKCategorie() {
        return pKCategorie;
    }

    public void setPKCategorie(Integer pKCategorie) {
        this.pKCategorie = pKCategorie;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @XmlTransient
    public List<Bug> getBugList() {
        return bugList;
    }

    public void setBugList(List<Bug> bugList) {
        this.bugList = bugList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pKCategorie != null ? pKCategorie.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Categorie)) {
            return false;
        }
        Categorie other = (Categorie) object;
        if ((this.pKCategorie == null && other.pKCategorie != null) || (this.pKCategorie != null && !this.pKCategorie.equals(other.pKCategorie))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "app.beans.Categorie[ pKCategorie=" + pKCategorie + " ]";
    }
    
}
