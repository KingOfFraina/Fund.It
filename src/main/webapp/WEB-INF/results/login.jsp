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
                <form id="form" class="needs-validation" novalidate
                      action="${pageContext.request.contextPath}/AutenticazioneController/login" method="post">

                    <!--email-->
                    <div>
                        <label for="exampleInputEmail1" class="form-label">Indirizzo email</label>
                        <input name="email" type="email" class="form-control" id="exampleInputEmail1"
                               aria-describedby="emailHelp"
                               pattern="^[a-zA-Z0-9.!#$%&’*+\=?^_`{|}~-]{1,29}+@[a-zA-Z0-9-]{1,29}+(?:\.[a-zA-Z0-9-]+){1,10}$"
                               required>
                        <div class=invalid-feedback>
                            Formato email non corretto
                        </div>
                        <div id="emailHelp" class="form-text">Non condivideremo la tua email con nessuno</div>

                    </div>

                    <!--password-->
                    <label for="exampleInputPassword1" class="form-label mt-4">Password</label>
                    <div class="input-group mb-3">

                        <input name="password" type="password" class="form-control" id="exampleInputPassword1"
                               pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&._])[A-Za-z\d@$!%*?&._]{8,32}$"
                               required>
                        <button style="background-color: #00AB98; color: white" class="btn btn-outline-secondary"
                                type="button" id="button-addon2" onclick="togglePassword()"><i class="fas fa-eye"></i>
                        </button>
                        <div class=invalid-feedback>
                            Formato password non corretto
                        </div>
                    </div>

                    <button type="submit" class="btn btn-primary pulsante mt-4 mb-3">Login</button>
                </form>
                <a class="text-black" href="${pageContext.request.contextPath}/AutenticazioneController/registrazione">Non
                    sei registrato? Iscriviti ora!</a>
            </div>
        </div>
        <div class="col-6 mt-5">
            <img class = "img-fluid" src="${pageContext.request.contextPath}/img/undraw_login_re_4vu2.svg" alt="" style="width: 110%;">
        </div>
    </div>
</div>

<%@include file="../components/footer.jsp" %>

<script type="text/javascript" src=${pageContext.request.contextPath}/js/form-validation.js></script>
<script type="text/javascript" src=${pageContext.request.contextPath}/js/registrazione.js></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>


</body>
</html>
