
package sample.ui;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.events.EventosMemorama;

public class Memorama extends Stage implements EventHandler {

    private String[] arImagenes = {"Avestruz.jpg","Buho.jpg","Cebra.jpg","Gorila.jpg","Leon.jpg","loro.jpg","Mariposa.jpg","Serpiente.jpg","Tigre.jpg","Tucan.jpg"};

    private Label lblTarjetas;
    private TextField txtNoTarjetas;
    private Button btnAceptar, btnAceptar2;
    private HBox hBox, hbCartas;
    private VBox vBox;
    private GridPane gdpMesa;
    private Button[][] arTarjetas;
    private String[][] arAsignacion;

    private int noPares, carta1x, carta1y, carta2x, carta2y, intentos, correctas, contador =0, vueltas = 0;
    private boolean carArriba;
    private Scene escena;
    private Image image = new Image("sample/assets/carta_base.jpg");
    private ImageView imagen = new ImageView(image);
    Thread hilo = new Thread();

    public Memorama(){

        CrearUI();
        this.setTitle("Memorama :)");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        System.out.println("no son iguales");
        imagen.setFitHeight(120);
        imagen.setPreserveRatio(true);
        carArriba = false;
        intentos = 0;
        correctas = 0;
        lblTarjetas = new Label("Número de Pares:");
        txtNoTarjetas = new TextField();
        btnAceptar = new Button("Voltear y revolver");
        btnAceptar2 = new Button("Nuevo mensaje");
        btnAceptar.addEventHandler(MouseEvent.MOUSE_CLICKED,this);
        //btnAceptar2.addEventHandler(MouseEvent.MOUSE_CLICKED,new EventosMemorama(2));
        /*btnAceptar.addEventHandler(MouseEvent.MOUSE_CLICKED,new EventosMemorama());
        btnAceptar.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Aqui va el mensaje");
            }
        });*/

        hBox = new HBox();
        hBox.getChildren().addAll(lblTarjetas,txtNoTarjetas,btnAceptar);
        hBox.setSpacing(10);

        gdpMesa = new GridPane();
        vBox = new VBox();
        hbCartas = new HBox();
        vBox.getChildren().addAll(hBox);

        escena = new Scene (vBox,500,500);
    }

    EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {

        }
    };



    @Override
    public void handle(Event event) {

        noPares = Integer.parseInt(txtNoTarjetas.getText());

        vBox.getChildren().remove(gdpMesa);
        hbCartas = new HBox();
        gdpMesa = new GridPane();
        arAsignacion = new String[2][noPares];
        revolver();

        arTarjetas = new Button[2][noPares];
        for (int i=0; i<2; i++){
            for( int j=0; j<noPares; j++){
                vueltas++;
                Image img = new Image("sample/assets/carta_base.jpg");
                ImageView imv = new ImageView(img);
                imv.setFitHeight(120);
                imv.setPreserveRatio(true);

                arTarjetas[i][j] = new Button();
                int finalI = i;
                int finalJ = j;
                arTarjetas[i][j].setOnAction(event1->verTarjeta(finalI,finalJ));
                arTarjetas[i][j].setGraphic(imv);
                arTarjetas[i][j].setPrefSize(80,120);

                if(contador < 4){
                    hbCartas.getChildren().add(arTarjetas[i][j]);
                    contador++;
                }
                else {
                    vBox.getChildren().add(hbCartas);

                    hbCartas = new HBox();
                    hbCartas.getChildren().add(arTarjetas[i][j]);
                    contador= 1;

                }
                if(noPares%4 != 0 && vueltas==(noPares*2)){
                    vBox.getChildren().add(hbCartas);
                }
                //gdpMesa.add(arTarjetas[i][j],j,i);
            }
        }

        //vBox.getChildren().add(gdpMesa);
    }

    private void verTarjeta(int finalI, int finalJ){

        Image img = new Image("sample/assets/"+arAsignacion[finalI][finalJ]);
        ImageView imv = new ImageView(img);
        imv.setFitHeight(120);
        imv.setPreserveRatio(true);
        arTarjetas[finalI][finalJ].setGraphic(imv);



        System.out.println("en linea " + carArriba);

        try{
            hilo.stop();
            hilo.start();
        }
        catch (Exception e){

        }
        revCarta(finalI,finalJ);




    }

    void revCarta(int finalI, int finalJ){
        if (!carArriba){
            carta1x = finalI;
            carta1y = finalJ;
            carArriba = true;
            System.out.println("una carta se volteo");
            arTarjetas[finalI][finalJ].setDisable(true);
        }
        else if(carArriba == true){

            carta2x = finalI;
            carta2y = finalJ;
            System.out.println("par" + arAsignacion[carta1x][carta1y]);
            arTarjetas[finalI][finalJ].setDisable(true);
            comCartas(carta1x,carta1y,carta2x,carta2y);

        }
    }

     void volCartas(int x1, int x2, int y1, int y2){
         Image img = new Image("sample/assets/carta_base.jpg");
         ImageView imv = new ImageView(img);
         imv.setFitHeight(120);
         imv.setPreserveRatio(true);

         arTarjetas[x1][x2].setPrefSize(80,120);
         arTarjetas[x1][x2].setGraphic(imv);


         Image img2 = new Image("sample/assets/carta_base.jpg");
         ImageView imv2 = new ImageView(img2);
         imv2.setFitHeight(120);
         imv2.setPreserveRatio(true);

         arTarjetas[y1][y2].setPrefSize(80,120);
         arTarjetas[y1][y2].setGraphic(imv2);
     }

    void comCartas(int x1, int x2, int y1, int y2){
        if (arAsignacion[x1][x2] != arAsignacion[y1][y2]){
            intentos++;
            carArriba = false;
            arTarjetas[x1][x2].setDisable(false);
            arTarjetas[y1][y2].setDisable(false);
            volCartas(x1,x2,y1,y2);

        }
        else {
            System.out.println("son iguales");
            carArriba = false;
            arTarjetas[x1][x2].setDisable(true);
            arTarjetas[y1][y2].setDisable(true);
            intentos++;
            correctas++;
            carArriba = false;
            System.out.println("Correctas "+correctas);
        }
        if (correctas == noPares){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Felicidades");
            alert.setHeaderText("Has completado el juego en "+ intentos + " intentos");
            alert.showAndWait();
            this.close();
        }
        System.out.println("intentos " + intentos);
    }


    private void revolver() {

        for(int i=0; i<2; i++)
            for(int j=0; j<noPares; j++){
                arAsignacion[i][j] = new String();
            }

        int posx, posy, cont = 0;
        for(int i=0; i<noPares;){
            posx = (int)(Math.random() * 2);
            posy = (int)(Math.random() * noPares);
            if( arAsignacion[posx][posy].equals("") ){
                arAsignacion[posx][posy] = arImagenes[i];
                cont++;
            }

            if(cont == 2){ // Sirve para comprobar que la imagen se asignó 2 veces
                i++;
                cont = 0;
            }
        }
    }


}/*
package sample.ui;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.events.EventosMemorama;

public class Memorama extends Stage implements EventHandler {

    private String[] arImagenes = {"Avestruz.jpg","Buho.jpg","Cebra.jpg","Gorila.jpg","Leon.jpg","loro.jpg","Mariposa.jpg","Serpiente.jpg","Tigre.jpg","Tucan.jpg"};

    private Label lblTarjetas;
    private TextField txtNoTarjetas;
    private Button btnAceptar, btnAceptar2;
    private HBox hBox;
    private VBox vBox;
    private HBox hbMesa;
    private Button[][] arTarjetas;
    private String[][] arAsignacion;

    private int noPares, noTarjeta, total;
    private Scene escena;

    public Memorama(){

        CrearUI();
        this.setTitle("Memorama :)");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        lblTarjetas = new Label("Número de Pares:");
        txtNoTarjetas = new TextField();
        btnAceptar = new Button("Voltear y revolver");
        btnAceptar2 = new Button("Nuevo mensaje");
        btnAceptar.addEventHandler(MouseEvent.MOUSE_CLICKED,this);
        //btnAceptar2.addEventHandler(MouseEvent.MOUSE_CLICKED,new EventosMemorama(2));
        /*btnAceptar.addEventHandler(MouseEvent.MOUSE_CLICKED,new EventosMemorama());
        btnAceptar.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Aqui va el mensaje");
            }
        });

        hBox = new HBox();
        hBox.getChildren().addAll(lblTarjetas,txtNoTarjetas,btnAceptar);
        hBox.setSpacing(10);

        hbMesa = new HBox();
        vBox = new VBox();
        vBox.getChildren().addAll(hBox,hbMesa);

        escena = new Scene (vBox,500,500);
    }

    @Override
    public void handle(Event event) {

        noPares = Integer.parseInt(txtNoTarjetas.getText());

        vBox.getChildren().remove(hbMesa);

        hbMesa = new HBox();
        arAsignacion = new String[2][noPares];
        revolver();

        if(noPares >  4 ) {
            noTarjeta = 4;
        }
        else {
            noTarjeta = noPares;
        }

        total = 0;

        arTarjetas = new Button[2][noPares];
        for (int i=0; i< 1+noPares/noTarjeta; i++){
            for( int j=0; j< noTarjeta ; j++){
                if(total<noPares){
                    Image img = new Image("sample/assets/carta_base.jpg");
                    ImageView imv = new ImageView(img);
                    imv.setFitHeight(120);
                    imv.setPreserveRatio(true);

                    arTarjetas[i][j] = new Button();
                    int finalI = i;
                    int finalJ = j;
                    arTarjetas[i][j].setOnAction(event1->verTarjeta(finalI,finalJ));
                    arTarjetas[i][j].setGraphic(imv);
                    arTarjetas[i][j].setPrefSize(80,120);

                    hbMesa.getChildren().add(arTarjetas[i][j]);

                }
                else {
                    break;
                }
                total++;

            }
            if(total< noPares+1)
            vBox.getChildren().add(hbMesa);
            hbMesa = new HBox();
        }

    }

    private void verTarjeta(int finalI, int finalJ) {

        Image img = new Image("sample/assets/"+arAsignacion[finalI][finalJ]);
        ImageView imv = new ImageView(img);
        imv.setFitHeight(120);
        imv.setPreserveRatio(true);

        arTarjetas[finalI][finalJ].setGraphic(imv);
    }


    private void revolver() {

        for(int i=0; i<2; i++)
            for(int j=0; j<noPares; j++){
                arAsignacion[i][j] = new String();
            }

        int posx, posy, cont = 0;
        for(int i=0; i<noPares;){
            posx = (int)(Math.random() * 2);
            posy = (int)(Math.random() * noPares);
            if( arAsignacion[posx][posy].equals("") ){
                arAsignacion[posx][posy] = arImagenes[i];
                cont++;
            }

            if(cont == 2){ // Sirve para comprobar que la imagen se asignó 2 veces
                i++;
                cont = 0;
            }
        }
    }
}
*/