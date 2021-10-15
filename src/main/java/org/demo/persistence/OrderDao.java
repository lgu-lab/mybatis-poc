package org.demo.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.demo.domain.Order;

/**
 * MyBatis Mapper with Java API 
 * see https://mybatis.org/mybatis-3/java-api.html
 * 
 * Each method name must be unique 
 *
 */
public interface OrderDao {
	
	static final String TABLE = "orders" ; 

	@Select("select count(*) from " + TABLE)
	long count();
	
	@Select("select * from " + TABLE )
	public List<Order> findAll();

	@Select("select * from " + TABLE + " where id = #{id}" )
	public Order findById(int id); 
	
	@Insert("insert into " + TABLE + "(" +
			"id,  name, customer, price , year, month " +
			") values (" +
			"#{id}, #{name}, #{customer}, #{price} , #{year}, #{month} " +
			")"
	)
	public void insert(Order o);

	@Update("update " + TABLE + " set " +
			"id = #{id}," +
			"name = #{name}," +
			"price = #{price}," +
			"customer = #{customer}" +
			"year = #{year}" +
			"month = #{month}" +
			"where  id = #{id} " )
	public int update(Order o);

	@Delete("delete from " + TABLE + 
			" where  id = #{param1} ")
	public int delete(int p1);
	
}
