package CttHelper.view;

import CttHelper.model.MappingXPath;
import CttHelper.model.MnemonicXPath;
import javafx.beans.value.ChangeListener;
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
    private TableView<MappingXPath> mappingTable;
    @FXML
    private TableView<MnemonicXPath> mnemonicTable;
    @FXML
    private TableColumn<MappingXPath, String> mappingXpath;
    @FXML
    private TableColumn<MnemonicXPath, String> mnemonicXPath;
    @FXML
    private TableColumn<MnemonicXPath, String> mnemonicName;
    @FXML
    Spinner<Integer> commonDeph = new Spinner<Integer>();
    final int initialValue = 0;
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

        // Value factory.
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 3, initialValue);

        commonDeph.setValueFactory(valueFactory);
        commonDeph.valueProperty().addListener((obs, oldValue, newValue) ->
                System.out.println("New value: "+newValue));



    }

    /**
     * Вызывается главным приложением, которое даёт на себя ссылку.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Добавление в таблицу данных из наблюдаемого списка
        mappingTable.setItems(mainApp.getMappingData());
        mnemonicTable.setItems(mainApp.getMnemonicData());
    }


    @FXML
    private void handleGenerateCttMnemonic() {
        this.mainApp.generateMnemonic();
        System.out.println("generate button");
        mnemonicXPath.setCellValueFactory(cellData -> cellData.getValue().MnemonicXPathProperty());
        mnemonicName.setCellValueFactory(cellData -> cellData.getValue().MnemonicNameProperty());
    }

    @FXML
    private void handleSpinner(){
        System.out.println(commonDeph.getValue());
    }

}