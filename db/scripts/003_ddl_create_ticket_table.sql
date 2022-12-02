create TABLE IF NOT EXISTS ticket (
    id SERIAL PRIMARY KEY,
    session_id INT NOT NULL REFERENCES sessions(id),
    pos_row INT NOT NULL,
    cell INT NOT NULL,
    user_id INT NOT NULL REFERENCES users(id),
    CONSTRAINT ticket_unique UNIQUE (session_id, pos_row, cell)
);

comment on table ticket is 'Билеты';
comment on column ticket.id is 'Идентификатор билета';
comment on column ticket.session_id is 'Внешний ключ на киносеанс';
comment on column ticket.pos_row is 'Номер ряда в зале';
comment on column ticket.cell is 'Номер места в зале';
comment on column ticket.user_id is 'Внешний ключ на пользователя';
