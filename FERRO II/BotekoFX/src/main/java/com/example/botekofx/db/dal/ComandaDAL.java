package com.example.botekofx.db.dal;

import com.example.botekofx.db.entidades.*;
import com.example.botekofx.db.util.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; // Importado para fechar o statement
import java.time.LocalDate; // Importado
import java.util.ArrayList;
import java.util.List;

public class ComandaDAL implements IDAL<Comanda>{
    // ... (métodos gravar, alterar, apagar permanecem como na correção anterior) ...
    @Override
    public boolean gravar(Comanda entidade) {
        String sql="""
                INSERT INTO comanda(
                com_numero, com_data, com_desc, com_valor, com_status, tpg_id) 
                VALUES (#1, #N2, '#3', #4, '#5', #N6);"""; // Usar #N para datas e nulls
        sql=sql.replace("#1", ""+entidade.getNumero());

        if (entidade.getData() != null) {
            sql=sql.replace("#N2", "'" + entidade.getData().toString() + "'");
        } else {
            sql=sql.replace("#N2", "null");
        }

        sql=sql.replace("#3", entidade.getDescricao());
        sql=sql.replace("#4", String.format("%.2f",entidade.getValor()).replace(",","."));
        sql=sql.replace("#5", ""+entidade.getStatus());

        if (entidade.getTipoPagamento() != null) {
            sql=sql.replace("#N6", ""+entidade.getTipoPagamento().getId());
        } else {
            sql=sql.replace("#N6", "null");
        }

        if(SingletonDB.getConexao().manipular(sql))
        {
            int id=SingletonDB.getConexao().getMaxPK("comanda","com_id");
            if (id == -1 && SingletonDB.getConexao().getMensagemErro().contains("Erro")) {
                return false;
            }
            entidade.setId(id);

            for(Comanda.Item item : entidade.getItemList()){
                String sql2= """
                  INSERT INTO item (com_id, prod_id, it_quantidade) VALUES (#1, #2, #3)
                """;
                sql2=sql2.replace("#1",""+id);
                sql2=sql2.replace("#2",""+item.getProduto().getId());
                sql2=sql2.replace("#3",""+item.getQuantidade());

                if (!SingletonDB.getConexao().manipular(sql2)) {
                    SingletonDB.getConexao().manipular("DELETE FROM comanda WHERE com_id="+id);
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean alterar(Comanda entidade) {
        if(SingletonDB.getConexao().manipular("DELETE FROM item WHERE com_id="+entidade.getId()))
        {
            String sql="""
                    UPDATE comanda
                    SET com_numero=#1, com_data=#N2, com_desc='#3', com_valor=#4, com_status='#5', tpg_id=#N6
                    WHERE com_id=#7;""";
            sql=sql.replace("#1", ""+entidade.getNumero());

            if (entidade.getData() != null) {
                sql=sql.replace("#N2", "'" + entidade.getData().toString() + "'");
            } else {
                sql=sql.replace("#N2", "null");
            }

            sql=sql.replace("#3", entidade.getDescricao());
            sql=sql.replace("#4", String.format("%.2f",entidade.getValor()).replace(",","."));
            sql=sql.replace("#5", ""+entidade.getStatus());

            if (entidade.getTipoPagamento() != null) {
                sql=sql.replace("#N6", ""+entidade.getTipoPagamento().getId());
            } else {
                sql=sql.replace("#N6", "null");
            }
            sql=sql.replace("#7", ""+entidade.getId());

            if(SingletonDB.getConexao().manipular(sql))
            {
                for(Comanda.Item item : entidade.getItemList()){
                    String sql2= """
                      INSERT INTO item (com_id, prod_id, it_quantidade) VALUES (#1, #2, #3)
                    """;
                    sql2=sql2.replace("#1",""+entidade.getId());
                    sql2=sql2.replace("#2",""+item.getProduto().getId());
                    sql2=sql2.replace("#3",""+item.getQuantidade());
                    if (!SingletonDB.getConexao().manipular(sql2)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean apagar(Comanda entidade) {
        if(SingletonDB.getConexao().manipular("DELETE FROM item WHERE com_id="+entidade.getId())) {
            if (SingletonDB.getConexao().manipular("DELETE FROM comanda WHERE com_id=" + entidade.getId()))
                return true;
        }
        return false;
    }


    @Override
    public Comanda get(int id) {
        Comanda comanda = null;
        String sql = "SELECT * FROM comanda WHERE com_id=" + id;
        ResultSet rs = null;
        Statement stmt = null;

        try {
            rs = SingletonDB.getConexao().consultar(sql);
            if (rs != null) {
                stmt = rs.getStatement(); // Para fechar depois
                if (rs.next()) {
                    // Coleta dos dados do ResultSet
                    int comId = rs.getInt("com_id");
                    int comNumero = rs.getInt("com_numero");
                    String comDesc = rs.getString("com_desc");
                    double comValor = rs.getDouble("com_valor");

                    // Tratamento do status
                    String statusStr = rs.getString("com_status");
                    char comStatus = 'A'; // Default, caso seja nulo ou inválido
                    if (statusStr != null && !statusStr.trim().isEmpty()) {
                        comStatus = statusStr.charAt(0);
                    }

                    // Tratamento do TipoPagamento
                    TipoPagamento tipoPagamento = null;
                    int tpgId = rs.getInt("tpg_id");
                    if (!rs.wasNull()) { // Verifica se o tpg_id lido do banco era SQL NULL
                        tipoPagamento = new TipoPagamentoDAL().get(tpgId);
                    }

                    // Instanciação da Comanda com os argumentos corretos
                    comanda = new Comanda(comId, comNumero, comDesc, comValor, comStatus, tipoPagamento);

                    // Tratamento e definição da data
                    java.sql.Date sqlDate = rs.getDate("com_data");
                    if (sqlDate != null) {
                        comanda.setData(sqlDate.toLocalDate());
                    } else {
                        // Se a data do banco for NULL, a comanda.data já foi definida como LocalDate.now()
                        // pelo construtor. Se desejar que seja null, a entidade Comanda precisaria ser ajustada
                        // ou definir explicitamente comanda.setData(null) se permitido pela entidade.
                        // Para manter o comportamento atual do construtor se DB for NULL:
                        // comanda.setData(LocalDate.now()); // Ou deixe como o construtor já fez
                        // Se for para refletir o NULL do DB e a entidade Comanda permitir:
                        comanda.setData(null);
                    }

                    // Carregar os itens da comanda
                    String sql2 = "SELECT * FROM item WHERE com_id=" + comId;
                    ResultSet rs2 = null;
                    Statement stmt2 = null;
                    try {
                        rs2 = SingletonDB.getConexao().consultar(sql2);
                        if (rs2 != null) {
                            stmt2 = rs2.getStatement();
                            while (rs2.next()) {
                                Produto produto = new ProdutoDAL().get(rs2.getInt("prod_id"));
                                if (produto != null) {
                                    comanda.addItem(produto, rs2.getInt("it_quantidade"));
                                } else {
                                    System.err.println("Produto com ID " + rs2.getInt("prod_id") + " não encontrado para item da comanda " + comId);
                                }
                            }
                        }
                    } finally {
                        if (rs2 != null) rs2.close();
                        if (stmt2 != null) stmt2.close();
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro de SQL ao buscar comanda por ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                // Fechar ResultSet e Statement de 'rs'
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar ResultSet/Statement em ComandaDAL.get(id): " + e.getMessage());
            }
        }
        return comanda;
    }

    @Override
    public List<Comanda> get(String filtro) {
        List<Comanda> comandas = new ArrayList<>();
        String sql = "SELECT * FROM comanda";
        if (filtro != null && !filtro.trim().isEmpty()) {
            sql += " WHERE " + filtro;
        }

        ResultSet rs = null;
        Statement stmt = null;

        try {
            rs = SingletonDB.getConexao().consultar(sql);
            if (rs != null) {
                stmt = rs.getStatement(); // Para fechar depois
                while (rs.next()) {
                    // Coleta dos dados do ResultSet
                    int comId = rs.getInt("com_id");
                    int comNumero = rs.getInt("com_numero");
                    String comDesc = rs.getString("com_desc");
                    double comValor = rs.getDouble("com_valor");

                    // Tratamento do status
                    String statusStr = rs.getString("com_status");
                    char comStatus = 'A'; // Default
                    if (statusStr != null && !statusStr.trim().isEmpty()) {
                        comStatus = statusStr.charAt(0);
                    }

                    // Tratamento do TipoPagamento
                    TipoPagamento tipoPagamento = null;
                    int tpgId = rs.getInt("tpg_id");
                    if (!rs.wasNull()) {
                        tipoPagamento = new TipoPagamentoDAL().get(tpgId);
                    }

                    // Instanciação da Comanda
                    Comanda comanda = new Comanda(comId, comNumero, comDesc, comValor, comStatus, tipoPagamento);

                    // Tratamento e definição da data
                    java.sql.Date sqlDate = rs.getDate("com_data");
                    if (sqlDate != null) {
                        comanda.setData(sqlDate.toLocalDate());
                    } else {
                        comanda.setData(null); // Se a data do DB for NULL
                    }

                    // Carregar os itens da comanda
                    String sql2 = "SELECT * FROM item WHERE com_id=" + comId;
                    ResultSet rs2 = null;
                    Statement stmt2 = null;
                    try {
                        rs2 = SingletonDB.getConexao().consultar(sql2);
                        if (rs2 != null) {
                            stmt2 = rs2.getStatement();
                            while (rs2.next()) {
                                Produto produto = new ProdutoDAL().get(rs2.getInt("prod_id"));
                                if (produto != null) {
                                    comanda.addItem(produto, rs2.getInt("it_quantidade"));
                                } else {
                                    System.err.println("Produto com ID " + rs2.getInt("prod_id") + " não encontrado para item da comanda " + comId);
                                }
                            }
                        }
                    } finally {
                        if (rs2 != null) rs2.close();
                        if (stmt2 != null) stmt2.close();
                    }
                    comandas.add(comanda);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro de SQL ao buscar lista de comandas: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                // Fechar ResultSet e Statement de 'rs'
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar ResultSet/Statement em ComandaDAL.get(filtro): " + e.getMessage());
            }
        }
        return comandas;
    }
    public int getProximoNumero() {
        String sql = "SELECT MAX(com_numero) FROM comanda";
        int max = 0;
        ResultSet rs = SingletonDB.getConexao().consultar(sql);
        try {
            if (rs != null && rs.next()) {
                max = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar o próximo número da comanda: " + e.getMessage());
        }
        return max + 1;
    }
}