<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>

<label for="report_date">日付</label><br />
<input type="date" name="report_date" value="<fmt:formatDate value='${report.report_date}' pattern='yyyy-MM-dd' />" />

<br /><br />

<label for="name">氏名</label><br />
<c:out value="${sessionScope.login_employee.name}" />
<br /><br />

<label for="title">タイトル</label><br />
<input type="text" name="title" value="${report.title}" />
<br /><br />

<label for="content">内容</label><br />
<textarea name="content" rows="10" cols="50">${report.content}</textarea>
<br /><br />

<label for="image">画像</label><br />
<input type="file" name="image">


<c:if test="${report.image != null}">
    <img src="https://quark2galaxy2quark.s3.amazonaws.com/tmp/${report.image}" style="width: 10%">
 <!--   <img src="/daily_report_system/uploads/${report.image}" style="width: 10%"> -->
</c:if>




<br /><br />

<label for="admin">承認者</label><br />
<select name="admin">
    <option value="-1">選択してください</option>
    <c:forEach var="admin" items="${adminList}">
        <option value="${admin.id}">
                <c:out value="${admin.name}" />
        </option>
    </c:forEach>
</select>

<br /><br />

<input type="hidden" name="_token" value="${_token}" />
<button type="submit">投稿</button>