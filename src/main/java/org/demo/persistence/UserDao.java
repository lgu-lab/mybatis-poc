package org.demo.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.demo.domain.User;

/**
 * MyBatis Mapper with Java API 
 * see https://mybatis.org/mybatis-3/java-api.html
 * 
 * Each method name must be unique 
 *
 */
public interface UserDao {
	
	static final String TABLE = "users" ; 

	@Select("select * from " + TABLE )
	public List<User> findAll();

	@Select("select * from " + TABLE + " where id = #{id}")
	public User findById(Integer id);
	
	@Select("select * from " + TABLE + " where id >= #{id1} and id <= #{id2}")
	public List<User> findByIdBetween(@Param("id1")int id1, @Param("id2")int id2); 
	// many arguments => @Param is mandatory  
	
	@Select("select count(*) from " + TABLE)
	long count();
	
	@Insert({"insert into " + TABLE ,
		"(id, username, password, email)",
		"values(#{id}, #{username}, #{password}, #{email})"
	} )
	public void insert(User user);

	@Update({"update " + TABLE + " set " ,
				"username=#{username},",
				"password=#{password},",
				"email=#{email} ",
			"where id=#{id}" })
	public int update(User user);

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
	public int insertList(List<User> list);
	// ### SQL: INSERT INTO users (username, password, email) VALUES   (?, ?, ?) , (?, ?, ?) , (?, ?, ?) , etc
	
}
