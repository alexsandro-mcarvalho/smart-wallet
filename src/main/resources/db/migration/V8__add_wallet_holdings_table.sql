CREATE TABLE wallet_holdings (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    quantity NUMERIC(18,8) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    asset_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,

    CONSTRAINT fk_wallet_holdings_asset
        FOREIGN KEY (asset_id)
        REFERENCES assets(id),

    CONSTRAINT fk_wallet_holdings_user
        FOREIGN KEY (user_id)
        REFERENCES users(id),

    CONSTRAINT uk_wallet_holdings_user_asset
        UNIQUE (user_id, asset_id)
);