package sample;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.ui.Memorama;
import sample.ui.Taquimecanografo;

public class Main extends Application implements EventHandler  {

    private VBox vPrincipal;

    private MenuBar mbPrincipal;
    private Menu menCompetencia1, menCompetencia2, menSalir;
    private MenuItem itmMemorama,itmPractica2,itmTerminar;
    private ToolBar tlbMenu;
    private Button btnToolBar1;
    private Scene escena;

    @Override
    public void start(Stage primaryStage) throws Exception{

        CrearUI();
        primaryStage.setTitle("Prácticas de Tópicos 2020");
        primaryStage.setMaximized(true);
        primaryStage.setScene(escena);
        primaryStage.addEventHandler(WindowEvent.WINDOW_HIDDEN ,this);

        primaryStage.show();


    }

    private void CrearUI() {

        mbPrincipal = new MenuBar();

        //Creacion de los menus
        menCompetencia1 =  new Menu("Competencia 1");
        menCompetencia2 = new Menu("competencia 2");
        menSalir        = new Menu("Salir");

        //Cargar menus a ala barra de menus
        mbPrincipal.getMenus().addAll(menCompetencia1,menCompetencia2,menSalir);

        //Crear menu item para el memorama
        itmMemorama = new MenuItem("Menorama");
        itmMemorama.setOnAction(event -> opcionMenu(1));

        itmPractica2 = new MenuItem("Taquimecanografo");
        itmPractica2.setOnAction(event -> opcionMenu(2));

        itmTerminar = new MenuItem("Hasta pronto");
        itmTerminar.setOnAction(event -> System.exit(0));

        //Cargar el item memorama al menu competencia1
        menCompetencia1.getItems().addAll(itmMemorama);
        menCompetencia1.getItems().addAll(itmPractica2);

        menSalir.getItems().add(itmTerminar);

        //crear una barra de herramientas
        tlbMenu = new ToolBar();
        btnToolBar1 = new Button();
        btnToolBar1.setOnAction(event -> opcionMenu(1));
        btnToolBar1.setPrefSize(64,64);

        //asignamos imagen al boton detro del toolbar
        Image img = new Image("sample/assets/game.png");
        ImageView imv = new ImageView(img);
        imv.setFitHeight(64);
        imv.setPreserveRatio(true);
        btnToolBar1.setGraphic(imv);

        tlbMenu.getItems().add(btnToolBar1);

        vPrincipal = new VBox();
        vPrincipal.getChildren().addAll(mbPrincipal,tlbMenu);

        escena = new Scene(vPrincipal);
        escena.getStylesheets().add("sample/assets/CSS/main_styles.css");
    }

    private void opcionMenu(int opc) {
        switch (opc){
            case 1 : new Memorama(); break;
            case 2 : new Taquimecanografo(); break;
            case 3 : break;
        }
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void handle(Event event) {
        System.out.println("Se esta mostrando la pantalla");
    }
}
