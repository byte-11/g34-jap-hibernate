package uz.pdp.g34jpahibernate.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uz.pdp.g34jpahibernate.entity.User;

import java.io.IOException;

import static uz.pdp.g34jpahibernate.config.DatasourceManager.ENTITY_MANAGER;

@WebServlet(name = "SimpleServlet", value = "/simple")
public class SimpleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ENTITY_MANAGER.getTransaction().begin();
        User user = User
                .builder()
                .email("soome@email.com")
                .password("****")
                .age(20)
                .build();
        ENTITY_MANAGER.persist(user);
        ENTITY_MANAGER.getTransaction().commit();
    }
}
