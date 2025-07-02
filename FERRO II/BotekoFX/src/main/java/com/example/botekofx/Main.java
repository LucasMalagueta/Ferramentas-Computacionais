package com.example.botekofx;

import com.example.botekofx.db.util.SingletonDB;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert; // Importado
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("menu-principal-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("BotekoFX Light!");
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Log do erro no console
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro Crítico");
            alert.setHeaderText("Falha ao carregar a interface principal");
            alert.setContentText("Não foi possível carregar o arquivo 'menu-principal-view.fxml'.\n" +
                    "A aplicação será encerrada.\nDetalhes: " + e.getMessage());
            alert.showAndWait();
            Platform.exit();
        }
    }

    public static void main(String[] args) {
        if(SingletonDB.conectarDB()) { //
            launch(args); // Passar args para launch
        }
        else
        {
            // Mantém JOptionPane para erros de DB antes do JavaFX iniciar completamente
            JOptionPane.showMessageDialog(null,"Problemas ao conectar o banco: \n"+
                    SingletonDB.getConexao().getMensagemErro(), "Erro de Conexão com Banco", JOptionPane.ERROR_MESSAGE); //
            // Platform.exit() não é necessário aqui, pois o launch() não será chamado.
            // A aplicação simplesmente não iniciará a UI JavaFX.
        }
    }
}