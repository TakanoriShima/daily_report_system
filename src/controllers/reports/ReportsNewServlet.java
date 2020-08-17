package controllers.reports;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Customer;
import models.Employee;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsNewServlet
 */
@WebServlet("/reports/new")
public class ReportsNewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsNewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("_token", request.getSession().getId());
        EntityManager em = DBUtil.createEntityManager();
        Report r = new Report();
        r.setReport_date(new Date(System.currentTimeMillis()));
        Employee e = (Employee)request.getSession().getAttribute("login_employee");
        List<Employee> adminList = em.createNamedQuery("getAllAdminsExceptMe", Employee.class).setParameter("admin_id", e.getId()).getResultList();
        request.setAttribute("adminList", adminList);

        request.setAttribute("report", r);
        request.setAttribute("adminList", adminList);

        List<Customer> myCustomerList = em.createNamedQuery("getMyAllCustomers", Customer.class).setParameter("employee", e).getResultList();
        request.setAttribute("myCustomerList", myCustomerList);



        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/new.jsp");
        rd.forward(request, response);
    }

}
