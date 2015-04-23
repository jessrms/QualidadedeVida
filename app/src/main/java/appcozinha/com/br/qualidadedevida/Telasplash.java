package appcozinha.com.br.qualidadedevida;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import java.util.Calendar;

public class Telasplash extends Activity implements Runnable{
	private final int DELAYSEGUNDOS= 5000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);
		Toast.makeText(this, "Aguarde abrindo....", Toast.LENGTH_LONG).show();
		Handler handler= new Handler();
		handler.postDelayed(this, DELAYSEGUNDOS);
        //iniciando o alarmmanager
        boolean alarmeAtivo = (PendingIntent.getBroadcast(this, 0, new Intent("ALARME_DISPARADO"), PendingIntent.FLAG_NO_CREATE) == null);

        if(alarmeAtivo){
            Log.i("Script", "Novo alarme");

            Intent intent = new Intent("ALARME_DISPARADO");
            PendingIntent p = PendingIntent.getBroadcast(this, 0, intent, 0);

            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(System.currentTimeMillis());
            c.add(Calendar.SECOND, 3);

            AlarmManager alarme = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarme.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 3600000, p);//3600000
        }
        else{
            Log.i("Script", "Alarme já ativo");
        }
	}

	@Override
	public void run() {
		SharedPreferences prefs = getSharedPreferences("meu_arquivo_de_preferencias", 0);
	       boolean jaLogou = prefs.getBoolean("estaLogado", false);

	        if(jaLogou) {
	            // chama a tela inicial
	        	startActivity(new Intent(this,ListaAlunosActivity.class));
	    		finish();
	        }else {
	            // chama a tela de login
	        	startActivity(new Intent(this,Login.class));
	    		finish();
	        }
		
	}
    @Override
    public void onDestroy(){
        super.onDestroy();

    }

}
