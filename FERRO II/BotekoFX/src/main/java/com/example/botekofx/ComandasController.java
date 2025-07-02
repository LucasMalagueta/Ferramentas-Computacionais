package com.example.botekofx;

import com.example.botekofx.db.dal.ComandaDAL;
import com.example.botekofx.db.dal.TipoPagamentoDAL;
import com.example.botekofx.db.entidades.Comanda;
import com.example.botekofx.db.entidades.Produto;
import com.example.botekofx.db.entidades.TipoPagamento;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ComandasController implements Initializable {

    public VBox painel;
    public TextField tfTotal;
    @FXML
    private Button btAdicionar;
    @FXML
    private Button btCancelar;
    @FXML
    private Button btConfirmar;
    @FXML
    private Button btFecharComanda;
    @FXML
    private Button btSelecionarProduto;
    @FXML
    private DatePicker dtData;
    @FXML
    private ListView<Comanda> lvComandas;
    @FXML
    private Spinner<Integer> spQuant;
    @FXML
    private TableView<Comanda.Item> tableView;
    @FXML
    private TextField tfDescr;
    @FXML
    private TextField tfNumero;
    private Comanda comanda=null;
    @FXML
    private TableColumn<Comanda.Item, Produto> colProduto;
    @FXML
    private TableColumn<Comanda.Item, Integer> colQuant;
    @FXML
    private TableColumn<Comanda.Item, Double> colValor;
    Produto produto;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        spQuant.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100,1));
        carregarComandas();
        dtData.setValue(LocalDate.now());
        painel.setDisable(true);
        colProduto.setCellValueFactory(new PropertyValueFactory<>("produto"));
        colQuant.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        btAdicionar.setDisable(true);

        tableView.getItems().addListener((ListChangeListener.Change<? extends Comanda.Item> c) -> {
            atualizarTotalComanda();
        });
    }

    /**
     * Método privado para limpar e resetar o formulário.
     */
    private void limparFormulario() {
        tfDescr.setText("");
        tfNumero.setText("");
        tfNumero.setDisable(true);
        dtData.setValue(LocalDate.now());
        tableView.getItems().clear();
        tfTotal.setText("0.00");
        btSelecionarProduto.setText("Selecione o Produto");
        btAdicionar.setDisable(true);
        comanda = null;
        painel.setDisable(true);
    }

    /**
     * Ação do botão Cancelar.
     * Se uma comanda estiver selecionada, pede confirmação para apagá-la.
     * Se não, apenas limpa o formulário.
     */
    @FXML
    void onCancelar(ActionEvent event) {
        if (comanda == null || comanda.getId() == 0) {
            limparFormulario();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Cancelamento");
        alert.setHeaderText("Cancelar Comanda Nº " + comanda.getNumero());
        alert.setContentText("Esta ação irá APAGAR a comanda permanentemente.\nTem certeza que deseja continuar?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ComandaDAL dal = new ComandaDAL();
            if (dal.apagar(comanda)) {
                carregarComandas();
                limparFormulario();
            } else {
                new Alert(Alert.AlertType.ERROR, "Erro ao cancelar a comanda.").showAndWait();
            }
        }
    }

    @FXML
    void onConfirmar(ActionEvent event) {
        ComandaDAL comandaDAL = new ComandaDAL();
        boolean novaComanda = (comanda == null || comanda.getId() == 0);
        if (novaComanda) {
            comanda = new Comanda();
        }

        comanda.setNumero(Integer.parseInt(tfNumero.getText()));
        comanda.setData(dtData.getValue());
        comanda.setDescricao(tfDescr.getText());
        if (novaComanda) {
            comanda.setStatus('A');
        }

        List<Comanda.Item> itens = new ArrayList<>(tableView.getItems());
        comanda.getItemList().clear();
        for(Comanda.Item item : itens) {
            comanda.addItem(item.getProduto(), item.getQuantidade());
        }

        if(novaComanda) {
            if (!comandaDAL.gravar(comanda)) {
                new Alert(Alert.AlertType.ERROR, "Erro ao gravar nova comanda.").showAndWait();
            }
        } else {
            if (!comandaDAL.alterar(comanda)) {
                new Alert(Alert.AlertType.ERROR, "Erro ao alterar comanda.").showAndWait();
            }
        }
        carregarComandas();
        limparFormulario(); // Usa o método de limpeza
    }

    @FXML
    void onFechar(ActionEvent event) {
        // Ação do botão "Fechar Janela" na parte inferior
        btFecharComanda.getScene().getWindow().hide();
    }

    @FXML
    void onFecharComanda(ActionEvent event) {
        if (this.comanda == null || this.comanda.getId() == 0) {
            new Alert(Alert.AlertType.WARNING, "Selecione uma comanda para fechar.").showAndWait();
            return;
        }
        if (this.comanda.getStatus() == 'F') {
            new Alert(Alert.AlertType.INFORMATION, "Esta comanda já está fechada.").showAndWait();
            return;
        }

        TipoPagamentoDAL tpDal = new TipoPagamentoDAL();
        List<TipoPagamento> tipos = tpDal.get("");

        if (tipos == null || tipos.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Nenhum tipo de pagamento cadastrado. Não é possível fechar a comanda.").showAndWait();
            return;
        }

        ChoiceDialog<TipoPagamento> dialog = new ChoiceDialog<>(tipos.get(0), tipos);
        dialog.setTitle("Seleção de Pagamento");
        dialog.setHeaderText("A comanda Nº " + this.comanda.getNumero() + " será fechada.");
        dialog.setContentText("Por favor, escolha a forma de pagamento:");

        Optional<TipoPagamento> result = dialog.showAndWait();

        result.ifPresent(tipoPagamentoSelecionado -> {
            this.comanda.setTipoPagamento(tipoPagamentoSelecionado);

            double valorPago;
            try {
                String totalStr = tfTotal.getText().replace(",", ".");
                valorPago = Double.parseDouble(totalStr);
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR, "Valor total da comanda é inválido. Não foi possível fechar.").showAndWait();
                return;
            }

            this.comanda.setValor(valorPago);
            this.comanda.setStatus('F');

            ComandaDAL comandaDAL = new ComandaDAL();
            if (comandaDAL.alterar(this.comanda)) {
                carregarComandas();
                limparFormulario(); // Usa o método de limpeza
                new Alert(Alert.AlertType.INFORMATION, "Comanda fechada com sucesso!").showAndWait();
            } else {
                this.comanda.setStatus('A');
                new Alert(Alert.AlertType.ERROR, "Erro ao fechar a comanda no banco de dados.\nA comanda permanecerá aberta.").showAndWait();
            }
        });
    }

    @FXML
    void onNovaComanda(ActionEvent event) {
        limparFormulario(); // Usa o método de limpeza
        comanda = new Comanda();
        comanda.setId(0);

        ComandaDAL dal = new ComandaDAL();
        int proximoNumero = dal.getProximoNumero();
        tfNumero.setText(String.valueOf(proximoNumero));
        tfNumero.setDisable(true);

        painel.setDisable(false);
        tfDescr.requestFocus();
    }

    // ... (outros métodos como onAdicionar, onApagarItem, etc., permanecem os mesmos) ...

    @FXML
    void onSelecionarProduto(ActionEvent event) {
        // Esta chamada foi atualizada em uma etapa anterior para usar WindowManager
        // Se você não implementou WindowManager, o código original de abrir stage estaria aqui.
        WindowManager.openModal("tabela-produto-view.fxml", "Selecionar Produto");
        produto=TabelaProdutoController.produto;
        if (produto != null) {
            btSelecionarProduto.setText(produto.getNome());
            btAdicionar.setDisable(false);
            spQuant.requestFocus();
        } else {
            btSelecionarProduto.setText("Selecione o Produto");
            btAdicionar.setDisable(true);
        }
    }

    @FXML
    void onAdicionar(ActionEvent event) {
        Comanda.Item item=new Comanda.Item(produto,spQuant.getValue());
        tableView.getItems().add(item);
        btSelecionarProduto.setText("Selecione o Produto");
        btAdicionar.setDisable(true);
        spQuant.getValueFactory().setValue(1);
        atualizarTotalComanda();
    }

    @FXML
    void onApagarItem(ActionEvent event) {
        Comanda.Item selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            tableView.getItems().remove(selectedItem);
            atualizarTotalComanda();
        }
    }

    private void carregarComandas() {
        ComandaDAL comandaDAL=new ComandaDAL();
        List<Comanda> comandaList=comandaDAL.get("com_status != 'F'"); //
        lvComandas.setItems(FXCollections.observableArrayList(comandaList));
    }

    public void onSelecionarComanda(MouseEvent mouseEvent) {
        painel.setDisable(false);
        Comanda comandaSelecionada = lvComandas.getSelectionModel().getSelectedItem();
        if (comandaSelecionada != null) {
            ComandaDAL dal = new ComandaDAL();
            comanda = dal.get(comandaSelecionada.getId());

            if (comanda != null) {
                dtData.setValue(comanda.getData());
                tfNumero.setText("" + comanda.getNumero());
                tfNumero.setDisable(true); // Desabilitar edição de comanda existente
                tfDescr.setText(comanda.getDescricao());
                tableView.setItems(FXCollections.observableArrayList(comanda.getItemList()));
                atualizarTotalComanda();
            } else{
                new Alert(Alert.AlertType.ERROR, "Erro ao carregar dados da comanda selecionada.").showAndWait();
                limparFormulario();
            }
        } else {
            limparFormulario();
        }
    }

    private void atualizarTotalComanda() {
        double total = 0;
        for (Comanda.Item item : tableView.getItems()) {
            total += item.getValor();
        }
        tfTotal.setText(String.format("%.2f", total));
    }
}