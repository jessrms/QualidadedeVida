package appcozinha.com.br.qualidadedevida;

/**
 * Created by Peterson on 05/03/2015.
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import bean.Aluno;
import bean.Empresas;
import dao.AlunoDAO;
import helper.FormularioHelper;
import utilitarios.JSONfunctions;
import utilitarios.Mascaras;
import utilitarios.ValidaCPF;

public class FormularioActivity extends Activity {
    JSONObject jsonobject;
    JSONArray jsonarray;
    ProgressDialog mProgressDialog;
    ArrayList<String> worldlist;
    ArrayList<Empresas> world;
    public static final String PREFS_EMPRESAS = "Empres";
    public static final String PREFS_NAME = "Preferences";
    private Spinner sp;
    private String tvinformacao,tvrisco;
    EditText nit,imc;
    EditText edaltura,edpeso,datanascimento;
    private String[] sexo = new String[]{"MASCULINO","FEMININO"};
    private Spinner spn;
    private final String TAG = "CADASTRO_ALUNO";
    public static final String IMC = "ResulatdoImc";
    EditText name;
    EditText sobrenome;
    EditText email;


    private Button botao;
    private FormularioHelper helper;
    private Aluno alunoParaSerAlterado = null;

    float num1, num2, resultado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario);
        helper=new FormularioHelper(this);
        botao=(Button)findViewById(R.id.sbSalvar);
        nit=(EditText)findViewById(R.id.edNit);
        nit.addTextChangedListener(Mascaras.insert("###.###.###-##", nit));

        edaltura=(EditText)findViewById(R.id.edaltura);
        edaltura.addTextChangedListener(Mascaras.insert("#.##", edaltura));

        edpeso=(EditText)findViewById(R.id.edpeso);
        edpeso.addTextChangedListener(Mascaras.insert("###", edpeso));

        datanascimento=(EditText)findViewById(R.id.ednascimento);
        datanascimento.addTextChangedListener(Mascaras.insert("##/##/####",	 datanascimento));
        imc=(EditText)findViewById(R.id.edimc);

        //verifica se tem dados no formulario
        alunoParaSerAlterado = (Aluno)getIntent().getSerializableExtra("ALUNO_SELECIONADO");

        if(alunoParaSerAlterado != null){

            helper.setAluno(alunoParaSerAlterado);

        }else{

        Bundle b= getIntent().getExtras();
        //novo usuario
			name = (EditText) findViewById(R.id.edNome);
			sobrenome=(EditText)findViewById(R.id.edSobrenome);
			email = (EditText) findViewById(R.id.edEmail);

			name.setText(name.getText()+ b.getString("nome"));
	        sobrenome.setText(sobrenome.getText()+ b.getString("sobrenome"));
	        email.setText(email.getText()+ b.getString("email"));
            Log.e(TAG,"entrou no bundle");

		}
       //pega as empresas cadastrada no servidor
       String empresaitem=null;

        SharedPreferences settings = getSharedPreferences(PREFS_EMPRESAS, 0);
       // JSONArray jsonArray2 =  new  JSONArray ( settings . getString ( "PrefEmpres" ,  "" ));
        empresaitem=settings.getString("PrefEmpres", "");
        Log.i(TAG,"chegou no formulario: "+empresaitem);
        String retira=empresaitem.replace("[","SEM EMPRESA ,");
        String colchete=retira.replace("]"," ");
        final List<String> myList = new ArrayList<String>(Arrays.asList(colchete.split(",")));

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,myList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp=(Spinner)findViewById(R.id.spinnerempresa);


        sp.setAdapter(adapter);
        //pega o valor salva e carrega no spinner
        if(alunoParaSerAlterado != null) {
            String compareValue = alunoParaSerAlterado.getEmpresa();


            if (!compareValue.equals(null)) {
                int spinnerPostion = adapter.getPosition(compareValue);
                sp.setSelection(spinnerPostion);
                spinnerPostion = 0;
            }
        }
        //carrega todos os valores ao clicar no spinner
        int posicao=sp.getSelectedItemPosition();
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int posicao, long id) {
                //editext do nit
            /*    nit=(EditText)findViewById(R.id.edNit);
                if(posicao > 0){
                    //se for maior que zero(sem empresa) habilita o editext para preenchimento
                    nit.setEnabled(true);
                }else{
                    //se for igual a zero(sem empresa) deixa o editext bloqueado
                    nit.setEnabled(false);
                }*/
                   int nome = parent.getPositionForView(view);
                   Log.i(TAG,"valor da posicao: "+nome);

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });
        //spinner sexo
        ArrayAdapter<String> adap = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,sexo);
        adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spn=(Spinner)findViewById(R.id.spinnersexo);
        //carrega o valor salvo
        spn.setAdapter(adap);
        if(alunoParaSerAlterado != null) {
            String comparaValor = alunoParaSerAlterado.getSexo();


            if (!comparaValor.equals(null)) {
                int spinnerPosicao = adap.getPosition(comparaValor);
                spn.setSelection(spinnerPosicao);
                spinnerPosicao = 0;
            }
        }
        //carrega todos o valores ao clicar no spinner
        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            String Valorselecionado;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Valorselecionado = parent.getItemAtPosition(position).toString();

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });


     //salva as informacoes no banco
        botao.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view) {
                String CPF=nit.getText().toString();
                if (ValidaCPF.isCPF(CPF) == true) {
                    Calculo();
                    Aluno aluno = helper.getAluno();

                    AlunoDAO dao = new AlunoDAO(FormularioActivity.this);

                    if (aluno.getId() == null) {
                        dao.cadastrar(aluno);

                    } else {
                        dao.alterar(aluno);
                    }

                    dao.close();

                    SharedPreferences settings = getSharedPreferences(IMC, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("Imc", imc.getText().toString());
                    editor.commit();
                    finish();

                }else{
                    Toast.makeText(getApplicationContext(),"CPF Inválido !!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    //calcula o imc
    public void Calculo(){
        float num1 =  Float.parseFloat(edpeso.getText().toString());
        float num2 = Float.parseFloat(edaltura.getText().toString());
        float resultado = (num1 / (num2 * num2));

        imc.setText(String.valueOf(resultado));

    }

}
