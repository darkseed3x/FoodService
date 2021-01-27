

CREATE TABLE assets (
    asset_id serial PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(2048) NOT NULL,
    price real NOT NULL
);
CREATE TABLE orders (
    order_id serial PRIMARY KEY,
    created timestamp with time zone NOT NULL default NOW()
);
CREATE TABLE order_items (
    id serial primary key,
    asset_id serial not null references assets(asset_id),
    order_id serial not null references orders(order_id),
    amount integer NOT NULL
);

