import java.util.LinkedList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class Snack {

	private int length;
	public LinkedList<Node> body = new LinkedList<>();
	private Pane area;
	public Node head;
	private double unit;
	int size;
	static boolean pause = false;
	String urlH = "head.png";
	String urlB = "body.png";

	public Snack(double areaX, double areaY, Size size, Pane area) {

		switch (size) {
		case Small:
			this.size = 20;
			break;
		case Medium:
			this.size = 30;
			break;
		case Large:
			this.size = 40;
			break;
		}
		this.unit = areaX / this.size;
		this.area = area;
		setLength(0);
		body.add(new Node(areaX / (2 * unit) - 1, areaY / (2 * unit), unit, Direction.R, urlH));
		body.add(new Node(body.getLast().X - 1, body.getLast().getY(), unit, Direction.R, urlB));
		body.add(new Node(body.getLast().X - 1, body.getLast().getY(), unit, Direction.R, urlB));
		head = body.get(0);
		for (Node r : body) {
			this.area.getChildren().add(r.getRect());
		}
		length = 0;
	}

	public void move(KeyEvent e) {
		Direction d = body.get(1).getDirection();
		if (e.getCode() == Setting.pause) {
			pause = !pause;
		}
		if (!Snack.pause) {
			if (e.getCode() == Setting.right && head.getDirection() != Direction.L && d != Direction.L) {
				head.setDirection(Direction.R);
			} else if (e.getCode() == Setting.down && head.getDirection() != Direction.U && d != Direction.U) {
				head.setDirection(Direction.D);
			} else if (e.getCode() == Setting.left && head.getDirection() != Direction.R && d != Direction.R) {
				head.setDirection(Direction.L);
			} else if (e.getCode() == Setting.up && head.getDirection() != Direction.D && d != Direction.D) {
				head.setDirection(Direction.U);
			}
		}
	}

	public boolean changeHead(ImageView food) {
		Node n = null;
		switch (head.direction) {
		case R:
			n = new Node(head.X + 1, head.Y, unit, head.direction, urlH);
			break;
		case U:
			n = new Node(head.X, head.Y - 1, unit, head.direction, urlH);
			break;
		case L:
			n = new Node(head.X - 1, head.Y, unit, head.direction, urlH);
			break;
		case D:
			n = new Node(head.X, head.Y + 1, unit, head.direction, urlH);
			break;
		}
		if (n.getX() >= size || n.getY() >= size || n.getX() < 0 || n.getY() < 0) {
			return false;
		} else if (contains(n.getRect())) {
			return false;
		} else {
			body.add(0, n);
			head.getRect().setImage(new Image(urlB));
			head = body.get(0);
			if (isAte(food)) {
				length++;
			} else {
				removeLast();
			}
			area.getChildren().add(head.getRect());
			return true;
		}

	}

	public boolean contains(ImageView n) {
		for (Node e : body) {
			if (e.getRect().getX() == n.getX() && e.getRect().getY() == n.getY()) {
				return true;
			}
		}
		return false;
	}

	public boolean isAte(ImageView food) {
		if (food.getX() == head.getRect().getX() && food.getY() == head.getRect().getY()) {
			area.getChildren().remove(food);
			return true;
		} else {
			return false;
		}

	}

	public void removeLast() {
		area.getChildren().remove(body.getLast().getRect());
		body.removeLast();
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	class Node {
		private ImageView rect;
		private Direction direction;
		private double X;
		private double Y;
		String url;

		public Node(double X, double Y, double unit, Direction direction, String url) {
			setX(X);
			setY(Y);
			this.url = url;
			rect = new ImageView(url);
			rect.setX(X * unit);
			rect.setY(Y * unit);
			rect.setFitWidth(unit);
			rect.setFitHeight(unit);
			setDirection(direction);
		}

		public ImageView getRect() {
			return rect;
		}

		public Direction getDirection() {
			return direction;
		}

		public double getX() {
			return X;
		}

		public double getY() {
			return Y;
		}

		public void setX(double x) {
			X = x;
		}

		public void setY(double y) {
			Y = y;
		}

		public void setRect(ImageView rect) {
			this.rect = rect;
		}

		public void setDirection(Direction direction) {
			this.direction = direction;
		}

	}

}
