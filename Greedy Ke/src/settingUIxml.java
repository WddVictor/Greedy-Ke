import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class settingUIxml implements Initializable {
	@FXML
	Text sizeS, speedS, upS, downS, rightS, leftS, pauseS;
	@FXML
	AnchorPane pane;
	Stage a;
	boolean clicked = false;
	Text t;

	public void click(MouseEvent e) {
		clicked = true;
		t = (Text) e.getSource();
		if (t == sizeS) {
			switch (Setting.size) {
			case Small:
				Setting.size = Size.Medium;
				break;
			case Medium:
				Setting.size = Size.Large;
				break;
			case Large:
				Setting.size = Size.Small;
			}
			t.setText(Setting.size.toString());
		} else {
			t.setText("---Press Keyboard---");
		}
	}

	public void close() {
		mainUIxml.s.close();
	}

	public void handle(KeyEvent event) {
		if (clicked) {
			if (t == speedS) {

			} else if (t == upS) {
				Setting.up = event.getCode();
			} else if (t == downS) {
				Setting.down = event.getCode();
			} else if (t == leftS) {
				Setting.left = event.getCode();
			} else if (t == rightS) {
				Setting.right = event.getCode();
			} else if (t == pauseS) {
				Setting.pause = event.getCode();
			}
			if (t != speedS && t != sizeS)
				t.setText(event.getCode().toString());
			clicked = false;
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		sizeS.setText(Setting.size.toString());
		upS.setText(Setting.up.toString());
		downS.setText(Setting.down.toString());
		rightS.setText(Setting.right.toString());
		leftS.setText(Setting.left.toString());
		pauseS.setText(Setting.pause.toString());

	}

}
