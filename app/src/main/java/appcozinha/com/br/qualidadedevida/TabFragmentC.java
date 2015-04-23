package appcozinha.com.br.qualidadedevida;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RelativeLayout;

public class TabFragmentC extends Fragment {
    public static final String PREFS_DICAS = "Dicas";
    private final String TAG = "CADASTRO_ALUNO";
    private WebView we;

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

        View vie=inflater.inflate(R.layout.tab_layout_c,container,false);
        we=(WebView)vie.findViewById(R.id.dicasview3);
        we.getSettings().setJavaScriptEnabled(true);
        String dicasitem=null;
        //  dicas salvas
        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_DICAS, 0);

        dicasitem=settings.getString("PrefDicas", "");
        String retira=dicasitem.replace(","," ");
        String tira=retira.replace("]"," ");
        Log.i(TAG, "chegou no dicasitem: " + tira);
        String[] item1= tira.split("</ul>");

        String jc=item1[2].toString();
        Log.i(TAG,"chegou no jc: "+jc);

        String data="<html><body><center>"+jc+"</center></body></html>";

        we.loadDataWithBaseURL(null,data,"text/html","utf-8",null);


        return vie;
    }

}
