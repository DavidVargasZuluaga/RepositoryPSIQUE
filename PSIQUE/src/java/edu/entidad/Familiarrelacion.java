/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.entidad;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DavidBrootal
 */
@Entity
@Table(name = "familiarrelacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Familiarrelacion.findAll", query = "SELECT f FROM Familiarrelacion f"),
    @NamedQuery(name = "Familiarrelacion.findByParentezco", query = "SELECT f FROM Familiarrelacion f WHERE f.parentezco = :parentezco"),
    @NamedQuery(name = "Familiarrelacion.findByIdRelacion", query = "SELECT f FROM Familiarrelacion f WHERE f.idRelacion = :idRelacion")})
public class Familiarrelacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 25)
    @Column(name = "parentezco")
    private String parentezco;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idRelacion")
    private Integer idRelacion;
    @JoinColumn(name = "idFamiliarParentezco", referencedColumnName = "idFamiliar")
    @ManyToOne
    private Familiar idFamiliarParentezco;
    @JoinColumn(name = "idFamiliarRelacion", referencedColumnName = "idFamiliar")
    @ManyToOne
    private Familiar idFamiliarRelacion;

    public Familiarrelacion() {
    }

    public Familiarrelacion(Integer idRelacion) {
        this.idRelacion = idRelacion;
    }

    public String getParentezco() {
        return parentezco;
    }

    public void setParentezco(String parentezco) {
        this.parentezco = parentezco;
    }

    public Integer getIdRelacion() {
        return idRelacion;
    }

    public void setIdRelacion(Integer idRelacion) {
        this.idRelacion = idRelacion;
    }

    public Familiar getIdFamiliarParentezco() {
        return idFamiliarParentezco;
    }

    public void setIdFamiliarParentezco(Familiar idFamiliarParentezco) {
        this.idFamiliarParentezco = idFamiliarParentezco;
    }

    public Familiar getIdFamiliarRelacion() {
        return idFamiliarRelacion;
    }

    public void setIdFamiliarRelacion(Familiar idFamiliarRelacion) {
        this.idFamiliarRelacion = idFamiliarRelacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRelacion != null ? idRelacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Familiarrelacion)) {
            return false;
        }
        Familiarrelacion other = (Familiarrelacion) object;
        if ((this.idRelacion == null && other.idRelacion != null) || (this.idRelacion != null && !this.idRelacion.equals(other.idRelacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.entidad.Familiarrelacion[ idRelacion=" + idRelacion + " ]";
    }
    
}
