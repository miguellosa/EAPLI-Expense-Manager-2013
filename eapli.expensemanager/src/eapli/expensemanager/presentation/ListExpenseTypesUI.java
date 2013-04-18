/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.expensemanager.presentation;

import eapli.expensemanager.controllers.BaseController;
import eapli.expensemanager.controllers.ListExpenseTypesController;
import eapli.expensemanager.model.ExpenseType;

/**
 *
 * @author Paulo Gandra Sousa
 */
class ListExpenseTypesUI extends BaseUI {

    private ListExpenseTypesController controller = new ListExpenseTypesController();
    ListWidget<ExpenseType> widget;
            
    @Override
    protected BaseController controller() {
        return controller;
    }

    @Override
    public boolean doShow() {
        widget = new ListWidget<ExpenseType>(controller.getExpenseTypes());
        widget.show();
        return true;
    }

    @Override
    public String headline() {
        return "LIST EXPENSE TYPES";    
    }

}
