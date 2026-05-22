-- Миграция для существующей БД H2 (если колонка ещё не создана Hibernate).
-- При spring.jpa.hibernate.ddl-auto=update колонка добавится автоматически.
ALTER TABLE reformer ADD COLUMN IF NOT EXISTS image_url VARCHAR(512);
