package et.artisan.cn.cps.entity;

import et.artisan.cn.cps.util.CommonStorage;
import java.util.ArrayList;

/**
 *
 * @author Yoseph Berhanu<yoseph@artisan.et>
 * @since 1.0
 * @version 1.0
 */
public class Role implements Entity {

    private byte code;
    private String textValue;
    private String englishName;
    private String localName;
    private ArrayList<String> validationMessage;

    private static final String labels[] = {"default", "primary", "info", "info", "success", "success", "success", "warning", "danger"};
    private static final String colors[] = {"grey", "red", "blue", "blue", "green", "green", "green", "warning", "danger"};

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getLabel() {
        return labels[code];
    }

    public String getColor() {
        return colors[code];
    }

    @Override
    public ArrayList<String> getValidationMessage() {
        return this.validationMessage;
    }
    
    @Override
    public boolean valideForSave() {
        validationMessage = new ArrayList<>();
        return true;
    }
    @Override
    public boolean valideForUpdate() {
        validationMessage = new ArrayList<>();
        return true;
    }

    public static Role create(int id) {
        return CommonStorage.getRepository().getRole(id);
    }
}
