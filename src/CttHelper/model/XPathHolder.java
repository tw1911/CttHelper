package CttHelper.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by tw on 01.04.2017.
 */
public class XPathHolder {
    private final StringProperty mappingXPath;
    private final StringProperty mnemonicXPath;


    public XPathHolder(){this(null,null);}

    public XPathHolder(String mapping){
        this.mappingXPath = new SimpleStringProperty(mapping);
        this.mnemonicXPath = new SimpleStringProperty(null);

    }

    public XPathHolder(String mapping, String mnemonic){
        this.mappingXPath = new SimpleStringProperty(mapping);
        this.mnemonicXPath = new SimpleStringProperty(mnemonic);

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

    public String getMappingXPath() {
        return mappingXPath.get();
    }

    public void setMappingXPath(String mappingXPath) {
        this.mappingXPath.set(mappingXPath);
    }

    public StringProperty MappingXPathProperty() {
        return mappingXPath;
    }

}
