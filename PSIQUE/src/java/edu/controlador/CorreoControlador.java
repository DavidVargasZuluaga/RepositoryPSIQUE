package edu.controlador;

import edu.entidad.Correo;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author DavidBrootal
 */

public class CorreoControlador {
    

    public boolean enviarCorreo(Correo correo){
        try {
            Properties p = new Properties();
            p.put("mail.smtp.host","setp.gmail.com");
            p.setProperty("mail.smtp.staristtls.enable","true");
            p.setProperty("mail.smtp.sport","587");
            p.setProperty("mail.smtp.user", correo.getUsuario() );
            p.setProperty("mail.smtp.auth","true");
            
            Session s = Session.getDefaultInstance(p,null);
            BodyPart texto = new MimeBodyPart();
            texto.setText(correo.getMensaje());
            BodyPart adjunto = new MimeBodyPart();
            if(!correo.getRutraArchivo().equals("")){
                adjunto.setDataHandler(new DataHandler(new FileDataSource(correo.getRutraArchivo())));
            }
            MimeMultipart m = new MimeMultipart();
            m.addBodyPart(texto);
            if(!correo.getRutraArchivo().equals("")){
                m.addBodyPart(adjunto);
            }
            MimeMessage mensaje = new MimeMessage(s);
            mensaje.setFrom(new InternetAddress(correo.getUsuario()));
            mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(correo.getDestino()));
            mensaje.setSubject(correo.getAsunto());
            mensaje.setContent(m);
           
            Transport t = s.getTransport("smtp");
            t.connect(correo.getUsuario(),correo.getContrasena());
            t.sendMessage(mensaje, mensaje.getAllRecipients());
            t.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    
    
}
