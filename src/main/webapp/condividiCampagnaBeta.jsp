<html>
    <head>
        <title>Condividi</title>
    </head>
    <body>
        <a href="mailto:?body=https://www.corriere.it/economia/&amp;subject=Pensione, quando si può lasciare il ">Mail</a>
        <hr>
        <a href="https://wa.me/?text=Dona a questa campagna: https://www.corriere.it/economia/" target="_blank">Whatsapp</a>
        <hr>
        <a href="https://www.facebook.com/sharer/sharer.php?u=https://www.corriere.it/economia/" target="_blank">Facebook</a>
        <hr>
        <a href="https://twitter.com/share?text=Dona a questa campagna&amp;url=https://www.corriere.it/economia//&amp;via=Fund.It" target="_blank">Twitter</a>
        <hr>
        <input type="text" readonly id="box" value="https://www.corriere.it/economia/"/>
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
