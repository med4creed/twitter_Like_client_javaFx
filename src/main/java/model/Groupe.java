package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Groupe implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String nomGroupe;
	private String description;
	private Date dateCreation;
	private List<Utilisateur> members;
	private Utilisateur userAdmin;
	private List<Message> grpMsgs;

	public Groupe() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNomGroupe() {
		return nomGroupe;
	}

	public void setNomGroupe(String nomGroupe) {
		this.nomGroupe = nomGroupe;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public List<Utilisateur> getMembers() {
		return members;
	}

	public void setMembers(List<Utilisateur> members) {
		this.members = members;
	}

	public Utilisateur getUserAdmin() {
		return userAdmin;
	}

	public void setUserAdmin(Utilisateur userAdmin) {
		this.userAdmin = userAdmin;
	}

	public List<Message> getGrpMsgs() {
		return grpMsgs;
	}

	public void setGrpMsgs(List<Message> grpMsgs) {
		this.grpMsgs = grpMsgs;
	}

	@Override
	public String toString() {
		return "TwitteGroupe [id=" + id + ", nomGroupe=" + nomGroupe
				+ ", dateCreation=" + dateCreation + ", members=" + members
				+ ", userAdmin=" + userAdmin + ", grpMsgs=" + grpMsgs + "]";
	}

}
