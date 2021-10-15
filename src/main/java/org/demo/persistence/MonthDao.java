package org.demo.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.demo.domain.Car;
import org.demo.domain.Month;

/**
 * MyBatis Mapper with Java API 
 * see https://mybatis.org/mybatis-3/java-api.html
 * 
 * Each method name must be unique 
 *
 */
public interface MonthDao {
	
	static final String TABLE = "months" ; 

	@Select("select * from " + TABLE )
	public List<Month> findAll();

	// without @Param : "Available parameters are [arg1, arg0, param1, param2]"
	@Select("select * from " + TABLE + " where year = #{param1} AND month = #{param2}")
	public Month findById(int year, int month); 
	
	@Select("select count(*) from " + TABLE)
	long count();
	
	@Insert("insert into " + TABLE + "(" +
			"year, month, name, open" +
			") values (" +
			"#{year}, #{month}, #{name}, #{open}" +
			")"
	)
	public void insert(Month o);

	@Update("update " + TABLE + " set " +
			"name = #{name}," +
			"open = #{open}" +
			"where  year = #{year} AND month = #{month} " )
	public int update(Month o);

	// without @Param : "Available parameters are [arg1, arg0, param1, param2]"
	@Delete("delete from " + TABLE + 
			" where  year = #{arg0} AND month = #{arg1} ")
	public int delete(int p1, int p2);
	
}
