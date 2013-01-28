package viewAndControl;

import java.io.IOException;
import java.net.URISyntaxException;

import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import javax.ws.rs.core.MultivaluedMap;

import taskAndService.TaskAndServiceUtilisateur;

import med4creed.TwitterApp;
import model.Utilisateur;

import clientJersey.ClientJersey;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class UtilisateurGUI extends Parent {
	TaskAndServiceUtilisateur taskServUser;
	AuthentificationGUI authenGUI;

	Label lblNom = new Label("Nom:");
	Label lblPnom = new Label("Prénom:");
	Label lblPseudo = new Label("Pseudo:");
	Label lblDateInscri = new Label("Date d'inscription:");
	Label lblMail = new Label("Email:");

	TextField txtfNom;
	TextField txtfPnom;
	TextField txtfPseudo;
	TextField txtfDateInscri;
	TextField txtfMail;

	GridPane gridpInfoUtilisateur;

	Button btnModifCpte;
	Button btnAnnulModifCpte;
	Button btnEnvoyerModifCpte;

	GridPane gridpModifMdp;
	Label lblAncMdp;
	Label lblNouvMdp;
	PasswordField ancienMdp;
	PasswordField nouvMdp;
	Button btnModifMdp;

	Label lblSuppCpte;
	Button btnSuppCpte;
	Button btnOui;
	Button btnNon;
	HBox hbSuppCpte;
	AnchorPane anchorSupCpte;

	GridPane gridpUtilisateurASuivre;
	Label lblPseudoAsuivre;
	Label lblDateInscriAsuivre;
	Label txtfPseudoAsuivre1;
	Label txtfDateInscriAsuivre1;

	GridPane gridpSuivre;
	Button btnSuivre;

	Button btnNePlusSuivre;

	public UtilisateurGUI() {
		taskServUser = new TaskAndServiceUtilisateur(
				"http://127.0.0.1:8080/twitterLikeTest-1.1-SNAPSHOT/resources");
	}

	// informations utilisateur
	public GridPane infoUtilisateur(final Utilisateur user) {
		txtfNom = new TextField(user.getNom());
		txtfPnom = new TextField(user.getPnom());
		txtfPseudo = new TextField(user.getPseudo());
		txtfDateInscri = new TextField(user.getDateInscription().toString());
		txtfMail = new TextField(user.getMail());

		gridpInfoUtilisateur = new GridPane();

		btnModifCpte = new Button("Modifier");
		btnAnnulModifCpte = new Button("Annuler");
		btnEnvoyerModifCpte = new Button("Envoyer");

		gridpInfoUtilisateur.setPadding(new Insets(10, 10, 10, 10));
		gridpInfoUtilisateur.setVgap(5);
		gridpInfoUtilisateur.setHgap(5);

		ColumnConstraints column1 = new ColumnConstraints();
		column1.setHalignment(HPos.LEFT);
		gridpInfoUtilisateur.getColumnConstraints().add(column1);
		
		ColumnConstraints column2 = new ColumnConstraints();
		column2.setHalignment(HPos.RIGHT);
		gridpInfoUtilisateur.getColumnConstraints().add(column2);

		txtfNom.setEditable(false);
		txtfPnom.setEditable(false);
		txtfPseudo.setEditable(false);
		txtfDateInscri.setEditable(false);
		txtfMail.setEditable(false);

		lblNom.setMinWidth(130);
		txtfNom.setPrefColumnCount(Integer.MAX_VALUE);

		btnAnnulModifCpte.setVisible(false);
		btnEnvoyerModifCpte.setVisible(false);
		final AnchorPane ap = new AnchorPane();
		ap.setVisible(false);
		
		btnModifCpte.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				txtfNom.setEditable(true);
				txtfPnom.setEditable(true);
				txtfPseudo.setEditable(true);
				txtfMail.setEditable(true);

				
				btnModifCpte.setVisible(false);
				ap.setVisible(true);
				btnEnvoyerModifCpte.setVisible(true);
				btnAnnulModifCpte.setVisible(true);
			}
		});
		btnEnvoyerModifCpte.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				MultivaluedMap<String, String> params = new MultivaluedMapImpl();
				params.add("nom", txtfNom.getText());
				params.add("pnom", txtfPnom.getText());
				params.add("pseudo", txtfPseudo.getText());
				params.add("mail", txtfMail.getText());
				taskServUser.taskModifierCompte(user.getId(), params);
				txtfNom.setEditable(false);
				txtfPnom.setEditable(false);
				txtfPseudo.setEditable(false);
				txtfMail.setEditable(false);
				
				ap.setVisible(false);
				btnEnvoyerModifCpte.setVisible(false);
				btnAnnulModifCpte.setVisible(false);
				btnModifCpte.setVisible(true);
			}
		});
		btnAnnulModifCpte.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				txtfNom.setEditable(false);
				txtfPnom.setEditable(false);
				txtfPseudo.setEditable(false);
				txtfMail.setEditable(false);

				ap.setVisible(false);
				btnEnvoyerModifCpte.setVisible(false);
				btnAnnulModifCpte.setVisible(false);
				btnModifCpte.setVisible(true);
			}
		});

		gridpInfoUtilisateur.getChildren().clear();
		
		ap.getChildren().addAll(btnEnvoyerModifCpte,
				btnAnnulModifCpte);
		AnchorPane.setLeftAnchor(btnEnvoyerModifCpte, 0.0);
		AnchorPane.setRightAnchor(btnAnnulModifCpte, 0.0);

		gridpInfoUtilisateur.add(lblNom, 0, 0);
		gridpInfoUtilisateur.add(txtfNom, 1, 0);
		gridpInfoUtilisateur.add(lblPnom, 0, 1);
		gridpInfoUtilisateur.add(txtfPnom, 1, 1);
		gridpInfoUtilisateur.add(lblPseudo, 0, 2);
		gridpInfoUtilisateur.add(txtfPseudo, 1, 2);
		gridpInfoUtilisateur.add(lblDateInscri, 0, 3);
		gridpInfoUtilisateur.add(txtfDateInscri, 1, 3);
		gridpInfoUtilisateur.add(lblMail, 0, 4);
		gridpInfoUtilisateur.add(txtfMail, 1, 4);
		gridpInfoUtilisateur.add(btnModifCpte, 1, 5);
		gridpInfoUtilisateur.add(ap, 1, 5);

		return gridpInfoUtilisateur;
	}

	// Modifier le mot de passe
	public GridPane modifierMdp(final Utilisateur user) {
		gridpModifMdp = new GridPane();
		lblAncMdp = new Label("Ancien mot de passe:");
		lblNouvMdp = new Label("Nouveau mot de passe:");
		ancienMdp = new PasswordField();
		nouvMdp = new PasswordField();
		btnModifMdp = new Button("Modifier mot de passe");

		gridpModifMdp.setPadding(new Insets(10, 10, 10, 10));
		gridpModifMdp.setVgap(5);
		gridpModifMdp.setHgap(5);

		ColumnConstraints column1 = new ColumnConstraints();
		column1.setHalignment(HPos.LEFT);
		gridpModifMdp.getColumnConstraints().add(column1);

		ColumnConstraints column2 = new ColumnConstraints();
		column2.setHalignment(HPos.RIGHT);
		gridpModifMdp.getColumnConstraints().add(column2);

		btnModifMdp.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				if (!nouvMdp.getText().isEmpty()
						&& !ancienMdp.getText().isEmpty()) {
					MultivaluedMap<String, String> params = new MultivaluedMapImpl();
					params.add("mdp", nouvMdp.getText());
					taskServUser.taskModifierMdp(user.getId(), params);
				} else {
					ancienMdp
							.setPromptText("Entrez votre ancien mot de passe!");
					nouvMdp.setPromptText("Entrez le nouveau mot de passe!");
				}
			}
		});

		lblAncMdp.setMinWidth(130);
		ancienMdp.setPromptText("Ancien mot de passe!");
		ancienMdp.setPrefColumnCount(Integer.MAX_VALUE);
		nouvMdp.setPromptText("Nouveau mot de passe!");

		gridpModifMdp.getChildren().clear();
		gridpModifMdp.add(lblAncMdp, 0, 0);
		gridpModifMdp.add(ancienMdp, 1, 0);
		gridpModifMdp.add(lblNouvMdp, 0, 1);
		gridpModifMdp.add(nouvMdp, 1, 1);
		gridpModifMdp.add(btnModifMdp, 1, 2);

		return gridpModifMdp;
	}

	// supprimer son compte
	public AnchorPane supprimerCpte(final long idUtilisateur,
			final StackPane root, final BorderPane bpLogin) {
		lblSuppCpte = new Label(
				"Etes vous sûr de vouloir supprimer votre compte?");
		btnSuppCpte = new Button("Supprimer mon compte");
		btnOui = new Button("Oui");
		btnNon = new Button("Non");
		hbSuppCpte = new HBox();
		anchorSupCpte = new AnchorPane();
		btnOui.setPrefWidth(50);
		btnNon.setPrefWidth(50);
		btnSuppCpte.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				btnSuppCpte.setVisible(false);
				hbSuppCpte.setVisible(true);
				lblSuppCpte.setVisible(true);
				btnOui.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						taskServUser.taskSupprimerCpte(idUtilisateur);
						root.getChildren().clear();
						root.getChildren().add(bpLogin);
					}
				});
				btnNon.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						btnSuppCpte.setVisible(true);
						hbSuppCpte.setVisible(false);
						lblSuppCpte.setVisible(false);
					}
				});
			}
		});

		hbSuppCpte.getChildren().clear();
		anchorSupCpte.getChildren().clear();
		hbSuppCpte.setPadding(new Insets(0, 0, 0, 0));
		hbSuppCpte.setSpacing(5);
		hbSuppCpte.getChildren().addAll(btnOui, btnNon);
		hbSuppCpte.setVisible(false);
		lblSuppCpte.setVisible(false);

		anchorSupCpte.getChildren()
				.addAll(lblSuppCpte, hbSuppCpte, btnSuppCpte);
		anchorSupCpte.setPadding(new Insets(10, 10, 10, 10));
		AnchorPane.setLeftAnchor(lblSuppCpte, 0.0);
		AnchorPane.setRightAnchor(hbSuppCpte, 0.0);
		AnchorPane.setRightAnchor(btnSuppCpte, 0.0);

		return anchorSupCpte;
	}

	// Info utilisateur à suivre
	public GridPane utilisateurAsuivre(final Utilisateur user,
			final Utilisateur userToFollow, final boolean suivre,
			final Service<ObservableList<Utilisateur>> service) {
		gridpUtilisateurASuivre = new GridPane();
		lblPseudoAsuivre = new Label("Pseudo:");
		lblDateInscriAsuivre = new Label("Date d'inscription:");

		btnSuivre = new Button("Suivre!");
		btnNePlusSuivre = new Button("Ne plus suivre!");

		gridpUtilisateurASuivre.setPadding(new Insets(10, 10, 10, 10));
		gridpUtilisateurASuivre.setVgap(5);
		gridpUtilisateurASuivre.setHgap(5);

		ColumnConstraints column1 = new ColumnConstraints();
		column1.setHalignment(HPos.LEFT);
		gridpUtilisateurASuivre.getColumnConstraints().add(column1);

		txtfPseudoAsuivre1 = new Label(userToFollow.getPseudo());
		txtfDateInscriAsuivre1 = new Label(userToFollow.getDateInscription()
				.toString());
		lblPseudoAsuivre.setMinWidth(130);

		btnSuivre.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				taskServUser.serviceSuivreUtilisateur(user.getId(),
						userToFollow.getId());
				service.restart();
			}
		});

		btnNePlusSuivre.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				taskServUser.serviceAnnulSuiviUtilisateur(user.getId(),
						userToFollow.getId());
				service.restart();
			}
		});
		gridpUtilisateurASuivre.getChildren().clear();
		gridpUtilisateurASuivre.add(lblPseudoAsuivre, 0, 0);
		gridpUtilisateurASuivre.add(txtfPseudoAsuivre1, 1, 0);
		gridpUtilisateurASuivre.add(lblDateInscriAsuivre, 0, 1);
		gridpUtilisateurASuivre.add(txtfDateInscriAsuivre1, 1, 1);
		if (suivre) {
			gridpUtilisateurASuivre.add(btnSuivre, 0, 2);
		} else {
			gridpUtilisateurASuivre.add(btnNePlusSuivre, 0, 2);
		}
		return gridpUtilisateurASuivre;
	}

}
