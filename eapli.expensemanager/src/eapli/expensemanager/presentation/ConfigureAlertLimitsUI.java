/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.expensemanager.presentation;

import eapli.expensemanager.controllers.ConfigureAlertLimitsController;
import eapli.expensemanager.model.ExpenseType;
import eapli.expensemanager.model.AlertLimitByExpenseType;
import eapli.expensemanager.model.AlertLimitExpenditure;
import eapli.expensemanager.model.AlertLimitType;
import eapli.framework.Controller;
import eapli.util.Console;
import java.util.List;

/**
 *
 * @author mcn
 */
public class ConfigureAlertLimitsUI extends BaseForm {

    double redLimit;
    double yellowLimit;
    ConfigureAlertLimitsController controller = new ConfigureAlertLimitsController();

    @Override
    public String headline() {
        return "SETTING ALERT LIMITS";
    }

    @Override
    protected Controller controller() {
        return controller;
    }

    @Override
    protected boolean doShow() {
        AlertLimitType alertType = chooseAlertLimitType();

        while (alertType != null) {

            switch (alertType) {
                case LIMITWEEKEXPENDITURE:
                    System.out.println(alertType.getDescription());
                    settingLimits(alertType);
                    break;

                case LIMITMONTHEXPENDITURE:
                    System.out.println(alertType.getDescription());
                    settingLimits(alertType);
                    break;

                        case LIMITDEVIATIONBYEXPTYPE:
                              ExpenseType eT = chooseExpenseType();
                              if (eT != null) {
                                    System.out.println("Limit values as percentage in [0..1]");
                                    settingLimitsExpType(alertType, eT);
                              }
                              break;

                case LIMITMINIMUMBALANCE:
                    System.out.println(alertType.getDescription());
                    settingLimits(alertType);
                    break;
            }
            alertType = chooseAlertLimitType();
        }
        return true;
    }

    private AlertLimitType chooseAlertLimitType() {
        AlertLimitType[] types = controller.getAlertLimitTypes();
        int size = types.length;
        System.out.println("Choose Alert Limit Type to Set its YELLOW and RED  Limits (0 to finish):");
        // TODO for consistency purposes, this block of code should use the ListWidget
        int i = 0;
        int op;
        for (AlertLimitType alLimType : types) {
            System.out.println(alLimType);
        }
        op = Console.readOption(1, size, 0);
        if (op != 0) {
            return types[op - 1];
        }
        return null;
    }



      private ExpenseType chooseExpenseType() {
            List<ExpenseType> list = controller.getAllExpenseTypes();
            int sizeET = list.size();
            if (sizeET == 0) {
                  System.out.println("No ExpenseType");
                  return null;
            } else {
                  System.out.println("Choose ExpenseType (0 to finish):");
                  int i = 0;
                  for (ExpenseType expT : list) {
                        i++;
                        System.out.println(i + " : " + expT.getDescription());
                  }
                  int eT = Console.readOption(1, sizeET, 0);
                  if (eT != 0) {
                        return list.get(eT - 1);
                  }
                  return null;
            }
        }
    

    private void settingLimits(AlertLimitType code) {
        AlertLimitExpenditure aLM = showCurrentAlertLimitsByType(code);
        if (aLM == null) {
            System.out.println("Set limits:");
            readLimitsExpenditure();
            controller.registerAlertLimitExpenditure(code, yellowLimit, redLimit);
        } else {

                  if (Console.readBoolean("Update limit values?(y/n)")) {
                        readLimitsExpenditure();
                        controller.updateAlertLimitExpenditure(aLM, yellowLimit, redLimit);
                  }
            }
        }
    

    private void settingLimitsExpType(AlertLimitType code, ExpenseType eT) {
        AlertLimitByExpenseType aLD =
                (AlertLimitByExpenseType) showCurrentAlertLimitsByExpenseType(eT);
        if (aLD == null) {
            System.out.println("Set limits:");
            readLimitsPercent();
            controller.registerAlertLimitByExpenseType(code, yellowLimit, redLimit, eT);
        } else {
            if (Console.readBoolean("Update limit values?(y/n)")) {
                readLimitsPercent();
                controller.updateAlertLimitByExpenseType(aLD, yellowLimit, redLimit);
            }
        }
    }

      private void readLimitsExpenditure() {
            do {
                  yellowLimit = Console.readDouble("Yellow Limit");
                  redLimit = Console.readDouble("Red Limit");
                  if (redLimit <= yellowLimit) {
                        System.out.println("ERROR Yellow Limit > Red Limit. Repeat Please");
                  }
            } while (redLimit <= yellowLimit);
      }

      private void readLimitsPercent() {
            boolean flag=true;
            while (flag){
                  yellowLimit = Console.readDouble("Yellow Limit");
                  redLimit = Console.readDouble("Red Limit");
                  flag= yellowLimit < 0 || yellowLimit > 1 || redLimit < 0 || redLimit > 1 || redLimit <= yellowLimit;
                  if (flag) {
                        System.out.println("ERROR: Limits in [0..1] and  Yellow Limit > Red Limit. Repeat Please");
                  }
            } 
      }

      private AlertLimitExpenditure showCurrentAlertLimitsByType(AlertLimitType alertType) {
            AlertLimitExpenditure alertLimits = controller.findAlertLimitByType(alertType);
            if (alertLimits != null) {
                  System.out.println("Current alert limits");
                  System.out.println(alertLimits);
                  return alertLimits;
            } else {
                  System.out.println("No alert limits set");
                  return null;
            }

    }
      
      
    private AlertLimitByExpenseType showCurrentAlertLimitsByExpenseType(ExpenseType eT) {
        AlertLimitByExpenseType alertLimits = controller.findAlertLimitByExpType(eT);
        if (alertLimits != null) {
            System.out.println("Current alert limits");
            System.out.println(alertLimits);
            return alertLimits;
        } else {
            System.out.println("No alert limits set");
            return null;
        }
    }
}

