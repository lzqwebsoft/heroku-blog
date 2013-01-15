package com.herokuapp.lzqwebsoft.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.herokuapp.lzqwebsoft.dao.ArticleDAO;
import com.herokuapp.lzqwebsoft.dao.ArticleTypeDAO;
import com.herokuapp.lzqwebsoft.pojo.Article;
import com.herokuapp.lzqwebsoft.pojo.ArticleType;
import com.herokuapp.lzqwebsoft.pojo.Page;
import com.herokuapp.lzqwebsoft.util.CommonConstant;

@Service("articleService")
public class ArticleServiceImpl implements ArticleService {
	@Autowired
	private ArticleTypeDAO articleTypeDAO;
	
	@Autowired
	private ArticleDAO articleDAO;

	@Override
	public void save(Article article, String typeName, boolean modelNew, boolean isDraft) {
		// 判断文章的类型是否通过文本框输入来得到
		if(modelNew) {
			ArticleType type = articleTypeDAO.getArticleTypeByName(typeName);
			if(type==null) {
				type = new ArticleType();
				type.setName(typeName);
				type.setDisable(false);
				type.setCreateAt(new Date());
				type.setUpdateAt(new Date());
				articleTypeDAO.save(type);
				article.setType(type);
			} else {
			    article.setType(type);
			}
		} else {
			ArticleType type = articleTypeDAO.getArticleTypeById(article.getType().getId());
			article.setType(type);
		}
		// 生成文章内容的路径，关保存为文本文件
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
		String id = format.format(new Date());
		StringBuffer filename = new StringBuffer().append("/")
		    .append(id).append(".txt");
		OutputStreamWriter out = null;
		File file = new File(CommonConstant.ARTICLES_DIR+"/"+filename.toString());
		try {
			if(!file.exists())
				file.createNewFile();
			out = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			out.write(article.getContent());
			out.close();
			article.setContentPath(filename.toString());
			
			// 文件对象入库
			Date now = new Date();
			article.setId(id);
			// 判断是否将文章保存为草稿或发表
			if(isDraft)
				article.setStatus(0);
			else
				article.setStatus(1);
			article.setReadedNum(0);
			article.setCreateAt(now);
			article.setUpdateAt(now);
			articleDAO.save(article);
		} catch (FileNotFoundException e) {
			Log log = LogFactory.getLog(ArticleServiceImpl.class);
            log.error(e.getMessage(), e);
		} catch (IOException e) {
			Log log = LogFactory.getLog(ArticleServiceImpl.class);
            log.error(e.getMessage(), e);
			if(out!=null) {
				try {
					out.close();
				} catch (IOException e1) {}
			}
		}
	}
	
	@Override
	public void update(Article article, String typeName, boolean modelNew, boolean isDraft) {
		// 判断文章的类型是否通过文本框输入来得到
		if(modelNew) {
			ArticleType type = articleTypeDAO.getArticleTypeByName(typeName);
			if(type==null) {
				type = new ArticleType();
				type.setName(typeName);
				type.setDisable(false);
				type.setCreateAt(new Date());
				type.setUpdateAt(new Date());
				articleTypeDAO.save(type);
				article.setType(type);
			} else {
			    article.setType(type);
			}
		} else {
			ArticleType type = articleTypeDAO.getArticleTypeById(article.getType().getId());
			article.setType(type);
		}
		
		OutputStreamWriter out = null;
		File file = new File(CommonConstant.ARTICLES_DIR+"/"+article.getContentPath());
		try {
			if(!file.exists())
				file.createNewFile();
			out = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			out.write(article.getContent());
			out.close();
			
			// 更新文件对象
			Date now = new Date();
			// 判断是否将文章保存为草稿或发表
			if(isDraft)
				article.setStatus(0);
			else
				article.setStatus(1);
			article.setReadedNum(0);
			article.setUpdateAt(now);
			articleDAO.edit(article);
		} catch (FileNotFoundException e) {
			Log log = LogFactory.getLog(ArticleServiceImpl.class);
            log.error(e.getMessage(), e);
		} catch (IOException e) {
			Log log = LogFactory.getLog(ArticleServiceImpl.class);
            log.error(e.getMessage(), e);
			if(out!=null) {
				try {
					out.close();
				} catch (IOException e1) {}
			}
		}
	}

	@Override
	public Article get(String id) {
		Article article = articleDAO.getArticleById(id);
		File file = new File(CommonConstant.ARTICLES_DIR+"/"+article.getContentPath());
		if(file.exists()&&file.isFile()) {
			InputStreamReader in = null;
			BufferedReader reader = null; 
			try {
				in = new InputStreamReader(new FileInputStream(file), "UTF-8");
				String line = "";
				String content = "";
				reader = new BufferedReader(in);
				while((line=reader.readLine())!=null) {
					content += line+"\r\n";
				}
				in.close();
				article.setContent(content);
				return article;
			} catch(IOException e) {
				Log log = LogFactory.getLog(ArticleServiceImpl.class);
	            log.error(e.getMessage(), e);
				if(in!=null) {
					try {
						in.close();
					} catch (IOException e1) {}
				}
			}
		}
		return null;
	}

