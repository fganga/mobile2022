package modelo;

import java.util.Date;

public class Entrada {
    private String patente,comentario,estado;
    private Date fecha;
    public Entrada(String patente, String comentario, String estado, Date fecha) {
        this.patente = patente;
        this.comentario = comentario;
        this.estado = estado;
        this.fecha = fecha;
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
