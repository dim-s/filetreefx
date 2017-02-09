package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.component.AbstractDirectoryTreeSource;
import sample.component.DirectoryTreeValue;
import sample.component.DirectoryTreeView;
import sample.component.FileDirectoryTreeSource;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        VBox root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        root.setSpacing(5);
        root.setPadding(new Insets(3));


        Button button = new Button("Select Folder");
        button.setPrefHeight(30);
        button.setMaxWidth(9999);

        Button refreshButton = new Button("Refresh");
        refreshButton.setMaxWidth(99999);

        DirectoryTreeView tree = new DirectoryTreeView(new FileDirectoryTreeSource(new File("D:/Trash")));
        tree.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tree.setEditable(true);

        tree.getRoot().setExpanded(true);

        tree.setOnMouseClicked(event -> {
            if (event.getClickCount() > 1) {
                Desktop desktop = Desktop.getDesktop();

                FileDirectoryTreeSource treeSource = (FileDirectoryTreeSource) tree.getTreeSource();
                try {
                    TreeItem<DirectoryTreeValue> focusedItem = tree.getFocusModel().getFocusedItem();

                    if (focusedItem != null) {
                        File file = new File(treeSource.getDirectory(), focusedItem.getValue().getPath());
                        if (file.isDirectory()) {
                            //desktop.open(file);
                        } else {
                            desktop.open(file);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        primaryStage.setOnHidden(event -> tree.getTreeSource().shutdown());

        button.setOnMouseClicked(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();

            File file = directoryChooser.showDialog(primaryStage);

            if (file != null) {
                tree.setTreeSource(new FileDirectoryTreeSource(file));
            }
        });

        refreshButton.setOnMouseClicked(event -> tree.refresh("/"));

        root.getChildren().add(button);
        root.getChildren().add(refreshButton);
        root.getChildren().add(tree);
        VBox.setVgrow(tree, Priority.ALWAYS);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 500, 700));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
