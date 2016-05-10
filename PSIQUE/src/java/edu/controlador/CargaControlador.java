/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.controlador;

import edu.entidad.*;
import edu.fachada.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.swing.ImageIcon;
import jxl.Image;
import org.apache.commons.io.FileUtils;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author DavidBrootal
 */
@Named(value = "cargaControlador")
@SessionScoped
public class CargaControlador implements Serializable {

    @Inject
    private UsuarioFacade usuarioFacade;

    private UploadedFile file;

    public CargaControlador() {
    }
    
    public Image mostrarFotoPerfil(Usuario u){
        Image img = null ;
        try {
            img = (Image) new ImageIcon(u.getFoto()).getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return img;
    }

    public void upload(Usuario u) {
        try {
            if (file != null) {
                u.setFoto(file.getContents());
                usuarioFacade.edit(u);
                FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
//    public StreamedContent fotoPerfil(Usuario u){
//        
//    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

}
