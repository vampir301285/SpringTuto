package com.minhduc.primefaces.lazyloading;

import org.primefaces.model.LazyDataModel;

import com.minhduc.primefaces.lazyloading.dao.DataService;
import com.minhduc.primefaces.lazyloading.enity.Employee;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class EmployeeController {

    EmployeeLazyDataModel dataModel = new EmployeeLazyDataModel(DataService.INSTANCE);

    public LazyDataModel<Employee> getModel() {
        return dataModel;
    }
}