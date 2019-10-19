package cn.fileserver.controller;

import cn.fileserver.domain.FileData;
import cn.fileserver.service.FileService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DataController extends HttpServlet {

    private FileService fileService = new FileService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuid = req.getParameter("uuid");
        FileData fileData = fileService.getDataByUUID(uuid);
        //返回JSON格式的数据
        new ObjectMapper().writeValue(resp.getOutputStream(), fileData);
    }
}
