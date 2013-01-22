package viewAndControl;

import model.Utilisateur;
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

public class AuthentificationGUI {

	GridPane gridpLogin;
	Label lblEmailLogin;
	Label lblMdpLogin;
	TextField txtfEmailLogin;
	PasswordField MdpLogin;
	Button BtnLogin;
	Button btnLogout;
	StackPane root;
	BorderPane bpAccueil;
	BorderPane bpLogin;
	Utilisateur user;

	public AuthentificationGUI() {
		gridpLogin = new GridPane();
		lblEmailLogin = new Label("Adresse électronique:");
		lblMdpLogin = new Label("Mot de passe:");
		txtfEmailLogin = new TextField();
		MdpLogin = new PasswordField();
		BtnLogin = new Button("Connexion");
		btnLogout = new Button("Déconnexion");
	}

	public GridPane login() {

		gridpLogin.setPadding(new Insets(10, 10, 10, 10));
		gridpLogin.setVgap(5);
		gridpLogin.setHgap(5);

		ColumnConstraints column1 = new ColumnConstraints();
		column1.setHalignment(HPos.LEFT);
		gridpLogin.getColumnConstraints().add(column1);

		ColumnConstraints column2 = new ColumnConstraints();
		column2.setHalignment(HPos.RIGHT);
		gridpLogin.getColumnConstraints().add(column2);

		lblEmailLogin.setMinWidth(150);
		lblEmailLogin.setMaxWidth(150);

		lblMdpLogin.setMinWidth(150);
		lblMdpLogin.setMaxWidth(150);

		txtfEmailLogin.setPromptText("votre e-mail...");
		txtfEmailLogin.setPrefColumnCount(150);
		txtfEmailLogin.setMinWidth(150);
		txtfEmailLogin.setMaxWidth(150);

		MdpLogin.setPromptText("votre mot de passe...");
		MdpLogin.setMinWidth(150);
		MdpLogin.setMaxWidth(150);

		BtnLogin.setMinWidth(100);
		BtnLogin.setMaxWidth(100);
		BtnLogin.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				if (txtfEmailLogin.getText() != null
						&& !txtfEmailLogin.getText().isEmpty()
						&& MdpLogin.getText() != null
						&& !MdpLogin.getText().isEmpty()) {
					System.out.println("vous etes connecté!");
					root.getChildren().clear();
					root.getChildren().add(bpAccueil);
				} else {
					System.out.println("il faut remplir les champs svp!");
				}
			}
		});

		gridpLogin.getChildren().clear();

		gridpLogin.add(lblEmailLogin, 0, 0);
		gridpLogin.add(txtfEmailLogin, 0, 1);
		gridpLogin.add(lblMdpLogin, 1, 0);
		gridpLogin.add(MdpLogin, 1, 1);
		gridpLogin.add(BtnLogin, 3, 1);
		gridpLogin.setAlignment(Pos.CENTER_RIGHT);

		return gridpLogin;
	}

	public void logout() {
		System.out.println("vous etes déconnecté!");
		root.getChildren().clear();
		root.getChildren().add(bpLogin);
		user = null;
	}
}
