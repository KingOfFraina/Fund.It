<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="../components/head.jsp" %>
    <title>Fund.it</title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin.css">
</head>
<body>
<%@include file="../components/navbar.jsp" %>

<div class="wrapper">
    <!-- Sidebar -->
    <%@include file="../components/sidebar_admin.jsp" %>

    <!-- Page Content -->
<div class = "container mt-3" style="display: none" id = "user_table">
    <%@include file="../components/admin/user_table.jsp" %>
</div>





</div>



<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js" integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13" crossorigin="anonymous"></script>

<script src ="${pageContext.request.contextPath}/js/admin.js"></script>
</body>
</html>
