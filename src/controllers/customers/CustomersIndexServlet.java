package controllers.customers;

import java.io.IOException;
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
import utils.DBUtil;

/**
 * Servlet implementation class CustomersIndexServlet
 */
@WebServlet("/customers/index")
public class CustomersIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomersIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // TODO Auto-generated method stub

        EntityManager em = DBUtil.createEntityManager();

        Employee e = (Employee) request.getSession().getAttribute("login_employee");

        List<Customer> myAllCustomerList = em.createNamedQuery("getMyAllCustomers", Customer.class)
                .setParameter("employee", e).getResultList();
        request.setAttribute("myAllCustomerList", myAllCustomerList);
        em.close();
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/customers/index.jsp");
        rd.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
