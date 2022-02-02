<nav id="sidebar">
    <div class="sidebar-header">
        <h3>Categorie</h3>


    </div>

    <ul class="list-unstyled components">

        <c:forEach items="${applicationScope.categorieList}" var="cat">
        <li>
            <a href="#"
               style="font-family: Comfortaa, cursive; font-size: 20px; text-decoration: none; color: black;">${cat.nome}
                &nbsp; &nbsp;<i class="fas fa-chevron-right"></i></a>
        </li>
        </c:forEach>

            <button type="button" id="sidebarCollapse2" class="btn btn-info" onclick="hideCategories()" style="margin-top: 10px; margin-left: 10px">
                <i class="fas fa-align-left"></i>
                <span>Nascondi Categorie</span>
            </button>

</nav>