DROP TABLE IF EXISTS http_request_log;
CREATE TABLE http_request_log (
    id UUID PRIMARY KEY,
    method VARCHAR(10) NOT NULL,
    url TEXT NOT NULL,
    headers JSONB NOT NULL,
    body TEXT,
    timestamp TIMESTAMP DEFAULT now()
);
