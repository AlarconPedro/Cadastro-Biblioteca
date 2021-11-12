package br.edu.alfaumuarama.cadastrobiblioteca.db;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

public class TbLivro {

    //construtor da classe TbLivro
    public TbLivro(Context context) {
        String sSQL = "CREATE TABLE IF NOT EXISTS TbLivro (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " titulo TEXT, autor TEXT, descricao TEXT, editora TEXT, " +
                " ano INTEGER, pagina INTEGER)";

        BancoDeDados.getInstance().executarSQL(context, sSQL);
        BancoDeDados.getInstance().adicionarNovaColuna(context, "TbLivro", "id", "INTEGER   ");
    }

    private String addAspas(String texto) {
        return "'" + texto + "'";
    }

    private void inserir(Context context, Livro livro) {
        String sSQL =
                "INSERT INTO TbLivro (titulo, autor, descricao, editora, ano, pagina) VALUES " +
                        "(" +
                        addAspas(livro.titulo) + ", " +
                        addAspas(livro.autor) + ", " +
                        addAspas(livro.descricao) + ", " +
                        addAspas(livro.editora) + ", " +
                        livro.ano + ", " +
                        livro.pagina +
                        ")";

        BancoDeDados.getInstance().executarSQL(context, sSQL);
    }

    public void salvar(Context context, Livro livro) {
        ArrayList<Livro> ListaLivro = buscar(context, livro.titulo,"" , "", "");

        if (!ListaLivro.isEmpty())
            alterar(context, livro);
        else
            inserir(context, livro);
    }

    private void alterar(Context context, Livro livro) {
        String sSQL =
                "UPDATE TbLivro SET " +
                        "  titulo = " + addAspas(livro.titulo) + ", " +
                        "  autor = " + addAspas(livro.autor) + ", " +
                        "  descricao = " + addAspas(livro.descricao) + ", " +
                        "  editora = " + addAspas(livro.editora) + ", " +
                        "  ano = " + livro.ano + ", " +
                        "  pagina = " + livro.pagina + " " +
                        "WHERE titulo = '" + livro.titulo + "'";

        BancoDeDados.getInstance().executarSQL(context, sSQL);
    }

    public void excluir(Context context, String titulo) {
        String sSQL = "DELETE FROM TbLivro WHERE titulo = '" + titulo + "'";
        BancoDeDados.getInstance().executarSQL(context, sSQL);
    }

    public ArrayList<Livro> buscar(Context context, String titulo, String autor, String descricao, String editora) {
        String condicaoSQL = "";

        if (titulo != null) {
            condicaoSQL = "titulo LIKE " + "'" + titulo + "%'";
        }

        BancoDeDados.getInstance().abrirBanco(context);
        Cursor cursor = BancoDeDados.getInstance().getMeuBanco().query(
                "TbLivro", //nome da tabela
                new String[] { "titulo", "autor", "descricao", "editora", "ano", "pagina" }, //colunas retornadas pela busca
                condicaoSQL, //condicao do WHERE
                null, //parametros da condicao do WHERE
                null, //os campos do Group By
                null, //condicao do having
                "titulo", //order by do SQL
                null //limite de registros
        );

        return retornaLista(cursor);
    }

    private ArrayList<Livro> retornaLista(Cursor cursor) {
        ArrayList<Livro> listaRetorno = new ArrayList<>();

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            //buscando o indice das colunas da tabela TbLivro
            int campoTitulo    = cursor.getColumnIndex("titulo");
            int campoAutor     = cursor.getColumnIndex("autor");
            int campoDescricao = cursor.getColumnIndex("descricao");
            int campoEditora   = cursor.getColumnIndex("editora");
            int campoAno       = cursor.getColumnIndex("ano");
            int campoPagina    = cursor.getColumnIndex("pagina");

            for (int i = 0; i < cursor.getCount(); i++) {
                //populando a classe livro com os dados do livro do banco de dados
                Livro livro = new Livro();
                livro.titulo    = cursor.getString(campoTitulo);
                livro.autor     = cursor.getString(campoAutor);
                livro.descricao = cursor.getString(campoDescricao);
                livro.editora   = cursor.getString(campoEditora);
                livro.ano       = cursor.getInt(campoAno);
                livro.pagina    = cursor.getInt(campoPagina);

                listaRetorno.add(livro);
                cursor.moveToNext();
            }
        }
        BancoDeDados.getInstance().fecharBanco();

        return listaRetorno;
    }

}
