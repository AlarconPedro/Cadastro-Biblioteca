package br.edu.alfaumuarama.cadastrobiblioteca.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BancoDeDados {
    private SQLiteDatabase banco;

    private static final BancoDeDados dataBase = new BancoDeDados();
    public static BancoDeDados getInstance() {
        return dataBase;
    }

    public SQLiteDatabase getMeuBanco() {
        return banco;
    }

    private void criarBanco(Context context) {
        banco = context.openOrCreateDatabase("Banco.db", Context.MODE_PRIVATE, null);
    }

    public void abrirBanco(Context context) {
        if (banco == null) {
            criarBanco(context);
        }
        else {
            if (!banco.isOpen()) {
                criarBanco(context);
            }
        }
    }

    public void fecharBanco() {
        if (banco != null) {
            if (banco.isOpen()) {
                banco.close();
            }
        }
    }

    public void executarSQL(Context context, String sSQL) {
        try {
            abrirBanco(context);
            banco.execSQL(sSQL);
            fecharBanco();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void adicionarNovaColuna(Context context, String tabela, String coluna, String tipoDados) {
        if (existeColunaNaTabela(context, tabela, coluna) == false) {
            executarSQL(context, "ALTER TABLE " + tabela + " ADD COLUMN " + coluna + " " + tipoDados);
        }
    }

    private boolean existeColunaNaTabela(Context context, String tabela, String coluna) {
        try {
            abrirBanco(context);
            Cursor cursor = banco.rawQuery("SELECT * FROM " + tabela + " LIMIT 0", null);
            int indiceColuina = cursor.getColumnIndex(coluna);

            fecharBanco();

            return (indiceColuina != -1);
        }
        catch (Exception ex) {
            return false;
        }
    }
}
