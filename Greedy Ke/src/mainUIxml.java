
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class mainUIxml implements Initializable {
	@FXML
	AnchorPane root;
	@FXML
	Pane background, area;
	@FXML
	Button start, setting;
	@FXML
	Text point;
	private double areaX, areaY, unit;
	private static Snack snack;
	private int poccess = 0;
	static Stage s;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		areaX = area.getPrefWidth();
		areaY = area.getPrefHeight();

		snack = new Snack(areaX, areaY, Setting.size, area);
	}

	public void changeSetting() {

		AlertBox setting = new AlertBox();
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				setting.setting();
			}
		});
	}

	public void findUnit() {
		switch (Setting.size) {
		case Small:
			unit = areaX / 20;
			break;
		case Medium:
			unit = areaX / 30;
			break;
		case Large:
			unit = areaX / 40;
			break;
		}
	}

	public static void setListener(Scene scene) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				snack.move(event);

			}

		});
	}

	public void start() {
		findUnit();
		if (poccess == 0) {
			area.getChildren().clear();
			snack = new Snack(areaX, areaY, Setting.size, area);
			point.setText("POINT: " + snack.getLength());
			myThread a = new myThread();
			a.start();
		}
	}

	class myThread extends Thread {
		boolean movable = true;
		boolean contain = false;
		ImageView food;

		@Override
		public void run() {
			poccess = 1;
			start.setDisable(true);
			setting.setDisable(true);
			synchronized (snack) {
				while (movable) {

					if (!contain) {
						int randomX = (int) (Math.random() * snack.size);
						int randomY = (int) (Math.random() * snack.size);
						food = new ImageView("food.png");
						food.setFitWidth(unit);
						food.setFitHeight(unit);
						food.setX(randomX * unit);
						food.setY(randomY * unit);
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								area.getChildren().add(food);
								contain = area.getChildren().contains(food);
							}
						});
					}
					try {
						sleep(Setting.speed / (snack.getLength() + 3));
						if (!Snack.pause) {
							Platform.runLater(new Runnable() {
								@Override
								public void run() {

									movable = snack.changeHead(food);
									point.setText("POINT: " + snack.getLength());
									contain = area.getChildren().contains(food);
								}
							});
						}
					} catch (InterruptedException e) {
						interrupt();
					}
				}
			}

			poccess = 0;
			start.setDisable(false);
			setting.setDisable(false);
			AlertBox restart = new AlertBox();
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					restart.restart("胖胖珂", "你胖死啦！");
				}
			});

		}
	}

	class AlertBox {

		public void setting() {
			s = new Stage();

			Parent root;
			try {
				root = FXMLLoader.load(getClass().getResource("settingUI.fxml"));
				s.setTitle("贪吃珂");
				Scene scene = new Scene(root);
				s.setScene(scene);
				s.setResizable(false);
				s.show();
				s.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent event) {
						s.close();
					}
				});
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		public void restart(String title, String message) {
			Stage window = new Stage() {
				@Override
				public void close() {
					hide();
					area.getChildren().clear();
					snack = new Snack(areaX, areaY, Setting.size, area);
				}
			};
			window.setTitle(title);
			window.initModality(Modality.APPLICATION_MODAL);
			window.setMinWidth(300);
			window.setMinHeight(150);
			Label label = new Label(message);
			Button button = new Button("瘦下来再来！");
			if(snack.getLength()==0) {
				window.setTitle("瘦瘦珂！");
				button.setText("珂珂不胖哦！");
				label.setText("珂珂瘦瘦的都饿晕啦！要吃多多甜甜圈长胖胖哦");
			}
			button.setOnAction(a -> window.close());

			

			VBox layout = new VBox(10);
			layout.getChildren().addAll(label, button);
			layout.setAlignment(Pos.CENTER);

			Scene scene = new Scene(layout);
			window.setScene(scene);
			window.showAndWait();
		}
	}

}