package cn.fileserver.domain;

import lombok.Data;

import java.util.Date;

@Data
public class FileData {
    private String uuid;
    private String size;
    private String suffix;
    private String name;
    private Date date;
    private String path;
}
