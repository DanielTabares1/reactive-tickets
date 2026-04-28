CREATE TABLE IF NOT EXISTS event (
    id SERIAL PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    description TEXT,
    event_date TIMESTAMP WITH TIME ZONE NOT NULL,
    site VARCHAR(200) NOT NULL,
    status VARCHAR(20) NOT NULL CHECK (status IN ('PUBLISHED', 'SOLD_OUT', 'CANCELLED', 'COMPLETED')),
    image_url VARCHAR(500),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    version BIGINT DEFAULT 0
);

-- Indexes
CREATE INDEX IF NOT EXISTS idx_event_date ON event(event_date);
CREATE INDEX IF NOT EXISTS idx_evento_status ON event(status);
CREATE INDEX IF NOT EXISTS idx_event_date_status ON event(event_date, status);

-- Comments
--COMMENT ON TABLE event IS 'Eventos disponibles para reserva de tickets';
--COMMENT ON COLUMN event.status IS 'PUBLISHED: visible para usuarios | SOLD_OUT: sin tickets | CANCELLED: evento cancelado | COMPLETED: evento pasado';
--COMMENT ON COLUMN event.version IS 'Control de concurrencia optimista';

CREATE TABLE IF NOT EXISTS zone (
    id SERIAL PRIMARY KEY,
    event_id INT NOT NULL REFERENCES event(id) ON DELETE CASCADE,
    name VARCHAR(200) NOT NULL,
    description TEXT,
    capacity INT NOT NULL,
    available_capacity INT NOT NULL,
    price NUMERIC(19,4) NOT NULL,
    currency VARCHAR(3) DEFAULT 'COP',
    version BIGINT DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    -- Constraints
    CONSTRAINT zone_event_name_unique UNIQUE(event_id, name),
    CONSTRAINT capacity_positive CHECK(capacity > 0),
    CONSTRAINT price_positive CHECK(price >= 0)
);

-- Indexes
CREATE INDEX IF NOT EXISTS idx_zone_event_id on zone(event_id);

