CREATE TABLE items (
    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    name TEXT,
    price DECIMAL(10,2) NOT NULL,
    type TEXT NOT NULL,
    endtime DATETIME NOT NULL,
    description TEXT,
    shipping DECIMAL(10,2),
    sold BIT DEFAULT 0
);
    -- Add other columns as needed