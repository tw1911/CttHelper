package CttHelper.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by tw on 01.04.2017.
 */
public class MappingXPath {

    private final StringProperty mappingXPath;

    public MappingXPath(){this(null);}


    public MappingXPath(String xpath) {
        this.mappingXPath = new SimpleStringProperty(xpath);
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
