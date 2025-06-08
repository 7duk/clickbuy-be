use sideproject;
drop table if exists `item`;
CREATE TABLE `item` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `name` varchar(250) NOT NULL,
                        `category_id` int NOT NULL,
                        `buy_price` double NOT NULL,
                        `sell_price` double NOT NULL,
                        `public_price` double,
                        `discount` int NOT NULL DEFAULT 0,
                        `is_deleted` int NOT NULL DEFAULT (0),
                        `rating` double NOT NULL DEFAULT 0.0,
                        `amount` int NOT NULL,
                        `description` longtext,
                        `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        `updated_at` datetime,
                        `created_by` int NOT NULL DEFAULT 1,
                        `updated_by` int,
                        PRIMARY KEY (`id`)
);
drop table if exists `category`;
CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(250) NOT NULL,
  `is_deleted`int NOT NULL DEFAULT (0),
`created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updated_at` datetime,
`created_by` int NOT NULL DEFAULT 1,
`updated_by` int,
  PRIMARY KEY (`id`)
);


alter table `item`
add CONSTRAINT `FK_CATEGORY` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`);


INSERT INTO `category`(id,name,is_deleted) VALUES
(1, 'Laptop', 0),
(2, 'Phone', 0),
(3, 'Smart Watch', 0),
(4, 'Keyboard', 0),
(7, 'Speakers, Headphones', 0),
(8, 'Monitor', 0),
(9, 'Accessories', 0);


create trigger cal_public_price
    before insert on item
    for each row
    set new.public_price = new.sell_price * (100 - new.discount) / 100;


INSERT INTO `item` (`id`, `name`, `category_id`, `buy_price`, `sell_price`, `discount`, `description`, `is_deleted`, `rating`, `amount`, `created_at`, `updated_at`, `created_by`, `updated_by`) VALUES

-- Phones (category_id = 2)
(570, 'iPhone 13 Pro', 2, 24490000, 25490000, 15, 'Display: Super Retina XDR 6.1 inch, Chip A15 Bionic', 0, 4.5, 5, '2023-12-30 14:01:52', NULL, 1, NULL),
(571, 'Samsung Galaxy S21', 2, 21990000, 22990000, 15, 'Display: Dynamic AMOLED 6.2 inch, Chip Exynos 2100', 0, 4.3, 5, '2023-12-30 14:01:52', NULL, 1, NULL),
(572, 'Google Pixel 6', 2, 15990000, 16990000, 15, 'Display: AMOLED 6.4 inch, Chip Tensor, Camera 50 MP', 0, 4.4, 5, '2023-12-30 14:01:52', NULL, 1, NULL),
(573, 'OnePlus 9', 2, 14990000, 15990000, 15, 'Display: Fluid AMOLED 6.55 inch, Chip Snapdragon 888', 0, 4.2, 5, '2023-12-30 14:01:52', NULL, 1, NULL),
(574, 'Xiaomi Mi 11', 2, 12990000, 13990000, 15, 'Display: AMOLED 6.81 inch, Chip Snapdragon 888', 0, 4.1, 5, '2023-12-30 14:01:52', NULL, 1, NULL),
(575, 'Oppo Find X3 Pro', 2, 27990000, 28990000, 15, 'Display: AMOLED 6.7 inch, Chip Snapdragon 888', 0, 4.3, 5, '2023-12-30 14:01:52', NULL, 1, NULL),
(576, 'Huawei P40 Pro+', 2, 24490000, 25490000, 15, 'Display: OLED 6.58 inch, Chip Kirin 990, Camera Leica', 0, 4.2, 5, '2023-12-30 14:01:52', NULL, 1, NULL),
(577, 'Sony Xperia 1 III', 2, 29990000, 30990000, 15, 'Display: 4K HDR OLED 6.5 inch, Chip Snapdragon 888', 0, 4.0, 5, '2023-12-30 14:01:52', NULL, 1, NULL),
(578, 'Motorola Edge+', 2, 21990000, 22990000, 15, 'Display: AMOLED 6.7 inch, Chip Snapdragon 870', 0, 4.1, 5, '2023-12-30 14:01:52', NULL, 1, NULL),
(579, 'LG G8 ThinQ', 2, 12990000, 13990000, 15, 'Display: OLED 6.1 inch, Chip Snapdragon 855', 0, 3.9, 5, '2023-12-30 14:01:52', NULL, 1, NULL),

