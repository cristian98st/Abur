package database;

import java.io.IOException;
import java.sql.*;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.ConfirmationPopUPController;

import static java.sql.Types.VARCHAR;

public class marketItem extends RecursiveTreeObject<marketItem> {
    public StringProperty player;
    public StringProperty itemName;
    public StringProperty classa;
    public StringProperty wear;
    public StringProperty rarity;
    public StringProperty itemPrice;
    public StringProperty expireDate;
    //extra
    static Stage confirmationStage;

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
    }

    public marketItem(String itemName, String classa, String wear, String rarity, String itemPrice, String expireDate) {
        this.player = null;
        this.itemName = new SimpleStringProperty(itemName);
        this.classa = new SimpleStringProperty(classa);
        this.wear = new SimpleStringProperty(wear);
        this.rarity = new SimpleStringProperty(rarity);
        this.itemPrice = new SimpleStringProperty(itemPrice);
        this.expireDate = new SimpleStringProperty(expireDate);
    }

    public ObservableList<marketItem> get(String col, String name, String col2Order, String order) throws SQLException{
        Connection con = Database.getConnection();
        String columns = "id, item_name,typeof,wear,rarity,class,added_at,updated_at";
        String ord = "asc,desc";
        ObservableList<marketItem> list = FXCollections.observableArrayList();
        Statement pstmt = con.createStatement();
        ResultSet rez;
        if (columns.contains(col) && !col.contains(",")) {
            if (ord.contains(order) && !order.contains(",")) {
                rez = pstmt.executeQuery("select acc.username, i.item_name, i.class, i.wear, i.rarity, auc.price from auction auc join accounts acc on auc.id_gamer = acc.id " +
                        "join items i on auc.id_item = i.id " +
                        "where i." + col + " like '%" + name + "%' order by i." + col2Order + " " + order);
            } else {
                rez = pstmt.executeQuery("select acc.username, i.item_name, i.class, i.wear, i.rarity, auc.price from auction auc join accounts acc on auc.id_gamer = acc.id " +
                        "join items i on auc.id_item = i.id " +
                        "where i." + col + " like '%" + name + "%' ");
            }
            while (rez.next()) {
                marketItem x = new marketItem(rez.getString(1), rez.getString(2), rez.getString(3), rez.getString(4), rez.getString(5), String.valueOf(rez.getFloat(6)));
                list.add(x);
            }
        }
        return list;
    }

    public ObservableList<marketItem> getMyIyems(int id) throws SQLException {
        Connection con = Database.getConnection();
        ObservableList<marketItem> list = FXCollections.observableArrayList();
        Statement pstmt = con.createStatement();
        ResultSet rez;

        rez = pstmt.executeQuery("select acc.username, i.item_name, i.class, i.wear, i.rarity, auc.price from auction auc join accounts acc on auc.id_gamer = acc.id " +
                "join items i on auc.id_item = i.id " +
                "where acc.id = " + id);
        while (rez.next()) {
            marketItem x = new marketItem(rez.getString(1), rez.getString(2), rez.getString(3), rez.getString(4), rez.getString(5), String.valueOf(rez.getFloat(6)));
            list.add(x);
        }
        return list;
    }

    public static String deleteSellingItem(int id, int item_id) {
        Connection con = Database.getConnection();
        String procedure = "{ call ELIMINATE_FROM_AUCTION(?,?,?)}";
        CallableStatement cs = null;
        try {
            cs = con.prepareCall(procedure);

            cs.setInt(1, id);
            cs.setInt(2, item_id);
            cs.registerOutParameter(3, VARCHAR);

            cs.execute();

            String result = cs.getString(3);

            return result;
        } catch (SQLException e) {
            return "Unknown error occured.";
        }
    }

    public static int fetchItemIDByName(String name) throws SQLException {
        Connection con = Database.getConnection();
        Statement stmt = con.createStatement();
        ResultSet result = stmt.executeQuery("SELECT id from items where item_name = '" + name + "'");

        result.next();
        return result.getInt(1);
    }

    public static String buyMarketItem(int sellerID, int buyerID, int itemID){
        Connection con = Database.getConnection();
        String procedure = "{ call DO_AUCTION_TRANZACTION(?,?,?,?)}";
        CallableStatement cs = null;
        try {
            cs = con.prepareCall(procedure);


            cs.setInt(1, sellerID);
            cs.setInt(2, buyerID);
            cs.setInt(3, itemID);
            cs.registerOutParameter(4, VARCHAR);

            cs.execute();

            String result = cs.getString(4);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return "Item already owned.";
        }
    }

    public void openPopUP(String answer) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/sample/ConfirmationPopUP.fxml"
                )
        );

        Stage stage = new Stage(StageStyle.DECORATED);
        Pane custom = loader.load();
        custom.getStyleClass().add("rootPane");
        stage.setScene(new Scene(custom));

        ConfirmationPopUPController controller =
                loader.<ConfirmationPopUPController>getController();
        controller.init(answer, "marketItem");
        confirmationStage = stage;
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }

    public static void cancelConfirmation() {
        confirmationStage.close();
    }

    @Override
    public String toString() {
        return player.toString() + itemName.toString() + classa.toString() + wear.toString() + rarity.toString() + itemPrice.toString() + expireDate.toString() + "\n";
    }
}