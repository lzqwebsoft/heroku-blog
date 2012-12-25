package com.herokuapp.lzqwebsoft.pojo;

import java.util.Date;

/**
 * 封装图片类
 * @author zqluo
 *
 */
public class Image {
	private int id;
	private String fileName;
	private String diskFilename;
	private long size;
	private String descriptions;
	private Date createAt;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getDiskFilename() {
        return diskFilename;
    }
    public void setDiskFilename(String diskFilename) {
        this.diskFilename = diskFilename;
    }
    public long getSize() {
        return size;
    }
    public void setSize(long size) {
        this.size = size;
    }
    public String getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
}