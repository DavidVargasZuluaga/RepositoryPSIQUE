package edu.entidad;

import javax.annotation.PostConstruct;

/**
 *
 * @author DavidBrootal
 */
public class Correo {
    
    String usuario;
    String correo;
    String contrasena;
    String rutraArchivo;
    String destino;
    String asunto;
    String mensaje;
    
    @PostConstruct
    public void init(){
        usuario = " ";
        correo = " ";
        contrasena = " ";
        rutraArchivo = " ";
        destino = " ";
         asunto = " ";
         mensaje = " ";
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getRutraArchivo() {
        return rutraArchivo;
    }

    public void setRutraArchivo(String rutraArchivo) {
        this.rutraArchivo = rutraArchivo;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    
    
}
