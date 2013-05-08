/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.expensemanager.model.observer;

/**
 *
 * @author mcn
 */
public enum AlertLimitType {
      /**
       * Limit week expenditure
       */
      LIMITWEEKEXPENDITURE(1, "Alert limit of weekly expenditure"),
      /**
       * Limit month expenditure
       */
      LIMITMONTHEXPENDITURE(2, "Alert limit of monthly expenditure"),
      /**
       * Limit of deviation from average by expense type
       */
      LIMITDEVIATIONBYEXPTYPE(3, "Alert limit of deviation from average expenditure by expense type"),
      /**
       * Limit of minimum balance
       */
      LIMITMINIMUMBALANCE(4, "Alert limit of minimum balance");
      
      /**
       * instance attributes.
       */
      private final int code;
      private final String description;

      /**
       * New instance - Constructor must be private
       */
      private AlertLimitType (int code, String description) {
            this.description = description;
            this.code = code;
      }

      /**
       * Returns code.
       */
      public int getCode() {
            return this.code;
      }

      /**
       * Returns the description .
       */
      public String getDescription() {
            return description;
      }
      
      @Override
      public String toString(){
            return code+":"+description;
      }

}
