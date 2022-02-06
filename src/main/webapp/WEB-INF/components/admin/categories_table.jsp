<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th scope="col">#ID</th>
        <th scope="col">Categoria</th>
        <th scope="col">Modifica Categoria</th>


    </tr>
    </thead>
    <tbody>
    <c:forEach items="${applicationScope.categorieList}" var="categoria">
        <tr>
            <th scope="row">${categoria.idCategoria}</th>
            <td>${categoria.nome}</td>
            <td>
                <form action="${pageContext.request.contextPath}/categorie/modificaCategoria" method="post">
                    <input type="text" value="${categoria.idCategoria}" name="idCategoria" hidden>
                    <input type="text" class="form-control" value="${categoria.nome}" name="nomeCategoria">

                    <button type="submit" class="btn btn-primary mt-3"
                            style="background-color: #00AB98; border-color: #00AB98; color: white">
                        <i class="fas fa-pen-square"></i>
                    </button>
                </form>
            </td>

        </tr>
    </c:forEach>
    </tbody>

</table>

<div class="container mt-5">
    <form method="post" action="${pageContext.request.contextPath}/categorie/inserisciCategoria" class = "needs-validation" novalidate>

        <label for = "nomeCategoria">Inserisci categoria</label>
        <input id = "nomeCategoria" type="text" class="form-control " name="nomeCategoria" required >


        <button type="submit" class="btn btn-primary mt-3"
                style="background-color: #00AB98; border-color: #00AB98; color: white">
            <i class="fas fa-plus-square"></i>
        </button>
    </form>
</div>

