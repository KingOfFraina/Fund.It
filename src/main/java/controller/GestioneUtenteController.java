package controller;

import controller.utils.FileServlet;
import controller.utils.Validator;
import model.beans.Donazione;
import model.beans.Utente;
import model.beans.proxies.DonazioneProxy;
import model.beans.proxies.UtenteProxy;
import model.services.DonazioniService;
import model.services.DonazioniServiceImpl;
import model.services.SegnalazioniService;
import model.services.SegnalazioniServiceImpl;
import model.services.UtenteService;
import model.services.TipoReport;
import model.services.ReportService;
import model.services.UtenteServiceImpl;
import model.storage.ConPool;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


@WebServlet(name = "GestioneUtenteController",
        value = "/GestioneUtenteController/*")
@MultipartConfig
public final class GestioneUtenteController extends HttpServlet {

   @Override
   protected void doGet(final HttpServletRequest request,
                        final HttpServletResponse response)
           throws ServletException, IOException {
      switch (request.getPathInfo()) {
         case "/visualizzaDashboard":
            visualizzaDashboard(request, response);
            break;
         case "/visualizzaDashboardAdmin":
            visualizzaDashboardAdmin(request, response);
            break;
         default:
            response.sendError(HttpServletResponse.SC_NOT_FOUND,
                    "Risorsa non trovata");
            break;
      }
   }

   @Override
   protected void doPost(final HttpServletRequest request,
                         final HttpServletResponse response)
           throws IOException, ServletException {
      String path = request.getPathInfo();

      switch (path) {
         case "/modificaProfilo":
            modificaProfilo(request, response);
            break;
         case "/promuoviDeclassaUtente":
            promuoviDeclassaUtente(request, response);
            break;
         default:
            response.sendError(HttpServletResponse.SC_NOT_FOUND,
                    "Risorsa non trovata");
            break;
      }
   }

   private void visualizzaDashboardAdmin(final HttpServletRequest request,
                                         final HttpServletResponse response)
           throws IOException, ServletException {
      if (!new Validator(request).isValidBean(new Utente(),
              request.getSession().getAttribute("utente"))) {
         response.sendRedirect(request.getServletContext().getContextPath()
                 + "/AutenticazioneController/login");
         return;
      } else {
         Utente ut = (Utente) request.getSession().getAttribute("utente");
         if (!ut.isAdmin()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Non autorizzato.");
         } else {
            UtenteService us = new UtenteServiceImpl();
            SegnalazioniService segnalazioniService =
                    new SegnalazioniServiceImpl();
            DonazioniService donazioniService = new DonazioniServiceImpl();

            List<Donazione> list = donazioniService.visualizzaDonazioni();
            DonazioneProxy proxy = new DonazioneProxy(new Donazione());
            list.forEach(d -> {
               proxy.setDonazione(d);
               d.setUtente(proxy.getUtente());
            });

            request.setAttribute("utentiList", us.visualizzaUtenti(ut));
            request.setAttribute("segnalazioniList",
                    segnalazioniService.trovaSegnalazioni());
            request.setAttribute("donazioniList", list);

            request.getRequestDispatcher("/WEB-INF/results/admin.jsp")
                    .forward(request, response);
         }
      }
   }

   private void visualizzaDashboard(final HttpServletRequest request,
                                    final HttpServletResponse response)
           throws ServletException, IOException {
      HttpSession session = request.getSession();

      if (!new Validator(request).isValidBean(new Utente(),
              session.getAttribute("utente"))) {
         response.sendRedirect(request.getServletContext().getContextPath()
                 + "/AutenticazioneController/login");
         return;
      } else {
         UtenteService uts = new UtenteServiceImpl();
         Utente ut = uts.visualizzaDashboardUtente(
                 ((Utente) session.getAttribute("utente")).getIdUtente());

         UtenteProxy utenteProxy = new UtenteProxy(ut);
         ut.setDonazioni(utenteProxy.getDonazioni());
         ut.setCampagne(utenteProxy.getCampagne());

         request.setAttribute("utente", ut);

         request.getRequestDispatcher("/WEB-INF/results/profilo_utente.jsp")
                 .forward(request, response);
      }
   }

