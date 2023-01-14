package mcs.plugin.app.core;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private TextField textField1;

    @FXML
    private Button btn_1;

    @FXML
    private void clickButton1(ActionEvent event) {
        String value = textField1.getText();
        textField1.setText(value + "1");
    }

    @FXML
    private Button btn_2;

    @FXML
    private void clickButton2(ActionEvent event) {
        String value = textField1.getText();
        textField1.setText(value + "2");
    }


}
