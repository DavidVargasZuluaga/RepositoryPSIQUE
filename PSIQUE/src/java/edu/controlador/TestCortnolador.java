/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.controlador;

import edu.entidad.*;
import edu.fachada.*;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.inject.Inject;

/**
 *
 * @author DavidBrootal
 */
@Named(value = "testCortnolador")
@SessionScoped
public class TestCortnolador implements Serializable {

    @Inject
    TestFacade testFacade;
    
    Test testLog;
    
    public TestCortnolador() {
        testLog = new Test() ; //Brutal es gay
    }
    
    
    public void aplicarTest (){
        
    }
    
}
