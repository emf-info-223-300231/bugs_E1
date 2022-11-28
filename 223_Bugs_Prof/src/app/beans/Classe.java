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
@Table(name = "t_classe")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Classe.findAll", query = "SELECT c FROM Classe c"),
    @NamedQuery(name = "Classe.findByPKClasse", query = "SELECT c FROM Classe c WHERE c.pKClasse = :pKClasse"),
    @NamedQuery(name = "Classe.findByNumero", query = "SELECT c FROM Classe c WHERE c.numero = :numero")})
public class Classe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PK_Classe")
    private Integer pKClasse;
    @Column(name = "Numero")
    private Integer numero;
    @OneToMany(mappedBy = "fKClasse")
    private List<Personne> personneList;

    public Classe() {
    }

    public Classe(Integer pKClasse) {
        this.pKClasse = pKClasse;
    }

    public Integer getPKClasse() {
        return pKClasse;
    }

    public void setPKClasse(Integer pKClasse) {
        this.pKClasse = pKClasse;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    @XmlTransient
    public List<Personne> getPersonneList() {
        return personneList;
    }

    public void setPersonneList(List<Personne> personneList) {
        this.personneList = personneList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pKClasse != null ? pKClasse.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Classe)) {
            return false;
        }
        Classe other = (Classe) object;
        if ((this.pKClasse == null && other.pKClasse != null) || (this.pKClasse != null && !this.pKClasse.equals(other.pKClasse))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "app.beans.Classe[ pKClasse=" + pKClasse + " ]";
    }
    
}
