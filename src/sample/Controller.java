package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.swing.text.html.HTML;
import java.awt.event.WindowAdapter;
import java.io.File;
import java.io.FileWriter;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;

public class Controller {

    @FXML public BorderPane mainPane;
    @FXML public HTMLEditor htmlEditor;
    @FXML public MenuBar menu;

    protected void setStageAndSetupListeners(Stage stage){

        ButtonHandler bh = new ButtonHandler(stage, htmlEditor);

        // Create menus
        Menu fileMenu = new Menu("File");
        Menu editMenu = new Menu("Edit");
        Menu helpMenu = new Menu("Help");

        // Create MenuItems
        MenuItem newItem = new MenuItem("New");
        MenuItem openFileItem = new MenuItem("Open");
        MenuItem saveFileItem = new MenuItem("Save");
        MenuItem saveAsFileItem = new MenuItem("Save as");
        MenuItem exitItem = new MenuItem("Exit");

        MenuItem undoItem = new MenuItem("Undo");
        MenuItem redoItem = new MenuItem("Redo");
        MenuItem cutItem = new MenuItem("Cut");
        MenuItem copyItem = new MenuItem("Copy");
        MenuItem pasteItem = new MenuItem("Paste");
        MenuItem deleteItem = new MenuItem("Delete");
        MenuItem selectAllItem = new MenuItem("Select all");

        // Add menuItems to the Menus
        fileMenu.getItems().addAll(newItem, openFileItem, saveFileItem, saveAsFileItem, exitItem);
        editMenu.getItems().addAll(undoItem, redoItem, cutItem, copyItem, pasteItem, deleteItem, selectAllItem);

        // Set Accelerators
        newItem.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
        saveFileItem.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));

        undoItem.setAccelerator(KeyCombination.keyCombination("Ctrl+Z"));
        redoItem.setAccelerator(KeyCombination.keyCombination("Ctrl+R"));
        cutItem.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
        copyItem.setAccelerator(KeyCombination.keyCombination("Ctrl+C"));
        pasteItem.setAccelerator(KeyCombination.keyCombination("Ctrl+V"));
        deleteItem.setAccelerator(KeyCombination.keyCombination("Delete"));
        selectAllItem.setAccelerator(KeyCombination.keyCombination("Ctrl+A"));


        // Set menu actions
        newItem.setOnAction(e -> bh.newButtonPressed());

        exitItem.setOnAction(e -> bh.exitButtonPressed());

        saveFileItem.setOnAction(e -> bh.saveButtonPressed());

        saveAsFileItem.setOnAction(e -> bh.saveAsButtonPressed());

        openFileItem.setOnAction(e -> bh.loadButtonPressed());
        stage.setOnCloseRequest(e -> {
            if (bh.exitButtonPressed() == 1){
                e.consume();
            }
        });

        // Add Menus to the MenuBar
        menu.getMenus().addAll(fileMenu, editMenu, helpMenu);
    }

    @FXML
    protected void initialize() {

    }
}
