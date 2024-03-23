package uz.pdp.g34jpahibernate.servlet;

import jakarta.persistence.Query;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import uz.pdp.g34jpahibernate.entity.User;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static uz.pdp.g34jpahibernate.config.ApplicationConfig.MAPPER;
import static uz.pdp.g34jpahibernate.config.DatasourceManager.ENTITY_MANAGER;

@WebServlet(name = "UserServlet", value = "/users")
public class UserServlet extends HttpServlet {

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        long userId = Long.parseLong(req.getParameter("userId"));
        User user = ENTITY_MANAGER.find(User.class, userId);
        String json = MAPPER.writeValueAsString(user);
        resp.setContentType("application/json");
        resp.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        int age = Integer.parseInt(req.getParameter("age"));

        ENTITY_MANAGER.getTransaction().begin();
        User user = User
                .builder()
                .email(email)
                .password(password)
                .age(age)
                .build();
        ENTITY_MANAGER.persist(user);
        ENTITY_MANAGER.getTransaction().commit();
        String json = MAPPER.writeValueAsString(user);
        resp.setContentType("application/json");
        resp.getWriter().write(json);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        Integer age = Integer.parseInt(req.getParameter("age"));

        User user = ENTITY_MANAGER.find(User.class, id);

        if (email != null){
            user.setEmail(email);
        }

        if (password != null){
            user.setPassword(password);
        }

        if (age > 0){
            user.setAge(age);
        }

        ENTITY_MANAGER.getTransaction().begin();
        ENTITY_MANAGER.merge(user);
        ENTITY_MANAGER.getTransaction().commit();

        String json = MAPPER.writeValueAsString(user);
        resp.setContentType("application/json");
        resp.getWriter().write(json);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        ENTITY_MANAGER.getTransaction().begin();
//        Query query = ENTITY_MANAGER.createQuery("DELETE FROM User u WHERE u.id = ?1");
        Query query = ENTITY_MANAGER.createNamedQuery("User.delete");
        query.setParameter(1, id);
        query.executeUpdate();
        ENTITY_MANAGER.getTransaction().commit();
        /*ENTITY_MANAGER.getTransaction().begin();
        User user = ENTITY_MANAGER.find(User.class, id);
        ENTITY_MANAGER.remove(user);
        ENTITY_MANAGER.getTransaction().commit()*/;
    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        List<User> users = ENTITY_MANAGER.createQuery("FROM User", User.class).getResultList();
//        List<User> users = ENTITY_MANAGER.createNativeQuery("SELECT * FROM users", User.class).getResultList();
        List<User> users = ENTITY_MANAGER.createNamedQuery("User.findAll", User.class).getResultList();
        String json = MAPPER.writeValueAsString(users);
        System.out.println("user.size => " + users.size());
        System.out.println(users);
        resp.setContentType("application/json");
        MAPPER.writeValue(resp.getOutputStream(), users);
    }
}
