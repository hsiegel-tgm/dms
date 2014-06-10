package dms.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

//import org.hibernate.validator.constraints.Email;


/**
 * @Author Hannah Siegel
 * @version 2014-06-09
 *
**/
@NamedQueries({
	@NamedQuery(name="getAllUsers",query="FROM User u order by u.ID"),
        @NamedQuery(name="getUser",query="FROM User u where u.username = :id"),
})
@Table(name="dmsuser")
@Entity
public class User implements Serializable, Comparable {
	@Id 
	@GeneratedValue
	private int ID;

	@Column(unique=true)
	private String username;

//	@Email
	private String email;

	private byte[] password;
	
	public User(String username, String email,byte[] password){
			this.username=username;
			this.email=email;
			this.password=password;
	}
	
	public User(){}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public byte[] getPassword() {
		return password;
	}
	
	public void setPassword(byte[] password) {
		this.password = password;
	}
	
	public int getID() {
		return ID;
	}

    @Override
    public int compareTo(Object o) {
        if(User.class != o.getClass()) return 0;
        return username.compareTo(((User) o).getUsername());
    }
}