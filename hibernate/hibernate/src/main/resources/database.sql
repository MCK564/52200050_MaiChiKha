-- Tạo cơ sở dữ liệu
CREATE DATABASE IF NOT EXISTS PhoneManufactureDB;
USE PhoneManufactureDB;

-- Tạo bảng cho Manufacture
CREATE TABLE IF NOT EXISTS Manufacture (
    ID VARCHAR(255) NOT NULL,
    Name VARCHAR(255) NOT NULL,
    Location VARCHAR(255),
    Employee INT,
    PRIMARY KEY (ID)
    );

-- Tạo bảng cho Phone
CREATE TABLE IF NOT EXISTS Phone (
    ID VARCHAR(255) NOT NULL,
    Name VARCHAR(255) NOT NULL,
    Price INT NOT NULL,
    Color VARCHAR(255),
    Country VARCHAR(255),
    Quantity INT NOT NULL,
    ManufactureID VARCHAR(255),
    PRIMARY KEY (ID),
    FOREIGN KEY (ManufactureID) REFERENCES Manufacture(ID)
    );

-- Tùy chọn, có thể tạo các chỉ mục trên các cột được tìm kiếm thường xuyên
CREATE INDEX idx_phone_name ON Phone (Name);
CREATE INDEX idx_manufacture_name ON Manufacture (Name);


-- Insert dữ liệu cho bảng Manufacture
INSERT INTO Manufacture (ID, Name, Location, Employee) VALUES
                                                           ('1', 'Apple', 'USA', 147000),
                                                           ('2', 'Samsung', 'Hàn Quốc', 287000),
                                                           ('3', 'Xiaomi', 'Trung Quốc', 18000),
                                                           ('4', 'OnePlus', 'Trung Quốc', 2000),
                                                           ('5', 'Realme', 'Ấn Độ', 7000),
                                                           ('6', 'Oppo', 'Trung Quốc', 40000);

-- Insert dữ liệu cho bảng Phone
INSERT INTO Phone (ID, Name, Price, Color, Country, Quantity, ManufactureID) VALUES
                                                                                 ('1', 'iPhone 12 Pro', 1199, 'Đen', 'USA', 100, '1'),
                                                                                 ('2', 'Galaxy S21 Ultra', 1299, 'Xanh', 'Hàn Quốc', 150, '2'),
                                                                                 ('3', 'Redmi Note 10 Pro', 299, 'Trắng', 'Trung Quốc', 200, '3'),
                                                                                 ('4', 'OnePlus 9 Pro', 969, 'Xám', 'Trung Quốc', 50, '4'),
                                                                                 ('5', 'Realme 8 Pro', 279, 'Vàng', 'Ấn Độ', 120, '5'),
                                                                                 ('6', 'Oppo Find X3 Pro', 1149, 'Đỏ', 'Trung Quốc', 80, '6'),
                                                                                 ('7', 'iPhone 12', 799, 'Đỏ', 'USA', 180, '1'),
                                                                                 ('8', 'Galaxy A52', 349, 'Đen', 'Hàn Quốc', 250, '2'),
                                                                                 ('9', 'Redmi 9', 149, 'Xanh', 'Trung Quốc', 300, '3'),
                                                                                 ('10', 'OnePlus Nord', 399, 'Xanh dương', 'Trung Quốc', 70, '4'),
                                                                                 ('11', 'Realme C25', 199, 'Đen', 'Ấn Độ', 110, '5'),
                                                                                 ('12', 'Oppo A94', 359, 'Hồng', 'Trung Quốc', 90, '6');
