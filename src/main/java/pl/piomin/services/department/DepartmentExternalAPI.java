package pl.piomin.services.department;

public interface DepartmentExternalAPI {

    DepartmentDTO getDepartmentByIdWithEmployees(Long id);
    DepartmentDTO add(DepartmentDTO department);
}
