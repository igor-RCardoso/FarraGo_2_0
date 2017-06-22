package com.example.geraldo.farrago_2_0.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Fábio on 22/06/2017.
 */

public class BancoDadosSingleton {
    private final String NOME_BANCO = new String("farra_go_db");
    public static BancoDadosSingleton getInstance() {   return ourInstance;    }
    private static BancoDadosSingleton ourInstance = new BancoDadosSingleton();
    private final String[] SCRIPT_DATA_BASE_CREATE = new String[] {"CREATE TABLE Usuario (" +
            "  id INTEGER UNSIGNED NOT NULL," +
            "  nome VARCHAR NOT NULL," +
            "  usuario VARCHAR NOT NULL," +
            "  senhaUsuario VARCHAR NOT NULL," +
            "  emailUsuario VARCHAR NOT NULL," +
            "  telefone VARCHAR NOT NULL," +
            "  cpf VARCHAR NOT NULL," +
            "  dataNascimento DATE NOT NULL," +
            "  tipo BOOL NOT NULL," +
            "  reputacao INTEGER UNSIGNED NULL," +
            "  PRIMARY KEY(id));",

            "CREATE TABLE Organizador (" +
            "  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT," +
            "  nomeFantasia VARCHAR(255) NULL," +
            "  nomeReal VARCHAR(255) NULL," +
            "  nomeResponsavel VARCHAR(255) NULL," +
            "  emailOrg VARCHAR(255) NULL," +
            "  senhaOrg VARCHAR(255) NULL," +
            "  endereco VARCHAR(255) NULL," +
            "  telefone VARCHAR(255) NULL," +
            "  cnpj VARCHAR(255) NULL," +
            "  PRIMARY KEY(id));",

            "CREATE TABLE Compra_Venda (" +
            "  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT," +
            "  Usuario_id INTEGER UNSIGNED NOT NULL," +
            "  avaliacao VARCHAR NULL," +
            "  comentario VARCHAR NULL," +
            "  PRIMARY KEY(id)," +
            "  INDEX Compra_Venda_FKIndex1(Usuario_id)," +
            "  FOREIGN KEY(Usuario_id)" +
            "    REFERENCES Usuario(id));",

            "CREATE TABLE Pagamento (" +
            "  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT," +
            "  Compra_Venda_id INTEGER UNSIGNED NOT NULL," +
            "  vencimento DATE NOT NULL," +
            "  valor DOUBLE NOT NULL," +
            "  PRIMARY KEY(id)," +
            "  INDEX Pagamento_FKIndex1(Compra_Venda_id)," +
            "  FOREIGN KEY(Compra_Venda_id)" +
            "    REFERENCES Compra_Venda(id));",


            "CREATE TABLE Evento (" +
            "  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT," +
            "  Organizador_id INTEGER UNSIGNED NOT NULL," +
            "  nomeEvento VARCHAR(255) NOT NULL," +
            "  endereco VARCHAR(255) NOT NULL," +
            "  horario VARCHAR(255) NOT NULL," +
            "  dataEvento DATE NOT NULL," +
            "  faixaEtaria INTEGER UNSIGNED NOT NULL," +
            "  descricao VARCHAR(255) NOT NULL," +
            "  tema VARCHAR(255) NOT NULL," +
            "  tipo VARCHAR(255) NOT NULL," +
            "  PRIMARY KEY(id)," +
            "  INDEX Evento_FKIndex1(Organizador_id)," +
            "  FOREIGN KEY(Organizador_id)" +
            "    REFERENCES Organizador(id));",

            "CREATE TABLE Ingresso (" +
            "  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT," +
            "  Evento_id INTEGER UNSIGNED NOT NULL," +
            "  Organizador_id INTEGER UNSIGNED NOT NULL," +
            "  sexo VARCHAR NOT NULL," +
            "  lote INTEGER UNSIGNED NOT NULL," +
            "  preco DOUBLE NOT NULL," +
            "  qtdDisponivel INTEGER UNSIGNED NULL," +
            "  PRIMARY KEY(id)," +
            "  INDEX Ingresso_FKIndex2(Organizador_id)," +
            "  INDEX Ingresso_FKIndex3(Evento_id)," +
            "  FOREIGN KEY(Organizador_id)" +
            "    REFERENCES Organizador(id)," +
            "  FOREIGN KEY(Evento_id)" +
            "    REFERENCES Evento(id));",

            "CREATE TABLE Item_de_Compra (" +
            "  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT," +
            "  Usuario_id INTEGER UNSIGNED NOT NULL," +
            "  Ingresso_id INTEGER UNSIGNED NOT NULL," +
            "  qrCode INTEGER UNSIGNED NULL," +
            "  PRIMARY KEY(id)," +
            "  INDEX Item_de_Compra_FKIndex1(Ingresso_id)," +
            "  INDEX Item_de_Compra_FKIndex2(Usuario_id)," +
            "  FOREIGN KEY(Ingresso_id)" +
            "    REFERENCES Ingresso(id)," +
            "  FOREIGN KEY(Usuario_id)" +
            "    REFERENCES Usuario(id));",

            "CREATE TABLE Tags (" +
            "  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT," +
            "  Evento_id INTEGER UNSIGNED NOT NULL," +
            "  descricao VARCHAR(255) NOT NULL," +
            "  nome VARCHAR(255) NOT NULL," +
            "  PRIMARY KEY(id)," +
            "  INDEX Tags_FKIndex1(Evento_id)," +
            "  FOREIGN KEY(Evento_id)" +
            "    REFERENCES Evento(id));",

            "CREATE TABLE Fotos (" +
            "  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT," +
            "  Evento_id INTEGER UNSIGNED NOT NULL," +
            "  foto VARCHAR(255) NOT NULL," +
            "  fotoPerfil VARCHAR(255) NOT NULL," +
            "  PRIMARY KEY(id)," +
            "  INDEX Fotos_FKIndex1(Evento_id)," +
            "  FOREIGN KEY(Evento_id)" +
            "    REFERENCES Evento(id)" +
            ");"
    };

    SQLiteDatabase db;

    private BancoDadosSingleton() {
        Log.i("INFORMACAO BD", "entrou construtor");
        db = MyApp.getContext().openOrCreateDatabase(NOME_BANCO, Context.MODE_PRIVATE, null);
        Log.i("INFORMACAO BD", "criou ou abriu");
        Cursor c = buscar("sqlite_master", null, "type = 'table'", "");
        if(c.getCount() == 1) {
            for (int i = 0; i < SCRIPT_DATA_BASE_CREATE.length; i++)
                db.execSQL(SCRIPT_DATA_BASE_CREATE[i]);
            Log.i("BANCO_DADOS", "Criou tabelas e as populou");
        }
        c.close();
        Log.i("BANCO_DADOS", "Abriu conexao com o bd");
    }

    public long inserir(String tabelas, ContentValues valores){
        long id = db.insert(tabelas, null, valores);
        return id;
    }
    public int atualizar(String tabelas, ContentValues valores, String where){
        int count = db.update(tabelas, valores, where, null);
        Log.i("BANCO_DADOS", "Atualizou [" + count + "] registros");
        return count;
    }

    public int deletar(String tabelas, String where){
        int id = db.delete(tabelas, where, null);
        return id;
    }
    public Cursor buscar(String tabelas, String[] colunas, String where, String orderBy){
        Cursor c;
        if(!where.equals(""))
            c = db.query(tabelas,colunas,where,null,null,null,null,orderBy);
        else
            c = db.query(tabelas,colunas,null, null, null, null, orderBy);
        return c;
    }
    public void abrir(){

    }
    public void fechar(){

    }

}
