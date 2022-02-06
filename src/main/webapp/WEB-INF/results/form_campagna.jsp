<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%@include file="../components/head.jsp" %>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login-registrazione.css">

</head>
<body>

<%@include file="../components/navbar.jsp" %>
<%@include file="../components/modals.jsp" %>

<!--Definizione colonne-->
<div class="container">
    <div class="row">
        <div class="col-6">
            <div id="form-div" class="container my-5 div-login">

                <div class="row mb-3" style="width: fit-content; margin-left: 10px">
                    <button id="back" class="btn btn-primary mt-4 mb-3"
                            data-bs-toggle="modal" data-bs-target="#modalAnnullamento">
                        <i class="fas fa-arrow-left"></i></button>
                </div>

                <form id="form" class="needs-validation" novalidate method="post" enctype="multipart/form-data">

                    <input type="hidden" value="${requestScope.campagna.idCampagna}" name="idCampagna">

                    <!--Titolo della campagna-->
                    <div class="col">
                        <label for="inputTitolo" class="form-label">Titolo</label>
                        <input name="titolo" type="text" class="form-control" id="inputTitolo"
                               placeholder="Dai un titolo alla tua campagna!" required
                               value="${requestScope.campagna.titolo}">
                        <div class=invalid-feedback>
                            Formato nome non corretto
                        </div>
                    </div>

                    <!--Descrizione della campagna-->
                    <div class="col mt-4">
                        <label for="inputDescrizione" class="form-label">Descrizione</label>
                        <div class="form-floating">
<textarea name="descrizione" class="form-control" placeholder="Descrivi la tua campagna"
          id="inputDescrizione" maxlength="3000" style="height: 100px"
          required>${requestScope.campagna.descrizione}</textarea>
                        </div>
                        <div class=invalid-feedback>
                            La descrizione deve essere lunga max. 3000 caratteri
                        </div>
                    </div>

                    <!--categoria-->
                    <div class="col mt-4">
                        <label for="selectCategorie" class="form-label">Categoria</label>
                        <select name="idCategoria" id="selectCategorie" class="form-select"
                                aria-label="Default select example" required>
                            <c:forEach items="${categorie}" var="cat">
                                <c:choose>
                                    <c:when test="${requestScope.campagna.categoria.idCategoria == cat.idCategoria}">
                                        <option selected value="${cat.idCategoria}">
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${cat.idCategoria}">
                                    </c:otherwise>
                                </c:choose>
                                ${cat.nome}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <!--Somma Target-->
                    <div class="col mt-4">
                        <label for="inputTarget" class="form-label">Che somma vorresti raggiungere?</label>
                        <input name="sommaTarget" type="number" class="form-control" id="inputTarget"
                               placeholder="10" required value="${requestScope.campagna.sommaTarget}">
                    </div>

                    <!--File-->

                    <c:choose>
                        <c:when test="${requestScope.campagna != null}">
                            <div class="mt-4 mb-3">
                                <label for="formFileMultipleI" class="form-label">Allega una o più immagini per la tua
                                    campagna</label>
                                <input class="form-control" name="file" type="file" id="formFileMultipleI" multiple
                                       accept="image/*,.jpg">
                            </div>
                            <button type="submit" class="btn btn-primary pulsante mt-4 mb-3"
                                    formaction="${pageContext.request.contextPath}/campagna/modificaCampagna"
                                    style="background-color: #00AB98; border-color: #00AB98">
                                Salva modifiche
                            </button>
                        </c:when>
                        <c:otherwise>
                            <div class="mt-4 mb-3">
                                <label for="formFileMultipleII" class="form-label">Allega una o più immagini per la tua
                                    campagna</label>
                                <input class="form-control" name="file" type="file" id="formFileMultipleII" multiple
                                       accept="image/*,.jpg" required>
                            </div>
                            <button type="submit" class="btn btn-primary pulsante mt-4 mb-3"
                                    style="border-color: #00AB98; background-color: #00AB98"
                                    formaction="${pageContext.request.contextPath}/campagna/creaCampagna">
                                Crea campagna
                            </button>
                        </c:otherwise>
                    </c:choose>
                </form>
            </div>
        </div>
        <div class="col-6 mt-5">
            <img src="${pageContext.request.contextPath}/img/undraw_virtual_assistant_jjo2.svg" alt=""
                 style="width: 75%; position: sticky; top:0px">
        </div>
    </div>
</div>

<%@include file="../components/footer.jsp" %>

<script type="text/javascript" src=${pageContext.request.contextPath}/js/form-validation.js></script>
<script type="text/javascript" src=${pageContext.request.contextPath}/js/registrazione.js></script>

</body>
</html>




