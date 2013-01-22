package taskAndService;

import java.io.IOException;
import java.net.URISyntaxException;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

import javax.ws.rs.core.MultivaluedMap;

import model.Message;

import clientJersey.ClientJersey;

import com.sun.jersey.api.client.ClientResponse;

public class TaskAndServiceMessage {

	ClientJersey client;

	public TaskAndServiceMessage(String rootUri) {
		try {
			this.client = client = new ClientJersey(
					"http://127.0.0.1:8080/twitterLikeTest-1.1-SNAPSHOT/resources");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public javafx.concurrent.Service<ObservableList<Message>> serviceGetAllMsgs() {
		final javafx.concurrent.Service<ObservableList<Message>> service = new javafx.concurrent.Service<ObservableList<Message>>() {
			@Override
			protected Task<ObservableList<Message>> createTask() {
				return new Task<ObservableList<Message>>() {
					protected ObservableList<Message> call() throws Exception {
						return client.getAllMessages();
					}
				};
			}
		};
		service.start();
		service.setOnFailed(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent success) {
				System.out.println("fail to get all mesages!!!");
			}
		});
		return service;
	}

	public javafx.concurrent.Service<ObservableList<Message>> serviceGetAllMsgsOfUtilisateur(
			final long idUtil) {
		final javafx.concurrent.Service<ObservableList<Message>> service = new javafx.concurrent.Service<ObservableList<Message>>() {
			@Override
			protected Task<ObservableList<Message>> createTask() {
				return new Task<ObservableList<Message>>() {
					protected ObservableList<Message> call() throws Exception {
						return client.getAllMessagesOfUtilisateur(idUtil);
					}
				};
			}
		};
		service.setOnFailed(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent success) {
				System.out.println("fail to get all mesages of user!!!");
			}
		});
		service.start();
		return service;
	}
	
	public javafx.concurrent.Service<ObservableList<Message>> serviceGetAllMsgsOfUtilisateursASuivre(
			final long idUtil) {
		final javafx.concurrent.Service<ObservableList<Message>> service = new javafx.concurrent.Service<ObservableList<Message>>() {
			@Override
			protected Task<ObservableList<Message>> createTask() {
				return new Task<ObservableList<Message>>() {
					protected ObservableList<Message> call() throws Exception {
						return client.getAllMessagesOfUsersToFollow(idUtil);
					}
				};
			}
		};
		service.setOnFailed(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent success) {
				System.out.println("fail to get all mesages of user to follow!!!");
			}
		});
		service.start();
		return service;
	}
	
	public javafx.concurrent.Service<ObservableList<Message>> serviceGetAllMsgsOfGroupe(
			final long idGrp) {
		final javafx.concurrent.Service<ObservableList<Message>> service = new javafx.concurrent.Service<ObservableList<Message>>() {
			@Override
			protected Task<ObservableList<Message>> createTask() {
				return new Task<ObservableList<Message>>() {
					protected ObservableList<Message> call() throws Exception {
						return client.getAllMessagesOfGroupe(idGrp);
					}
				};
			}
		};
		service.setOnFailed(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent success) {
				System.out.println("fail to get all mesages of a group!!!");
			}
		});
		service.start();
		return service;
	}

	public Task taskPosterMsg(final long idUtil,final MultivaluedMap<String, String> params) {
		final Task<ClientResponse> taskPosterMsg = new Task<ClientResponse>() {
			protected ClientResponse call() throws Exception {
				return client.createMessage(idUtil, params);
			}
		};
		taskPosterMsg.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent success) {
				ClientResponse response = (ClientResponse) success.getSource()
						.getValue();
				System.out.println(response.toString());
			}
		});
		taskPosterMsg.setOnFailed(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent success) {
				System.out.println("fail to create a mesage!!!");
			}
		});
		new Thread(taskPosterMsg, "Fetch Menu Thread").start();
		return taskPosterMsg;
	}
	
	public Task taskPosterMsgDeGroupe(final long idUtil,final long idGrp,final MultivaluedMap<String, String> params) {
		final Task<ClientResponse> taskPosterMsg = new Task<ClientResponse>() {
			protected ClientResponse call() throws Exception {
				return client.createMessageForGrrp(idUtil, idGrp, params);
			}
		};
		taskPosterMsg.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent success) {
				ClientResponse response = (ClientResponse) success.getSource()
						.getValue();
				System.out.println(response.toString());
			}
		});
		taskPosterMsg.setOnFailed(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent success) {
				System.out.println("fail to create a mesage og group!!!");
			}
		});
		new Thread(taskPosterMsg, "Fetch Menu Thread").start();
		return taskPosterMsg;
	}

	public Task taskSuppMsg(final long idMsg) {
		final Task<ClientResponse> taskSuppMsg = new Task<ClientResponse>() {
			protected ClientResponse call() throws Exception {
				return client.deleteMessage(idMsg);
			}
		};
		taskSuppMsg.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent success) {
				ClientResponse response = (ClientResponse) success.getSource()
						.getValue();
				System.out.println(response.toString());
			}
		});
		taskSuppMsg.setOnFailed(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent success) {
				System.out.println("fail to delete mesage!!!");
			}
		});
		new Thread(taskSuppMsg, "Fetch Menu Thread").start();
		return taskSuppMsg;
	}

}
