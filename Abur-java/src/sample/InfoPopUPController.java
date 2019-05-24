package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class InfoPopUPController {
    @FXML
    Label lblID, lblName, lblClass, lblType, lblWear, lblRarity, lblPrice;

    public void init(String id, String name, String classa, String type, String wear, String rarity, String price){
        lblID.setText(lblID.getText() + id);
        lblName.setText(lblName.getText() + name);
        lblClass.setText(lblClass.getText() + classa);
        lblType.setText(lblType.getText() + type);
        lblWear.setText(lblWear.getText() + wear);
        lblRarity.setText(lblRarity.getText() + rarity);
        lblPrice.setText(lblPrice.getText() + price);
    }
}
