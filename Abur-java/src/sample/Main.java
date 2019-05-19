package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.application.Platform.exit;

public class Main extends Application implements Initializable {

    @FXML
    private AnchorPane panelMyAccount, panelMyGames, panelMyItems, panelSettings;
    @FXML
    private JFXButton btnMyAccount, btnMyGames, btnMyItems, btnSettings, btnLogout;
    @FXML
    private TableView<Game> gamesTable;
    @FXML
    private TableView<Item> itemsTable;
    @FXML
    private TableView<marketItem> marketItemTable;

    static Stage stage = new Stage();

    public static void closeStage() {
        stage.close();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        primaryStage.setTitle("Login");
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/resources/dark-theme.css");
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
        stage = primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void Cancel(ActionEvent actionEvent) {
        exit();
    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        makePanelInvisible(panelMyAccount, panelMyGames, panelMyItems, panelSettings);
        if (event.getSource() == btnMyAccount) {
            panelMyAccount.setVisible(true);
        } else if (event.getSource() == btnMyGames) {
            panelMyGames.setVisible(true);
        } else if (event.getSource() == btnMyItems) {
            panelMyItems.setVisible(true);
        } else if (event.getSource() == btnSettings) {
            panelSettings.setVisible(true);
        } else if (event.getSource() == btnLogout) {
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            primaryStage.setTitle("Login");
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/resources/dark-theme.css");
            primaryStage.setScene(scene);
            primaryStage.initStyle(StageStyle.TRANSPARENT);
            primaryStage.show();
            LoginController.closeStage();
        }
    }


    private void makePanelInvisible(AnchorPane... panels) {
        for (AnchorPane panel : panels) {
            panel.setVisible(false);
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){

        // Games
        TableColumn<Game, String> gameID = new TableColumn<>("ID");
        gameID.setPrefWidth(262);
        gameID.setStyle("-fx-background-color: #323232; -fx-text-fill: #fff");

        gameID.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Game, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Game, String> param) {
                return param.getValue().id;
            }
        });

        TableColumn<Game, String> title = new TableColumn<>("Title");
        title.setPrefWidth(262);
        title.setStyle("-fx-background-color: #323232; -fx-text-fill: #fff");


