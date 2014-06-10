package dms.model;

import java.io.Serializable;
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
	@NamedQuery(name="getAllCategories",query="FROM Category c"),
})
@Entity
public class Category implements Serializable {
	@Id 
	@GeneratedValue
	private int ID;

	private String name;
        
	public Category(String name){
			this.name = name;
	}
	
	public Category(){}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getID() {
		return ID;
	}
	
	
}