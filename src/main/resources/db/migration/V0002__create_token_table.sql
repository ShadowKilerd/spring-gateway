CREATE TABLE `token` (
  `id` varchar(50) not null primary key,
  `token` text,
  `user_id` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;