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
@Table(name = "t_application")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Application.findAll", query = "SELECT a FROM Application a"),
    @NamedQuery(name = "Application.findByPKApplication", query = "SELECT a FROM Application a WHERE a.pKApplication = :pKApplication"),
    @NamedQuery(name = "Application.findByNom", query = "SELECT a FROM Application a WHERE a.nom = :nom")})
public class Application implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PK_Application")
    private Integer pKApplication;
    @Column(name = "Nom")
    private String nom;
    @OneToMany(mappedBy = "fKApplication")
    private List<Bug> bugList;

    public Application() {
    }

    public Application(Integer pKApplication) {
        this.pKApplication = pKApplication;
    }

    public Integer getPKApplication() {
        return pKApplication;
    }

    public void setPKApplication(Integer pKApplication) {
        this.pKApplication = pKApplication;
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
        hash += (pKApplication != null ? pKApplication.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Application)) {
            return false;
        }
        Application other = (Application) object;
        if ((this.pKApplication == null && other.pKApplication != null) || (this.pKApplication != null && !this.pKApplication.equals(other.pKApplication))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nom;
    }
    
}
