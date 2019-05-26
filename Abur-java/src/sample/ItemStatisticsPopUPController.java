package sample;

import database.Item;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class ItemStatisticsPopUPController {
    @FXML
    LineChart<String, Double> itemChart;
    @FXML
    Label itemTitle, popularityLevel;

    public void init(String itemTitle, int popularity, String[] dates, Double[] prices) {
        XYChart.Series<String, Double> series = new XYChart.Series<>();

        for (int i = 0; i < dates.length; i++) {
            series.getData().add(new XYChart.Data<>(dates[i], prices[i]));
        }

        this.itemTitle.setText(itemTitle);
        itemChart.getData().add(series);

        if(popularity < 20 && popularity > -1){
            popularityLevel.setText("Very popular");
            popularityLevel.setStyle("-fx-text-fill: #01d100");
        } else if(popularity < 70) {
            popularityLevel.setText("Popular");
            popularityLevel.setStyle("-fx-text-fill: #789d00");
        } else if(popularity < 100) {
            popularityLevel.setText("Normal");
            popularityLevel.setStyle("-fx-text-fill: #9a9a9d");
        } else if(popularity < 150){
            popularityLevel.setText("Unpopular");
            popularityLevel.setStyle("-fx-text-fill: #9d4700");
        } else {
            popularityLevel.setText("Very unpopular");
            popularityLevel.setStyle("-fx-text-fill: #9d0600");
        }
    }

    public void cancelStatistics(ActionEvent actionEvent) {
        Item.cancelStatistics();
    }
}
