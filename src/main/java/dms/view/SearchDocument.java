package dms.view;

import com.vaadin.data.util.IndexedContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import dms.DmsUI;
import dms.exceptions.DatabaseException;
import dms.model.Category;
import dms.model.Document;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dominik
 */
public class SearchDocument extends VerticalLayout implements View {

    public SearchDocument() {
        Label header = new Label("Search Document");
        header.addStyleName("h1");
        addComponent(header);
        addStyleName("timeline");
        

        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(true);
        
        TextField search = new TextField("Search");
        layout.addComponent(search);
        
        HorizontalLayout hLay = new HorizontalLayout();
        
        layout.addComponent(hLay);
        
        Table table = new Table();
        List<Document> documents;
        try {
            documents = ((DmsUI) UI.getCurrent()).getDmsSession().getDocumentManager().getAllDocuments();
        } catch (DatabaseException ex) {
            documents = new ArrayList<Document>();
        }
        
        IndexedContainer ic = new IndexedContainer();
        ic.addContainerProperty("Name", String.class, null);
        ic.addContainerProperty("Version", Integer.class, null);
        ic.addContainerProperty("Document Type", String.class, null);
        ic.addContainerProperty("Last Modified", String.class, null);
        ic.addContainerProperty("Admin", String.class, null);
        for(Document document : documents) { 
            ic.addItem(document);
            ic.getContainerProperty(document, "Name").setValue(document.getName());
            ic.getContainerProperty(document, "Version").setValue(document.getVersion());
            ic.getContainerProperty(document, "Document Type").setValue(document.getDocumentType());
            ic.getContainerProperty(document, "Last Modified").setValue(document.getLastModified());
            ic.getContainerProperty(document, "Admin").setValue(document.getAdmin().getUsername());
        }
        table.setMultiSelect(true);
        table.setContainerDataSource(ic);
        table.setVisibleColumns("Name", "Version", "Document Type","Last Modified","Admin");
        table.setSelectable(true);
        table.setImmediate(true);
        table.setHeight("400px");
        table.setWidth("100%");
        layout.addComponent(table);
        
        this.addComponent(layout);
        this.setExpandRatio(layout, 1);
        setSizeFull();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
    
}