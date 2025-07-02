package com.example.botekofx;

import com.example.botekofx.db.dal.CategoriaDAL;
import com.example.botekofx.db.dal.ProdutoDAL;
import com.example.botekofx.db.entidades.Categoria;
import com.example.botekofx.db.entidades.Produto;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FormProdutoController implements Initializable {

    @FXML
    private ComboBox<Categoria> cbCategoria;

    @FXML
    private TextArea taDescr;

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfPreco;

    @FXML
    void onCancelar(ActionEvent event) {
        tfId.getScene().getWindow().hide();
    }

    @FXML
    void onConfirmar(ActionEvent event) {
        //criar objeto do tipo Produto e carregar os dados
        Produto produto=new Produto();
        produto.setId(Integer.parseInt(tfId.getText()));
        produto.setNome(tfNome.getText());
        produto.setCategoria(cbCategoria.getSelectionModel().getSelectedItem());
        produto.setPreco(Double.parseDouble(tfPreco.getText()));
        produto.setDescricao(taDescr.getText());
        // instanciar a DAL de produto e inserir o objeto
        ProdutoDAL produtoDAL=new ProdutoDAL();
        if(TabelaProdutoController.produto==null) {
            if (produtoDAL.gravar(produto) == false) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro ao gravar o produto");
                alert.showAndWait();
            }
        }
        else{
            if (produtoDAL.alterar(produto) == false) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro ao alterar o produto");
                alert.showAndWait();
            }
        }
        tfId.getScene().getWindow().hide();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        carregarCategorias();
        cbCategoria.getSelectionModel().selectFirst();
        Produto produto=TabelaProdutoController.produto;
        if(produto!=null)
        {
            tfId.setText(""+produto.getId());
            tfNome.setText(""+produto.getNome());
            tfPreco.setText(""+produto.getPreco());
            taDescr.setText(""+produto.getDescricao());
            cbCategoria.getSelectionModel().select(produto.getCategoria());
        }
    }

    private void carregarCategorias() {
        CategoriaDAL categoriaDAL=new CategoriaDAL();
        List<Categoria> categoriaList=categoriaDAL.get("");
        cbCategoria.setItems(FXCollections.observableArrayList(categoriaList));
    }
}
