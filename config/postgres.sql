CREATE USER springuser;

ALTER USER springuser WITH PASSWORD '---';

CREATE DATABASE investing;

CREATE DATABASE "investing"
    WITH OWNER "postgres"
    ENCODING 'UTF8'
    LC_COLLATE = 'en_US.UTF-8'
    LC_CTYPE = 'en_US.UTF-8'
    TEMPLATE template0;

GRANT ALL PRIVILEGES ON DATABASE investing TO springuser;
