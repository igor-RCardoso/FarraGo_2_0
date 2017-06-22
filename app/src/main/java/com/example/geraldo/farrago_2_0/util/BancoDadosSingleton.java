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
            "  nome TEXT NOT NULL," +
            "  usuario TEXT NOT NULL," +
            "  senhaUsuario TEXT NOT NULL," +
            "  emailUsuario TEXT NOT NULL," +
            "  telefone TEXT NOT NULL," +
            "  cpf TEXT NOT NULL," +
            "  dataNascimento TEXT NOT NULL," +
            "  tipo INTEGER NOT NULL," +
            "  reputacao INTEGER UNSIGNED NULL," +
            "  PRIMARY KEY(id));",

            "CREATE TABLE Organizador (" +
            "  id INTEGER UNSIGNED NOT NOT NULL," +
            "  nomeFantasia TEXT NOT NULL," +
            "  nomeReal TEXT NOT NULL," +
            "  nomeResponsavel TEXT NOT NULL," +
            "  emailOrg TEXT NOT NULL," +
            "  senhaOrg TEXT NOT NULL," +
            "  endereco TEXT NOT NULL," +
            "  telefone TEXT NOT NULL," +
            "  cnpj TEXT NOT NULL," +
            "  PRIMARY KEY(id));",

            "CREATE TABLE Compra_Venda (" +
            "  id INTEGER UNSIGNED NOT NULL," +
            "  Usuario_id INTEGER UNSIGNED NOT NULL," +
            "  avaliacao TEXT NULL," +
            "  comentario TEXT NULL," +
            "  PRIMARY KEY(id)," +
            "  INDEX Compra_Venda_FKIndex1(Usuario_id)," +
            "  FOREIGN KEY(Usuario_id)" +
            "    REFERENCES Usuario(id));",

            "CREATE TABLE Pagamento (" +
            "  id INTEGER UNSIGNED NOT NULL," +
            "  Compra_Venda_id INTEGER UNSIGNED NOT NULL," +
            "  vencimento TEXT NOT NULL," +
            "  valor REAL NOT NULL," +
            "  PRIMARY KEY(id)," +
            "  INDEX Pagamento_FKIndex1(Compra_Venda_id)," +
            "  FOREIGN KEY(Compra_Venda_id)" +
            "    REFERENCES Compra_Venda(id));",


            "CREATE TABLE Evento (" +
            "  id INTEGER UNSIGNED NOT NULL," +
            "  Organizador_id INTEGER UNSIGNED NOT NULL," +
            "  nomeEvento TEXT NOT NULL," +
            "  endereco TEXT NOT NULL," +
            "  horario TEXT NOT NULL," +
            "  dataEvento TEXT NOT NULL," +
            "  faixaEtaria INTEGER UNSIGNED NOT NULL," +
            "  descricao TEXT NOT NULL," +
            "  tema TEXT NOT NULL," +
            "  PRIMARY KEY(id)," +
            "  INDEX Evento_FKIndex1(Organizador_id)," +
            "  FOREIGN KEY(Organizador_id)" +
            "    REFERENCES Organizador(id));",

            "CREATE TABLE Ingresso (" +
            "  id INTEGER UNSIGNED NOT NULL," +
            "  Evento_id INTEGER UNSIGNED NOT NULL," +
            "  sexo TEXT NOT NULL," +
            "  lote INTEGER UNSIGNED NOT NULL," +
            "  preco REAL NOT NULL," +
            "  qtdDisponivel INTEGER UNSIGNED NULL," +
            "  PRIMARY KEY(id)," +
            "  INDEX Ingresso_FKIndex2(Organizador_id)," +
            "  INDEX Ingresso_FKIndex3(Evento_id)," +
            "  FOREIGN KEY(Evento_id)" +
            "    REFERENCES Evento(id));",

            "CREATE TABLE Item_de_Compra (" +
            "  id INTEGER UNSIGNED NOT NULL," +
            "  Usuario_id INTEGER UNSIGNED NOT NULL," +
            "  Ingresso_id INTEGER UNSIGNED NOT NULL," +
            "  Compra_Venda_id INTEGER UNSIGNED NOT NULL," +
            "  qrCode INTEGER UNSIGNED NULL," +
            "  PRIMARY KEY(id)," +
            "  INDEX Item_de_Compra_FKIndex1(Ingresso_id)," +
            "  INDEX Item_de_Compra_FKIndex2(Usuario_id)," +
            "  INDEX Item_de_Compra_FKIndex3(Compra_Venda_id)," +
            "  FOREIGN KEY(Ingresso_id)" +
            "    REFERENCES Ingresso(id)," +
            "  FOREIGN KEY(Usuario_id)" +
            "    REFERENCES Usuario(id));",
            "  FOREIGN KEY(Usuario_id)" +
            "    REFERENCES Compra_Venda(id));",

            "CREATE TABLE Tags (" +
            "  id INTEGER UNSIGNED NOT NULL," +
            "  Evento_id INTEGER UNSIGNED NOT NULL," +
            "  descricao TEXT NOT NULL," +
            "  nome TEXT NOT NULL," +
            "  PRIMARY KEY(id)," +
            "  INDEX Tags_FKIndex1(Evento_id)," +
            "  FOREIGN KEY(Evento_id)" +
            "    REFERENCES Evento(id));",

            "CREATE TABLE Fotos (" +
            "  id INTEGER UNSIGNED NOT NULL," +
            "  Evento_id INTEGER UNSIGNED NOT NULL," +
            "  foto TEXT NOT NULL," +
            "  fotoPerfil TEXT NOT NULL," +
            "  PRIMARY KEY(id)," +
            "  INDEX Fotos_FKIndex1(Evento_id)," +
            "  FOREIGN KEY(Evento_id)" +
            "    REFERENCES Evento(id)" +
            ");",

            "INSERT INTO Usuario(id,nome,usuario,senhaUsuario,emailUsuario,telefone,cpf,dataNascimento,tipo) VALUES" +
                    "(0,'Fabio','Fabio','1234','fabio.godoy@ufv.br','9999999999','99999999999','05/11/1996',0)",

            "INSERT INTO Usuario(id,nome,usuario,senhaUsuario,emailUsuario,telefone,cpf,dataNascimento,tipo) VALUES" +
                    "(1,'Igor','Igor','1234','igor.cardoso@ufv.br','9999999999','99999999999','24/04/1994',0)",

            "INSERT INTO Usuario(id,nome,usuario,senhaUsuario,emailUsuario,telefone,cpf,dataNascimento,tipo) VALUES" +
                    "(2,'Julio','Julio','1234','julio.pinheiro@ufv.br','9999999999','99999999999','17/10/1996',0)",

            "INSERT INTO Organizador(id,nomeFantasia,nomeReal,nomeResponsavel,emailOrg,senhaOrg,endereco,telefone,cnpj) VALUES" +
                    "(0, 'Eventos Legais S/A', 'Evento Legais S/A', 'Joesley', 'eventoslegais@gmail.com', '1234', 'Brasil', '9999999999', '99999999999')",

            "INSERT INTO Organizador(id,nomeFantasia,nomeReal,nomeResponsavel,emailOrg,senhaOrg,endereco,telefone,cnpj) VALUES" +
                    "(1, 'Eventos Bacanas S/A', 'Eventos Bacanas S/A', 'Wesley', 'eventosbacanas@gmail.com', '1234', 'Brasil', '9999999999', '99999999999')",

            "INSERT INTO Evento(id,Organizador_id,nomeEvento,endereco,horario,dataEvento,faixaEtaria,descricao,tema) VALUES" +
                    "(0,0,'Evento muito legal', 'Brasil', '20:00', '08/07/2017', '16', 'O evento é muito legal', 'Legal') ",

            "INSERT INTO Evento(id,Organizador_id,nomeEvento,endereco,horario,dataEvento,faixaEtaria,descricao,tema) VALUES" +
                    "(1,0,'Evento super legal', 'Brasil', '20:00', '21/10/2017', '16', 'O evento é super legal', 'Super Legal') ",

            "INSERT INTO Evento(id,Organizador_id,nomeEvento,endereco,horario,dataEvento,faixaEtaria,descricao,tema) VALUES" +
                    "(2,1,'Evento ultra legal', 'Brasil', '20:00', '30/11/2017', '16', 'O evento é ultra legal', 'Ultra Legal') ",

            "INSERT INTO Ingresso(id,Evento_id,sexo,lote,preco,qtdDisponivel) VALUES" +
                    "(0,0,'Unissex',1,40.00,340)",

            "INSERT INTO Ingresso(id,Evento_id,sexo,lote,preco,qtdDisponivel) VALUES" +
                    "(1,1,'Masculino',2,45.00,200)",

            "INSERT INTO Ingresso(id,Evento_id,sexo,lote,preco,qtdDisponivel) VALUES" +
                    "(2,2,'Feminino',2,35.00,220)",

            "INSERT INTO Item_de_Compra(id,Usuario_id,Ingresso_id,Compra_Venda_id,qrCode) VALUES" +
                    "(0,0,0,0,2)",

            "INSERT INTO Item_de_Compra(id,Usuario_id,Ingresso_id,Compra_Venda_id,qrCode) VALUES" +
                    "(1,0,0,0,2)",

            "INSERT INTO Item_de_Compra(id,Usuario_id,Ingresso_id,Compra_Venda_id,qrCode) VALUES" +
                    "(2,0,1,0,2)",

            "INSERT INTO Item_de_Compra(id,Usuario_id,Ingresso_id,Compra_Venda_id,qrCode) VALUES" +
                    "(3,1,2,1,2)",

            "INSERT INTO Item_de_Compra(id,Usuario_id,Ingresso_id,Compra_Venda_id,qrCode) VALUES" +
                    "(4,1,1,1,2)",

            "INSERT INTO Item_de_Compra(id,Usuario_id,Ingresso_id,Compra_Venda_id,qrCode) VALUES" +
                    "(5,2,2,2,2)",

            "INSERT INTO Compra_Venda(id,Usuario_id,avaliacao,comentario) VALUES" +
                    "(0,0,'Muito bom','Muito legal')",

            "INSERT INTO Compra_Venda(id,Usuario_id,avaliacao,comentario) VALUES" +
                    "(1,1,'Muito bom','Muito legal')",

            "INSERT INTO Compra_Venda(id,Usuario_id,avaliacao,comentario) VALUES" +
                    "(2,2,'Muito bom','Muito legal')"
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
