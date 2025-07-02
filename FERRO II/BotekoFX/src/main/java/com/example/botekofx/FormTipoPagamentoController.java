package com.example.botekofx;

import com.example.botekofx.db.dal.TipoPagamentoDAL;
import com.example.botekofx.db.entidades.TipoPagamento;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class FormTipoPagamentoController implements Initializable {

    @FXML
    private TextField tfId;
    @FXML
    private TextField tfDescricao;

    private TipoPagamento original = TabelaTipoPagamentoController.tipoPagamento;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (original != null) {
            tfId.setText(String.valueOf(original.getId()));
            tfDescricao.setText(original.getDescricao());
        }
    }

    @FXML
    void onConfirmar(ActionEvent event) {
        if (tfDescricao.getText().isEmpty()) {
            mostrarAlerta("Erro de Validação", "O campo de descrição não pode estar vazio.", Alert.AlertType.ERROR);
            return;
        }

        TipoPagamento tipo = new TipoPagamento(Integer.parseInt(tfId.getText()), tfDescricao.getText());
        TipoPagamentoDAL dal = new TipoPagamentoDAL();
        boolean resultado;

        if (tipo.getId() == 0) { // Novo
            resultado = dal.gravar(tipo);
        } else { // Alteração
            resultado = dal.alterar(tipo);
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