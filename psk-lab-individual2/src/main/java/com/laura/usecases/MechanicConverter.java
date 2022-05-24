package com.laura.usecases;

import com.laura.entities.Mechanic;
import com.laura.persistence.MechanicsDAO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;

@ManagedBean
@RequestScoped
public class MechanicConverter implements Converter {

    @Inject
    private MechanicsDAO mechanicsDAO;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String submittedValue) {
        if (submittedValue == null || submittedValue.isEmpty()) {
            return null;
        }
        return mechanicsDAO.findByName(submittedValue);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (o == null) {
            return "";
        }
        if (o instanceof Mechanic) {
            return ((Mechanic) o).getName();
        }
        return "";
    }
}
