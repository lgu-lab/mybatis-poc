CREATE TABLE `users`  (
  `id` INT(10) NOT NULL,
  `username` VARCHAR(20) NOT NULL,
  `password` VARCHAR(20) ,
  `email` VARCHAR(50) ,
  PRIMARY KEY (`id`)
) ;

CREATE TABLE `cars`  (
  `car_id` INT(10) NOT NULL,
  `car_name` VARCHAR(40),
  `car_price` DECIMAL ,
  `car_ok` BOOLEAN ,
  PRIMARY KEY (`car_id`)
) ;

CREATE TABLE `years`  (
	year  INT NOT NULL,
	name VARCHAR(40),
  PRIMARY KEY (`year`)
) ;

CREATE TABLE `comments`  (
	id   INT NOT NULL,
	text VARCHAR(40),
  PRIMARY KEY (`id`)
) ;

CREATE TABLE `months`  (
	year  INT NOT NULL,
	month INT NOT NULL,
	name VARCHAR(40),
	open BOOLEAN ,
	comment_id INT ,
  PRIMARY KEY (`year`, `month`)
) ;

CREATE TABLE `orders`  (
	id   INT NOT NULL,
	name VARCHAR(40),
	customer VARCHAR(40),
    price DECIMAL ,
	year  INT ,
	month INT ,
  PRIMARY KEY (id),
  foreign key (year, month) references months(year, month)
) ;
