package dms.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

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
        this.addComponent(layout);
        this.setExpandRatio(layout, 1);
        setSizeFull();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
    
}