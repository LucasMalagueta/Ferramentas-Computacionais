package com.example.botekofx.db.util;

public class SingletonDB {
    private static Conexao conexao=null;

    private SingletonDB() {
    }

    public static boolean conectarDB(){
        conexao=new Conexao();
        return conexao.conectar("jdbc:postgresql://localhost/",
                "boteko_db","postgres","postgres123");
    }
    public static Conexao getConexao(){
        return conexao;
    }
}
