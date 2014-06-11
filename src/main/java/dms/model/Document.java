package dms.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 * @Author Hannah Siegel
 * @version 2014-06-09
 */
@NamedQueries({
    @NamedQuery(name="getDocuments", query = "FROM Document d"),
    @NamedQuery(name="getManagedDocuments", query = "FROM Document d where d.admin = :id"),
    @NamedQuery(name="SearchAuthor",query="FROM Document d WHERE d.admin = :id"), //session.getUser ansprechen! Q1
//    @NamedQuery(name="SearchCategory",query="FROM Document d WHERE :id IN d.categories"), //session.getUser ansprechen! Q2

    @NamedQuery(name="SearchDocumentType",query="FROM Document d WHERE d.documentType = :id"), //pdf docx ... ansprechen! Q5
//    @NamedQuery(name="SearchDocumentName",query="FROM Document d WHERE d.name LIKE %:id%"), // documentenname.toLowerCase ... ansprechen! 
    @NamedQuery(name="SearchDocumentUser",query="FROM Document d WHERE :id IN d.users"),//session.getUser ansprechen!
})
@Entity
public class Document implements Serializable {

    @Id
    @GeneratedValue
    private int ID;

    private String name;
    
    private String description;

    @Column(unique = true)
    private String path;

    private int version;

    private String documentType;

    private Date lastModified;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private User occupant;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private User admin;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<KeyWord> keyWords = new HashSet<KeyWord>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Category> categories = new HashSet<Category>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<User> users = new HashSet<User>();

    public Document(String name, String description, String documentType, int version, String path, User admin) {
        this.name = name;
        this.description = description;
        this.documentType = documentType;
        this.version = version;
        this.path = path;
        this.admin = admin;
    }

    public Document() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public User getOccupant() {
        return occupant;
    }

    public void setOccupant(User occupant) {
        this.occupant = occupant;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public Set<KeyWord> getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(Set<KeyWord> keyWords) {
        this.keyWords = keyWords;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public int getID() {
        return ID;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public boolean equals(Object o) {
        if(o == null || o.getClass() != this.getClass()) return false;
        return ((Document) o).getID() == ID;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + this.ID;
        return hash;
    }
}
