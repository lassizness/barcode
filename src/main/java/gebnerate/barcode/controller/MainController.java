package gebnerate.barcode.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import gebnerate.barcode.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class MainController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void onExitApplication(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void onMenuAbout(ActionEvent event) throws IOException {

        Parent home_page_parent = FXMLLoader.load(getClass().getResource("/gebnerate/barcode/about.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);

        Stage ModalGenerate = new Stage();//Новое окно
        MainApplication ModalWindow = new MainApplication();//Экземляр класса с родительским окном

        ModalGenerate.initOwner(ModalWindow.getWindow());//Указываем новому окну, родителя. getWindow() гетер родительского окна
        ModalGenerate.initModality(Modality.APPLICATION_MODAL);//задаем модальность
        ModalGenerate.setScene(home_page_scene);//подгружаем сцену
        ModalGenerate.setTitle("О программе");
        ModalGenerate.showAndWait();

    }

    @FXML
    void onMenuConnectedBD(ActionEvent event) {

    }

    @FXML
    void onMenuGenerate(ActionEvent event) throws IOException {

        Parent home_page_parent = FXMLLoader.load(getClass().getResource("/gebnerate/barcode/generate.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);

       /* Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(home_page_scene);
        app_stage.show();*/

        Stage ModalGenerate = new Stage();//Новое окно
        MainApplication ModalWindow = new MainApplication();//Экземляр класса с родительским окном

        ModalGenerate.initOwner(ModalWindow.getWindow());//Указываем новому окну, родителя. getWindow() гетер родительского окна
        ModalGenerate.initModality(Modality.APPLICATION_MODAL);//задаем модальность
        ModalGenerate.setScene(home_page_scene);//подгружаем сцену
        ModalGenerate.setTitle("Генерация баркодов");
        ModalGenerate.showAndWait();

    }

    @FXML
    void onMenuViewHistory(ActionEvent event) throws IOException {

        Parent home_page_parent = FXMLLoader.load(getClass().getResource("/gebnerate/barcode/history.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);

        Stage ModalGenerate = new Stage();//Новое окно
        MainApplication ModalWindow = new MainApplication();//Экземляр класса с родительским окном

        ModalGenerate.initOwner(ModalWindow.getWindow());//Указываем новому окну, родителя. getWindow() гетер родительского окна
        ModalGenerate.initModality(Modality.APPLICATION_MODAL);//задаем модальность
        ModalGenerate.setScene(home_page_scene);//подгружаем сцену
        ModalGenerate.setTitle("История баркодов");
        ModalGenerate.showAndWait();
    }

    @FXML
    void onMenuViewReserv(ActionEvent event) {

    }

    @FXML
    void initialize() {

    }
}

