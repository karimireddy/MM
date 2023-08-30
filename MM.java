
import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MM {

	public static void main(String[] args) {

		String logOffTime = "18:"; //Shuts down at 6PM
		String logOffTime2 = "22:"; //Shuts down at 10PM if it's restarted again after 6 PM
		try {
			Robot robot = new Robot();
			PointerInfo a;
			Point b;

			while (true) {
				try {
					SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
					String time = parser.format(new Date().getTime());
					if (time.contains(logOffTime) || time.contentEquals(logOffTime2)) {
						System.out.println("It's time to log-off");
						System.exit(0);
					}
					a = MouseInfo.getPointerInfo();
					b = a.getLocation();
					int x, y;
					x = (int) b.getX();
					y = (int) b.getY();

					Thread.sleep(30000);

					a = MouseInfo.getPointerInfo();
					b = a.getLocation();
					int x1, y1;

					x1 = (int) b.getX();
					y1 = (int) b.getY();

					if (x == x1 && y == y1) {

						robot.mouseMove(x + 1, y + 1);
						System.out.println(x + "-" + y);
						Thread.sleep(30000);

						a = MouseInfo.getPointerInfo();
						b = a.getLocation();
						x = (int) b.getX();
						y = (int) b.getY();
						robot.mouseMove(x - 1, y - 1);
						System.out.println(x + "-" + y);
						Thread.sleep(30000);
					} else {
						System.out.print(".");
					}
				} catch (Exception e) {
					try {
						Thread.sleep(30000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		} catch (AWTException e) {
		}
	}
}
