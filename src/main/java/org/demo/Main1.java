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
import org.demo.domain.Month;
import org.demo.domain.Order;
import org.demo.domain.User;
import org.demo.persistence.CarDao;
import org.demo.persistence.MonthDao;
import org.demo.persistence.OrderDao;
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
		Car car = new Car();
		car.setId(id);
		car.setName(name);
		car.setPrice(price);
		car.setOk(ok);
		return car ;
	}
	private static Month buildMonth(int year, int month, String name, boolean open) {
		Month o = new Month();
		o.setYear(year);
		o.setMonth(month);
		o.setName(name);
		o.setOpen(open);
		return o ;
	}	
	private static Order buildOrder(int id,  String name, BigDecimal price, String customer, int year, int month) {
		Order o = new Order();
		o.setId(id);
		o.setName(name);
		o.setPrice(price);
		o.setCustomer(customer);
		o.setYear(year);
		o.setMonth(month);
		return o ;
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
		
		System.out.println("===========================================");
		testsWithUser(sqlSession);
		System.out.println("===========================================");
		testsWithCar(sqlSession);
		System.out.println("===========================================");
		testsWithMonth(sqlSession);
		System.out.println("===========================================");
		testsWithOrder(sqlSession);
		System.out.println("===========================================");
	}

	//----------------------------------------------------------------------------------------
	// USER
	//----------------------------------------------------------------------------------------
	private static void testsWithUser(SqlSession sqlSession) {
		// Get MAPPER :   <T> T getMapper(Class<T> type);
		UserDao userDao = sqlSession.getMapper(UserDao.class);

		int r;
		/*
		 * Mapper scope : See : https://mybatis.org/mybatis-3/getting-started.html
		 * The best scope for mapper instances is method scope. 
		 * That is, they should be requested within the method that they are used, and then be discarded. 
		 * They do not need to be closed explicitly
		 */
		insertUsers(userDao);
		insertList(userDao);
		printAllUsers(userDao);
		
		r = userDao.delete(3);
		System.out.println("delete(3) ret : " + r ) ;  // 1 : found => deleted
		r = userDao.delete(3);
		System.out.println("delete(3) ret : " + r ) ;  // 0 : not found => not deleted
		r = userDao.deleteByName("Name5");
		System.out.println("deleteByName ret : " + r ) ; 
		r = userDao.deleteByNameAndMail("Name6", "m6@foo.com");
		System.out.println("deleteByNameAndMail ret : " + r ) ; 
		
		r = userDao.update(buildUser(4, "NewName4", "secret4", "NewName4@foo.com") );
		System.out.println("update ret : " + r ) ; // 1 : found => updated
		r = userDao.update(buildUser(99, "Name99", "secret99", null) );
		System.out.println("update ret : " + r ) ; // 0 : not found => not updated
		

		System.out.println("count : " + userDao.count() ) ;
		printAllUsers(userDao);
		
		System.out.println("findByIdBetween(7, 20) : " ) ;
		for ( User u : userDao.findByIdBetween(7, 20) ) {
			System.out.println(" . " + u);
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

	//----------------------------------------------------------------------------------------
	// CAR
	//----------------------------------------------------------------------------------------
	private static void testsWithCar(SqlSession sqlSession) {
		CarDao  dao = sqlSession.getMapper(CarDao.class);
		insertCars(dao);
		updateCars(dao);
		findCars(dao);
		printAllCars(dao);
		findByIdBetween(dao, 1, 3); 
		deleteCars(dao);
		printAllCars(dao);
	}
	
	//----------------------------------------------------------------------------------------
	private static void insertCars(CarDao dao) {
		dao.insert( buildCar(1, "Aaaaa", BigDecimal.valueOf(25000), true));
		dao.insert( buildCar(2, "Bbbb",  BigDecimal.valueOf(12500), false) );
		dao.insert( buildCar(3, "Cccc",  BigDecimal.valueOf(33000), false) );
		dao.insert( buildCar(4, "Dddd",  BigDecimal.valueOf(44000), false) );
	}
	private static void updateCars(CarDao dao) {
		dao.update( buildCar(1, "Aa updated", BigDecimal.valueOf(25000), true));
		dao.update( buildCar(2, "Bb updated", BigDecimal.valueOf(12500), false) );
		dao.update( buildCar(101, "Xx updated", BigDecimal.valueOf(33000), false) );
		dao.update( buildCar(102, "Yy updated", BigDecimal.valueOf(44000), false) );
	}
	
	private static void findCars(CarDao dao) {
		Car car = dao.findById(1);
		System.out.println(" find car 1 : " + car);
	}

	private static void findByIdBetween(CarDao dao, int id1, int id2) {
		System.out.println(" findByIdBetween(" + id1 + "," + id2 + ") : " );
		for ( Car o : dao.findByIdBetween(id1, id2) ) {
			System.out.println(" . " + o);
		}
	}

	private static void deleteCars(CarDao dao) {
		dao.delete(1);
		dao.delete(3);
	}
	
	private static void printAllCars(CarDao dao) {
		System.out.println("count : " + dao.count() ) ;
		List<Car> all = dao.findAll();
		for ( Car x : all ) {
			System.out.println(" . " + x);
		}
	}
	
	//----------------------------------------------------------------------------------------
	// MONTH
	//----------------------------------------------------------------------------------------
	private static void testsWithMonth(SqlSession sqlSession) {
		MonthDao  dao = sqlSession.getMapper(MonthDao.class);
		insertMonths(dao);
		findMonths(dao);
		printAllMonths(dao);
		deleteMonths(dao);
		printAllMonths(dao);
	}
	
	//----------------------------------------------------------------------------------------
	private static void insertMonths(MonthDao dao) {
		dao.insert( buildMonth(2020, 01, "Jan 2020", false) );
		dao.insert( buildMonth(2020, 02, "Feb 2020", false)  );
		dao.insert( buildMonth(2021, 01, "Jan 2021", true) );
		dao.insert( buildMonth(2021, 02, "Feb 2021", true) );
	}
	private static void deleteMonths(MonthDao dao) {
		dao.delete( 2020, 01 );
		dao.delete( 2020, 02 );
	}
	private static void findMonths(MonthDao dao) {
		System.out.println(" find month 2020/01 : " + dao.findById(2020, 1));
		System.out.println(" find month 2020/12 : " + dao.findById(2020, 12));
	}
	private static void printAllMonths(MonthDao dao) {
		System.out.println("count : " + dao.count() ) ;
		List<Month> all = dao.findAll();
		for ( Month m : all ) {
			System.out.println(" . " + m);
			for ( Order o : m.getOrders() ) {
				System.out.println("   . Order : " + o);
				
			}
		}
	}

	//----------------------------------------------------------------------------------------
	// ORDER
	//----------------------------------------------------------------------------------------
	private static void testsWithOrder(SqlSession sqlSession) {
		OrderDao  dao = sqlSession.getMapper(OrderDao.class);
		insertOrders(dao);
		printAllOrders(dao);
//		printAllCars(dao);
//		deleteMonths(dao);
//		printAllCars(dao);

		MonthDao  monthDao = sqlSession.getMapper(MonthDao.class);
		printAllMonths(monthDao);
	}
	
	//----------------------------------------------------------------------------------------
	private static void insertOrders(OrderDao dao) {
		dao.insert( buildOrder(1, "order1", BigDecimal.valueOf(1100), "customerA", 2021, 01) );
		dao.insert( buildOrder(2, "order2", BigDecimal.valueOf(2200), "customerB", 2021, 01) );
		dao.insert( buildOrder(3, "order3", BigDecimal.valueOf(3300), "customerC", 2021, 02) );
		//dao.insert( buildOrder(9, "order9", BigDecimal.valueOf(9900), "customerC", 2023, 02) ); // FK violation
	}
	private static void printAllOrders(OrderDao dao) {
		System.out.println("count : " + dao.count() ) ;
		List<Order> all = dao.findAll();
		for ( Order x : all ) {
			System.out.println(" . " + x);
		}
	}
}
