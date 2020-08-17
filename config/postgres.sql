CREATE USER "spring-user";

ALTER USER "spring-user" WITH PASSWORD '---';

CREATE DATABASE spring_boot_api
    WITH OWNER "postgres"
    ENCODING 'UTF8'
    LC_COLLATE = 'en_US.UTF-8'
    LC_CTYPE = 'en_US.UTF-8'
    TEMPLATE template0;

GRANT ALL PRIVILEGES ON DATABASE spring_boot_api TO "spring-user";
