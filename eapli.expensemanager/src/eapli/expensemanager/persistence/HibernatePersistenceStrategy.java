/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.expensemanager.persistence;

/**
 *
 * @author Paulo Gandra Sousa
 */
    class HibernatePersistenceStrategy implements PersistenceStrategy {

        @Override
        public ExpenseRepository expenseRepository() {
            return new eapli.expensemanager.persistence.hibernate.ExpenseRepositoryImpl();
        }

        @Override
        public ExpenseTypeRepository expenseTypeRepository() {
            return new eapli.expensemanager.persistence.hibernate.ExpenseTypeRepositoryImpl();
        }

        @Override
        public IncomeRepository incomeRepository() {
            return new eapli.expensemanager.persistence.hibernate.IncomeRepositoryImpl();
        }

        @Override
        public IncomeTypeRepository incomeTypeRepository() {
            return new eapli.expensemanager.persistence.hibernate.IncomeTypeRepositoryImpl();
        }

        @Override
        public CheckingAccountRepository checkingAccountRepository() {
            return new eapli.expensemanager.persistence.hibernate.CheckingAccountRepositoryImpl();
        }
    }

