# Global initializing

```
private static final String PREFERENCE_NODE_NAME = "beta.hotel.preferences";

//PREFERENCE_NODE_NAME can be named anything 
//since it is used to identify the specific preference node 
//where user preferences are stored.

private static final String THEME_KEY = "theme";
private String theme;
```


---

Creates a key 'THEME_KEY'. This works similar as the Hash Map.
It will not create duplicate key and will simply replace the old value with the new value

```
private void saveWindowsSettings(){  

    Preferences userPref = Preferences.userRoot().node(PREFERENCE_NODE_NAME);  

    if(dark.isSelected()) userPref.put(THEME_KEY, "dark");  
    else if (light.isSelected()) userPref.put(THEME_KEY, "light"); {  
  
    }  
  
}
```

---

Preferences .get(key, default_value);
If the key exists, then it will use the value that is linked with the key i.e. dark or light
However if it does not, it will use "light" as the 'default theme'

```
private void loadWindowsSettings(){  
    Preferences userPref = Preferences.userRoot().node(PREFERENCE_NODE_NAME);  
    this.theme = userPref.get(THEME_KEY, "light");  
}
```

---
# Settings.java controller 

Selects the radio button i.e. light or dark in the themeSelector toggle group
```
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
```

---
This method clears the current loaded css and applies the newly selected theme
```
private void applySettings(Parent root){  
  
        loadWindowsSettings();  
  
        try {  
            switch (theme) {  
                case "light":  
                    URL lightPath = new File("src/main/resources/beta/hotelver1/dashboard.css").toURI().toURL();  
                    root.getStylesheets().clear();  
                    root.getStylesheets().add(lightPath.toExternalForm());  
                    break;  
                case "dark":  
                    URL darkPath = new File("src/main/resources/beta/hotelver1/dashboardDark.css").toURI().toURL();  
                    root.getStylesheets().clear();  
                    root.getStylesheets().add(darkPath.toExternalForm());  
					break;
                case null, default:  
                    break;  
            }  
        }catch (IOException e){  
            e.printStackTrace();  
        }
```

---
FXML button function
```
@FXML  
void saveSettings(ActionEvent event) {  
    saveWindowsSettings();  
    Parent root = ((Node) event.getSource()).getScene().getRoot();  
    applySettings(root);  
}
```

---
### Switch Scenes Controller 

```
@FXML  
void switchToDashboard(ActionEvent event) throws IOException {  
  
    try{  
        URL path = new File("src/main/resources/beta/hotelver1/admin-revised.fxml").toURI().toURL();  
        Parent root = FXMLLoader.load(path);  
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();  
        applySettings(root);  
  
        stage.setResizable(true);  
        stage.getScene().setRoot(root);  
        stage.show();  
    }catch(Exception e){  
        e.printStackTrace();  
    }
```

---

# Main/Launch.java

```
@Override  
public void start(Stage stage) throws Exception {  
  
    loadWindowsSettings();  
  
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("admin-revised.fxml"));  
    Scene root = new Scene(fxmlLoader.load());  
  
    switch (theme){  
        case "light":  
            root.getStylesheets().add(getClass().getResource("dashboard.css").toExternalForm());  
            break;  
        case "dark":  
            root.getStylesheets().add(getClass().getResource("dashboardDark.css").toExternalForm());  
        case null, default:  
            break;  
    }  
  
    stage.setTitle("Hello!");  
    stage.setScene(root);  
    stage.show();  
}
```
