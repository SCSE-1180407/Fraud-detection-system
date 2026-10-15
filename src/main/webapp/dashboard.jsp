<%@ page contentType="text/html;charset=UTF-8" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Dashboard"/><c:set var="active" value="dash"/><%@ include file="/WEB-INF/jspf/header.jspf" %>
<h1>Dashboard</h1><p class="sub">Live picture of every transaction scored so far.</p>
<div class="grid g4">
<div class="card stat s-safe"><b>${safe}</b><span>Safe</span></div>
<div class="card stat s-susp"><b>${suspicious}</b><span>Suspicious</span></div>
<div class="card stat s-fraud"><b>${fraud}</b><span>Fraud</span></div>
<a class="card stat s-open" href="${ctx}/alerts" style="text-decoration:none;color:inherit"><b>${openAlerts}</b><span>Open alerts - review now</span></a></div>
<div class="card" style="margin-top:1rem"><div style="display:flex;justify-content:space-between;align-items:center;margin-bottom:.5rem"><h2 style="margin:0">Highest-risk transactions</h2><a class="btn sm" href="${ctx}/submit">New transaction</a></div>
<c:choose><c:when test="${empty topRisky}"><div class="empty">No transactions yet. Submit one to see it scored here.</div></c:when>
<c:otherwise><div class="tw"><table><thead><tr><th>ID</th><th>Customer</th><th>Amount</th><th>Score</th><th>Level</th></tr></thead><tbody>
<c:forEach var="t" items="${topRisky}"><tr><td>#${t.id}</td><td><c:out value="${t.customerName}"/></td><td>&#8377;${t.amount}</td><td><b>${t.riskScore}</b></td><td><span class="pill ${t.riskLevel}">${t.riskLevel}</span></td></tr></c:forEach>
</tbody></table></div></c:otherwise></c:choose></div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
