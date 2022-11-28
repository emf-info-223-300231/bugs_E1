/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.beans;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ramalhom
 */
@Entity
@Table(name = "t_bug")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bug.findAll", query = "SELECT b FROM Bug b"),
    @NamedQuery(name = "Bug.findByPKBug", query = "SELECT b FROM Bug b WHERE b.pKBug = :pKBug"),
    @NamedQuery(name = "Bug.findByTitre", query = "SELECT b FROM Bug b WHERE b.titre = :titre"),
    @NamedQuery(name = "Bug.findByDescription", query = "SELECT b FROM Bug b WHERE b.description = :description"),
    @NamedQuery(name = "Bug.findByDateCreation", query = "SELECT b FROM Bug b WHERE b.dateCreation = :dateCreation"),
    @NamedQuery(name = "Bug.findByDateModif", query = "SELECT b FROM Bug b WHERE b.dateModif = :dateModif")})
public class Bug implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PK_Bug")
    private Integer pKBug;
    @Column(name = "Titre")
    private String titre;
    @Column(name = "Description")
    private String description;
    @Column(name = "DateCreation")
    @Temporal(TemporalType.DATE)
    private Date dateCreation;
    @Column(name = "DateModif")
    @Version
    private Timestamp dateModif;
    @JoinTable(name = "t_bug_assignement", joinColumns = {
        @JoinColumn(name = "FK_Bug", referencedColumnName = "PK_Bug")}, inverseJoinColumns = {
        @JoinColumn(name = "FK_Personne", referencedColumnName = "PK_Personne")})
    @ManyToMany
    private List<Personne> personneList;
    @JoinColumn(name = "FK_Application", referencedColumnName = "PK_Application")
    @ManyToOne
    private Application fKApplication;
    @OneToMany(mappedBy = "fKBugLie")
    private List<Bug> bugList;
    @JoinColumn(name = "FK_BugLie", referencedColumnName = "PK_Bug")
    @ManyToOne
    private Bug fKBugLie;
    @JoinColumn(name = "FK_Categorie", referencedColumnName = "PK_Categorie")
    @ManyToOne
    private Categorie fKCategorie;
    @JoinColumn(name = "FK_Personne_Cree", referencedColumnName = "PK_Personne")
    @ManyToOne
    private Personne fKPersonneCree;
    @JoinColumn(name = "FK_Priorite", referencedColumnName = "PK_Priorite")
    @ManyToOne
    private Priorite fKPriorite;

    public Bug() {
    }

    public Bug(Integer pKBug) {
        this.pKBug = pKBug;
    }

    public Integer getPKBug() {
        return pKBug;
    }

    public void setPKBug(Integer pKBug) {
        this.pKBug = pKBug;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Timestamp getDateModif() {
        return dateModif;
    }

    public void setDateModif(Timestamp dateModif) {
        this.dateModif = dateModif;
    }

    @XmlTransient
    public List<Personne> getPersonneList() {
        return personneList;
    }

    public void setPersonneList(List<Personne> personneList) {
        this.personneList = personneList;
    }

    public Application getFKApplication() {
        return fKApplication;
    }

    public void setFKApplication(Application fKApplication) {
        this.fKApplication = fKApplication;
    }

    @XmlTransient
    public List<Bug> getBugList() {
        return bugList;
    }

    public void setBugList(List<Bug> bugList) {
        this.bugList = bugList;
    }

    public Bug getFKBugLie() {
        return fKBugLie;
    }

    public void setFKBugLie(Bug fKBugLie) {
        this.fKBugLie = fKBugLie;
    }

    public Categorie getFKCategorie() {
        return fKCategorie;
    }

    public void setFKCategorie(Categorie fKCategorie) {
        this.fKCategorie = fKCategorie;
    }

    public Personne getFKPersonneCree() {
        return fKPersonneCree;
    }

    public void setFKPersonneCree(Personne fKPersonneCree) {
        this.fKPersonneCree = fKPersonneCree;
    }

    public Priorite getFKPriorite() {
        return fKPriorite;
    }

    public void setFKPriorite(Priorite fKPriorite) {
        this.fKPriorite = fKPriorite;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pKBug != null ? pKBug.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bug)) {
            return false;
        }
        Bug other = (Bug) object;
        if ((this.pKBug == null && other.pKBug != null) || (this.pKBug != null && !this.pKBug.equals(other.pKBug))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return titre + " (" + fKApplication + ")";
    }
    
}
