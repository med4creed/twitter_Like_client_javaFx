package viewAndControl;

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
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.ws.rs.core.MultivaluedMap;

import taskAndService.TaskAndServiceGroupe;
import taskAndService.TaskAndServiceMessage;

import model.Groupe;
import model.Message;
import model.Utilisateur;

import clientJersey.ClientJersey;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class GroupeGUI extends Parent {
	TaskAndServiceGroupe taskServGrp;

	public GroupeGUI() {
		taskServGrp = new TaskAndServiceGroupe(
				"http://127.0.0.1:8080/twitterLikeTest-1.1-SNAPSHOT/resources");
	}

	// Créer un nouveau groupe
	Button btnCreerGrpe = new Button("Créer");
	Button btnAnnulCreaGrpe = new Button("Annuler");
	Label lblNomGrpe = new Label("Nom du groupe:");
	TextField txtfNomGrpe = new TextField();
	GridPane gridpCreationGrpe = new GridPane();

	public GridPane creerGroupe(final long idUtil) {

		gridpCreationGrpe.setPadding(new Insets(10, 10, 10, 10));
		gridpCreationGrpe.setVgap(5);
		gridpCreationGrpe.setHgap(5);

		ColumnConstraints column1 = new ColumnConstraints();
		column1.setHalignment(HPos.LEFT);
		gridpCreationGrpe.getColumnConstraints().add(column1);

		ColumnConstraints column2 = new ColumnConstraints();
		column2.setHalignment(HPos.RIGHT);
		gridpCreationGrpe.getColumnConstraints().add(column2);
		
		
		lblNomGrpe.setMinWidth(100);

		txtfNomGrpe.setPrefColumnCount(Integer.MAX_VALUE);

		btnCreerGrpe.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				if ((txtfNomGrpe.getText() != null && !txtfNomGrpe.getText()
						.isEmpty())) {
					MultivaluedMap<String, String> params = new MultivaluedMapImpl();
					String nomGrpe = txtfNomGrpe.getText();
					params.add("nomGroupe", nomGrpe);
					txtfNomGrpe.clear();
					taskServGrp.taskCreerGrpe(idUtil, params);
				} else {
					txtfNomGrpe.setPromptText("Donnez un nom à votre groupe!");
				}
			}
		});

		btnAnnulCreaGrpe.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				txtfNomGrpe.clear();
			}
		});
		gridpCreationGrpe.getChildren().clear();
		gridpCreationGrpe.add(lblNomGrpe, 0, 0);
		gridpCreationGrpe.add(txtfNomGrpe, 1, 0);
		gridpCreationGrpe.add(btnAnnulCreaGrpe, 0, 1);
		gridpCreationGrpe.add(btnCreerGrpe, 1, 1);

		return gridpCreationGrpe;
	}

	// information du groupe
	// final Label lblNomGrpe = new Label("Nom du groupe:");
	Label lblDateCreation = new Label("Date de création:");
	Label lblAdminGrpe = new Label("Admin groupe:");
	Label lblNomGrpe1;
	Label lblDateCreation1;
	Label lblAdminGrpe1;
	GridPane gridpInfoGrpe = new GridPane();

	public GridPane infoGroupe(Groupe g) {
		final Groupe grpe = g;

		gridpInfoGrpe.setPadding(new Insets(10, 10, 10, 10));
		gridpInfoGrpe.setVgap(5);
		gridpInfoGrpe.setHgap(5);

		ColumnConstraints column1 = new ColumnConstraints();
		column1.setHalignment(HPos.LEFT);
		gridpInfoGrpe.getColumnConstraints().add(column1);

		ColumnConstraints column2 = new ColumnConstraints();
		column2.setHalignment(HPos.RIGHT);
		gridpInfoGrpe.getColumnConstraints().add(column2);
		
		gridpInfoGrpe.setMaxWidth(500);
		gridpInfoGrpe.setPrefWidth(500);
		gridpInfoGrpe.setMinWidth(500);
		
		lblNomGrpe.setMinWidth(130);

		lblNomGrpe1 = new Label(grpe.getNomGroupe());
		lblDateCreation1 = new Label(grpe.getDateCreation().toString());
		lblAdminGrpe1 = new Label(grpe.getUserAdmin().getPseudo());
		txtfNomGrpe.setPrefColumnCount(Integer.MAX_VALUE);
		txtfNomGrpe.setEditable(false);

		// liste des abonnés d'un groupe
		TitledPane tpMembres = new TitledPane();
		tpMembres.setText("Membres");
		final ListView<String> listView = new ListView<String>();
		for (Utilisateur tm : grpe.getMembers()) {
			listView.getItems().add(tm.getPseudo());
		}
		listView.setMaxHeight(listView.getItems().size() * 25);
		tpMembres.setContent(listView);
		tpMembres.setExpanded(false);
		tpMembres.setAnimated(false);
		gridpInfoGrpe.getChildren().clear();
		gridpInfoGrpe.add(lblNomGrpe, 0, 0);
		gridpInfoGrpe.add(lblNomGrpe1, 1, 0);
		gridpInfoGrpe.add(lblDateCreation, 0, 1);
		gridpInfoGrpe.add(lblDateCreation1, 1, 1);
		gridpInfoGrpe.add(lblAdminGrpe, 0, 2);
		gridpInfoGrpe.add(lblAdminGrpe1, 1, 2);
		gridpInfoGrpe.add(tpMembres, 1, 3);

		return gridpInfoGrpe;
	}

	// Supprimer un groupe
	GridPane gridpSuppGrpe;
	Label lblSuppGrpe = new Label(
			"Etes vous sûr de vouloir supprimer votre groupe?");

	Button btnSuppGrpe = new Button("Supprimer le groupe");
	Button btnOui = new Button("Oui");
	Button btnNon = new Button("Non");

	HBox hbSuppGrpe = new HBox();
	AnchorPane anchorSupGrpe = new AnchorPane();

	public GridPane supprimerUnGrpe(long idUtil, Groupe g,
			Service<ObservableList<Groupe>> s) {
		final Service<ObservableList<Groupe>> service = s;
		final Groupe grpe = g;

		btnOui.setPrefWidth(50);
		btnNon.setPrefWidth(50);

		anchorSupGrpe.setPadding(new Insets(10, 0, 10, 10));
		AnchorPane.setLeftAnchor(lblSuppGrpe, 0.0);
		AnchorPane.setRightAnchor(hbSuppGrpe, 0.0);
		anchorSupGrpe.setVisible(false);

		if (g.getUserAdmin().getId() != idUtil)
			btnSuppGrpe.setVisible(false);
		btnSuppGrpe.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				btnSuppGrpe.setVisible(false);
				anchorSupGrpe.setVisible(true);

				btnOui.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						anchorSupGrpe.setVisible(false);
						taskServGrp.taskSuppGrpe(grpe.getId());
						service.restart();
					}
				});
				btnNon.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						anchorSupGrpe.setVisible(false);
						btnSuppGrpe.setVisible(true);
					}
				});
			}
		});

		hbSuppGrpe.getChildren().clear();
		anchorSupGrpe.getChildren().clear();
		hbSuppGrpe.getChildren().addAll(btnOui, btnNon);
		anchorSupGrpe.getChildren().addAll(lblSuppGrpe, hbSuppGrpe);
		gridpSuppGrpe = infoGroupe(grpe);
		gridpSuppGrpe.add(btnSuppGrpe, 0, 3);
		gridpSuppGrpe.add(anchorSupGrpe, 0, 4, 2, 1);

		return gridpSuppGrpe;
	}

	// rejoindre un groupe
	GridPane gridpRejoinGrpe;
	Button btnRejoindre = new Button("Rejoindre");

	public GridPane rejoindreUnGroupe(final long idUtil, Groupe g,
			Service<ObservableList<Groupe>> s) {
		final Service<ObservableList<Groupe>> service = s;
		final Groupe grpe = g;

		btnRejoindre.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				taskServGrp.taskRejoindreGrpe(idUtil, grpe.getId());
				service.restart();

			}
		});

		btnRejoindre.setAlignment(Pos.TOP_LEFT);
		gridpRejoinGrpe = infoGroupe(grpe);
		gridpRejoinGrpe.add(btnRejoindre, 0, 3);

		return gridpRejoinGrpe;
	}

	// Quitter un groupe
	GridPane gridpQuitterGrpe;

	Button btnQuitter = new Button("Quitter!");
	ToggleButton btnMsgGrp = new ToggleButton("Actualités!");

	public GridPane quitterUnGroupe(final long idUtil, Groupe g,
			Service<ObservableList<Groupe>> s) {
		final Service<ObservableList<Groupe>> service = s;
		final Groupe grpe = g;

		btnQuitter.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {

				taskServGrp.taskQuitterGrpe(idUtil, grpe.getId());
				service.restart();
			}
		});

		btnMsgGrp.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				TaskAndServiceMessage taskAndSeidGrp = new TaskAndServiceMessage(
						"http://127.0.0.1:8080/twitterLikeTest-1.1-SNAPSHOT/resources");
				final javafx.concurrent.Service<ObservableList<Message>> service = taskAndSeidGrp
						.serviceGetAllMsgsOfGroupe(grpe.getId());

				service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
					private ObservableList<Message> listMsgs = FXCollections
							.observableArrayList();

					public void handle(WorkerStateEvent success) {
						listMsgs = (ObservableList<Message>) success
								.getSource().getValue();
						Platform.runLater(new Runnable() {
							public void run() {
								if (btnMsgGrp.isSelected()) {
									VBox vb = new VBox();

									MessageGUI msgGUI1 = new MessageGUI();
									GridPane gb = msgGUI1.posterMessage(idUtil,
											grpe.getId(), true);

									vb.getChildren().add(gb);
									vb.setMaxWidth(380);
									for (Message msg : listMsgs) {
										MessageGUI msgGUI = new MessageGUI();
										vb.getChildren().add(
												msgGUI.afficherMessages(idUtil,
														msg, service));

									}

									ScrollPane sp = new ScrollPane();
									sp.setContent(vb);
									
									gb.setMaxWidth(380);
									sp.setMaxWidth(Double.MAX_VALUE);
									gridpQuitterGrpe.setMaxWidth(Double.MAX_VALUE);
									
									System.out.println(gridpQuitterGrpe.getWidth());
									btnQuitter.setAlignment(Pos.TOP_LEFT);
									gridpQuitterGrpe = infoGroupe(grpe);
									gridpQuitterGrpe.add(btnQuitter, 0, 3);
									gridpQuitterGrpe.add(btnMsgGrp, 0, 4);
									gridpQuitterGrpe.add(sp, 0, 5, 2, 10);
								} else {
									gridpQuitterGrpe.getChildren().clear();
									btnQuitter.setAlignment(Pos.TOP_LEFT);
									gridpQuitterGrpe = infoGroupe(grpe);
									gridpQuitterGrpe.add(btnQuitter, 0, 3);
									gridpQuitterGrpe.add(btnMsgGrp, 0, 4);
								}
							}
						});
					}
				});

			}
		});

		btnQuitter.setAlignment(Pos.TOP_LEFT);
		gridpQuitterGrpe = infoGroupe(grpe);
		gridpQuitterGrpe.add(btnQuitter, 0, 3);
		gridpQuitterGrpe.add(btnMsgGrp, 0, 4);

		return gridpQuitterGrpe;
	}

}