	@Override
	public void delete(String id) {
		Article article = articleDAO.getArticleById(id);
		if(article!=null) {
			File file = new File(CommonConstant.ARTICLES_DIR+"/"+article.getContentPath());
			if(file.exists()&&file.isFile()) {
				file.delete();
			}
			articleDAO.delete(article);
		}
	}

	@Override
	public Page<Article> getAllAricle(int pageNo, int pageSize) {
		Page<Article> page = articleDAO.getAllArticle(pageNo, pageSize);
		List<Article> list = page.getData();
		List<Article> articles = new ArrayList<Article>();
		String dir = CommonConstant.ARTICLES_DIR+"/";
		for(Article article : list) {
			File file = new File(dir+article.getContentPath());
			if(file.exists()&&file.isFile()) {
				InputStreamReader in = null;
				BufferedReader reader = null; 
				try {
					in = new InputStreamReader(new FileInputStream(file), "UTF-8");
					String line = "";
					String content = "";
					reader = new BufferedReader(in);
					while((line=reader.readLine())!=null&&content.length()<300) {
						line = line.replaceAll("<.*?>", "");
						content += line+"\r\n";
					}
					content += "...";
					in.close();
					article.setContent(content);
				} catch(IOException e) {
					Log log = LogFactory.getLog(ArticleServiceImpl.class);
		            log.error(e.getMessage(), e);
					if(in!=null) {
						try {
							in.close();
						} catch (IOException e1) {}
					}
				}
			}
			articles.add(article);
		}
		page.setData(articles);
		return page;
	}

	@Override
	public Page<Article> getAllAricleWithoutContent(int pageNo, int pageSize) {
		Page<Article> page = articleDAO.getAllArticle(pageNo, pageSize);
		return page;
	}

	@Override
	public List<Article> getAllDrafts() {
		return articleDAO.getAllDraft();
	}

	@Override
	public Page<Article> getArticleByTypeAndTitle(int articleTypeId,
			String title, int pageNo, int pageSize) {
		return articleDAO.selectArticleByTypeAndTitle(articleTypeId, title, pageNo, pageSize);
	}

	@Override
	public void updateAllowComment(String articleId, boolean allowComment) {
		Article article = articleDAO.getArticleById(articleId);
		if(article!=null) {
			article.setAllowComment(allowComment);
			articleDAO.update(article);
		}
	}

	@Override
	public void updateIsTop(String articleId, boolean isTop) {
		Article article = articleDAO.getArticleById(articleId);
		if(article!=null) {
			article.setIsTop(isTop);
			articleDAO.update(article);
		}
	}

	@Override
	public Page<Article> getArticleByTypeId(int typeId, int pageNo, int pageSize) {
		Page<Article> page = articleDAO.selectArticleByTypeId(typeId, pageNo, pageSize);
		List<Article> list = page.getData();
		List<Article> articles = new ArrayList<Article>();
		String dir = CommonConstant.ARTICLES_DIR+"/";
		for(Article article : list) {
			File file = new File(dir+article.getContentPath());
			if(file.exists()&&file.isFile()) {
				InputStreamReader in = null;
				BufferedReader reader = null; 
				try {
					in = new InputStreamReader(new FileInputStream(file), "UTF-8");
					String line = "";
					String content = "";
					reader = new BufferedReader(in);
					while((line=reader.readLine())!=null&&content.length()<300) {
						line = line.replaceAll("<.*?>", "");
						content += line+"\r\n";
					}
					content += "...";
					in.close();
					article.setContent(content);
				} catch(IOException e) {
					Log log = LogFactory.getLog(ArticleServiceImpl.class);
		            log.error(e.getMessage(), e);
					if(in!=null) {
						try {
							in.close();
						} catch (IOException e1) {}
					}
				}
			}
			articles.add(article);
		}
		page.setData(articles);
		return page;
	}

	@Override
	public List<Article> getReadedTop10() {
		return articleDAO.seletArticleTop10();
	}

	@Override
	public List<Article> getAssociate5Articles(Article article) {
		return articleDAO.getAssociate5Articles(article);
	}

	@Override
	public Article getNextArticle(Article article) {
		return articleDAO.getNextArticle(article);
	}

	@Override
	public Article getPreviousArticle(Article article) {
		return articleDAO.getPreviousArticle(article);
	}
}
