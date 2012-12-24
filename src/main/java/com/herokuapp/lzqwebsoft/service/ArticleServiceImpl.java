package com.herokuapp.lzqwebsoft.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.herokuapp.lzqwebsoft.dao.ArticleDAO;
import com.herokuapp.lzqwebsoft.dao.ArticleTypeDAO;
import com.herokuapp.lzqwebsoft.pojo.Article;
import com.herokuapp.lzqwebsoft.pojo.ArticleType;
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
		URL url = Article.class.getResource(CommonConstant.ARTICLES_DIR);
		OutputStreamWriter out = null;
		File file = new File(url.getPath()+filename.toString());
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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
		
		URL url = Article.class.getResource(CommonConstant.ARTICLES_DIR);
		OutputStreamWriter out = null;
		File file = new File(url.getPath()+article.getContentPath());
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
			articleDAO.update(article);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
		URL url = Article.class.getResource(CommonConstant.ARTICLES_DIR);
		File file = new File(url.getPath()+article.getContentPath());
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
				e.printStackTrace();
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
			URL url = Article.class.getResource(CommonConstant.ARTICLES_DIR);
			File file = new File(url.getPath()+article.getContentPath());
			if(file.exists()&&file.isFile()) {
				file.delete();
			}
			articleDAO.delete(article);
		}
	}

	@Override
	public List<Article> getAllAricle() {
		List<Article> list = articleDAO.getAllArticle();
		List<Article> articles = new ArrayList<Article>();
		for(Article article : list) {
			URL url = Article.class.getResource(CommonConstant.ARTICLES_DIR);
			File file = new File(url.getPath()+article.getContentPath());
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
					e.printStackTrace();
					if(in!=null) {
						try {
							in.close();
						} catch (IOException e1) {}
					}
				}
			}
			articles.add(article);
		}
		return articles;
	}
}
