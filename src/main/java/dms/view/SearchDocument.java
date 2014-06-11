package dms.view;

import com.vaadin.data.util.IndexedContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import dms.DmsUI;
import dms.control.SearchListener;
import dms.exceptions.DatabaseException;
import dms.model.Document;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Dominik
 */
public class SearchDocument extends VerticalLayout implements View {
    
    private TextField search;
    private Table table;
    private IndexedContainer ic;

    public SearchDocument() {
        Label header = new Label("Chercher des documents");
        header.addStyleName("h1");
        addComponent(header);
        addStyleName("timeline");
        

        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(true);
        
        HorizontalLayout hl = new HorizontalLayout();
        hl.setSpacing(true);
        hl.setMargin(true);
        
        search = new TextField();
        Button searchButton = new Button("chercher");
        searchButton.addClickListener(new SearchListener(this));
        
        hl.addComponent(search);
        hl.addComponent(searchButton);
        
        layout.addComponent(hl);
        
        
        table = new Table();
        ic = new IndexedContainer();
        ic.addContainerProperty("Nom", String.class, null);
        ic.addContainerProperty("Version", Integer.class, null);
        ic.addContainerProperty("Type du Document", String.class, null);
        ic.addContainerProperty("Administrateur", String.class, null);
        
        List<Document> documents;
        try {
            documents = ((DmsUI) UI.getCurrent()).getDmsSession().getDocumentManager().getAllDocuments();
        } catch (DatabaseException ex) {
            documents = new ArrayList<Document>();
        }
        show(documents);
        
        table.setMultiSelect(false);
        table.setContainerDataSource(ic);
        table.setVisibleColumns("Nom", "Version", "Type du Document","Administrateur");
        table.setSelectable(true);
        table.setImmediate(true);
        table.setSizeFull();
        layout.addComponent(table);
        
        this.addComponent(layout);
        this.setExpandRatio(layout, 1);
        setSizeFull();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
    
    public String getSearchValue() {
        return search.getValue();
    }

    public void show(Iterable<Document> search) {
        table.removeAllItems();
        ic.removeAllItems();
        for(Document document : search) { 
            ic.addItem(document);
            ic.getContainerProperty(document, "Nom").setValue(document.getName());
            ic.getContainerProperty(document, "Version").setValue(document.getVersion());
            ic.getContainerProperty(document, "Type du Document").setValue(document.getDocumentType());
            ic.getContainerProperty(document, "Administrateur").setValue(document.getAdmin().getUsername());
        }
        table.setContainerDataSource(ic);
    }
    
}