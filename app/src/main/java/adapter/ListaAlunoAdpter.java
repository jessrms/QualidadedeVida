package adapter;

import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import appcozinha.com.br.qualidadedevida.R;
import bean.Aluno;
//lista de usuarios
public class ListaAlunoAdpter extends BaseAdapter {
	
	private final List<Aluno> listaAlunos;
	private final Activity activity;
	
	public ListaAlunoAdpter(Activity activity, List<Aluno> listaAlunos) {
		
		this.listaAlunos=listaAlunos;
		this.activity=activity;
	}
	
	@Override
	public int getCount() {
		
		return listaAlunos.size();
	}

	@Override
	public Object getItem(int posicao) {
		// TODO Auto-generated method stub
		return listaAlunos.get(posicao);
	}

	@Override
	public long getItemId(int posicao) {
		// TODO Auto-generated method stub
		return listaAlunos.get(posicao).getId();
	}

	@Override
	public View getView(int posicao, View convertView, ViewGroup parent) {
		
		View view = activity.getLayoutInflater().inflate(R.layout.item , null);
		Aluno aluno =listaAlunos.get(posicao);
		
			
			view.setBackgroundColor(activity.getResources().getColor(R.color.linha_par));
		//}else{
			//view.setBackgroundColor(activity.getResources().getColor(R.color.linha_impar));
		//}
		
		TextView nome=(TextView)view.findViewById(R.id.itemNome);
		nome.setText(aluno.getNome() +" "+ aluno.getSobrenome());
		TextView sobrenome=(TextView)view.findViewById(R.id.itemSobrenome);
		
		sobrenome.setText(aluno.getSobrenome());
	
		TextView email=(TextView)view.findViewById(R.id.itemEmail);
	
		email.setText(aluno.getEmail());

		
		Bitmap bmp;
		//if(aluno.getFoto() != null){
			//bmp=BitmapFactory.decodeFile(aluno.getFoto());
			
		//}else{
			bmp=BitmapFactory.decodeResource(activity.getResources(),R.drawable.ic_launcher);
		//}
		bmp=Bitmap.createScaledBitmap(bmp, 100, 100, true);
		ImageView foto=(ImageView) view.findViewById(R.id.itemFoto);
		foto.setImageBitmap(bmp);
		
				
		return view;
	}
	

}
	