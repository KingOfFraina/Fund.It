<html>
    <head>
        <title>Condividi</title>
    </head>
    <body>
        <a href="${requestScope.linkList.get('mail')}" target="_blank">Mail</a>
        <hr>
        <a href="${requestScope.linkList.get('whatsapp')}" target="_blank">Whatsapp</a>
        <hr>
        <a href="${requestScope.linkList.get('facebook')}" target="_blank">Facebook</a>
        <hr>
        <a href="${requestScope.linkList.get('twitter')}" target="_blank">Twitter</a>
        <hr>
        <input type="text" readonly id="box" value="${requestScope.linkList.get('link')}"/>
        <button id="btn" onclick="foo()">Copia link</button>
        <script>
            function foo() {
                var copyText = document.getElementById("box");
                copyText.select();
                copyText.setSelectionRange(0, 99999);
                navigator.clipboard.writeText(copyText.value);
                alert("Copied the text: " + copyText.value);
            }
        </script>
    </body>
</html>