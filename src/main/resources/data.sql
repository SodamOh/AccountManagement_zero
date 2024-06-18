insert into account_user(id, name, created_at, updated_at)
values (1, 'Pororo', now(), now());

insert into account_user(id, name, created_at, updated_at)
values (2, 'Lupi', now(), now());

insert into account_user(id, name, created_at, updated_at)
values (3, 'Eddie', now(), now());

insert into account_user(id, name, created_at, updated_at)
values (4, 'klong', now(), now());

-- 해결해야 할 문제 :
-- 에러 발생: [42S04][42104] Table "ACCOUNT_USER" not found (this database is empty); SQL statement:
-- h2에 실행시 오류메세지가 뜨고, insert가 바로 적용되지 않으나, 시간 지나서 보면 또 적용되어져있음
-- account에는 http 실행시 딜레이 없이 정상 저장됨