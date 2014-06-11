package dms.view;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.MouseEventDetails.MouseButton;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseListener;
import dms.DmsSession;
import dms.DmsUI;
import dms.exceptions.DatabaseException;
import dms.model.Document;
import java.util.List;

/**
 *
 * @author Dominik
 */
public class MyDocuments extends HorizontalLayout implements View {

    private TabSheet eventSheet;
    private HorizontalLayout documentPanel;
    private DmsSession session = ((DmsUI) UI.getCurrent()).getDmsSession();

    @Override
    public void enter(ViewChangeEvent event) {
        setSizeFull();
        addStyleName("reports");

        addComponent(buildEventsView());
    }

    private Component buildEventsView() {
        eventSheet = new TabSheet();
        eventSheet.setSizeFull();
        eventSheet.addStyleName("borderless");
        eventSheet.addStyleName("editors");

        final VerticalLayout center = new VerticalLayout();
        center.setSizeFull();
        center.setCaption("Documents fait");
        eventSheet.addComponent(center);

        VerticalLayout titleAndDrafts = new VerticalLayout();
        titleAndDrafts.setSizeUndefined();
        titleAndDrafts.setSpacing(true);
        titleAndDrafts.addStyleName("drafts");
        center.addComponent(titleAndDrafts);
        center.setComponentAlignment(titleAndDrafts, Alignment.MIDDLE_CENTER);

        Label draftsTitle = new Label("Mes documents");
        draftsTitle.addStyleName("h1");
        draftsTitle.setSizeUndefined();
        titleAndDrafts.addComponent(draftsTitle);
        titleAndDrafts.setComponentAlignment(draftsTitle, Alignment.TOP_CENTER);

        documentPanel = new HorizontalLayout();
        documentPanel.setSpacing(true);
        titleAndDrafts.addComponent(documentPanel);
        
        fillDocuments();
        newDocument();

        return eventSheet;
    }

    private void fillDocuments() {
        documentPanel.removeAllComponents();
        List<Document> documents;
        try {
            documents = session.getDocumentManager().getManagedDocuments(session.getUser());
        } catch (DatabaseException ex) {
            return;
        }
        for(final Document document : documents) {
            CssLayout eventBox = new CssLayout();
            eventBox.addStyleName("event-box");
//            eventBox.addStyleName("state-" + event.getState().toString().toLowerCase());
//            Image draft = new Image(null, new ThemeResource(
//                    "img/draft-report-thumb.png"));
//            eventBox.addComponent(draft);
            Label draftTitle = new Label(
                    document.getName() + "<br />"
                            + "<span>version: " + document.getVersion()+ "</span><br />",
                    ContentMode.HTML);
            draftTitle.setSizeUndefined();
            eventBox.addComponent(draftTitle);
            documentPanel.addComponent(eventBox);
            // TODO layout bug, we need to set the alignment also for the first
            // child
            documentPanel.setComponentAlignment(eventBox, Alignment.MIDDLE_CENTER);

            final Button delete = new Button("×");
            delete.setPrimaryStyleName("delete-button");
            eventBox.addComponent(delete);
            delete.addClickListener(new ClickListener() {
                @Override
                public void buttonClick(ClickEvent clickEvent) {
                    UI.getCurrent().addWindow(new ConfirmWindow(
                        "Confirmer: Effacé document","Est-ce que vous êtrez sure que vous voudrias effacé cette esti document de calisse? \"" + document.getName() + "\"?",
                            new ClickListener() {
                                @Override
                                public void buttonClick(ClickEvent clickEvent) {
                                    try {
                                        session.getDocumentManager().delete(document);
                                        fillDocuments();
                                        newDocument();
                                    } catch (DatabaseException ex) {
                                    }
                                }
                            }
                        )
                    );
                }
            });

            eventBox.addLayoutClickListener(new LayoutClickListener() {
                @Override
                public void layoutClick(LayoutClickEvent clickEvent) {
                    if (clickEvent.getButton() == MouseButton.LEFT && clickEvent.getChildComponent() != delete) {
                        if(UI.getCurrent().getWindows().isEmpty()) {
                            Window w = new EditDocumentWindow(document);
                            w.addCloseListener(new CloseListener() {
                                @Override
                                public void windowClose(Window.CloseEvent e) {
                                    fillDocuments();
                                    newDocument();
                                }
                            });
                            UI.getCurrent().addWindow(w);
                        }
                    }
                }
            });
//            draft.setDescription("Click to edit");
            delete.setDescription("Effacer document");
            documentPanel.addComponent(eventBox);
        }
    }
    
    private void newDocument() {
        VerticalLayout createBox = new VerticalLayout();
        createBox.setWidth(null);
        createBox.addStyleName("create");
        Button create = new Button("Nouveau document");
        create.addStyleName("default");
        createBox.addComponent(create);
        createBox.setComponentAlignment(create, Alignment.MIDDLE_CENTER);
        documentPanel.addComponent(createBox);
        create.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                if(UI.getCurrent().getWindows().isEmpty()) {
                    Window w = new EditDocumentWindow();
                    UI.getCurrent().addWindow(w);
                    w.addCloseListener(new CloseListener() {
                        @Override
                        public void windowClose(Window.CloseEvent e) {
                            fillDocuments();
                            newDocument();
                        }
                    });
                }
            }
        });
    }
}
    