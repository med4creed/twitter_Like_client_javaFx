package viewAndControl;

import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import javax.ws.rs.core.MultivaluedMap;

import taskAndService.TaskAndServiceMessage;

import model.Message;

import clientJersey.ClientJersey;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class MessageGUI extends Parent {
	TaskAndServiceMessage taskAndServMsg;

	public MessageGUI() {
		taskAndServMsg = new TaskAndServiceMessage(
				"http://127.0.0.1:8080/twitterLikeTest-1.1-SNAPSHOT/resources");
	}

	// Poster d'un message
	TextArea txtaMsg = new TextArea();
	Button btnPoster = new Button("Publier");
	Button btnAnnulPost = new Button("Clear");
	AnchorPane anchorPostMsg = new AnchorPane();
	GridPane gridpPostMsg = new GridPane();

	public GridPane posterMessage(final long idUtil, final long idGrp,final boolean isGrpMsg) {

		gridpPostMsg.setPadding(new Insets(10, 10, 10, 10));
		gridpPostMsg.setVgap(5);
		gridpPostMsg.setHgap(5);

		txtaMsg.setPromptText("Quoi de neuf!!!");
		txtaMsg.setPrefColumnCount(Integer.MAX_VALUE);
		txtaMsg.setPrefRowCount(2);
		txtaMsg.setWrapText(true);

		AnchorPane.setLeftAnchor(btnAnnulPost, 0.0);
		AnchorPane.setRightAnchor(btnPoster, 0.0);

		btnPoster.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				if ((txtaMsg.getText() != null && !txtaMsg.getText().isEmpty())) {
					MultivaluedMap<String, String> params = new MultivaluedMapImpl();
					String msg = txtaMsg.getText();
					params.add("msg", msg);
					txtaMsg.clear();
					if (!isGrpMsg) {
						taskAndServMsg.taskPosterMsg(idUtil, params);
					} else {
						taskAndServMsg.taskPosterMsgDeGroupe(idUtil, idGrp, params);
					}

				} else {
					txtaMsg.setPromptText("You have not left a comment.");
				}
			}
		});

		btnAnnulPost.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				txtaMsg.clear();
			}
		});
		anchorPostMsg.getChildren().clear();
		gridpPostMsg.getChildren().clear();

		anchorPostMsg.getChildren().addAll(btnAnnulPost, btnPoster);
		gridpPostMsg.add(txtaMsg, 0, 0, 2, 1);
		gridpPostMsg.add(anchorPostMsg, 0, 1, 2, 1);

		return gridpPostMsg;
	}

	// affichage des messages
	Label lblAffichMsg;
	Label lblPseudo;
	Label lblDateMsg;
	Button btnSuppMsg = new Button("X");
	AnchorPane anchorAffichMsg = new AnchorPane();
	GridPane gridpAffichMsg = new GridPane();

	public GridPane afficherMessages(long idUtilisateur, Message message,
			Service<ObservableList<Message>> s) {
		final Service<ObservableList<Message>> service = s;
		final Message msg = message;

		gridpAffichMsg.setPadding(new Insets(10, 10, 10, 10));
		gridpAffichMsg.setVgap(5);
		gridpAffichMsg.setHgap(5);
		gridpAffichMsg.setPrefHeight(60);
		gridpAffichMsg.setMaxHeight(100);

		lblAffichMsg = new Label(msg.getMsg());
		lblAffichMsg.setPrefWidth(400);
		lblAffichMsg.setWrapText(true);

		if (msg.getUser().getId() != idUtilisateur)
			btnSuppMsg.setVisible(false);
		btnSuppMsg.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				taskAndServMsg.taskSuppMsg(msg.getId());
				service.restart();
			}
		});

		lblPseudo = new Label(msg.getUser().getPseudo());
		lblDateMsg = new Label(msg.getDateCreation().toString());

		anchorAffichMsg.getChildren().clear();
		gridpAffichMsg.getChildren().clear();

		anchorAffichMsg.getChildren().addAll(lblPseudo, lblDateMsg);
		AnchorPane.setLeftAnchor(lblPseudo, 0.0);
		AnchorPane.setRightAnchor(lblDateMsg, 0.0);
		gridpAffichMsg.add(anchorAffichMsg, 0, 0, 3, 1);
		gridpAffichMsg.add(lblAffichMsg, 0, 1, 2, 1);
		gridpAffichMsg.add(btnSuppMsg, 2, 1);

		return gridpAffichMsg;
	}

}
