module gebnerate.barcode {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires barbecue;
    requires java.desktop;


    opens gebnerate.barcode to javafx.fxml;
    exports gebnerate.barcode;
    exports gebnerate.barcode.controller;
    opens gebnerate.barcode.controller to javafx.fxml;
}