package CttHelper.view;

import CttHelper.MainApp;
import CttHelper.model.MappingXPath;
import CttHelper.model.MnemonicXPath;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public class PutFormController {
    MainFormController MainForm;
    @FXML
    private TableView<StringProperty> putTable;
    @FXML
    private TableColumn<StringProperty, String> putLines;

    public void setMainFormController(MainFormController MainForm){
        this.MainForm=MainForm;
    }

    // Ссылка на главное приложение.
    private MainApp mainApp;

    /**
     * Конструктор.
     * Конструктор вызывается раньше метода initialize().
     */
    public PutFormController() {
    }

    /**
     * Инициализация класса-контроллера. Этот метод вызывается автоматически
     * после того, как fxml-файл будет загружен.
     */
    @FXML
    private void initialize() {
        putTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        putLines.setCellValueFactory(new PropertyValueFactory<StringProperty, String>("Put Expression"));

    }

    /**
     * Вызывается главным приложением, которое даёт на себя ссылку.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Добавление в таблицу данных из наблюдаемого списка
        //putTable.setItems(mainApp.getMappingData());
    }


    @FXML
    private void handleGenerate() {
        System.out.println("Generate Put Form Action");
        this.mainApp.generatePut(MainForm.commonDeph.getValue());
        putTable.setItems(mainApp.getPutExpressions());
        putLines.setCellValueFactory(cellData -> cellData.getValue());


    }

    @FXML
    private void handleCopyAction(){

        StringBuilder forClipboard = new StringBuilder();
        ObservableList<StringProperty> rowList = putTable.getSelectionModel().getSelectedItems();
        for (StringProperty putExpr :  rowList){
            forClipboard.append(putExpr.getValue());
            forClipboard.append("\n");
        }
        final ClipboardContent content = new ClipboardContent();

        content.putString(forClipboard.toString());
        Clipboard.getSystemClipboard().setContent(content);
    }

}