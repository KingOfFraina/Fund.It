package model.services;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Iterator;

public interface ReportService {
   /**
    * Queto metodo permette la creazione di un nuovo report.
    * @param request la destinazione del report
    * @param tipoReport il tipo di report (vedi TipoReport)
    * @param titolo il titolo del report
    * @param arguments il body del report
    * @return l'esito con cui si è conclusa l'operazione
    */
   static boolean creaReport(HttpServletRequest request, TipoReport tipoReport,
                             String titolo, String... arguments) {
      if (request != null) {
         request.setAttribute("tipoReport", tipoReport.toString());
         request.setAttribute("titoloReport", titolo);

         String body = "";

         for (Iterator<String> it = Arrays.stream(arguments).iterator();
              it.hasNext();) {

            body += it.next() + "\n";
         }

         request.setAttribute("bodyReport", body);
         return true;
      }

      return false;
   }
}
