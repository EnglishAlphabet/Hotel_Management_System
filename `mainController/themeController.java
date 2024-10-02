package beta.hotelver1.controller;

import javafx.scene.Parent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.prefs.Preferences;

public class themeSettings {
    private static final String PREFERENCE_NODE_NAME = "beta.hotel.preferences";
    private static final String THEME_KEY = "theme";
    private String theme;


    public void applySettings(Parent root, String filename){

        loadWindowsSettings();

        try {
            String cssPath = "src/main/resources/beta/hotelver1/"+filename;

            if(theme.matches("dark")){
                cssPath+="dark.css";
            }else{
                cssPath+=".css";
            }
            URL appTheme = new File(cssPath).toURI().toURL();
            root.getStylesheets().clear();
            root.getStylesheets().add(appTheme.toExternalForm());

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void loadWindowsSettings(){

        Preferences userPref = Preferences.userRoot().node(PREFERENCE_NODE_NAME);

        this.theme = userPref.get(THEME_KEY, "light");
    }



}
