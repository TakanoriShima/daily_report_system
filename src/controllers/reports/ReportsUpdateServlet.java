package controllers.reports;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import models.Employee;
import models.Report;
import models.validators.ReportValidator;
import utils.DBUtil;
import utils.EncryptUtil;

/**
 * Servlet implementation class ReportsUpdateServlet
 */
@MultipartConfig
@WebServlet("/reports/update")
public class ReportsUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsUpdateServlet() {
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

            // 画像アップロード
            Part part = request.getPart("image");
            String filename = getFileName(part);
            String filePath = getServletContext().getRealPath("/uploads/") + filename;
            System.out.println(filePath);

            part.write(filePath);

            System.out.println("画像アップロード完了");

            EntityManager em = DBUtil.createEntityManager();

            Report r = em.find(Report.class, (Integer) (request.getSession().getAttribute("report_id")));
            Date report_date = new Date(System.currentTimeMillis());
            String rd_str = request.getParameter("report_date");
            //日付欄が未入力の場合、当日の日付を入れる
            if(rd_str != null && !rd_str.equals("")) {
                //Stringで受け取った日付をDate型へ変換
                report_date = Date.valueOf(request.getParameter("report_date"));
            }
            r.setReport_date(report_date);
            r.setTitle(request.getParameter("title"));
            r.setContent(request.getParameter("content"));
            r.setUpdated_at(new Timestamp(System.currentTimeMillis()));
            r.setImage(filename);

            Employee admin = em.find(Employee.class, Integer.parseInt(request.getParameter("admin")));
            r.setAdmin(admin);

            List<String> errors = ReportValidator.validate(r);
            if (errors.size() > 0) {
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("report", r);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/edit.jsp");
                rd.forward(request, response);
            } else {
                em.getTransaction().begin();
                em.getTransaction().commit();
                em.close();
                request.getSession().setAttribute("flush", "更新が完了しました。");

                request.getSession().removeAttribute("report_id");

                response.sendRedirect(request.getContextPath() + "/reports/index");
            }
        }
    }

    private String getFileName(Part part) {
        String[] headerArrays = part.getHeader("Content-Disposition").split(";");
        String fileName = null;
        for (String head : headerArrays) {
            if (head.trim().startsWith("filename")) {
                fileName = head.substring(head.indexOf('"')).replaceAll("\"", "");
            }
        }

        int size = fileName.length();
        int cut_length = 3;
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        String randName = EncryptUtil.getPasswordEncrypt(
                currentTime.toString(),
                (String)this.getServletContext().getAttribute("salt")
        );


        fileName = randName + "." + (fileName.substring(size - cut_length));

        return fileName;
    }

}