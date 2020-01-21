package com.piv.money.transfers;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class MoneyTransfersServer {
    private Server jettyServer;

    public void start() throws Exception {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        jettyServer = new Server(8080);
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        jerseyServlet.setInitParameter("javax.ws.rs.Application", MoneyTransferApplication.class.getCanonicalName());

        jettyServer.start();
    }

    public void join() throws Exception {
        jettyServer.join();
    }

    public void destroy() {
        jettyServer.destroy();
    }

    public void stop() throws Exception {
        jettyServer.stop();
    }

    public static void main(String[] args) {
        MoneyTransfersServer server = new MoneyTransfersServer();
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.destroy();
        }
    }
}