-- Smart Watches (category_id = 3)
(590, 'Apple Watch Series 7', 3, 5290000, 5990000, 20, 'Display: Always-On Retina, Water Resistant WR50, Heart Rate Monitor, Blood Oxygen Monitor', 0, 4.6, 5, '2023-12-08 22:35:27', NULL, 1, NULL),
(591, 'Samsung Galaxy Watch 4', 3, 4390000, 4790000, 10, 'Display: Super AMOLED, Water Resistant WR50, Heart Rate Monitor, Blood Oxygen Monitor', 0, 4.4, 5, '2023-12-08 22:35:27', NULL, 1, NULL),
(592, 'Fitbit Sense', 3, 3290000, 3690000, 20, 'Display: AMOLED, Water Resistant WR50, Heart Rate Monitor, Blood Oxygen Monitor', 0, 4.3, 5, '2023-12-08 22:35:27', NULL, 1, NULL),
(593, 'Garmin Venu 2', 3, 4090000, 4490000, 20, 'Display: AMOLED, Water Resistant WR50, Heart Rate Monitor, Built-in GPS', 0, 4.5, 5, '2023-12-08 22:35:27', NULL, 1, NULL),
(594, 'Huawei Watch GT 3', 3, 2890000, 3290000, 20, 'Display: AMOLED, Water Resistant WR50, Heart Rate Monitor, Blood Oxygen Monitor', 0, 4.2, 5, '2023-12-08 22:35:27', NULL, 1, NULL),
(595, 'Xiaomi Mi Watch', 3, 1590000, 1990000, 20, 'Display: AMOLED, Water Resistant WR50, Heart Rate Monitor, Blood Oxygen Monitor', 0, 4.0, 5, '2023-12-08 22:35:27', NULL, 1, NULL),
(596, 'Amazfit GTR 3', 3, 2090000, 2490000, 20, 'Display: AMOLED, Water Resistant WR50, Heart Rate Monitor, Built-in GPS', 0, 4.1, 5, '2023-12-08 22:35:27', NULL, 1, NULL),
(597, 'Fossil Gen 6', 3, 2890000, 3290000, 20, 'Display: AMOLED, Water Resistant WR30, Heart Rate Monitor, Blood Oxygen Monitor', 0, 4.0, 5, '2023-12-08 22:35:27', NULL, 1, NULL),
(598, 'TicWatch Pro 3', 3, 2390000, 2790000, 20, 'Display: AMOLED, Water Resistant WR30, Heart Rate Monitor, Built-in GPS', 0, 3.9, 5, '2023-12-08 22:35:27', NULL, 1, NULL),
(599, 'Garmin Forerunner 945', 3, 5790000, 6490000, 20, 'Display: MIP LCD, Water Resistant WR50, Heart Rate Monitor, Built-in GPS, Blood Oxygen Monitor', 0, 4.7, 5, '2023-12-08 22:35:27', NULL, 1, NULL),

-- Keyboards (category_id = 4)
(600, 'Logitech G Pro X', 4, 1499000, 1899000, 15, 'Type: Mechanical (Hot-swappable Switch), Connection: USB, RGB Backlit, Water Resistant', 0, 4.5, 5, '2023-12-08 22:36:46', NULL, 1, NULL),
(601, 'Corsair K95 RGB Platinum XT', 4, 3099000, 3499000, 20, 'Type: Mechanical (Cherry MX), Connection: USB, RGB Backlit, Water Resistant', 0, 4.6, 5, '2023-12-08 22:36:46', NULL, 1, NULL),
(602, 'Razer BlackWidow Elite', 4, 2499000, 2899000, 20, 'Type: Mechanical (Razer Green), Connection: USB, RGB Backlit, USB and Audio Pass-Through', 0, 4.4, 5, '2023-12-08 22:36:46', NULL, 1, NULL),
(603, 'SteelSeries Apex Pro', 4, 3599000, 3999000, 20, 'Type: Mechanical (OmniPoint Adjustable), Connection: USB, RGB Backlit, USB Port and Magnetic Wrist Rest', 0, 4.7, 5, '2023-12-08 22:36:46', NULL, 1, NULL),
(604, 'HyperX Alloy FPS Pro', 4, 999000, 1399000, 20, 'Type: Mechanical (Cherry MX), Connection: USB, Tenkeyless, Water Resistant', 0, 4.3, 5, '2023-12-08 22:36:46', NULL, 1, NULL),
(605, 'Cooler Master CK552', 4, 1199000, 1599000, 20, 'Type: Mechanical (Gateron), Connection: USB, RGB Backlit, Water Resistant', 0, 4.2, 5, '2023-12-08 22:36:46', NULL, 1, NULL),
(606, 'Ducky One 2 Mini', 4, 1599000, 1999000, 20, 'Type: Mechanical (Cherry MX), Connection: USB, Mini 60%, RGB Backlit', 0, 4.5, 5, '2023-12-08 22:36:46', NULL, 1, NULL),
(607, 'Keychron K6', 4, 1099000, 1499000, 20, 'Type: Mechanical (Gateron), Connection: Bluetooth/USB-C, Compact 65%, RGB Backlit', 0, 4.3, 5, '2023-12-08 22:36:46', NULL, 1, NULL),
(608, 'Anne Pro 2', 4, 899000, 1299000, 20, 'Type: Mechanical (Gateron), Connection: Bluetooth/USB-C, Compact 60%, RGB Backlit', 0, 4.1, 5, '2023-12-08 22:36:46', NULL, 1, NULL),
(609, 'Leopold FC660M', 4, 1799000, 2199000, 20, 'Type: Mechanical (Cherry MX), Connection: USB-C, Compact 66%, No Backlight', 0, 4.4, 5, '2023-12-08 22:36:46', NULL, 1, NULL),

