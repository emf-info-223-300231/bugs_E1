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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
@Table(name = "t_personne")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Personne.findAll", query = "SELECT p FROM Personne p"),
    @NamedQuery(name = "Personne.findByPKPersonne", query = "SELECT p FROM Personne p WHERE p.pKPersonne = :pKPersonne"),
    @NamedQuery(name = "Personne.findByNom", query = "SELECT p FROM Personne p WHERE p.nom = :nom"),
    @NamedQuery(name = "Personne.findByPrenom", query = "SELECT p FROM Personne p WHERE p.prenom = :prenom")})
public class Personne implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PK_Personne")
    private Integer pKPersonne;
    @Column(name = "Nom")
    private String nom;
    @Column(name = "Prenom")
    private String prenom;
    @ManyToMany(mappedBy = "personneList")
    private List<Bug> bugList;
    @JoinColumn(name = "FK_Classe", referencedColumnName = "PK_Classe")
    @ManyToOne
    private Classe fKClasse;
    @OneToMany(mappedBy = "fKPersonneCree")
    private List<Bug> bugList1;

    public Personne() {
    }

    public Personne(Integer pKPersonne) {
        this.pKPersonne = pKPersonne;
    }

    public Integer getPKPersonne() {
        return pKPersonne;
    }

    public void setPKPersonne(Integer pKPersonne) {
        this.pKPersonne = pKPersonne;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @XmlTransient
    public List<Bug> getBugList() {
        return bugList;
    }

    public void setBugList(List<Bug> bugList) {
        this.bugList = bugList;
    }

    public Classe getFKClasse() {
        return fKClasse;
    }

    public void setFKClasse(Classe fKClasse) {
        this.fKClasse = fKClasse;
    }

    @XmlTransient
    public List<Bug> getBugList1() {
        return bugList1;
    }

    public void setBugList1(List<Bug> bugList1) {
        this.bugList1 = bugList1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pKPersonne != null ? pKPersonne.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Personne)) {
            return false;
        }
        Personne other = (Personne) object;
        if ((this.pKPersonne == null && other.pKPersonne != null) || (this.pKPersonne != null && !this.pKPersonne.equals(other.pKPersonne))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "app.beans.Personne[ pKPersonne=" + pKPersonne + " ]";
    }
    
}
