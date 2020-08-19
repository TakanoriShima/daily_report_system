package models.validators;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import models.Employee;
import models.Report;

public class ReportValidator {

    public static List<String> validate(Report r) {
        List<String> errors = new ArrayList<String>();


        String title_error = _validateTitle(r.getTitle());
        if (!title_error.equals("")) {
            errors.add(title_error);
        }

        String content_error = _validateContent(r.getContent());
        if (!content_error.equals("")) {
            errors.add(content_error);
        }

        String admin_error = _validateAdmin(r.getAdmin());
        if (!admin_error.equals("")) {
            errors.add(admin_error);
        }

        String start_time_error = _validateStartTime(r.getStart_time());
        if (!start_time_error.equals("") ) {
            errors.add(start_time_error);
        }

        String end_time_error = _validateEndTime(r.getEnd_time());
        if (!end_time_error.equals("") && end_time_error != null) {
            errors.add(end_time_error);
        }



        return errors;
    }


    private static String _validateTitle(String title) {
        if (title == null || title.equals("")) {
            return "タイトルを入力してください。";
        }

        return "";
    }

    private static String _validateContent(String content) {
        if (content == null || content.equals("")) {
            return "内容を入力してください。";
        }

        return "";
    }

    private static String _validateAdmin(Employee admin) {
        if(admin == null || admin.getId() == -1){
            return "承認者を選択してください。";
        }

        return "";
    }

    private static String _validateStartTime(Time start_time) {
        if(start_time.toString().equals("00:00:00")){
            return "出勤時間を選択してください。";
        }

        return "";
    }

    private static String _validateEndTime(Time end_time) {
        if(end_time.toString().equals("00:00:00")){
            return "退勤時間を選択してください。";
        }

        return "";
    }

}
