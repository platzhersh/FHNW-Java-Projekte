package patterns.strategy.date;

public class USPrintDate implements PrintDate {
	public void print(Date d) {
		System.out.println("Date: " + d.month + "/" + d.day + "/" + d.year);
	}
}
