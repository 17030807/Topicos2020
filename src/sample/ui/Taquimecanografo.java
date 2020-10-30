package sample.ui;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Taquimecanografo extends Stage implements EventHandler<KeyEvent> {

    //Cambio de color
    boolean banderaColor= false;

    //Conteo de errores
    private int contador = 0;
    private int errores = 0;

    //Arreglos para etiquetar los botones
    private String arLblBtn1[] =
            {"ESC","F1","F2","F3","F4","F5","F6","F7","F8","F9","F10","F11","F12","INS"};
    private String arLblBtn2[] =
            {"    º    ","1","2","3","4","5","6","7","8","9","0"," ' ","  ¡  ","           BS          "};
    private String arLblBtn3[] =
            {"        TAB        ","Q","W","E","R","T","Y","U","I","O","P"," ´ "," + ","     }     "};
    private String arLblBtn4[] =
            {"      MAYUS      ","A","S","D","F","G","H","J","K","L","Ñ"," { ","     ENTER    "};
    private String arLblBtn5[] =
            {"      SHIFT     ","Z","X","C","V","B","N","M"," , "," . "," - ","          SHIFT        "};
    private String arLblBtn6[] =
            {"  CTRL  ","FN","WIN"," ALT ","             SPACE              ","ALT GR"," < ","CTRL"};

    //Elementos del cronometro
    private Timer time;
    private Button btnEmpezar;
    private Button btnRevizar;
    private HBox hbCrono;
    private int m=0,s=0,cs=0;
    String tiempo;


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
    private Button[] arBtnTeclado3 = new Button[14];
    private Button[] arBtnTeclado4 = new Button[13];
    private Button[] arBtnTeclado5 = new Button[12];
    private Button[] arBtnTeclado6 = new Button[8];

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

        time = new Timer(10,accion);

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
            arBtnTeclado3[i] = new Button(arLblBtn3[i]);
            arHBoxTeclas[0].getChildren().addAll(arBtnTeclado1[i]);
            arHBoxTeclas[0].setAlignment(Pos.CENTER);
            arHBoxTeclas[1].getChildren().addAll(arBtnTeclado2[i]);
            arHBoxTeclas[1].setAlignment(Pos.CENTER);
            arHBoxTeclas[2].getChildren().addAll(arBtnTeclado3[i]);
            arHBoxTeclas[2].setAlignment(Pos.CENTER);
            arBtnTeclado1[i].setStyle("-fx-background-color: #8d8d8d");
            arBtnTeclado2[i].setStyle("-fx-background-color: #8d8d8d");
            arBtnTeclado3[i].setStyle("-fx-background-color: #8d8d8d");
            if(i<13){
                arBtnTeclado4[i] = new Button(arLblBtn4[i]);
                arHBoxTeclas[3].getChildren().addAll(arBtnTeclado4[i]);
                arHBoxTeclas[3].setAlignment(Pos.CENTER);
                arBtnTeclado4[i].setStyle("-fx-background-color: #8d8d8d");
            }
            if (i<12){
                arBtnTeclado5[i] = new Button(arLblBtn5[i]);
                arHBoxTeclas[4].getChildren().addAll(arBtnTeclado5[i]);
                arHBoxTeclas[4].setAlignment(Pos.CENTER);
                arBtnTeclado5[i].setStyle("-fx-background-color: #8d8d8d");
            }
            if (i<8){
                arBtnTeclado6[i] = new Button(arLblBtn6[i]);
                arHBoxTeclas[5].getChildren().addAll(arBtnTeclado6[i]);
                arHBoxTeclas[5].setAlignment(Pos.CENTER);
                arBtnTeclado6[i].setStyle("-fx-background-color: #8d8d8d");
            }

        }

        btnEmpezar = new Button("Empezar");
        btnEmpezar.setDisable(true);
        btnEmpezar.setOnAction(event -> eventoTactil(2));

        btnRevizar = new Button("Revisar");
        btnRevizar.setDisable(true);
        btnRevizar.setOnAction(event -> eventoTactil(3));

        hbCrono = new HBox();
        hbCrono.setAlignment(Pos.CENTER);
        hbCrono.getChildren().addAll(btnEmpezar,btnRevizar);

        vBoxTeclado.getChildren().addAll(arHBoxTeclas[0],arHBoxTeclas[1],arHBoxTeclas[2],arHBoxTeclas[3],arHBoxTeclas[4],arHBoxTeclas[5],hbCrono);

    }

    private void crearEscritura() {
        txtContenido= new TextArea();
        txtContenido.setPrefRowCount(6);
        txtContenido.setEditable(false);
        txtEscritura = new TextArea();
        txtEscritura.setPrefRowCount(6);
        txtEscritura.setEditable(false);
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
                try {
                    FileReader fr = new FileReader(file);
                    BufferedReader br = new BufferedReader(fr);
                    txtContenido.setText(br.readLine());
                    btnEmpezar.setDisable(false);
                }catch (Exception e){

                }

                break;
            case 2:
                System.out.println("Timer on");
                txtEscritura.setEditable(true);
                time.start();
                btnEmpezar.setDisable(true);
                btnRevizar.setDisable(false);
                break;
            case 3:

                if (txtContenido.getText().equals(txtEscritura.getText())){
                    time.stop();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Felicidades");
                    alert.setHeaderText("Has terminado en "+ tiempo + " con "+ errores +" error/es :D");
                    alert.showAndWait();
                    this.close();
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText("Textos no coinciden :C");
                    alert.showAndWait();
                    errores++;
                }

        }
    }

    private  ActionListener accion = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            cs++;
            if (cs==100){
                cs=0;
                s++;
            }
            if (s==60){
                s=0;
                m++;
            }
            actTiempo();
        }
    };

    private void actTiempo() {
        tiempo = (m<=9?"0":"")+m+":"+(s<=9?"0":"")+s+":"+(cs<=9?"0":"")+cs;
        //lblCrono.setText(tiempo+"");
        System.out.println(tiempo);
    }

    private void leeArchivo() throws FileNotFoundException {
        File archivo = new File ("C:\\archivo.txt");
        FileReader fr = new FileReader(archivo);
        BufferedReader br = new BufferedReader(fr);
    }


    //TECLAS
    @Override
    public void handle(KeyEvent keyEvent) {
        //System.out.println(keyEvent.getCode().toString());
        if (contador == 1) {
            errores++;
        }
        if(keyEvent.getCode().toString() != "BACK_SPACE"){
            contador = 0;
        }
        switch (keyEvent.getCode().toString()){
            case "ESCAPE":
                if (banderaColor == false)
                    arBtnTeclado1[0].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado1[0].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "F1":
                if (banderaColor == false)
                    arBtnTeclado1[1].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado1[1].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "F2":
                if (banderaColor == false)
                    arBtnTeclado1[2].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado1[2].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "F3":
                if (banderaColor == false)
                    arBtnTeclado1[3].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado1[3].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "F4":
                if (banderaColor == false)
                    arBtnTeclado1[4].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado1[4].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "F5":
                if (banderaColor == false)
                    arBtnTeclado1[5].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado1[5].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "F6":
                if (banderaColor == false)
                    arBtnTeclado1[6].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado1[6].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "F7":
                if (banderaColor == false)
                    arBtnTeclado1[7].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado1[7].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "F8":
                if (banderaColor == false)
                    arBtnTeclado1[8].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado1[8].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "F9":
                if (banderaColor == false)
                    arBtnTeclado1[9].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado1[9].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "F10":
                if (banderaColor == false)
                    arBtnTeclado1[10].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado1[10].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "F11":
                if (banderaColor == false)
                    arBtnTeclado1[11].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado1[11].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "F12":
                if (banderaColor == false)
                    arBtnTeclado1[12].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado1[12].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "INSERT":
                if (banderaColor == false)
                    arBtnTeclado1[13].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado1[13].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "UNDEFINED":
                if (banderaColor == false)
                    arBtnTeclado2[0].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado2[0].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "DIGIT1":
                if (banderaColor == false)
                    arBtnTeclado2[1].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado2[1].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "DIGIT2":
                if (banderaColor == false)
                    arBtnTeclado2[2].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado2[2].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "DIGIT3":
                if (banderaColor == false)
                    arBtnTeclado2[3].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado2[3].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "DIGIT4":
                if (banderaColor == false)
                    arBtnTeclado2[4].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado2[4].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "DIGIT5":
                if (banderaColor == false)
                    arBtnTeclado2[5].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado2[5].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "DIGIT6":
                if (banderaColor == false)
                    arBtnTeclado2[6].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado2[6].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "DIGIT7":
                if (banderaColor == false)
                    arBtnTeclado2[7].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado2[7].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "DIGIT8":
                if (banderaColor == false)
                    arBtnTeclado2[8].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado2[8].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "DIGIT9":
                if (banderaColor == false)
                    arBtnTeclado2[9].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado2[9].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "DIGIT0":
                if (banderaColor == false)
                    arBtnTeclado2[10].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado2[10].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "QUOTE":
                if (banderaColor == false)
                    arBtnTeclado2[11].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado2[11].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "BACK_SPACE":
                if (banderaColor == false)
                    arBtnTeclado2[13].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado2[13].setStyle("-fx-background-color: #8d8d8d");

                contador++;
                break;
            case "TAB":
                if (banderaColor == false)
                    arBtnTeclado3[0].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado3[0].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "Q":
                if (banderaColor == false)
                    arBtnTeclado3[1].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado3[1].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "W":
                if (banderaColor == false)
                    arBtnTeclado3[2].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado3[2].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "E":
                if (banderaColor == false)
                    arBtnTeclado3[3].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado3[3].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "R":
                if (banderaColor == false)
                    arBtnTeclado3[4].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado3[4].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "T":
                if (banderaColor == false)
                    arBtnTeclado3[5].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado3[5].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "Y":
                if (banderaColor == false)
                    arBtnTeclado3[6].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado3[6].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "U":
                if (banderaColor == false)
                    arBtnTeclado3[7].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado3[7].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "I":
                if (banderaColor == false)
                    arBtnTeclado3[8].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado3[8].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "O":
                if (banderaColor == false)
                    arBtnTeclado3[9].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado3[9].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "P":
                if (banderaColor == false)
                    arBtnTeclado3[10].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado3[10].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "DEAD_ACUTE":
                if (banderaColor == false)
                    arBtnTeclado3[11].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado3[11].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "PLUS":
                if (banderaColor == false)
                    arBtnTeclado3[12].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado3[12].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "BRACERIGHT":
                if (banderaColor == false)
                    arBtnTeclado3[13].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado3[13].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "CAPS":
                if (banderaColor == false)
                    arBtnTeclado4[0].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado4[0].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "A":
                if (banderaColor == false)
                    arBtnTeclado4[1].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado4[1].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "S":
                if (banderaColor == false)
                    arBtnTeclado4[2].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado4[2].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "D":
                if (banderaColor == false)
                    arBtnTeclado4[3].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado4[3].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "F":
                if (banderaColor == false)
                    arBtnTeclado4[4].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado4[4].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "G":
                if ( banderaColor == false)
                    arBtnTeclado4[5].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado4[5].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "H":
                if (banderaColor == false)
                    arBtnTeclado4[6].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado4[6].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "J":
                if (banderaColor == false)
                    arBtnTeclado4[7].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado4[7].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "K":
                if (banderaColor == false)
                    arBtnTeclado4[8].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado4[8].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "L":
                if (banderaColor == false)
                    arBtnTeclado4[9].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado4[9].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "BRACELEFT":
                if (banderaColor == false)
                    arBtnTeclado4[11].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado4[11].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "ENTER":
                if (banderaColor == false)
                    arBtnTeclado4[12].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado4[12].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "SHIFT":
                if (banderaColor == false)
                    arBtnTeclado5[0].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado5[0].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "Z":
                if (banderaColor == false)
                    arBtnTeclado5[1].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado5[1].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "X":
                if (banderaColor == false)
                    arBtnTeclado5[2].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado5[2].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "C":
                if (banderaColor == false)
                    arBtnTeclado5[3].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado5[3].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "V":
                if (banderaColor == false)
                    arBtnTeclado5[4].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado5[4].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "B":
                if (banderaColor == false)
                    arBtnTeclado5[5].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado5[5].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "N":
                if (banderaColor == false)
                    arBtnTeclado5[6].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado5[6].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "M":
                if (banderaColor == false)
                    arBtnTeclado5[7].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado5[7].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "COMMA":
                if (banderaColor == false)
                    arBtnTeclado5[8].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado5[8].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "PERIOD":
                if (banderaColor == false)
                    arBtnTeclado5[9].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado5[9].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "MINUS":
                if (banderaColor == false)
                    arBtnTeclado5[10].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado5[10].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "CONTROL":
                if (banderaColor == false)
                    arBtnTeclado6[0].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado6[0].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "WINDOWS":
                if (banderaColor == false)
                    arBtnTeclado6[2].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado6[2].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "ALT":
                if (banderaColor == false)
                    arBtnTeclado6[3].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado6[3].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "SPACE":
                if (banderaColor == false)
                    arBtnTeclado6[4].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado6[4].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "ALT_GRAPH":
                if (banderaColor == false)
                    arBtnTeclado6[5].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado6[5].setStyle("-fx-background-color: #8d8d8d");
                break;
            case "LESS":
                if (banderaColor == false)
                    arBtnTeclado6[6].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado6[6].setStyle("-fx-background-color: #8d8d8d");
                break;
        }
        banderaColor = !banderaColor;
    }
}
