create database Hana_Shop
go

use Hana_Shop
go 

create table Role(
roleId			varchar(10)		primary key not null,
roleName		varchar(50)		not null,
)
-- Create table
create table Users(
userId			varchar(40)		primary key not null,
roleId			varchar(10)		not null,
fullName		nvarchar(50)	not null,
birthDate		datetime		not null,
phoneNumber		varchar(15)		not null,
gender			bit				not null,
email			varchar(50)		null,
address			nvarchar(50)	null,
registeredDate	datetime		not null,
password		varchar(MAX)	null,
status			int				not null
)


create table Category(
categoryId		varchar(40)		primary key not null,
categoryName	nvarchar(50)	not null,
)

create table Food(
foodId			varchar(40)		primary key not null,
categoryId		varchar(40)		not null,
foodName		nvarchar(50)	not null,
foodPrice		float			not null,
foodQuantity	int				not null,
imageUrl		varchar(MAX)	not null,
foodDescription	nvarchar(100)	not null,
createDate		datetime		not null,
status			bit				not null
)

create table Action(
actionId		varchar(40)		primary key not null,
foodId			varchar(40)		not null,
updateUser		varchar(40)		not null,
typeAction		varchar(50)		not null,
updateDate		datetime        not null,
status			bit				not null
)


create table Orders (
orderId			varchar(40)		primary key not null,
userId			varchar(40)		not null,
fullName		nvarchar(50)	not null,
phoneNumber		varchar(15)		not null,
address			nvarchar(50)	not null,
createAt		datetime		not null,
orderTotal		float			not null,
status			int				not null
)

create table OrderDetail(
orderItemId		varchar(40)		primary key not null,
foodId		varchar(40)		not null,
orderId			varchar(40)		not null,
foodQuantity	int				not null,
foodPrice	float			not null,
createAt		datetime		not null,
status			bit				not null
)


--Create relationshop with table

--1. Food, Category
ALTER TABLE Food ADD CONSTRAINT q1
FOREIGN KEY (categoryId) references Category(categoryId)
--2. User vs Role
ALTER TABLE Users ADD CONSTRAINT q2
FOREIGN KEY (roleId) references Role(roleId)
--3. Order vs Users
ALTER TABLE Orders ADD CONSTRAINT q3
FOREIGN KEY (userId) references Users(userId)
--4. Order detail vs Orders, Food
ALTER TABLE OrderDetail ADD CONSTRAINT q4
FOREIGN KEY (orderId) references Orders(orderId)
ALTER TABLE OrderDetail ADD CONSTRAINT q5
FOREIGN KEY (foodId) references Food(foodId)
--5. Action, Users, Food
ALTER TABLE Action ADD CONSTRAINT q6
FOREIGN KEY (updateUser) references Users(userId)
ALTER TABLE Action ADD CONSTRAINT q7
FOREIGN KEY (foodId) references Food(foodId)

--insert values into table Role
insert into Role(roleId, roleName)
		  values('ROL_1', 'Admin'),
				('ROL_2', 'User')

--insert values into table Users
insert into Users(userId, roleId, fullName, birthDate, phoneNumber, gender, email, address, registeredDate, password, status)
		   values('SE141026', 'ROL_2', 'Huyen Nguyen', '2000-06-06', '0902472118', 0, null, N'Mộ Đức, tp.Quảng Ngãi', '2021-01-06', 'abc123', 1),
				 ('SE141028', 'ROL_1', 'Danh Pham', '2000-08-18', '0902472119', 1, null, N'Bình Tân, tp.Hồ Chí Minh', '2020-01-06', 'abc123', 1),
				 ('101533064830923138345', 'ROL_2', 'Huyen Ho', '1999-06-06', '0902472124', 1, 'danhpvse141028@fpt.edu.vn', N'Quận 9, tp.Hồ Chí Minh', '2021-01-01', null, 1),
				 ('101376767150318749528', 'ROL_1', 'Danh Nguyen', '2000-08-20', '0904567899', 1, 'danhskipper18@gmail.com', N'Nghĩa Thương, Quảng Ngãi', '2020-08-18', null, 1)
			
--insert values into table Category
insert into Category(categoryId, categoryName)
              values('TYPE_1', 'Food'),
					('TYPE_2', 'Drink')

