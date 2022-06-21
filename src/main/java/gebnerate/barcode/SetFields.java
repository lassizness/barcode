package gebnerate.barcode;

import gebnerate.barcode.controller.HistoryController;

public class SetFields extends HistoryController {

    private int id;
    private String barcode;
    private String type;


    public SetFields(int id, String barcode, String type) {
        this.id = id;
        this.barcode = barcode;
        this.type = type;

    }

    public int getId() {
        return id;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setType(String type) {
        this.type = type;
    }


}
