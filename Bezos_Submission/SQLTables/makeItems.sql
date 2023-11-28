CREATE TABLE items (
    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    name TEXT,
    seller_username TEXT,
    bidder_username TEXT,
    price DECIMAL(10,2) NOT NULL,
    type TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    description TEXT,
    shipping DECIMAL(10,2) DEFAULT 0,
    expedited_shipping DECIMAL(10,2) DEFAULT 0,
    sold BIT DEFAULT 0,
    purchase_amount DECIMAL(10,2) DEFAULT 0
);
    -- Add other columns as needed