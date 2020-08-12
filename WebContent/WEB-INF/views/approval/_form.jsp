<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<label for="report_id">レポートid</label><br />
<c:out value="${report.id}" />
<br /><br />

<label for="report_date">日付</label><br />
<c:out value="${report.report_date}" />
<br /><br />

<label for="name">氏名</label><br />
<c:out value="${report.employee.name}" />
<br /><br />

<label for="title">タイトル</label><br />
<c:out value="${report.title}" />
<br /><br />

<label for="content">内容</label><br />
<c:out value="${report.content}" />
<br /><br />

<c:if test="${report.image != null}">
    <img src="https://quark2galaxy2quark.s3.amazonaws.com/tmp/${report.image}" style="width: 10%">
 <!--   <img src="/daily_report_system/uploads/${report.image}" style="width: 10%"> -->
</c:if>

<label for="created_at">登録日時</label><br />
<c:out value="${report.created_at}" />
<br /><br />

<label for="updated_at">更新日時</label><br />
<c:out value="${report.updated_at}" />
<br /><br />

<label for="approval_status"></label><br />
<select name="approval_status">
    <option value="0"> 承 認 </option>
    <option value="1"> 拒 否 </option>
</select>
<br /><br />

<label for="comment">コメント</label><br />
<textarea name="comment" rows="8" cols="50" placeholder="コメントを入力してください"></textarea>
<br /><br />

<input type="hidden" name="_token" value="${_token}" />
<input type="hidden" name="report_id" value="${report.id}" />
<input type="hidden" name="approval_id" value="${approval.id}" />
<button type="submit">登録</button>