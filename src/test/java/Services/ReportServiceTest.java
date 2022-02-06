package Services;

import model.services.ReportService;
import model.services.TipoReport;
import org.junit.Test;
import org.mockito.Mockito;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReportServiceTest {
   @Test
   public void creaReport1() {
      assertFalse(ReportService.creaReport(null, TipoReport.INFO, "Titolo", "Parametro 1"));
   }

   @Test
   public void creaReport2() {
      HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
      Mockito.when(request.getSession()).thenReturn(Mockito.mock(HttpSession.class));
      Mockito.when(request.getSession().getAttribute("tipoReport")).thenReturn(TipoReport.INFO.toString());
      Mockito.when(request.getSession().getAttribute("titoloReport")).thenReturn("Titolo");
      Mockito.when(request.getSession().getAttribute("bodyReport")).thenReturn("Parametro 1\nParametro 2");
      boolean esito = ReportService.creaReport(request, TipoReport.INFO, "Titolo", "Parametro 1", "Parametro 2");

      assertAll(
              () -> assertTrue(esito),
              () -> assertEquals(TipoReport.INFO.toString(), request.getSession().getAttribute("tipoReport")),
              () -> assertEquals("Titolo", request.getSession().getAttribute("titoloReport")),
              () -> assertEquals("Parametro 1\nParametro 2", request.getSession().getAttribute("bodyReport"))
      );
   }
}
