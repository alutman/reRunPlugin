<%@include file="/include.jsp" %>
<script>
    function resizeTextarea (field) {
        field.rows = countHeight(field.value, field.cols);
    }
    function countHeight(str, cols) {
        str = str+"";
        var newLines = (str.match(/\n/g) || []).length;
        var plusOne = 1;
        var lines = str.split("\n");
        var wraps = 0;
        lines.forEach(function(element) {
            while(element.length > cols) {
                wraps++;
                element = element.substring(cols, element.length);
            }
        });
        return newLines + plusOne + wraps;
    }
    window.onload = function() {
        var paramAreaArr = document.getElementsByName("reRunParamTextArea");
        for(var i = 0; i < paramAreaArr.length; i++) {
            resizeTextarea(paramAreaArr[i]);
        }
    }
</script>
<form action="${pageContext.request.contextPath}/app/rerun" method="post">
    <input type="hidden" id="buildId" name="buildId" value="${buildId}">
    <h2>Re-run Parameters</h2>
    <c:if test="${parameters.size() > 0}">
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
                        <textarea name="reRunParamTextArea" onkeyup="resizeTextarea(this)" onclick="resizeTextarea(this)" rows="1" cols="100" style="font-family: monospace;  resize: none; overflow: hidden; word-break: break-all" type="text" name="paramValues" value="${parameter.getValue()}">${parameter.getValue()}</textarea>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${parameters.size() <= 0}">
        <i>no parameters defined </i>
        <br/>
    </c:if>
    <br/>
    <input class="btn btn_mini submitButton" type="submit" value="Re-run"/>
</form>
<br/>
<c:forEach items="${messages}" var="message">
    <pre><c:out value="${message}"/></pre>
</c:forEach>
