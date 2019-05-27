
// Shadow of Love - 2IKX8TNPG5
// Rock - MJ68JTWLBJ - 851
// Luvitus - OWFC0W1YM1

package sample;

import static javafx.application.Platform.exit;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import com.jfoenix.controls.JFXPopup;
import database.*;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

public class Main extends Application implements Initializable {

    @FXML
    private AnchorPane panelMyAccount, panelMyGames, panelMyItems, panelSellingItems, panelSettings;
    @FXML
    private JFXButton btnMyAccount, btnMyGames, btnMyItems, btnSellingItems, btnSettings, btnLogout, delete1, delete2, delete3, buyButton2;
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
    private Label lblMoney, lblCoins, lblName, lblEmail;
    @FXML
    private GridPane gameGridPane, itemGridPane;
    @FXML
    private JFXPopup itemPopUP = new JFXPopup();
    @FXML
    private JFXPopup gamePopUP = new JFXPopup();
    @FXML
    private JFXPopup infoPopUP = new JFXPopup();
    @FXML
    private JFXPopup gameInfoPopUP = new JFXPopup();

    private String username, sellingItemName, sellingGameName;
    private static int id;
    private String pass;
    private String mail;
    private int coins;
    public static BooleanProperty sellPopUPOpen = new SimpleBooleanProperty();

    Label lbblID = new Label(), lbblName = new Label(), lbblClass = new Label(), lbblType = new Label();
    Label lbblWear = new Label(), lbblRarity = new Label(), lbblPrice = new Label();

    static Stage stage = new Stage();
    private VBox vBox = new VBox();
    private double positionX;
    private double positionY;
    private Scene mainScene;
    private VBox vBox1;

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

    public void init(int id, String username, String pass, String mail, int coins, Scene scene) {
        this.id = id;
        this.username = username;
        this.pass = pass;
        this.mail = mail;
        this.coins = coins;
        lblMoney.setText("Coins: " + coins + "$");
        lblCoins.setText("Coins: " + coins + "$");
        lblName.setText("Name: " + username);
        lblEmail.setText("Email: " + mail);
        this.mainScene = scene;

        initMyGames();
        initMyItems();
    }

    public void reinit() throws SQLException {
        LoginController loginController = new LoginController();
        loginController.retrieveByName(username);
        coins = loginController.getCoins();
        lblMoney.setText("Coins: " + coins + "$");
        lblCoins.setText("Coins: " + coins + "$");

        initMyGames();
        initMyItems();
    }

