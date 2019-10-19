package cn.fileserver;

import cn.fileserver.controller.DataController;
import cn.fileserver.controller.DownloadController;
import cn.fileserver.controller.UploadController;
import cn.fileserver.filter.AuthFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.DispatcherType;
import javax.servlet.MultipartConfigElement;
import java.util.EnumSet;

public class FileApplication {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8081);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        //配置servlet
        ServletHolder servletHolder = new ServletHolder(new UploadController());
        servletHolder.getRegistration().setMultipartConfig(new MultipartConfigElement(""));
        context.addServlet(servletHolder, "/upload");
        context.addServlet(new ServletHolder(new DataController()), "/data");
        context.addServlet(new ServletHolder(new DownloadController()), "/download");

        //注册过滤器
        context.addFilter(new FilterHolder(new AuthFilter()), "/*",EnumSet.allOf(DispatcherType.class));

        server.start();
        server.join();
    }
}
