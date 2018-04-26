insert into app_user (username, password, email, first_name, last_name, password_hint, phone_number, website, country, province, city, address, postal_code, account_expired, account_locked, credentials_expired, account_enabled, version) values ('admin', '$2a$10$bH/ssqW8OhkTlIso9/yakubYODUOmh.6m5HEJvcBq3t3VdBh7ebqO', 'admin@app.com', '', '', '', '', '', '', '', '', '', '', false, false, false, true, 0);
insert into app_user (username, password, email, first_name, last_name, password_hint, phone_number, website, country, province, city, address, postal_code, account_expired, account_locked, credentials_expired, account_enabled, version) values ('user', '$2a$10$CnQVJ9bsWBjMpeSKrrdDEeuIptZxXrwtI6CZ/OgtNxhIgpKxXeT9y', 'user@app.com', '', '', '', '', '', '', '', '', '', '', false, false, false, true, 0);

insert into role (name, description) values ('ROLE_ADMIN', 'Administrator role (can edit Users)');
insert into role (name, description) values ('ROLE_USER', 'Default role for all Users');

insert into user_role (user_name, role_name) values  ('admin', 'ROLE_ADMIN');
insert into user_role (user_name, role_name) values  ('user', 'ROLE_USER');
insert into oauth_client_details (client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove)
    values ('client', '', '$2a$10$3vdSxiMEvLTAxtbgjWDwCuoHd/t8oyZ4nWZyz5heUr6BWkNg8QMEy', 'openid', 'authorization_code,refresh_token,password', '', '', 0, 0, NULL, 'true');
