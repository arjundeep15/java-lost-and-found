INSERT IGNORE INTO users(name,email,password,role) VALUES('Campus Administrator','admin@campus.edu','admin123','ADMIN');
INSERT INTO items(title,description,category,item_status,location_found,contact,image_name,created_by)
SELECT 'Blue water bottle','Blue reusable bottle with a small sticker.','Personal items','FOUND','Library, first floor','helpdesk@campus.edu','bottle.svg',NULL
WHERE NOT EXISTS (SELECT 1 FROM items WHERE title='Blue water bottle' AND location_found='Library, first floor');
INSERT INTO items(title,description,category,item_status,location_found,contact,image_name,created_by)
SELECT 'Student notebook','Black notebook with lecture notes inside.','Books & stationery','LOST','Engineering Block','helpdesk@campus.edu','notebook.svg',NULL
WHERE NOT EXISTS (SELECT 1 FROM items WHERE title='Student notebook' AND location_found='Engineering Block');
INSERT INTO items(title,description,category,item_status,location_found,contact,image_name,created_by)
SELECT 'Set of keys','Three keys on a round keyring.','Personal items','FOUND','Cafeteria','helpdesk@campus.edu','keys.svg',NULL
WHERE NOT EXISTS (SELECT 1 FROM items WHERE title='Set of keys' AND location_found='Cafeteria');
INSERT INTO items(title,description,category,item_status,location_found,contact,image_name,created_by)
SELECT 'Wireless earbuds','White earbuds in a charging case.','Electronics','LOST','Computer Lab','helpdesk@campus.edu','earbuds.svg',NULL
WHERE NOT EXISTS (SELECT 1 FROM items WHERE title='Wireless earbuds' AND location_found='Computer Lab');