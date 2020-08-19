<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>商談　一覧</h2>
        <table id="report_list">
            <tbody>
                <tr>
                    <th class="report_name">日にち</th>
                    <th class="report_action">商談内容</th>
                </tr>
                <c:forEach var="business" items="${getCustomerBisinessList}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="report_name"><c:out value="${business.report_date}" /></td>
                        <td class="report_action"><c:out value="${business.business}" /></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>


        <p><a href="<c:url value='/' />">トップページへ</a></p>

    </c:param>
</c:import>