package spring_boot.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping("/error")
    @ResponseBody
    String error(HttpServletRequest request, HttpServletResponse httpServletResponse) throws IOException {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String page = "<h1>Error occurred</h1>";
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            page = page + "<p><b>Status:</b> " + statusCode + " " + RequestDispatcher.ERROR_EXCEPTION + "</p>";
        }
        return page;
    }

    public String getErrorPath() {
        return "/error";
    }
}
