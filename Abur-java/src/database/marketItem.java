package database;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class marketItem extends RecursiveTreeObject<marketItem> {
    public StringProperty player;
    public StringProperty itemName;
    public StringProperty classa;
    public StringProperty wear;
    public StringProperty rarity;
    public StringProperty itemPrice;
    public StringProperty expireDate;
    //extra
    public ObservableValue<JFXButton> btnDelete;

    public marketItem() {
    }

    public marketItem(String player, String itemName, String classa, String wear, String rarity, String itemPrice, String expireDate) {
        this.player = new SimpleStringProperty(player);
        this.itemName = new SimpleStringProperty(itemName);
        this.classa = new SimpleStringProperty(classa);
        this.wear = new SimpleStringProperty(wear);
        this.rarity = new SimpleStringProperty(rarity);
        this.itemPrice = new SimpleStringProperty(itemPrice);
        this.expireDate = new SimpleStringProperty(expireDate);

        this.btnDelete = new ObservableValue<JFXButton>() {
            @Override
            public void addListener(ChangeListener<? super JFXButton> listener) {

            }

            @Override
            public void removeListener(ChangeListener<? super JFXButton> listener) {

            }

            @Override
            public JFXButton getValue() {
                JFXButton button = new JFXButton("X");
                button.setStyle("-fx-background-color: -fx-parent; -fx-border-color: -fx-parent; -fx-text-fill: #8f2300");
                return button;
            }

            @Override
            public void addListener(InvalidationListener listener) {

            }

            @Override
            public void removeListener(InvalidationListener listener) {

            }
        };
    }

    public ObservableList<marketItem> get(String col, String name, String col2Order, String order) throws SQLException, DBException {
        Connection con = Database.getConnection();
        String columns = "id, item_name,typeof,wear,rarity,class,added_at,updated_at";
        String ord = "asc,desc";
        ObservableList<marketItem> list = FXCollections.observableArrayList();
        Statement pstmt = con.createStatement();
        ResultSet rez;
        if (columns.contains(col) && !col.contains(",")) {
            if (ord.contains(order) && !order.contains(",")) {
                rez = pstmt.executeQuery("select acc.username, i.item_name, i.class, i.wear, i.rarity, auc.price, auc.exp_date from auction auc join accounts acc on auc.id_gamer = acc.id " +
                        "join items i on auc.id_item = i.id " +
                        "where i." + col + " like '%" + name + "%' order by i." + col2Order + " " + order);
            } else {
                rez = pstmt.executeQuery("select acc.username, i.item_name, i.class, i.wear, i.rarity, auc.price, auc.exp_date from auction auc join accounts acc on auc.id_gamer = acc.id " +
                        "join items i on auc.id_item = i.id " +
                        "where i." + col + " like '%" + name + "%' ");
            }
            while (rez.next()) {
                marketItem x = new marketItem(rez.getString(1), rez.getString(2), rez.getString(3), rez.getString(4), rez.getString(5), String.valueOf(rez.getFloat(6)), rez.getString(7));
                list.add(x);
            }
        }
        return list;
    }

    public ObservableList<marketItem> getMyIyems(int id) throws SQLException {
        Connection con = Database.getConnection();
        String columns = "id, item_name,typeof,wear,rarity,class,added_at,updated_at";
        String ord = "asc,desc";
        ObservableList<marketItem> list = FXCollections.observableArrayList();
        Statement pstmt = con.createStatement();
        ResultSet rez;

        rez = pstmt.executeQuery("select acc.username, i.item_name, i.class, i.wear, i.rarity, auc.price, auc.exp_date from auction auc join accounts acc on auc.id_gamer = acc.id " +
                "join items i on auc.id_item = i.id " +
                "where acc.id = " + id);
        while (rez.next()) {
            marketItem x = new marketItem(rez.getString(1), rez.getString(2), rez.getString(3), rez.getString(4), rez.getString(5), String.valueOf(rez.getFloat(6)), rez.getString(7));
            list.add(x);
        }
        return list;
    }

    @Override
    public String toString() {
        return player.toString() + itemName.toString() + classa.toString() + wear.toString() + rarity.toString() + itemPrice.toString() + expireDate.toString() + "\n";
    }
}