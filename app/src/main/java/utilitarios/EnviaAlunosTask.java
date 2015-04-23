package utilitarios;

import java.util.List;

import org.json.JSONException;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import adapter.AlunoConverter;
import appcozinha.com.br.qualidadedevida.ListaAlunosActivity;
import bean.Aluno;
import dao.AlunoDAO;
//envia usuario para servidor web
public class EnviaAlunosTask extends AsyncTask<Object, Object, String>{
	private static final String TAG="CADASTRO_ALUNO";
		private final String url="http://ip.jsontest.com/";//http://192.168.1.128:8080/AlunoWeb/receber-json
		
		private Context context;
		private ProgressDialog progress;
		
		public EnviaAlunosTask(Context context){
			this.context=context;
		}

		protected void onPreExecute(){

			progress= ProgressDialog.show(context,"Aguarde...",
					"Enviando dados para o servidor web",true,true);

		}
		
		protected String doInBackground(Object... params) {
			//pega os dados do usuario
			AlunoDAO dao= new AlunoDAO(context);
			List<Aluno> lista=dao.listar();
			dao.close();
			
			String json= new AlunoConverter().toJSON(lista);
			//envia para servidor
			String jsonResposta= new WebClient(url).post(json);
			Log.i(TAG, "json"+jsonResposta);
			return jsonResposta;
			
			
			
		}
		protected void onPostExecute(String result){
			
			
				progress.dismiss();
                //ListaAlunosActivity enbled=new ListaAlunosActivity();
                //enbled.enableRegister(true);
				//Toast.makeText(context, result, Toast.LENGTH_LONG).show();
				 Toast.makeText(context,
                         "Salvo com Sucesso!", Toast.LENGTH_SHORT).show();
				

		}

}
