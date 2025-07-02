package com.example.botekofx.db.dal;

import com.example.botekofx.db.entidades.Categoria;
import com.example.botekofx.db.entidades.Produto;
// Removido import redundante de com.example.botekofx.db.dal.*;
import com.example.botekofx.db.util.SingletonDB;

import java.sql.ResultSet;
import java.sql.SQLException; // Importado para tratar exceção
import java.sql.Statement; // Importado para fechar o statement
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAL implements IDAL<Produto>{
    @Override
    public boolean gravar(Produto entidade) {
        // Validação básica da entidade
        if (entidade == null || entidade.getCategoria() == null) {
            System.err.println("Erro ao gravar produto: Entidade ou categoria nula.");
            return false; // Categoria é NOT NULL no banco
        }

        String sql="""
          INSERT INTO produto(
          cat_id, prod_nome, prod_preco, prod_descr)
          VALUES (#1, '#2', #3, '#4');"""; // prod_preco é numeric, não precisa de aspas
        sql=sql.replace("#1", ""+entidade.getCategoria().getId());
        sql=sql.replace("#2", entidade.getNome());
        sql=sql.replace("#3", String.format("%.2f",entidade.getPreco()).replace(",","."));
        sql=sql.replace("#4", entidade.getDescricao() != null ? entidade.getDescricao() : ""); // Evita "null" literal na query

        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean alterar(Produto entidade) {
        if (entidade == null || entidade.getCategoria() == null) {
            System.err.println("Erro ao alterar produto: Entidade ou categoria nula.");
            return false; // Categoria é NOT NULL no banco
        }
        if (entidade.getId() <= 0) {
            System.err.println("Erro ao alterar produto: ID da entidade inválido.");
            return false;
        }

        String sql="""
                UPDATE produto
                SET cat_id=#1, prod_nome='#2', prod_preco=#3, prod_descr='#4'
                WHERE prod_id=#5"""; // prod_preco é numeric, não precisa de aspas
        sql=sql.replace("#1", ""+entidade.getCategoria().getId());
        sql=sql.replace("#2", entidade.getNome());
        sql=sql.replace("#3", String.format("%.2f",entidade.getPreco()).replace(",","."));
        sql=sql.replace("#4", entidade.getDescricao() != null ? entidade.getDescricao() : ""); // Evita "null" literal
        sql=sql.replace("#5", ""+entidade.getId());
        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean apagar(Produto entidade) {
        if (entidade == null || entidade.getId() <= 0) {
            System.err.println("Erro ao apagar produto: Entidade nula ou ID inválido.");
            return false;
        }
        return SingletonDB.getConexao().
                manipular("DELETE FROM produto WHERE prod_id="+entidade.getId());
    }

    @Override
    public Produto get(int id) {
        if (id <= 0) {
            System.err.println("Erro ao buscar produto: ID inválido.");
            return null;
        }
        Produto produto=null;
        String sql="SELECT * FROM produto WHERE prod_id="+id;
        ResultSet rs = null;
        Statement stmt = null;

        try {
            rs = SingletonDB.getConexao().consultar(sql);
            if (rs != null) {
                stmt = rs.getStatement(); // Obter o Statement para fechá-lo depois
                if (rs.next()) {
                    // cat_id é NOT NULL na tabela produto
                    int catId = rs.getInt("cat_id");
                    Categoria categoria = new CategoriaDAL().get(catId);

                    if (categoria == null) {
                        // Isso indicaria um problema de integridade referencial se cat_id é NOT NULL
                        // ou que CategoriaDAL.get() não encontrou uma categoria que deveria existir.
                        System.err.println("Erro ao buscar produto: Categoria com ID " + catId + " não encontrada para o produto ID " + id);
                        return null; // Não criar produto se a categoria obrigatória não for encontrada
                    }

                    produto = new Produto(id,
                            rs.getString("prod_nome"),
                            rs.getDouble("prod_preco"),
                            rs.getString("prod_descr"), // Pode ser null
                            categoria);
                }
            }
        } catch (SQLException e ){
            System.err.println("Erro de SQL ao buscar produto por ID: " + e.getMessage());
            // e.printStackTrace(); // Para debugging
        } catch (Exception e ){ // Captura genérica para outras exceções inesperadas
            System.err.println("Erro inesperado ao buscar produto por ID: " + e.getMessage());
            // e.printStackTrace(); // Para debugging
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar ResultSet/Statement em ProdutoDAL.get(id): " + e.getMessage());
            }
        }
        return produto;
    }

    @Override
    public List<Produto> get(String filtro) {
        List<Produto> produtos=new ArrayList<>(); // Inicializar sempre
        String sql="SELECT * FROM produto";
        if(filtro != null && !filtro.trim().isEmpty()) { // Checar null e se não está vazia após trim
            sql+=" WHERE "+filtro;
        }

        ResultSet rs = null;
        Statement stmt = null;

        try {
            rs = SingletonDB.getConexao().consultar(sql);
            if (rs != null) {
                stmt = rs.getStatement(); // Obter o Statement para fechá-lo depois
                while (rs.next()) {
                    int catId = rs.getInt("cat_id"); // cat_id é NOT NULL
                    Categoria categoria = new CategoriaDAL().get(catId);

                    if (categoria == null) {
                        System.err.println("Aviso: Categoria com ID " + catId + " não encontrada para o produto ID " + rs.getInt("prod_id") + ". Produto não será adicionado à lista.");
                        continue; // Pula este produto se a categoria obrigatória não for encontrada
                    }

                    produtos.add(new Produto(rs.getInt("prod_id"),
                            rs.getString("prod_nome"),
                            rs.getDouble("prod_preco"),
                            rs.getString("prod_descr"), // Pode ser null
                            categoria));
                }
            }
        } catch (SQLException e ){
            System.err.println("Erro de SQL ao buscar lista de produtos: " + e.getMessage());
            // e.printStackTrace();
        } catch (Exception e ){
            System.err.println("Erro inesperado ao buscar lista de produtos: " + e.getMessage());
            // e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar ResultSet/Statement em ProdutoDAL.get(filtro): " + e.getMessage());
            }
        }
        return produtos;
    }
}