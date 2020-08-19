<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${report != null}">
                 <c:if test="${flush != null}">
                    <div id="flush_success">
                        <c:out value="${flush}"></c:out>
                    </div>
                </c:if>
                <h2>日報　詳細ページ</h2>

                <table>
                    <tbody>
                        <tr>
                            <th>氏名</th>
                            <td><c:out value="${report.employee.name}" /></td>
                        </tr>
                        <tr>
                            <th>日付</th>
                            <td><fmt:formatDate value="${report.report_date}" pattern="yyyy-MM-dd" /></td>
                        </tr>
                        <tr>
                            <th>内容</th>
                            <td>
                                <pre><c:out value="${report.content}" /></pre>
                            </td>
                        </tr>
                        <tr>
                            <th>画像</th>
                            <td>
                            <c:choose>
                                <c:when test="${report.image != null}" >
                                    <img src="https://quark2galaxy2quark.s3.amazonaws.com/tmp/${report.image}" style="width: 10%">
                                </c:when>
                                <c:otherwise>
                                    画像はありません。
                                </c:otherwise>
                            </c:choose>


                             <!--   <img src="/daily_report_system/uploads/${report.image}" style="width: 10%"> -->

                            </td>
                        </tr>
                        <tr>
                            <th>登録日時</th>
                            <td>
                                <fmt:formatDate value="${report.created_at}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </td>
                        </tr>
                        <tr>
                            <th>更新日時</th>
                            <td>
                                <fmt:formatDate value="${report.updated_at}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </td>
                        </tr>
                          <tr>
                            <th>出勤時間</th>
                            <td>
                                <fmt:formatDate value="${report.start_time}" pattern="HH:mm" />
                            </td>
                        </tr>
                          <tr>
                            <th>退勤時間</th>
                            <td>
                                <fmt:formatDate value="${report.end_time}" pattern="HH:mm" />
                            </td>
                        </tr>
                        <tr>
                            <th>いいね数</th>
                            <td>
                                <c:out value="${liked_count}件" />
                            </td>
                        </tr>

                        <c:if test="${MyFoviritedEmployeeList != null}">
                            <tr>
                                <th>いいねをしてくれた人の一覧</th>
                                <td>
                                    <ul>
                                    <c:forEach var="emp" items="${MyFoviritedEmployeeList}">
                                        <li><c:out value="${emp.name}" /></li>
                                    </c:forEach>
                                    </ul>
                                </td>
                            </tr>
                        </c:if>

                    </tbody>
                </table>

                <br/>
                <c:choose>
                    <c:when test="${favorites_count == 0}">
                        <form method="POST" action="/daily_report_system/favorites/create">
                            <input type="hidden" name="report_id" value="${report.id}">
                            <input type ="submit" value="いいね">
                        </form>
                    </c:when>
                    <c:otherwise>
                        <form method="POST" action="/daily_report_system/favorites/destroy">
                            <input type="hidden" name="report_id" value="${report.id}">
                            <input type ="submit" value="いいね解除">
                        </form>

                    </c:otherwise>
                </c:choose>


                <br/>
                <!-- 作成者以外の人が該当の日報を編集できないようにする（editリンクを出さない） -->
                <c:if test="${sessionScope.login_employee.id == report.employee.id}">
                    <p><a href="<c:url value="/reports/edit?id=${report.id}" />">この日報を編集する</a></p>
                </c:if>
            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>

        <p><a href="<c:url value="/reports/index" />">一覧に戻る</a></p>
    </c:param>
</c:import>