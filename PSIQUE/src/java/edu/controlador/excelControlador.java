/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.controlador;

import edu.fachada.*;
import edu.entidad.*;
import javax.inject.Named;
import java.io.File;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 *
 * @author DavidBrootal
 */
@Named(value = "excelControlador")
@SessionScoped
public class excelControlador implements Serializable {

    @Inject
    UsuarioFacade usuarioFacade;
    
    @Inject
    RolFacade rolFacade;

    Workbook archivoExcel;
    Sheet miHoja;

    private int rol;
    private String tipoDocumento;
    private long noDocumento;
    private String correo;
    private String clave;
    private String estado;
    private Date fechaNacimiento;
    private String nombres;
    private String primerApellido;
    private String segundoApellido;
    private String telefono;

    public int cantidadHojas(String url) throws IOException, BiffException {
        archivoExcel = Workbook.getWorkbook(new File(url));
        miHoja = archivoExcel.getSheet(0);
        return archivoExcel.getNumberOfSheets();
    }
    
    public boolean ingresarUsuario(){ 
        
        try {
        Usuario u = new Usuario();
        u.setIdRol(rolFacade.find(rol));
        u.setTipoDocumento(tipoDocumento);
        u.setNoDocumento(noDocumento);
        u.setCorreo(correo);
        u.setClave(clave);
        u.setEstado(estado);
        u.setFechaNacimiento(fechaNacimiento);
        u.setPrimerApellido(primerApellido);
        u.setNombres(nombres);
        u.setSegundoApellido(segundoApellido);
        u.setTelefono(telefono);
       
           usuarioFacade.create(u);
           return true;
       } catch (Exception e) {
           return false;
       }
    }

    public Workbook getArchivoExcel() {
        return archivoExcel;
    }

    public void setArchivoExcel(Workbook archivoExcel) {
        this.archivoExcel = archivoExcel;
    }

    public Sheet getMiHoja() {
        return miHoja;
    }

    public void setMiHoja(Sheet miHoja) {
        this.miHoja = miHoja;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public long getNoDocumento() {
        return noDocumento;
    }

    public void setNoDocumento(long noDocumento) {
        this.noDocumento = noDocumento;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

}
