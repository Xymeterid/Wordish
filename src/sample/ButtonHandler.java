package sample;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.text.html.HTMLEditorKit;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Optional;
import java.util.Scanner;

public class ButtonHandler {

    public ButtonHandler(Stage stage, HTMLEditor htmlEditor){
        this.stage = stage;
        lastSavedState = "<html dir=\"ltr\"><head></head><body contenteditable=\"true\"></body></html>";
        currentFile = null;
        this.htmlEditor = htmlEditor;
    }

    public void newButtonPressed(){
        int res = saveChanges();
        if (res == 0){
            htmlEditor.setHtmlText("");
            lastSavedState = "<html dir=\"ltr\"><head></head><body contenteditable=\"true\"></body></html>";
            currentFile = null;
        }
    }

    public int exitButtonPressed(){
        int res = saveChanges();
        if (res == 0) {
            System.exit(0);
            return 0;
        }
        return 1;
    }

    public int saveButtonPressed(){
        if (currentFile != null){
            try {
                FileWriter fileWriter = new FileWriter(currentFile);
                fileWriter.write(htmlEditor.getHtmlText());
                fileWriter.close();
                lastSavedState = htmlEditor.getHtmlText();
                return 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 1;
        }
        else{
            return saveAsButtonPressed();
        }
    }

    public int saveAsButtonPressed() {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter for text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("HTML Files (*.html)", "*.html");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(htmlEditor.getHtmlText());
                fileWriter.close();
                lastSavedState = htmlEditor.getHtmlText();
                currentFile = file;
                return 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 1;
    }

    public void loadButtonPressed(){
        int res = saveChanges();
        if (res == 0) {
            FileChooser fileChooser = new FileChooser();

            String data = "";

            //Set extension filter for text files
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("HTML Files (*.html)", "*.html");
            fileChooser.getExtensionFilters().add(extFilter);

            //Show open file dialog
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                try {
                    FileReader fr = new FileReader(file);
                    Scanner scan = new Scanner(fr);

                    while (scan.hasNextLine()) {
                        data += scan.nextLine();
                    }

                    fr.close();
                    htmlEditor.setHtmlText(data);
                    htmlEditor.requestFocus();
                    lastSavedState = htmlEditor.getHtmlText();
                    currentFile = file;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private int saveChanges(){
        String currentState = htmlEditor.getHtmlText();
        if (!currentState.equals(lastSavedState)){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Unsaved changes");
            alert.setContentText("You have unsaved changes. Do you want to save them?");

            ButtonType buttonTypeYes = new ButtonType("Yes");
            ButtonType buttonTypeNo = new ButtonType("No");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeYes){
                if (saveButtonPressed() == 0){
                    return 0;
                }
                else{
                    return 1;
                }
            } else if (result.get() == buttonTypeNo) {
                return 0;
            } else {
                return 1;
            }
        }
        return 0;
    }

    private Stage stage;
    private String lastSavedState;
    private File currentFile;
    private HTMLEditor htmlEditor;
}
