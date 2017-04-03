package CttHelper.view;

import CttHelper.model.MappingXPath;
import CttHelper.model.MnemonicXPath;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import CttHelper.MainApp;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.event.EventHandler;

public class MainFormController {
    @FXML
    private TableView<MappingXPath> xpathTable;
    @FXML
    private TableColumn<MappingXPath, String> mappingXpath;
    @FXML
    private TableColumn<MnemonicXPath, String> mnemonicXpath;


    // Ссылка на главное приложение.
    private MainApp mainApp;

    /**
     * Конструктор.
     * Конструктор вызывается раньше метода initialize().
     */
    public MainFormController() {
    }

    /**
     * Инициализация класса-контроллера. Этот метод вызывается автоматически
     * после того, как fxml-файл будет загружен.
     */
    @FXML
    private void initialize() {
        // Инициализация таблицы адресатов с двумя столбцами.


        mappingXpath.setCellValueFactory(new PropertyValueFactory<MappingXPath, String>("mappingXPath"));
        mappingXpath.setCellFactory(TextFieldTableCell.forTableColumn());
        mappingXpath.setOnEditCommit(
                new EventHandler<CellEditEvent<MappingXPath, String>>() {
                    @Override
                    public void handle(CellEditEvent<MappingXPath, String> t) {
                        ((MappingXPath) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setMappingXPath(t.getNewValue());
                    }
                }
        );
        mnemonicXpath.setCellValueFactory(cellData -> cellData.getValue().MnemonicXPathProperty());

        // Очистка дополнительной информации об адресате.

        // Слушаем изменения выбора, и при изменении отображаем
        // дополнительную информацию об адресате.
//        xpathTable.getSelectionModel().selectedItemProperty().addListener(
//                (observable, oldValue, newValue) -> showPersonDetails(newValue));

    }

    /**
     * Вызывается главным приложением, которое даёт на себя ссылку.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Добавление в таблицу данных из наблюдаемого списка
        xpathTable.setItems(mainApp.getMappingData());
    }


    @FXML
    private void handleGeneratePerson() {
        xpathTable.getItems().remove(1);
    }


}