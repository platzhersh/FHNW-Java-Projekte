package patterns.state.tickets;

public interface TicketMachine {

	void setDestination(int destination);

	void setFirstClass(boolean firstClass);
	void setReturnTicket(boolean retour);
	void setHalfPrice(boolean halfPrice);

	void enterMoney(double amount);

	void cancel();

	double getPrice();
	double getEnteredMoney();

	boolean isHalfPrice();
	boolean isFirstClass();
	boolean isRetour();
	
	boolean isInStateInit();
	boolean isInStateDestSelected();
	boolean isInStateMoneyEntered();
}