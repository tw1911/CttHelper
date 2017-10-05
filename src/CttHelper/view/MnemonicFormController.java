package CttHelper.view;

import CttHelper.MainApp;
import CttHelper.model.MappingXPath;
import CttHelper.model.MnemonicXPath;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Created by rrumyantsev on 05.06.2017.
 */
public class MnemonicFormController {

    MainFormController MainForm;
    @FXML
    private TableView<MnemonicXPath> mnemonicTable;
    @FXML
    private TableColumn<MnemonicXPath, String> mnemonicXPath;
    @FXML
    private TableColumn<MnemonicXPath, String> mnemonicName;

    private MainApp mainApp;

    @FXML
    private void initialize() {
        mnemonicTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }

    public void setMainFormController(MainFormController MainForm){
        this.MainForm=MainForm;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        // Добавление в таблицу данных из наблюдаемого списка

    }

    @FXML
    private void handleGenerateCttMnemonic() {
        this.mainApp.generateMnemonic(mainApp.getCommonPart(MainForm.commonDeph.getValue()));
        mnemonicTable.setItems(mainApp.getMnemonicData());
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
