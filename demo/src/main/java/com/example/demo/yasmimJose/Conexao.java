package com.example.demo.yasmimJose;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {
    public static Connection conectar() {
        Connection conexao = null;
        try {
            // Caminho do banco (localhost = seu computador)
            String url = "jdbc:mysql://localhost:3306/barDB";
            String user = "root";
            String password = "Y@smim101"; // padrão do XAMPP é sem senha

            conexao = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Conectado ao banco com sucesso!");
        } catch (Exception e) {
            System.out.println("❌ Erro ao conectar: " + e.getMessage());
        }
        return conexao;
    }

    public static void main(String[] args) {
        conectar(); // chama o método pra testar
    }
}
