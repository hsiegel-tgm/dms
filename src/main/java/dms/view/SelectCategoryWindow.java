package dms.view;

import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import dms.DmsUI;
import dms.exceptions.DatabaseException;
import dms.model.Category;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Dominik
 */
public class SelectCategoryWindow extends Window {
    
    private final FormLayout form;
    private Label textField;
    private Table table;

    public SelectCategoryWindow(ClickListener listener) {
        super("Selection du categorie");
        
        form = new FormLayout();
        
        setModal(true);
        setClosable(false);
        setResizable(false);
        setWidth("280px");
//        addStyleName("edit-dashboard");
        addStyleName("new-event");
        
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(buildForm());
        layout.addComponent(buildMenu(listener));
        
        setContent(layout);
    }
    
    private Component buildForm() {
        form.setSizeUndefined();
        form.setMargin(true);
        form.setSpacing(true);
        
        table = new Table();
        List<Category> categories;
        try {
            categories = ((DmsUI) UI.getCurrent()).getDmsSession().getDocumentManager().getAllCategories();
        } catch (DatabaseException ex) {
            categories = new ArrayList<Category>();
        }
        
        IndexedContainer ic = new IndexedContainer();
        ic.addContainerProperty("Categorie", String.class, null);
        for(Category category : categories) { 
            ic.addItem(category);
            ic.getContainerProperty(category, "Categorie").setValue(category.getName());
        }
        table.setMultiSelect(true);
        table.setContainerDataSource(ic);
        table.setVisibleColumns("Categorie");
        table.setSelectable(true);
        table.setImmediate(true);
        table.setHeight("400px");
        table.setWidth("240px");
        form.addComponent(table);
        return form;
    }

    private Component buildMenu(ClickListener listener) {
        HorizontalLayout menu = new HorizontalLayout();
        menu.setMargin(true);
        menu.setSpacing(true);
        menu.addStyleName("footer");
        menu.setWidth("100%");
        
        Button ok = new Button("Ajouter");
        ok.addStyleName("wide");
        ok.addStyleName("default");
        ok.addClickListener(listener);
        ok.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                close();
            }
        });
        ok.setClickShortcut(KeyCode.ENTER, null);
        menu.addComponent(ok);
        menu.setExpandRatio(ok, 1);
        menu.setComponentAlignment(ok, Alignment.TOP_RIGHT);

        Button cancel = new Button("Abandoner");
        cancel.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                close();
            }
        });
        cancel.setClickShortcut(KeyCode.ESCAPE, null);
        menu.addComponent(cancel);
        
        return menu;
    }
    
    public Set<Category> getSelectedCategories() {
        return (Set<Category>) table.getValue();
    }
}
    