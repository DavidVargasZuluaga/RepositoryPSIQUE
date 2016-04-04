/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.entidad;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author DavidBrootal
 */
@Entity
@Table(name = "programaformacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Programaformacion.findAll", query = "SELECT p FROM Programaformacion p"),
    @NamedQuery(name = "Programaformacion.findByIdPrograma", query = "SELECT p FROM Programaformacion p WHERE p.idPrograma = :idPrograma"),
    @NamedQuery(name = "Programaformacion.findByNombrePrograma", query = "SELECT p FROM Programaformacion p WHERE p.nombrePrograma = :nombrePrograma")})
public class Programaformacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPrograma")
    private Integer idPrograma;
    @Size(max = 30)
    @Column(name = "nombrePrograma")
    private String nombrePrograma;
    @OneToMany(mappedBy = "idPrograma")
    private List<Coordinador> coordinadorList;
    @OneToMany(mappedBy = "idPrograma")
    private List<Ficha> fichaList;

    public Programaformacion() {
    }

    public Programaformacion(Integer idPrograma) {
        this.idPrograma = idPrograma;
    }

    public Integer getIdPrograma() {
        return idPrograma;
    }

    public void setIdPrograma(Integer idPrograma) {
        this.idPrograma = idPrograma;
    }

    public String getNombrePrograma() {
        return nombrePrograma;
    }

    public void setNombrePrograma(String nombrePrograma) {
        this.nombrePrograma = nombrePrograma;
    }

    @XmlTransient
    public List<Coordinador> getCoordinadorList() {
        return coordinadorList;
    }

    public void setCoordinadorList(List<Coordinador> coordinadorList) {
        this.coordinadorList = coordinadorList;
    }

    @XmlTransient
    public List<Ficha> getFichaList() {
        return fichaList;
    }

    public void setFichaList(List<Ficha> fichaList) {
        this.fichaList = fichaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrograma != null ? idPrograma.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Programaformacion)) {
            return false;
        }
        Programaformacion other = (Programaformacion) object;
        if ((this.idPrograma == null && other.idPrograma != null) || (this.idPrograma != null && !this.idPrograma.equals(other.idPrograma))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.entidad.Programaformacion[ idPrograma=" + idPrograma + " ]";
    }
    
}
