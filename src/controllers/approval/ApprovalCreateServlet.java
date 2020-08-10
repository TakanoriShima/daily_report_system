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
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ApprovalCreateServlet
 */
@WebServlet("/approval/create")
public class ApprovalCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApprovalCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String _token = (String) request.getParameter("_token");
        //        CSRF対策（_token）に値がセットされていなかったりセッションIDと値が異なっていたらデータの登録ができない）
        if (_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            //            Approvalのインスタンス生成
            Approval a = new Approval();


            //            パラメータを取得してプロパティに代入（引数がString以外の時はapproval_statusのような処理が必要になる）
            a.setApproval_status(Integer.parseInt(request.getParameter("approval_status")));
            Integer report_id = Integer.parseInt(request.getParameter("report_id"));

            //            上記で取得したidのレポートを取得
            Report r = em.find(Report.class, report_id);

            r.setApproval(a);
            a.setApproval_flag(1);
            a.setComment(request.getParameter("comment"));

            //            現在日時を取得してプロパティにセットする
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            a.setCreated_at(currentTime);
            a.setUpdated_at(currentTime);

            //データベースに保存
            em.getTransaction().begin();
            em.persist(a);
            em.getTransaction().commit();
            em.close();
            request.getSession().setAttribute("flush", "承認ステータスが登録されました。");

            response.sendRedirect(request.getContextPath() + "/approval/index");
        }
    }
}
