package com.herokuapp.lzqwebsoft.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.herokuapp.lzqwebsoft.dao.ArticleTypeDAO;
import com.herokuapp.lzqwebsoft.pojo.ArticleType;

@Service("articleTypeService")
public class ArticleTypeServiceImpl implements ArticleTypeService {
	
	@Autowired
	private ArticleTypeDAO articleTypeDAO;

	@Override
	public List<ArticleType> getAllArticleType() {
		return articleTypeDAO.getAllArticleType();
	}

	@Override
	public ArticleType save(ArticleType type) {
		type.setCreateAt(new Date());
		type.setUpdateAt(new Date());
		return articleTypeDAO.save(type);
	}

	@Override
	public void delete(String idStr) {
		try {
			int id = Integer.parseInt(idStr);
			ArticleType deletedType = articleTypeDAO.getArticleTypeById(id);
			if(deletedType!=null)
			   articleTypeDAO.delete(deletedType);
		} catch(NumberFormatException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void disable(String idStr, boolean disable) {
		try {
			int id = Integer.parseInt(idStr);
			ArticleType articleType = articleTypeDAO.getArticleTypeById(id);
			if(articleType!=null) {
				articleType.setDisable(disable);
				articleType.setUpdateAt(new Date());
				articleTypeDAO.update(articleType);
			}
		} catch(NumberFormatException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(String idStr, String typeName) {
		try {
			int id = Integer.parseInt(idStr);
			ArticleType articleType = articleTypeDAO.getArticleTypeById(id);
			if(articleType!=null) {
				articleType.setName(typeName);
				articleType.setUpdateAt(new Date());
				articleTypeDAO.update(articleType);
			}
		} catch(NumberFormatException e) {
			e.printStackTrace();
		}
	}
}
