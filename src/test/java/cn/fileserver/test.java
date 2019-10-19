package cn.fileserver;

import cn.fileserver.controller.UploadController;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@MultipartConfig
public class test extends HttpServlet {

    @Before
    public void init()  {
        Server server = new Server(8081);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        ServletHolder servletHolder = new ServletHolder(new UploadController());
        servletHolder.getRegistration().setMultipartConfig(new MultipartConfigElement(""));
        context.addServlet(servletHolder, "/upload");

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //测试上传文件
    @Override
    @Test
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Part part = req.getPart("multipartFile");
        System.out.println();

        BufferedInputStream bis = new BufferedInputStream(part.getInputStream());
        Path path1 = Paths.get("E:\\", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        if (!Files.exists(path1)) {
            Files.createDirectory(path1);
        }
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path1.toString() + "2.jpg"));
        byte[] bytes = new byte[1024 * 10];
        int len = 0;
        while ((len = bis.read(bytes)) != -1) {
            bos.write(bytes, 0, len);
        }
        bos.close();
        bis.close();
    }
}
