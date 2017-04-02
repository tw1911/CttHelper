package CttHelper;

import CttHelper.model.MappingXPath;
import CttHelper.model.XPathHolder;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import CttHelper.view.MainFormController;

import java.io.IOException;

public class MainApp extends Application {

    private ObservableList<XPathHolder> mappingData = FXCollections.observableArrayList();

    public MainApp() {
        // В качестве образца добавляем некоторые данные
        mappingData.add(new XPathHolder("/some/somechild/somesubchild"));
        mappingData.add(new XPathHolder("/some/somechild/somesubchild2"));
        mappingData.add(new XPathHolder("/some/somechild/somesubchild3"));
        mappingData.add(new XPathHolder("/some/somechild/somesubchild4"));
        mappingData.add(new XPathHolder("/some/somechild/somesubchild5"));
    }

    public ObservableList<XPathHolder> getMappingData() {
        return mappingData;
    }

    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");

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
            TabPane personOverview = (TabPane) loader.load();

            // Помещаем сведения об адресатах в центр корневого макета.
            rootLayout.setCenter(personOverview);

            // Даём контроллеру доступ к главному приложению.
            MainFormController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Возвращает главную сцену.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

}