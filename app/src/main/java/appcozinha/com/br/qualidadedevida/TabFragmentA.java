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

public class TabFragmentA extends Fragment {
    public static final String PREFS_DICAS = "Dicas";
    private final String TAG = "CADASTRO_ALUNO";
    private WebView wd;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.tab_layout_a,container,false);
        wd=(WebView)v.findViewById(R.id.dicasview1);
       wd.getSettings().setJavaScriptEnabled(true);
        String dicasitem=null;
        //pega dicas salvas
        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_DICAS, 0);

        dicasitem=settings.getString("PrefDicas", "");
        String colchete=dicasitem.replace("["," ");
        Log.i(TAG, "chegou no dicasitem: " + colchete);
        String[] item1= colchete.split("</ul>");
        String ja=item1[0].toString();
        Log.i(TAG,"chegou no ja: "+ja);

        String data="<html><body><center>"+ja+"</center></body></html>";
        wd.loadDataWithBaseURL(null,data,"text/html","utf-8",null);


        return v;
	}

}
