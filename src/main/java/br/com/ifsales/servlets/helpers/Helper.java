package br.com.ifsales.servlets.helpers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface Helper {

    Object execute(HttpServletRequest req, HttpServletResponse resp) throws Exception;
}
