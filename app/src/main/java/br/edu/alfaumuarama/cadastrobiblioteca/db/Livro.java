package br.edu.alfaumuarama.cadastrobiblioteca.db;

import java.util.HashMap;

public class Livro {
    public String titulo;
    public String autor;
    public String descricao;
    public String editora;
    public int ano;
    public int pagina;

    public HashMap<String,String> toHashMap() {
        HashMap<String,String> item = new HashMap<>();
        item.put("titulo", titulo);
        item.put("descricao", descricao);
        item.put("editora", editora);
        item.put("autor", autor);
        item.put("ano", String.valueOf(ano));
        item.put("pagina", String.valueOf(pagina));

        return item;
    }
}
