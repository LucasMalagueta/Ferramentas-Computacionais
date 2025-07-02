package com.example.botekofx;

import com.example.botekofx.db.dal.CategoriaDAL;
import com.example.botekofx.db.entidades.Categoria;
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

public class TabelaCategoriaController implements Initializable {

    @FXML
    private TableColumn<Categoria, Integer> colId;
    @FXML
    private TableColumn<Categoria, String> colNome;
    @FXML
    private TableView<Categoria> tableView;
    @FXML
    private Button btAlterar;
    @FXML
    private Button btApagar;

    static public Categoria categoria = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        btAlterar.setDisable(true);
        btApagar.setDisable(true);

        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            btAlterar.setDisable(newSelection == null);
            btApagar.setDisable(newSelection == null);
        });

        carregarTabela();
    }

    private void carregarTabela() {
        CategoriaDAL dal = new CategoriaDAL();
        List<Categoria> categorias = dal.get("");
        tableView.setItems(FXCollections.observableArrayList(categorias));
    }

    private void abrirFormulario() {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("form-categoria-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Nova/Alterar Categoria");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            carregarTabela();
        } catch (IOException e) {
            mostrarAlerta("Erro", "Falha ao carregar o formulário: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    void onNovo(ActionEvent event) {
        categoria = null;
        abrirFormulario();
    }

    @FXML
    void onAlterar(ActionEvent event) {
        categoria = tableView.getSelectionModel().getSelectedItem();
        abrirFormulario();
    }

    @FXML
    void onApagar(ActionEvent event) {
        Categoria selecionada = tableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Exclusão");
        alert.setHeaderText("Excluir: " + selecionada.getNome());
        alert.setContentText("Tem certeza que deseja apagar esta categoria?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            CategoriaDAL dal = new CategoriaDAL();
            if (dal.apagar(selecionada)) {
                carregarTabela();
            } else {
                mostrarAlerta("Erro", "Erro ao apagar a categoria. Verifique se ela não está em uso por algum produto.", Alert.AlertType.ERROR);
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