--insert values into table Food
insert into Food(foodId, categoryId, foodName, foodPrice, foodQuantity, imageUrl, foodDescription, createDate, status)
		  values('FOOD-00001', 'TYPE_2', N'Coca cola', 2, 50, N'coca-cola.jpg', N'1 Coca cola', '2021-01-11 11:09:52.247', 1),
		        ('FOOD-00002', 'TYPE_1', N'Chicken fried', 5, 100, N'chicken-fry.jpg', N'1 Chicken fry', '2021-01-11 11:09:52.247', 1),
				('FOOD-00003', 'TYPE_2', N'Coffee', 3, 60, N'coffee.jpg', N'1 Coffee', '2021-01-11 11:09:52.247', 1),
				('FOOD-00004', 'TYPE_1', N'Chicken rice', 8, 110, N'chicken-rice.jpg', N'1 Chicken rice', '2021-01-11 11:09:52.247', 1),
				('FOOD-00005', 'TYPE_2', N'Trà tắc', 2, 70, N'tra-tac.jpg', N'1 Coca cola', '2021-01-11 11:09:52.247', 1),
				('FOOD-00006', 'TYPE_1', N'Pizza', 6, 120, N'kfc-pizza.jpg', N'1 Coca cola', '2021-01-11 11:09:52.247', 1),
				('FOOD-00007', 'TYPE_2', N'Trà đào', 4, 80, N'tra-dao.jpg', N'2 Ly Trà đào', '2021-01-11 11:09:52.247', 1),
				('FOOD-00008', 'TYPE_1', N'Hambuger', 8, 130, N'hambuger.jpg', N'2 Bánh Hambuger', '2021-01-11 11:09:52.247', 1),
				('FOOD-00009', 'TYPE_2', N'Trà olong', 4, 90, N'tea.jpg', N'2 Ly trà olong', '2021-01-11 11:09:52.247', 1),
				('FOOD-00010', 'TYPE_1', N'Chizza', 10, 140, N'kfc-pizza.jpg', N'2 Chizza', '2021-01-11 11:09:52.247', 1),
				('FOOD-00011', 'TYPE_2', N'Coca cola', 10, 100, N'coca-cola.jpg', N'5 Coca cola', '2021-01-11 00:00:00.000', 1),
				('FOOD-00012', 'TYPE_1', N'Hambuger', 10, 150, N'hambuger.jpg', N'2 Bánh Hambuger', '2021-01-12 00:00:00.000', 1),
				('FOOD-00013', 'TYPE_2', N'Trà Highland', 6, 110, N'tea.jpg', N'2 Ly trà highland', '2021-01-12 23:06:38.020', 1),
				('FOOD-00014', 'TYPE_1', N'Chicken fried', 12, 160, N'chicken-fry.jpg', N'3 Chicken fried', '2021-01-12 23:07:17.270', 1),
				('FOOD-00015', 'TYPE_2', N'Trà đào', 4, 120, N'tra-dao.jpg', N'1 2 Ly trà đào', '2021-01-15 11:32:41.183', 1),
				('FOOD-00016', 'TYPE_1', N'Pizza', 6, 170, N'kfc-pizza.jpg', N'2 Bánh Pizza', '2021-01-19 13:25:18.953', 1),
				('FOOD-00017', 'TYPE_2', N'Pepsi', 4, 130, N'pepsi.jpg', N'2 Lon Pepsi', '2021-01-19 13:28:11.423', 1),
				('FOOD-00018', 'TYPE_1', N'Chizza', 9, 180, N'kfc-pizza.jpg', N'3 Bánh Chizza', '2021-01-19 13:36:27.187', 1),
				('FOOD-00019', 'TYPE_2', N'Seven up', 2, 140, N'seven-up.jpg', N'1 Ly Seven-up', '2021-01-19 13:36:59.760', 1),
				('FOOD-00020', 'TYPE_1', N'Chicken rice', 14, 190, N'chicken-rice.jpg', N'1 Đĩa Chicken rice, bonus Coca', '2021-01-19 13:38:03.940', 1),
				('FOOD-00021', 'TYPE_2', N'Trà tắc', 5, 150, N'tra-tac.jpg', N'1 Ly trà tắc', '2021-01-19 23:37:17.393', 1),
				('FOOD-00022', 'TYPE_1', N'Fish fried', 8, 200, N'fish-fry.jpg', N'1 Đĩa Fish Fried, bonus pepsi', '2021-01-20 11:37:30.400', 1),
				('FOOD-00023', 'TYPE_2', N'Coca cola', 5, 160, N'coca-cola.jpg', N'2 Coca cola', '2021-01-20 14:14:02.783', 1),
				('FOOD-00024', 'TYPE_1', N'Hambuger', 12, 210, N'hambuger.jpg', N'2 Bánh Hambuger, bonus 2 ly 7-up', '2021-01-21 08:27:12.050', 1)