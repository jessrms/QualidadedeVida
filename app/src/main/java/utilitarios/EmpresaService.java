package utilitarios;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import bean.Empresas;


/**
 * Created by Peterson on 27/03/2015.
 */
public class EmpresaService extends Service {
    public static final String PREFS_EMPRESAS = "Empres";
    ArrayList<String> worldemp;
    ArrayList<Empresas> worldep;
    JSONObject jsonobject;
    JSONArray jsonarray;
    private final String TAG = "CADASTRO_EMPRESAS";
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate()
    {
        // TODO Auto-generated method stub
        super.onCreate();
        Log.i("Script", "-> chegou no oncreate do EmpresaService!");
        new DownloadEmpresas().execute();
        stopSelf();
    }


    @Override
    public int onStartCommand(Intent intent, int flags,int startId)
    {
        return(super.onStartCommand(intent, flags,startId));

    }
    private class DownloadEmpresas extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            // Locate the WorldPopulation Class
            worldep = new ArrayList<Empresas>();
            // Create an array to populate the spinner
            worldemp = new ArrayList<String>();
            // JSON file URL address
            jsonobject = JSONfunctions
                    .getJSONfromURL("http://www.androidsesi.0catch.com/empresas.txt");
            //  jsonobject="file:///android_asset/empresas.txt";
            try {
                // Locate the NodeList name
                jsonarray = jsonobject.getJSONArray("employers");
                for (int i = 0; i < jsonarray.length(); i++) {
                    jsonobject = jsonarray.getJSONObject(i);
                    Empresas worldpop = new Empresas();

                    worldpop.setId(jsonobject.optInt("id"));
                    worldpop.setEmpresa(jsonobject.optString("name"));
                    // worldpop.setPopulation(jsonobject.optString("population"));
                    // worldpop.setFlag(jsonobject.optString("flag"));
                    worldep.add(worldpop);

                    // Populate spinner with country names original:deion
                    worldemp.add(jsonobject.optString("name"));
                    Log.i(TAG,"Chegou Json: "+worldemp);
                    // Dicas dicas=getI;
           /*        SharedPreferences prefs =  PreferenceManager
                            . getDefaultSharedPreferences(getApplication());
                    JSONArray jsonArray =  new  JSONArray ();
                    jsonArray . put (worldemp );

                    SharedPreferences.Editor editor = prefs . edit ();
                    editor . putString ( PREFS_EMPRESAS , jsonArray . toString ());
                  //  System . out . println ( jsonArray . toString ());
                    editor . commit ();*/

                    SharedPreferences settings =getSharedPreferences(PREFS_EMPRESAS,0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("PrefEmpres",worldemp.toString());

                    editor.commit();
                    Log.i(TAG,"Salvou no Sharepreference Empresas: "+editor);


                }
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void imageView) {
           /*
            Empresas bean=new Empresas();
            bean.getEmpresa(worldemp);
            EmpresasDAO dao = new EmpresasDAO(getApplication());
            dao.cadastrar(bean);
            */
        }


    }

    @Override
    public void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
}
