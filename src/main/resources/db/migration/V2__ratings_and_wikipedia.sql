-- Колонки рейтинга и Википедии (при ddl-auto=update Hibernate создаст их автоматически).
ALTER TABLE reformer ADD COLUMN IF NOT EXISTS overall_rating VARCHAR(1);
ALTER TABLE reformer ADD COLUMN IF NOT EXISTS wikipedia_url VARCHAR(512);
