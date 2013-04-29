/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.expensemanager.presentation;

import eapli.expensemanager.model.Expense;
import eapli.expensemanager.model.Visitor;

/**
 *
 * @author Paulo Gandra Sousa
 */
class ExpenseListVisitor implements Visitor<Expense> {

    public ExpenseListVisitor() {
    }

    @Override
    public void visit(Expense visited) {
            System.out.print(visited.getDateOcurred() + " ");
            System.out.print(visited.getAmount() + " ");
            System.out.println(visited.getDescription());
    }
    
}