    private void initMyGames() {
        try {
            Game game = new Game();
            List<String> games = game.getMyGames(id);
            int j = 0;
            gameGridPane.getChildren().clear();
            gameGridPane.setOnMouseMoved(e -> {
                if (gameGridPane.isVisible()) {
                    positionX = e.getX();
                    positionY = e.getY();
                }
            });
            for (String g : games) {
                JFXButton button = new JFXButton(g);
                button.getStyleClass().add("gameOrItem");
                button.setPrefSize(200, 200);
                button.setOnMouseClicked(e -> {
                    showGamePopUP(e.getSceneX(), e.getSceneY(), g);
                });
                button.setOnMouseEntered(e -> {
                    boolean isStationary = true;
                    long t = System.currentTimeMillis();
                    long end = t + 800;
                    double xPosition = e.getX() - positionX;
                    double yPosition = e.getY() - positionY;
                    while (System.currentTimeMillis() < end) {
                        if (xPosition != e.getX() - positionX || yPosition != e.getY() - positionY) {
                            isStationary = false;
                            break;
                        }
                    }
                    if (isStationary == true) {
                        sellingGameName = g;
                        showGameInfoPopUP(e.getSceneX(), e.getSceneY(), g);
                    }
                });
                button.setOnMouseExited(e -> {
                    cancelGameInfoPopUP();
                });
                gameGridPane.addColumn(j, button);
                j++;
                if (j == 4)
                    j = 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initMyItems() {
        try {
            Item item = new Item();
            List<String> items = item.getMyItems(id);
            int j = 0;
            itemGridPane.getChildren().clear();
            itemGridPane.setOnMouseMoved(e -> {
                if (itemGridPane.isVisible()) {
                    positionX = e.getX();
                    positionY = e.getY();
                }
            });
            for (String i : items) {
                JFXButton button = new JFXButton(i);
                button.getStyleClass().add("gameOrItem");
                button.setPrefSize(200, 200);
                button.setOnMouseClicked(event -> {
                    showPopUP(event.getSceneX(), event.getSceneY(), i);
                });
                button.setOnMouseEntered(e -> {
                    boolean isStationary = true;
                    long t = System.currentTimeMillis();
                    long end = t + 800;
                    double xPosition = e.getX() - positionX;
                    double yPosition = e.getY() - positionY;
                    while (System.currentTimeMillis() < end) {
                        if (xPosition != e.getX() - positionX || yPosition != e.getY() - positionY) {
                            isStationary = false;
                            break;
                        }
                    }
                    if (isStationary == true) {
                        sellingItemName = i;
                        showInfoPopUP(e.getSceneX(), e.getSceneY(), i);
                    }
                });
                button.setOnMouseExited(e -> {
                    cancelInfoPopUP();
                });

                itemGridPane.addColumn(j, button);
                j++;
                if (j == 4)
                    j = 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initMarketItems() {
        ObservableList<marketItem> marketItems = FXCollections.observableArrayList();
        marketItem market = new marketItem();
        try {
            marketItems = market.get("item_name", search3.getText(), "item_name", "asc");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        marketItemTable.setItems(marketItems);
    }

    private void initSellingItems() {
        ObservableList<marketItem> sellingItems = FXCollections.observableArrayList();
        marketItem market = new marketItem();
        try {
            sellingItems = market.getMyIyems(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sellingTable.setItems(sellingItems);
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
            gamesTable.setItems(games);
        }
        if (event.getSource() == delete2) {
            ObservableList<Item> items = FXCollections.observableArrayList();
            Item item = new Item();
            try {
                items = item.get("item_name", search2.getText(), "item_name", "asc");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            itemsTable.setItems(items);
        }

        if (event.getSource() == delete3) {
            ObservableList<marketItem> marketItems = FXCollections.observableArrayList();
            marketItem market = new marketItem();
            try {
                marketItems = market.get("item_name", search3.getText(), "item_name", "asc");
            } catch (SQLException e) {
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

        sellPopUPOpen.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue == false) {
                    initMyItems();
                    cancelPopUP();
                }
            }
        });

        itemGridPane.setOnMouseClicked(e -> cancelPopUP());

        // Games
        games();

        //items
        items();

        //Marketplace
        marketplace();

        // My selling items
        sellingItems();

        // My items popup
        initSellPopUP();
        initGameSellPopUP();
        initGamePopUP();
        initItemPopUP();
    }

    private void initItemPopUP() {
        vBox = new VBox(lbblID, lbblName, lbblClass, lbblType, lbblWear, lbblRarity, lbblPrice);

        vBox.setPrefSize(250, 200);
        vBox.setStyle("-fx-background-color: #323232; -fx-border-width: 2px; -fx-border-color: #252526");
        vBox.setPadding(new Insets(10));
        infoPopUP.setContent(vBox);
        infoPopUP.setSource(itemGridPane);
    }


    private void initGamePopUP() {
        vBox1 = new VBox(lbblID, lbblName, lbblClass, lbblType, lbblWear, lbblRarity, lbblPrice);

        vBox1.setPrefSize(250, 200);
        vBox1.setStyle("-fx-background-color: #323232; -fx-border-width: 2px; -fx-border-color: #252526");
        vBox1.setPadding(new Insets(10));
        gameInfoPopUP.setContent(vBox1);
        gameInfoPopUP.setSource(itemGridPane);
    }

    private void initSellPopUP() {
        JFXButton gameSellButton = new JFXButton("Sell");
        gameSellButton.setOnMouseClicked(e -> {
            try {
                Item item = new Item();
                item.fetchItemByName(sellingItemName);
                item.openSellPopUP(id, item.getName(), item.getPclass(), item.getType(), item.getWear(), item.getRarity());
                sellPopUPOpen.setValue(true);
            } catch (SQLException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        JFXButton itemStatisticsButton = new JFXButton("Statistics");
        itemStatisticsButton.setOnMouseClicked(e -> {
            try {
                Item item = new Item();
                item.fetchItemByName(sellingItemName);
                item.openItemStatistics(sellingItemName, item.getPopularity((int) item.getId()), item.getDates(item.getId()), item.getPrices(item.getId()));
            } catch (SQLException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        VBox vBox1 = new VBox(gameSellButton, itemStatisticsButton);
        vBox1.setSpacing(10);
        itemPopUP.setContent(vBox1);
        itemPopUP.setSource(itemGridPane);
    }


    private void initGameSellPopUP() {
        JFXButton gameStatisticsButton = new JFXButton("Statistics");
        gameStatisticsButton.setOnMouseClicked(e -> {
            try {
                Game game = new Game();
                game.fetchGameByName(sellingGameName);
                GenAlg x = new GenAlg(game.getId());
                x.getDatesAndValues();
                Double predictedPrice = Double.valueOf(x.start());
                game.openGameStatistics(sellingGameName, predictedPrice, game.getDates(game.getId()), game.getPrices(game.getId()));
            } catch (SQLException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        VBox vBox1 = new VBox(gameStatisticsButton);
        gamePopUP.setContent(vBox1);
        gamePopUP.setSource(gameGridPane);
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
        date.setPrefWidth(190);
        date.setStyle("-fx-background-color: #323232; -fx-text-fill: #fff");


        date.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Game, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Game, String> param) {
                return param.getValue().launch_date;
            }
        });

        TableColumn<Game, Void> buyButton1 = new TableColumn<>("");
        buyButton1.setPrefWidth(70);
        buyButton1.setStyle("-fx-background-color: #323232; -fx-text-fill: #fff; -fx-font-weight: bold");


        addButtonTableGames(buyButton1);

        gamesTable.getColumns().add(gameID);
        gamesTable.getColumns().add(title);
        gamesTable.getColumns().add(gamePrice);
        gamesTable.getColumns().add(date);
    }

    private void addButtonTableGames(TableColumn<Game, Void> buyButton1) {
        Callback<TableColumn<Game, Void>, TableCell<Game, Void>> cellFactory = new Callback<TableColumn<Game, Void>, TableCell<Game, Void>>() {
            @Override
            public TableCell<Game, Void> call(final TableColumn<Game, Void> param) {
                final TableCell<Game, Void> cell = new TableCell<Game, Void>() {

                    private final JFXButton btn = new JFXButton("Buy");

                    {
                        btn.setStyle("-fx-background-color: -fx-parent; -fx-border-color: -fx-parent; -fx-text-fill: #8f2300");
                        btn.setOnAction((ActionEvent event) -> {

                            int row = getTableRow().getIndex();
                            TableColumn col = gamesTable.getColumns().get(1);
                            Game g = gamesTable.getItems().get(row);
                            int id_game = Integer.parseInt((String) col.getCellObservableValue(g).getValue());
                            String result;
                            try {
                                result = Game.buyGame(id, id_game);
                                g.openPopUP(result);
                                reinit();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        buyButton1.setCellFactory(cellFactory);

        gamesTable.getColumns().add(buyButton1);

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

        TableColumn<Item, Void> buyButton2 = new TableColumn<>();
        buyButton2.setPrefWidth(70);
        buyButton2.setStyle("-fx-background-color: #323232; -fx-text-fill: #fff; -fx-font-weight: bold");

        addButtonTableItems(buyButton2);


        itemsTable.getColumns().add(itemID);
        itemsTable.getColumns().add(itemName);
        itemsTable.getColumns().add(classa);
        itemsTable.getColumns().add(typeOf);
        itemsTable.getColumns().add(wear);
        itemsTable.getColumns().add(rarity);
        itemsTable.getColumns().add(itemPrice);
    }

    private void addButtonTableItems(TableColumn<Item, Void> buyButton2) {
        Callback<TableColumn<Item, Void>, TableCell<Item, Void>> cellFactory = new Callback<TableColumn<Item, Void>, TableCell<Item, Void>>() {
            @Override
            public TableCell<Item, Void> call(final TableColumn<Item, Void> param) {
                final TableCell<Item, Void> cell = new TableCell<Item, Void>() {

                    private final JFXButton btn = new JFXButton("Buy");

                    {
                        btn.setStyle("-fx-background-color: -fx-parent; -fx-border-color: -fx-parent; -fx-text-fill: #8f2300");
                        btn.setOnAction((ActionEvent event) -> {

                            int row = getTableRow().getIndex();
                            TableColumn col = itemsTable.getColumns().get(1);
                            Item i = itemsTable.getItems().get(row);
                            int id_item = Integer.parseInt((String) col.getCellObservableValue(i).getValue());
                            String result;
                            try {
                                result = Item.buyItem(id, id_item);
                                i.openPopUP(result);
                                reinit();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        buyButton2.setCellFactory(cellFactory);

        itemsTable.getColumns().add(buyButton2);

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
                return param.getValue().classa;
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


        TableColumn<marketItem, Void> buyButton3 = new TableColumn<>();
        buyButton3.setPrefWidth(70);
        buyButton3.setStyle("-fx-background-color: #323232; -fx-text-fill: #fff; -fx-font-weight: bold");

        addButtonTableMarketItem(buyButton3);

        marketItemTable.getColumns().add(marketPlayer);
        marketItemTable.getColumns().add(marketItemName);
        marketItemTable.getColumns().add(marketClassa);
        marketItemTable.getColumns().add(marketWear);
        marketItemTable.getColumns().add(marketRarity);
        marketItemTable.getColumns().add(marketItemPrice);
        marketItemTable.getColumns().add(marketExpireDate);
    }

    private void addButtonTableMarketItem(TableColumn<marketItem, Void> buyButton3) {
        Callback<TableColumn<marketItem, Void>, TableCell<marketItem, Void>> cellFactory = new Callback<TableColumn<marketItem, Void>, TableCell<marketItem, Void>>() {
            @Override
            public TableCell<marketItem, Void> call(final TableColumn<marketItem, Void> param) {
                final TableCell<marketItem, Void> cell = new TableCell<marketItem, Void>() {

                    private final JFXButton btn = new JFXButton("Buy");

                    {
                        btn.setStyle("-fx-background-color: -fx-parent; -fx-border-color: -fx-parent; -fx-text-fill: #8f2300");
                        btn.setOnAction((ActionEvent event) -> {

                            int row = getTableRow().getIndex();
                            TableColumn col1 = marketItemTable.getColumns().get(1);
                            TableColumn col2 = marketItemTable.getColumns().get(2);
                            marketItem i = marketItemTable.getItems().get(row);
                            try {
                                int seller_id = User.fetchUserIDByName((String) col1.getCellObservableValue(i).getValue());
                                int id_item = marketItem.fetchItemIDByName((String) col2.getCellObservableValue(i).getValue());
                                String result;
                                result = marketItem.buyMarketItem(seller_id, id, id_item);
                                i.openPopUP(result);
                                reinit();
                                if (result.equals("Done"))
                                    initMarketItems();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        buyButton3.setCellFactory(cellFactory);

        marketItemTable.getColumns().add(buyButton3);

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

        TableColumn<marketItem, Void> deleteButton = new TableColumn<>("");
        deleteButton.setPrefWidth(50);
        deleteButton.setStyle("-fx-background-color: #323232; -fx-text-fill: #fff; -fx-font-weight: bold");

        addButtonTableSellingItem(deleteButton);

        sellingTable.getColumns().add(marketItemName);
        sellingTable.getColumns().add(marketClassa);
        sellingTable.getColumns().add(marketWear);
        sellingTable.getColumns().add(marketRarity);
        sellingTable.getColumns().add(marketItemPrice);
        sellingTable.getColumns().add(marketExpireDate);
    }

    private void addButtonTableSellingItem(TableColumn<marketItem, Void> deleteButton) {
        Callback<TableColumn<marketItem, Void>, TableCell<marketItem, Void>> cellFactory = new Callback<TableColumn<marketItem, Void>, TableCell<marketItem, Void>>() {
            @Override
            public TableCell<marketItem, Void> call(final TableColumn<marketItem, Void> param) {
                final TableCell<marketItem, Void> cell = new TableCell<marketItem, Void>() {

                    private final JFXButton btn = new JFXButton("X");

                    {
                        btn.setStyle("-fx-background-color: -fx-parent; -fx-border-color: -fx-parent; -fx-text-fill: #8f2300");
                        btn.setOnAction((ActionEvent event) -> {

                            int row = getTableRow().getIndex();
                            TableColumn col1 = sellingTable.getColumns().get(1);
                            marketItem i = sellingTable.getItems().get(row);
                            try {
                                int item_id = marketItem.fetchItemIDByName((String) col1.getCellObservableValue(i).getValue());
                                String result;
                                result = marketItem.deleteSellingItem(id, item_id);
                                i.openPopUP(result);
                                if (result.equals("Done")) {
                                    initSellingItems();
                                    initMyItems();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        deleteButton.setCellFactory(cellFactory);

        sellingTable.getColumns().add(deleteButton);

    }

    public static int getID() {
        return id;
    }

    public void showPopUP(double x, double y, String i) {
        itemPopUP.show(JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, x - 290, y - 90);
        sellingItemName = i;
    }

    public void cancelPopUP() {
        itemPopUP.close();
    }

    private void showGamePopUP(double sceneX, double sceneY, String g) {
        gamePopUP.show(JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, sceneX - 290, sceneY - 90);
        sellingGameName = g;
    }

    private void showInfoPopUP(double x, double y, String i) {
        sellingItemName = i;
        Item item = new Item();
        try {
            item.fetchItemByName(sellingItemName);
            lbblID.setText("ID: " + item.getId());
            lbblName.setText("Name: " + item.getName());
            lbblClass.setText("Class: " + item.getPclass());
            lbblType.setText("Type: " + item.getType());
            lbblWear.setText("Wear: " + item.getWear());
            lbblRarity.setText("Rarity: " + item.getRarity());
            lbblPrice.setText("Official Price: " + item.getPrice());

            vBox.getChildren().clear();
            vBox.getChildren().setAll(lbblID, lbblName, lbblClass, lbblType, lbblWear, lbblRarity, lbblPrice);

            infoPopUP.show(JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.RIGHT, x - 1850, y - 700);
            infoPopUP.setPrefSize(0, 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showGameInfoPopUP(double x, double y, String g) {
        sellingGameName = g;
        Game game = new Game();

        try {
            game.fetchGameByName(sellingGameName);
            lbblID.setText("ID: " + game.getId());
            lbblName.setText("Name: " + game.getTitle());
            lbblRarity.setText("Launch date: " + game.getDate());
            lbblPrice.setText("Official Price: " + game.getPrice());

            vBox1.getChildren().clear();
            vBox1.getChildren().setAll(lbblID, lbblName, lbblRarity, lbblPrice);

            gameInfoPopUP.show(JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.RIGHT, x - 1850, y - 700);
            gameInfoPopUP.setPrefSize(0, 0);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cancelInfoPopUP() {
        infoPopUP.close();
    }

    public void cancelGameInfoPopUP() {
        gameInfoPopUP.close();
    }

}