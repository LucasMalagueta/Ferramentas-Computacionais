package com.example.botekofx;

import com.example.botekofx.db.dal.TipoPagamentoDAL;
import com.example.botekofx.db.entidades.TipoPagamento;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class TabelaTipoPagamentoController implements Initializable {

    @FXML
    private TableColumn<TipoPagamento, Integer> colId;
    @FXML
    private TableColumn<TipoPagamento, String> colDescricao;
    @FXML
    private TableView<TipoPagamento> tableView;
    @FXML
    private Button btAlterar; // Adicionado
    @FXML
    private Button btApagar;  // Adicionado

    static public TipoPagamento tipoPagamento = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Mapeamento das colunas
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        // Desabilitar botões no início
        btAlterar.setDisable(true);
        btApagar.setDisable(true);

        // Adicionar listener para a seleção na tabela
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Se um item for selecionado, habilita os botões
                btAlterar.setDisable(false);
                btApagar.setDisable(false);
            } else {
                // Se nenhum item for selecionado, desabilita os botões
                btAlterar.setDisable(true);
                btApagar.setDisable(true);
            }
        });

        carregarTabela();
    }

    private void carregarTabela() {
        TipoPagamentoDAL dal = new TipoPagamentoDAL();
        List<TipoPagamento> tipos = dal.get("");
        tableView.setItems(FXCollections.observableArrayList(tipos));
    }

    private void abrirFormulario() {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("form-tipopagamento-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Novo/Alterar Tipo de Pagamento");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            carregarTabela(); // Recarrega a tabela após o formulário fechar
        } catch (IOException e) {
            mostrarAlerta("Erro", "Falha ao carregar o formulário: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    void onNovo(ActionEvent event) {
        tipoPagamento = null;
        abrirFormulario();
    }

    @FXML
    void onAlterar(ActionEvent event) {
        // A verificação de nulo não é mais necessária aqui, pois o botão estará desabilitado.
        tipoPagamento = tableView.getSelectionModel().getSelectedItem();
        abrirFormulario();
    }

    @FXML
    void onApagar(ActionEvent event) {
        // A verificação de nulo também foi simplificada.
        TipoPagamento selecionado = tableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Exclusão");
        alert.setHeaderText("Excluir: " + selecionado.getDescricao());
        alert.setContentText("Tem certeza que deseja apagar este tipo de pagamento?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            TipoPagamentoDAL dal = new TipoPagamentoDAL();
            if (dal.apagar(selecionado)) {
                carregarTabela();
            } else {
                mostrarAlerta("Erro", "Erro ao apagar o tipo de pagamento. Verifique se ele não está em uso.", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    void onFechar(ActionEvent event) {
        Stage stage = (Stage) tableView.getScene().getWindow();
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