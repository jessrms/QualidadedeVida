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

public class TabFragmentB extends Fragment {
    public static final String PREFS_DICAS = "Dicas";
    private final String TAG = "CADASTRO_ALUNO";
    private WebView wc;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.tab_layout_b,container,false);
        wc=(WebView)view.findViewById(R.id.dicasview2);
        wc.getSettings().setJavaScriptEnabled(true);
        String dicasitem=null;
        //  dicas salvas
        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_DICAS, 0);

        dicasitem=settings.getString("PrefDicas", "");
        String retira=dicasitem.replace(","," ");
        Log.i(TAG, "chegou no dicasitem: " + retira);
        String[] item2= retira.split("</ul>");
        String jb=item2[1].toString();
        Log.i(TAG,"chegou no jb: "+jb);

        String data="<html><body><center>"+jb+"</center></body></html>";

        wc.loadDataWithBaseURL(null,data,"text/html","utf-8",null);


        return view;
	}

}
