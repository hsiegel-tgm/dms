/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dms.control;

import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import dms.DmsUI;
import dms.exceptions.DatabaseException;
import dms.view.SearchDocument;

/**
 *
 * @author Dominik
 */
public class SearchListener implements Button.ClickListener {
    
    private final SearchDocument searchDocument;
    
    public SearchListener(SearchDocument searchDocument) {
        this.searchDocument = searchDocument;
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        DocumentManager dm = ((DmsUI)UI.getCurrent()).getDmsSession().getDocumentManager();
        try {
            searchDocument.show(dm.search(searchDocument.getSearchValue()));
        } catch (DatabaseException ex) {
            ex.printStackTrace();
        }
    }
    
}
