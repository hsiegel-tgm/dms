package dms.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


/**
 * @Author Hannah Siegel
 * @version 2014-06-09
 *
**/
@NamedQueries({
	@NamedQuery(name="getComment",query="FROM Comment c"),
})
@Entity
public class Comment implements Serializable {
	@Id 
	@GeneratedValue
	private int ID;

	private String text;
	
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private Document document;	
	
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private User author;	

	private Date created;	
	
	public Comment(String text, Document document, User author, Date created){
			this.text = text;
			this.document = document;
			this.author = author;
			this.created = created;
	}
	
	public Comment(){}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public int getID() {
		return ID;
	}
	
	
}