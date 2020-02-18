package com.minhduc.primefaces.lazyloading;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.minhduc.primefaces.lazyloading.dao.DataService;
import com.minhduc.primefaces.lazyloading.enity.Employee;

import java.util.List;
import java.util.Map;

public class EmployeeLazyDataModel extends LazyDataModel<Employee> {

    private DataService dataService;

    /**
     * 
     */
    private static final long serialVersionUID = 8731790623648903997L;

    public EmployeeLazyDataModel(DataService dataService) {
        this.dataService = dataService;
        this.setRowCount(dataService.getEmployeeTotalCount());

    }

    @Override
    public List<Employee> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        List<Employee> list = dataService.getEmployeeList(first, pageSize, sortField, sortOrder, filters);
        if (filters != null && filters.size() > 0) {
            // otherwise it will still show all page links; pages at end will be empty
            this.setRowCount(dataService.getFilteredRowCount(filters));
        } else {
            this.setRowCount(dataService.getEmployeeTotalCount());
        }
        return list;
    }
}