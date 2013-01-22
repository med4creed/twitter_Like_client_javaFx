package viewAndControl;

import taskAndService.TaskAndServiceGroupe;
import taskAndService.TaskAndServiceMessage;
import taskAndService.TaskAndServiceUtilisateur;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.Groupe;
import model.Message;
import model.Utilisateur;

public class InterfaceAccueil {
	TaskAndServiceGroupe taskServGrp;
	TaskAndServiceMessage taskAndServMsg;
	TaskAndServiceUtilisateur taskServUser;
	MessageGUI msgGUI;
	UtilisateurGUI userGUI;
	GroupeGUI grpGUI;
	InscriptionGUI inscripGUI;
	AuthentificationGUI authenGUI;
	Utilisateur user;
	StackPane root;
	BorderPane bpAccueil;
	BorderPane bpLogin;

	BorderPane borderpCentre;

	public InterfaceAccueil(String rootUri, Utilisateur user) {
		this.taskServGrp = new TaskAndServiceGroupe(rootUri);
		this.taskAndServMsg = new TaskAndServiceMessage(rootUri);
		this.taskServUser = new TaskAndServiceUtilisateur(rootUri);
		this.msgGUI = new MessageGUI();
		this.userGUI = new UtilisateurGUI();
		this.grpGUI = new GroupeGUI();
		this.inscripGUI = new InscriptionGUI();
		this.authenGUI = new AuthentificationGUI();
		this.user = user;
	}

	// Menu principal
	MenuBar menuBarPrincipal = new MenuBar();
	Menu menuFile = new Menu("File");
	Menu menuEdit = new Menu("Edit");
	Menu menuView = new Menu("View");
	Menu menuHelp = new Menu("Help");

