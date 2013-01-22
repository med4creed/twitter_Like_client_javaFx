package med4creed;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
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

import javax.ws.rs.core.MultivaluedMap;

import model.Groupe;
import model.Message;
import model.Utilisateur;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import taskAndService.TaskAndServiceGroupe;
import taskAndService.TaskAndServiceMessage;
import taskAndService.TaskAndServiceUtilisateur;
import viewAndControl.GroupeGUI;
import viewAndControl.MessageGUI;
import viewAndControl.UtilisateurGUI;
import clientJersey.ClientJersey;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class TwitterApp extends Application {

	ClientJersey client;
	String rootUri = "http://127.0.0.1:8080/twitterLikeTest-1.1-SNAPSHOT/resources";
	Utilisateur user;
	public static Utilisateur user1 = new Utilisateur();
	StackPane root;
	BorderPane bpLogin;
	BorderPane bpAccueil;
	Scene scene;

	MessageGUI msgGUI;
	UtilisateurGUI userGUI;
	GroupeGUI grpGUI;
	TaskAndServiceGroupe taskServGrp;
	TaskAndServiceMessage taskAndServMsg;
	TaskAndServiceUtilisateur taskServUser;

	BorderPane bpCentre;
	VBox vbTop;

	@Override
	public void start(Stage primaryStage) throws Exception {
		init(primaryStage);
		primaryStage.show();
	}

	private void init(Stage primaryStage) {
		primaryStage.setTitle("Welcome on twitter like!");
		root = new StackPane();
		scene = new Scene(root, 600, 600, Color.ROSYBROWN);
		primaryStage.setScene(scene);

		bpLogin = new BorderPane();
		bpLogin.setTop(login());
		bpLogin.setCenter(creerUnCompte());

		vbTop = new VBox();
		vbTop.getChildren().addAll(menuPrincipal(), toolBarPrincipal());
		bpCentre = new BorderPane();
		bpAccueil = new BorderPane();
		bpAccueil.setTop(vbTop);
		bpAccueil.setCenter(bpCentre);
		bpAccueil.setLeft(navBarPrincipale());

		taskServGrp = new TaskAndServiceGroupe(rootUri);
		taskAndServMsg = new TaskAndServiceMessage(rootUri);
		taskServUser = new TaskAndServiceUtilisateur(rootUri);

		root.getChildren().add(bpLogin);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Twitter Like");
		launch(args);
	}

	// Login
	GridPane gridpLogin = new GridPane();
	Label lblEmailLogin = new Label("Adresse électronique:");
	Label lblMdpLogin = new Label("Mot de passe:");
	TextField txtfEmailLogin = new TextField();
	PasswordField MdpLogin = new PasswordField();
	Button BtnLogin = new Button("Connexion");

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
					MultivaluedMap<String, String> params = new MultivaluedMapImpl();
					params.add("mail", txtfEmailLogin.getText());
					params.add("mdp", MdpLogin.getText());
					System.out.println(params.toString());
					final Service<ClientResponse> service = taskServUser
							.serviceAuthentification(params);
					service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
						public void handle(WorkerStateEvent success) {
							ClientResponse response = (ClientResponse) success
									.getSource().getValue();
							if (response.getStatus() == 200) {
								user = utilisateurFromJson(response
										.getEntity(String.class));
								msgGUI = new MessageGUI();
								userGUI = new UtilisateurGUI();
								grpGUI = new GroupeGUI();
								root.getChildren().clear();
								root.getChildren().add(bpAccueil);
								System.out.println(response.toString());
								System.out.println("vous etes connecté!...Mr "
										+ user.getPseudo());
								root.getChildren().clear();
								root.getChildren().add(bpAccueil);
							} else {
								System.out.println(response.toString());
							}
						}
					});

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

	// Inscription
	GridPane gridpCreationCpte = new GridPane();
	final TextField txtfNom = new TextField();
	final TextField txtfPnom = new TextField();
	final TextField txtfPseudo = new TextField();
	final TextField txtfMail1 = new TextField();
	final TextField txtfMail2 = new TextField();
	final PasswordField Mdp1 = new PasswordField();
	final PasswordField Mdp2 = new PasswordField();

	final Label lblNom = new Label("Nom:");
	final Label lblPnom = new Label("Prénom:");
	final Label lblPseudo = new Label("Pseudo:");
	final Label lblMail1 = new Label("E-mail:");
	final Label lblMail2 = new Label("Confirmation e-mail:");
	final Label lblMdp1 = new Label("Mot de passe:");
	final Label lblMdp2 = new Label("Confirmation mot de passe:");

	Button btnCreerCpte = new Button("Créer");
	Button btnAnnulerCpte = new Button("Annuler");

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
				ProgressIndicator pi = new ProgressIndicator();
				pi.setMaxSize(100, 100);
				pi.progressProperty().bind(task.progressProperty());
				bpLogin.setCenter(pi);
				task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
					public void handle(WorkerStateEvent success) {
						ClientResponse response = (ClientResponse) success
								.getSource().getValue();
						user = utilisateurFromJson(response
								.getEntity(String.class));
						msgGUI = new MessageGUI();
						userGUI = new UtilisateurGUI();
						grpGUI = new GroupeGUI();
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

	// Menu principal
	MenuBar menuBarPrincipal = new MenuBar();
	Menu menuFile = new Menu("File");
	Menu menuEdit = new Menu("Edit");
	Menu menuView = new Menu("View");
	Menu menuHelp = new Menu("Help");

	private MenuBar menuPrincipal() {

		MenuItem exit = new MenuItem("Exit");
		exit.setAccelerator(KeyCombination.keyCombination("Alt+F4"));
		exit.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				System.exit(0);
			}
		});
		menuFile.getItems().addAll(new SeparatorMenuItem(), exit);

		MenuItem aboute = new MenuItem("Aboute twitterLike!");
		menuHelp.getItems().addAll(aboute);

		menuBarPrincipal.getMenus().addAll(menuFile, menuEdit, menuView,
				menuHelp);
		return menuBarPrincipal;
	}

	// Tool bar principale
	HBox hboxToolBar = new HBox();
	Button btnAccueil = new Button("Accueil");
	Button btnMonEspace = new Button("Mon espace");
	Button btnDeconnex = new Button("Déconnexion");

	public HBox toolBarPrincipal() {
		btnAccueil.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				final javafx.concurrent.Service<ObservableList<Message>> service = taskAndServMsg
						.serviceGetAllMsgs();
				bpCentre.getChildren().clear();
				ProgressIndicator pi = new ProgressIndicator();
				pi.setMaxSize(60, 60);
				pi.progressProperty().bind(service.progressProperty());
				bpCentre.setCenter(pi);
				service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
					public void handle(WorkerStateEvent success) {
						final ObservableList<Message> listMsgs = (ObservableList<Message>) success
								.getSource().getValue();

						Platform.runLater(new Runnable() {
							public void run() {
								VBox vb = new VBox();
								for (Message msg : listMsgs) {
									msgGUI = new MessageGUI();
									vb.getChildren()
											.add(msgGUI.afficherMessages(
													user.getId(), msg, service));
								}

								ScrollPane sp = new ScrollPane();
								sp.setContent(vb);
								sp.setPrefWidth(400);
								sp.setMaxWidth(500);
								bpCentre.getChildren().clear();
								bpCentre.setCenter(sp);
							}
						});
					}
				});

			}
		});

		btnMonEspace.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				Platform.runLater(new Runnable() {
					public void run() {
						bpCentre.getChildren().clear();
						bpCentre.setTop(userGUI.infoUtilisateur(user));
						bpCentre.setCenter(userGUI.modifierMdp(user));
						bpCentre.setBottom(userGUI.supprimerCpte(user.getId()));
					}
				});
			}
		});
		btnDeconnex.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				logout();
			}
		});

		hboxToolBar.getChildren().clear();
		hboxToolBar.setPadding(new Insets(0, 0, 0, 0));
		hboxToolBar.setSpacing(5);
		hboxToolBar.setAlignment(Pos.CENTER_RIGHT);
		hboxToolBar.getChildren().addAll(btnAccueil, btnMonEspace, btnDeconnex);
		return hboxToolBar;
	}

	// Navbar principal
	Accordion accordionNavBar = new Accordion();
	Button bt1 = new Button("Mes coordonnées");
	Button bt2 = new Button("Modifier le mot de passe ");
	Button bt3 = new Button("Supprimer mon compte");
	Button bt4 = new Button("Poster un message");
	Button bt5 = new Button("Tous mes messages");
	Button bt51 = new Button("Messages à suivre");
	Button bt6 = new Button("Créer un groupe");
	Button bt7 = new Button("Mes groupes");
	Button bt8 = new Button("Trouver un groupe");
	Button bt9 = new Button("Utilisateurs à suivre");
	Button bt10 = new Button("Abonnements groupes");
	Button bt11 = new Button("Trouver un utilisateur");

	public Accordion navBarPrincipale() {
		VBox vbt1 = new VBox();

		bt1.setMaxWidth(Double.MAX_VALUE);
		bt2.setMaxWidth(Double.MAX_VALUE);
		bt3.setMaxWidth(Double.MAX_VALUE);

		bt1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {

				Platform.runLater(new Runnable() {
					public void run() {
						bpCentre.getChildren().clear();
						bpCentre.setCenter(userGUI.infoUtilisateur(user));
					}
				});
			}
		});

		bt2.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				bpCentre.getChildren().clear();
				bpCentre.setCenter(userGUI.modifierMdp(user));
			}
		});
		bt3.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				bpCentre.getChildren().clear();
				bpCentre.setCenter(userGUI.supprimerCpte(user.getId()));
			}
		});
		vbt1.getChildren().addAll(bt1, bt2, bt3);

		// Mes messages
		VBox vbt2 = new VBox();

		bt4.setMaxWidth(Double.MAX_VALUE);
		bt5.setMaxWidth(Double.MAX_VALUE);
		bt51.setMaxWidth(Double.MAX_VALUE);

		bt4.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				bpCentre.getChildren().clear();
				bpCentre.setCenter(msgGUI.posterMessage(user.getId(), 0, false));
			}
		});

		bt5.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				final javafx.concurrent.Service<ObservableList<Message>> service = taskAndServMsg
						.serviceGetAllMsgsOfUtilisateur(user.getId());
				bpCentre.getChildren().clear();
				ProgressIndicator pi = new ProgressIndicator();
				pi.setMaxSize(100, 100);
				pi.progressProperty().bind(service.progressProperty());
				bpCentre.setCenter(pi);
				service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
					private ObservableList<Message> listMsgs = FXCollections
							.observableArrayList();

					public void handle(WorkerStateEvent success) {
						listMsgs = (ObservableList<Message>) success
								.getSource().getValue();
						Platform.runLater(new Runnable() {
							public void run() {
								VBox vb = new VBox();
								for (Message msg : listMsgs) {
									System.out.println(msg);
									msgGUI = new MessageGUI();
									vb.getChildren()
											.add(msgGUI.afficherMessages(
													user.getId(), msg, service));

								}

								ScrollPane sp = new ScrollPane();
								sp.setContent(vb);
								sp.setPrefWidth(400);
								sp.setMaxWidth(500);
								bpCentre.getChildren().clear();
								bpCentre.setCenter(sp);
							}
						});
					}
				});
			}
		});

		bt51.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				final javafx.concurrent.Service<ObservableList<Message>> service = taskAndServMsg
						.serviceGetAllMsgsOfUtilisateursASuivre(user.getId());
				bpCentre.getChildren().clear();
				ProgressIndicator pi = new ProgressIndicator();
				pi.setMaxSize(100, 100);
				pi.progressProperty().bind(service.progressProperty());
				bpCentre.setCenter(pi);
				service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
					private ObservableList<Message> listMsgsASuivre = FXCollections
							.observableArrayList();

					public void handle(WorkerStateEvent success) {
						listMsgsASuivre = (ObservableList<Message>) success
								.getSource().getValue();
						Platform.runLater(new Runnable() {
							public void run() {
								VBox vb = new VBox();
								for (Message msg : listMsgsASuivre) {
									System.out.println(msg);
									msgGUI = new MessageGUI();
									vb.getChildren()
											.add(msgGUI.afficherMessages(
													user.getId(), msg, service));

								}

								ScrollPane sp = new ScrollPane();
								sp.setContent(vb);
								sp.setPrefWidth(400);
								sp.setMaxWidth(500);
								bpCentre.getChildren().clear();
								bpCentre.setCenter(sp);
							}
						});
					}
				});
			}
		});

		vbt2.getChildren().addAll(bt4, bt5, bt51);

		// Groupes
		VBox vbt3 = new VBox();

		bt6.setMaxWidth(Double.MAX_VALUE);
		bt7.setMaxWidth(Double.MAX_VALUE);
		bt8.setMaxWidth(Double.MAX_VALUE);

		bt6.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				bpCentre.getChildren().clear();
				bpCentre.setCenter(grpGUI.creerGroupe(user.getId()));
			}
		});

		bt7.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				final javafx.concurrent.Service<ObservableList<Groupe>> service = taskServGrp
						.serviceGetAllGroupesOfAdmin(user.getId());
				bpCentre.getChildren().clear();
				ProgressIndicator pi = new ProgressIndicator();
				pi.setMaxSize(100, 100);
				pi.progressProperty().bind(service.progressProperty());
				bpCentre.setCenter(pi);
				service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
					private ObservableList<Groupe> listGrpes = FXCollections
							.observableArrayList();

					public void handle(WorkerStateEvent success) {
						listGrpes = (ObservableList<Groupe>) success
								.getSource().getValue();

						Platform.runLater(new Runnable() {
							public void run() {
								grpGUI = new GroupeGUI();
								VBox vb = new VBox();
								for (Groupe grpe : listGrpes) {
									grpGUI = new GroupeGUI();
									vb.getChildren()
											.add(grpGUI.supprimerUnGrpe(
													user.getId(), grpe, service));
								}
								ScrollPane sp = new ScrollPane();
								sp.setContent(vb);
								sp.setPrefWidth(400);
								sp.setMaxWidth(500);
								bpCentre.getChildren().clear();
								bpCentre.setCenter(sp);
							}
						});
					}
				});
			}
		});

		bt8.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				final javafx.concurrent.Service<ObservableList<Groupe>> service = taskServGrp
						.serviceGetAllGrpes();
				bpCentre.getChildren().clear();
				ProgressIndicator pi = new ProgressIndicator();
				pi.setMaxSize(100, 100);
				pi.progressProperty().bind(service.progressProperty());
				bpCentre.setCenter(pi);
				service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
					private ObservableList<Groupe> listGrpes = FXCollections
							.observableArrayList();

					public void handle(WorkerStateEvent success) {
						listGrpes = (ObservableList<Groupe>) success
								.getSource().getValue();

						Platform.runLater(new Runnable() {
							public void run() {
								VBox vb = new VBox();
								for (Groupe g : listGrpes) {
									grpGUI = new GroupeGUI();
									Boolean isMemeber = false;
									if (g.getUserAdmin().getId() != user
											.getId()) {
										for (Utilisateur tm : g.getMembers()) {
											if (tm.getId() == user.getId())
												isMemeber = true;
										}
										if (!isMemeber) {
											vb.getChildren().add(
													grpGUI.rejoindreUnGroupe(
															user.getId(), g,
															service));
										}
									}
								}
								ScrollPane sp = new ScrollPane();
								sp.setContent(vb);
								sp.setPrefWidth(400);
								sp.setMaxWidth(500);
								bpCentre.getChildren().clear();
								bpCentre.setCenter(sp);
							}
						});
					}
				});
			}
		});

		vbt3.getChildren().addAll(bt6, bt7, bt8);
		// Mes abonnements
		VBox vbt4 = new VBox();

		bt9.setMaxWidth(Double.MAX_VALUE);
		bt10.setMaxWidth(Double.MAX_VALUE);
		bt9.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				final javafx.concurrent.Service<ObservableList<Utilisateur>> service = taskServUser
						.getAllUtilisateursASuivre(user.getId());
				bpCentre.getChildren().clear();
				ProgressIndicator pi = new ProgressIndicator();
				pi.setMaxSize(100, 100);
				pi.progressProperty().bind(service.progressProperty());
				bpCentre.setCenter(pi);
				service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
					private ObservableList<Utilisateur> listUtilis = FXCollections
							.observableArrayList();

					public void handle(WorkerStateEvent success) {
						listUtilis = (ObservableList<Utilisateur>) success
								.getSource().getValue();

						Platform.runLater(new Runnable() {
							public void run() {
								VBox vb = new VBox();
								for (Utilisateur userToFollow : listUtilis) {
									userGUI = new UtilisateurGUI();
									vb.getChildren().add(
											userGUI.utilisateurAsuivre(user,
													userToFollow, false,
													service));
								}
								ScrollPane sp = new ScrollPane();
								sp.setContent(vb);
								sp.setPrefWidth(400);
								sp.setMaxWidth(500);
								bpCentre.getChildren().clear();
								bpCentre.setCenter(sp);
							}
						});
					}
				});
			}
		});
		bt10.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				final javafx.concurrent.Service<ObservableList<Groupe>> service = taskServGrp
						.serviceGetAllGrpesOfUtilisateur(user.getId());
				bpCentre.getChildren().clear();
				ProgressIndicator pi = new ProgressIndicator();
				pi.setMaxSize(100, 100);
				pi.progressProperty().bind(service.progressProperty());
				bpCentre.setCenter(pi);
				service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
					private ObservableList<Groupe> listGrpes = FXCollections
							.observableArrayList();

					public void handle(WorkerStateEvent success) {
						listGrpes = (ObservableList<Groupe>) success
								.getSource().getValue();

						Platform.runLater(new Runnable() {
							public void run() {
								VBox vb = new VBox();
								for (Groupe grpe : listGrpes) {
									grpGUI = new GroupeGUI();
									vb.getChildren()
											.add(grpGUI.quitterUnGroupe(
													user.getId(), grpe, service));

								}
								ScrollPane sp = new ScrollPane();
								sp.setContent(vb);
								sp.setPrefWidth(400);
								sp.setMaxWidth(500);
								bpCentre.getChildren().clear();
								bpCentre.setCenter(sp);
							}
						});
					}
				});
			}
		});
		vbt4.getChildren().addAll(bt9, bt10);
		// Utilisateurs
		VBox vbt5 = new VBox();

		bt11.setMaxWidth(Double.MAX_VALUE);

		bt11.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				final javafx.concurrent.Service<ObservableList<Utilisateur>> service = taskServUser
						.serviceGetAllUtilisateurs();
				bpCentre.getChildren().clear();
				ProgressIndicator pi = new ProgressIndicator();
				pi.setMaxSize(100, 100);
				pi.progressProperty().bind(service.progressProperty());
				bpCentre.setCenter(pi);
				service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
					private ObservableList<Utilisateur> listUtilis1 = FXCollections
							.observableArrayList();

					public void handle(WorkerStateEvent success) {
						listUtilis1 = (ObservableList<Utilisateur>) success
								.getSource().getValue();
						Platform.runLater(new Runnable() {
							public void run() {
								VBox vb = new VBox();
								List<Long> listId = new ArrayList<Long>();
								if (user.getUserToFollow() != null) {
									for (Utilisateur u : user.getUserToFollow()) {
										listId.add(u.getId());

									}
								}
								for (Utilisateur userToFollow : listUtilis1) {
									if (userToFollow.getId() != user.getId()
											&& !listId.contains(userToFollow
													.getId())) {
										vb.getChildren().add(
												userGUI.utilisateurAsuivre(
														user, userToFollow,
														true, service));

									}
								}
								ScrollPane sp = new ScrollPane();
								sp.setContent(vb);
								sp.setPrefWidth(400);
								sp.setMaxWidth(500);
								bpCentre.getChildren().clear();
								bpCentre.setCenter(sp);
							}
						});
					}
				});
			}
		});

		vbt5.getChildren().addAll(bt11);

		TitledPane t1 = new TitledPane("Mon compte", vbt1);
		TitledPane t2 = new TitledPane("Mes messages", vbt2);
		TitledPane t3 = new TitledPane("Groupes", vbt3);
		TitledPane t5 = new TitledPane("Utilisateurs", vbt5);
		TitledPane t4 = new TitledPane("Mes abonnements", vbt4);

		accordionNavBar.getPanes().add(t1);
		accordionNavBar.getPanes().add(t2);
		accordionNavBar.getPanes().add(t3);
		accordionNavBar.getPanes().add(t5);
		accordionNavBar.getPanes().add(t4);
		return accordionNavBar;
	}

	// //////////////////////////////////////////////////////////////////////////////////
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

}
