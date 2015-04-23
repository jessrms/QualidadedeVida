package dao;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import bean.Aluno;


public class AlunoDAO extends SQLiteOpenHelper{
	private static final int VERSAO=1;
	private static final String TABELA="Aluno";
	private static final String DATABASE="MPAlunos";
	
	private static final String TAG="CADASTRO_ALUNO";
	
		public AlunoDAO(Context context){
		
		super(context,DATABASE,null,VERSAO);
		 
	}
	public void onCreate(SQLiteDatabase database){

		String ddl="CREATE TABLE " + TABELA + "("
				+ "id INTEGER PRIMARY KEY,"
				+"nome TEXT,sobrenome TEXT,email TEXT,"
				+"sexo TEXT,empresa TEXT,nit TEXT,"
				+"datanascimento TEXT,altura TEXT,peso TEXT,imc TEXT,"
				+"foto TEXT)";
		database.execSQL(ddl);
	}

	public void onUpgrade(SQLiteDatabase database,int versaoAntiga,int versaoNova){
		
		String sql="DROP TABLE IF EXISTS"+ TABELA;
		database.execSQL(sql);
		onCreate(database);
	}
	public void cadastrar(Aluno aluno){
		
		ContentValues values=new ContentValues();
		
		values.put("nome",aluno.getNome());
		values.put("sobrenome",aluno.getSobrenome());
		values.put("email",aluno.getEmail());
		values.put("sexo",aluno.getSexo());
		values.put("empresa",aluno.getEmpresa());
		values.put("nit",aluno.getNit());
		values.put("datanascimento",aluno.getDatanascimento());
		values.put("altura",aluno.getAltura());
		values.put("peso",aluno.getPeso());
		values.put("imc",aluno.getImc());
		values.put("foto",aluno.getFoto());
		getWritableDatabase().insert(TABELA, null, values);
		Log.i(TAG,"Aluno cadastrado: "+ aluno.getNome());
	}
	public List<Aluno> listar(){
		
		List<Aluno> lista=new ArrayList<Aluno>();
		String sql="Select * from Aluno order by nome";
		Cursor cursor=getReadableDatabase().rawQuery(sql, null);
		try{
			while(cursor.moveToNext()){
				Aluno aluno=new Aluno();
				
				aluno.setId(cursor.getLong(0));
				aluno.setNome(cursor.getString(1));
				aluno.setSobrenome(cursor.getString(2));
				aluno.setEmail(cursor.getString(3));
				aluno.setSexo(cursor.getString(4));
				aluno.setEmpresa(cursor.getString(5));
				aluno.setNit(cursor.getString(6));
				aluno.setDatanascimento(cursor.getString(7));
				aluno.setAltura(cursor.getString(8));
				aluno.setPeso(cursor.getString(9));
				aluno.setImc(cursor.getString(10));
				aluno.setFoto(cursor.getString(11));
				
				
				lista.add(aluno);
				
			}
		}catch (SQLException e){
			
		}finally{
			cursor.close();
		}
		return lista;	
	}
	public void deletar(Aluno aluno){
		
		String[] args={aluno.getId().toString()};
		
		getWritableDatabase().delete(TABELA, "id=?",args);
		
		Log.i(TAG,"Aluno deletado: "+aluno.getNome());
	}
	
	public void alterar(Aluno aluno){
		ContentValues values= new ContentValues();
		values.put("nome", aluno.getNome());
		values.put("sobrenome", aluno.getSobrenome());
		values.put("email", aluno.getEmail());
		values.put("sexo", aluno.getSexo());
		values.put("empresa", aluno.getEmpresa());
		values.put("nit", aluno.getNit());
		values.put("datanascimento", aluno.getDatanascimento());
		values.put("altura", aluno.getAltura());
		values.put("peso", aluno.getPeso());
		values.put("imc", aluno.getImc());
		values.put("foto", aluno.getFoto());
		
		String[] args={aluno.getId().toString()};
		
		getWritableDatabase().update(TABELA, values, "id=?", args);
		Log.i(TAG, "Aluno alterado: "+ aluno.getNome());
		
	}
	public boolean isAluno(String email){
		String sql="select * from " + TABELA + " where email = ?";
		String[] valores={ email };
		Cursor cursor=null;
		try{
			
			cursor= getReadableDatabase().rawQuery(sql, valores);
			return cursor.getCount() > 0;
		}catch(SQLException e){
			Log.e(TAG,e.getMessage());
			return false;
		}finally{
			cursor.close();
		}
	}

}


