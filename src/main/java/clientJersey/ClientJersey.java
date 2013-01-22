package clientJersey;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import model.Message;
import model.Groupe;
import model.Utilisateur;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public class ClientJersey {

	private WebResource wr;

	public ClientJersey() {
	}

	public ClientJersey(String rootUrl) throws IOException, URISyntaxException {
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING,
				Boolean.TRUE);
		Client client = Client.create(clientConfig);
		URL url = new URL(rootUrl);
		wr = client.resource(url.toURI());
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////
	// Utilisateurs
	// /////////////
	public ClientResponse createUtilisateur(
			MultivaluedMap<String, String> params) {
		ClientResponse response = wr.path("utilisateurs/create")
				.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				.post(ClientResponse.class, params);
		return response;
	}

	public ClientResponse authentification(MultivaluedMap<String, String> params) {
		ClientResponse response = wr.path("utilisateurs/authentification")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.post(ClientResponse.class, params);
		return response;
	}

	public Utilisateur getUtilisateur(long idUtil) {
		String str = wr.path("utilisateurs/" + idUtil)
				.accept(MediaType.APPLICATION_JSON).get(String.class);

		Utilisateur Utilisateur = utilisateurFromJson(str);
		return Utilisateur;
	}

	public ObservableList<Utilisateur> getAllUtilisateurs() {
		String str = wr.path("utilisateurs").accept(MediaType.APPLICATION_JSON)
				.get(String.class);

		ObservableList<Utilisateur> obsListUtilis = listUtilisateurFromJson(str);

		return obsListUtilis;
	}

	public ObservableList<Utilisateur> getAllUtilisateursASuivre(long idUtil) {
		String str = wr.path("utilisateurs/" + idUtil + "/utilisateurASuivre")
				.accept(MediaType.APPLICATION_JSON).get(String.class);

		ObservableList<Utilisateur> obsListUtilisAsuivre = listUtilisateurFromJson(str);

		return obsListUtilisAsuivre;
	}

	public ObservableList<Utilisateur> getAllUtilisateursOfGroupe(long idgrp) {
		String str = wr.path("utilisateurs/groupe/" + idgrp)
				.accept(MediaType.APPLICATION_JSON).get(String.class);
		ObservableList<Utilisateur> obsListUtilisOfGrp = listUtilisateurFromJson(str);

		return obsListUtilisOfGrp;
	}

	public ClientResponse rejoindreGroupe(long idUtil, long idGrpe) {
		ClientResponse response = wr
				.path("utilisateurs/" + idUtil + "/rejoindre/" + idGrpe)
				.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				.put(ClientResponse.class);
		return response;
	}

	public ClientResponse quitterGroupe(long idUtil, long idGrpe) {
		ClientResponse response = wr
				.path("utilisateurs/" + idUtil + "/quitter/" + idGrpe)
				.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				.put(ClientResponse.class);
		return response;
	}

	public ClientResponse suivreUtilisateur(long idUtil, long idUtilAsuivre) {
		ClientResponse response = wr
				.path("utilisateurs/" + idUtil + "/suivre/" + idUtilAsuivre)
				.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				.put(ClientResponse.class);
		return response;
	}

	public ClientResponse nePlusSuivreUtilisateur(long idUtil,
			long idUtilAsuivre) {
		ClientResponse response = wr
				.path("utilisateurs/" + idUtil + "/nePlusSuivre/"
						+ idUtilAsuivre)
				.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				.put(ClientResponse.class);
		return response;
	}

	public ClientResponse updateUtilisateur(long idUtil,
			MultivaluedMap<String, String> params) {
		ClientResponse response = wr.path("utilisateurs/update/" + idUtil)
				.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				.put(ClientResponse.class, params);
		return response;
	}

	public ClientResponse deleteUtilisateur(long idUtil) {
		ClientResponse response = wr.path("utilisateurs/delete/" + idUtil)
				.type(MediaType.WILDCARD_TYPE).delete(ClientResponse.class);
		return response;
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

	private ObservableList<Utilisateur> listUtilisateurFromJson(String str) {
		ObjectMapper mapper = new ObjectMapper();
		TypeFactory t = TypeFactory.defaultInstance();
		List<Utilisateur> listUtilis = new ArrayList<Utilisateur>();
		try {
			listUtilis = mapper.readValue(str, t.constructCollectionType(
					ArrayList.class, Utilisateur.class));
		} catch (JsonParseException e1) {
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ObservableList<Utilisateur> obsListUtilis = FXCollections
				.observableArrayList();
		obsListUtilis.addAll(listUtilis);
		return obsListUtilis;
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////////////////////////////
	// Messages
	// /////////

	public ClientResponse createMessage(long idUser,
			MultivaluedMap<String, String> params) {
		ClientResponse response = wr.path("messages/create/" + idUser)
				.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				.post(ClientResponse.class, params);
		return response;
	}

	public ClientResponse createMessageForGrrp(long idUser, long idGrp,
			MultivaluedMap<String, String> params) {
		ClientResponse response = wr
				.path("messages/create/" + idUser + "/" + idGrp)
				.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				.post(ClientResponse.class, params);
		return response;
	}

	public ObservableList<Message> getAllMessages() {
		String str = wr.path("messages").accept(MediaType.APPLICATION_JSON)
				.get(String.class);
		ObservableList<Message> obsListMsgs = listMesageFromJson(str);

		return obsListMsgs;
	}

	public ObservableList<Message> getAllMessagesOfUsersToFollow(long idUtil) {
		String str = wr.path("messages/aSuivre/utilisateur/" + idUtil)
				.accept(MediaType.APPLICATION_JSON).get(String.class);
		ObservableList<Message> obsListMsgs = listMesageFromJson(str);
		return obsListMsgs;
	}

	public ObservableList<Message> getAllMessagesOfUtilisateur(long idUtil) {
		String str = wr.path("messages/utilisateur/" + idUtil)
				.accept(MediaType.APPLICATION_JSON).get(String.class);
		ObservableList<Message> obsListMsgs = listMesageFromJson(str);
		return obsListMsgs;
	}

	public ObservableList<Message> getAllMessagesOfGroupe(long idGrp) {
		String str = wr.path("messages/groupe/" + idGrp)
				.accept(MediaType.APPLICATION_JSON).get(String.class);
		ObservableList<Message> obsListMsgs = listMesageFromJson(str);
		return obsListMsgs;
	}

	public ClientResponse deleteMessage(long idMsg) {
		ClientResponse response = wr.path("messages/delete/" + idMsg)
				.type(MediaType.WILDCARD_TYPE).delete(ClientResponse.class);
		return response;
	}

	private ObservableList<Message> listMesageFromJson(String str) {
		List<Message> listMsgs = new ArrayList<Message>();
		ObjectMapper mapper = new ObjectMapper();
		TypeFactory t = TypeFactory.defaultInstance();
		try {
			listMsgs = mapper.readValue(str,
					t.constructCollectionType(ArrayList.class, Message.class));
		} catch (JsonParseException e1) {
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ObservableList<Message> obsListMsgs = FXCollections
				.observableArrayList();
		obsListMsgs.addAll(listMsgs);
		return obsListMsgs;
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////////////////////////////
	// Groupes
	// ////////
	public ClientResponse createGroupe(long idUtil,
			MultivaluedMap<String, String> params) {
		ClientResponse response = wr.path("groupes/create/" + idUtil)
				.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				.post(ClientResponse.class, params);
		return response;
	}

	public ObservableList<Groupe> getAllGroupes() {
		String str = wr.path("groupes").accept(MediaType.APPLICATION_JSON)
				.get(String.class);
		ObservableList<Groupe> obsListGrpes = listGroupFromJson(str);

		return obsListGrpes;
	}

	public Groupe getGroupe(long idGrpe) {
		String str = wr.path("groupes/" + idGrpe)
				.accept(MediaType.APPLICATION_JSON).get(String.class);
		Groupe grp = groupFromJson(str);
		return grp;
	}

	public ObservableList<Groupe> getAllGroupesOfUtilisateur(long idUtil) {
		String str = wr.path("groupes/utilisateur/" + idUtil)
				.accept(MediaType.APPLICATION_JSON).get(String.class);
		ObservableList<Groupe> obsListGrpes = listGroupFromJson(str);

		return obsListGrpes;
	}

	public ObservableList<Groupe> getAllGroupesOfAdmin(long idAdmin) {
		String str = wr.path("groupes/admin/" + idAdmin)
				.accept(MediaType.APPLICATION_JSON).get(String.class);
		ObservableList<Groupe> obsListGrpes = listGroupFromJson(str);

		return obsListGrpes;
	}

	public ClientResponse updateGroupe(long idGrpe,
			MultivaluedMap<String, String> params) {
		ClientResponse response = wr.path("groupes/update" + idGrpe)
				.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				.put(ClientResponse.class, params);
		return response;
	}

	public ClientResponse deleteGroupe(long idGrpe) {
		ClientResponse response = wr.path("groupes/delete/" + idGrpe)
				.type(MediaType.WILDCARD_TYPE).delete(ClientResponse.class);
		return response;
	}

	private ObservableList<Groupe> listGroupFromJson(String str) {
		List<Groupe> listGrpes = new ArrayList<Groupe>();
		ObjectMapper mapper = new ObjectMapper();
		TypeFactory t = TypeFactory.defaultInstance();
		try {
			listGrpes = mapper.readValue(str,
					t.constructCollectionType(ArrayList.class, Groupe.class));
		} catch (JsonParseException e1) {
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ObservableList<Groupe> obsListGrpes = FXCollections
				.observableArrayList();
		obsListGrpes.addAll(listGrpes);
		return obsListGrpes;
	}

	private Groupe groupFromJson(String str) {
		Groupe grp = new Groupe();
		ObjectMapper mapper = new ObjectMapper();

		try {
			grp = mapper.readValue(str, Groupe.class);
		} catch (JsonParseException e1) {
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return grp;
	}

}
