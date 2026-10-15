USE sentrax;
INSERT INTO app_user(username, password_hash, role) VALUES ('analyst', 'a1b2c3d4e5f60718293a4b5c6d7e8f90:0062ffa81b38990dbde29cad3200e8456418d1e75240e29d61b457ece40b2acf', 'ANALYST');
INSERT INTO customer(name, home_city, avg_amount) VALUES
 ('Asha Verma','Delhi',5000),('Rohan Mehta','Mumbai',8000),('Priya Nair','Bengaluru',3000),
 ('Imran Khan','Hyderabad',6000),('Sneha Iyer','Chennai',4000),('Deepak Rao','Ghaziabad',2500);
INSERT INTO txn(customer_id, amount, merchant, location, device_id, txn_time, risk_score, risk_level, reasons) VALUES
 (1,4200,'Big Bazaar','Delhi','ASHA-PHONE','2026-10-10 14:10:00',0,'SAFE',''),
 (1,5600,'Amazon','Delhi','ASHA-LAPTOP','2026-10-11 19:45:00',0,'SAFE',''),
 (2,7800,'Reliance Digital','Mumbai','ROHAN-PHONE','2026-10-10 12:00:00',0,'SAFE',''),
 (3,2900,'Swiggy','Bengaluru','PRIYA-PHONE','2026-10-12 21:15:00',0,'SAFE',''),
 (4,6100,'Myntra','Hyderabad','IMRAN-PHONE','2026-10-12 16:30:00',0,'SAFE',''),
 (5,3800,'Zomato','Chennai','SNEHA-PHONE','2026-10-13 13:20:00',0,'SAFE',''),
 (6,2400,'DMart','Ghaziabad','DEEPAK-PHONE','2026-10-13 18:05:00',0,'SAFE',''),
 (2,52000,'Jewel World','Kolkata','UNKNOWN-DEV-9','2026-10-14 02:40:00',85,'FRAUD','Amount threshold: 6.5x average; Location mismatch: Kolkata far from home; New device; Odd hour');
INSERT INTO alert(transaction_id) VALUES (8);
