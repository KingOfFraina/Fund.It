<!-- Optional JavaScript -->
<!-- Popper.js first, then Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha1/js/bootstrap.min.js" integrity="sha384-oesi62hOLfzrys4LxRF63OJCXdXDipiYWBnvTl9Y9/TRlw5xlKIEHpNyvvDShgf/" crossorigin="anonymous"></script>

<script>
    var option =
        {
            animation : true,
            delay : 10000
        };

    function Toasty( )
    {
        var toastHTMLElement = document.getElementById( 'reportToast' );

        var toastElement = new bootstrap.Toast( toastHTMLElement, option );

        document.getElementById('toastMessage').innerText = document.getElementById('message').value.replaceAll('+', '\n');

        toastElement.show( );
    }



</script>
<div class="toast align-items-center" role="alert" aria-live="assertive" aria-atomic="true" id = "reportToast">
    <div class="d-flex">
        <div class="toast-body" id = "toastBody">
            <p id = "toastMessage">
            </p>
        </div>
        <button type="button" class="btn-close me-2 m-auto" data-bs-dismiss="toast" aria-label="Close" onclick="hideToast()"></button>
    </div>
</div>

<script>
    hideToast = function (){
        document.getElementById("reportToast").style.display = "none";
    }
</script>