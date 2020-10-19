package sample.ui;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Taquimecanografo extends Stage implements EventHandler<KeyEvent> {

    //Cambio de color
    boolean banderaColor= false;

    //Arreglos para etiquetar los botones
    private String arLblBtn1[] =
            {"ESC","F1","F2","F3","F4","F5","F6","F7","F8","F9","F10","F11","F12","PWR"};
    private String arLblBtn2[] =
            {"º","1","2","3","4","5","6","7","8","9","0","'","¡","BS"};

    //Elementos para el toolbar
    private ToolBar tlbMenu;
    private Button btnAbrir;

    //Elementos para la escritura
    private TextArea txtContenido, txtEscritura;

    //Elementos de las teclas
    private HBox[] arHBoxTeclas = new HBox[6];
    private VBox vBoxTeclado;
    private Button[] arBtnTeclado1 = new Button[14];
    private Button[] arBtnTeclado2 = new Button[14];

    //Elementos de agrupacion global
    private VBox vBoxPrincipal;
    private Scene escena;

    public Taquimecanografo(){
        crearUI();
        this.setTitle("Tutor de taquimecanografo");
        this.setScene(escena);
        this.show();
    }

    private void crearUI(){
        crearToolbar();
        crearEscritura();
        crearTeclado();

        vBoxPrincipal = new VBox();
        vBoxPrincipal.setSpacing(5);

        vBoxPrincipal.getChildren().addAll
                (tlbMenu, txtContenido,txtEscritura,vBoxTeclado);
        vBoxPrincipal.setSpacing(10);
        vBoxPrincipal.setPadding(new Insets(10));
        escena = new Scene(vBoxPrincipal,800,500);
    }

    private void crearTeclado() {
        vBoxTeclado = new VBox();
        vBoxTeclado.setSpacing(8);
        for (int i = 0; i < arHBoxTeclas.length; i++) {
            arHBoxTeclas[i] = new HBox();
        }

        for (int i = 0; i < arBtnTeclado1.length; i++) {
            arBtnTeclado1[i] = new Button(arLblBtn1[i]);
            arBtnTeclado2[i] = new Button(arLblBtn2[i]);
            arHBoxTeclas[0].getChildren().addAll(arBtnTeclado1[i]);
            arHBoxTeclas[1].getChildren().addAll(arBtnTeclado2[i]);
            arBtnTeclado1[i].setStyle("-fx-background-color: #8d8d8d");
            arBtnTeclado2[i].setStyle("-fx-background-color: #8d8d8d");

        }
        vBoxTeclado.getChildren().addAll(arHBoxTeclas[0],arHBoxTeclas[1]);
    }

    private void crearEscritura() {
        txtContenido= new TextArea();
        txtContenido.setPrefRowCount(6);
        txtContenido.setEditable(false);
        txtEscritura = new TextArea();
        txtEscritura.setPrefRowCount(6);
        txtEscritura.setOnKeyPressed(this);
        txtEscritura.setOnKeyReleased(this);
    }

    private void crearToolbar() {
        //crear una barra de herramientas
        tlbMenu = new ToolBar();
        btnAbrir = new Button();
        btnAbrir.setOnAction(event -> eventoTactil(1));
        btnAbrir.setPrefSize(64,64);

        //asignamos imagen al boton detro del toolbar
        Image img = new Image("sample/assets/open.png");
        ImageView imv = new ImageView(img);
        imv.setFitHeight(25);
        imv.setPreserveRatio(true);
        btnAbrir.setGraphic(imv);
        tlbMenu.getItems().addAll(btnAbrir);
    }

    private void eventoTactil(int opc) {
        switch (opc){
            case 1:
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Abrir archivo");
                File file = fileChooser.showOpenDialog(this);
                break;
        }
    }


    @Override
    public void handle(KeyEvent keyEvent) {
        switch (keyEvent.getCode().toString()){
            case "BACK_SPACE":
                if (banderaColor == false)
                    arBtnTeclado2[13].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado2[13].setStyle("-fx-background-color: #8d8d8d");
                break;
        }
        banderaColor = !banderaColor;
    }
}
