package bean;

import java.io.Serializable;

/**
 * Created by Peterson on 11/03/2015.
 */
public class Empresas implements Serializable{
    private Long id;
    private String empresa;

    @Override
    public String toString(){
        return empresa;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }
}
