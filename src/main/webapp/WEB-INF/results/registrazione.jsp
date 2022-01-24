<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="../components/head.jsp" %>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login-registrazione.css">
</head>
<body>

<%@include file="../components/simple-navbar.jsp" %>

<!--Definizione colonne-->
<div class="container">
    <div class="row">
        <div class="col-6">
            <div id="form-div" class="container my-5 div-login">

                <!--Form dati utente-->
                <%@include file="../components/dati_utente.jsp" %>
            </div>
        </div>
        <div class="col-6 mt-5">
            <img src="${pageContext.request.contextPath}/img/undraw_welcome_re_h3d9.svg" alt="" style="width: 110%; position: sticky; top:15px">
        </div>
    </div>
</div>

<%@include file="../components/footer.jsp" %>

<script type="text/javascript" src=${pageContext.request.contextPath}/js/form-validation.js></script>
<script type="text/javascript" src=${pageContext.request.contextPath}/js/registrazione.js></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>


</body>
</html>




























