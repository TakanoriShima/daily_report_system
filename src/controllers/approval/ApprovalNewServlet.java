package controllers.approval;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ApprovalNewServlet
 */
@WebServlet("/approval/new")
public class ApprovalNewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApprovalNewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();
        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));
        em.close();

        //	    Approvalのインスタンスを生成してリクエストスコープに格納
        request.setAttribute("report", r);
        request.setAttribute("_token", request.getSession().getId());

        //        /approval/newサーブレットからnew.jspを呼び出す
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/approval/new.jsp");
        rd.forward(request, response);
    }

}
