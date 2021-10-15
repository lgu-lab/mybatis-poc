package org.demo.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.FetchType;
import org.demo.domain.Comment;
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
public interface MonthDao {
	
	static final String TABLE = "months" ; 

	@Select("select count(*) from " + TABLE)
	long count();
	
	// without @Param : "Available parameters are [arg1, arg0, param1, param2]"
	@Select("select * from " + TABLE + " where year = #{param1} AND month = #{param2}")
	@Results(id="MonthResult", value = {
		@Result( property = "commentId", column = "comment_id"),
		@Result( property = "orderList", // field name to populate in Month class
				 //javaType = List.class, 
				column = "{param1=year, param2=month}", // column(s) to use in MONTH table : 'year' and 'month'
				// param names usable in @Select in "orderList" method : 'param1' and 'param2'
				// {  param-name in called method @Select  =  column-name in the current table  }
				 many = @Many(select = "findOrderList", fetchType = FetchType.DEFAULT) )
		, 
		@Result( property = "refComment", // field name to populate in Month class
				column = "comment_id", // column(s) to use in MONTH table
				one = @One(select = "findRefComment") ), 
		@Result( property = "refYear", // field name to populate in Month class
				column = "year", // column(s) to use in MONTH table
				one = @One(select = "findRefYear", fetchType = FetchType.DEFAULT) ) 
		})
	public Month findById(int year, int month); 
	
	@Select("select * from " + TABLE )
	@ResultMap("MonthResult") // reuse the same "result map" as defined in "findById"
	public List<Month> findAll();

	@Insert("insert into " + TABLE + "(" +
			"year, month, name, open, comment_id" +
			") values (" +
			"#{year}, #{month}, #{name}, #{open}, #{commentId}" +
			")"
	)
	public void insert(Month o);

	@Update("update " + TABLE + " set " +
			"name = #{name}," +
			"open = #{open}" +
			"comment_id = #{commentId}" +
			"where  year = #{year} AND month = #{month} " )
	public int update(Month o);

	// without @Param : "Available parameters are [arg1, arg0, param1, param2]"
	@Delete("delete from " + TABLE + 
			" where  year = #{arg0} AND month = #{arg1} ")
	public int delete(int p1, int p2);
	
	//--------------------------------------------------------------------------
	// OneToMany relation
	//--------------------------------------------------------------------------
	@Select("SELECT * FROM ORDERS " +
			" WHERE year = #{param1} AND month = #{param2}") // OK if column = "{param1=year, param2=month}"
//			" WHERE year = #{foo} AND month = #{bar}") // no error ! (nothing found)
//			" WHERE year = #{year} AND month = #{month}") // works only if : column = "{year=year, month=month}" in @Result
//	List<Order> findOrderList(@Param("year") int year, @Param("month") int month);
	List<Order> findOrderList(int year, int month); // ok param is defined in @Result
	
	//--------------------------------------------------------------------------
	// ManyToOne relation
	//--------------------------------------------------------------------------
	@Select("SELECT * FROM years WHERE year = #{param1}")
	Year findRefYear(int year); 

	@Select("SELECT * FROM comments WHERE id = #{param1}") 
	Comment findRefComment(int id); 
}
