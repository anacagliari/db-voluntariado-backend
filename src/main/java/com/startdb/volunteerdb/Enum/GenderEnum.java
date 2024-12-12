package com.startdb.volunteerdb.Enum;

public enum GenderEnum {

    FEMININO("feminino"),
    MASCULINO("masculino");

    private String valor;

    private GenderEnum(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
  
}
