create TABLE IF NOT EXISTS sessions (
  id SERIAL PRIMARY KEY,
  name text NOT NULL,
  time text NOT NULL
);

comment on table sessions is 'Киносеансы';
comment on column sessions.id is 'Идентификатор киносеанса';
comment on column sessions.name is 'Название киносеанса';
comment on column sessions.time is 'Время киносеанса';
