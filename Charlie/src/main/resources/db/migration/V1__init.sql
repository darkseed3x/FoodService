

CREATE TABLE ${RestaName}_ASSETS (
    asset_id serial PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(2048) NOT NULL,
    price real NOT NULL
);
CREATE TABLE ${RestaName}_ORDERS (
    order_id serial PRIMARY KEY,
    created timestamp with time zone NOT NULL default NOW()
);
CREATE TABLE ${RestaName}_ORDER_ITEMS (
    id serial primary key,
    asset_id serial not null references ${RestaName}_ASSETS(asset_id),
    order_id serial not null references ${RestaName}_ORDERS(order_id),
    amount integer NOT NULL
);

