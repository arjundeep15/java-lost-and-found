-- Demo rows are inserted by src/main/resources/data.sql.
INSERT INTO items(title,description,category,item_status,location_found,contact,image_name,created_by)
VALUES ('Blue water bottle','Blue reusable bottle with a small sticker.','Personal items','FOUND','Library, first floor','helpdesk@campus.edu','bottle.svg',NULL),
('Student notebook','Black notebook with lecture notes inside.','Books & stationery','LOST','Engineering Block','helpdesk@campus.edu','notebook.svg',NULL),
('Set of keys','Three keys on a round keyring.','Personal items','FOUND','Cafeteria','helpdesk@campus.edu','keys.svg',NULL),
('Wireless earbuds','White earbuds in a charging case.','Electronics','LOST','Computer Lab','helpdesk@campus.edu','earbuds.svg',NULL);