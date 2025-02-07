package br.com.ifsales.servlets.helpers.funnel;

import br.com.ifsales.dao.FunnelDao;
import br.com.ifsales.model.*;
import br.com.ifsales.servlets.helpers.Helper;
import br.com.ifsales.servlets.helpers.HelperUtils;
import br.com.ifsales.utils.DataSourceSearcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import java.time.LocalDate;

public class SaveFunnelHelper implements Helper {
    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        long id = Long.parseLong(req.getParameter("id"));
        long customerId = Long.parseLong(req.getParameter("customer"));
        long salespersonId = Long.parseLong(req.getParameter("salesperson"));
        long storeId = Long.parseLong(req.getParameter("store"));
        long productId = Long.parseLong(req.getParameter("product"));
        LocalDate paidDate = LocalDate.parse(req.getParameter("paidDate"));
        Double discount = Double.parseDouble(req.getParameter("discount"));
        int productQuantity = Integer.parseInt(req.getParameter("productQuantity"));

        Customer customer = new Customer();
        customer.setId(customerId);

        Salesperson salesperson = new Salesperson();
        salesperson.setId(salespersonId);

        Store store = new Store();
        store.setId(storeId);

        Product product = new Product();
        product.setId(productId);

        Funnel funnel = new Funnel();
        funnel.setId(id);
        funnel.setCustomer(customer);
        funnel.setSalesperson(salesperson);
        funnel.setStore(store);
        funnel.setProduct(product);
        funnel.setPaidDate(paidDate);
        funnel.setDiscount(discount);
        funnel.setProductQuantity(productQuantity);

        FunnelDao funnelDao = new FunnelDao(DataSourceSearcher.getInstance().getDataSource());

        HelperUtils.saveOrUpdate(req, funnel, funnelDao, id);

        if (req.getAttribute("result") == "registerError") {
            return "/pages/home/funnel/funnelForm.jsp";
        }

        return "redirect?action=listFunnels";
    }
}