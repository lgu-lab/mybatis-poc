package org.demo;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.demo.domain.Car;
import org.demo.domain.User;
import org.demo.persistence.CarDao;
import org.demo.persistence.UserDao;

public class Main1 {

	public static void main(String[] args) throws IOException {
		InputStream is = Resources.getResourceAsStream("mybatis.xml");
		System.out.println("InputStream OK");
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
		is.close();
		System.out.println("SqlSessionFactory OK");
		
		Configuration conf = sqlSessionFactory.getConfiguration();
		System.out.println("conf.getDatabaseId : " + conf.getDatabaseId() );
		
		// 
		// SqlSessionFactory is used to open SqlSession
		//
		/*
		sqlSession = factory.openSession(true); // autocommit true/false
		sqlSession = factory.openSession(connection); 
		sqlSession = factory.openSession(TransactionIsolationLevel.NONE); 
		sqlSession = factory.openSession(TransactionIsolationLevel.READ_COMMITTED); 
		sqlSession = factory.openSession(TransactionIsolationLevel.READ_UNCOMMITTED); 
		sqlSession = factory.openSession(TransactionIsolationLevel.REPEATABLE_READ); 
		sqlSession = factory.openSession(TransactionIsolationLevel.SERIALIZABLE); 
		sqlSession = factory.openSession(TransactionIsolationLevel.SQL_SERVER_SNAPSHOT); 
		*/
		try (SqlSession sqlSession = sqlSessionFactory.openSession())  {
			System.out.println("SqlSession ready");
			process(sqlSession); 
			//sqlSession.close();
		}
	}
	
	private static User buildUser(Integer id, String username, String password, String email) {
		// Build user with only default constructor and setters
		User user = new User();
		user.setId(id);
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		return user ;
	}
	private static Car buildCar(Integer id, String name, BigDecimal price, boolean ok) {
		// Build user with only default constructor and setters
		Car car = new Car();
		//car.setId(id);
		car.setCarId(id);
		//car.setName(name);
		car.setCar_Name(name);
		car.setPrice(null);
		car.setOk(ok);
		return car ;
	}
	
	/**
	 * SqlSession scope : See : https://mybatis.org/mybatis-3/getting-started.html 
	 * Each thread should have its own instance of SqlSession. 
	 * Instances of SqlSession are not to be shared and are not thread safe. 
	 * Therefore the best scope is request or method scope. 
	 * Never keep references to a SqlSession instance in a static field or even an instance field of a class. 
	 * Never keep references to a SqlSession in any sort of managed scope, such as HttpSession of the Servlet framework. 
	 * If you're using a web framework of any sort, consider the SqlSession to follow a similar scope 
	 * to that of an HTTP request.
	 * @param sqlSession
	 */
	private static void process(SqlSession sqlSession) {
		
		LogFactory.useJdkLogging();
		
		sqlSession.getConfiguration();
		sqlSession.getConnection(); // Retrieves inner database connection (java.sql.Connection)
		sqlSession.clearCache();

		sqlSession.commit();
		sqlSession.commit(true); // force = true
		sqlSession.rollback();
		sqlSession.rollback(true); // force = true
		sqlSession.flushStatements();
		
		
		// Get MAPPER :   <T> T getMapper(Class<T> type);
		UserDao userDao = sqlSession.getMapper(UserDao.class);
		CarDao  carDao = sqlSession.getMapper(CarDao.class);

		/*
		 * Mapper scope : See : https://mybatis.org/mybatis-3/getting-started.html
		 * The best scope for mapper instances is method scope. 
		 * That is, they should be requested within the method that they are used, and then be discarded. 
		 * They do not need to be closed explicitly
		 */
		insertUsers(userDao);
		insertList(userDao);
		printAllUsers(userDao);
		
		userDao.delete(3);
		userDao.deleteByName("Name5");
		userDao.deleteByNameAndMail("Name6", "m6@foo.com");
		
		int r = userDao.update(buildUser(4, "NewName4", "secret4", "NewName4@foo.com") );
		System.out.println("update ret : " + r ) ; // 1 : found => updated
		r = userDao.update(buildUser(99, "Name99", "secret99", null) );
		System.out.println("update ret : " + r ) ; // 0 : not found => not updated
		

		System.out.println("count : " + userDao.count() ) ;
		printAllUsers(userDao);
		
		System.out.println("findByIdBetween(7, 20) : " ) ;
		for ( User u : userDao.findByIdBetween(7, 20) ) {
			System.out.println(" . " + u);
		}
		
		insertCars(carDao);
		findCars(carDao);
		printAllCars(carDao);
	}

	//----------------------------------------------------------------------------------------
	private static void insertCars(CarDao dao) {
		dao.insert( buildCar(1, "Aaaaa", BigDecimal.valueOf(25000), true));
		dao.insert( buildCar(2, "Bbbb",  BigDecimal.valueOf(12500), false) );
	}
	
	private static void findCars(CarDao dao) {
		Car car = dao.findById(1);
		System.out.println(" find car 1 : " + car);
	}
	
	private static void printAllCars(CarDao dao) {
		System.out.println("count : " + dao.count() ) ;
		List<Car> all = dao.findAll();
		for ( Car x : all ) {
			System.out.println(" . " + x);
		}
	}
	
	//----------------------------------------------------------------------------------------
	private static void insertUsers(UserDao userDao) {
		userDao.insert( buildUser(1, "Bob", "abc", "Bob@foo.com") );
		userDao.insert( buildUser(2, "Joe", "xyz", "Joe@foo.com") );
	}
	
	private static void insertList(UserDao userDao) {
		List<User> users = new LinkedList<>();
		for ( int i = 3 ; i < 12 ; i++ ) {
			User user = new User();
			user.setId(i);
			user.setUsername("Name"+i);
			user.setEmail("m"+i+"@foo.com");
			users.add(user);
		}
		int n = userDao.insertList(users);
		System.out.println("insertList : ret = " + n);
	}
	
	private static void printAllUsers(UserDao userDao) {
		System.out.println("count : " + userDao.count() ) ;
		List<User> all = userDao.findAll();
		for ( User u : all ) {
			System.out.println(" . " + u);
		}
	}
}
