package br.edu.alfaumuarama.cadastrobiblioteca.db;

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

}
