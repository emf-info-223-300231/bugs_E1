package app.workers;

import app.beans.Application;
import app.beans.Bug;
import app.beans.Categorie;
import app.beans.Personne;
import app.beans.Priorite;
import app.exceptions.MyDBException;
import java.io.File;
import java.util.List;

/**
 * Interface qui définit les services métier nécessaires avec la base de données
 * liée.
 *
 * @author jcstritt
 */
public interface DbWorkerItf {

    // gestion des bugs
    List<Bug> lireBugs() throws MyDBException;
    long compterBugs() throws MyDBException;
    void ajouterBugs(Bug b) throws MyDBException;
    Bug lireBug(Bug b) throws MyDBException;
    void modifierBug(Bug b) throws MyDBException;
    void effacerBug(Bug b) throws MyDBException;
    
    // filtrer bugs
    List<Bug> filtrerParTitreComme(String valeur) throws MyDBException;
    List<Bug> filtrerParApplication(Object valeur) throws MyDBException;

    List<Application> lireApplications() throws MyDBException;
    List<Categorie> lireCategories() throws MyDBException;
    List<Priorite> lirePriorites() throws MyDBException;
    List<Personne> lirePersonnes() throws MyDBException;
    
    // autres
    void fermerBD();

    boolean estConnecte();
}
