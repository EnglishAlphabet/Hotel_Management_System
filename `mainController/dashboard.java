package beta.hotelver1.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.prefs.Preferences;

public class dashboardController {

    @FXML
    private Button customer;

    @FXML
    private Button dashboard;

    @FXML
    private Button employee;

    @FXML
    private LineChart<?, ?> linechard;

    @FXML
    private Button logout;

    @FXML
    private Text monthoverview;

    @FXML
    private PieChart piechard;

    @FXML
    private PieChart piechard1;

    @FXML
    private TableColumn<?, ?> reservation;

    @FXML
    private TableColumn<?, ?> reservation1;

    @FXML
    private Button setting;

    @FXML
    private Text yearoverview;

    private static final String PREFERENCE_NODE_NAME = "beta.hotel.preferences";
    private static final String THEME_KEY = "theme";
    private static final String USER_KEY = "user";

    private String theme;
    private String userID;

    @FXML
    void logouttomainpage(ActionEvent event) {

    }

    @FXML
    void switchtocustomer(ActionEvent event) {

    }

    @FXML
    void switchtodashboard(ActionEvent event) {

    }

    @FXML
    void switchtoemployee(ActionEvent event) {

    }

    @FXML
    void switchtosetting(ActionEvent event) throws IOException {

        try{

            URL path = new File("src/main/resources/beta/hotelver1/settings.fxml").toURI().toURL();

            Parent root = FXMLLoader.load(path);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

            loadWindowsSettings();

            switch (theme){
                case "light":
                    URL lightPath = new File("src/main/resources/beta/hotelver1/settings.css").toURI().toURL();
                    root.getStylesheets().add(lightPath.toExternalForm());
                    break;
                case "dark":
                    URL darkPath = new File("src/main/resources/beta/hotelver1/settingsdark.css").toURI().toURL();
                    root.getStylesheets().add(darkPath.toExternalForm());
                case null, default:
                    break;
            }

            stage.setResizable(true);
            stage.getScene().setRoot(root);
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    private void loadWindowsSettings(){
        Preferences userPref = Preferences.userRoot().node(PREFERENCE_NODE_NAME);

        this.theme = userPref.get(THEME_KEY, "light");
        this.userID = userPref.get(USER_KEY, "Unauthorized");
    }



}

