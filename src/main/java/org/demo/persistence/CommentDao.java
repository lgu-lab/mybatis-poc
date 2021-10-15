package org.demo.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.demo.domain.Comment;

/**
 * MyBatis Mapper with Java API 
 * see https://mybatis.org/mybatis-3/java-api.html
 * 
 * Each method name must be unique 
 *
 */
public interface CommentDao {
	
	static final String TABLE = "comments" ; 

	@Select("select count(*) from " + TABLE)
	long count();
	
	@Select("select * from " + TABLE + " where id = #{param1}")
	public Comment findById(int id); 
	
	@Select("select * from " + TABLE )
	public List<Comment> findAll();

	@Insert("insert into " + TABLE + "(" +
			"id, text" +
			") values (" +
			"#{id}, #{text}" +
			")"
	)
	public void insert(Comment o);

	@Update("update " + TABLE + " set " +
			"text = #{text}," +
			"where  id = #{param1} " )
	public int update(Comment o);

	@Delete("delete from " + TABLE + " where id = #{param1}")
	public int delete(int id);
	
}
