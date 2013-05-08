/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.expensemanager.persistence.jpa;

import eapli.expensemanager.model.ExpenseType;
import eapli.expensemanager.model.observer.AlertLimit;
import eapli.expensemanager.model.observer.AlertLimitByExpenseType;
import eapli.expensemanager.model.observer.AlertLimitExpenditure;
import eapli.expensemanager.model.observer.AlertLimitPercentValues;
import eapli.expensemanager.model.observer.AlertLimitType;
import eapli.expensemanager.persistence.AlertLimitRepository;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 *
 * @author mcn
 */
public class AlertLimitRepositoryImpl extends JpaRepository<AlertLimit, Integer> implements AlertLimitRepository {

      @Override
      public List<AlertLimit> all() {
            return super.all();
      }

//      @Override
//      public AlertLimit save(AlertLimit alertLimit) {
//            return super.save(alertLimit);
//      }

      @Override
      public AlertLimit findByKey(int key) {
            return super.read(key);
      }

      @Override
      public AlertLimitExpenditure update(int key, BigDecimal yellow, BigDecimal red) {
            EntityManager em = getEntityManager();
            assert em != null;
            EntityTransaction tx = em.getTransaction();

            AlertLimitExpenditure temp = null;
            try {
                  tx.begin();
                  temp = (AlertLimitExpenditure)em.find(entityClass, key);
                  temp.setLimitYellow(yellow);
                  temp.setLimitRed(red);
                  tx.commit();
            } catch (PersistenceException ex) {
                  
            } finally {
                  em.close();
            }
            return temp;
      }

      
       @Override
      public AlertLimitPercentValues update(int key, double yellow, double red) {
            EntityManager em = getEntityManager();
            assert em != null;
            EntityTransaction tx = em.getTransaction();

            AlertLimitPercentValues temp = null;
            try {
                  tx.begin();
                  temp = (AlertLimitPercentValues)em.find(entityClass, key);
                  temp.setPercentLimitRed(yellow);
                  temp.setPercentLimitYellow(red);
                  tx.commit();
            } catch (PersistenceException ex) {
                  
            } finally {
                  em.close();
            }
            return temp;
      }

      @Override
      public List<AlertLimitExpenditure> findByAlertType(AlertLimitType aLertType) {
             EntityManager em = getEntityManager();
            Query q = em.createQuery("SELECT e FROM AlertLimit e WHERE e.alertType = :aLertT");
            q.setParameter("aLertT", aLertType);
            return q.getResultList();
      }

      @Override
      public List<AlertLimitByExpenseType> findByET(ExpenseType eT) {
            EntityManager em = getEntityManager();
            Query q = em.createQuery("SELECT e FROM AlertLimitByExpenseType e WHERE e.expenseType= :eT");
            q.setParameter("eT", eT);
            return q.getResultList();
      }

      @Override
      public void save(AlertLimitExpenditure alertLimit) {
            super.save(alertLimit);
      }

      @Override
      public void save(AlertLimitByExpenseType alertLimit) {
            super.save(alertLimit);
      }

     
}