package dms.control;

import com.vaadin.ui.Button;
import dms.view.EditDocumentWindow;
import dms.view.SelectCategoryWindow;

/**
 * Listenes on the LoginButton
 * @author Dominik Scholz
 * @version 0.1
 */
public class SelectCategoryListener implements Button.ClickListener {
    
    private SelectCategoryWindow selectCategoryWindow;
    private final EditDocumentWindow editDocumentWindow;
    
    public SelectCategoryListener(EditDocumentWindow editDocumentWindow) {
        this.editDocumentWindow = editDocumentWindow;
    }
    
    @Override
    public void buttonClick(Button.ClickEvent event) {
        editDocumentWindow.addCategories(selectCategoryWindow.getSelectedCategories());
    }

    public void setSelectCategoryWindow(SelectCategoryWindow selectCategoryWindow) {
        this.selectCategoryWindow = selectCategoryWindow;
    }
}
