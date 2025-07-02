package com.example.botekofx.db.dal;

import com.example.botekofx.db.entidades.TipoPagamento;
import com.example.botekofx.db.util.SingletonDB; // Corrigido para utilizar a classe SingletonDB

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; // Importado para fechar o statement
import java.util.ArrayList;
import java.util.List;

public class TipoPagamentoDAL implements IDAL<TipoPagamento>{
    @Override
    public boolean gravar(TipoPagamento entidade) {
        String sql="INSERT INTO tipo_pagamento (tpg_descr) VALUES ('#1')"; // Assumindo que ID é serial/default
        sql=sql.replace("#1", entidade.getDescricao());
        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean alterar(TipoPagamento entidade) {
        String sql="UPDATE tipo_pagamento SET tpg_descr='#1' WHERE tpg_id=#2";
        sql=sql.replace("#1", entidade.getDescricao());
        sql=sql.replace("#2", ""+entidade.getId());
        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean apagar(TipoPagamento entidade) {
        return SingletonDB.getConexao().manipular("DELETE FROM tipo_pagamento WHERE tpg_id="+entidade.getId());
    }

    @Override
    public TipoPagamento get(int id) {
        TipoPagamento tipoPagamento = null;
        String sql = "SELECT * FROM tipo_pagamento WHERE tpg_id=" + id;
        ResultSet rs = null;
        Statement stmt = null;

        try {
            if (SingletonDB.getConexao() == null || !SingletonDB.getConexao().getEstadoConexao()) {
                System.err.println("Erro em TipoPagamentoDAL.get(id): Sem conexão com o banco de dados.");
                return null;
            }
            rs = SingletonDB.getConexao().consultar(sql);
            if (rs != null) {
                stmt = rs.getStatement();
                if (rs.next()) {
                    tipoPagamento = new TipoPagamento(rs.getInt("tpg_id"), rs.getString("tpg_descr"));
                }
            } else {
                System.err.println("Erro em TipoPagamentoDAL.get(id): Falha ao consultar o banco. Mensagem: " +
                        (SingletonDB.getConexao() != null ? SingletonDB.getConexao().getMensagemErro() : "Conexão nula"));
            }
        } catch (SQLException e) {
            System.err.println("Erro de SQL em TipoPagamentoDAL.get(id): " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erro inesperado em TipoPagamentoDAL.get(id): " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar ResultSet/Statement em TipoPagamentoDAL.get(id): " + e.getMessage());
            }
        }
        return tipoPagamento;
    }

    @Override
    public List<TipoPagamento> get(String filtro) {
        List<TipoPagamento> tipos = new ArrayList<>();
        String sql = "SELECT * FROM tipo_pagamento";
        if (filtro != null && !filtro.trim().isEmpty()) {
            sql += " WHERE " + filtro;
        }
        ResultSet rs = null;
        Statement stmt = null;

        try {
            if (SingletonDB.getConexao() == null || !SingletonDB.getConexao().getEstadoConexao()) {
                System.err.println("Erro em TipoPagamentoDAL.get(filtro): Sem conexão com o banco de dados.");
                return tipos; // Retorna lista vazia
            }
            rs = SingletonDB.getConexao().consultar(sql);
            if (rs != null) {
                stmt = rs.getStatement();
                while (rs.next()) {
                    tipos.add(new TipoPagamento(rs.getInt("tpg_id"), rs.getString("tpg_descr")));
                }
            } else {
                System.err.println("Erro em TipoPagamentoDAL.get(filtro): Falha ao consultar o banco. Mensagem: " +
                        (SingletonDB.getConexao() != null ? SingletonDB.getConexao().getMensagemErro() : "Conexão nula"));
            }
        } catch (SQLException e) {
            System.err.println("Erro de SQL em TipoPagamentoDAL.get(filtro): " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erro inesperado em TipoPagamentoDAL.get(filtro): " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar ResultSet/Statement em TipoPagamentoDAL.get(filtro): " + e.getMessage());
            }
        }
        return tipos;
    }
}