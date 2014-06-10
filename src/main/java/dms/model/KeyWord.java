package dms.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


/**
 * @Author Hannah Siegel
 * @version 2014-06-09
 *
**/
@NamedQueries({
	@NamedQuery(name="getKeyWord",query="FROM KeyWord k"),
})
@Entity
public class KeyWord implements Serializable {
	@Id 
	@GeneratedValue
	private int ID;

	private String name;
	
	public KeyWord(String name){
		this.name = name;
	}
	
	public KeyWord(){}
	
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