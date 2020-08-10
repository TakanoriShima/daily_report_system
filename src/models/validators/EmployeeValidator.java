package models.validators;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import models.Employee;
import utils.DBUtil;

public class EmployeeValidator {
    public static List<String> validate(Employee e, Boolean code_duplicate_check_flag, Boolean password_check_flag) {
        List<String> errors = new ArrayList<String>();

        String code_error = _validateCode(e.getCode(), code_duplicate_check_flag);
        if (!code_error.equals("")) {
            errors.add(code_error);
        }

        String name_error = _validateName(e.getName());
        if (!name_error.equals("")) {
            errors.add(name_error);
        }

        String password_error = _validatePassword(e.getPassword(), password_check_flag);
        if (!password_error.equals("")) {
            errors.add(password_error);
        }

        return errors;
    }

    //社員番号
    private static String _validateCode(String code, Boolean code_duplicate_check_flag) {
        //必須入力チェック
        if (code == null || code.equals("")) {
            return "社員番号を入力してください。";
        }

        //既に登録されている社員番号との重複チェック
        //登録情報を変更する際に社員番号まで重複チェックされてしまうと困るので新規登録の時のみ入力チェックをするために「Boolean」を使用
        if (code_duplicate_check_flag) {
            EntityManager em = DBUtil.createEntityManager();
            long employee_count = (long) em.createNamedQuery("checkRegisteredCode", Long.class)
                    .setParameter("code", code)
                    .getSingleResult();
            em.close();
            if (employee_count > 0) {
                return "入力された社員番号の情報は既に存在しています。";
            }
        }

        return "";
    }

    //社員名の必須入力チェック
    private static String _validateName(String name) {
        if (name == null || name.equals("")) {
            return "氏名を入力してください。";
        }

        return "";
    }

    //パスワードの必須入力チェック
    //一般的に登録情報を変更するときは「パスワードは変更する場合のみ入力してください」と表示されるので新規登録の場合のみパスワードの入力ちチェックを行うため「Boolean」を使用
    private static String _validatePassword(String password, Boolean password_check_flag) {
        if (password == null || password.equals("")) {
            return "パスワードを入力してください。";
        }

        return "";
    }

}
