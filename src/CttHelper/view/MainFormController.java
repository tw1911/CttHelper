package CttHelper.view;

import CttHelper.model.MappingXPath;
import CttHelper.model.MnemonicXPath;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
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
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

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
    @FXML
    private Label commPart;


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
        mnemonicTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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
                commPart.setText(mainApp.getCommonPart(newValue)));



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
        this.mainApp.generateMnemonic(mainApp.getCommonPart(commonDeph.getValue()));
        mnemonicXPath.setCellValueFactory(cellData -> cellData.getValue().MnemonicXPathProperty());
        mnemonicName.setCellValueFactory(cellData -> cellData.getValue().MnemonicNameProperty());

    }

    @FXML
    private void handleCopyAction(){
        StringBuilder forClipboard = new StringBuilder();
        ObservableList<MnemonicXPath> rowList = mnemonicTable.getSelectionModel().getSelectedItems();
        for (MnemonicXPath mnemonic :  rowList){
            forClipboard.append(mnemonic.getMnemonicName()+"="+mnemonic.getMnemonicXPath());
            forClipboard.append("\n");
        }
        final ClipboardContent content = new ClipboardContent();

        content.putString(forClipboard.toString());
        Clipboard.getSystemClipboard().setContent(content);
    }


}