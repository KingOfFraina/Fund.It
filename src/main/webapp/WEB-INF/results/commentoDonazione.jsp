<html>
  <head>
      <title>Scrivi un commento</title>
  </head>
  <body>
    <form method="post" action="${pageContext.request.contextPath}/donazione/scriviCommento">
        <input type="text" name="commento" placeholder="Il tuo commento">
        <div>
            <input type="checkbox" name="anonimo" id="anonimo">
            <label for="anonimo">Anonimo</label>
        </div>
        <input type="submit" value="Salva Commento">
    </form>
  </body>
</html>