-- Mice (Note: Mouse items were listed under ID 5, but there's no category 5. Moving to category 9 - Accessories)
(610, 'Logitech G Pro Wireless', 9, 2099000, 2499000, 20, 'Type: Wireless, Sensor: HERO 25K, Resolution: 16,000 DPI, Weight: 80g', 0, 4.6, 5, '2023-12-08 22:37:03', NULL, 1, NULL),
(611, 'Razer DeathAdder Elite', 9, 1299000, 1699000, 20, 'Type: Wired, Sensor: PMW3366, Resolution: 16,000 DPI, Weight: 96g', 0, 4.4, 5, '2023-12-08 22:37:03', NULL, 1, NULL),
(612, 'SteelSeries Rival 600', 9, 1899000, 2299000, 20, 'Type: Wired, Sensor: TrueMove3+, Resolution: 12,000 DPI, Weight: 96g', 0, 4.3, 5, '2023-12-08 22:37:03', NULL, 1, NULL),
(613, 'Corsair Dark Core RGB/SE', 9, 1599000, 1999000, 20, 'Type: Wired/Wireless, Sensor: PMW3367, Resolution: 16,000 DPI, Weight: 128g', 0, 4.2, 5, '2023-12-08 22:37:03', NULL, 1, NULL),
(614, 'Logitech MX Master 3', 9, 2099000, 2499000, 20, 'Type: Wireless, Sensor: Darkfield, Resolution: 4,000 DPI, 7 Control Buttons', 0, 4.7, 5, '2023-12-08 22:37:03', NULL, 1, NULL),
(615, 'Roccat Kone AIMO', 9, 1099000, 1499000, 20, 'Type: Wired, Sensor: Owl-Eye, Resolution: 16,000 DPI, Weight: 130g', 0, 4.1, 5, '2023-12-08 22:37:03', NULL, 1, NULL),
(616, 'Corsair Harpoon RGB Wireless', 9, 599000, 799000, 20, 'Type: Wireless, Sensor: PMW3325, Resolution: 10,000 DPI, Weight: 99g', 0, 4.0, 5, '2023-12-08 22:37:03', NULL, 1, NULL),
(617, 'Logitech G502 HERO', 9, 1199000, 1599000, 20, 'Type: Wired, Sensor: HERO 16K, Resolution: 16,000 DPI, Weight: 121g', 0, 4.5, 5, '2023-12-08 22:37:03', NULL, 1, NULL),
(618, 'SteelSeries Sensei Ten', 9, 1099000, 1499000, 20, 'Type: Wired, Sensor: TrueMove Pro, Resolution: 18,000 DPI, Weight: 92g', 0, 4.2, 5, '2023-12-08 22:37:03', NULL, 1, NULL),
(619, 'Glorious Model O', 9, 1099000, 1499000, 20, 'Type: Wired, Sensor: Pixart PMW3360, Resolution: 12,000 DPI, Weight: 67g', 0, 4.3, 5, '2023-12-08 22:37:03', NULL, 1, NULL),

-- Tablets (Note: Tablets were listed under category 6, but there's no category 6. Moving to category 9 - Accessories)
(620, 'iPad Pro 12.9-inch (2021)', 9, 21990000, 22990000, 20, 'Display: Liquid Retina XDR, Chip: M1, Storage: 128GB, OS: iPadOS', 0, 4.8, 5, '2023-12-08 22:37:23', NULL, 1, NULL),
(621, 'Samsung Galaxy Tab S7+', 9, 17990000, 18990000, 20, 'Display: Super AMOLED, Chip: Snapdragon 865+, Storage: 128GB, OS: Android', 0, 4.5, 4, '2023-12-08 22:37:23', NULL, 1, NULL),
(622, 'Microsoft Surface Pro 7', 9, 16990000, 17990000, 20, 'Display: PixelSense, Chip: Intel Core i5, Storage: 256GB, OS: Windows 10', 0, 4.4, 5, '2023-12-08 22:37:23', NULL, 1, NULL),
(623, 'Lenovo Tab P11 Pro', 9, 9290000, 9990000, 20, 'Display: OLED, Chip: Snapdragon 730G, Storage: 128GB, OS: Android', 0, 4.2, 5, '2023-12-08 22:37:23', NULL, 1, NULL),
(624, 'Huawei MatePad Pro 12.6', 9, 13990000, 14990000, 20, 'Display: OLED, Chip: Kirin 9000E, Storage: 256GB, OS: HarmonyOS', 0, 4.3, 5, '2023-12-08 22:37:23', NULL, 1, NULL),
(625, 'Apple iPad Air (2020)', 9, 12990000, 13990000, 20, 'Display: Liquid Retina, Chip: A14 Bionic, Storage: 64GB, OS: iPadOS', 0, 4.6, 5, '2023-12-08 22:37:23', NULL, 1, NULL),
(626, 'Samsung Galaxy Tab A7', 9, 5290000, 5990000, 20, 'Display: TFT, Chip: Snapdragon 662, Storage: 32GB, OS: Android', 0, 4.0, 5, '2023-12-08 22:37:23', NULL, 1, NULL),
(627, 'Amazon Fire HD 10', 9, 2790000, 3190000, 20, 'Display: Full HD, Chip: MediaTek, Storage: 64GB, OS: Fire OS', 0, 3.8, 5, '2023-12-08 22:37:23', NULL, 1, NULL),
(628, 'Microsoft Surface Go 3', 9, 8290000, 8990000, 20, 'Display: PixelSense, Chip: Intel Pentium Gold, Storage: 128GB, OS: Windows 10', 0, 4.1, 5, '2023-12-08 22:37:23', NULL, 1, NULL),
(629, 'Lenovo Tab M10 FHD Plus', 9, 3890000, 4290000, 20, 'Display: Full HD, Chip: MediaTek Helio P22T, Storage: 64GB, OS: Android', 0, 3.9, 5, '2023-12-08 22:37:23', NULL, 1, NULL),

-- Speakers, Headphones (category_id = 7)
(630, 'Sony WH-1000XM4', 7, 4599000, 4999000, 20, 'Type: Over-ear, Connection: Bluetooth, Noise Cancelling: Yes, Battery Life: 30 hours', 0, 4.8, 5, '2023-12-08 22:37:40', NULL, 1, NULL),
(631, 'AirPods Pro', 7, 4799000, 5499000, 20, 'Type: In-ear, Connection: Bluetooth, Noise Cancelling: Yes, Battery Life: 24 hours', 0, 4.7, 5, '2023-12-08 22:37:40', NULL, 1, NULL),
(632, 'Bose QuietComfort 35 II', 7, 6299000, 6999000, 20, 'Type: Over-ear, Connection: Bluetooth, Noise Cancelling: Yes, Battery Life: 20 hours', 0, 4.6, 5, '2023-12-08 22:37:40', NULL, 1, NULL),
(633, 'JBL Free X', 7, 1299000, 1699000, 20, 'Type: True wireless, Connection: Bluetooth, Noise Cancelling: No, Battery Life: 4 hours', 0, 4.0, 5, '2023-12-08 22:37:40', NULL, 1, NULL),
(634, 'SteelSeries Arctis 7', 7, 2099000, 2499000, 20, 'Type: Over-ear, Connection: Wireless/USB, Noise Cancelling: No, Battery Life: 24 hours', 0, 4.4, 5, '2023-12-08 22:37:40', NULL, 1, NULL),
(635, 'Sennheiser HD 660 S', 7, 12990000, 13990000, 20, 'Type: Over-ear, Connection: Wired, Noise Cancelling: No, Sensitivity: 104dB', 0, 4.7, 5, '2023-12-08 22:37:40', NULL, 1, NULL),
(636, 'Beats Powerbeats Pro', 7, 3399000, 3799000, 20, 'Type: True wireless, Connection: Bluetooth, Noise Cancelling: No, Battery Life: 9 hours', 0, 4.3, 5, '2023-12-08 22:37:40', NULL, 1, NULL),
(637, 'HyperX Cloud II', 7, 1599000, 1999000, 20, 'Type: Over-ear, Connection: USB/3.5mm, Noise Cancelling: No, Sensitivity: 98dB', 0, 4.5, 5, '2023-12-08 22:37:40', NULL, 1, NULL),
(638, 'Logitech G Pro X Gaming Headset', 7, 1799000, 2199000, 20, 'Type: Over-ear, Connection: 3.5mm/USB, Noise Cancelling: No, Sensitivity: 91.7dB', 0, 4.4, 5, '2023-12-08 22:37:40', NULL, 1, NULL),
(639, 'Razer Kraken X', 7, 899000, 1299000, 20, 'Type: Over-ear, Connection: 3.5mm, Noise Cancelling: No, Sensitivity: 106dB', 0, 4.1, 5, '2023-12-08 22:37:40', NULL, 1, NULL),

-- Monitors (category_id = 8)
(640, 'Dell UltraSharp U2719D', 8, 6899000, 7599000, 20, 'Size: 27 inch, Resolution: 2560x1440, Aspect Ratio: 16:9, Ports: HDMI/DisplayPort', 0, 4.5, 5, '2023-12-08 22:37:59', NULL, 1, NULL),
(641, 'LG 27GL83A-B', 8, 9999000, 10999000, 20, 'Size: 27 inch, Resolution: 2560x1440, Aspect Ratio: 16:9, Refresh Rate: 144Hz', 0, 4.6, 5, '2023-12-08 22:37:59', NULL, 1, NULL),
(642, 'Samsung Odyssey G7', 8, 13999000, 14999000, 20, 'Size: 32 inch, Resolution: 2560x1440, Aspect Ratio: 16:9, Curved: 1000R, Refresh Rate: 240Hz', 0, 4.4, 5, '2023-12-08 22:37:59', NULL, 1, NULL),
(643, 'Acer Predator XB271HU', 8, 12999000, 13999000, 20, 'Size: 27 inch, Resolution: 2560x1440, Aspect Ratio: 16:9, Refresh Rate: 144Hz, G-Sync', 0, 4.3, 5, '2023-12-08 22:37:59', NULL, 1, NULL),
(644, 'ASUS ROG Swift PG279Q', 8, 14999000, 15999000, 20, 'Size: 27 inch, Resolution: 2560x1440, Aspect Ratio: 16:9, Refresh Rate: 165Hz, G-Sync', 0, 4.5, 5, '2023-12-08 22:37:59', NULL, 1, NULL),
(645, 'Dell S2419HGF', 8, 4299000, 4699000, 20, 'Size: 24 inch, Resolution: 1920x1080, Aspect Ratio: 16:9, Refresh Rate: 144Hz, FreeSync', 0, 4.2, 5, '2023-12-08 22:37:59', NULL, 1, NULL),
(646, 'BenQ EX2780Q', 8, 9999000, 10999000, 20, 'Size: 27 inch, Resolution: 2560x1440, Aspect Ratio: 16:9, Refresh Rate: 144Hz, HDRi', 0, 4.4, 5, '2023-12-08 22:37:59', NULL, 1, NULL),
(647, 'ViewSonic XG2402', 8, 4099000, 4499000, 20, 'Size: 24 inch, Resolution: 1920x1080, Aspect Ratio: 16:9, Refresh Rate: 144Hz, FreeSync', 0, 4.1, 5, '2023-12-08 22:37:59', NULL, 1, NULL),
(648, 'Alienware AW3420DW', 8, 19999000, 20999000, 20, 'Size: 34 inch, Resolution: 3440x1440, Aspect Ratio: 21:9, Refresh Rate: 120Hz, G-Sync', 0, 4.6, 5, '2023-12-09 01:02:45', NULL, 1, NULL),
(649, 'HP Omen X 25', 8, 6799000, 7499000, 20, 'Size: 25 inch, Resolution: 1920x1080, Aspect Ratio: 16:9, Refresh Rate: 240Hz, G-Sync', 0, 4.3, 5, '2023-12-09 01:02:45', NULL, 1, NULL),

-- Accessories (category_id = 9)
(650, 'Anker PowerCore 10000', 9, 199000, 299000, 20, 'Type: Power Bank, Capacity: 10,000mAh, Connection: USB-A, Charging Port: Micro-USB', 0, 4.4, 5, '2023-12-09 01:04:25', NULL, 1, NULL),
(651, 'Logitech MX Master 3 Mouse', 9, 2599000, 2999000, 40, 'Type: Wireless Mouse, Connection: Bluetooth/USB, Buttons: 7, Sensor: Darkfield', 0, 4.7, 5, '2023-12-09 01:04:25', NULL, 1, NULL),
(652, 'PopSockets PopGrip', 9, 99000, 199000, 20, 'Type: PopGrip, Material: Silicone, Colors: Various, Adjustable Angle', 0, 4.1, 5, '2023-12-09 01:04:25', NULL, 1, NULL),
(653, 'UGREEN Cable Organizer', 9, 39000, 59000, 35, 'Type: Cable Management, Material: Silicone, Quantity: 5, Colors: Various', 0, 4.2, 5, '2023-12-09 01:04:25', NULL, 1, NULL),
(654, 'AmazonBasics Laptop Stand', 9, 159000, 259000, 30, 'Type: Laptop Stand, Material: Metal, Height Adjustable, Size: 11 x 13 x 7.2 inch', 0, 4.3, 5, '2023-12-09 01:04:25', NULL, 1, NULL),
(655, 'UGREEN USB Hub', 9, 89000, 189000, 40, 'Type: USB Hub, Ports: 4, Connection: USB 3.0, Compatible: Windows/Mac/Linux', 0, 4.0, 5, '2023-12-09 01:04:25', NULL, 1, NULL),
(656, 'Razer Goliathus Extended Chroma', 9, 499000, 699000, 30, 'Type: Mousepad, Size: Extended, RGB Chroma Lighting, Connection: USB', 0, 4.4, 5, '2023-12-08 22:38:19', NULL, 1, NULL),
(657, 'JBL Clip 3 Portable Speaker', 9, 699000, 1099000, 20, 'Type: Portable Speaker, Power: 3.3W, Connection: Bluetooth, Battery Life: 10 hours', 0, 4.2, 5, '2023-12-08 22:38:19', NULL, 1, NULL),
(658, 'UGREEN Phone Stand', 9, 109000, 129000, 20, 'Type: Phone Stand, Material: ABS Plastic, Adjustable Angle, Color: White', 0, 4.1, 5, '2023-12-08 22:38:19', NULL, 1, NULL),
(659, 'Belkin Wireless Charger', 9, 399000, 499000, 20, 'Type: Wireless Charger, Power: 10W, Connection: USB-C, Compatible: Qi-enabled devices', 0, 4.3, 5, '2023-12-08 22:38:19', NULL, 1, NULL),

-- Laptops (category_id = 1)
(668, 'Dell XPS 13', 1, 23990000, 24990000, 15, 'Display: 13.4 inch FHD+, Chip Intel Core i5, RAM 8GB, SSD 256GB', 1, 4.5, 5, '2023-12-30 14:01:21', NULL, 1, NULL),
(669, 'MacBook Air', 1, 29990000, 30990000, 15, 'Display: 13.3 inch Retina, Chip Apple M1, RAM 8GB, SSD 256GB', 0, 4.7, 5, '2023-12-30 14:01:21', NULL, 1, NULL),
(670, 'HP Spectre x360', 1, 25990000, 26990000, 15, 'Display: 13.3 inch FHD, Chip Intel Core i7, RAM 16GB, SSD 512GB', 0, 4.4, 5, '2023-12-30 14:01:21', NULL, 1, NULL),
(671, 'Asus ZenBook 14', 1, 17990000, 18990000, 15, 'Display: 14 inch FHD, Chip Intel Core i5, RAM 8GB, SSD 256GB', 0, 4.3, 5, '2023-12-30 14:01:21', NULL, 1, NULL);


