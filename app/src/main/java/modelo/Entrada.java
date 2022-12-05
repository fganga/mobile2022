package modelo;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.Date;

public class Entrada implements Serializable {


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    // we are using exclude because
    // we are not saving our id
    @Exclude
    private String id;



    private String patente,comentario,estado;
    private Date fecha;
    public Entrada(String patente, String comentario, String estado, Date fecha) {
        this.patente = patente;
        this.comentario = comentario;
        this.estado = estado;
        this.fecha = fecha;
    }
    public Entrada(){

    }
    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }







}
