-- 修正article_types为自增长
ALTER TABLE article_types CHANGE `id` `id` INT NOT NULL AUTO_INCREMENT;
ALTER TABLE article_types AUTO_INCREMENT=11;