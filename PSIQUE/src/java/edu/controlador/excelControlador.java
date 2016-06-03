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
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
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

    @EJB
    private PsicologoFacade psicologoFacade;

    @EJB
    private RolFacade rolFacade;

    @EJB
    private UsuarioFacade usuarioFacade;
    private Usuario usuario;

    Workbook archivoExcel;
    Sheet miHoja;

    private int idrol;
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
    private String jornada;

    private String fechatemp;
    private String nombre;

    private int modalCargaPsicologo;

    public excelControlador() {
        usuario = new Usuario();
        modalCargaPsicologo = 0;
    }

    public int cantidadHojas(String url) throws IOException, BiffException {
        archivoExcel = Workbook.getWorkbook(new File(url));
        miHoja = archivoExcel.getSheet(0);
        return archivoExcel.getNumberOfSheets();
    }

    public boolean ingresarPsicologo() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        boolean existe = true;
        try {

            usuario.setNombres(nombres);
            usuario.setPrimerApellido(primerApellido);
            usuario.setSegundoApellido(segundoApellido);
            usuario.setIdRol(rolFacade.find(2));
            usuario.setTipoDocumento(tipoDocumento);
            usuario.setNoDocumento(noDocumento);
            usuario.setCorreo(correo);
            usuario.setClave(clave);
            usuario.setEstado("ACTIVO");
            usuario.setTelefono(telefono);

            //  String fecha = (String) (fechaNacimiento.toString());
            //   usuario.setFechaNacimiento((Date) format.parse(fecha));
            for (int i = 0; i < usuarioFacade.findAll().size(); i++) {
                if (usuarioFacade.findAll().get(i).getNoDocumento() == usuario.getNoDocumento()) {
                    existe = false;
                    modalCargaPsicologo = 2;
                    break;
                }
            }
            if (existe) {
                usuarioFacade.create(usuario);

                Psicologo psicologo = new Psicologo();
                psicologo.setIdPsicologo(usuario.getIdUsuario());
                psicologo.setJornada(jornada);
                psicologoFacade.create(psicologo);
                System.out.println("usuario creado");
                modalCargaPsicologo = 1;
            } else {
            }

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

    public String getJornada() {
        return jornada;
    }

    public void setJornada(String jornada) {
        this.jornada = jornada;
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

    public String getFechatemp() {
        return fechatemp;
    }

    public void setFechatemp(String fechatemp) {
        this.fechatemp = fechatemp;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
