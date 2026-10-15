<%@ page contentType="text/html;charset=UTF-8" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Alerts"/><c:set var="active" value="alerts"/><%@ include file="/WEB-INF/jspf/header.jspf" %>
<h1>Open alerts</h1><p class="sub">Highest risk first. Read the reason, then mark the alert resolved.</p>
<div class="card"><c:choose><c:when test="${empty alerts}"><div class="empty">No open alerts. Nothing needs review right now.</div></c:when>
<c:otherwise><div class="tw"><table><thead><tr><th>Alert</th><th>Customer</th><th>Amount</th><th>Score</th><th>Reasons</th><th></th></tr></thead><tbody>
<c:forEach var="a" items="${alerts}"><tr><td>#${a.id}<br><small style="color:var(--mut)">${a.createdAt}</small></td><td><c:out value="${a.customerName}"/></td><td>&#8377;${a.amount}</td>
<td><span class="pill ${a.riskLevel}">${a.riskScore}</span></td><td style="max-width:320px"><c:out value="${a.reasons}"/></td>
<td><form method="post" action="${ctx}/alerts/resolve"><input type="hidden" name="alertId" value="${a.id}"><button class="btn sm">Mark resolved</button></form></td></tr></c:forEach>
</tbody></table></div></c:otherwise></c:choose></div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
