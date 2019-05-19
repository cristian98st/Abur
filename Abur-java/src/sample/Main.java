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
        TableColumn<Game, String> id = new TableColumn<>("ID");
        id.setPrefWidth(262);
        id.setStyle("-fx-background-color: #323232; -fx-text-fill: #fff");

        id.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Game, String>, ObservableValue<String>>() {
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

        TableColumn<Game, String> price = new TableColumn<>("Price");
        price.setPrefWidth(262);
        price.setStyle("-fx-background-color: #323232; -fx-text-fill: #fff");


        price.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Game, String>, ObservableValue<String>>() {
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

        final TreeItem<Game> root = new RecursiveTreeItem<Game>(games, RecursiveTreeObject::getChildren);
        gamesTable.getColumns().setAll(id, title, price, date);
//        gamesTable.setRoot
//        gamesTable.setShowRoot(false);
        gamesTable.setItems(games);
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

//    public static void autoResizeColumns( TreeTableView<?> table )
//    {
//        //Set the right policy
//        table.setColumnResizePolicy( TreeTableView.UNCONSTRAINED_RESIZE_POLICY);
//        table.getColumns().stream().forEach( (column) ->
//        {
//            //Minimal width = columnheader
//            Text t = new Text( column.getText() );
//            double max = t.getLayoutBounds().getWidth();
//            for ( int i = 0; i < table.getTreeItem().size(); i++ )
//            {
//                //cell must not be empty
//                if ( column.getCellData( i ) != null )
//                {
//                    t = new Text( column.getCellData( i ).toString() );
//                    double calcwidth = t.getLayoutBounds().getWidth();
//                    //remember new max-width
//                    if ( calcwidth > max )
//                    {
//                        max = calcwidth;
//                    }
//                }
//            }
//            //set the new max-widht with some extra space
//            column.setPrefWidth( max + 10.0d );
//        } );
//    }
}
