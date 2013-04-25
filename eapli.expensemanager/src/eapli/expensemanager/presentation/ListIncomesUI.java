/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.expensemanager.presentation;

import eapli.expensemanager.presentation.framework.BaseUI;
import eapli.expensemanager.controllers.BaseController;
import eapli.expensemanager.controllers.ListIncomesController;
import eapli.expensemanager.model.Expense;
import eapli.expensemanager.model.Income;
import eapli.expensemanager.presentation.framework.ListWidget;
import java.util.List;

/**
 *
 * @author Paulo Gandra Sousa
 */
class ListIncomesUI extends BaseUI {

    ListIncomesController controller = new ListIncomesController();
    ListWidget<Income> widget;
    
    @Override
    protected BaseController controller() {
        return controller;
    }

    @Override
    protected boolean doShow() {
        widget = new ListWidget<Income>(controller.getIncomes(), new IncomeListVisitor());
        widget.show();

        
        return true;
    }

    @Override
    public String headline() {
        return "LIST INCOMES";
    }
    
}
