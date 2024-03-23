package uz.pdp.g34jpahibernate.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uz.pdp.g34jpahibernate.entity.Address;
import uz.pdp.g34jpahibernate.entity.Employee;

import java.io.IOException;

import static uz.pdp.g34jpahibernate.config.ApplicationConfig.MAPPER;
import static uz.pdp.g34jpahibernate.config.DatasourceManager.ENTITY_MANAGER;

@WebServlet(name = "EmployeeServlet", value = "/employees")
public class EmployeeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Employee employee = Employee.builder()
                .firstName("Anna")
                .lastName("Dark")
                .position("accountant")
                .salary(1200D)
                .build();

        Address address = Address.builder()
                .country("England")
                .city("London")
                .postalCode("123412123")
                .build();

        employee.setAddress(address);
        address.setEmployee(employee);

        ENTITY_MANAGER.getTransaction().begin();
        ENTITY_MANAGER.persist(employee);
        ENTITY_MANAGER.getTransaction().commit();

        /*ENTITY_MANAGER.getTransaction().begin();
        ENTITY_MANAGER.persist(address);
        employee.setAddress(address);
        ENTITY_MANAGER.persist(employee);
        ENTITY_MANAGER.getTransaction().commit();*/
        System.out.println(employee);
//        String json = MAPPER.writeValueAsString(employee);
//        resp.setContentType("application/json");
//        resp.getWriter().write(json);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String employeeId = req.getParameter("employee_id");
        String addressId = req.getParameter("address_id");

        if (employeeId != null) {
            Employee employee = ENTITY_MANAGER.find(Employee.class, Long.parseLong(employeeId));
            String json = MAPPER.writeValueAsString(employee);
            resp.setContentType("application/json");
            resp.getWriter().write(json);
        }else if (addressId != null){
            Address address = ENTITY_MANAGER.find(Address.class, Long.parseLong(addressId));
            System.out.println("address => " + address);
            System.out.println("address.employee => " + address.getEmployee());
//            String json = MAPPER.writeValueAsString(address);
            /*resp.setContentType("application/json");
            resp.getWriter().write(json);*/
        }
    }
}
