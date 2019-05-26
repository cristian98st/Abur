package sample;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.awt.*;

public class GameStatisticsPopUPController {
    @FXML
    LineChart<String, Double> gameChart;
    @FXML
    Label gameTitle, price;

    public void init(String gameTitle, double predictedPrice, String[] dates, Double[] prices) {
        this.gameTitle.setText(gameTitle);

        XYChart.Series<String, Double> series = new XYChart.Series<>();

        for(int i=0; i<dates.length; i++){
            series.getData().add(new XYChart.Data<>(dates[i], prices[i]));
        }

        gameChart.getData().add(series);
        price.setText(String.valueOf(predictedPrice));
    }
}
