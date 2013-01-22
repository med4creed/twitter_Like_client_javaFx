package med4creed;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.ws.rs.core.MultivaluedMap;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import clientJersey.ClientJersey;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Groupe;
import model.Message;
import model.Utilisateur;
import viewAndControl.AuthentificationGUI;
import viewAndControl.GroupeGUI;
import viewAndControl.InscriptionGUI;
import viewAndControl.InterfaceAccueil;
import viewAndControl.MessageGUI;
import viewAndControl.UtilisateurGUI;

public class twitterGUI extends Application {

	private ClientJersey client;
	Utilisateur user;
	StackPane root;
	Scene scene;
	BorderPane bpLogin;
	BorderPane bpAccueil;
	VBox vbTop;
	BorderPane bpCentre;

	MessageGUI messageGUI;
	UtilisateurGUI utilisateurGUI;
	GroupeGUI groupeGUI;
	AuthentificationGUI authenGUI;
	InscriptionGUI inscriGUI;
	InterfaceAccueil accueilGUI;

	@Override
	public void start(Stage primaryStage) throws Exception {
		init(primaryStage);
		primaryStage.show();
	}

	private void init(Stage primaryStage) {
		primaryStage.setTitle("Welcome on twitter like!");
		root = new StackPane();
		scene = new Scene(root, 600, 600, Color.DARKGREY);
		primaryStage.setScene(scene);
		inscriGUI = new InscriptionGUI();
		user = inscriGUI.getUser();
		authenGUI = new AuthentificationGUI();
		accueilGUI = new InterfaceAccueil(
				"http://127.0.0.1:8080/twitterLikeTest-1.1-SNAPSHOT/resources",
				user);
		bpLogin = new BorderPane();
		bpAccueil = new BorderPane();
		
		bpLogin.setTop(authenGUI.login());
		bpLogin.setCenter(inscriGUI.creerUnCompte());
		
		vbTop = new VBox();
		vbTop.getChildren().addAll(accueilGUI.menuPrincipal(),accueilGUI.toolBarPrincipal());
		bpCentre = new BorderPane();
		
		bpAccueil.setTop(vbTop);
		bpAccueil.setCenter(bpCentre);
		bpAccueil.setLeft(accueilGUI.navBarPrincipale());
		
		
		
		root.getChildren().add(bpLogin);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Twitter Like");
		launch(args);
	}
	
}
