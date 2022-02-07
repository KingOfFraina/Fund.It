<nav id="sidebar">
    <div class="sidebar-header">
        <h3>Categorie</h3>


    </div>

    <ul class="list-unstyled components">

        <c:forEach items="${applicationScope.categorieList}" var="cat">
        <li>
            <form action="${pageContext.request.contextPath}/campagna/ricercaCategoria" method="get">
            <input type="text" value="${cat.idCategoria}" name = "idCat" hidden>
                <button type="submit"
               style="font-family: Comfortaa, cursive; font-size: 20px; text-decoration: none; color: black;">${cat.nome}
                &nbsp; &nbsp;<i class="fas fa-chevron-right"></i></button>
            </form>
        </li>
        </c:forEach>

            <button type="button" id="sidebarCollapse2" class="btn btn-info" onclick="hideCategories()" style="margin-top: 10px; margin-left: 10px">
                <i class="fas fa-align-left"></i>
                <span>Nascondi Categorie</span>
            </button>

</nav>