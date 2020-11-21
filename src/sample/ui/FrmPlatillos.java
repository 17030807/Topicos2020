package sample.ui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.models.PlatillosDAO;
import sample.models.TipoPlatilloDAO;

public class FormPlatillos extends Stage {
    private TextField txtPlatillo, txtPrecio;
    private ComboBox<TipoPlatilloDAO> cbTipoPlatillo;
    private Button btnGuardar;
    private VBox vBox;
    private Scene scene;
    private PlatillosDAO objPlatillo;
    private TableView<PlatillosDAO> tbvPlatillos;

    public FormPlatillos(TableView<PlatillosDAO> tbvPlatillos, PlatillosDAO objPlatillo){
        if(objPlatillo == null)
            this.objPlatillo = objPlatillo;
        else
            objPlatillo = new PlatillosDAO();

        CreaUI();
        this.setTitle("Geston de Platillos");
        this.setScene(scene);
        this.show();

        this.tbvPlatillos = tbvPlatillos;

    }

    private void CreaUI() {
        txtPlatillo = new TextField();
        txtPlatillo.setText(objPlatillo.getNombre_platillo());
        txtPrecio = new TextField();
        txtPrecio.setText(objPlatillo.getPrecio()+"");
        cbTipoPlatillo = new ComboBox<>();
        cbTipoPlatillo.setItems(new TipoPlatilloDAO().getAllTipo());
        btnGuardar = new Button("Guardar Platillo");
        btnGuardar.setOnAction(event -> Guardar());
        vBox = new VBox();
        vBox.getChildren().addAll(txtPlatillo,txtPrecio,cbTipoPlatillo,btnGuardar);
        scene = new Scene(vBox,250,250);
    }

    private void Guardar() {
        objPlatillo.setNombre_platillo(txtPlatillo.getText());
        objPlatillo.setPrecio(Float.parseFloat(txtPrecio.getText()));
        objPlatillo.setId_tipo(Integer.parseInt("1"));

        if(objPlatillo == null) {
            objPlatillo.insPlatillo();
        }
        else{
            objPlatillo.updPlatillo();
        }

        tbvPlatillos.setItems(objPlatillo.getAllPlatillo());
        tbvPlatillos.refresh();

        this.close();
    }
}
