package br.com.pauloarcenio.enums;

public enum TipoLittle {
    NORMAL("Normal"), EXTRAFORTE("Extraforte"), SPRAY("Spray");

    private final String nomeLittle;

    public static String getStringTipoLittle(String nome) {
        String retorno = "";
        for (TipoLittle tipo : TipoLittle.values()) {
            if (tipo.getNomeLittle().equalsIgnoreCase(nome)) {
                retorno = tipo.getNomeLittle();
            }
        }
        return retorno;
    }

    public static TipoLittle getTipoPorNome(String nome) {
        TipoLittle retorno = NORMAL;
        for (TipoLittle tipo : TipoLittle.values()) {
            if (tipo.getNomeLittle().equalsIgnoreCase(nome)) {
                retorno = tipo;
            }
        }
        return retorno;
    }

    private TipoLittle(String nomeLittle) {
        this.nomeLittle = nomeLittle;
    }

    public String getNomeLittle() {
        return nomeLittle;
    }

}
