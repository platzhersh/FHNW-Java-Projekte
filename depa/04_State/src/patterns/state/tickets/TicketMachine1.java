package patterns.state.tickets;

public class TicketMachine1 implements TicketMachine {
	private int destination;
	private boolean firstClass, retour, halfPrice;
	private double price;
	private double enteredMoney;

	private State state = State.INIT;

	private enum State {
		INIT, DEST_SELECTED, MONEY_ENTERED
	}

	@Override
	public void setDestination(int destination) {
		if (state != State.INIT)
			throw new IllegalStateException(
					"destination selection only possible in init state");
		this.destination = destination;
		this.firstClass = false;
		this.halfPrice = false;
		this.firstClass = false;
		this.enteredMoney = 0;
		this.price = calculatePrice(destination, firstClass, retour, halfPrice);
		state = State.DEST_SELECTED;
	}

	@Override
	public void setFirstClass(boolean firstClass) {
		if (state == State.INIT || state == State.MONEY_ENTERED)
			throw new IllegalStateException();
		this.firstClass = firstClass;
		price = calculatePrice(destination, firstClass, retour, halfPrice);
	}

	@Override
	public void setReturnTicket(boolean retour) {
		if (state == State.INIT || state == State.MONEY_ENTERED)
			throw new IllegalStateException();
		this.retour = true;
		price = calculatePrice(destination, firstClass, retour, halfPrice);
	}

	@Override
	public void setHalfPrice(boolean halfPrice) {
		if (state == State.INIT || state == State.MONEY_ENTERED)
			throw new IllegalStateException();
		this.halfPrice = halfPrice;
		price = calculatePrice(destination, firstClass, retour, halfPrice);
	}

	@Override
	public void enterMoney(double amount) {
		if (state == State.INIT)
			throw new IllegalStateException();
		if (state == State.DEST_SELECTED)
			state = State.MONEY_ENTERED;
		this.enteredMoney += amount;
		if (enteredMoney >= price) {
			printTicket(destination, price, firstClass, retour, halfPrice);
			System.out.printf("Change: %4.2f\n", enteredMoney-price);
			state = State.INIT;
		}
	}

	@Override
	public void cancel() {
		if(state == State.MONEY_ENTERED) {
			System.out.printf("Change: %4.2f\n", enteredMoney);
		}
		state = State.INIT;
	}

	private void printTicket(int destination, double price, boolean firstClass,
			boolean retour, boolean halfPrice) {
		System.out.printf("Ticket to %s %s%s%s for CHF %4.2f\n", destination, 
				firstClass ? "1Kl" :  "2Kl",
				retour ? " retour" : "",
				halfPrice ? " [ermaessigt]": "", price);
	}

	private double calculatePrice(int destination, boolean firstClass,
			boolean retour, boolean halfPrice) {
		double price = Math.floor(destination / 100.0 * 20) / 20;
		if(firstClass) price = 2 * price;
		if(retour) price = 2 * price;
		if(halfPrice) price = price / 2;
		return price;
	}

	@Override
	public double getPrice() {
		return price;
	}
	
	@Override
	public double getEnteredMoney() {
		return enteredMoney;
	}

	public boolean isFirstClass() {
		return firstClass;
	}

	public boolean isRetour() {
		return retour;
	}

	public boolean isHalfPrice() {
		return halfPrice;
	}

	@Override
	public boolean isInStateInit() {
		return state == State.INIT;
	}

	@Override
	public boolean isInStateDestSelected() {
		return state == State.DEST_SELECTED;
	}

	@Override
	public boolean isInStateMoneyEntered() {
		return state == State.MONEY_ENTERED;
	}


}
