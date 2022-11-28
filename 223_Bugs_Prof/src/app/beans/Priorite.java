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
@Table(name = "t_priorite")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Priorite.findAll", query = "SELECT p FROM Priorite p"),
    @NamedQuery(name = "Priorite.findByPKPriorite", query = "SELECT p FROM Priorite p WHERE p.pKPriorite = :pKPriorite"),
    @NamedQuery(name = "Priorite.findByNom", query = "SELECT p FROM Priorite p WHERE p.nom = :nom")})
public class Priorite implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PK_Priorite")
    private Integer pKPriorite;
    @Column(name = "Nom")
    private String nom;
    @OneToMany(mappedBy = "fKPriorite")
    private List<Bug> bugList;

    public Priorite() {
    }

    public Priorite(Integer pKPriorite) {
        this.pKPriorite = pKPriorite;
    }

    public Integer getPKPriorite() {
        return pKPriorite;
    }

    public void setPKPriorite(Integer pKPriorite) {
        this.pKPriorite = pKPriorite;
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
        hash += (pKPriorite != null ? pKPriorite.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Priorite)) {
            return false;
        }
        Priorite other = (Priorite) object;
        if ((this.pKPriorite == null && other.pKPriorite != null) || (this.pKPriorite != null && !this.pKPriorite.equals(other.pKPriorite))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "app.beans.Priorite[ pKPriorite=" + pKPriorite + " ]";
    }
    
}
