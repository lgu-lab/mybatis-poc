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

CREATE TABLE `months`  (
	year  INT NOT NULL,
	month INT NOT NULL,
	name VARCHAR(40),
	open BOOLEAN ,
  PRIMARY KEY (`year`, `month`)
) ;
