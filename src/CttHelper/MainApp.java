package CttHelper;

import CttHelper.model.MappingXPath;
import CttHelper.model.MnemonicXPath;
import CttHelper.view.MnemonicFormController;
import CttHelper.view.PutFormController;
import CttHelper.view.RootLayoutController;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.input.Clipboard;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import CttHelper.view.MainFormController;
import java.util.ArrayList;

import java.io.*;
import java.util.Arrays;

public class MainApp extends Application {

    private ObservableList<MappingXPath> mappingData = FXCollections.observableArrayList();
    private ObservableList<MnemonicXPath> mnemonicXPathsData = FXCollections.observableArrayList();
    private ObservableList<StringProperty> putExpressions = FXCollections.observableArrayList();

    public MainApp() {
        // В качестве образца добавляем некоторые данные
        mappingData.add(new MappingXPath("/some/somechild/somesubchild"));
        mappingData.add(new MappingXPath("/some/somechild/somesubchild2"));
        mappingData.add(new MappingXPath("/some/somechild/somesubchild3"));
        mappingData.add(new MappingXPath("/some/somechild/somesubchild4"));
        mappingData.add(new MappingXPath("/some/somechild/somesubchild5"));
    }

    public ObservableList<MappingXPath> getMappingData() {
        return mappingData;
    }
    public ObservableList<MnemonicXPath> getMnemonicData() {return mnemonicXPathsData;}
    public ObservableList<StringProperty> getPutExpressions(){return putExpressions;}


    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("CTT Helper");

        initRootLayout();

        showMainForm();
    }

    /**
     * Инициализирует корневой макет.
     */
    public void initRootLayout() {
        try {
            // Загружаем корневой макет из fxml файла.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = loader.load();

            // Отображаем сцену, содержащую корневой макет.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setHeight(768);
            primaryStage.setWidth(1024);

            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Показывает в корневом макете сведения об адресатах.
     */
    public void showMainForm() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/MainForm.fxml"));
            AnchorPane MainForm = (AnchorPane) loader.load();

            rootLayout.setCenter(MainForm);

            // Даём контроллеру доступ к главному приложению.
            MainFormController controller = loader.getController();
            controller.setMainApp(this);

            FXMLLoader loader1 = new FXMLLoader();
            loader1.setLocation(MainApp.class.getResource("view/PutForm.fxml"));
            AnchorPane putPane = (AnchorPane) loader1.load();
            controller.setTab("Put",putPane);
            // Даём контроллеру доступ к главному приложению.
            PutFormController putController = loader1.getController();
            putController.setMainApp(this);
            putController.setMainFormController(controller);

            FXMLLoader loader2 = new FXMLLoader();
            loader2.setLocation(MainApp.class.getResource("view/MnemonicForm.fxml"));
            AnchorPane mnemonicPane = (AnchorPane) loader2.load();
            controller.setTab("Mnemonic",mnemonicPane);
            MnemonicFormController mnemonicController = loader2.getController();
            mnemonicController.setMainApp(this);
            mnemonicController.setMainFormController(controller);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void loadMappingFromFile(File file){
        String line;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mappingData.clear();
        try {
            while ((line=reader.readLine())!=null){

                mappingData.add(new MappingXPath(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadMappingFromClipboard(){
        Clipboard clipboard = Clipboard.getSystemClipboard();
        if(clipboard.hasString()) {
            mappingData.clear();
            String clipboardString = clipboard.getString();
            String[] list = clipboardString.split("\\r?\\n");
            for (String line:list){
                mappingData.add(new MappingXPath(line));
            }
        }
    }

    public void generateMnemonic(String commPart){
        mnemonicXPathsData.clear();
        for(MappingXPath mapping: mappingData){
            mnemonicXPathsData.add(new MnemonicXPath(mapping,commPart));
        }

    }

    public void generatePut(int depth){
        putExpressions.clear();
        for(MappingXPath mapping: mappingData){
            putExpressions.add(new SimpleStringProperty(mapping.getMappingXPath()));
        }
    }

    public String getCommonPart(int depth){
        StringBuilder commPart = new StringBuilder();
        String[] xpath = mappingData.get(0).getMappingXPath().substring(1).split("/");
        for (int i=0; i<depth;i++){
            commPart.append("/"+xpath[i]);
        }
        return commPart.toString();
    }
}