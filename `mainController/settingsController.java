package beta.hotelver1.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.prefs.Preferences;

public class settingsController {

    @FXML
    private Button audit_view;

    @FXML
    private Button bookings;

    @FXML
    private Button cancel;

    @FXML
    private RadioButton dark;

    @FXML
    private Button dashboard;

    @FXML
    private Button guests;

    @FXML
    private RadioButton light;

    @FXML
    private Button logout;

    @FXML
    private Button path_button;

    @FXML
    private Button save;

    @FXML
    private Button setting;

    private static final String PREFERENCE_NODE_NAME = "beta.hotel.preferences";
    private static final String THEME_KEY = "theme";
    private static final String USER_KEY = "user";

    private String theme;
    private String userID;
    private themeSettings ts= new themeSettings();


    @FXML
    void dark(ActionEvent event) {

    }

    @FXML
    void light(ActionEvent event) {

    }


    @FXML
    void switchToDashboard(ActionEvent event) throws IOException {

        try{
            URL path = new File("src/main/resources/beta/hotelver1/dashboard.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(path);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

            ts.applySettings(root, "dashboard");

            stage.setResizable(true);
            stage.getScene().setRoot(root);
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void switchtorooms(ActionEvent event) {

    }

    @FXML
    void switchtoservices(ActionEvent event) throws IOException {
        try{

            URL path = new File("src/main/resources/beta/hotelver1/adminfood.fxml").toURI().toURL();

            Parent root = FXMLLoader.load(path);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

            ts.applySettings(root, "adminfood");

            stage.setResizable(true);
            stage.getScene().setRoot(root);
            stage.show();

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    @FXML
    void switchtobookings(ActionEvent event) throws IOException {
        try{

            URL path = new File("src/main/resources/beta/hotelver1/booking.fxml").toURI().toURL();

            Parent root = FXMLLoader.load(path);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

            ts.applySettings(root, "booking");

            stage.setResizable(true);
            stage.getScene().setRoot(root);
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    ToggleGroup themeSelector = new ToggleGroup();

    @FXML
    public void initialize() {
        dark.setToggleGroup(themeSelector); light.setToggleGroup(themeSelector);
        loadWindowsSettings();
        switch (theme){
            case "light":
                light.setSelected(true);
                break;
            case "dark":
                dark.setSelected(true);
                break;
            case null, default:
                break;
        }

    }


    @FXML
    void saveSettings(ActionEvent event) {
        saveWindowsSettings();
        Parent root = ((Node) event.getSource()).getScene().getRoot();
        ts.applySettings(root, "settings");
    }

    private void loadWindowsSettings(){
        Preferences userPref = Preferences.userRoot().node(PREFERENCE_NODE_NAME);

        this.theme = userPref.get(THEME_KEY, "light");
        this.userID = userPref.get(USER_KEY, "Unauthorized");
    }

    private void saveWindowsSettings(){
        Preferences userPref = Preferences.userRoot().node(PREFERENCE_NODE_NAME);

        if(dark.isSelected()) userPref.put(THEME_KEY, "dark");
        else if (light.isSelected()) userPref.put(THEME_KEY, "light"); {

        }

    }

}
