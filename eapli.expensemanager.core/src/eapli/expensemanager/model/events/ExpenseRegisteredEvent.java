/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.expensemanager.model.events;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import eapli.expensemanager.limits.ExpenditureByExpenseTypeOverLimitAlertEvent;
import eapli.expensemanager.limits.ExpenditureOverLimitAlertEvent;
import eapli.expensemanager.model.AlertLimit;
import eapli.expensemanager.model.AlertLimitByExpenseType;
import eapli.expensemanager.model.AlertLimitExpenditure;
import eapli.expensemanager.model.AlertLimitType;
import eapli.expensemanager.model.CheckingAccount;
import eapli.expensemanager.model.Expense;
import eapli.expensemanager.model.ExpenseType;
import eapli.expensemanager.persistence.AlertLimitRepository;
import eapli.expensemanager.persistence.CheckingAccountRepository;
import eapli.expensemanager.persistence.PersistenceFactory;
import eapli.util.DateTime;

/**
 * 
 * @author mcn
 */
public class ExpenseRegisteredEvent {

	private final Expense expenseRegistered;

	public ExpenseRegisteredEvent(Expense expenseRegistered) {
		this.expenseRegistered = expenseRegistered;
	}

	private int getYearOcurred() {
		Date date = expenseRegistered.getDateOcurred();
		return DateTime.dateToCalendar(date).get(Calendar.YEAR);
	}

	private int getMonthOccurred() {
		Date date = expenseRegistered.getDateOcurred();
		return DateTime.dateToCalendar(date).get(Calendar.MONTH) + 1;
	}

	private int getWeekOccurred() {
		Date date = expenseRegistered.getDateOcurred();
		int week = DateTime.weekNumber(date);
		return week;
	}

	public BigDecimal getAmount() {
		return expenseRegistered.getAmount();

	}

	private ExpenseType getExpenseType() {
		return expenseRegistered.getExpenseType();
	}

	// FIXME the name of this method is misleading. an isXXX() method should
	// return boolean and should not have side effects, e.g., create objects
	// FIXME this method name does not follow lower camel case convention
	// TODO should this class be responsible for creating the AlertEvent?
	// it increases the coupling between the two classes
	// wouldn't it be better to have that responsibility in the WatchDog?
	public ExpenditureOverLimitAlertEvent IsOverLimitWeekExpenditure() {
		CheckingAccountRepository repo = PersistenceFactory
				.buildPersistenceFactory().checkingAccountRepository();
		CheckingAccount account = repo.theAccount();
		int year = getYearOcurred();
		int week = getWeekOccurred();
		BigDecimal expenditure;
		expenditure = account.expenditureOfWeek(year, week);
		// Because the event registered is not saved yet
		expenditure = expenditure.add(getAmount());
		return buildAlertEvent(expenditure,
				AlertLimitType.LIMIT_WEEK_EXPENDITURE);
	}

	// FIXME the name of this method is misleading. an isXXX() method should
	// return boolean and should not have side effects, e.g., create objects
	// FIXME this method name does not follow lower camel case convention
	// TODO should this class be responsible for creating the AlertEvent?
	// it increases the coupling between the two classes
	// wouldn't it be better to have that responsibility in the WatchDog?
	public ExpenditureOverLimitAlertEvent IsOverLimitMonthExpenditure() {
		CheckingAccountRepository repo = PersistenceFactory
				.buildPersistenceFactory().checkingAccountRepository();
		CheckingAccount account = repo.theAccount();
		int year = getYearOcurred();
		int month = getMonthOccurred();
		BigDecimal expenditure;
		expenditure = account.expenditureOfMonth(year, month);
		// Because the event registered is not saved yet
		expenditure = expenditure.add(getAmount());
		return buildAlertEvent(expenditure,
				AlertLimitType.LIMIT_MONTH_EXPENDITURE);
	}

	// FIXME this method should not be public
	public ExpenditureOverLimitAlertEvent buildAlertEvent(
			BigDecimal expenditure, AlertLimitType alertType) {
		AlertLimitExpenditure alertLimit = (AlertLimitExpenditure) AlertLimit
				.findByAlertType(alertType);
		if (alertLimit != null) {
			if (expenditure.compareTo(alertLimit.getLimitRed()) > 0) {
				return new ExpenditureOverLimitAlertEvent(alertLimit
						.getAlertType().getDescription(),
						alertLimit.getLimitYellow(), alertLimit.getLimitRed(),
						expenditure, "RED");
			}
			if (expenditure.compareTo(alertLimit.getLimitYellow()) > 0) {
				return new ExpenditureOverLimitAlertEvent(alertLimit
						.getAlertType().getDescription(),
						alertLimit.getLimitYellow(), alertLimit.getLimitRed(),
						expenditure, "YELLOW");
			}
			return null;
		}
		return null;
	}

	// FIXME the name of this method is misleading. an isXXX() method should
	// return boolean and should not have side effects, e.g., create objects
	// FIXME this method name de snot follow lower camel case convention
	// TODO should this class be responsible for creating the AlertEvent?
	// it increases the coupling between the two classes
	// wouldn't it be better to have that responsibility in the WatchDog?
	public ExpenditureByExpenseTypeOverLimitAlertEvent IsOverLimitDeviationByExpenseType() {
		AlertLimitRepository alertLimitRepo = PersistenceFactory
				.buildPersistenceFactory().alertLimitRepository();
		ExpenseType eT = getExpenseType();
		AlertLimitByExpenseType alertLimitET = (AlertLimitByExpenseType) alertLimitRepo
				.findByExpenseType(eT);
		if (alertLimitET != null) {
			CheckingAccountRepository repo = PersistenceFactory
					.buildPersistenceFactory().checkingAccountRepository();
			CheckingAccount account = repo.theAccount();
			BigDecimal average = account.averageExpenditure(eT);
			return buildAlertEventDeviation(getAmount(), average, alertLimitET);
		}
		return null;
	}

	private ExpenditureByExpenseTypeOverLimitAlertEvent buildAlertEventDeviation(
			BigDecimal amount, BigDecimal average,
			AlertLimitByExpenseType alertLimitET) {
		double yellowPercent = alertLimitET.getPercentLimitYellow();
		double redPercent = alertLimitET.getPercentLimitRed();
		BigDecimal yellow = average.multiply(new BigDecimal(yellowPercent));
		BigDecimal red = average.multiply(new BigDecimal(redPercent));
		BigDecimal limMinYellow = average.subtract(yellow);
		BigDecimal limMaxYellow = average.add(yellow);
		BigDecimal limMinRed = average.subtract(red);
		BigDecimal limMaxRed = average.add(red);

		if ((amount.compareTo(limMinRed) <= 0)
				|| (amount.compareTo(limMaxRed) >= 0)) {
			return new ExpenditureByExpenseTypeOverLimitAlertEvent(alertLimitET
					.getAlertType().getDescription(), yellowPercent,
					redPercent, amount, average, "RED",
					alertLimitET.getExpenseType());
		}
		if ((amount.compareTo(limMinYellow) <= 0)
				|| (amount.compareTo(limMaxYellow) >= 0)) {
			return new ExpenditureByExpenseTypeOverLimitAlertEvent(alertLimitET
					.getAlertType().getDescription(), yellowPercent,
					redPercent, amount, average, "YELLOW",
					alertLimitET.getExpenseType());
		}
		return null;
	}
}