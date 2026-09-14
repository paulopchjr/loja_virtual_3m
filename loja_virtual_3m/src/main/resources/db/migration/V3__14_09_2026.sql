ALTER TABLE pessoa_fisica ADD COLUMN IF NOT EXISTS tipo_pessoa character varying(255);
ALTER TABLE pessoa_juridica ADD COLUMN IF NOT EXISTS tipo_pessoa character varying(255);