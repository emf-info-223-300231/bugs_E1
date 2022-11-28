package app.workers;

import app.beans.Application;
import app.beans.Bug;
import app.beans.Categorie;
import app.beans.Personne;
import app.beans.Priorite;
import app.exceptions.MyDBException;
import app.workers.dao.JpaDao;
import app.workers.dao.JpaDaoItf;
import java.util.List;

/**
 * Couche "métier" gérant les accès de et vers la base de données.
 *
 * @author jcstritt
 */
public class DbWorker implements DbWorkerItf {

    private static final String JPA_PU = "PU_MYSQL";
    private final JpaDaoItf<Bug, Integer> bugWrk;
    private final JpaDaoItf<Application, Integer> applicationWrk;
    private final JpaDaoItf<Categorie, Integer> categorieWrk;
    private final JpaDaoItf<Priorite, Integer> prioriteWrk;
    private final JpaDaoItf<Personne, Integer> personneWrk;

    /**
     * Constructeur.
     *
     * @throws app.exceptions.MyDBException
     */
    public DbWorker() throws MyDBException {
        bugWrk = new JpaDao<>(JPA_PU, Bug.class);
        applicationWrk = new JpaDao<>(JPA_PU, Application.class);
        categorieWrk = new JpaDao<>(JPA_PU, Categorie.class);
        prioriteWrk = new JpaDao<>(JPA_PU, Priorite.class);
        personneWrk = new JpaDao<>(JPA_PU, Personne.class);
    }

    /*
   * AUTRES
     */
    @Override
    public void fermerBD() {
        bugWrk.deconnecter();
        applicationWrk.deconnecter();
        categorieWrk.deconnecter();
        prioriteWrk.deconnecter();
        personneWrk.deconnecter();
    }

    @Override
    public boolean estConnecte() {
        return bugWrk.estConnectee() && applicationWrk.estConnectee() && categorieWrk.estConnectee() && prioriteWrk.estConnectee() && personneWrk.estConnectee();
    }

    @Override
    public List<Bug> lireBugs() throws MyDBException {
        return bugWrk.lireListe();
    }

    @Override
    public long compterBugs() throws MyDBException {
        return bugWrk.compter();
    }

    @Override
    public void ajouterBugs(Bug b) throws MyDBException {
        bugWrk.creer(b);
    }

    @Override
    public Bug lireBug(Bug b) throws MyDBException {
        return bugWrk.lire(b.getPKBug());
    }

    @Override
    public void modifierBug(Bug b) throws MyDBException {
        bugWrk.modifier(b);
    }

    @Override
    public void effacerBug(Bug b) throws MyDBException {
        if (b != null) {
            bugWrk.effacer(b.getPKBug());
        }
    }

    @Override
    public List<Bug> filtrerParTitreComme(String valeur) throws MyDBException {
        return bugWrk.filtrerComme("titre", valeur);
    }

    @Override
    public List<Bug> filtrerParApplication(Object valeur) throws MyDBException {
        return bugWrk.filtrerObjet("fKApplication", valeur);
    }

    @Override
    public List<Application> lireApplications() throws MyDBException {
        return applicationWrk.lireListe();
    }

    @Override
    public List<Categorie> lireCategories() throws MyDBException {
        return categorieWrk.lireListe();
    }

    @Override
    public List<Priorite> lirePriorites() throws MyDBException {
        return prioriteWrk.lireListe();
    }

    @Override
    public List<Personne> lirePersonnes() throws MyDBException {
        return personneWrk.lireListe();
    }

}
