CREATE TABLE bids (
    bid_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    username TEXT, 
    item_id INTEGER,
    bid_amount DECIMAL(10,2),
    FOREIGN KEY (username) REFERENCES users(username),
    FOREIGN KEY (item_id) REFERENCES items(id)
    
);