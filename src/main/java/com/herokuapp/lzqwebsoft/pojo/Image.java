package com.herokuapp.lzqwebsoft.pojo;

import java.util.Date;

/**
 * 封装图片类
 * @author zqluo
 *
 */
public class Image {
	private String id;
	private String fileName;
	private byte[] content;   // 图片的内容，使用二进制存储在数据库中
	private long size;
	private String descriptions;
	private Date createAt;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
    public byte[] getContent() {
        return content;
    }
    public void setContent(byte[] content) {
        this.content = content;
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
	/**
	 * 根据图片的文件名来得到对应的ContentType
	 * @return ContentType
	 */
	public String getContentType(){
	    if(fileName==null) {
	        return null;
	    } else {
	        int index = fileName.lastIndexOf('.');
	        if(index==-1)
	            return null;
	        String suffix = fileName.substring(index).toLowerCase();
	        if(suffix.equals("gif")) {
	            return "image/gif";
	        } else if (suffix.equals("jpg")||suffix.equals("jpeg")||suffix.equals("jpe")) {
	            return "image/jpeg";
	        } else if (suffix.equals("png")) {
                return "image/png";
            } else if (suffix.equals("tiff")||suffix.equals("tif")) {
                return "image/tiff";
            } else if (suffix.equals("bmp")) {
                return "image/x-ms-bmp";
            } else if (suffix.equals("xpm")) {
                return "image/x-xpixmap";
            } else {
                return null;
            }
	    }
	}
}