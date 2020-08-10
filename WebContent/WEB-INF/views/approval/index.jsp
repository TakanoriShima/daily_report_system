<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
         <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
         </c:if>
         <h2>日報承認　一覧</h2>
         <table id="approval_list">
         <tbody>
         <tr>
         <th class="report_name">氏名</th>
                    <th class="report_date">日付</th>
                    <th class="report_title">タイトル</th>
                    <th class="report_action">操作</th>
                    <th class="report_approval">承認状況</th>
                    </tr>
                    <c:forEach var="report" items="${reports}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="report_name"><c:out value="${report.employee.name}" /></td>
                        <td class="report_date"><c:out value="${report.report_date}" /></td>
                        <td class="report_title"><c:out value="${report.title}" /></td>
                        <td class="report_action">
                            <c:choose>
                                <c:when test="${report.approval.approval_flag == 1}">
                                    <a href="<c:url value='/approval/show?id=${report.approval.id}' />">詳細を見る(処理済み)</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="<c:url value='/approval/new?id=${report.id}' />">承認画面へ</a>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td class="report_approval">
                            <c:choose>
                                <c:when test="${report.getApproval().getApproval_status() == 0}">承認</c:when>
                                <c:when test="${report.getApproval().getApproval_status() == 1}">拒否</c:when>
                                <c:otherwise>未承認</c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                    </c:forEach>
         </tbody>
         </table>

         <div id="pagination">
            （全 ${reports_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((reports_count - 1) / 15) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/approval/index?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
    </c:param>
</c:import>