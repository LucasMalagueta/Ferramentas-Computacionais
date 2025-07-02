package com.example.botekofx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowManager {

    /**
     * Abre uma nova janela modal com o ícone padrão da aplicação.
     * @param fxmlFile O nome do arquivo FXML a ser carregado (ex: "tabela-produto-view.fxml").
     * @param title O título da nova janela.
     */
    public static void openModal(String fxmlFile, String title) {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxmlFile));
            Scene scene = new Scene(fxmlLoader.load());

            stage.setTitle(title);
            stage.setScene(scene);

            // Define o ícone da aplicação para esta janela
            try {
                Image icon = new Image(Main.class.getResourceAsStream("icons/app-icon.png"));
                stage.getIcons().add(icon);
            } catch (Exception e) {
                System.err.println("Alerta: Não foi possível carregar o ícone da janela: " + e.getMessage());
            }

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

    private static void mostrarAlertaErro(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(titulo);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}