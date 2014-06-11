package dms.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


/**
 * @author Hannah Siegel
 * @version 2014-06-09
 */
@NamedQueries({
	@NamedQuery(name="getAllCategories",query="FROM Category c"),
})
@Entity
public class Category implements Serializable, Comparable {
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

    @Override
    public int compareTo(Object o) {
        if(o.getClass() != this.getClass()) return 0;
        return name.compareTo(((Category) o).getName());
    }
    
    @Override
    public boolean equals(Object o) {
        if(o.getClass() != this.getClass()) return false;
        return ((Category) o).getName().equals(name);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }
}