package br.edu.alfaumuarama.cadastrobiblioteca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.edu.alfaumuarama.cadastrobiblioteca.db.Livro;
import br.edu.alfaumuarama.cadastrobiblioteca.db.TbLivro;

public class CadastroLivroActivity extends AppCompatActivity {

    EditText txtTitulo, txtAutor, txtDescricao, txtEditora, txtAno, txtNumPaginas;
    Button btnAdicionar, btnExcluir, btnCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_livro);

        txtTitulo     = findViewById(R.id.txtTitulo);
        txtAutor      = findViewById(R.id.txtAutor);
        txtDescricao  = findViewById(R.id.txtDescricao);
        txtEditora    = findViewById(R.id.txtEditora);
        txtAno        = findViewById(R.id.txtAno);
        txtNumPaginas = findViewById(R.id.txtNumPaginas);

        Intent caminhoTela = getIntent();
        if (caminhoTela != null) {
            Bundle params = caminhoTela.getExtras();

            if (params != null) {
                txtTitulo.setText(params.getString("titulo"));
                txtAutor.setText(params.getString("autor"));
                txtDescricao.setText(params.getString("descricao"));
                txtEditora.setText(params.getString("editora"));
                txtAno.setText(String.valueOf(params.getInt("ano")));
                txtNumPaginas.setText(String.valueOf(params.getInt("pagina")));
//                txtTitulo.setEnabled(false);
            }
        }

        btnAdicionar = findViewById(R.id.btnAdicionar);
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvar();
            }
        });

        btnExcluir = findViewById(R.id.btnExcluir);
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                excluir();
            }
        });

        btnCancelar = findViewById(R.id.btnCancela);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voltar();
            }
        });
    }

    private void voltar() {
        onBackPressed();
    }

    private void salvar() {
        //criando a classe livro com os dados dos campos da tela
        Livro livro     = new Livro();
        livro.titulo    = txtTitulo.getText().toString();
        livro.autor     = txtAutor.getText().toString();
        livro.descricao = txtDescricao.getText().toString();
        livro.editora   = txtEditora.getText().toString();
        livro.ano       = Integer.parseInt(txtAno.getText().toString());
        livro.pagina    = Integer.parseInt(txtNumPaginas.getText().toString());

        //salvando os dados da classe livro no banco de dados
        TbLivro tbLivro = new TbLivro(this);
        tbLivro.salvar(this, livro);

        voltar();
    }

    private void excluir() {
        //excluindo o livro no banco de dados pelo Titulo
        TbLivro tbLivro = new TbLivro(this);
        tbLivro.excluir(this, txtTitulo.getText().toString());

        voltar();
    }
}