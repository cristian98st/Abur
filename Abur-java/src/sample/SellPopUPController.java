package sample;

import database.Item;
import database.marketItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class SellPopUPController {

    @FXML
    Label lblItemName, lblClass, lblTypeOf, lblWear, lblRarity, lblError;

    @FXML
    TextField txtPrice;
    private int seller_id;
    private String item_name;

    public void init(int seller_id, String itemName, String classa, String typeOf, String wear, String rarity) {
        this.seller_id = seller_id;
        this.item_name = itemName;
        lblItemName.setText(lblItemName.getText() + itemName);
        lblClass.setText(lblClass.getText() + classa);
        lblTypeOf.setText(lblTypeOf.getText() + typeOf);
        lblWear.setText(lblWear.getText() + wear);
        lblRarity.setText(lblRarity.getText() + rarity);
    }

    public void cancelPopUP(ActionEvent actionEvent) {
        Item.cancelSell();
    }

    public void confirmSell(ActionEvent actionEvent) throws SQLException {
        if (txtPrice.getText().isEmpty())
            lblError.setText("You must enter a price for the item");
        else if (txtPrice.getText().matches("[0-9]+")) {
            if (txtPrice.getText().startsWith("0"))
                lblError.setText("The price cannot start with 0");
            else {
                Item.sellItem(seller_id, marketItem.fetchItemIDByName(item_name), Integer.valueOf(txtPrice.getText()));
                Item.cancelSell();
            }
        }
        else
            lblError.setText("Invalid price");
    }
}
