<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th scope="col">#ID</th>
        <th scope="col">Categoria</th>

    </tr>
    </thead>
    <tbody>
    <c:forEach items="${applicationScope.categorieList}" var="categoria">
            <tr>
                <th scope="row">${categoria.idCategoria}</th>
                <td>${categoria.nome}</td>

            </tr>
    </c:forEach>
    </tbody>
</table>