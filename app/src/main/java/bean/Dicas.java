package bean;

import android.widget.ImageView;

import java.io.Serializable;

/**
 * Created by Peterson on 12/03/2015.
 */
public class Dicas implements Serializable {
    private Long id;
    private String imagem;

    @Override
    public String toString(){
        return imagem;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}
