package CttHelper.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by tw on 01.04.2017.
 */
public class MnemonicXPath {

    private final StringProperty mnemonicXPath;

    public MnemonicXPath(){this(null);}


    public MnemonicXPath(String xpath) {
        this.mnemonicXPath = new SimpleStringProperty(xpath);
    }

    public String getMnemonicXPath() {
        return mnemonicXPath.get();
    }

    public void setMnemonicXPath(String mappingXPath) {
        this.mnemonicXPath.set(mappingXPath);
    }

    public StringProperty MnemonicXPathProperty() {
        return mnemonicXPath;
    }

}