   private void modificaProfilo(final HttpServletRequest request,
                                final HttpServletResponse response)
           throws IOException, ServletException {
      HttpSession session = request.getSession();
      if (!new Validator(request).isValidBean(new Utente(),
              session.getAttribute("utente"))) {
         response.sendRedirect(request.getServletContext().getContextPath()
                 + "/AutenticazioneController/login");
         return;
      } else {
         Utente utente = new Utente();
         if (request.getParameter("password").equals(
                 request.getParameter("confermaPassword"))
                 && request.getParameter("email").equals(
                 request.getParameter("confermaEmail"))) {
            if (new Validator(request).assertUtente()) {
               Utente inSessione = (Utente) session.getAttribute("utente");
               utente.setIdUtente(((Utente) session.getAttribute("utente"))
                       .getIdUtente());
               utente.createPasswordHash(request.getParameter("password"));
               utente.setEmail(request.getParameter("email"));
               utente.setNome(request.getParameter("nome"));
               utente.setCognome(request.getParameter("cognome"));
               utente.setDataDiNascita(
                       LocalDate.parse(request.getParameter("dataDiNascita")));
               utente.setTelefono(request.getParameter("telefono"));
               utente.setStrada(request.getParameter("indirizzo"));
               utente.setCitta(request.getParameter("citta"));
               utente.setCap(request.getParameter("cap"));
               utente.setCf(request.getParameter("cf"));
               utente.setAdmin(inSessione.isAdmin());
               List<String> listFoto = FileServlet.uploadFoto(request);

               if (!listFoto.isEmpty()) {
                  utente.setFotoProfilo(listFoto.get(0));
               } else {
                  utente.setFotoProfilo(inSessione.getFotoProfilo());
               }

               if (new UtenteServiceImpl().modificaProfilo(utente)) {
                  ReportService.creaReport(request, TipoReport.INFO,
                          "Esito operazione:",
                          "Modifica effettuata con successo");
               } else {
                  ReportService.creaReport(request, TipoReport.ERRORE,
                          "Esito operazione:",
                          "Modifica non effettuata con successo");
               }

               session.setAttribute("utente", utente);
               request.getRequestDispatcher(
                               "/WEB-INF/results/profilo_utente.jsp")
                       .forward(request, response);
            } else {
               response.sendError(HttpServletResponse.SC_BAD_REQUEST);
               return;
            }
         } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
         }
      }
   }

   private void promuoviDeclassaUtente(final HttpServletRequest request,
                                       final HttpServletResponse response)
           throws IOException, ServletException {
      HttpSession session = request.getSession();

      if (!new Validator(request).isValidBean(new Utente(),
              session.getAttribute("utente"))) {
         response.sendRedirect(request.getServletContext().getContextPath()
                 + "/AutenticazioneController/login");
         return;
      } else {
         Utente utente = (Utente) session.getAttribute("utente");

         if (!utente.isAdmin()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Non Autorizzato");
            return;
         } else {
            String idUtente = request.getParameter("utentemod");
            Utente ut = null;
            UtenteService utenteService = new UtenteServiceImpl();

            if (idUtente != null) {
               try {
                  ut = utenteService
                          .visualizzaDashboardUtente(
                                  Integer.parseInt(idUtente));
               } catch (NumberFormatException e) {
                  response.sendError(HttpServletResponse
                          .SC_INTERNAL_SERVER_ERROR);
                  return;
               }

               if (utenteService.promuoviDeclassaUtente(utente, ut)) {
                  ReportService.creaReport(request, TipoReport.INFO,
                          "Esito operazione:",
                          "Modifica effettuata con successo");
               } else {
                  ReportService.creaReport(request, TipoReport.ERRORE,
                          "Esito operazione:",
                          "Modifica non effettuata con successo");
               }
               visualizzaDashboardAdmin(request, response);
            } else {
               response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
               return;
            }
         }
      }
   }

   @Override
   public void destroy() {
      ConPool.getInstance().closeDataSource();
      super.destroy();
   }
}
