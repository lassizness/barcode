package gebnerate.barcode.controller;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

import gebnerate.barcode.ConnectBD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyEvent;


import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;


public class GenerateController {


    /*private int[] AllowedValues = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        private KeyCode[] AllowedButton = {NUMPAD0, NUMPAD1, NUMPAD2, NUMPAD3, NUMPAD4, NUMPAD5, NUMPAD6, NUMPAD7, NUMPAD8, NUMPAD9, DIGIT0, DIGIT1,
                DIGIT2, DIGIT3, DIGIT4, DIGIT5, DIGIT6, DIGIT7, DIGIT8, DIGIT9};*/
    //private String[] typeBarcode={"EAN13"=>"13","EAN8"=>"8"};
    public Map<String, Integer> typeBarcode;
    private String barcode;

    ConnectBD connectBD = new ConnectBD();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private RadioButton Code39;

    @FXML
    private RadioButton EAN128;

    @FXML
    private RadioButton EAN13;

    @FXML
    private RadioButton EAN8;

    @FXML
    private Button generateBracode;

    @FXML
    private ToggleGroup group;

    @FXML
    private TextField maskBarcode;

    @FXML
    private TextField resultGenerationBarcode;

    @FXML
    private Label error;

    @FXML
    private TextField articul;

    @FXML
    private Button saveClipBoard;

    @FXML
    private ImageView imgbar;

    @FXML
    void changeTest(KeyEvent event) {

    }

    @FXML
    void chekMaskBarcode(ActionEvent event) {
    }

    @FXML
    void onClickGenBracode(ActionEvent event) throws SQLException {
        String sqlquery, type = "", temp = maskBarcode.getText();
        int typeId = 0;

        if (EAN13.isSelected()) {//блок проверки типа штрихкода
            typeId = typeBarcode.get(EAN13.getText());
            type = EAN13.getText();
        }
        if (EAN8.isSelected()) {
            typeId = typeBarcode.get(EAN8.getText());
            type = EAN8.getText();
        }
        if (EAN128.isSelected()) {
            typeId = typeBarcode.get(EAN128.getText());
            type = EAN128.getText();
        }

        connectBD.open();
        while (true) {
            barcode = generateBracode(typeId, temp);

            sqlquery = "select barcode from history where barcode=" + barcode + ";";


            String resultQuery = connectBD.query(sqlquery);

            //String type = getKeyMap(typeBarcode, typeId);

            if (resultQuery == "noresultata") {
                sqlquery = "insert into history (barcode,type) values (" + barcode + ",\"" + type + "\")";
                resultQuery = connectBD.query(sqlquery);
                break;
            }

        }
        connectBD.close();
        resultGenerationBarcode.setText(barcode);

    }

    @FXML
    void saveClipBoard(ActionEvent event) throws Exception {

        String resultQuery, sqlquery = "", type = "";
        int typeId;

        if (EAN13.isSelected()) {//блок проверки типа штрихкода
            typeId = typeBarcode.get(EAN13.getText());
            type = EAN13.getText();
        }
        if (EAN8.isSelected()) {
            typeId = typeBarcode.get(EAN8.getText());
            type = EAN8.getText();
        }
        if (EAN128.isSelected()) {
            typeId = typeBarcode.get(EAN128.getText());
            type = EAN128.getText();
        }


        connectBD.open();

        sqlquery = "select product from reserv where barcode=" + barcode + ";";


        resultQuery = connectBD.query(sqlquery);

        //String type = getKeyMap(typeBarcode, typeId);

        if (resultQuery == "noresultata") {
            sqlquery = "insert into reserv (barcode,type,product) values (" + barcode + ",\"" + type + "\"" + articul.getText() + ")";
            resultQuery = connectBD.query(sqlquery);

            ClipboardContent clipboardContent = new ClipboardContent();
            clipboardContent.putString(resultGenerationBarcode.getText());
            Clipboard.getSystemClipboard().setContent(clipboardContent);
            imgbar.setImage(convertToFxImage(generateEAN13BarcodeImage(resultGenerationBarcode.getText())));


        } else {
            error.isVisible();
            error.setText("Ошибк. Штрихкод уже сипользуется с " + resultQuery);

        }


        connectBD.close();


    }

    @FXML
    void initialize() {
        typeBarcode = new HashMap<String, Integer>();
        typeBarcode.put("EAN13", 12);
        typeBarcode.put("EAN8", 8);
        typeBarcode.put("EAN128", 128);

    }

    @FXML
    private String generateBracode(int typeBarcode, String maskBarcode) {

        String res = "";
        Random random = new Random();
        if (maskBarcode.length() > 0) {
            res += maskBarcode;
        }

        // System.out.println(maskBarcode.length());
        for (int i = 0; i < typeBarcode - maskBarcode.length(); i++) {
            if (i == 0 && maskBarcode.length() <= 0) {//Первая цифра штрих кода не может быть 0
                res += String.valueOf(random.nextInt(1, 9));
            } else {
                res += String.valueOf(random.nextInt(0, 9));
            }
        }

        return res;

    }

    public static BufferedImage generateEAN13BarcodeImage(String barcodeText) throws Exception {
        Font myFont = new Font("Serif", Font.BOLD, 12);
        Barcode barcode = BarcodeFactory.createEAN13(barcodeText);
        barcode.setFont(myFont);

        return BarcodeImageHandler.getImage(barcode);
    }

    private static Image convertToFxImage(BufferedImage image) {
        WritableImage wr = null;
        if (image != null) {
            wr = new WritableImage(image.getWidth(), image.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    pw.setArgb(x, y, image.getRGB(x, y));
                }
            }
        }

        return new ImageView(wr).getImage();
    }
   /* public String getKeyMap(Map typeBarcode, int typeId) {

        Iterator it = typeBarcode.entrySet().iterator();
        String type = "";

        while (it.hasNext()) {
            Map.Entry res = (Map.Entry) it.next();
            if (res.getValue() == String.valueOf(typeId)) {
                System.out.println(res.getKey());
                type = res.getKey().toString();
                return type;
            }
            it.remove(); // дабы не было ConcurrentModificationException
        }
        return "";
    }*/

}
