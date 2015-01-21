<%@include file="/include.jsp"%>

<form action="/app/rerun" method="post">
    <input type="hidden" id="promoid" name="promoid" value="${promo}">
    <input class="btn btn_mini submitButton" type="submit" value="Re-run"/>
</form>
<%--TODO Display "Run started/could not start" message--%>
<br/>
<h2>Re-run Parameters</h2>
<table class="runnerFormTable" style="width:100%">
    <tbody>
    <tr>
        <th class="paramName" style="width:30%;">Name</th>
        <th class="paramValue" style="width:70%;">Value</th>
    </tr>
    <c:forEach items="${parameters}" var="parameter">
        <tr>
            <td>
                <pre><c:out value="${parameter.getKey()}"/></pre>
            </td>
            <td>
                <pre><c:out value="${parameter.getValue()}"/></pre>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<br/>
<c:forEach items="${messages}" var="message">
    <pre><c:out value="${message}"/></pre>
</c:forEach>
