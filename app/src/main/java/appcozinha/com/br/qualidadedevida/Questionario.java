package appcozinha.com.br.qualidadedevida;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.JavascriptInterface;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import utilitarios.EnviaQuestTask;
import utilitarios.UserFunctions;


public class Questionario extends Activity{
    public static final String PREFS_NAME = "Preferences";
    public static final String PREFS_QUEST = "Questionario";
    private final String TAG = "Questionario";
    WebView wv;
	private String url="file:///android_asset/www/index.html";//file:///android_asset/www/dicas.txt
	@Override
	protected void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.questionario);
		
	    wv=(WebView)findViewById(R.id.webView);
	    wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setBuiltInZoomControls(true);
        wv.addJavascriptInterface(this,"ExemploWebView");

        wv.loadUrl(url);
	}
    //pega as resposta do questionario
    @JavascriptInterface
    public void getForm(String a,String b,String c,String d,String e,String f,String g,String h,String i,String j,String l,String m,String n,String o,String p){
        Log.i("Script","questao1: "+a);
        Log.i("Script","questao2: "+b);
        Log.i("Script","questao3: "+c);
        Log.i("Script","questao4: "+d);
        Log.i("Script","questao5: "+e);
        Log.i("Script","questao6: "+f);
        Log.i("Script","questao7: "+g);
        Log.i("Script","questao8: "+h);
        Log.i("Script","questao9: "+i);
        Log.i("Script","questao10: "+j);
        Log.i("Script","questao11: "+l);
        Log.i("Script","questao12: "+m);
        Log.i("Script","questao13: "+n);
        Log.i("Script","questao14: "+o);
        Log.i("Script","questao15: "+p);

        SharedPreferences quest = PreferenceManager
                .getDefaultSharedPreferences(this);
        JSONArray jsonArray = new JSONArray();
        //jsonArray.put("usuario: "+fname);
        jsonArray.put("resposta1: "+a);
        jsonArray.put("resposta2: "+b);
        jsonArray.put("resposta3: "+c);
        jsonArray.put("resposta4: "+d);
        jsonArray.put("resposta5: "+e);
        jsonArray.put("resposta6: "+f);
        jsonArray.put("resposta7: "+g);
        jsonArray.put("resposta8: "+h);
        jsonArray.put("resposta9: "+i);
        jsonArray.put("resposta10: "+j);
        jsonArray.put("resposta11: "+l);
        jsonArray.put("resposta12: "+m);
        jsonArray.put("resposta13: "+n);
        jsonArray.put("resposta14: "+o);
        jsonArray.put("resposta15: "+p);

        //salva
            SharedPreferences.Editor editor = quest.edit();
            editor.putString(PREFS_QUEST, jsonArray.toString());
            Log.i(TAG, jsonArray.toString());
            editor.commit();

            finish();

    }
    //se nao responder tudo notifica
    @JavascriptInterface
    public void getErro(String erro){
        Log.i("Script","falta preencher: "+erro);
        Toast.makeText(this,"Responda todas as Questões!",Toast.LENGTH_LONG).show();
    }

}