	public MenuBar menuPrincipal() {
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
				borderpCentre.getChildren().clear();
				ProgressIndicator pi = new ProgressIndicator();
				pi.setMaxSize(60, 60);
				pi.progressProperty().bind(service.progressProperty());
				borderpCentre.setCenter(pi);
				service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
					public void handle(WorkerStateEvent success) {
						final ObservableList<Message> listMsgs = (ObservableList<Message>) success
								.getSource().getValue();

						Platform.runLater(new Runnable() {
							public void run() {
								VBox vb = new VBox();
								for (Message msg : listMsgs) {
									vb.getChildren()
											.add(msgGUI.afficherMessages(
													user.getId(), msg, service));
								}

								ScrollPane sp = new ScrollPane();
								sp.setContent(vb);
								sp.setPrefWidth(400);
								sp.setMaxWidth(500);
								borderpCentre.getChildren().clear();
								borderpCentre.setCenter(sp);
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
						borderpCentre.getChildren().clear();
						borderpCentre.setTop(userGUI.infoUtilisateur(user));
						borderpCentre.setCenter(userGUI.modifierMdp(user));
						borderpCentre.setBottom(userGUI.supprimerCpte(user
								.getId()));
					}
				});
			}
		});
		btnDeconnex.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				authenGUI.logout();
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
						borderpCentre.getChildren().clear();
						borderpCentre.setCenter(userGUI.infoUtilisateur(user));
					}
				});
			}
		});

		bt2.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				borderpCentre.getChildren().clear();
				borderpCentre.setCenter(userGUI.modifierMdp(user));
			}
		});
		bt3.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				borderpCentre.getChildren().clear();
				borderpCentre.setCenter(userGUI.supprimerCpte(user.getId()));
			}
		});
		vbt1.getChildren().addAll(bt1, bt2, bt3);

		// Mes messages
		VBox vbt2 = new VBox();

		bt4.setMaxWidth(Double.MAX_VALUE);
		bt5.setMaxWidth(Double.MAX_VALUE);

		bt4.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				borderpCentre.getChildren().clear();
				borderpCentre.setCenter(msgGUI.posterMessage(user.getId(), 0, false));
			}
		});

		bt5.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				final javafx.concurrent.Service<ObservableList<Message>> service = taskAndServMsg
						.serviceGetAllMsgsOfUtilisateur(user.getId());
				borderpCentre.getChildren().clear();
				ProgressIndicator pi = new ProgressIndicator();
				pi.setMaxSize(100, 100);
				pi.progressProperty().bind(service.progressProperty());
				borderpCentre.setCenter(pi);
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
									vb.getChildren()
											.add(msgGUI.afficherMessages(
													user.getId(), msg, service));

								}
								ScrollPane sp = new ScrollPane();
								sp.setContent(vb);
								sp.setPrefWidth(400);
								sp.setMaxWidth(500);
								borderpCentre.getChildren().clear();
								borderpCentre.setCenter(sp);
							}
						});
					}
				});
			}
		});

		vbt2.getChildren().addAll(bt4, bt5);

		// Groupes
		VBox vbt3 = new VBox();

		bt6.setMaxWidth(Double.MAX_VALUE);
		bt7.setMaxWidth(Double.MAX_VALUE);
		bt8.setMaxWidth(Double.MAX_VALUE);

		bt6.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				borderpCentre.getChildren().clear();
				borderpCentre.setCenter(grpGUI.creerGroupe(user.getId()));
			}
		});

		bt7.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				final javafx.concurrent.Service<ObservableList<Groupe>> service = taskServGrp
						.serviceGetAllGroupesOfAdmin(user.getId());
				borderpCentre.getChildren().clear();
				ProgressIndicator pi = new ProgressIndicator();
				pi.setMaxSize(100, 100);
				pi.progressProperty().bind(service.progressProperty());
				borderpCentre.setCenter(pi);
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
									vb.getChildren()
											.add(grpGUI.supprimerUnGrpe(
													user.getId(), grpe, service));
								}
								ScrollPane sp = new ScrollPane();
								sp.setContent(vb);
								sp.setPrefWidth(400);
								sp.setMaxWidth(500);
								borderpCentre.getChildren().clear();
								borderpCentre.setCenter(sp);
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
				borderpCentre.getChildren().clear();
				ProgressIndicator pi = new ProgressIndicator();
				pi.setMaxSize(100, 100);
				pi.progressProperty().bind(service.progressProperty());
				borderpCentre.setCenter(pi);
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
								borderpCentre.getChildren().clear();
								borderpCentre.setCenter(sp);
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
				borderpCentre.getChildren().clear();
				ProgressIndicator pi = new ProgressIndicator();
				pi.setMaxSize(100, 100);
				pi.progressProperty().bind(service.progressProperty());
				borderpCentre.setCenter(pi);
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
//									vb.getChildren()
//											.add(userGUI
//													.utilisateurAsuivre(userToFollow));
								}
								ScrollPane sp = new ScrollPane();
								sp.setContent(vb);
								sp.setPrefWidth(400);
								sp.setMaxWidth(500);
								borderpCentre.getChildren().clear();
								borderpCentre.setCenter(sp);
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
				borderpCentre.getChildren().clear();
				ProgressIndicator pi = new ProgressIndicator();
				pi.setMaxSize(100, 100);
				pi.progressProperty().bind(service.progressProperty());
				borderpCentre.setCenter(pi);
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
									vb.getChildren()
											.add(grpGUI.quitterUnGroupe(
													user.getId(), grpe, service));

								}
								ScrollPane sp = new ScrollPane();
								sp.setContent(vb);
								sp.setPrefWidth(400);
								sp.setMaxWidth(500);
								borderpCentre.getChildren().clear();
								borderpCentre.setCenter(sp);
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
				borderpCentre.getChildren().clear();
				ProgressIndicator pi = new ProgressIndicator();
				pi.setMaxSize(100, 100);
				pi.progressProperty().bind(service.progressProperty());
				borderpCentre.setCenter(pi);
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
//									vb.getChildren()
//											.add(userGUI
//													.utilisateurAsuivre(userToFollow));

								}

								ScrollPane sp = new ScrollPane();
								sp.setContent(vb);
								sp.setPrefWidth(400);
								sp.setMaxWidth(500);
								borderpCentre.getChildren().clear();
								borderpCentre.setCenter(sp);
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
}
