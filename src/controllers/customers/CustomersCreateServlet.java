package controllers.customers;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Customer;
import models.Employee;
import utils.DBUtil;

/**
 * Servlet implementation class CustomersCreateServlet
 */
@WebServlet("/customers/create")
public class CustomersCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomersCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        EntityManager em = DBUtil.createEntityManager();

        Customer c = new Customer();

        //レポート作成者のidを取得
        c.setEmployee((Employee) request.getSession().getAttribute("login_employee"));

        c.setName(request.getParameter("name"));

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        c.setCreated_at(currentTime);
        c.setUpdated_at(currentTime);

        em.getTransaction().begin();

        em.persist(c);

        em.getTransaction().commit();
        em.close();
        request.getSession().setAttribute("flush", "顧客登録が完了しました。");

        response.sendRedirect(request.getContextPath() + "/customers/index");
    }
}
