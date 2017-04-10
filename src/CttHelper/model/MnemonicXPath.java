package CttHelper.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by tw on 01.04.2017.
 */
public class MnemonicXPath {



    private final StringProperty mnemonicXPath;
    private final StringProperty mnemonicName;

    public MnemonicXPath(MappingXPath mapping,String commPart){
        this.mnemonicXPath = new SimpleStringProperty(getXPathFromMapping(mapping.getMappingXPath()));
        this.mnemonicName = new SimpleStringProperty(getMnemonicNameFromMapping(mapping.getMappingXPath(),commPart));
    }

    public MnemonicXPath(String name, String xpath) {
        this.mnemonicXPath = new SimpleStringProperty(xpath);
        this.mnemonicName = new SimpleStringProperty(name);
    }

    public String getMnemonicXPath() {
        return mnemonicXPath.get();
    }

    public String getMnemonicName(){
        return mnemonicName.get();
    }

    public void setMnemonicXPath(String mappingXPath) {
        this.mnemonicXPath.set(mappingXPath);
    }
    public void setMnemonicName(String mnemonicName) {this.mnemonicName.set(mnemonicName);}

    public StringProperty MnemonicXPathProperty() {
        return mnemonicXPath;
    }

    public StringProperty MnemonicNameProperty() {return mnemonicName; }

    public static String getXPathFromMapping(String mapping){
        String[] elements = mapping.substring(1).split("/");
        StringBuilder xpath = new StringBuilder();
        for (String element : elements){
            xpath.append("/*[local-name()='"+element+"']");
        }
        return xpath.toString();
    }

    public static String getMnemonicNameFromMapping(String mapping,String commonPart){
        if(commonPart.length()>=mapping.length()){
            return "to long common part";
        }
        if(mapping.indexOf(commonPart)!=-1){
            mapping = mapping.substring(commonPart.length());
        }
        String[] elements = mapping.substring(1).split("/");
        StringBuilder name = new StringBuilder();
        for (String element : elements){
            name.append(element+"_");
        }
        name.deleteCharAt(name.length()-1);
        return name.toString();
    }
}