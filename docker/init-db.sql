-- Initial database setup for Coffee Shop Management
-- This file runs when PostgreSQL container starts for the first time

-- Create extensions
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "pg_trgm";

-- Set timezone
SET timezone = 'UTC';

-- Create indexes for better performance
-- These will be created by Liquibase, but we can add them here for initial setup

-- Sample data for testing (optional)
-- You can uncomment this for development/testing

-- Insert sample categories
INSERT INTO menu_categories (name, display_order) VALUES 
('Coffee', 1),
('Tea', 2),
('Desserts', 3),
('Sandwiches', 4)
ON CONFLICT DO NOTHING;

-- Insert sample tables
INSERT INTO tables (table_number, capacity, is_available, is_reserved, is_active) VALUES 
('T1', 2, true, false, true),
('T2', 2, true, false, true),
('T3', 4, true, false, true),
('T4', 4, true, false, true),
('T5', 3, true, false, true),
('T6', 3, true, false, true),
('T7', 2, true, false, true),
('T8', 4, true, false, true),
('T9', 2, true, false, true),
('T10', 3, true, false, true)
ON CONFLICT DO NOTHING;

-- Insert sample menu items
INSERT INTO menu_items (name, description, price, category_id) VALUES 
('Espresso', 'Classic Italian espresso shot', 2.50, 1),
('Cappuccino', 'Espresso with steamed milk foam', 3.50, 1),
('Latte', 'Espresso with steamed milk', 4.00, 1),
('Americano', 'Espresso with hot water', 3.00, 1),
('Green Tea', 'Traditional green tea', 2.50, 2),
('Black Tea', 'Classic black tea', 2.00, 2),
('Cheesecake', 'New York style cheesecake', 5.50, 3),
('Chocolate Cake', 'Rich chocolate layer cake', 4.50, 3),
('Club Sandwich', 'Triple decker with bacon', 8.50, 4),
('Vegetarian Sandwich', 'Fresh vegetables and cheese', 7.50, 4)
ON CONFLICT DO NOTHING;

-- Grant permissions (if needed)
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO postgres;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO postgres;

-- Create view for active reservations
CREATE OR REPLACE VIEW active_reservations AS
SELECT r.*, t.table_number, t.capacity
FROM reservations r
JOIN tables t ON r.dining_table_id = t.id
WHERE r.status NOT IN ('CANCELLED', 'COMPLETED')
AND r.start_time > NOW() - INTERVAL '24 hours';

-- Create function to check table availability
CREATE OR REPLACE FUNCTION is_table_available(
    p_table_id INTEGER,
    p_start_time TIMESTAMP,
    p_end_time TIMESTAMP
) RETURNS BOOLEAN AS $$
BEGIN
    RETURN NOT EXISTS (
        SELECT 1 FROM reservations 
        WHERE dining_table_id = p_table_id
        AND status NOT IN ('CANCELLED', 'COMPLETED')
        AND (
            (start_time <= p_start_time AND end_time > p_start_time) OR
            (start_time < p_end_time AND end_time >= p_end_time) OR
            (start_time >= p_start_time AND end_time <= p_end_time)
        )
    );
END;
$$ LANGUAGE plpgsql;

-- Create trigger for automatic timestamp updates
CREATE OR REPLACE FUNCTION update_modified_column()
RETURNS TRIGGER AS $$
BEGIN
    -- This will be used for future audit columns
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Add comments for documentation
COMMENT ON TABLE menu_categories IS 'Categories for menu items organization';
COMMENT ON TABLE menu_items IS 'Food and beverage items available in the coffee shop';
COMMENT ON TABLE tables IS 'Dining tables with capacity and availability status';
COMMENT ON TABLE reservations IS 'Customer reservations with time slots and status tracking';
COMMENT ON TABLE customers IS 'Customer information for orders and reservations';
COMMENT ON TABLE waiters IS 'Staff members who serve customers';
COMMENT ON TABLE orders IS 'Customer orders with items and pricing';

-- Create indexes for performance
CREATE INDEX IF NOT EXISTS idx_reservations_table_time ON reservations(dining_table_id, start_time);
CREATE INDEX IF NOT EXISTS idx_reservations_status ON reservations(status);
CREATE INDEX IF NOT EXISTS idx_menu_items_category ON menu_items(category_id);
CREATE INDEX IF NOT EXISTS idx_orders_date ON orders(order_date);
CREATE INDEX IF NOT EXISTS idx_orders_waiter ON orders(waiter_id);

-- Analyze tables for query optimizer
ANALYZE menu_categories;
ANALYZE menu_items;
ANALYZE tables;
ANALYZE reservations;
ANALYZE customers;
ANALYZE waiters;
ANALYZE orders;
