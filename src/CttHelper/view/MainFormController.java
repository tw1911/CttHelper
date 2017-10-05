package CttHelper.view;

import CttHelper.model.MappingXPath;
import CttHelper.model.MnemonicXPath;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;

public class MainFormController {
    @FXML
    private TableView<MappingXPath> mappingTable;
    @FXML
    private TableColumn<MappingXPath, String> mappingXpath;
    @FXML
    Spinner<Integer> commonDeph = new Spinner<Integer>();
    final int initialValue = 0;
    @FXML
    private Label commPart;
    @FXML
    private Tab PutTab,MnemonicTab;

    public void setTab(String tabName,AnchorPane content){
        if(tabName=="Put"){PutTab.setContent(content);}
        if(tabName=="Mnemonic"){MnemonicTab.setContent(content);}
    }

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

        mappingTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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
    }

    @FXML
    private void handlePasteAction(){
        this.mainApp.loadMappingFromClipboard();
    }

}