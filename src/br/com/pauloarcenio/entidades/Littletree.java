
package br.com.pauloarcenio.entidades;

import br.com.pauloarcenio.enums.TipoLittle;

public class Littletree extends Produto {

    public Littletree( String nome, TipoLittle tipo, int valor, int quantidade) {
        super(nome, tipo, valor, quantidade);
    }
    
    public Littletree(int id, String nome, TipoLittle tipo, int valor, int quantidade) {
        super(id, nome, tipo, valor, quantidade);
    }

    public Littletree(String nome, int quantidade, int valor) {
        super( nome, quantidade, valor);
    }

    public Littletree(int id, String nome, int quantidade, int valor) {
        super(id, nome, quantidade, valor);
    }
    
}
