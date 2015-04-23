package appcozinha.com.br.qualidadedevida;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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


public class PasswordReset extends Activity {

private static String KEY_SUCCESS = "success";
private static String KEY_ERROR = "error";

  EditText email;
  TextView alert;
  Button resetpass;
  Button voltar;
  ImageView ia;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.passwordreset);
    //volta para login
        voltar = (Button) findViewById(R.id.bktolog);
        voltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), Login.class);
                startActivityForResult(myIntent, 0);
                finish();
            }

        });


        email = (EditText) findViewById(R.id.forpas);
        alert = (TextView) findViewById(R.id.alert);
        resetpass = (Button) findViewById(R.id.respass);
        resetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	if (  ( !email.getText().toString().equals(""))  )
                {
            		enableEmail(false);
                	NetAsync(view);
                }
                else if ( ( email.getText().toString().equals("")) )
                {
                    Toast.makeText(getApplicationContext(),
                            "Campo de Email Vazio!", Toast.LENGTH_SHORT).show();
                }


            }



        });
        //abre o link do cozinha
        ia=(ImageView)findViewById(R.id.ia);
        ia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.portaldaindustria.com.br/sesi/canal/canalcozinhabrasil/";

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

    private class NetCheck extends AsyncTask<String,String,Boolean>

                {
                    private ProgressDialog nDialog;

                    @Override
                    protected void onPreExecute(){
                        super.onPreExecute();
                        nDialog = new ProgressDialog(PasswordReset.this);
                        nDialog.setMessage("Carregando..");
                        nDialog.setTitle("Conferindo Rede");
                        nDialog.setIndeterminate(false);
                        nDialog.setCancelable(true);
                        nDialog.show();
                    }

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
                            new ProcessRegister().execute();
                        }
                        else{
                        	enableEmail(true);
                            nDialog.dismiss();
                            alert.setText("Erro em Conexão de Rede");
                        }
                    }
                }





                private class ProcessRegister extends AsyncTask<String, String, JSONObject> {


                    private ProgressDialog pDialog;

                    String forgotpassword;
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        forgotpassword = email.getText().toString();

                        pDialog = new ProgressDialog(PasswordReset.this);
                        pDialog.setTitle("Contactando Servidor");
                        pDialog.setMessage("Adquirindo Dados...");
                        pDialog.setIndeterminate(false);
                        pDialog.setCancelable(true);
                        pDialog.show();
                    }

                    @Override
                    protected JSONObject doInBackground(String... args) {


                        UserFunctions userFunction = new UserFunctions();
                        JSONObject json = userFunction.forPass(forgotpassword);
                        return json;


                    }


                    @Override
                    protected void onPostExecute(JSONObject json) {
                  /**
                   * Checando se o Processo de Mudan�a de senha � sucesss
                   **/
                        try {
                            if (json.getString(KEY_SUCCESS) != null) {
                                alert.setText("");
                                String res = json.getString(KEY_SUCCESS);
                                String red = json.getString(KEY_ERROR);


                                if(Integer.parseInt(res) == 1){
                                	enableEmail(true);
                                   pDialog.dismiss();
                                    alert.setText("Um email de recuperação será enviado a você, para mais detalhes confira o email.");



                                }
                                else if (Integer.parseInt(red) == 2)
                                {    
                                	enableEmail(true);
                                	pDialog.dismiss();
                                    alert.setText("Seu email não existe em nosso banco de dados.");
                                }
                                else {
                                	enableEmail(true);
                                    pDialog.dismiss();
                                    alert.setText("Error occured in changing Password");
                                }




                            }}
                        catch (JSONException e) {
                            e.printStackTrace();


                        }
                    }}
            public void NetAsync(View view){
                new NetCheck().execute();
            }
            public void enableEmail(boolean status){
            	email.setEnabled(status);
            	resetpass.setEnabled(status);
            	voltar.setEnabled(status);
            	
        		
        		//btSend.setText(status ? "Enviar" : "Enviando...");
        	}
}





