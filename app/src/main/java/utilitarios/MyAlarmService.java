package utilitarios;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


import appcozinha.com.br.qualidadedevida.MainActivity;
import appcozinha.com.br.qualidadedevida.R;
import bean.Dicas;

/**
 * Created by Peterson on 20/03/2015.
 */
public class MyAlarmService extends Service {
    public static final String PREFS_DICAS = "Dicas";
    ArrayList<String> worldlist;
    ArrayList<Dicas> world;
    JSONObject jsonobject;
    JSONArray jsonarray;
    private final String TAG = "CADASTRO_ALUNO";
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate()
    {
        // TODO Auto-generated method stub
        super.onCreate();
        Log.i("Script","-> chegou no oncreate do MyAlarmService");
        new DownloadJSON().execute();
        stopSelf();
    }


    @Override
    public int onStartCommand(Intent intent, int flags,int startId)
    {
        return(super.onStartCommand(intent, flags,startId));
    }
    //download do json
    private class DownloadJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            // Locate the WorldPopulation Class
            world = new ArrayList<Dicas>();
            // Create an array to populate the spinner
            worldlist = new ArrayList<String>();
            // JSON file URL address
            jsonobject = JSONfunctions
                    .getJSONfromURL("http://www.androidsesi.0catch.com/tips.txt");
            //  jsonobject="file:///android_asset/empresas.txt";
            try {
                // Locate the NodeList name
                jsonarray = jsonobject.getJSONArray("tips");
                for (int i = 0; i < jsonarray.length(); i++) {
                    jsonobject = jsonarray.getJSONObject(i);
                    Dicas worldpop = new Dicas();

                    worldpop.setId(jsonobject.optInt("type"));
                    worldpop.setImagem(jsonobject.optString("deion"));
                    // worldpop.setPopulation(jsonobject.optString("population"));
                    // worldpop.setFlag(jsonobject.optString("flag"));
                    world.add(worldpop);

                    // Populate spinner with country names original:deion
                    worldlist.add(jsonobject.optString("deion"));
                    Log.i(TAG,"Chegou Json: "+worldlist);
                    // Dicas dicas=getI;
                    //salva o json
                    SharedPreferences settings =getSharedPreferences(PREFS_DICAS,0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("PrefDicas",worldlist.toString());
                    editor.commit();
                    Log.i(TAG,"Salvou no Sharepreference: "+editor);
                    //se tem informacao mostra notificacao para usuario
                    if (worldlist != null) {
                        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        PendingIntent p = PendingIntent.getActivity(getApplicationContext(), 0, new Intent(getApplicationContext(), MainActivity.class), 0);

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplication());
                        builder.setTicker("Novas dicas do SESI");
                        builder.setContentTitle("QUALIDADE DE VIDA");
                        builder.setContentText("Dicas SESI");
                        builder.setSmallIcon(R.drawable.ic_launcher);
                        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
                        builder.setContentIntent(p);
                        Log.i("Script","->notificou!");
                        Notification n = builder.build();
                        n.vibrate = new long[]{150, 300, 150, 600};
                        n.flags = Notification.FLAG_AUTO_CANCEL;
                        nm.notify(R.drawable.ic_launcher, n);
                    }else{
                        Log.i("Script","-> nao chegou nada no json!");
                    }
                }
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void imageView) {

        }

    }

    @Override
    public void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
}
