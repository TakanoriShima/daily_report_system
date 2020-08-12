<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${approval != null}">
                <h2>日報　承認ページ</h2>

                <table>
                    <tbody>
                        <tr>
                            <th>氏名</th>
                            <td><c:out value="${approval.report.employee.name}" /></td>
                        </tr>
                        <tr>
                            <th>日付</th>
                            <td><fmt:formatDate value="${approval.report.report_date}" pattern="yyyy-MM-dd" /></td>
                        </tr>
                        <tr>
                            <th>内容</th>
                            <td>
                                <pre><c:out value="${approval.report.content}" /></pre>
                            </td>
                        </tr>
                        <tr>
                        <tr>
                            <th>画像</th>
                            <td>
                                <c:if test="${approval.report.image != null}">
                                    <img src="https://quark2galaxy2quark.s3.amazonaws.com/tmp/${approval.report.image}" style="width: 50%">
                                 <!--   <img src="/daily_report_system/uploads/${report.image}" style="width: 10%"> -->
                                </c:if>
                            </td>
                        </tr>
                        <tr>
                            <th>登録日時</th>
                            <td>
                                <fmt:formatDate value="${approval.report.created_at}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </td>
                        </tr>
                        <tr>
                            <th>更新日時</th>
                            <td>
                                <fmt:formatDate value="${approval.report.updated_at}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </td>
                        </tr>
                        <tr>
                            <th>承認状況</th>
                            <td>
                                <c:choose>
                                    <c:when test="${approval.approval_status == 0}">承認済み</c:when>
                                    <c:otherwise>拒否</c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <tr>
                            <th>コメント</th>
                            <td>
                                <pre><c:out value="${approval.comment}" /></pre>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <br /><br />

<!--                 承認者以外の人が該当の承認状況を編集できないようにする（editリンクを出さない） -->
                <c:if test="${sessionScope.login_employee.admin_flag == 1}">
                    <p><a href="<c:url value="/approval/edit?id=${approval.id}" />">この承認内容を編集する</a></p>
                </c:if>
            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>

        <p><a href="<c:url value="/approval/index" />">一覧に戻る</a></p>
    </c:param>
</c:import>