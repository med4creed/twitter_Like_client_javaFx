package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class Utilisateur implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String nom;
	private String pnom;
	private String pseudo;
	private String mail;
	private String mdp;
	private Date dateInscription;
	private List<Groupe> grps;
	private List<Groupe> grpsAdmin;
	private List<Message> msgs;
	private List<Utilisateur> userToFollow;

	public Utilisateur() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPnom() {
		return pnom;
	}

	public void setPnom(String pnom) {
		this.pnom = pnom;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	public Date getDateInscription() {
		return dateInscription;
	}

	public void setDateInscription(Date dateInscription) {
		this.dateInscription = dateInscription;
	}

	public List<Groupe> getGrps() {
		return grps;
	}

	public void setGrps(List<Groupe> grps) {
		this.grps = grps;
	}

	public List<Groupe> getGrpsAdmin() {
		return grpsAdmin;
	}

	public void setGrpsAdmin(List<Groupe> grpsAdmin) {
		this.grpsAdmin = grpsAdmin;
	}

	public List<Message> getMsgs() {
		return msgs;
	}

	public void setMsgs(List<Message> msgs) {
		this.msgs = msgs;
	}

	public List<Utilisateur> getUserToFollow() {
		return userToFollow;
	}

	public void setUserToFollow(List<Utilisateur> userToFollow) {
		this.userToFollow = userToFollow;
	}

	@Override
	public String toString() {
		return "TwitteMan [id=" + id + ", nom=" + nom + ", pnom=" + pnom
				+ ", pseudo=" + pseudo + ", mail=" + mail + ", mdp=" + mdp
				+ ", dateInscription=" + dateInscription + ", grps=" + grps
				+ ", grpsAdmin=" + grpsAdmin + ", msgs=" + msgs
				+ ", userToFollow=" + userToFollow + "]";
	}

}
