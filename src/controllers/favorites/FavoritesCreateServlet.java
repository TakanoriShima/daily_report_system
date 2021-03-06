package controllers.favorites;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Favorite;
import utils.DBUtil;

/**
 * Servlet implementation class FavoritesCreateController
 */
@WebServlet("/favorites/create")
public class FavoritesCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;


    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        Integer report_id = Integer.parseInt(request.getParameter("report_id"));
        System.out.println("ReportId: " + report_id);

        EntityManager em = DBUtil.createEntityManager();
        Favorite f = new Favorite();

        Employee e = (Employee) request.getSession().getAttribute("login_employee");

        f.setEmployee_id(e.getId());
        f.setReport_id(report_id);

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        f.setCreated_at(currentTime);
        f.setUpdated_at(currentTime);

        em.getTransaction().begin();

        em.persist(f);

        em.getTransaction().commit();
        em.close();

        request.getSession().setAttribute("flush", "お気に入り登録が完了しました。");

        response.sendRedirect(request.getContextPath() + "/reports/show?id=" + report_id);

    }

}
