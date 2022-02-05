<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th scope="col">#ID Donazione</th>
        <th scope="col">#ID Campagna</th>
        <th scope="col">Donatore</th>
        <th scope="col">Somma Donata</th>
        <th scope="col">Data Donazione</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${requestScope.donazioniList}" var="donazione">
        <tr class="clickable clickable-row"
            onclick="window.location.href ='${pageContext.request.contextPath}/GestioneCampagnaController/campagna?idCampagna=${donazione.campagna.idCampagna}'">
            <th scope="row">${donazione.idDonazione}</th>
            <td>${donazione.campagna.idCampagna}</td>
            <td>${donazione.nome} ${donazione.cognome}</td>
            <td>${donazione.sommaDonata}&euro;</td>
            <td>${donazione.dataOra}</td>
        </tr>

    </c:forEach>
    </tbody>
</table>