        title.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Game, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Game, String> param) {
                return param.getValue().title;
            }
        });

        TableColumn<Game, String> gamePrice = new TableColumn<>("Price");
        gamePrice.setPrefWidth(262);
        gamePrice.setStyle("-fx-background-color: #323232; -fx-text-fill: #fff");


        gamePrice.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Game, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Game, String> param) {
                return param.getValue().price;
            }
        });

        TableColumn<Game, String> date = new TableColumn<>("Release Date");
        date.setPrefWidth(262);
        date.setStyle("-fx-background-color: #323232; -fx-text-fill: #fff");


        date.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Game, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Game, String> param) {
                return param.getValue().date;
            }
        });

        ObservableList<Game> games = FXCollections.observableArrayList();
        games.add(new Game("1", "AC1", "102", "2/12/2004"));
        games.add(new Game("2", "AC2", "103", "2/12/2005"));
        games.add(new Game("3", "AC3", "104", "2/12/2006"));
        games.add(new Game("4", "AC4", "1005", "2/12/2007"));

        gamesTable.getColumns().setAll(gameID, title, gamePrice, date);
        gamesTable.setItems(games);


        //items
        TableColumn<Item, String> itemID = new TableColumn<>("ID");
        itemID.setPrefWidth(150);
        itemID.setStyle("-fx-background-color: #323232; -fx-text-fill: #fff");

        itemID.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Item, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Item, String> param) {
                return param.getValue().id;
            }
        });

        TableColumn<Item, String> itemName = new TableColumn<>("Item Name");
        itemName.setPrefWidth(150);
        itemName.setStyle("-fx-background-color: #323232; -fx-text-fill: #fff");


        itemName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Item, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Item, String> param) {
                return param.getValue().itemName;
            }
        });

        TableColumn<Item, String> classa = new TableColumn<>("Class");
        classa.setPrefWidth(150);
        classa.setStyle("-fx-background-color: #323232; -fx-text-fill: #fff");


        classa.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Item, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Item, String> param) {
                return param.getValue().itemPrice;
            }
        });

        TableColumn<Item, String> typeOf = new TableColumn<>("Type Of");
        typeOf.setPrefWidth(150);
        typeOf.setStyle("-fx-background-color: #323232; -fx-text-fill: #fff");


        typeOf.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Item, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Item, String> param) {
                return param.getValue().typeOf;
            }
        });

        TableColumn<Item, String> wear = new TableColumn<>("Wear");
        wear.setPrefWidth(150);
        wear.setStyle("-fx-background-color: #323232; -fx-text-fill: #fff");


        wear.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Item, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Item, String> param) {
                return param.getValue().wear;
            }
        });

        TableColumn<Item, String> rarity = new TableColumn<>("Rarity");
        rarity.setPrefWidth(150);
        rarity.setStyle("-fx-background-color: #323232; -fx-text-fill: #fff");


        rarity.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Item, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Item, String> param) {
                return param.getValue().rarity;
            }
        });

        TableColumn<Item, String> itemPrice = new TableColumn<>("Price");
        itemPrice.setPrefWidth(150);
        itemPrice.setStyle("-fx-background-color: #323232; -fx-text-fill: #fff");


        itemPrice.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Item, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Item, String> param) {
                return param.getValue().itemPrice;
            }
        });

        ObservableList<Item> items = FXCollections.observableArrayList();
        items.add(new Item("1", "Sword1", "Warrior", "melee", "destroyed", "rare", "100"));
        items.add(new Item("2", "Bow", "Hunter", "range", "good", "common", "700"));

        itemsTable.getColumns().setAll(itemID, itemName, classa, typeOf, wear, rarity, itemPrice);
        itemsTable.setItems(items);



        //Marketplace

        TableColumn<marketItem, String> marketPlayer = new TableColumn<>("Selling player");
        marketPlayer.setPrefWidth(150);
        marketPlayer.setStyle("-fx-background-color: #323232; -fx-text-fill: #fff");

        marketPlayer.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<marketItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<marketItem, String> param) {
                return param.getValue().player;
            }
        });

        TableColumn<marketItem, String> marketItemName = new TableColumn<>("Item Name");
        marketItemName.setPrefWidth(150);
        marketItemName.setStyle("-fx-background-color: #323232; -fx-text-fill: #fff");


        marketItemName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<marketItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<marketItem, String> param) {
                return param.getValue().itemName;
            }
        });

        TableColumn<marketItem, String> marketClassa = new TableColumn<>("Class");
        marketClassa.setPrefWidth(150);
        marketClassa.setStyle("-fx-background-color: #323232; -fx-text-fill: #fff");


        marketClassa.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<marketItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<marketItem, String> param) {
                return param.getValue().itemPrice;
            }
        });

        TableColumn<marketItem, String> marketWear = new TableColumn<>("Wear");
        marketWear.setPrefWidth(150);
        marketWear.setStyle("-fx-background-color: #323232; -fx-text-fill: #fff");


        marketWear.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<marketItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<marketItem, String> param) {
                return param.getValue().wear;
            }
        });

        TableColumn<marketItem, String> marketRarity = new TableColumn<>("Rarity");
        marketRarity.setPrefWidth(150);
        marketRarity.setStyle("-fx-background-color: #323232; -fx-text-fill: #fff");


        marketRarity.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<marketItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<marketItem, String> param) {
                return param.getValue().rarity;
            }
        });

        TableColumn<marketItem, String> marketItemPrice = new TableColumn<>("Price");
        marketItemPrice.setPrefWidth(150);
        marketItemPrice.setStyle("-fx-background-color: #323232; -fx-text-fill: #fff");


        marketItemPrice.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<marketItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<marketItem, String> param) {
                return param.getValue().itemPrice;
            }
        });

        TableColumn<marketItem, String> marketExpireDate = new TableColumn<>("Expires at");
        marketExpireDate.setPrefWidth(150);
        marketExpireDate.setStyle("-fx-background-color: #323232; -fx-text-fill: #fff");


        marketExpireDate.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<marketItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<marketItem, String> param) {
                return param.getValue().expireDate;
            }
        });

        ObservableList<marketItem> marketItems = FXCollections.observableArrayList();
        marketItems.add(new marketItem("Gigel", "Sword2", "Warrior", "destroyed", "legendary", "400", "12/12/2012"));
        marketItems.add(new marketItem("Gica", "SPear", "Hunter", "good", "legendary", "40000", "21/2/2050"));

        marketItemTable.getColumns().setAll(marketPlayer, marketItemName, marketClassa, marketWear, marketRarity, marketItemPrice, marketExpireDate);
        marketItemTable.setItems(marketItems);
    }

    class Game extends RecursiveTreeObject<Game>{
        StringProperty id;
        StringProperty title;
        StringProperty price;
        StringProperty date;

        public Game(String id, String title, String price, String date) {
            this.id = new SimpleStringProperty(id);
            this.title = new SimpleStringProperty(title);
            this.price = new SimpleStringProperty(price);
            this.date = new SimpleStringProperty(date);
        }
    }

    class Item extends RecursiveTreeObject<Item>{
        StringProperty id;
        StringProperty itemName;
        StringProperty classa;
        StringProperty typeOf;
        StringProperty wear;
        StringProperty rarity;
        StringProperty itemPrice;

        public Item(String id, String itemName, String classa, String typeOf, String wear, String rarity, String itemPrice){
            this.id = new SimpleStringProperty(id);
            this.itemName = new SimpleStringProperty(itemName);
            this.classa = new SimpleStringProperty(classa);
            this.typeOf = new SimpleStringProperty(typeOf);
            this.wear = new SimpleStringProperty(wear);
            this.rarity = new SimpleStringProperty(rarity);
            this.itemPrice = new SimpleStringProperty(itemPrice);
        }
    }

    class marketItem extends RecursiveTreeObject<marketItem>{
        StringProperty player;
        StringProperty itemName;
        StringProperty classa;
        StringProperty wear;
        StringProperty rarity;
        StringProperty itemPrice;
        StringProperty expireDate;

        public marketItem(String player, String itemName, String classa, String wear, String rarity, String itemPrice, String expireDate){
            this.player = new SimpleStringProperty(player);
            this.itemName = new SimpleStringProperty(itemName);
            this.classa = new SimpleStringProperty(classa);
            this.wear = new SimpleStringProperty(wear);
            this.rarity = new SimpleStringProperty(rarity);
            this.itemPrice = new SimpleStringProperty(itemPrice);
            this.expireDate = new SimpleStringProperty(expireDate);
        }
    }
}
