package utilitarios;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import adapter.AlunoConverter;
import appcozinha.com.br.qualidadedevida.ListaAlunosActivity;
import bean.Aluno;
import dao.AlunoDAO;

/**
 * Created by Peterson on 23/03/2015.
 */
//envia json do questionario para servidor web
public class EnviaQuestTask extends AsyncTask<Object, Object, String> {
    private static final String TAG="Resposta do servidor:";
    private final String url="http://ip.jsontest.com/";//http://192.168.1.128:8080/AlunoWeb/receber-json
    public static final String PREFS_QUEST = "Questionario";
    private Context context;
    private ProgressDialog progress;

    public EnviaQuestTask(Context context){
        this.context=context;
    }

    protected void onPreExecute(){

        progress= ProgressDialog.show(context, "Aguarde...",
                "Enviando dados para o servidor web", true, true);

    }

    protected String doInBackground(Object... params) {

        try {
            //pega as informacoes
            SharedPreferences quest = PreferenceManager
                    .getDefaultSharedPreferences(context);

            JSONArray jsonArray2 = new JSONArray(quest.getString(PREFS_QUEST, "[]"));
           // Context lista=jsonArray2.toJSONObject();
            String json = new QuestConverter().toJSON(context);
            //envia json
            String jsonResposta = new WebClient(url).post(json);
            Log.i(TAG, "json" + jsonResposta);
            return jsonResposta;
        }catch (JSONException e){
            Log.i("CADASTRO_ALUNO", e.getMessage());
            e.printStackTrace();
            return null;

        }


    }
    protected void onPostExecute(String result){


        progress.dismiss();
      //  ListaAlunosActivity enbled=new ListaAlunosActivity();
        //enbled.enableRegister(true);
        //Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        Toast.makeText(context,
                "Enviado com Sucesso!", Toast.LENGTH_SHORT).show();


    }

}
