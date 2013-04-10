/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.expensemanager.bootstrap;

import eapli.expensemanager.model.CheckingAccount;
import eapli.expensemanager.model.ExpenseType;
import eapli.expensemanager.persistence.CheckingAccountRepository;
import eapli.expensemanager.persistence.ExpenseTypeRepository;
import eapli.expensemanager.persistence.PersistenceRegistry;

/**
 * this classes serves as bootstrap data loader. just to make sure some data exists
 * in order to use the system
 * it should be removed for "production-ready" deployment
 * 
 * @author Paulo Gandra Sousa
 */
public class Bootstrap {

    private static void ensureClothingExpenseTypeExists(ExpenseTypeRepository repo) {
        ExpenseType clothing = repo.findForName(CLOTHING_EXPENSE_TYPE);
        if (clothing == null) {
            clothing = new ExpenseType(CLOTHING_EXPENSE_TYPE, CLOTHING_EXPENSE_TYPE_DESC);
            repo.save(clothing);
        }
    }

    private static void ensureTransportsExpenseTypeExists(ExpenseTypeRepository repo) {
        ExpenseType transports = repo.findForName(TRANSPORTS_EXPENSE_TYPE);
        if (transports == null) {
            transports = new ExpenseType(TRANSPORTS_EXPENSE_TYPE, TRANSPORTS_EXPENSE_TYPE_DESC);
            repo.save(transports);
        }
    }
    
    public Bootstrap() {
    }

    static {
        ensureTheAccountExists();
        ensureDefaultExpenseTypesExist();
    }

    private static void ensureTheAccountExists() {
        CheckingAccountRepository repo = PersistenceRegistry.instance().checkingAccountRepository();
        try {
            CheckingAccount theAccount = repo.theAccount();
        }
        catch (IllegalStateException ex) {
            CheckingAccount theAccount = new CheckingAccount();
            repo.save(theAccount);
        }
    }
    
    final static String CLOTHING_EXPENSE_TYPE = "Cloth.";
    final static String CLOTHING_EXPENSE_TYPE_DESC = "Clothing";
    final static String TRANSPORTS_EXPENSE_TYPE = "Trans.";
    final static String TRANSPORTS_EXPENSE_TYPE_DESC = "Transports";
    
    private static void ensureDefaultExpenseTypesExist() {
        ExpenseTypeRepository repo = PersistenceRegistry.instance().expenseTypeRepository();
        ensureClothingExpenseTypeExists(repo);
        ensureTransportsExpenseTypeExists(repo);
    }
}
