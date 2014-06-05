package com.herokuapp.lzqwebsoft.pojo;

import java.util.Date;
import java.util.Set;

/**
 * 封装文章对象
 * @author zqluo
 *
 */
public class Article {
	private String id;
	private User author;
	private ArticleType type;    // 关联一个文章类型
	private int patternTypeId;
	private String title;
	private boolean allowComment;
	private String codeTheme;     // 代码的主题
	private boolean isTop;
	private String content;
	private int status;
	private long readedNum;
	private Date createAt;
	private Date updateAt;
	private int commentCount;   // 一个虚拟的属性，记录对应评论的个数
	private Set<Comment> comments;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public ArticleType getType() {
		return type;
	}
	public void setType(ArticleType type) {
		this.type = type;
	}
	public int getPatternTypeId() {
		return patternTypeId;
	}
	public void setPatternTypeId(int patternTypeId) {
		this.patternTypeId = patternTypeId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean getAllowComment() {
		return allowComment;
	}
	public void setAllowComment(boolean allowComment) {
		this.allowComment = allowComment;
	}
	public String getCodeTheme() {
        return codeTheme;
    }
    public void setCodeTheme(String codeTheme) {
        this.codeTheme = codeTheme;
    }
    public boolean getIsTop() {
		return isTop;
	}
	public void setIsTop(boolean isTop) {
		this.isTop = isTop;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public long getReadedNum() {
		return readedNum;
	}
	public void setReadedNum(long readedNum) {
		this.readedNum = readedNum;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	public Date getUpdateAt() {
		return updateAt;
	}
	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public Set<Comment> getComments() {
		return comments;
	}
	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}
	// 判断文单原创，转载，翻译
	public String getPatternTypeLabel() {
	    if(this.patternTypeId==1)
	        return "<span class='label label-success'>原</span>";
	    else if(this.patternTypeId==2)
	        return "<span class='label label-default'>转</span>";
	    else if(this.patternTypeId==3)
	        return "<span class='label label-warning'>译</span>";
	    else
	        return "";
	}
}
