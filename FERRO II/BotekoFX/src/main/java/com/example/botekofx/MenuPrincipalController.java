package com.example.botekofx;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class MenuPrincipalController {

    @FXML
    private Button btnCadastro;

    private void abrirJanelaModal(String fxmlFile, String titulo) {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxmlFile));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle(titulo);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlertaErro("Falha ao carregar a interface", "Não foi possível carregar o arquivo '" + fxmlFile + "'.\nDetalhes: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlertaErro("Erro Inesperado", "Ocorreu um erro inesperado ao tentar abrir a janela.\nDetalhes: " + e.getMessage());
        }
    }

    @FXML
    void onMenuCadastro(ActionEvent event) {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem produtoItem = new MenuItem("PRODUTO");
        produtoItem.setOnAction(e -> onCadProduto());

        MenuItem categoriaItem = new MenuItem("CATEGORIA");
        categoriaItem.setOnAction(e -> onCadCategoria());

        MenuItem tipoPagamentoItem = new MenuItem("TIPO DE PAGAMENTO");
        tipoPagamentoItem.setOnAction(e -> onCadTipoPagamento());

        contextMenu.getItems().addAll(produtoItem, categoriaItem, tipoPagamentoItem);
        contextMenu.show(btnCadastro, javafx.geometry.Side.BOTTOM, 0, 0);
    }

    private void onCadProduto() {
        abrirJanelaModal("tabela-produto-view.fxml", "Gerenciamento de Produtos");
    }

    private void onCadCategoria() {
        // Agora abre a nova tela de gerenciamento de categorias
        abrirJanelaModal("tabela-categoria-view.fxml", "Gerenciamento de Categorias");
    }

    private void onCadTipoPagamento() {
        abrirJanelaModal("tabela-tipopagamento-view.fxml", "Gerenciamento de Tipos de Pagamento");
    }

    @FXML
    void onComandas(ActionEvent actionEvent) {
        abrirJanelaModal("comandas-view.fxml", "Gerenciamento de Comandas");
    }

    @FXML
    void onSobre(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sobre BotekoFX Light");
        alert.setHeaderText("BotekoFX Light - Sistema de Gerenciamento Simplificado");
        alert.setContentText("Versão: 1.0 (SNAPSHOT)\n" +
                "Desenvolvido como exemplo de aplicação JavaFX com banco de dados.\n\n" +
                "Desenvolvido por Lucas Malagueta e Mateus Silva Maciel");
        alert.getDialogPane().setMinHeight(javafx.scene.layout.Region.USE_PREF_SIZE);
        alert.showAndWait();
    }

    @FXML
    void onSair(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Sair da Aplicação");
        alert.setHeaderText("Confirmação de Saída");
        alert.setContentText("Deseja realmente sair do BotekoFX Light?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Platform.exit();
            System.exit(0);
        }
    }

    private void mostrarAlertaErro(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(titulo);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}