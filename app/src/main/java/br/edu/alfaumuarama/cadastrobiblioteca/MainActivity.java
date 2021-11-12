package br.edu.alfaumuarama.cadastrobiblioteca;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import br.edu.alfaumuarama.cadastrobiblioteca.db.Livro;
import br.edu.alfaumuarama.cadastrobiblioteca.db.TbLivro;

public class MainActivity extends ListActivity {

    ArrayList<Livro> listaLivros;
    Button btnAdicionar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdicionar = findViewById(R.id.btnAdicionar);
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CadastroLivroActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        buscarDados();
    }

    private void buscarDados() {
        TbLivro tbLivro = new TbLivro(MainActivity.this);
        listaLivros = tbLivro.buscar(MainActivity.this, "", "", "", "");

        ListAdapter adapter = new SimpleAdapter(
                MainActivity.this, //classe que controla a lista
                getListaAlunos(), //lista com os dados em formato ArrayList<HashMap>
                R.layout.activity_list_livro, //modelo da linha da listagem
                new String[]{"titulo", "autor", "descricao", "editora", "ano", "pagina"}, //campos que vem da lista de dados
                new int[]{R.id.txtTitulo, R.id.txtAutor, R.id.txtDescricao, R.id.txtEditora, R.id.txtAno, R.id.txtPaginas} //campos visuais do modelo
        );

        setListAdapter(adapter);
    }

    //metodo para converter a listaAluno para o formato array de hashmap
    private ArrayList<HashMap<String, String>> getListaAlunos() {
        ArrayList<HashMap<String, String>> listaRetorno = new ArrayList<>();

        for (int i = 0; i < listaLivros.size(); i++)
            listaRetorno.add(listaLivros.get(i).toHashMap());

        return listaRetorno;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Livro livro = listaLivros.get(position);
        Intent telaCadastro = new Intent(this, CadastroLivroActivity.class);

        Bundle params = new Bundle();
        params.putString("titulo", livro.titulo);
        params.putString("autor", livro.autor);
        params.putString("descricao", livro.descricao);
        params.putString("editora", livro.editora);
        params.putInt("ano", livro.ano);
        params.putInt("pagina", livro.pagina);

        telaCadastro.putExtras(params);
        startActivity(telaCadastro);
    }
}