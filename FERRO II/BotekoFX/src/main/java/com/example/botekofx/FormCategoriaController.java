package com.example.botekofx;

import com.example.botekofx.db.dal.CategoriaDAL;
import com.example.botekofx.db.entidades.Categoria;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class FormCategoriaController implements Initializable {

    @FXML
    private TextField tfId;
    @FXML
    private TextField tfNome;

    private Categoria original = TabelaCategoriaController.categoria;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (original != null) {
            tfId.setText(String.valueOf(original.getId()));
            tfNome.setText(original.getNome());
        }
    }

    @FXML
    void onConfirmar(ActionEvent event) {
        if (tfNome.getText().isEmpty()) {
            mostrarAlerta("Erro de Validação", "O campo de nome não pode estar vazio.", Alert.AlertType.ERROR);
            return;
        }

        Categoria categoria = new Categoria(Integer.parseInt(tfId.getText()), tfNome.getText());
        CategoriaDAL dal = new CategoriaDAL();
        boolean resultado;

        if (categoria.getId() == 0) { // Novo
            resultado = dal.gravar(categoria);
        } else { // Alteração
            resultado = dal.alterar(categoria);
        }

        if (resultado) {
            fecharJanela();
        } else {
            mostrarAlerta("Erro de Banco de Dados", "Falha ao salvar o registro.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onCancelar(ActionEvent event) {
        fecharJanela();
    }

    private void fecharJanela() {
        Stage stage = (Stage) tfId.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}