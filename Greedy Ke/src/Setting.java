import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;

public class Setting {
	public static Size size = Size.Large;
	public static int speed = 600;
	public static KeyCode up = KeyCode.W;
	public static KeyCode down = KeyCode.S;
	public static KeyCode left = KeyCode.A;
	public static KeyCode right = KeyCode.D;
	public static KeyCode pause = KeyCode.P;
	public static Paint body;
	public static Paint head;

}

enum Direction {
	U, D, L, R
}

enum Size {
	Small, Medium, Large
}
