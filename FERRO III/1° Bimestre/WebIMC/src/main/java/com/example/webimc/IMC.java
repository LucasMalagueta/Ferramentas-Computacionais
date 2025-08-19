package com.example.webimc;

public class IMC {
    public static String getCondicaoFisica(double IMC) {
        String condicao = "";
        if (IMC < 18.5) {
            condicao = "Abaixo do Peso";
        }else if (IMC >= 18.5 && IMC <= 24.9) {
            condicao = "Peso Normal";
        }
        else if (IMC >= 25 && IMC <= 29.9) {
            condicao = "Sobrepeso";
        }
        else if (IMC >= 30 && IMC <= 34.9) {
            condicao = "Obesidade grau I";
        }
        else if (IMC >= 35 && IMC <= 39.9) {
            condicao = "Obesidade grau II";
        }
        else if (IMC >= 40) {
            condicao = "Obesidade grau III (mórbida)";
        }
        return condicao;
    }
}
