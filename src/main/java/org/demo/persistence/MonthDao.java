package org.demo.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.demo.domain.Month;
import org.demo.domain.Order;

/**
 * MyBatis Mapper with Java API 
 * see https://mybatis.org/mybatis-3/java-api.html
 * 
 * Each method name must be unique 
 *
 */
public interface MonthDao {
	
	static final String TABLE = "months" ; 

	// without @Param : "Available parameters are [arg1, arg0, param1, param2]"
	@Select("select * from " + TABLE + " where year = #{param1} AND month = #{param2}")
	@Results(id="MonthResult", value = {
			@Result( property = "orderList", 
					 //javaType = List.class, 
					 many = @Many(select = "findOrderList"),
					column = "{param1=year, param2=month}" // param names usable in @Select in "orderList" method
					// {  param-name in called method @Select  =  column-name in the current table  }
			) 
			})
	public Month findById(int year, int month); 
	
	@Select("select * from " + TABLE )
//	@Results(value = {
//			@Result( property = "orderList", 
//					 //javaType = List.class, 
//					 many = @Many(select = "findOrderList"),
//					column = "{param1=year, param2=month}"
//			) 
//			})
	@ResultMap("MonthResult") // reuse the same "result map" as defined in "findById"
	public List<Month> findAll();

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
	
	//--------------------------------------------------------------------------
	// OneToMany relation
	//--------------------------------------------------------------------------
	@Select("SELECT * FROM ORDERS " +
			" WHERE year = #{param1} AND month = #{param2}") // OK if column = "{param1=year, param2=month}"
//			" WHERE year = #{foo} AND month = #{bar}") // no error ! (nothing found)
//			" WHERE year = #{year} AND month = #{month}") // works only if : column = "{year=year, month=month}" in @Result
//	List<Order> findOrderList(@Param("year") int year, @Param("month") int month);
	List<Order> findOrderList(int year, int month); // ok param is defined in @Result
	
}
