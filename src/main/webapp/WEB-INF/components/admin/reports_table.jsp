<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th scope="col">#ID Segnalazione</th>
        <th scope="col">#ID Campagna</th>
        <th scope="col">Motivazione</th>
        <th scope="col">Archivia</th>
        <th scope="col">Cancella</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${requestScope.segnalazioniList}" var="segnalazione">
        <c:if test="${segnalazione.statoSegnalazione.toString().equalsIgnoreCase('Attiva')}">
           <tr class="clickable clickable-row"
               onclick="window.location.href ='${pageContext.request.contextPath}/campagna/campagna?idCampagna=${segnalazione.campagnaSegnalata.idCampagna}'">
               <th scope="row">${segnalazione.idSegnalazione}</th>
               <td>${segnalazione.campagnaSegnalata.idCampagna}</td>
               <td>${segnalazione.descrizione}</td>
               <td><button class="btn btn-primary pulsante" style="border-color: #00AB98; background-color: #00AB98; color: white"><i class="fas fa-archive"></i></button></td>
               <td><button class="btn btn-primary pulsante" style="border-color: #00AB98; background-color: #00AB98; color: white"><i class="far fa-trash-alt"></i></button></td>
           </tr>
       </c:if>
    </c:forEach>
    </tbody>
</table>