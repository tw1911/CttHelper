package CttHelper;

import CttHelper.model.MappingXPath;
import CttHelper.model.MnemonicXPath;
import CttHelper.view.RootLayoutController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
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
            // Загружаем сведения об адресатах.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/MainForm.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Помещаем сведения об адресатах в центр корневого макета.
            rootLayout.setCenter(personOverview);

            // Даём контроллеру доступ к главному приложению.
            MainFormController controller = loader.getController();
            controller.setMainApp(this);

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
        getCommonPartDepth(mappingData);
    }

    public void generateMnemonic(){
        for(MappingXPath mapping: mappingData){
            mnemonicXPathsData.add(new MnemonicXPath(mapping));
        }
        for (MnemonicXPath xpath: mnemonicXPathsData){
            System.out.println(xpath.getMnemonicName()+"="+xpath.getMnemonicXPath());
        }
    }

    public int getCommonPartDepth(ObservableList<MappingXPath> mapping){
        ArrayList<ArrayList<String>> matrix = new ArrayList<>();
        for (MappingXPath xpath:mapping){
            matrix.add(new ArrayList<>(Arrays.asList(xpath.getMappingXPath().split("/"))));
        }
        for (ArrayList<String> tmp: matrix){
            System.out.println(tmp);
        }
        return 0;
    }

}