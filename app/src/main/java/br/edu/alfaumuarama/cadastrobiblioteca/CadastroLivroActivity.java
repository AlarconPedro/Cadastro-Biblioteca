package br.edu.alfaumuarama.cadastrobiblioteca;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class CadastroLivroActivity extends AppCompatActivity {

    EditText txtTitulo, txtAutor, txtDescricao, txtEditora, txtAno, txtNumPaginas;
    Button btnAdicionar, btnExcluir, btnCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_livro);


    }
}