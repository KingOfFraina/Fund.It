package controller;

import model.storage.ConPool;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;


@WebServlet(name = "")
public class DonazioneController extends HttpServlet {
    @Override
    public void destroy()
    {
        ConPool.getInstance().closeDataSource();
        super.destroy();
    }
}
