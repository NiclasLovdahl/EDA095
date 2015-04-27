import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class TimeServer1 {

	public TimeServer1() {
	}

	public void run() {
		Date date = new Date();
		String s = "";
		String dateString = "";
		Locale loc = new Locale("swe");

		while (true) {
			System.out.print("What do you want to show? (date/time): ");
			Scanner scan = new Scanner(System.in);

			if (scan.hasNext()) {
				s = scan.nextLine();
			}

			switch (s) {
			case "date":
				dateString = DateFormat.getDateInstance(1, loc).format(date);
				break;
			case "time":
				dateString = DateFormat.getTimeInstance(1, loc).format(date);
				break;
			case "quit":
				return;
			default:
			}
			System.out.println(dateString);
		}
	}

	public static void main(String[] args) {
		TimeServer1 ts = new TimeServer1();
		ts.run();
	}

}
