ALTER TABLE assets
DROP CONSTRAINT uq_asset_symbol;

ALTER TABLE assets
ADD CONSTRAINT uq_asset_name_symbol UNIQUE (name, symbol);