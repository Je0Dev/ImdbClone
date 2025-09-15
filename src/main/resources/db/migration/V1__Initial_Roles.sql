-- This script runs once to set up the initial roles in the database.
-- We can now remove the role creation logic from the CommandLineRunner.

INSERT INTO
    role (id, name)
VALUES (1, 'ROLE_USER') ON CONFLICT (id) DO NOTHING;

INSERT INTO
    role (id, name)
VALUES (2, 'ROLE_ADMIN') ON CONFLICT (id) DO NOTHING;
