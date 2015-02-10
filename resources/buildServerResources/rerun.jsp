<%@include file="/include.jsp" %>
<script>
    function resizeTextarea (field) {
        field.rows = countHeight(field.value);
    }
    function countHeight(str) {
//        TODO For each line, detect if wrapped
            return (str.match(/\n/g) || []).length +1;
    }
</script>
<form action="${pageContext.request.contextPath}/app/rerun" method="post">
    <input type="hidden" id="buildId" name="buildId" value="${buildId}">
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
                    <c:out value="${parameter.getKey()}"/>
                    <input type="hidden" name="paramNames" value="${parameter.getKey()}"/>
                </td>
                <td>

                    <textarea onkeyup="resizeTextarea(this)" rows="${parameter.height()}" style="width: 80%; max-width: 80vw; font-family: monospace; font-size: 12px" type="text" name="paramValues" value="${parameter.getValue()}">${parameter.getValue()}</textarea>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <br/>
    <input class="btn btn_mini submitButton" type="submit" value="Re-run"/>
</form>
<%--TODO Display "Run started/could not start" message--%>
<br/>
<c:forEach items="${messages}" var="message">
    <pre><c:out value="${message}"/></pre>
</c:forEach>
