package appcozinha.com.br.qualidadedevida;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.HashMap;

public class Registered extends Activity {
    ImageView ib;
	public static final String PREFS_NAME = "Preferences";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.registered);
        ib=(ImageView)findViewById(R.id.ib);

        /**
         * Exibir os detalhes de inscri��o em vis�o de Texto
         **/

        final TextView fname = (TextView)findViewById(R.id.fname);
        final TextView lname = (TextView)findViewById(R.id.lname);
        final TextView uname = (TextView)findViewById(R.id.uname);
        final TextView email = (TextView)findViewById(R.id.email);
        final TextView created_at = (TextView)findViewById(R.id.regat);
        
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        fname.setText(settings.getString("PrefNome", ""));
        lname.setText(settings.getString("PrefSobrenome", ""));
        uname.setText(settings.getString("PrefEmail", ""));
        email.setText(settings.getString("PrefUsuario", ""));
        created_at.setText(settings.getString("PrefCreate", ""));

        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            	 SharedPreferences.Editor editor = settings.edit();
            	 editor.clear();
            	 editor.commit();
            	 
                Intent myIntent = new Intent(view.getContext(), Login.class);
                startActivityForResult(myIntent, 0);
                finish();
            }

        });
        //abre o link do cozinha
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.portaldaindustria.com.br/sesi/canal/canalcozinhabrasil/";

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

    }}
