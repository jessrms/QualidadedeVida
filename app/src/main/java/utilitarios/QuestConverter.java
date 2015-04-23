package utilitarios;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

import bean.Aluno;
import dao.AlunoDAO;

/**
 * Created by Peterson on 23/03/2015.
 */
//converte resposta do questionario em json
public class QuestConverter {
    public static final String PREFS_QUEST = "Questionario";
    public static final String PREFS_NAME = "Preferences";
    public String toJSON(Context context) {

        try {
            JSONStringer jsonStringer=new JSONStringer();
            jsonStringer.object().key("list").array().object().key("questionario").array();
            SharedPreferences quest = PreferenceManager
                    .getDefaultSharedPreferences(context);
            JSONArray jsonArray2 = new JSONArray(quest.getString(PREFS_QUEST, "[]"));
            //for (int i = 0; i < jsonArray2.length(); i++) {
            SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,0);
            String fname=settings.getString("PrefNome", "");
                jsonStringer.object()
                        .key(fname).value(jsonArray2)
                        .endObject();
                Log.d("QuestConvert", jsonArray2 + "");
            //}
            jsonStringer.endArray().endObject().endArray().endObject();
            Log.i("CADASTRO_ALUNO", jsonStringer.toString());
            return jsonStringer.toString();
        } catch (JSONException e) {
            Log.i("CADASTRO_ALUNO", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
