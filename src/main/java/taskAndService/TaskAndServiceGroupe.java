package taskAndService;

import java.io.IOException;
import java.net.URISyntaxException;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

import javax.ws.rs.core.MultivaluedMap;

import model.Groupe;

import clientJersey.ClientJersey;

import com.sun.jersey.api.client.ClientResponse;

public class TaskAndServiceGroupe {

	ClientJersey client;

	public TaskAndServiceGroupe(String rootUri) {
		try {
			this.client = client = new ClientJersey(
					"http://127.0.0.1:8080/twitterLikeTest-1.1-SNAPSHOT/resources");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public javafx.concurrent.Service<ObservableList<Groupe>> serviceGetAllGrpesOfUtilisateur(
			final long idUtil) {
		final javafx.concurrent.Service<ObservableList<Groupe>> service = new javafx.concurrent.Service<ObservableList<Groupe>>() {
			@Override
			protected Task<ObservableList<Groupe>> createTask() {
				return new Task<ObservableList<Groupe>>() {
					protected ObservableList<Groupe> call() throws Exception {
						return client.getAllGroupesOfUtilisateur(idUtil);
					}
				};
			}
		};
		service.setOnFailed(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent success) {
				System.out.println("fail to get all groups of user!!!");
			}
		});
		service.start();
		return service;
	}

	public javafx.concurrent.Service<ObservableList<Groupe>> serviceGetAllGroupesOfAdmin(
			final long idUtil) {
		final javafx.concurrent.Service<ObservableList<Groupe>> service = new javafx.concurrent.Service<ObservableList<Groupe>>() {
			@Override
			protected Task<ObservableList<Groupe>> createTask() {
				return new Task<ObservableList<Groupe>>() {
					protected ObservableList<Groupe> call() throws Exception {
						return client.getAllGroupesOfAdmin(idUtil);
					}
				};
			}
		};
		service.setOnFailed(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent success) {
				System.out.println("fail to get all groups of admin!!!");
			}
		});
		service.start();
		return service;
	}

	public javafx.concurrent.Service<ObservableList<Groupe>> serviceGetAllGrpes() {
		final javafx.concurrent.Service<ObservableList<Groupe>> service = new javafx.concurrent.Service<ObservableList<Groupe>>() {
			@Override
			protected Task<ObservableList<Groupe>> createTask() {
				return new Task<ObservableList<Groupe>>() {
					protected ObservableList<Groupe> call() throws Exception {
						return client.getAllGroupes();
					}
				};
			}
		};
		service.setOnFailed(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent success) {
				System.out.println("fail to get all groups!!!");
			}
		});
		service.start();
		return service;
	}

	public Task taskCreerGrpe(final long idUtil,final MultivaluedMap<String, String> params) {
		final Task<ClientResponse> taskCreerGrpe = new Task<ClientResponse>() {
			protected ClientResponse call() throws Exception {
				return client.createGroupe(idUtil, params);
			}
		};
		taskCreerGrpe.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent success) {
				ClientResponse response = (ClientResponse) success.getSource()
						.getValue();
				System.out.println(response.toString());
			}
		});
		taskCreerGrpe.setOnFailed(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent success) {
				System.out.println("fail to create a new group!");
			}
		});
		new Thread(taskCreerGrpe, "Fetch Menu Thread").start();
		return taskCreerGrpe;
	}

	public Task taskSuppGrpe(final long idGrpe) {
		final Task<ClientResponse> taskSuppGrp = new Task<ClientResponse>() {
			protected ClientResponse call() throws Exception {
				return client.deleteGroupe(idGrpe);
			}
		};
		taskSuppGrp.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent success) {
				ClientResponse response = (ClientResponse) success.getSource()
						.getValue();
				System.out.println(response.toString());
			}
		});
		taskSuppGrp.setOnFailed(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent success) {
				System.out.println("fail to delete group!!!");
			}
		});
		new Thread(taskSuppGrp, "Fetch Menu Thread").start();
		return taskSuppGrp;
	}

	public Task taskRejoindreGrpe(final long idUtil, final long idGrpe) {
		final Task<ClientResponse> taskRejoindreGrp = new Task<ClientResponse>() {
			protected ClientResponse call() throws Exception {
				return client.rejoindreGroupe(idUtil, idGrpe);
			}
		};
		taskRejoindreGrp.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent success) {
				ClientResponse response = (ClientResponse) success.getSource()
						.getValue();
				System.out.println(response.toString());
			}
		});
		taskRejoindreGrp.setOnFailed(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent success) {
				System.out.println("fail to join the group!!!");
			}
		});
		new Thread(taskRejoindreGrp, "Fetch Menu Thread").start();
		return taskRejoindreGrp;
	}

	public Task taskQuitterGrpe(final long idUtil, final long idGrpe) {
		final Task<ClientResponse> taskQuitterGrp = new Task<ClientResponse>() {
			protected ClientResponse call() throws Exception {
				return client.quitterGroupe(idUtil, idGrpe);
			}
		};
		taskQuitterGrp.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent success) {
				ClientResponse response = (ClientResponse) success.getSource()
						.getValue();
				System.out.println(response.toString());
			}
		});
		taskQuitterGrp.setOnFailed(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent success) {
				System.out.println("fail to join the group!!!");
			}
		});
		new Thread(taskQuitterGrp, "Fetch Menu Thread").start();
		return taskQuitterGrp;
	}

}
