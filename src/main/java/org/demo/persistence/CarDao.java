package org.demo.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
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

	@Select("select * from " + TABLE )
	public List<Car> findAll();

	@Select("select * from " + TABLE + " where car_id = #{id}")
	public Car findById(Integer idxxx); // single argument => any name
	
	@Select("select * from " + TABLE + " where id >= #{id1} and id <= #{id2}")
	public List<Car> findByIdBetween(@Param("id1")int id1, @Param("id2")int id2); 
	// many arguments => @Param is mandatory  
	
	@Select("select count(*) from " + TABLE)
	long count();
	
	@Insert({"insert into " + TABLE ,
		"(car_id, car_name, car_price, car_ok)",
		"values(#{id}, #{name}, #{price}, #{ok})"
	} )
	public void insert(Car car);

	@Update({"update " + TABLE + " set " ,
				"username = #{username},",
				"password = #{password},",
				"email = #{email} ",
			"where id = #{id}" })
	public int update(Car user);

	@Delete("delete from " + TABLE + " where id = #{id}")
	public int delete(Integer id);
	
	@Delete("delete from " + TABLE + " where username = #{x}") // single argument => name is not meaningful 
	public int deleteByName(String foo); // single argument => @Param is not mandatory 

	@Delete("delete from " + TABLE + " where username = #{name} and email = #{mail}")
	public int deleteByNameAndMail(@Param("name") String name, @Param("mail") String mail);
	// many arguments => @Param is mandatory  
	
	/**
	 * @param list
	 * @return number of rows inserted
	 */
	@Insert({
		"<script>",
        "INSERT INTO ", TABLE,
            "(id, username, password, email)",
        "VALUES" ,
//            "<foreach item='i' collection='list' open='' separator=',' close=''>",
            "<foreach item='item' collection='list' separator=',' >",
                "(",
                    "#{item.id},",
                    "#{item.username},",
                    "#{item.password},",
                    "#{item.email}",
                ")",
            "</foreach>",
    	"</script>"})
	public int insertList(List<Car> list);
	// ### SQL: INSERT INTO users (username, password, email) VALUES   (?, ?, ?) , (?, ?, ?) , (?, ?, ?) , etc
	
}
