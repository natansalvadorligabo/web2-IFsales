package br.com.ifsales.servlets;

import br.com.ifsales.servlets.helpers.Helper;
import br.com.ifsales.servlets.helpers.HelperFactory;
import com.google.gson.JsonObject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Serial;

@WebServlet("/redirect")
public class FrontControllerServlet extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;

    public FrontControllerServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        Helper helper = new HelperFactory().getHelper(req);
        try
        {
            Object response = helper.execute(req, resp);
            if(response instanceof JsonObject)
            {
                resp.setContentType("application/json");
                resp.getWriter().write(response.toString());
            }
            else
            {
                RequestDispatcher dispatcher = req.getRequestDispatcher(response.toString());
                dispatcher.forward(req, resp);
            }
        }
        catch(Exception error) {
            throw new ServletException(error);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}