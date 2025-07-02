package com.example.botekofx;

import com.example.botekofx.db.dal.ProdutoDAL;
import com.example.botekofx.db.entidades.Categoria;
import com.example.botekofx.db.entidades.Produto;
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

public class TabelaProdutoController implements Initializable {

    @FXML
    private TableColumn<Produto, Categoria> colCategoria;
    @FXML
    private TableColumn<Produto, Integer> colId;
    @FXML
    private TableColumn<Produto, String> colNome;
    @FXML
    private TableColumn<Produto, Double> colPreco;
    @FXML
    private TableView<Produto> tableView;
    @FXML
    private Button btAlterar; // Adicionado
    @FXML
    private Button btApagar;  // Adicionado

    static public Produto produto = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Mapeamento das colunas da tabela
        colId.setCellValueFactory(new PropertyValueFactory<>("id")); //
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria")); //
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome")); //
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco")); //

        // Desabilitar botões no início
        btAlterar.setDisable(true);
        btApagar.setDisable(true);

        // Adicionar listener para a seleção na tabela
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                btAlterar.setDisable(false);
                btApagar.setDisable(false);
            } else {
                btAlterar.setDisable(true);
                btApagar.setDisable(true);
            }
        });

        carregarTabela(); //
    }

    private void carregarTabela() {
        ProdutoDAL produtoDAL = new ProdutoDAL(); //
        List<Produto> produtoList = produtoDAL.get(""); // Busca todos os produtos
        if (produtoList != null) {
            tableView.setItems(FXCollections.observableArrayList(produtoList)); //
        } else {
            tableView.setItems(FXCollections.observableArrayList()); // Lista vazia
            mostrarAlertaErro("Não foi possível carregar os produtos do banco de dados."); //
        }
        tableView.refresh(); // Garante que a tabela seja atualizada visualmente
    }

    @FXML
    void onNovoProduto(ActionEvent event) {
        TabelaProdutoController.produto = null; // Garante que o formulário abra em modo "novo"
        abrirFormularioProduto("Novo Produto"); //
    }

    @FXML
    void onAlterar(ActionEvent actionEvent) {
        // A verificação de nulo foi removida, pois o botão só estará ativo com um item selecionado.
        produto = tableView.getSelectionModel().getSelectedItem();
        abrirFormularioProduto("Alterar Produto"); //
    }

    @FXML
    void onApagar(ActionEvent actionEvent) {
        // A verificação de nulo e o alerta de "nenhum item" foram removidos.
        Produto produtoSelecionado = tableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); //
        alert.setTitle("Confirmar Exclusão"); //
        alert.setHeaderText("Excluir Produto: " + produtoSelecionado.getNome()); //
        alert.setContentText("Tem certeza que deseja deletar este produto?\nEsta ação não pode ser desfeita."); //

        Optional<ButtonType> result = alert.showAndWait(); //
        if (result.isPresent() && result.get() == ButtonType.OK) { //
            ProdutoDAL produtoDAL = new ProdutoDAL(); //
            if (produtoDAL.apagar(produtoSelecionado)) { //
                Alert infoAlert = new Alert(Alert.AlertType.INFORMATION); //
                infoAlert.setTitle("Sucesso"); //
                infoAlert.setHeaderText(null); //
                infoAlert.setContentText("Produto deletado com sucesso!"); //
                infoAlert.showAndWait(); //
                carregarTabela(); // Recarrega a tabela
            } else {
                mostrarAlertaErro("Erro ao deletar o produto. Verifique se ele está sendo usado em alguma comanda."); //
            }
        }
    }

    @FXML
    void onFechar(ActionEvent event) {
        TabelaProdutoController.produto = tableView.getSelectionModel().getSelectedItem(); //
        Stage stage = (Stage) tableView.getScene().getWindow(); //
        stage.close(); //
    }

    private void abrirFormularioProduto(String titulo) {
        try {
            Stage stage = new Stage(); //
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("form-produto-view.fxml")); //
            Scene scene = new Scene(fxmlLoader.load()); //
            stage.setTitle(titulo); //
            stage.setScene(scene); //
            stage.initModality(Modality.APPLICATION_MODAL); //
            stage.showAndWait(); // Espera o formulário fechar
            carregarTabela();    // Recarrega a tabela após fechar o formulário
        } catch (IOException e) {
            e.printStackTrace(); //
            mostrarAlertaErro("Falha ao carregar o formulário do produto: " + e.getMessage()); //
        } catch (Exception e) {
            e.printStackTrace(); //
            mostrarAlertaErro("Ocorreu um erro inesperado: " + e.getMessage()); //
        }
    }

    private void mostrarAlertaErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR); //
        alert.setTitle("Erro"); //
        alert.setHeaderText(null); //
        alert.setContentText(mensagem); //
        alert.showAndWait(); //
    }
}