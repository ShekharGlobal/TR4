package com.example.async;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * Using the Asynchronous Servlet Mechanism  Idea

   Browser sends request

   Servlet does not block

    Server responds later when data is ready
    
 */
@WebServlet(urlPatterns = "/async", asyncSupported = true)
public class AsyncServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException {

        response.setContentType("text/plain");
//Tells the server: “Don’t block this request. I’ll respond later.”
        AsyncContext asyncContext = request.startAsync();

        new Thread(() -> {
            try {
            	//Creates a new thread, Simulates a slow operation
                //Sleeps for 3 seconds
                Thread.sleep(3000);

                PrintWriter out =
                    asyncContext.getResponse().getWriter();
                out.println("Server update at " +
                             System.currentTimeMillis());

                asyncContext.complete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}