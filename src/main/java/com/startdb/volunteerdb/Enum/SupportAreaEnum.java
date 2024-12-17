package com.startdb.volunteerdb.Enum;

public enum SupportAreaEnum {
    APRENDIZADO_TECNOLOGIA("Aprendizado de Tecnologia"),
    REALIZACAO_TAREFAS("Realização de Tarefas Cotidianas"),
    PARTICIPACAO_ATIVIDADES("Participação em Atividades Recreativas");

    private final String description;

    SupportAreaEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
