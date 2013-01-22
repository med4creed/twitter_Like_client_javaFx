package taskAndService;

import java.io.IOException;
import java.net.URISyntaxException;

import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

import javax.ws.rs.core.MultivaluedMap;

import model.Utilisateur;

import clientJersey.ClientJersey;

import com.sun.jersey.api.client.ClientResponse;

public class TaskAndServiceUtilisateur {

	ClientJersey client;

	public TaskAndServiceUtilisateur(String rootUri) {
		try {
			this.client = client = new ClientJersey(
					"http://127.0.0.1:8080/twitterLikeTest-1.1-SNAPSHOT/resources");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public Task<ClientResponse> taskCreerCpte(
			final MultivaluedMap<String, String> params) {
		final Task<ClientResponse> taskCreerCpte = new Task<ClientResponse>() {
			protected ClientResponse call() throws Exception {
				return client.createUtilisateur(params);
			}
		};
		taskCreerCpte.setOnFailed(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent success) {
				System.out.println("fail to create a new user!");
			}
		});
		new Thread(taskCreerCpte, "Fetch Menu Thread").start();
		return taskCreerCpte;
	}

	public Service<ClientResponse> serviceAuthentification(
			final MultivaluedMap<String, String> params) {
		final javafx.concurrent.Service<ClientResponse> service = new javafx.concurrent.Service<ClientResponse>() {
			@Override
			protected Task<ClientResponse> createTask() {
				return new Task<ClientResponse>() {
					protected ClientResponse call() throws Exception {
						return client.authentification(params);
					}
				};
			}
		};
		service.setOnFailed(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent success) {
				System.out.println("fail to authentificate user!!!");
			}
		});
		service.start();
		return service;
	}

	public javafx.concurrent.Service<Utilisateur> serviceGetUtilisateur(
			final long idUtil) {
		final javafx.concurrent.Service<Utilisateur> service = new javafx.concurrent.Service<Utilisateur>() {
			@Override
			protected Task<Utilisateur> createTask() {
				return new Task<Utilisateur>() {
					protected Utilisateur call() throws Exception {
						return client.getUtilisateur(idUtil);
					}
				};
			}
		};
		service.setOnFailed(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent success) {
				System.out.println("fail to get user!!!");
			}
		});
		service.start();
		return service;
	}

	public javafx.concurrent.Service<ObservableList<Utilisateur>> serviceGetAllUtilisateurs() {
		final javafx.concurrent.Service<ObservableList<Utilisateur>> service = new javafx.concurrent.Service<ObservableList<Utilisateur>>() {
			@Override
			protected Task<ObservableList<Utilisateur>> createTask() {
				return new Task<ObservableList<Utilisateur>>() {
					protected ObservableList<Utilisateur> call()
							throws Exception {
						return client.getAllUtilisateurs();
					}
				};
			}
		};
		service.setOnFailed(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent success) {
				System.out.println("fail to get all users!!!");
			}
		});
		service.start();
		return service;
	}

	public javafx.concurrent.Service<ObservableList<Utilisateur>> getAllUtilisateursASuivre(
			final long idUtil) {
		final javafx.concurrent.Service<ObservableList<Utilisateur>> service = new javafx.concurrent.Service<ObservableList<Utilisateur>>() {
			@Override
			protected Task<ObservableList<Utilisateur>> createTask() {
				return new Task<ObservableList<Utilisateur>>() {
					protected ObservableList<Utilisateur> call()
							throws Exception {
						return client.getAllUtilisateursASuivre(idUtil);
					}
				};
			}
		};
		service.setOnFailed(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent success) {
				System.out.println("fail to get all users to follow!!!");
			}
		});
		service.start();
		return service;
	}

	public javafx.concurrent.Service<ObservableList<Utilisateur>> getAllUtilisateursOfGroupe(
			final long idgrp) {
		final javafx.concurrent.Service<ObservableList<Utilisateur>> service = new javafx.concurrent.Service<ObservableList<Utilisateur>>() {
			@Override
			protected Task<ObservableList<Utilisateur>> createTask() {
				return new Task<ObservableList<Utilisateur>>() {
					protected ObservableList<Utilisateur> call()
							throws Exception {
						return client.getAllUtilisateursOfGroupe(idgrp);
					}
				};
			}
		};
		service.setOnFailed(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent success) {
				System.out.println("fail to get all users of a groupe!!!");
			}
		});
		service.start();
		return service;
	}

	public Task taskModifierCompte(final long idUtil,
			final MultivaluedMap<String, String> params) {
		final Task<ClientResponse> taskModifCpte = new Task<ClientResponse>() {
			protected ClientResponse call() throws Exception {
				return client.updateUtilisateur(idUtil, params);
			}
		};
		taskModifCpte.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent success) {
				ClientResponse response = (ClientResponse) success.getSource()
						.getValue();
				System.out.println(response.toString());
			}
		});
		taskModifCpte.setOnFailed(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent success) {
				System.out.println("fail to update user info!!!");
			}
		});
		new Thread(taskModifCpte, "Fetch Menu Thread").start();
		return taskModifCpte;
	}

	public Task taskModifierMdp(final long idUtil,
			final MultivaluedMap<String, String> params) {
		final Task<ClientResponse> taskModifMdp = new Task<ClientResponse>() {
			protected ClientResponse call() throws Exception {
				return client.updateUtilisateur(idUtil, params);
			}
		};
		taskModifMdp.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent success) {
				ClientResponse response = (ClientResponse) success.getSource()
						.getValue();
				System.out.println(response.toString());
			}
		});
		taskModifMdp.setOnFailed(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent success) {
				System.out.println("fail to update user psw!!!");
			}
		});
		new Thread(taskModifMdp, "Fetch Menu Thread").start();
		return taskModifMdp;
	}

	public javafx.concurrent.Service<ClientResponse> serviceSuivreUtilisateur(
			final long idUtil, final long idUtilAsuivre) {
		final javafx.concurrent.Service<ClientResponse> service = new javafx.concurrent.Service<ClientResponse>() {
			@Override
			protected Task<ClientResponse> createTask() {
				return new Task<ClientResponse>() {
					protected ClientResponse call() throws Exception {
						return client.suivreUtilisateur(idUtil, idUtilAsuivre);
					}
				};
			}
		};
		service.setOnFailed(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent success) {
				System.out.println("fail to follow user!!!");
			}
		});
		service.start();
		return service;
	}

	public Task taskSuivreUtilisateur(final long idUtil,
			final long idUtilAsuivre) {
		final Task<ClientResponse> taskSuivre = new Task<ClientResponse>() {
			protected ClientResponse call() throws Exception {
				return client.suivreUtilisateur(idUtil, idUtilAsuivre);
			}
		};
		taskSuivre.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent success) {
				ClientResponse response = (ClientResponse) success.getSource()
						.getValue();
				System.out.println(response.toString());
			}
		});
		taskSuivre.setOnFailed(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent success) {
				System.out.println("fail to follow user!!!");
			}
		});
		new Thread(taskSuivre, "Fetch Menu Thread").start();
		return taskSuivre;
	}

	public javafx.concurrent.Service<ClientResponse> serviceAnnulSuiviUtilisateur(
			final long idUtil, final long idUtilAsuivre) {
		final javafx.concurrent.Service<ClientResponse> service = new javafx.concurrent.Service<ClientResponse>() {
			@Override
			protected Task<ClientResponse> createTask() {
				return new Task<ClientResponse>() {
					protected ClientResponse call() throws Exception {
						return client.nePlusSuivreUtilisateur(idUtil,
								idUtilAsuivre);
					}
				};
			}
		};
		service.setOnFailed(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent success) {
				System.out.println("fail to cancel following user!!!");
			}
		});
		service.start();
		return service;
	}

	public Task taskAnnulSuiviUtilisateur(final long idUtil,
			final long idUtilAsuivre) {
		final Task<ClientResponse> taskNePlusSuivre = new Task<ClientResponse>() {
			protected ClientResponse call() throws Exception {
				return client.nePlusSuivreUtilisateur(idUtil, idUtilAsuivre);
			}
		};
		taskNePlusSuivre.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent success) {
				ClientResponse response = (ClientResponse) success.getSource()
						.getValue();
				System.out.println(response.toString());
			}
		});
		taskNePlusSuivre.setOnFailed(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent success) {
				System.out.println("fail to cancel following user!!!");
			}
		});
		new Thread(taskNePlusSuivre, "Fetch Menu Thread").start();
		return taskNePlusSuivre;
	}

	public Task taskSupprimerCpte(final long idUtil) {
		final Task<ClientResponse> taskDeleteUser = new Task<ClientResponse>() {
			protected ClientResponse call() throws Exception {
				return client.deleteUtilisateur(idUtil);
			}
		};
		taskDeleteUser.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent success) {
				ClientResponse response = (ClientResponse) success.getSource()
						.getValue();
				System.out.println(response.toString());
			}
		});
		taskDeleteUser.setOnFailed(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent success) {
				System.out.println("fail to delete user!!!");
			}
		});
		new Thread(taskDeleteUser, "Fetch Menu Thread").start();
		return taskDeleteUser;
	}

}
