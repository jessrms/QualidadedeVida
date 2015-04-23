package appcozinha.com.br.qualidadedevida;


import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import adapter.ListaAlunoAdpter;
import bean.Aluno;
import dao.AlunoDAO;
import helper.FormularioHelper;

import utilitarios.EnviaAlunosTask;
import utilitarios.EnviaQuestTask;

public class ListaAlunosActivity extends Activity {
	//definacao de constante
    public static final String PREFS_DICAS = "Dicas";
    private FormularioHelper helper;
	private final String TAG = "CADASTRO_ALUNO";
    public static final String PREFS_NAME = "Preferences";
    String name;
    String sobrenome;
    String mail;
    public static final String IMC = "ResulatdoImc";
    private ListView listview;
	private ImageView ic;
	private List<Aluno> listaAlunos;
	
	private ListaAlunoAdpter adapter;
	
	private TextView tvInfo;
	private Aluno alunoSelecionado=null;
	private Button btform,btDicas,btQuest,btImc,btLogout,btServ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.listaalunoslayout);
      //  verificalista();
      //  enableRegister(true);
      //  helper=new FormularioHelper();
        //pega as informacoes do usuario
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, 0);
        tvInfo=(TextView)findViewById(R.id.tvInfo);
        name = preferences.getString("PrefNome", "");
        tvInfo.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        tvInfo.setText("Seja Bem-vindo(a): "+(preferences.getString("PrefNome", "")));
        sobrenome = preferences.getString("PrefSobrenome", "");
        mail = preferences.getString("PrefEmail", "");


        //deixa o usuario logado
        SharedPreferences prefs = getSharedPreferences("meu_arquivo_de_preferencias", 0);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean("estaLogado", true);
		editor.commit();
        //abre o formulario de cadastro
		btform=(Button)findViewById(R.id.btLogin);
		listview = (ListView) findViewById(R.id.listView);
		btform.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
                if(adapter.getCount()  == 0 ) {

                Bundle b = new Bundle();
                b.putString("nome", name);
                b.putString("sobrenome", sobrenome);
                b.putString("email", mail);
                Intent valores = new Intent(getApplicationContext(), FormularioActivity.class);
                valores.putExtras(b);
                startActivity(valores);
                    }else{
                      Toast.makeText(ListaAlunosActivity.this,"Usuário já possui Cadastro!",Toast.LENGTH_LONG).show();
                    }
            }
		});
        //abre a tela com o resultado de imc
        btImc=(Button)findViewById(R.id.btImc);
        btImc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter.getCount()  != 0 ) {
                SharedPreferences settings=getSharedPreferences(IMC, 0);

                    Intent telaimc = new Intent(getApplicationContext(), ResultadoImc.class);
                    //   telaimc.putExtras(b);
                    startActivity(telaimc);
                }else{
                    Toast.makeText(ListaAlunosActivity.this,"Preencha seu Cadastro!",Toast.LENGTH_LONG).show();
                }
            }
        });
        //abre a tela de dicas do sesi
		btDicas=(Button)findViewById(R.id.btDicas);
		btDicas.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

                    Intent dicas = new Intent(ListaAlunosActivity.this, MainActivity.class);
                    startActivity(dicas);

			}
		});
        //abre a tela do questionario
		btQuest=(Button)findViewById(R.id.btQuest);
		btQuest.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent quest=new Intent(ListaAlunosActivity.this,Questionario.class);
				startActivity(quest);
			}
		});
        //envia os dados para servidor web
        btServ=(Button)findViewById(R.id.btServ);
        btServ.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(adapter.getCount() != 0) {
                    if (temConexao(ListaAlunosActivity.this)) {
                        //enableRegister(false);
                        new EnviaAlunosTask(ListaAlunosActivity.this).execute();
                        new EnviaQuestTask(ListaAlunosActivity.this).execute();
                    } else {
                        mostraAlerta();
                    }

                }else{
                    Toast.makeText(ListaAlunosActivity.this,"Preencha seu Cadastro!",Toast.LENGTH_LONG).show();
                }
                }
        });
        //tira a opcao de usuario ficar logado retornando para tela de login
        btLogout=(Button)findViewById(R.id.btLogout);
        btLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                SharedPreferences prefs = getSharedPreferences("meu_arquivo_de_preferencias", 0);
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.commit();

                Intent quest=new Intent(ListaAlunosActivity.this,Login.class);
                startActivity(quest);
                finish();
            }
        });
        //ao clicar na imagem abre o link do cozinhabrasil
        ic=(ImageView)findViewById(R.id.ic);
        ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.portaldaindustria.com.br/sesi/canal/canalcozinhabrasil/";

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
		registerForContextMenu(listview);
		//metodo que escuta o evento de click simples
		listview.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> adapter ,View view,int posicao,long id){
				
				Intent form=new Intent(ListaAlunosActivity.this, FormularioActivity.class);
				alunoSelecionado=(Aluno)listview.getItemAtPosition(posicao);
				form.putExtra("ALUNO_SELECIONADO", alunoSelecionado);
				startActivity(form);
				
			}
		});
		//metodo que escuta o evento de click Longo
		listview.setOnItemLongClickListener(new OnItemLongClickListener(){
			@Override
			public boolean onItemLongClick(AdapterView<?>adapter,View view,int posicao,long id){
				alunoSelecionado = (Aluno) adapter.getItemAtPosition(posicao);
				Log.i(TAG, "Aluno selecionado ListView.longClick()"
						+ alunoSelecionado.getNome());
				return false;
			}
		});


    }
	//deixa a lista de usuario carregada
	@Override
	protected void onResume() {
	
		super.onResume();
        this.carregarLista();
	}
	//pega as informacoes no banco e coloca na lista
	private void carregarLista(){
		
		AlunoDAO dao= new AlunoDAO(this);
		this.listaAlunos=dao.listar();
		dao.close();
		
		this.adapter=new ListaAlunoAdpter(this,listaAlunos);
		this.listview.setAdapter(adapter);
	}
	@Override
	public void onCreateContextMenu(ContextMenu menu,View view,ContextMenuInfo menuInfo){
		super.onCreateContextMenu(menu, view, menuInfo);
		getMenuInflater().inflate(R.menu.menu_contexto, menu);
		
	}
    //funcao para deletar usuario
	@Override
	public boolean onContextItemSelected(MenuItem item){
		Intent intent;
		switch(item.getItemId()){
		case R.id.menuDeletar:
			excluirAluno();
			break;
		default:
			break;
			
		}
		return super.onContextItemSelected(item);
	}
	private void excluirAluno(){
		
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setMessage("Confirmar a exclusao de: "+ alunoSelecionado.getNome());
		
		builder.setPositiveButton("Sim", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
				dao.deletar(alunoSelecionado);
				dao.close();
				carregarLista();
				alunoSelecionado = null;

			}
		});
		builder.setNegativeButton("Nao", null);
		AlertDialog dialog=	builder.create();
		dialog.setTitle("Confirmacao de operacao");
		dialog.show();
		
	}
    public void enableRegister(boolean status){

        btform.setEnabled(status);
        btDicas.setEnabled(status);
        btQuest.setEnabled(status);
        btImc.setEnabled(status);
        btLogout.setEnabled(status);
        btServ.setEnabled(status);
        listview.setEnabled(status);


        //btSend.setText(status ? "Enviar" : "Enviando...");
    }
    // verifica se conexao com iternet para enviar dados para servidor
    private boolean temConexao(Context classe) {
        //Pego a conectividade do contexto passado como argumento
        ConnectivityManager gerenciador = (ConnectivityManager) classe.getSystemService(Context.CONNECTIVITY_SERVICE);
        //Crio a variável informacao que recebe as informações da Rede
        NetworkInfo informacao = gerenciador.getActiveNetworkInfo();
        //Se o objeto for nulo ou nao tem conectividade retorna false
        if ((informacao != null) && (informacao.isConnectedOrConnecting()) && (informacao.isAvailable())) {
            return true;
        }
        return false;
    }

    // Mostra a informação caso não tenha internet.
    private void mostraAlerta() {
        AlertDialog.Builder informa = new AlertDialog.Builder(ListaAlunosActivity.this);
        informa.setTitle("Alerta!").setMessage("Sem conexão com a internet.");
        informa.setNeutralButton("Voltar", null).show();

    }
    private void verificalista(){
        if(adapter.getCount() != 0){
            Log.i(TAG,"entrou no if adapter: "+adapter);
            if(mail !=  alunoSelecionado.getEmail() ){
                AlunoDAO dao=new AlunoDAO(this);
                alunoSelecionado=(Aluno)listview.getAdapter();
                Log.i(TAG,"aluno da lista: "+alunoSelecionado);
                dao.deletar(alunoSelecionado);
                dao.close();
            }
        }else{
            Log.i(TAG,"não tem lista");
        }

    }
}
