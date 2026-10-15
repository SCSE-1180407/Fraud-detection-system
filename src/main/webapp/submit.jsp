<%@ page contentType="text/html;charset=UTF-8" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="New transaction"/><c:set var="active" value="submit"/><%@ include file="/WEB-INF/jspf/header.jspf" %>
<h1>New transaction</h1><p class="sub">Enter the details. You get a score and the reasons right away.</p>
<form class="card" method="post" action="${ctx}/submit">
<c:if test="${not empty error}"><div class="err" role="alert"><c:out value="${error}"/></div></c:if>
<div class="form2">
<div class="field"><label for="c">Customer</label><select id="c" name="customerId" required><option value="">Choose a customer</option>
<c:forEach var="c" items="${customers}"><option value="${c.id}"><c:out value="${c.name}"/> (${c.homeCity})</option></c:forEach></select></div>
<div class="field"><label for="a">Amount (&#8377;)</label><input id="a" name="amount" type="number" min="1" step="0.01" required></div>
<div class="field"><label for="m">Merchant</label><input id="m" name="merchant" required></div>
<div class="field"><label for="l">Location</label><input id="l" name="location" required><div class="hint">City where the payment was made.</div></div>
<div class="field"><label for="d">Device ID</label><input id="d" name="deviceId" required><div class="hint">A device this customer has not used before adds risk.</div></div>
<div class="field"><label for="t">Time</label><input id="t" name="txnTime" type="datetime-local"><div class="hint">Leave empty to use the current time.</div></div></div>
<button class="btn">Score transaction</button></form>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
