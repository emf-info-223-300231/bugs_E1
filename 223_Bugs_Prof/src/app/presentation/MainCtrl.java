package app.presentation;

import app.beans.Application;
import app.beans.Bug;
import app.beans.Categorie;
import app.beans.Personne;
import app.beans.Priorite;
import app.exceptions.MyDBException;
import app.helpers.DateTimeLib;
import app.helpers.JfxPopup;
import app.workers.DbWorker;
import app.workers.DbWorkerItf;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author PA
 */
public class MainCtrl implements Initializable {

    final static private String PU = "PU_MYSQL";

    private DbWorkerItf dbWrk;
    private boolean modeAjout;

    @FXML
    private Button btnSauver;
    @FXML
    private TextField txtTitre;
    @FXML
    private DatePicker dpkDateCreation;
    @FXML
    private ComboBox<Categorie> cbxCategorie;
    @FXML
    private ComboBox<Bug> cbxBugLie;
    @FXML
    private ComboBox<Application> cbxApplication;
    @FXML
    private ComboBox<Priorite> cbxPriorite;
    @FXML
    private ComboBox<Personne> cbxAuteur;
    @FXML
    private TextArea txaDescription;
    @FXML
    private ListView<Bug> lstBugs;
    @FXML
    private TextField txtTitreFiltre;
    @FXML
    private ComboBox<Application> cbxApplicationFiltre;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Evénement sur la liste de bugs
        lstBugs.getSelectionModel().selectedItemProperty().addListener((changed, oldVal, newVal) -> selectBug(newVal));

        // Créer le worker et connexion à la db intégrée
        try {
            dbWrk = new DbWorker();
        } catch (MyDBException ex) {
            JfxPopup.displayError("ERREUR", null, ex.getMessage());
            quitter();
        }

        if (dbWrk.estConnecte()) {
            try {
                lstBugs.getItems().setAll(dbWrk.lireBugs());
                cbxApplication.getItems().setAll(dbWrk.lireApplications());
                cbxApplicationFiltre.getItems().setAll(dbWrk.lireApplications());
                cbxAuteur.getItems().setAll(dbWrk.lirePersonnes());
                cbxPriorite.getItems().setAll(dbWrk.lirePriorites());
                cbxCategorie.getItems().setAll(dbWrk.lireCategories());
                cbxBugLie.getItems().setAll(dbWrk.lireBugs());
            } catch (MyDBException ex) {
                JfxPopup.displayError("ERREUR", null, ex.getMessage());
                quitter();
            }
        } else {
            JfxPopup.displayError("ERREUR", "Une erreur s'est produite", "");
            quitter();
        }
    }

    public void quitter() {
        dbWrk.fermerBD(); // ne pas oublier !!!
        Platform.exit();
    }

    @FXML
    private void menuAjouter(ActionEvent event) {
        modeAjout = true;
        lstBugs.setDisable(true);
        effaceChamps();
    }

    @FXML
    private void menuEffacer(ActionEvent event) {
        try {
            dbWrk.effacerBug(lstBugs.getSelectionModel().getSelectedItem());
            lstBugs.getItems().setAll(dbWrk.lireBugs());
        } catch (MyDBException ex) {
            JfxPopup.displayError("ERREUR", null, ex.getMessage());
        }
        effaceChamps();
    }

    @FXML
    private void menuQuitter(ActionEvent event) {
        quitter();
    }

    private void afficherBug(Bug b) {
        if (b != null) {
            txtTitre.setText(b.getTitre());
            txaDescription.setText(b.getDescription());
            dpkDateCreation.setValue(DateTimeLib.dateToLocalDate(b.getDateCreation()));
            cbxApplication.getSelectionModel().select(b.getFKApplication());
            cbxAuteur.getSelectionModel().select(b.getFKPersonneCree());
            cbxCategorie.getSelectionModel().select(b.getFKCategorie());
            cbxPriorite.getSelectionModel().select(b.getFKPriorite());
            cbxBugLie.getSelectionModel().select(b.getFKBugLie());
        }
    }

    private void selectBug(Bug newVal) {
        if (newVal != null) {
            afficherBug(newVal);
        }
    }

    private Bug lireBug(Bug b) {
        if (b == null) {
            b = new Bug();
        }
        b.setTitre(txtTitre.getText());
        b.setDescription(txaDescription.getText());
        b.setDateCreation(DateTimeLib.localDateToDate(dpkDateCreation.getValue()));
        b.setFKApplication(cbxApplication.getSelectionModel().getSelectedItem());
        b.setFKBugLie(cbxBugLie.getSelectionModel().getSelectedItem());
        b.setFKCategorie(cbxCategorie.getSelectionModel().getSelectedItem());
        b.setFKPersonneCree(cbxAuteur.getSelectionModel().getSelectedItem());
        b.setFKPriorite(cbxPriorite.getSelectionModel().getSelectedItem());
        return b;
    }

    private void effaceChamps() {
        txtTitre.setText("");
        txaDescription.setText("");
        dpkDateCreation.setValue(null);
        cbxApplication.getSelectionModel().select(null);
        cbxAuteur.getSelectionModel().select(null);
        cbxBugLie.getSelectionModel().select(null);
        cbxCategorie.getSelectionModel().select(null);
        cbxPriorite.getSelectionModel().select(null);
    }

    @FXML
    private void sauver(ActionEvent event) {
        try {
            if (modeAjout) {
                Bug b = lireBug(null);
                dbWrk.ajouterBugs(b);
                //lstBugs.getItems().setAll(dbWrk.lireBugs());

            } else {
                Bug b = lireBug(lstBugs.getSelectionModel().getSelectedItem());
                dbWrk.modifierBug(b);
            }
            // on remet à jour la liste (pour avoir les info de maj version et date)
            lstBugs.getItems().setAll(dbWrk.lireBugs());
        } catch (MyDBException ex) {
            JfxPopup.displayError("ERREUR", null, ex.getMessage());
        }
        effaceChamps();
        lstBugs.setDisable(false);
        modeAjout = false;

    }

    @FXML
    private void filtrerTitre(ActionEvent event) {
        try {
            List<Bug> listeFiltree = dbWrk.filtrerParTitreComme(txtTitreFiltre.getText());
            if (listeFiltree != null) {
                lstBugs.getItems().setAll(listeFiltree);
            } else {
                JfxPopup.displayError("Pas de valeur", "Aucun bug trouvé !", "Aucune bug avec le titre " + txtTitreFiltre.getText() + " n'existe dans la base de données.");
            }
        } catch (MyDBException ex) {
            JfxPopup.displayError("ERREUR", null, ex.getMessage());
        }
    }

    @FXML
    private void filtrerApplication(ActionEvent event) {
        try {
            List<Bug> listeFiltree = dbWrk.filtrerParApplication(cbxApplicationFiltre.getSelectionModel().getSelectedItem());
            if (listeFiltree != null) {
                lstBugs.getItems().setAll(listeFiltree);
            } else {
                JfxPopup.displayError("Pas de valeur", "Aucun bug trouvé !", "Aucune bug avec le nom d'application " + cbxApplicationFiltre.getSelectionModel().getSelectedItem() + " n'existe dans la base de données.");
            }
        } catch (MyDBException ex) {
            JfxPopup.displayError("ERREUR", null, ex.getMessage());
        }
    }

}
