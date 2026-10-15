<%@ page contentType="text/html;charset=UTF-8" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html><html lang="en"><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width,initial-scale=1"><title>Sign in - SentraX</title>
<link href="https://fonts.googleapis.com/css2?family=Manrope:wght@400;600;800&display=swap" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style.css"></head>
<body class="login"><form class="card" method="post" action="${pageContext.request.contextPath}/login">
<h1>SentraX</h1><p class="sub">Sign in to review risky transactions.</p>
<c:if test="${not empty error}"><div class="err" role="alert"><c:out value="${error}"/></div></c:if>
<div class="field"><label for="u">Username</label><input id="u" name="username" required autofocus autocomplete="username"></div>
<div class="field"><label for="p">Password</label><input id="p" name="password" type="password" required autocomplete="current-password"></div>
<button class="btn" style="width:100%">Sign in</button></form></body></html>
