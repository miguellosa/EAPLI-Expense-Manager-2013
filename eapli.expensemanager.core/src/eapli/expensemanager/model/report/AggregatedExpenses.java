/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.expensemanager.model.report;

import eapli.expensemanager.model.Expense;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author nuno
 */
public class AggregatedExpenses {

    private BigDecimal sum = BigDecimal.ZERO;
    private final List<Expense> expenses = new ArrayList<Expense>();

    /**
     * Add a movement to the list
     *
	 * @param expense
     * @param movement
     */
    public void aggregate(Expense expense) {
        expenses.add(expense);
        sum = sum.add(expense.getAmount());
    }

    /**
     * Returns all movements
	 * @return 
     */
    public List<Expense> all() {
        return Collections.unmodifiableList(expenses);
    }

    /*
     * Returns the sum of all movements
     */
    /**
     *
     * @return
     */
    public BigDecimal getSum() {
        return sum;
    }
}
