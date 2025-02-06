package br.com.ifsales.servlets.helpers;

import br.com.ifsales.dao.Dao;
import br.com.ifsales.dao.Storable;
import jakarta.servlet.http.HttpServletRequest;

import java.sql.SQLException;

public class HelperUtils {

    public static <T extends Storable> void saveOrUpdate(HttpServletRequest req, T storable, Dao<T> dao, long id) {
        if (id == 0) {
            try {
                if (dao.save(storable)) req.setAttribute("result", "registerSuccess");
            } catch (SQLException e) {
                System.err.println(e.getStackTrace());
                req.setAttribute("result", "registerError");
            }
        } else {
            storable.setId(id);

            try {
                if (dao.update(storable)) req.setAttribute("result", "updateSuccess");
            } catch (SQLException e) {
                req.setAttribute("result", "updateError");
            }
        }
    }


}
