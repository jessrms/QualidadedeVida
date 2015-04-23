package adapter;

import java.util.List;

import org.json.JSONException;
import org.json.JSONStringer;

import android.util.Log;

import bean.Aluno;
//converte os dados em json
public class AlunoConverter {
	public String toJSON(List<Aluno> lisAlunos){
		try{
			JSONStringer jsonStringer=new JSONStringer();
			
			jsonStringer.object().key("list").array().object().key("usuario").array();
			for(Aluno aluno : lisAlunos){
				
				jsonStringer.object()
					.key("id").value(aluno.getId())
					.key("nome").value(aluno.getNome())
					.key("sobrenome").value(aluno.getSobrenome())
					.key("email").value(aluno.getEmail())
					.key("sexo").value(aluno.getSexo())
					.key("empresa").value(aluno.getEmpresa())
					.key("nit").value(aluno.getNit())
					.key("datanascimento").value(aluno.getDatanascimento())
					.key("altura").value(aluno.getAltura())
					.key("peso").value(aluno.getPeso())
					.key("imc").value(aluno.getImc())
					.endObject();
			}
			jsonStringer.endArray().endObject().endArray().endObject();
			Log.i("CADASTRO_ALUNO", jsonStringer.toString());
			return jsonStringer.toString();
			
		}catch(JSONException e){
			Log.i("CADASTRO_ALUNO", e.getMessage());
			return null;
		}
	}
}
