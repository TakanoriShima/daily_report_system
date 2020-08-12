package controllers.reports;

import java.io.File;
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

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import models.Employee;
import models.Report;
import models.validators.ReportValidator;
import utils.DBUtil;
import utils.EncryptUtil;

/**
 * Servlet implementation class ReportsCreateServlet

 */
@MultipartConfig
@WebServlet("/reports/create")
public class ReportsCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("POST");
        String _token = (String) request.getParameter("_token");
        if (_token != null && _token.equals(request.getSession().getId())) {

            // 画像アップロード
            Part part = request.getPart("image");
            String filename = getFileName(part);

            String filePath = getServletContext().getRealPath("/uploads/") + filename;
            System.out.println(filePath);

            File uploadDir = new File(getServletContext().getRealPath("/uploads/"));
            if (!uploadDir.exists()) uploadDir.mkdir();

            part.write(filePath);

            /* S3 */
            final String region = "us-east-1";
            final String awsAccessKey = "AKIASNU7DZ6PTNGHCBYL";
            final String awsSecretKey = "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";
            final String bucketName = "quark2galaxy2quark";



            // 認証情報を用意
            AWSCredentials credentials = new BasicAWSCredentials(
                // アクセスキー
                    awsAccessKey,
                // シークレットキー
                    awsSecretKey
            );

            // クライアントを生成
            AmazonS3 s3 = AmazonS3ClientBuilder
                .standard()
                // 認証情報を設定
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                // リージョンを AP_NORTHEAST_1(東京) に設定
                .withRegion(region)
                .build();

         // === ファイルから直接アップロードする場合 ===
         // アップロードするファイル
         File file = new File(filePath);
         // ファイルをアップロード
         s3.putObject(
                 // アップロード先バケット名
                 bucketName,
                 // アップロード後のキー名
                 "tmp/" + filename,
                 // ファイルの実体
                 file
         );


//            final long threshold = 5L * 1024L * 1024L;
//            final TransferManager transferManager = TransferManagerBuilder.standard()
//                                     .withS3Client(s3)
//                                     .withMultipartUploadThreshold(threshold)
//                                     .build();
//
//            final Upload upload = transferManager.upload(bucketName, uploadFile.getName(), uploadFile);
//            try {
//                upload.waitForCompletion();
//            } catch (AmazonServiceException e) {
//                // TODO 自動生成された catch ブロック
//                e.printStackTrace();
//            } catch (AmazonClientException e) {
//                // TODO 自動生成された catch ブロック
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                // TODO 自動生成された catch ブロック
//                e.printStackTrace();
//            }
//
//
//            // 6. 転送マネージャーを終了させる
//            transferManager.shutdownNow();
            /* end S3 */

            /* for local */

            //            String filePath = getServletContext().getRealPath("/uploads/") + filename;
            //            System.out.println(filePath);
            //            part.write(filePath);

            System.out.println("画像アップロード完了");

            EntityManager em = DBUtil.createEntityManager();

            Report r = new Report();

            //レポート作成者のidを取得
            r.setEmployee((Employee) request.getSession().getAttribute("login_employee"));

            Date report_date = new Date(System.currentTimeMillis());
            String rd_str = request.getParameter("report_date");
            //日付欄が未入力の場合、当日の日付を入れる
            if (rd_str != null && !rd_str.equals("")) {
                //Stringで受け取った日付をDate型へ変換
                report_date = Date.valueOf(request.getParameter("report_date"));
            }
            r.setReport_date(report_date);

            r.setTitle(request.getParameter("title"));
            r.setContent(request.getParameter("content"));
            //            Integer approval_id = Integer.parseInt(request.getParameter("approval_id"));
            //            r.setApproval_id(request.getParameter("approval_id"));
            //          上記で取得したidのレポートを取得

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            r.setCreated_at(currentTime);
            r.setUpdated_at(currentTime);

            r.setImage(filename);

            Employee admin = em.find(Employee.class, Integer.parseInt(request.getParameter("admin")));
            r.setAdmin(admin);
            Employee e = (Employee)request.getSession().getAttribute("login_employee");
            List<Employee> adminList = em.createNamedQuery("getAllAdminsExceptMe", Employee.class).setParameter("admin_id", e.getId()).getResultList();
            request.setAttribute("adminList", adminList);


            List<String> errors = ReportValidator.validate(r);
            if (errors.size() > 0) {
                em.close();
                request.setAttribute("adminList", adminList);
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
                (String) this.getServletContext().getAttribute("salt"));

        fileName = randName + "." + (fileName.substring(size - cut_length));

        return fileName;
    }

}