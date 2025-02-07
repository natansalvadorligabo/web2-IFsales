package br.com.ifsales.servlets.helpers;

import br.com.ifsales.dao.Dao;
import br.com.ifsales.dao.Storable;
import jakarta.servlet.http.HttpServletRequest;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;

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
                System.err.println(e.getStackTrace());
                req.setAttribute("result", "updateError");
            }
        }
    }

    public static <T extends Storable> String safeDelete(HttpServletRequest req, Optional<T> storable, Dao<T> dao, String path) {
        try {
            if (storable.isPresent() && dao.delete(storable.get().getId())) {
                req.setAttribute("result", "deleteSuccess");
                return "redirect?action=" + path;
            }
        } catch (SQLException e) {
            System.err.println(Arrays.toString(e.getStackTrace()));
        }

        req.setAttribute("result", "deleteError");
        return "redirect?action=" + path;
    }


}
