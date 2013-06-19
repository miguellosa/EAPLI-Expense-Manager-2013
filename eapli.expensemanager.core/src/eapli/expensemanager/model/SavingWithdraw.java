/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.expensemanager.model;

import eapli.util.DateTime;
import java.math.BigDecimal;
import java.util.Calendar;
import javax.persistence.Entity;

/**
 *
 * @author AJS
 */
@Entity
public class SavingWithdraw extends Movement {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    protected SavingWithdraw() {
    }

    public SavingWithdraw(String description, Calendar dateOccurred,
                          BigDecimal amount) {
        super(description, dateOccurred, amount);
    }

    public SavingWithdraw(String description, int year, int month, int day,
                          BigDecimal amount) {
        this(description, DateTime.newCalendar(year, month, day), amount);
    }
}
