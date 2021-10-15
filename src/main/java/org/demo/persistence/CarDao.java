package org.demo.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.demo.domain.Car;

/**
 * MyBatis Mapper with Java API 
 * see https://mybatis.org/mybatis-3/java-api.html
 * 
 * Each method name must be unique 
 *
 */
public interface CarDao {
	
	static final String TABLE = "cars" ; 

	@Select("select count(*) from " + TABLE)
	long count();
	
	@Select("select * from " + TABLE )
	public List<Car> findAll();

	// single argument => single "#{..}" with any name  
	@Select("select * from " + TABLE + " where car_id = #{anyParamNameXxxx}")
	public Car findById(Integer foo); // Columns matching setter or private field name
	
	// single argument => single "#{..}" with any name  
	@Delete("delete from " + TABLE + " where car_id = #{anyParamName}")
	public int delete(Integer bar);
	
//	@Select("select * from " + TABLE + " where car_id >= #{id1} and car_id <= #{id2}")
//	public List<Car> findByIdBetween(@Param("id1")int id1, @Param("id2")int id2); 
//	// many arguments => @Param is mandatory  
	
	@Select("select * from " + TABLE + " where car_id >= #{param1} and car_id <= #{param2}")
	public List<Car> findByIdBetween(int id1, int id2); 
	
	@Insert({"insert into " + TABLE ,
		"(car_id, car_name, car_price, car_ok)",
		"values(#{id}, #{name}, #{price}, #{ok})"
	} )
	public void insert(Car o);

	@Update({"update " + TABLE + " set " ,
				"car_name = #{name},",
				"car_price = #{price},",
				"car_ok = #{ok} ",
			"where car_id = #{id}" })
	public int update(Car o);

}
