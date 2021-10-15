package org.demo.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.demo.domain.Month;
import org.demo.domain.Order;
import org.demo.domain.Year;

/**
 * MyBatis Mapper with Java API 
 * see https://mybatis.org/mybatis-3/java-api.html
 * 
 * Each method name must be unique 
 *
 */
public interface YearDao {
	
	static final String TABLE = "years" ; 

	@Select("select count(*) from " + TABLE)
	long count();
	
	@Select("select * from " + TABLE + " where year = #{param1}")
	public Year findById(int year); 
	
	@Select("select * from " + TABLE )
	public List<Year> findAll();

	@Insert("insert into " + TABLE + "(" +
			"year, name" +
			") values (" +
			"#{year}, #{name}" +
			")"
	)
	public void insert(Year o);

	@Update("update " + TABLE + " set " +
			"name = #{name}," +
			"where  year = #{param1} " )
	public int update(Year o);

	@Delete("delete from " + TABLE + " where year = #{param1}")
	public int delete(int year);
	
}
