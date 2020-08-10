package controllers.approval;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Approval;
import utils.DBUtil;

/**
 * Servlet implementation class ApprovalUpdateServlet
 */
@WebServlet("/approval/update")
public class ApprovalUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApprovalUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String _token = (String) request.getParameter("_token");
        if (_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            Integer approval_id = Integer.parseInt(request.getParameter("approval_id"));

            //            Approvalのインスタンス生成
            Approval a = em.find(Approval.class, approval_id);

            //            パラメータを取得してプロパティに代入（引数がString以外の時はapproval_statusのような処理が必要になる）
            a.setApproval_status(Integer.parseInt(request.getParameter("approval_status")));
            a.setComment(request.getParameter("comment"));
            a.setUpdated_at(new Timestamp(System.currentTimeMillis()));

            em.getTransaction().begin();
            em.getTransaction().commit();
            em.close();
            request.getSession().setAttribute("flush", "更新が完了しました。");

            request.getSession().removeAttribute("approval_id");

            response.sendRedirect(request.getContextPath() + "/approval/index");
        }

    }

}
