package controllers.reports;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Report;
import models.validators.ReportValidator;
import utils.DBUtil;

/**
 * Servlet implementation class ReportRetryCreate
 */
@WebServlet("/reports/retry_create")
public class ReportRetryCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportRetryCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            Report r = new Report();

            //レポート作成者のidを取得
            r.setEmployee((Employee)request.getSession().getAttribute("login_employee"));

//            Integer approval_id = Integer.parseInt(request.getParameter("approval_id"));
//            //上記で取得したidのレポートを取得
//            Approval a = em.find(Approval.class, approval_id);
//            r.setApproval(a);

            Date report_date = new Date(System.currentTimeMillis());
            String rd_str = request.getParameter("report_date");
            //日付欄が未入力の場合、当日の日付を入れる
            if(rd_str != null && !rd_str.equals("")) {
                //Stringで受け取った日付をDate型へ変換
                report_date = Date.valueOf(request.getParameter("report_date"));
            }
            r.setReport_date(report_date);

            Employee admin = em.find(Employee.class, Integer.parseInt(request.getParameter("admin")));
            r.setAdmin(admin);
            r.setTitle(request.getParameter("title"));
            r.setContent(request.getParameter("content"));
//            Integer approval_id = Integer.parseInt(request.getParameter("approval_id"));
//            r.setApproval_id(request.getParameter("approval_id"));
//          上記で取得したidのレポートを取得

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            r.setCreated_at(currentTime);
            r.setUpdated_at(currentTime);

            List<String> errors = ReportValidator.validate(r);
            if(errors.size() > 0) {
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("report", r);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/new.jsp");
                rd.forward(request, response);
            } else {
                em.getTransaction().begin();
                System.out.println("!!!");
                em.persist(r);
                System.out.println("aaa");
                em.getTransaction().commit();
                em.close();
                request.getSession().setAttribute("flush", "登録が完了しました。");

                response.sendRedirect(request.getContextPath() + "/reports/index");
            }
        }
    }

}
