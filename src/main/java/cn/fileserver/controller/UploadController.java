package cn.fileserver.controller;

import cn.fileserver.service.FileService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.IntStream;

@Slf4j
@MultipartConfig
public class UploadController extends HttpServlet {

    private FileService fileService = new FileService();
    private static String uploadPath = "E:\\";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        //获得上传的文件
        Part part = req.getPart("multipartFile");

        //String uuid = fileService.uploadFile(part, req.getServletContext().getRealPath("/")); 返回null

        //储存文件
        String uuid = fileService.uploadFile(part, uploadPath);

        //返回UUID
        resp.getWriter().write(uuid);
    }


    public static void main(String[] args) {
        System.out.println(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));

    }
}
