package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.sun.istack.internal.Nullable;

import database.DBException;
import database.Game;
import database.Item;
import database.marketItem;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static javafx.application.Platform.exit;

public class Main extends Application implements Initializable {

    @FXML
    private AnchorPane panelMyAccount, panelMyGames, panelMyItems, panelSellingItems, panelSettings;
    @FXML
    private JFXButton btnMyAccount, btnMyGames, btnMyItems, btnSellingItems, btnSettings, btnLogout, delete1, delete2, delete3;
    @FXML
    private TableView<Game> gamesTable;
    @FXML
    private TableView<Item> itemsTable;
    @FXML
    private TableView<marketItem> marketItemTable;
    @FXML
    private TableView<marketItem> sellingTable;
    @FXML
    private TextField search1, search2, search3;
    @FXML
    private Label lblMoney;

    private String username;
    private int id;
    private String pass;
    private String mail;
    private int coins;

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

    public void init(int id, String username, String pass, String mail, int coins) {
        this.id = id;
        this.username = username;
        this.pass = pass;
        this.mail = mail;
        this.coins = coins;
        lblMoney.setText("Coins: " + coins + "$");
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void Cancel(ActionEvent actionEvent) {
        exit();
    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        makePanelInvisible(panelMyAccount, panelMyGames, panelMyItems, panelSellingItems, panelSettings);
        if (event.getSource() == btnMyAccount) {
            panelMyAccount.setVisible(true);
        } else if (event.getSource() == btnMyGames) {
            panelMyGames.setVisible(true);
        } else if (event.getSource() == btnMyItems) {
            panelMyItems.setVisible(true);
        } else if (event.getSource() == btnSellingItems) {
            panelSellingItems.setVisible(true);
            ObservableList<marketItem> sellingItems = FXCollections.observableArrayList();
            marketItem market = new marketItem();
            try {
                sellingItems = market.getMyIyems(id);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            sellingTable.setItems(sellingItems);
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
        if (event.getSource() == delete1) {
            Game game = new Game();
            ObservableList<Game> games = FXCollections.observableArrayList();
            try {
                games = game.get("title", search1.getText(), "title", "asc");
            } catch (SQLException | DBException e) {
                e.printStackTrace();
            }
            gamesTable.setItems(games);
        }
        if (event.getSource() == delete2) {
            ObservableList<Item> items = FXCollections.observableArrayList();
            Item item = new Item();
            try {
                items = item.get("item_name", search2.getText(), "item_name", "asc");
            } catch (SQLException | DBException e) {
                e.printStackTrace();
            }
            itemsTable.setItems(items);
        }

        if (event.getSource() == delete3) {
            ObservableList<marketItem> marketItems = FXCollections.observableArrayList();
            marketItem market = new marketItem();
            try {
                marketItems = market.get("item_name", search3.getText(), "item_name", "asc");
            } catch (SQLException | DBException e) {
                e.printStackTrace();
            }
            marketItemTable.setItems(marketItems);
        }

    }

    private void makePanelInvisible(AnchorPane... panels) {
        for (AnchorPane panel : panels) {
            panel.setVisible(false);
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        // Games
        games();

        //items
        items();

        //Marketplace
        marketplace();

        // My selling items
        sellingItems();
    }

    public void games() {
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
        date.setPrefWidth(200);
        date.setStyle("-fx-background-color: #323232; -fx-text-fill: #fff");


        date.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Game, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Game, String> param) {
                return param.getValue().launch_date;
            }
        });

        TableColumn<Game, JFXButton> buyButton1 = new TableColumn<>("");
        buyButton1.setPrefWidth(70);
        buyButton1.setStyle("-fx-background-color: #323232; -fx-text-fill: #fff; -fx-font-weight: bold");

        buyButton1.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Game, JFXButton>, ObservableValue<JFXButton>>() {
            @Override
            public ObservableValue<JFXButton> call(TableColumn.CellDataFeatures<Game, JFXButton> param) {
                return param.getValue().buyButton;
            }
        });

        gamesTable.getColumns().setAll(buyButton1, gameID, title, gamePrice, date);
    }

    public void items() {
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
                return param.getValue().name;
            }
        });

        TableColumn<Item, String> classa = new TableColumn<>("Class");
        classa.setPrefWidth(150);
        classa.setStyle("-fx-background-color: #323232; -fx-text-fill: #fff");


        classa.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Item, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Item, String> param) {
                return param.getValue().price;
            }
        });

        TableColumn<Item, String> typeOf = new TableColumn<>("Type Of");
        typeOf.setPrefWidth(150);
        typeOf.setStyle("-fx-background-color: #323232; -fx-text-fill: #fff");


        typeOf.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Item, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Item, String> param) {
                return param.getValue().type;
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
                return param.getValue().price;
            }
        });

        TableColumn<Item, JFXButton> buyButton2 = new TableColumn<>();
        buyButton2.setPrefWidth(70);
        buyButton2.setStyle("-fx-background-color: #323232; -fx-text-fill: #fff; -fx-font-weight: bold");

        buyButton2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Item, JFXButton>, ObservableValue<JFXButton>>() {
            @Override
            public ObservableValue<JFXButton> call(TableColumn.CellDataFeatures<Item, JFXButton> param) {
                return param.getValue().buyButton;
            }
        });


        itemsTable.getColumns().setAll(buyButton2, itemID, itemName, classa, typeOf, wear, rarity, itemPrice);
    }

    public void marketplace() {
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

        TableColumn<marketItem, JFXButton> buyButton3 = new TableColumn<>();
        buyButton3.setPrefWidth(70);
        buyButton3.setStyle("-fx-background-color: #323232; -fx-text-fill: #fff; -fx-font-weight: bold");

        buyButton3.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<marketItem, JFXButton>, ObservableValue<JFXButton>>() {
            @Override
            public ObservableValue<JFXButton> call(TableColumn.CellDataFeatures<marketItem, JFXButton> param) {
                return param.getValue().buyButton;
            }
        });

        marketItemTable.getColumns().setAll(buyButton3, marketPlayer, marketItemName, marketClassa, marketWear, marketRarity, marketItemPrice, marketExpireDate);
    }

    public void sellingItems() {

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

        TableColumn<marketItem, JFXButton> deleteButton = new TableColumn<>("");
        deleteButton.setPrefWidth(50);
        deleteButton.setStyle("-fx-background-color: #323232; -fx-text-fill: #fff; -fx-font-weight: bold");

        deleteButton.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<marketItem, JFXButton>, ObservableValue<JFXButton>>() {
            @Override
            public ObservableValue<JFXButton> call(TableColumn.CellDataFeatures<marketItem, JFXButton> param) {
                return param.getValue().btnDelete;
            }
        });


        sellingTable.getColumns().setAll(deleteButton, marketItemName, marketClassa, marketWear, marketRarity, marketItemPrice, marketExpireDate);

    }
}