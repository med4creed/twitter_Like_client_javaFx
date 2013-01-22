package viewAndControl;

import java.io.IOException;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import javax.ws.rs.core.MultivaluedMap;

import model.Utilisateur;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import taskAndService.TaskAndServiceUtilisateur;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class InscriptionGUI extends Parent{

	TaskAndServiceUtilisateur taskServUser;	
	Utilisateur user;
	StackPane root;
	BorderPane bpAccueil;
	
	TextField txtfNom;
	TextField txtfPnom;
	TextField txtfPseudo;
	TextField txtfMail1;
	TextField txtfMail2;
	PasswordField Mdp1;
	PasswordField Mdp2;

	Label lblNom;
	Label lblPnom;
	Label lblPseudo;
	Label lblMail1;
	Label lblMail2;
	Label lblMdp1;
	Label lblMdp2;

	Button btnCreerCpte;
	Button btnAnnulerCpte;
	
	GridPane gridpCreationCpte;

	public InscriptionGUI() {
		taskServUser = new TaskAndServiceUtilisateur(
				"http://127.0.0.1:8080/twitterLikeTest-1.1-SNAPSHOT/resources");

		gridpCreationCpte = new GridPane();
		txtfNom = new TextField();
		txtfPnom = new TextField();
		txtfPseudo = new TextField();
		txtfMail1 = new TextField();
		txtfMail2 = new TextField();
		Mdp1 = new PasswordField();
		Mdp2 = new PasswordField();

		lblNom = new Label("Nom:");
		lblPnom = new Label("Prénom:");
		lblPseudo = new Label("Pseudo:");
		lblMail1 = new Label("E-mail:");
		lblMail2 = new Label("Confirmation e-mail:");
		lblMdp1 = new Label("Mot de passe:");
		lblMdp2 = new Label("Confirmation mot de passe:");

		btnCreerCpte = new Button("Créer");
		btnAnnulerCpte = new Button("Annuler");
	}

	public GridPane creerUnCompte() {

		gridpCreationCpte.setPadding(new Insets(10, 10, 10, 10));
		gridpCreationCpte.setVgap(5);
		gridpCreationCpte.setHgap(5);

		ColumnConstraints column1 = new ColumnConstraints();
		column1.setHalignment(HPos.LEFT);
		gridpCreationCpte.getColumnConstraints().add(column1);

		ColumnConstraints column2 = new ColumnConstraints();
		column2.setHalignment(HPos.RIGHT);
		gridpCreationCpte.getColumnConstraints().add(column2);

		lblNom.setMinWidth(100);
		lblNom.setMaxWidth(100);
		txtfNom.setPrefColumnCount(200);
		txtfNom.setMinWidth(200);
		txtfNom.setMaxWidth(200);
		lblMail2.setWrapText(true);
		lblMdp2.setWrapText(true);

		btnCreerCpte.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				MultivaluedMap<String, String> params = new MultivaluedMapImpl();
				params.add("nom", txtfNom.getText());
				params.add("pnom", txtfPnom.getText());
				params.add("pseudo", txtfPseudo.getText());
				params.add("mail", txtfMail1.getText());
				params.add("mdp", Mdp1.getText());
				Task<ClientResponse> task = taskServUser.taskCreerCpte(params);
				task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
					public void handle(WorkerStateEvent success) {
						ClientResponse response = (ClientResponse) success
								.getSource().getValue();
						user = utilisateurFromJson(response
								.getEntity(String.class));
						root.getChildren().clear();
						root.getChildren().add(bpAccueil);
						System.out.println(response.toString());
					}
				});
			}
		});

		btnAnnulerCpte.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				txtfNom.clear();
				txtfPnom.clear();
				txtfPseudo.clear();
				txtfMail1.clear();
				txtfMail2.clear();
				Mdp1.clear();
				Mdp2.clear();
			}
		});

		gridpCreationCpte.getChildren().clear();

		gridpCreationCpte.add(lblNom, 0, 0);
		gridpCreationCpte.add(txtfNom, 1, 0);
		gridpCreationCpte.add(lblPnom, 0, 1);
		gridpCreationCpte.add(txtfPnom, 1, 1);
		gridpCreationCpte.add(lblPseudo, 0, 2);
		gridpCreationCpte.add(txtfPseudo, 1, 2);
		gridpCreationCpte.add(lblMail1, 0, 3);
		gridpCreationCpte.add(txtfMail1, 1, 3);
		gridpCreationCpte.add(lblMail2, 0, 4);
		gridpCreationCpte.add(txtfMail2, 1, 4);
		gridpCreationCpte.add(lblMdp1, 0, 5);
		gridpCreationCpte.add(Mdp1, 1, 5);
		gridpCreationCpte.add(lblMdp2, 0, 6);
		gridpCreationCpte.add(Mdp2, 1, 6);
		gridpCreationCpte.add(btnCreerCpte, 1, 7);
		gridpCreationCpte.add(btnAnnulerCpte, 0, 7);
		gridpCreationCpte.setAlignment(Pos.TOP_RIGHT);

		return gridpCreationCpte;
	}

	private Utilisateur utilisateurFromJson(String str) {
		Utilisateur Utilisateur = new Utilisateur();
		ObjectMapper mapper = new ObjectMapper();

		try {
			Utilisateur = mapper.readValue(str, Utilisateur.class);
		} catch (JsonParseException e1) {
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return Utilisateur;
	}

	public Utilisateur getUser() {
		return user;
	}

	public void setUser(Utilisateur user) {
		this.user = user;
	}

	public StackPane getRoot() {
		return root;
	}

	public void setRoot(StackPane root) {
		this.root = root;
	}

	public BorderPane getBpAccueil() {
		return bpAccueil;
	}

	public void setBpAccueil(BorderPane bpAccueil) {
		this.bpAccueil = bpAccueil;
	}
	
	

}
