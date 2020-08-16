package controllers.favorites;

import java.io.IOException;

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
 * Servlet implementation class FavoritesDestroyServlet
 */
@WebServlet("/favorites/destroy")
public class FavoritesDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer report_id = Integer.parseInt(request.getParameter("report_id"));
        System.out.println("ReportId: " + report_id);

        EntityManager em = DBUtil.createEntityManager();
        Employee e = (Employee) request.getSession().getAttribute("login_employee");

        Favorite f = (Favorite)em.createNamedQuery("getFavorite", Favorite.class).setParameter("employee_id", e.getId()).setParameter("report_id", report_id).getSingleResult();

        em.getTransaction().begin();
        em.remove(f);       // データ削除
        em.getTransaction().commit();
        em.close();


        request.getSession().setAttribute("flush", "お気に入り解除が完了しました。");

        response.sendRedirect(request.getContextPath() + "/reports/show?id=" + report_id);

    }

}
