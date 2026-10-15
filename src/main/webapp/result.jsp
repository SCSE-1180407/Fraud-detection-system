<%@ page contentType="text/html;charset=UTF-8" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="title" value="Result"/><c:set var="active" value="submit"/><%@ include file="/WEB-INF/jspf/header.jspf" %>
<%-- request attr: report (score, level, results[name,weight,points,reason]) --%>
<div class="card verdict lv-${report.level}">
<div class="gwrap"><svg class="gauge" viewBox="0 0 120 120" aria-hidden="true"><circle class="bg" cx="60" cy="60" r="54"/>
<circle class="fg" cx="60" cy="60" r="54" style="stroke-dashoffset:<fmt:formatNumber value='${339.3*(1-report.score/100)}' maxFractionDigits='1' groupingUsed='false'/>"/></svg>
<div class="gnum"><div><b>${report.score}</b><br><span>out of 100</span></div></div></div>
<div><span class="pill ${report.level}">${report.level}</span>
<h1 style="margin-top:.5rem"><c:choose><c:when test="${report.level=='FRAUD'}">Alert raised for analyst review</c:when>
<c:when test="${report.level=='SUSPICIOUS'}">Review the signals below</c:when><c:otherwise>Looks normal. Logged.</c:otherwise></c:choose></h1>
<div class="spec" role="img" aria-label="Score ${report.score} on a 0 to 100 scale"><i style="left:${report.score}%"></i></div>
<div class="ticks"><span>0</span><span>Suspicious from 35</span><span>Fraud from 70</span><span>100</span></div></div></div>
<div class="card" style="margin-top:1rem"><h2>Why this score</h2>
<c:forEach var="r" items="${report.results}"><div class="rule"><div><b><c:out value="${r.name}"/></b><small>${r.points} of ${r.weight} points</small></div>
<div><div class="bar"><i style="width:${r.points*100/r.weight}%"></i></div><small style="color:var(--mut)"><c:out value="${r.reason}"/></small></div><b>+${r.points}</b></div></c:forEach>
<c:if test="${empty report.results}"><div class="empty">No rule fired for this transaction.</div></c:if></div>
<p style="margin-top:1.5rem"><a class="btn" href="${ctx}/submit">Score another</a> <a class="btn ghost" href="${ctx}/dashboard">Back to dashboard</a></p>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
