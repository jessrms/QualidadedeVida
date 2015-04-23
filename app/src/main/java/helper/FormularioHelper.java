package helper;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import appcozinha.com.br.qualidadedevida.FormularioActivity;
import appcozinha.com.br.qualidadedevida.R;
import bean.Aluno;

public class FormularioHelper {
    public static final String PREFS_EMPRESAS = "Empres";
    private String[] sex = new String[]{"MASCULINO","FEMININO"};
	private EditText nome;
	private EditText sobrenome;
	private EditText email;
	private Spinner sexo;
	private Spinner empresa;
	private EditText nit;
	private EditText datanascimento;
	private EditText altura;
	private EditText peso;
	private EditText imc;
//	private ImageView foto;
	private static final String TAG="CADASTRO_ALUNO";
	
	private Aluno aluno;
	
	public FormularioHelper(FormularioActivity activity){
		
		
		 nome=(EditText) activity.findViewById(R.id.edNome);
		 sobrenome=(EditText)activity.findViewById(R.id.edSobrenome);
		 email=(EditText)activity.findViewById(R.id.edEmail);
		 sexo=(Spinner)activity.findViewById(R.id.spinnersexo);
		 empresa=(Spinner)activity.findViewById(R.id.spinnerempresa);
		 nit=(EditText)activity.findViewById(R.id.edNit);
		 datanascimento=(EditText)activity.findViewById(R.id.ednascimento);
		 altura=(EditText)activity.findViewById(R.id.edaltura);
		 peso=(EditText)activity.findViewById(R.id.edpeso);
		 imc=(EditText)activity.findViewById(R.id.edimc);
		 
		//foto=(ImageView)activity.findViewById(R.id.foto);
		 aluno=new Aluno();
	}
    //pega os dados do usuario
	public Aluno getAluno(){
		aluno.setNome(nome.getText().toString());
		Log.i(TAG,"helper: "+ aluno.getNome());
		aluno.setSobrenome(sobrenome.getText().toString());
		Log.i(TAG,"helper: "+ aluno.getSobrenome());
		aluno.setEmail(email.getText().toString());
		Log.i(TAG,"helper: "+ aluno.getEmail());
		aluno.setSexo(sexo.getSelectedItem().toString());
		Log.i(TAG,"helper: "+ aluno.getSexo());
		aluno.setEmpresa(empresa.getSelectedItem().toString());
		Log.i(TAG,"helper: "+ aluno.getEmpresa());
		aluno.setNit(nit.getText().toString());
		Log.i(TAG,"helper: "+ aluno.getNit());
		aluno.setDatanascimento(datanascimento.getText().toString());
		Log.i(TAG,"helper: "+ aluno.getDatanascimento());
		aluno.setAltura(altura.getText().toString());
		Log.i(TAG,"helper: "+ aluno.getAltura());
		aluno.setPeso(peso.getText().toString());
		Log.i(TAG,"helper: "+ aluno.getPeso());

		aluno.setImc(imc.getText().toString());
		Log.i(TAG,"helper: "+ aluno.getImc());
		return aluno;
		
	}
    //seta os dados do usuario
	public void setAluno(Aluno aluno){
		
		nome.setText(aluno.getNome());
		Log.i(TAG,"Helper setAluno: "+ aluno.getNome());
		sobrenome.setText(aluno.getSobrenome());
		Log.i(TAG,"Helper setAluno: "+ aluno.getSobrenome());
		
		sexo.setTag(aluno.getSexo());
		Log.i(TAG,"Helper setAluno: "+ aluno.getSexo());
		empresa.setTag(aluno.getEmpresa());
        Log.i(TAG,"Helper setAluno: "+ aluno.getEmpresa());
        email.setText(aluno.getEmail());
		Log.i(TAG,"Helper setAluno: "+ aluno.getEmail());

		datanascimento.setText(aluno.getDatanascimento());
		Log.i(TAG,"Helper setAluno: "+ aluno.getDatanascimento());
		nit.setText(aluno.getNit());
		Log.i(TAG,"Helper setAluno: "+ aluno.getNit());
		
		peso.setText(aluno.getPeso());
		Log.i(TAG,"Helper setAluno: "+ aluno.getPeso());
		altura.setText(aluno.getAltura());
		Log.i(TAG,"Helper setAluno: "+ aluno.getAltura());
		
		imc.setText(aluno.getImc());
		Log.i(TAG,"Helper setAluno: "+ aluno.getImc());




        this.aluno=aluno;
	}

}
