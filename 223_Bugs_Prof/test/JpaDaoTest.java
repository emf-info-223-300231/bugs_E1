
import app.beans.Application;
import app.beans.Bug;
import app.beans.Categorie;
import app.beans.Personne;
import app.beans.Priorite;
import app.exceptions.MyDBException;
import app.helpers.DateTimeLib;
import app.helpers.SystemLib;
import app.workers.dao.JpaDao;
import java.math.BigDecimal;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.junit.Assert.assertTrue;
import app.workers.dao.JpaDaoItf;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Test des principales fonctionnalités de la couche DAO sur une base de données
 * MySql. Les données pour monter la base se trouvent dans le dossier "data" de
 * ce projet.
 *
 * @author Mario Ramalho
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JpaDaoTest {

    private static final String JPA_PU = "PU_MYSQL";

    private static JpaDaoItf<Bug, Integer> bugWrk;
    private static JpaDaoItf<Application, Integer> applicationWrk;
    private static JpaDaoItf<Categorie, Integer> categorieWrk;
    private static JpaDaoItf<Priorite, Integer> prioriteWrk;
    private static JpaDaoItf<Personne, Integer> personneWrk;

    private static int lastPK = -1;


    /*
   * SETUP ET TEARDOWN (METHODES AVANT ET APRES LES TESTS)
     */
    @BeforeClass
    public static void setUpClass() throws Exception {
        // ouvre les sous-workers de la couche dao
        bugWrk = new JpaDao<>(JPA_PU, Bug.class);
        applicationWrk = new JpaDao<>(JPA_PU, Application.class);
        categorieWrk = new JpaDao<>(JPA_PU, Categorie.class);
        prioriteWrk = new JpaDao<>(JPA_PU, Priorite.class);
        personneWrk = new JpaDao<>(JPA_PU, Personne.class);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /*
   * TESTS
     */
    @Test
    public void a_testerEstConnectee() throws MyDBException {
        System.out.println("*** " + SystemLib.getCurrentMethod() + " ...");
        boolean ok = bugWrk.estConnectee();
        if (ok) {
            System.out.println("  OK, connecté !");
        } else {
            System.out.println("  KO, pas connecté !");
        }
        assertTrue(ok);
    }

    @Test
    public void b_testerCreer() throws MyDBException {
        System.out.println("*** " + SystemLib.getCurrentMethod() + " ...");
        Application ap = new Application();
        ap.setNom("Mon application");
        applicationWrk.creer(ap);
        Bug b = new Bug();
        b.setTitre("Bug de test");
        b.setDescription("Bug de test description");
        b.setDateCreation(new Date());
        b.setFKApplication(ap);
        bugWrk.creer(b);
        lastPK = b.getPKBug();

    }

    @Test
    public void c_testerLire() throws MyDBException {
        System.out.println("*** " + SystemLib.getCurrentMethod() + " ...");
        boolean ok = lastPK >= 0;
        if (ok) {
            Bug b = bugWrk.lire(lastPK);
            ok = b != null;
            System.out.println("  " + b);
        }
        assertTrue(ok);
    }

    @Test
    public void d_testerModifier() throws MyDBException {
        System.out.println("*** " + SystemLib.getCurrentMethod() + " ...");
        String TITRE_TEST = "Nouveau titre";
        boolean ok = lastPK >= 0;
        if (ok) {
            Bug b1 = bugWrk.lire(lastPK);
            String savedTitre = b1.getTitre();
            b1.setTitre(TITRE_TEST);
            bugWrk.modifier(b1);
            System.out.println("  " + b1);

            Bug b2 = bugWrk.lire(b1.getPKBug());
            ok = (b2 != null) && b2.getTitre().equals(TITRE_TEST);
            if (ok) {
                b1.setTitre(savedTitre);
                bugWrk.modifier(b1);
            }
            System.out.println("  " + b1);

        }
        assertTrue(ok);
    }

    @Test
    public void e_testerCompter() throws MyDBException {
        System.out.println("*** " + SystemLib.getCurrentMethod() + " ...");
        long n = bugWrk.compter();
        System.out.println("  n = " + n);
        boolean ok = n > 0;
        assertTrue(ok);
    }

    @Test
    public void f_testerfiltrerComme() throws MyDBException {
        System.out.println("*** " + SystemLib.getCurrentMethod() + " ...");
        List<Bug> bAll = bugWrk.lireListe();
        List<Bug> bFiltree = bugWrk.filtrerComme("titre", "test");
        System.out.println("Filtrer le titre avec le texte : \"test\"");
        boolean ok = bFiltree != null && !bFiltree.isEmpty() && bFiltree.size() <= bAll.size();
        if (ok) {
            System.out.println(" Nombre d'élément total : " + bAll.size());
            System.out.println(" Nombre d'élément filtré : " + bFiltree.size());
        }
        assertTrue(ok);
    }

    @Test
    public void g_testerfiltrerObjet() throws MyDBException {
        System.out.println("*** " + SystemLib.getCurrentMethod() + " ...");
        List<Bug> bAll = bugWrk.lireListe();
        Application applicationTest = applicationWrk.lireListe().get(0);
        List<Bug> bFiltree = bugWrk.filtrerObjet("fKApplication", applicationTest);
        System.out.println("Filtrer avec l'application : \""+applicationTest+"\"");
        boolean ok = bFiltree != null && !bFiltree.isEmpty() && bFiltree.size() <= bAll.size();
        if (ok) {
            System.out.println(" Nombre d'élément total : " + bAll.size());
            System.out.println(" Nombre d'élément filtré : " + bFiltree.size());
        }
        assertTrue(ok);
    }

    @Test
    public void h_testerEffacer() throws MyDBException {
        System.out.println("*** " + SystemLib.getCurrentMethod() + " ...");
        if (lastPK >= 0) {
            Bug b = bugWrk.lire(lastPK);
            System.out.println("  " + b);
            bugWrk.effacer(b.getPKBug());

        }

    }

    @Test
    public void z_testerDeconnecter() throws MyDBException {
        System.out.println("*** " + SystemLib.getCurrentMethod() + " ...");
        bugWrk.deconnecter();
        applicationWrk.deconnecter();
        prioriteWrk.deconnecter();
        categorieWrk.deconnecter();
        personneWrk.deconnecter();
    }

}
