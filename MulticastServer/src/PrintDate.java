import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class PrintDate {

	public PrintDate() {
	}

	public static void main(String[] args) {
		Date date = new Date();
		Locale loc = new Locale("ger");
		System.out.println(DateFormat.getDateTimeInstance(1, 2, loc).format(
				date));
	}

}
