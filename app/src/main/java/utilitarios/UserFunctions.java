package utilitarios;


import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import android.content.Context;


public class UserFunctions {

    private JSONParser jsonParser;

    //URL para a  API PHP 
    private static String loginURL = "http://api.learn2crack.com/android/loginapi/";
    private static String registerURL = "http://api.learn2crack.com/android/loginapi/";
    private static String forpassURL = "http://api.learn2crack.com/android/loginapi/";
    private static String chgpassURL = "http://api.learn2crack.com/android/loginapi/";
    private static String empresaURL = "hhttp://dontpad.com/cozinhabr";

    private static String login_tag = "login";
    private static String register_tag = "register";
    private static String forpass_tag = "forpass";
    private static String chgpass_tag = "chgpass";
    private static String empresa_tag = "empresa";


    // construtor
    public UserFunctions(){
        jsonParser = new JSONParser();
    }

    /**
     * Funcao do Login
     **/

    public JSONObject loginUser(String email, String password){
        //  construindo Par�metros
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", login_tag));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
        JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
        return json;
    }

    /**
     * Funcao para mudar senha
     **/

    public JSONObject chgPass(String newpas, String email){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", chgpass_tag));

        params.add(new BasicNameValuePair("newpas", newpas));
        params.add(new BasicNameValuePair("email", email));
        JSONObject json = jsonParser.getJSONFromUrl(chgpassURL, params);
        return json;
    }





    /**
     * Funcao para resetar password
     **/

    public JSONObject forPass(String forgotpassword){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", forpass_tag));
        params.add(new BasicNameValuePair("forgotpassword", forgotpassword));
        JSONObject json = jsonParser.getJSONFromUrl(forpassURL, params);
        return json;
    }






     /**
      * Funcao para cadastrar
      **/
    public JSONObject registerUser(String fname, String lname, String email, String uname, String password){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", register_tag));
        params.add(new BasicNameValuePair("fname", fname));
        params.add(new BasicNameValuePair("lname", lname));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("uname", uname));
        params.add(new BasicNameValuePair("password", password));
        JSONObject json = jsonParser.getJSONFromUrl(registerURL,params);
        return json;
    }
   /* public JSONObject empresas(String empresas, String lname, String email, String uname, String password){
        // Building Parameters
        List<String> params = new ArrayList<String>();

        params.add(("tag", empresa_tag));
        params.add(new BasicNameValuePair("fname", fname));
        params.add(new BasicNameValuePair("lname", lname));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("uname", uname));
        params.add(new BasicNameValuePair("password", password));
        JSONObject json = jsonParser.getJSONFromUrl(empresaURL,params);
        return json;
    }
*/

    /**
     * Funcao para logout usuario
     * Resetar os dados tempor�rios armazenados em Banco de dados de SQLite
     * */
    public boolean logoutUser(Context context){
     //   DatabaseHandler db = new DatabaseHandler(context);
       // db.resetTables();
        return true;
    }

}

