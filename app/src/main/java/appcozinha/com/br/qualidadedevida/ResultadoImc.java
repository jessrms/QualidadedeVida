package appcozinha.com.br.qualidadedevida;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import bean.Aluno;
import bean.Dicas;
import dao.AlunoDAO;
import helper.FormularioHelper;
import utilitarios.EnviaAlunosTask;
import utilitarios.JSONfunctions;
import utilitarios.Mascaras;

public class ResultadoImc extends Activity{
    public static final String PREFS_DICAS = "Dicas";
    JSONObject jsonobject;
    JSONArray jsonarray;
    ProgressDialog mProgressDialog;
    ArrayList<String> worldlist;
    ArrayList<Dicas> world;
    private int mPaginaAtual;
    private List<Dicas> listaDicas;

    private ListView listview;
    private final String TAG = "CADASTRO_ALUNO";
    private Dicas alunoSelecionado=null;
	private TextView resultado;
	private TextView tvinformacao;

	private TextView tvrisco;
	private Button ir;
    private Aluno aluno=null;
    private FormularioHelper helper;
    private ImageView ie;
    public static final String IMC = "ResulatdoImc";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.resultadoimc);
        ie=(ImageView)findViewById(R.id.ie);
		
		ir=(Button)findViewById(R.id.btir);
		resultado=(TextView)findViewById(R.id.tvresult);
		tvinformacao=(TextView)findViewById(R.id.tvinforma);
		tvrisco=(TextView)findViewById(R.id.tvrisc);

        SharedPreferences settings = getSharedPreferences(IMC, 0);

            resultado.setText(settings.getString("Imc",""));
        //pega o imc compara e mostra o resultado
            if (resultado != null) {
               Log.i(TAG,"resultado: "+resultado);
                float   res = Float.parseFloat(resultado.getText().toString());
                Log.i(TAG,"resultado: "+res);

                String informacao = "Ainda não calculado";
                String riscos = "Riscos a Saúde";


                if (res < 15) {
                    informacao = "Abaixo do Peso I.";
                    riscos = "Anorexia, bulemia, osteoporose e auto consumo de massa muscular.";
                }
                if ((res >= 15) && (res < 18.59)) {
                    informacao = "Abaixo do Peso.";
                    riscos = "Transtornos digestivos, debilidade, fadiga crônica, stress, ansiedade e difusão das hormonas.";
                }
                if ((res >= 18.6) && (res < 24.99)) {
                    informacao = "Peso Normal.";
                    riscos = "Estado normal, bom nível de energia, vitalidade e boa condição física. Mantenha-se assim.";
                }
                if ((res >= 25) && (res < 29.99)) {
                    informacao = "Acima do Peso.";
                    riscos = "Fadiga, problemas digestivos, má circulação nas pernas e varizes";
                }
                if ((res >= 30) && (res < 34.99)) {
                    informacao = "Obesidade I.";
                    riscos = "maior probabilidade de complicações como diabetes, hipertensão arterial e hipercolesterolemia.";
                }
                if ((res >= 35) && (res < 39.99)) {
                    informacao = "Obesidade II.";
                    riscos = " Seu excesso de peso já pode estar provocando um risco muito elevado de complicações metabólicas, como diabetes, hipertensão arterial e hipercolesterolemia, além de predispor a doenças osteoarticulares diversas.";
                }
                if (res >= 40) {
                    informacao = "Obesidade III.";
                    riscos = "Falta de ar, apneia; sonolência; trombose pulmonar; úlceras varicosas; cancro do cólon, uterino e mamário; refluxo esofágico; discriminação social, laboral e sexual.";
                }

                tvinformacao.setText(String.valueOf(informacao));
                tvrisco.setText(String.valueOf(riscos));
            }

        //fecha a tela
	     ir.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
                finish();

			}
		});
        //abre o link do cozinha
        ie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.portaldaindustria.com.br/sesi/canal/canalcozinhabrasil/";

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
	}


}
	        		
		




