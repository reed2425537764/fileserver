package cn.fileserver.service;

import cn.fileserver.dao.FileDao;
import cn.fileserver.domain.FileData;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Slf4j
public class FileService {

    private FileDao fileDao = new FileDao();

    public String uploadFile(Part part,String path1) throws IOException {
        //创建文件夹
        Path path = Paths.get(path1, LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        if (!Files.exists(path)) {
            Files.createDirectory(path);
        }

        //生成UUID
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String header = part.getHeader("Content-Disposition");
        log.info("上传文件请求头信息：{}", header);
        String suffix = part.getSubmittedFileName().split("\\.")[1];

        //拼接最终文件名
        String fileName = uuid + '.' + suffix;
        //保存文件
        Files.copy(part.getInputStream(), path.resolve(fileName));

        //数据写入数据库
        fileDao.saveData(uuid,Long.toString(part.getSize()),suffix,part.getSubmittedFileName()
                ,LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),path.resolve(fileName).toString());
        return uuid;
    }

    public FileData getDataByUUID(String uuid) {
        return fileDao.getDataByUUID(uuid);
    }
}
