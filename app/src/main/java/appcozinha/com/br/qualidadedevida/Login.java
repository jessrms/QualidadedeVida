package appcozinha.com.br.qualidadedevida;



import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import utilitarios.UserFunctions;

public class Login extends Activity {

    Button btnLogin;
    Button Btnregister;
    
    EditText inputEmail;
    EditText inputPassword;
    TextView reset;
    public static final String PREFS_NAME = "Preferences";
    private TextView loginErrorMsg;
    ImageView iv;
    /**
     * Chamou quando a atividade � criada primeiro.
     */
    private static String KEY_SUCCESS = "success";
    private static String KEY_UID = "uid";
    private static String KEY_USERNAME = "uname";
    private static String KEY_FIRSTNAME = "fname";
    private static String KEY_LASTNAME = "lname";
    private static String KEY_EMAIL = "email";
    private static String KEY_CREATED_AT = "created_at";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.pword);
        Btnregister = (Button) findViewById(R.id.registerbtn);
        btnLogin = (Button) findViewById(R.id.login);
        reset = (TextView)findViewById(R.id.esqueceu);
        loginErrorMsg = (TextView) findViewById(R.id.loginErrorMsg);
        iv=(ImageView)findViewById(R.id.iv);
        //chama a tela de recuperar senha
        reset.setPaintFlags(reset.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);
        reset.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent myIntent = new Intent(v.getContext(), PasswordReset.class);
	            startActivityForResult(myIntent, 0);
	            finish();   
	
			}
		});
      	            
        

        //chama a tela de fazer registro
        Btnregister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	
                Intent myIntent = new Intent(view.getContext(), Register.class);
                startActivityForResult(myIntent, 0);
                finish();
             }});

/**
 *  Login button evento de click  
 * Um Toast � fixado para alertar quando o E-mail e campo de Contra-senha est� vazio
 **/
        
       
        
        btnLogin.setOnClickListener(new View.OnClickListener() {
        	
            public void onClick(View view) {
            	
            	if (  ( !inputEmail.getText().toString().equals("")) && ( !inputPassword.getText().toString().equals("")) )
                {
                	enableViews(false);
                    NetAsync(view);
                }
                else if ( ( !inputEmail.getText().toString().equals("")) )
                {
                    Toast.makeText(getApplicationContext(),
                            "Campo de Senha Vazio!", Toast.LENGTH_SHORT).show();
                }
                else if ( ( !inputPassword.getText().toString().equals("")) )
                {
                    Toast.makeText(getApplicationContext(),
                            "Campo de Email Vazio!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),
                            "Campo de Email e Senha estão Vazios!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    //ao clicar na imagem abre o link do cozinha
    iv.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String url = "http://www.portaldaindustria.com.br/sesi/canal/canalcozinhabrasil/";

            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }
    });
    }
/**
 * Async Task para conferir se conex�o de internet est� trabalhando.
 **/

    private class NetCheck extends AsyncTask<String,String,Boolean>
    {
           private ProgressDialog nDialog;
           
        @Override
        protected void onPreExecute(){
   
            super.onPreExecute();

            nDialog = new ProgressDialog(Login.this);
           // nDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT)); 
            nDialog.setTitle("Conferindo Rede");
            nDialog.setMessage("Carregando..");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);
            nDialog.show();
        }
        /**
         * Adquire estado de dispositivo atual e confere a conex�o de internet tentando Google
        **/
        @Override
        protected Boolean doInBackground(String... args){

               ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()) {
   
                try {
                    URL url = new URL("http://www.google.com");
                    HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                    urlc.setConnectTimeout(3000);
                    urlc.connect();
                    if (urlc.getResponseCode() == 200) {
                        return true;
                    }
                } catch (MalformedURLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return false;

        }
        @Override
        protected void onPostExecute(Boolean th){

            if(th == true){
   
                nDialog.dismiss();
                new ProcessLogin().execute();
            }
            else{
            	enableViews(true);
                nDialog.dismiss();
                loginErrorMsg.setText("Erro em Conexão de Rede!");
            }
        }
    }

    /**
     * Async Task para adquirir e enviar dados a Meu banco de dados por respone de JSON.
     **/
    private class ProcessLogin extends AsyncTask<String, String, JSONObject> {


        private ProgressDialog pDialog;

        String email,password;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            inputEmail = (EditText) findViewById(R.id.email);
            inputPassword = (EditText) findViewById(R.id.pword);
            email = inputEmail.getText().toString();
            password = inputPassword.getText().toString();
            pDialog = new ProgressDialog(Login.this);
            pDialog.setTitle("Contactando Servidor");
            pDialog.setMessage("Logando...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

            UserFunctions userFunction = new UserFunctions();
            JSONObject json = userFunction.loginUser(email, password);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
   
        	try {
               if (json.getString(KEY_SUCCESS) != null) {

                    String res = json.getString(KEY_SUCCESS);

                    if(Integer.parseInt(res) == 1){
                        pDialog.setMessage("Carregando Espaço de Usuário");
                        pDialog.setTitle("Adquirindo Dados");

                        JSONObject json_user = json.getJSONObject("user");

                        UserFunctions logout = new UserFunctions();
                        logout.logoutUser(getApplicationContext());
                        SharedPreferences settings=getSharedPreferences(PREFS_NAME, 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("PrefNome", json_user.getString(KEY_FIRSTNAME));
                        editor.putString("PrefSobrenome", json_user.getString(KEY_LASTNAME));
                        editor.putString("PrefEmail", json_user.getString(KEY_EMAIL));
                        editor.putString("PrefSenha",json_user.getString(KEY_UID));
                        editor.putString("PrefUsuario", json_user.getString(KEY_USERNAME));
                        editor.putString("PrefCreate", json_user.getString(KEY_CREATED_AT));
                       // editor.clear();
                        editor.commit();

                        Intent upanel = new Intent(getApplicationContext(), ListaAlunosActivity.class);
                        upanel.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        pDialog.dismiss();
                        startActivity(upanel);
                        /**
                         * fecha tela de login
                         **/
                        finish();
                    }else{
                    	enableViews(true);
                        pDialog.dismiss();
                        loginErrorMsg.setText("Nome/Senha Incorretos!");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
       }
    }
    public void NetAsync(View view){
   
        new NetCheck().execute();
    }
    //bloqueia o tela de login
    public void enableViews(boolean status){
    	btnLogin.setEnabled(status);
    	Btnregister.setEnabled(status);
    	reset.setEnabled(status);
    	inputEmail.setEnabled(status);
    	inputPassword.setEnabled(status);
	
		
		//btSend.setText(status ? "Enviar" : "Enviando...");
	}
}