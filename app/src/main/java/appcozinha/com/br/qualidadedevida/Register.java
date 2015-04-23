package appcozinha.com.br.qualidadedevida;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import utilitarios.UserFunctions;

public class Register extends Activity {


    /**
     *  JSON Response node nomes.
     **/


    private static String KEY_SUCCESS = "success";
    private static String KEY_UID = "uid";
    private static String KEY_FIRSTNAME = "fname";
    private static String KEY_LASTNAME = "lname";
    private static String KEY_USERNAME = "uname";
    private static String KEY_EMAIL = "email";
    private static String KEY_CREATED_AT = "created_at";
    private static String KEY_ERROR = "error";
    public static final String PREFS_NAME = "Preferences";
    /**
     * Definindo items de layout.
     **/
    ImageView iv;
    EditText inputFirstName;
    EditText inputLastName;
    EditText inputUsername;
    EditText inputEmail;
    EditText inputPassword;
    Button btnRegister;
    Button retornar;
    TextView registerErrorMsg;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
       setContentView(R.layout.register);

    /**
     * Definindo todos os items de layout
     **/
        inputFirstName = (EditText) findViewById(R.id.fname);
        inputLastName = (EditText) findViewById(R.id.lname);
        inputUsername = (EditText) findViewById(R.id.uname);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.pword);
        btnRegister = (Button) findViewById(R.id.register);
        registerErrorMsg = (TextView) findViewById(R.id.register_error);
        iv=(ImageView)findViewById(R.id.im);


/**
 * Button para voltar pra tela de login
 **/

        retornar = (Button) findViewById(R.id.bktologin);
        retornar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), Login.class);
                startActivityForResult(myIntent, 0);
                finish();
            }

        });
        //abre o link do cozinha
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.portaldaindustria.com.br/sesi/canal/canalcozinhabrasil/";

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        /**
         *  Bot�o cadastrar clique evento.  
         * Um Toast � fixado para alertar quando os campos estiverem vazios.  
         * Outra toast � fixado para alertar Username deve conter 5 car�cteres.
         **/

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (  ( !inputUsername.getText().toString().equals("")) && ( !inputPassword.getText().toString().equals("")) && ( !inputFirstName.getText().toString().equals("")) && ( !inputLastName.getText().toString().equals("")) && ( !inputEmail.getText().toString().equals("")) )
                {
                    if ( inputUsername.getText().toString().length() > 4 ){
                    	enableRegister(false);
                    	NetAsync(view);

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),
                                "Usuário deveria conter no mínimo 5 caracteres", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),
                            "Um ou mais campos estão vazios", Toast.LENGTH_SHORT).show();
                }
            }
        });
       }
    /**
     * Async Task para conferir se conex�o de internet est� trabalhando
     **/

   private class NetCheck extends AsyncTask<String,String,Boolean>
    {
        private ProgressDialog nDialog;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            nDialog = new ProgressDialog(Register.this);
            nDialog.setMessage("Carregando..");
            nDialog.setTitle("Conferindo Rede");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);
            nDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... args){


/**
 * Adquire estado de dispositivo atual e confere se a conex�o de internet esta trabalhando tentando Google
 **/
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
                new ProcessRegister().execute();
            }
            else{
            	enableRegister(true);
                nDialog.dismiss();
                registerErrorMsg.setText("Erro em Conexão de Rede");
            }
        }
    }





    private class ProcessRegister extends AsyncTask<String, String, JSONObject> {

/**
 *  definindo Di�logo de Processo
 **/
        private ProgressDialog pDialog;

        String email,password,fname,lname,uname;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            inputUsername = (EditText) findViewById(R.id.uname);
            inputPassword = (EditText) findViewById(R.id.pword);
               fname = inputFirstName.getText().toString();
               lname = inputLastName.getText().toString();
                email = inputEmail.getText().toString();
                uname= inputUsername.getText().toString();
                password = inputPassword.getText().toString();

            pDialog = new ProgressDialog(Register.this);
            pDialog.setTitle("Contactando Servidor");
            pDialog.setMessage("Registrando...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {


        UserFunctions userFunction = new UserFunctions();
        JSONObject json = userFunction.registerUser(fname, lname, email, uname, password);

            return json;


        }
       @Override
        protected void onPostExecute(JSONObject json) {
       /**
        *Checando para mensagem de sucesso.
        **/
                try {
                    if (json.getString(KEY_SUCCESS) != null) {
                        registerErrorMsg.setText("");
                        String res = json.getString(KEY_SUCCESS);

                        String red = json.getString(KEY_ERROR);

                        if(Integer.parseInt(res) == 1){
                            pDialog.setTitle("Adquirindo Dados");
                            pDialog.setMessage("Carregando Info");

                            registerErrorMsg.setText("Registrado com Sucesso!");


                           // DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                            JSONObject json_user = json.getJSONObject("user");
                            SharedPreferences settings=getSharedPreferences(PREFS_NAME, 0);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("PrefNome", json_user.getString(KEY_FIRSTNAME));
                            editor.putString("PrefSobrenome", json_user.getString(KEY_LASTNAME));
                            editor.putString("PrefEmail", json_user.getString(KEY_EMAIL));
                            editor.putString("PrefSenha", json_user.getString(KEY_UID));
                            editor.putString("PrefUsuario", json_user.getString(KEY_USERNAME));
                            editor.putString("PrefCreate", json_user.getString(KEY_CREATED_AT));
                            editor.commit();
                            

                            UserFunctions logout = new UserFunctions();
                            logout.logoutUser(getApplicationContext());
             				Intent registered=new Intent(getApplicationContext(),Registered.class);
            				//registered.putExtras(b);
            				startActivity(registered);

                            /**
                             * Feche todas as vis�es antes de lan�ar tela Registrada
                            **/
                            registered.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            pDialog.dismiss();
                            startActivity(registered);


                              finish();
                        }

                        else if (Integer.parseInt(red) ==2){
                        	enableRegister(true);
                        	pDialog.dismiss();
                            registerErrorMsg.setText("Usuário já existe");
                        }
                        else if (Integer.parseInt(red) ==3){
                        	enableRegister(true);
                        	pDialog.dismiss();
                            registerErrorMsg.setText("Email inválido");
                        }

                    }


                        else{
                        	enableRegister(true);	
                        	pDialog.dismiss();

                            registerErrorMsg.setText("Ocorreu um erro ao Registrar");
                        }

                } catch (JSONException e) {
                    e.printStackTrace();


                }
            }}
        public void NetAsync(View view){
            new NetCheck().execute();
        }

        public void enableRegister(boolean status){
        	
            inputFirstName.setEnabled(status);
            inputLastName.setEnabled(status);
            inputUsername.setEnabled(status);
            inputEmail.setEnabled(status);
            inputPassword.setEnabled(status);
            btnRegister.setEnabled(status);
            retornar.setEnabled(status);
        	
    		
    		//btSend.setText(status ? "Enviar" : "Enviando...");
    	}
}


