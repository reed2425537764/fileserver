package cn.fileserver.controller;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public class DownloadController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");

        //获取文件保存的路径
        String path = req.getParameter("path");

        File file = new File(path);
        resp.setContentLength((int) file.length());
        //设置响应头
        resp.setHeader("Accept-Ranges", "bytes");
        resp.setHeader("content-disposition", "attachment;filename=" + file.getName());
        resp.setContentType("application/octet-stream");
        log.info("下载文件：{}   路径为：{}",file.getName(),path);

        try {
            Files.copy(file.toPath(), resp.getOutputStream());
        } catch (IOException e) {
            log.info("文件下载失败 文件名: {}", file.getName());
            e.printStackTrace();
        }
    }
}
