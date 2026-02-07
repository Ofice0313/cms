
    create table tb_client (
        id bigint not null auto_increment,
        email varchar(255),
        first_name varchar(255),
        last_name varchar(255),
        phone varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_deposit (
        deposit_date date,
        due_date date,
        initial_deposit_value decimal(38,2),
        paid_installments integer,
        remaining_amount decimal(38,2),
        sale_value decimal(38,2),
        status tinyint,
        total_installments integer,
        client_id bigint,
        created_at datetime(6),
        updated_at datetime(6),
        vehicle_id bigint not null,
        observations varchar(255),
        primary key (vehicle_id)
    ) engine=InnoDB;

    create table tb_installment (
        due_date date,
        installment_number integer,
        payment_date date,
        status tinyint,
        value_per_installment decimal(38,2),
        created_at datetime(6),
        deposit_id bigint,
        id bigint not null auto_increment,
        updated_at datetime(6),
        observations varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_permission (
        id bigint not null auto_increment,
        description varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_sale (
        sale_date date,
        sale_value decimal(38,2),
        client_id bigint,
        vehicle_id bigint not null,
        observations varchar(255),
        primary key (vehicle_id)
    ) engine=InnoDB;

    create table tb_users (
        account_non_expired bit,
        account_non_locked bit,
        credentials_non_expired bit,
        enabled bit,
        id bigint not null auto_increment,
        full_name varchar(255),
        password varchar(255),
        user_name varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_vehicle (
        acquisition_date date,
        cp decimal(12,2),
        customs_broker decimal(12,2),
        driver decimal(12,2),
        innater decimal(12,2),
        inspection decimal(12,2),
        license_plate decimal(12,2),
        loading decimal(12,2),
        manufactured_year integer,
        order_date date,
        purchase_value decimal(12,2),
        registration_date date,
        rights decimal(12,2),
        status tinyint,
        travel decimal(12,2),
        id bigint not null auto_increment,
        brand varchar(255),
        model varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table user_permission (
        permission_id bigint not null,
        user_id bigint not null
    ) engine=InnoDB;

    alter table tb_deposit 
       add constraint UKa4iajkufddbjun5h2ftgybmeh unique (client_id);

    alter table tb_users 
       add constraint UKt3xry9rxngbbv1utykxcve9qt unique (user_name);

    alter table tb_deposit 
       add constraint FKher6ysca2kp2b29m84at4eiay 
       foreign key (client_id) 
       references tb_client (id);

    alter table tb_deposit 
       add constraint FKdqnt5455ig2k3ax6ngshh17v3 
       foreign key (vehicle_id) 
       references tb_vehicle (id);

    alter table tb_installment 
       add constraint FKg0ks3o2mthmqk1gx2arx7rv3a 
       foreign key (deposit_id) 
       references tb_deposit (vehicle_id);

    alter table tb_sale 
       add constraint FK43jttra653rb5w78icqgc9fub 
       foreign key (client_id) 
       references tb_client (id);

    alter table tb_sale 
       add constraint FKry4f3m5gy3tkyle92q4csrerb 
       foreign key (vehicle_id) 
       references tb_vehicle (id);

    alter table user_permission 
       add constraint FKdn4bu8iyay2kbab49i14b5sey 
       foreign key (permission_id) 
       references tb_permission (id);

    alter table user_permission 
       add constraint FKow2d74fs3qkvha007sxo1uv7s 
       foreign key (user_id) 
       references tb_users (id);
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Toyota', 'Corolla', 2021, '2021-03-15', '2021-03-15', 0, 85000.00, 25000.50, 15000.00, 8500.50, 12000.00, 3500.00, 4500.00, 2000.00, 1500.00, 3000.00, '2021-03-15');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Honda', 'Civic', 2020, '2020-06-20', '2020-06-20', 0, 78000.00, 32000.75, 13500.00, 7800.00, 11000.00, 3200.00, 4200.00, 1800.00, 1400.00, 2800.00, '2020-06-20');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Ford', 'Ranger', 2022, '2022-01-10', '2022-01-10', 0, 125000.00, 15000.00, 18000.00, 9500.00, 14000.00, 4000.00, 5000.00, 2500.00, 1800.00, 3500.00, '2022-01-10');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Chevrolet', 'Onix', 2019, '2019-11-05', '2019-11-05', 1, 45000.00, 68000.25, 10000.00, 6000.00, 8500.00, 2500.00, 3500.00, 1500.00, 1200.00, 2200.00, '2019-11-05');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Volkswagen', 'Polo', 2023, '2023-08-22', '2023-08-22', 0, 95000.00, 8500.00, 16500.00, 8800.00, 12500.00, 3600.00, 4600.00, 2100.00, 1600.00, 3100.00, '2023-08-22');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Nissan', 'Kicks', 2021, '2021-05-18', '2021-05-18', 0, 72000.00, 42000.50, 14000.00, 7500.00, 10500.00, 3100.00, 4100.00, 1900.00, 1450.00, 2700.00, '2021-05-18');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Hyundai', 'HB20', 2020, '2020-09-30', '2020-09-30', 1, 55000.00, 55000.80, 12000.00, 6800.00, 9500.00, 2800.00, 3800.00, 1700.00, 1300.00, 2500.00, '2020-09-30');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Renault', 'Duster', 2022, '2022-04-12', '2022-04-12', 0, 98000.00, 28000.00, 17000.00, 9000.00, 13000.00, 3800.00, 4800.00, 2300.00, 1700.00, 3300.00, '2022-04-12');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Fiat', 'Argo', 2021, '2021-07-25', '2021-07-25', 0, 62000.00, 35000.60, 13000.00, 7200.00, 10000.00, 3000.00, 4000.00, 1850.00, 1350.00, 2600.00, '2021-07-25');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Jeep', 'Compass', 2023, '2023-02-14', '2023-02-14', 0, 145000.00, 12000.00, 20000.00, 10500.00, 15500.00, 4500.00, 5500.00, 2800.00, 2000.00, 3800.00, '2023-02-14');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('João', 'Silva', 'joao.silva@empresa.com', '(11) 98765-4321');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Maria', 'Santos', 'maria.santos@email.com.br', '(21) 99876-5432');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Carlos', 'Oliveira', 'carlos.oliveira@companhia.com', '(31) 97654-3210');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Ana', 'Costa', 'ana.costa@dominio.com', '(41) 96543-2109');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Pedro', 'Almeida', 'pedro.almeida@negocio.com', '(51) 95432-1098');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Juliana', 'Pereira', 'juliana.pereira@corp.com', '(61) 94321-0987');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Ricardo', 'Rodrigues', 'ricardo.rodrigues@firma.com', '(71) 93210-9876');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Fernanda', 'Lima', 'fernanda.lima@empresarial.com', '(81) 92109-8765');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Lucas', 'Gomes', 'lucas.gomes@comercial.com', '(85) 91098-7654');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Patrícia', 'Martins', 'patricia.martins@business.com', '(98) 90987-6543');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Roberto', 'Ferreira', 'roberto.ferreira@enterprise.com', '(67) 89876-5432');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Amanda', 'Rocha', 'amanda.rocha@group.com', '(27) 88765-4321');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Marcos', 'Barbosa', 'marcos.barbosa@holdings.com', '(19) 87654-3210');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Cristina', 'Nascimento', 'cristina.nascimento@corporation.com', '(47) 86543-2109');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Diego', 'Carvalho', 'diego.carvalho@international.com', '(92) 85432-1098');
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (550000.00, '2021-04-15', 'Venda realizada com sucesso. Cliente satisfeito.', 1, 1);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id ) VALUES (520000.00, '2020-07-25', 'Pagamento à vista. Documentação completa.', 2, 2);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id ) VALUES (580000.00, '2022-02-20', 'Financiamento aprovado. Entrega agendada.', 3, 3);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (480000.00, '2019-12-10', 'Venda com desconto especial. Cliente antigo.', 4, 4);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (570000.00, '2023-09-05', 'Documentação em dia. IPVA pago.', 5, 5);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (530000.00, '2021-06-20', 'Venda para concessionária. Lote fechado.', 6, 6);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (510000.00, '2020-11-15', 'Cliente particular. Pagamento parcelado.', 7, 7);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (560000.00, '2022-05-18', 'Exportação para Paraguai. Documentação internacional.', 8, 8);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (525000.00, '2021-08-30', 'Venda com garantia estendida. Revisão incluída.', 9, 9);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (590000.00, '2023-03-25', 'Cliente corporativo. Frota empresarial.', 10, 10);
INSERT INTO tb_permission (description) VALUES ('ROLE_ADMIN');
INSERT INTO tb_permission (description) VALUES ('ROLE_MANAGER');
INSERT INTO tb_permission (description) VALUES ('ROLE_COMMON_USER');
INSERT INTO tb_users (user_name, full_name, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES ('leandro', 'Leandro Costa', '1e3cdeeaaaeeda173ff6d002e7cb5e3f91ebc354dcff52156c9eaba1793a3a5e5bee306c11099e22', b'1', b'1', b'1', b'1');
INSERT INTO tb_users (user_name, full_name, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES ('flavio', 'Flavio Costa', '362ad02420268beeb22d3a1f0d92749df461d7f4b74c9433d7415bdeef1b2902f4eb1edaecb37cb3', b'1', b'1', b'1', b'1');
INSERT INTO tb_users (user_name, full_name, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES ('caleb', 'Marcelo Caleb', '4f3cf84ad6d14e085071cba3d078fc1c6826cdddaff891cfa305da954eb1302a18322ed92bcbdd0e', b'1', b'1', b'1', b'1');
INSERT INTO user_permission (user_id, permission_id) VALUES (1, 1);
INSERT INTO user_permission (user_id, permission_id) VALUES	(2, 1);
INSERT INTO user_permission (user_id, permission_id) VALUES (1, 2);
INSERT INTO user_permission (user_id, permission_id) VALUES (3, 1);
INSERT INTO user_permission (user_id, permission_id) VALUES (3, 2);

    create table tb_client (
        id bigint not null auto_increment,
        email varchar(255),
        first_name varchar(255),
        last_name varchar(255),
        phone varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_deposit (
        deposit_date date,
        due_date date,
        initial_deposit_value decimal(38,2),
        paid_installments integer,
        remaining_amount decimal(38,2),
        sale_value decimal(38,2),
        status tinyint,
        total_installments integer,
        client_id bigint,
        created_at datetime(6),
        updated_at datetime(6),
        vehicle_id bigint not null,
        observations varchar(255),
        primary key (vehicle_id)
    ) engine=InnoDB;

    create table tb_installment (
        due_date date,
        installment_number integer,
        payment_date date,
        status tinyint,
        value_per_installment decimal(38,2),
        created_at datetime(6),
        deposit_id bigint,
        id bigint not null auto_increment,
        updated_at datetime(6),
        observations varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_permission (
        id bigint not null auto_increment,
        description varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_sale (
        sale_date date,
        sale_value decimal(38,2),
        client_id bigint,
        vehicle_id bigint not null,
        observations varchar(255),
        primary key (vehicle_id)
    ) engine=InnoDB;

    create table tb_users (
        account_non_expired bit,
        account_non_locked bit,
        credentials_non_expired bit,
        enabled bit,
        id bigint not null auto_increment,
        full_name varchar(255),
        password varchar(255),
        user_name varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_vehicle (
        acquisition_date date,
        cp decimal(12,2),
        customs_broker decimal(12,2),
        driver decimal(12,2),
        innater decimal(12,2),
        inspection decimal(12,2),
        license_plate decimal(12,2),
        loading decimal(12,2),
        manufactured_year integer,
        order_date date,
        purchase_value decimal(12,2),
        registration_date date,
        rights decimal(12,2),
        status tinyint,
        travel decimal(12,2),
        id bigint not null auto_increment,
        brand varchar(255),
        model varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table user_permission (
        permission_id bigint not null,
        user_id bigint not null
    ) engine=InnoDB;

    alter table tb_deposit 
       add constraint UKa4iajkufddbjun5h2ftgybmeh unique (client_id);

    alter table tb_users 
       add constraint UKt3xry9rxngbbv1utykxcve9qt unique (user_name);

    alter table tb_deposit 
       add constraint FKher6ysca2kp2b29m84at4eiay 
       foreign key (client_id) 
       references tb_client (id);

    alter table tb_deposit 
       add constraint FKdqnt5455ig2k3ax6ngshh17v3 
       foreign key (vehicle_id) 
       references tb_vehicle (id);

    alter table tb_installment 
       add constraint FKg0ks3o2mthmqk1gx2arx7rv3a 
       foreign key (deposit_id) 
       references tb_deposit (vehicle_id);

    alter table tb_sale 
       add constraint FK43jttra653rb5w78icqgc9fub 
       foreign key (client_id) 
       references tb_client (id);

    alter table tb_sale 
       add constraint FKry4f3m5gy3tkyle92q4csrerb 
       foreign key (vehicle_id) 
       references tb_vehicle (id);

    alter table user_permission 
       add constraint FKdn4bu8iyay2kbab49i14b5sey 
       foreign key (permission_id) 
       references tb_permission (id);

    alter table user_permission 
       add constraint FKow2d74fs3qkvha007sxo1uv7s 
       foreign key (user_id) 
       references tb_users (id);
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Toyota', 'Corolla', 2021, '2021-03-15', '2021-03-15', 0, 85000.00, 25000.50, 15000.00, 8500.50, 12000.00, 3500.00, 4500.00, 2000.00, 1500.00, 3000.00, '2021-03-15');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Honda', 'Civic', 2020, '2020-06-20', '2020-06-20', 0, 78000.00, 32000.75, 13500.00, 7800.00, 11000.00, 3200.00, 4200.00, 1800.00, 1400.00, 2800.00, '2020-06-20');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Ford', 'Ranger', 2022, '2022-01-10', '2022-01-10', 0, 125000.00, 15000.00, 18000.00, 9500.00, 14000.00, 4000.00, 5000.00, 2500.00, 1800.00, 3500.00, '2022-01-10');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Chevrolet', 'Onix', 2019, '2019-11-05', '2019-11-05', 1, 45000.00, 68000.25, 10000.00, 6000.00, 8500.00, 2500.00, 3500.00, 1500.00, 1200.00, 2200.00, '2019-11-05');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Volkswagen', 'Polo', 2023, '2023-08-22', '2023-08-22', 0, 95000.00, 8500.00, 16500.00, 8800.00, 12500.00, 3600.00, 4600.00, 2100.00, 1600.00, 3100.00, '2023-08-22');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Nissan', 'Kicks', 2021, '2021-05-18', '2021-05-18', 0, 72000.00, 42000.50, 14000.00, 7500.00, 10500.00, 3100.00, 4100.00, 1900.00, 1450.00, 2700.00, '2021-05-18');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Hyundai', 'HB20', 2020, '2020-09-30', '2020-09-30', 1, 55000.00, 55000.80, 12000.00, 6800.00, 9500.00, 2800.00, 3800.00, 1700.00, 1300.00, 2500.00, '2020-09-30');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Renault', 'Duster', 2022, '2022-04-12', '2022-04-12', 0, 98000.00, 28000.00, 17000.00, 9000.00, 13000.00, 3800.00, 4800.00, 2300.00, 1700.00, 3300.00, '2022-04-12');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Fiat', 'Argo', 2021, '2021-07-25', '2021-07-25', 0, 62000.00, 35000.60, 13000.00, 7200.00, 10000.00, 3000.00, 4000.00, 1850.00, 1350.00, 2600.00, '2021-07-25');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Jeep', 'Compass', 2023, '2023-02-14', '2023-02-14', 0, 145000.00, 12000.00, 20000.00, 10500.00, 15500.00, 4500.00, 5500.00, 2800.00, 2000.00, 3800.00, '2023-02-14');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('João', 'Silva', 'joao.silva@empresa.com', '(11) 98765-4321');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Maria', 'Santos', 'maria.santos@email.com.br', '(21) 99876-5432');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Carlos', 'Oliveira', 'carlos.oliveira@companhia.com', '(31) 97654-3210');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Ana', 'Costa', 'ana.costa@dominio.com', '(41) 96543-2109');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Pedro', 'Almeida', 'pedro.almeida@negocio.com', '(51) 95432-1098');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Juliana', 'Pereira', 'juliana.pereira@corp.com', '(61) 94321-0987');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Ricardo', 'Rodrigues', 'ricardo.rodrigues@firma.com', '(71) 93210-9876');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Fernanda', 'Lima', 'fernanda.lima@empresarial.com', '(81) 92109-8765');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Lucas', 'Gomes', 'lucas.gomes@comercial.com', '(85) 91098-7654');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Patrícia', 'Martins', 'patricia.martins@business.com', '(98) 90987-6543');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Roberto', 'Ferreira', 'roberto.ferreira@enterprise.com', '(67) 89876-5432');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Amanda', 'Rocha', 'amanda.rocha@group.com', '(27) 88765-4321');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Marcos', 'Barbosa', 'marcos.barbosa@holdings.com', '(19) 87654-3210');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Cristina', 'Nascimento', 'cristina.nascimento@corporation.com', '(47) 86543-2109');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Diego', 'Carvalho', 'diego.carvalho@international.com', '(92) 85432-1098');
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (550000.00, '2021-04-15', 'Venda realizada com sucesso. Cliente satisfeito.', 1, 1);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id ) VALUES (520000.00, '2020-07-25', 'Pagamento à vista. Documentação completa.', 2, 2);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id ) VALUES (580000.00, '2022-02-20', 'Financiamento aprovado. Entrega agendada.', 3, 3);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (480000.00, '2019-12-10', 'Venda com desconto especial. Cliente antigo.', 4, 4);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (570000.00, '2023-09-05', 'Documentação em dia. IPVA pago.', 5, 5);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (530000.00, '2021-06-20', 'Venda para concessionária. Lote fechado.', 6, 6);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (510000.00, '2020-11-15', 'Cliente particular. Pagamento parcelado.', 7, 7);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (560000.00, '2022-05-18', 'Exportação para Paraguai. Documentação internacional.', 8, 8);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (525000.00, '2021-08-30', 'Venda com garantia estendida. Revisão incluída.', 9, 9);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (590000.00, '2023-03-25', 'Cliente corporativo. Frota empresarial.', 10, 10);
INSERT INTO tb_permission (description) VALUES ('ROLE_ADMIN');
INSERT INTO tb_permission (description) VALUES ('ROLE_MANAGER');
INSERT INTO tb_permission (description) VALUES ('ROLE_COMMON_USER');
INSERT INTO tb_users (user_name, full_name, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES ('leandro', 'Leandro Costa', '1e3cdeeaaaeeda173ff6d002e7cb5e3f91ebc354dcff52156c9eaba1793a3a5e5bee306c11099e22', b'1', b'1', b'1', b'1');
INSERT INTO tb_users (user_name, full_name, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES ('flavio', 'Flavio Costa', '362ad02420268beeb22d3a1f0d92749df461d7f4b74c9433d7415bdeef1b2902f4eb1edaecb37cb3', b'1', b'1', b'1', b'1');
INSERT INTO tb_users (user_name, full_name, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES ('caleb', 'Marcelo Caleb', '4f3cf84ad6d14e085071cba3d078fc1c6826cdddaff891cfa305da954eb1302a18322ed92bcbdd0e', b'1', b'1', b'1', b'1');
INSERT INTO user_permission (user_id, permission_id) VALUES (1, 1);
INSERT INTO user_permission (user_id, permission_id) VALUES	(2, 1);
INSERT INTO user_permission (user_id, permission_id) VALUES (1, 2);
INSERT INTO user_permission (user_id, permission_id) VALUES (3, 1);
INSERT INTO user_permission (user_id, permission_id) VALUES (3, 2);

    create table tb_client (
        id bigint not null auto_increment,
        email varchar(255),
        first_name varchar(255),
        last_name varchar(255),
        phone varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_deposit (
        deposit_date date,
        due_date date,
        initial_deposit_value decimal(38,2),
        paid_installments integer,
        remaining_amount decimal(38,2),
        sale_value decimal(38,2),
        status tinyint,
        total_installments integer,
        client_id bigint,
        created_at datetime(6),
        updated_at datetime(6),
        vehicle_id bigint not null,
        observations varchar(255),
        primary key (vehicle_id)
    ) engine=InnoDB;

    create table tb_installment (
        due_date date,
        installment_number integer,
        payment_date date,
        status tinyint,
        value_per_installment decimal(38,2),
        created_at datetime(6),
        deposit_id bigint,
        id bigint not null auto_increment,
        updated_at datetime(6),
        observations varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_permission (
        id bigint not null auto_increment,
        description varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_sale (
        sale_date date,
        sale_value decimal(38,2),
        client_id bigint,
        vehicle_id bigint not null,
        observations varchar(255),
        primary key (vehicle_id)
    ) engine=InnoDB;

    create table tb_users (
        account_non_expired bit,
        account_non_locked bit,
        credentials_non_expired bit,
        enabled bit,
        id bigint not null auto_increment,
        full_name varchar(255),
        password varchar(255),
        user_name varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_vehicle (
        acquisition_date date,
        cp decimal(12,2),
        customs_broker decimal(12,2),
        driver decimal(12,2),
        innater decimal(12,2),
        inspection decimal(12,2),
        license_plate decimal(12,2),
        loading decimal(12,2),
        manufactured_year integer,
        order_date date,
        purchase_value decimal(12,2),
        registration_date date,
        rights decimal(12,2),
        status tinyint,
        travel decimal(12,2),
        id bigint not null auto_increment,
        brand varchar(255),
        model varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table user_permission (
        permission_id bigint not null,
        user_id bigint not null
    ) engine=InnoDB;

    alter table tb_deposit 
       add constraint UKa4iajkufddbjun5h2ftgybmeh unique (client_id);

    alter table tb_users 
       add constraint UKt3xry9rxngbbv1utykxcve9qt unique (user_name);

    alter table tb_deposit 
       add constraint FKher6ysca2kp2b29m84at4eiay 
       foreign key (client_id) 
       references tb_client (id);

    alter table tb_deposit 
       add constraint FKdqnt5455ig2k3ax6ngshh17v3 
       foreign key (vehicle_id) 
       references tb_vehicle (id);

    alter table tb_installment 
       add constraint FKg0ks3o2mthmqk1gx2arx7rv3a 
       foreign key (deposit_id) 
       references tb_deposit (vehicle_id);

    alter table tb_sale 
       add constraint FK43jttra653rb5w78icqgc9fub 
       foreign key (client_id) 
       references tb_client (id);

    alter table tb_sale 
       add constraint FKry4f3m5gy3tkyle92q4csrerb 
       foreign key (vehicle_id) 
       references tb_vehicle (id);

    alter table user_permission 
       add constraint FKdn4bu8iyay2kbab49i14b5sey 
       foreign key (permission_id) 
       references tb_permission (id);

    alter table user_permission 
       add constraint FKow2d74fs3qkvha007sxo1uv7s 
       foreign key (user_id) 
       references tb_users (id);
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Toyota', 'Corolla', 2021, '2021-03-15', '2021-03-15', 0, 85000.00, 25000.50, 15000.00, 8500.50, 12000.00, 3500.00, 4500.00, 2000.00, 1500.00, 3000.00, '2021-03-15');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Honda', 'Civic', 2020, '2020-06-20', '2020-06-20', 0, 78000.00, 32000.75, 13500.00, 7800.00, 11000.00, 3200.00, 4200.00, 1800.00, 1400.00, 2800.00, '2020-06-20');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Ford', 'Ranger', 2022, '2022-01-10', '2022-01-10', 0, 125000.00, 15000.00, 18000.00, 9500.00, 14000.00, 4000.00, 5000.00, 2500.00, 1800.00, 3500.00, '2022-01-10');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Chevrolet', 'Onix', 2019, '2019-11-05', '2019-11-05', 1, 45000.00, 68000.25, 10000.00, 6000.00, 8500.00, 2500.00, 3500.00, 1500.00, 1200.00, 2200.00, '2019-11-05');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Volkswagen', 'Polo', 2023, '2023-08-22', '2023-08-22', 0, 95000.00, 8500.00, 16500.00, 8800.00, 12500.00, 3600.00, 4600.00, 2100.00, 1600.00, 3100.00, '2023-08-22');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Nissan', 'Kicks', 2021, '2021-05-18', '2021-05-18', 0, 72000.00, 42000.50, 14000.00, 7500.00, 10500.00, 3100.00, 4100.00, 1900.00, 1450.00, 2700.00, '2021-05-18');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Hyundai', 'HB20', 2020, '2020-09-30', '2020-09-30', 1, 55000.00, 55000.80, 12000.00, 6800.00, 9500.00, 2800.00, 3800.00, 1700.00, 1300.00, 2500.00, '2020-09-30');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Renault', 'Duster', 2022, '2022-04-12', '2022-04-12', 0, 98000.00, 28000.00, 17000.00, 9000.00, 13000.00, 3800.00, 4800.00, 2300.00, 1700.00, 3300.00, '2022-04-12');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Fiat', 'Argo', 2021, '2021-07-25', '2021-07-25', 0, 62000.00, 35000.60, 13000.00, 7200.00, 10000.00, 3000.00, 4000.00, 1850.00, 1350.00, 2600.00, '2021-07-25');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Jeep', 'Compass', 2023, '2023-02-14', '2023-02-14', 0, 145000.00, 12000.00, 20000.00, 10500.00, 15500.00, 4500.00, 5500.00, 2800.00, 2000.00, 3800.00, '2023-02-14');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('João', 'Silva', 'joao.silva@empresa.com', '(11) 98765-4321');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Maria', 'Santos', 'maria.santos@email.com.br', '(21) 99876-5432');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Carlos', 'Oliveira', 'carlos.oliveira@companhia.com', '(31) 97654-3210');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Ana', 'Costa', 'ana.costa@dominio.com', '(41) 96543-2109');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Pedro', 'Almeida', 'pedro.almeida@negocio.com', '(51) 95432-1098');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Juliana', 'Pereira', 'juliana.pereira@corp.com', '(61) 94321-0987');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Ricardo', 'Rodrigues', 'ricardo.rodrigues@firma.com', '(71) 93210-9876');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Fernanda', 'Lima', 'fernanda.lima@empresarial.com', '(81) 92109-8765');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Lucas', 'Gomes', 'lucas.gomes@comercial.com', '(85) 91098-7654');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Patrícia', 'Martins', 'patricia.martins@business.com', '(98) 90987-6543');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Roberto', 'Ferreira', 'roberto.ferreira@enterprise.com', '(67) 89876-5432');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Amanda', 'Rocha', 'amanda.rocha@group.com', '(27) 88765-4321');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Marcos', 'Barbosa', 'marcos.barbosa@holdings.com', '(19) 87654-3210');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Cristina', 'Nascimento', 'cristina.nascimento@corporation.com', '(47) 86543-2109');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Diego', 'Carvalho', 'diego.carvalho@international.com', '(92) 85432-1098');
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (550000.00, '2021-04-15', 'Venda realizada com sucesso. Cliente satisfeito.', 1, 1);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id ) VALUES (520000.00, '2020-07-25', 'Pagamento à vista. Documentação completa.', 2, 2);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id ) VALUES (580000.00, '2022-02-20', 'Financiamento aprovado. Entrega agendada.', 3, 3);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (480000.00, '2019-12-10', 'Venda com desconto especial. Cliente antigo.', 4, 4);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (570000.00, '2023-09-05', 'Documentação em dia. IPVA pago.', 5, 5);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (530000.00, '2021-06-20', 'Venda para concessionária. Lote fechado.', 6, 6);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (510000.00, '2020-11-15', 'Cliente particular. Pagamento parcelado.', 7, 7);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (560000.00, '2022-05-18', 'Exportação para Paraguai. Documentação internacional.', 8, 8);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (525000.00, '2021-08-30', 'Venda com garantia estendida. Revisão incluída.', 9, 9);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (590000.00, '2023-03-25', 'Cliente corporativo. Frota empresarial.', 10, 10);
INSERT INTO tb_permission (description) VALUES ('ROLE_ADMIN');
INSERT INTO tb_permission (description) VALUES ('ROLE_MANAGER');
INSERT INTO tb_permission (description) VALUES ('ROLE_COMMON_USER');
INSERT INTO tb_users (user_name, full_name, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES ('leandro', 'Leandro Costa', '1e3cdeeaaaeeda173ff6d002e7cb5e3f91ebc354dcff52156c9eaba1793a3a5e5bee306c11099e22', b'1', b'1', b'1', b'1');
INSERT INTO tb_users (user_name, full_name, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES ('flavio', 'Flavio Costa', '362ad02420268beeb22d3a1f0d92749df461d7f4b74c9433d7415bdeef1b2902f4eb1edaecb37cb3', b'1', b'1', b'1', b'1');
INSERT INTO tb_users (user_name, full_name, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES ('caleb', 'Marcelo Caleb', '4f3cf84ad6d14e085071cba3d078fc1c6826cdddaff891cfa305da954eb1302a18322ed92bcbdd0e', b'1', b'1', b'1', b'1');
INSERT INTO user_permission (user_id, permission_id) VALUES (1, 1);
INSERT INTO user_permission (user_id, permission_id) VALUES	(2, 1);
INSERT INTO user_permission (user_id, permission_id) VALUES (1, 2);
INSERT INTO user_permission (user_id, permission_id) VALUES (3, 1);
INSERT INTO user_permission (user_id, permission_id) VALUES (3, 2);

    create table tb_client (
        id bigint not null auto_increment,
        email varchar(255),
        first_name varchar(255),
        last_name varchar(255),
        phone varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_deposit (
        deposit_date date,
        due_date date,
        initial_deposit_value decimal(38,2),
        paid_installments integer,
        remaining_amount decimal(38,2),
        sale_value decimal(38,2),
        status tinyint,
        total_installments integer,
        client_id bigint,
        created_at datetime(6),
        updated_at datetime(6),
        vehicle_id bigint not null,
        observations varchar(255),
        primary key (vehicle_id)
    ) engine=InnoDB;

    create table tb_installment (
        due_date date,
        installment_number integer,
        payment_date date,
        status tinyint,
        value_per_installment decimal(38,2),
        created_at datetime(6),
        deposit_id bigint,
        id bigint not null auto_increment,
        updated_at datetime(6),
        observations varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_permission (
        id bigint not null auto_increment,
        description varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_sale (
        sale_date date,
        sale_value decimal(38,2),
        client_id bigint,
        vehicle_id bigint not null,
        observations varchar(255),
        primary key (vehicle_id)
    ) engine=InnoDB;

    create table tb_users (
        account_non_expired bit,
        account_non_locked bit,
        credentials_non_expired bit,
        enabled bit,
        id bigint not null auto_increment,
        full_name varchar(255),
        password varchar(255),
        user_name varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_vehicle (
        acquisition_date date,
        cp decimal(12,2),
        customs_broker decimal(12,2),
        driver decimal(12,2),
        innater decimal(12,2),
        inspection decimal(12,2),
        license_plate decimal(12,2),
        loading decimal(12,2),
        manufactured_year integer,
        order_date date,
        purchase_value decimal(12,2),
        registration_date date,
        rights decimal(12,2),
        status tinyint,
        travel decimal(12,2),
        id bigint not null auto_increment,
        brand varchar(255),
        model varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table user_permission (
        permission_id bigint not null,
        user_id bigint not null
    ) engine=InnoDB;

    alter table tb_deposit 
       add constraint UKa4iajkufddbjun5h2ftgybmeh unique (client_id);

    alter table tb_users 
       add constraint UKt3xry9rxngbbv1utykxcve9qt unique (user_name);

    alter table tb_deposit 
       add constraint FKher6ysca2kp2b29m84at4eiay 
       foreign key (client_id) 
       references tb_client (id);

    alter table tb_deposit 
       add constraint FKdqnt5455ig2k3ax6ngshh17v3 
       foreign key (vehicle_id) 
       references tb_vehicle (id);

    alter table tb_installment 
       add constraint FKg0ks3o2mthmqk1gx2arx7rv3a 
       foreign key (deposit_id) 
       references tb_deposit (vehicle_id);

    alter table tb_sale 
       add constraint FK43jttra653rb5w78icqgc9fub 
       foreign key (client_id) 
       references tb_client (id);

    alter table tb_sale 
       add constraint FKry4f3m5gy3tkyle92q4csrerb 
       foreign key (vehicle_id) 
       references tb_vehicle (id);

    alter table user_permission 
       add constraint FKdn4bu8iyay2kbab49i14b5sey 
       foreign key (permission_id) 
       references tb_permission (id);

    alter table user_permission 
       add constraint FKow2d74fs3qkvha007sxo1uv7s 
       foreign key (user_id) 
       references tb_users (id);
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Toyota', 'Corolla', 2021, '2021-03-15', '2021-03-15', 0, 85000.00, 25000.50, 15000.00, 8500.50, 12000.00, 3500.00, 4500.00, 2000.00, 1500.00, 3000.00, '2021-03-15');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Honda', 'Civic', 2020, '2020-06-20', '2020-06-20', 0, 78000.00, 32000.75, 13500.00, 7800.00, 11000.00, 3200.00, 4200.00, 1800.00, 1400.00, 2800.00, '2020-06-20');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Ford', 'Ranger', 2022, '2022-01-10', '2022-01-10', 0, 125000.00, 15000.00, 18000.00, 9500.00, 14000.00, 4000.00, 5000.00, 2500.00, 1800.00, 3500.00, '2022-01-10');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Chevrolet', 'Onix', 2019, '2019-11-05', '2019-11-05', 1, 45000.00, 68000.25, 10000.00, 6000.00, 8500.00, 2500.00, 3500.00, 1500.00, 1200.00, 2200.00, '2019-11-05');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Volkswagen', 'Polo', 2023, '2023-08-22', '2023-08-22', 0, 95000.00, 8500.00, 16500.00, 8800.00, 12500.00, 3600.00, 4600.00, 2100.00, 1600.00, 3100.00, '2023-08-22');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Nissan', 'Kicks', 2021, '2021-05-18', '2021-05-18', 0, 72000.00, 42000.50, 14000.00, 7500.00, 10500.00, 3100.00, 4100.00, 1900.00, 1450.00, 2700.00, '2021-05-18');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Hyundai', 'HB20', 2020, '2020-09-30', '2020-09-30', 1, 55000.00, 55000.80, 12000.00, 6800.00, 9500.00, 2800.00, 3800.00, 1700.00, 1300.00, 2500.00, '2020-09-30');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Renault', 'Duster', 2022, '2022-04-12', '2022-04-12', 0, 98000.00, 28000.00, 17000.00, 9000.00, 13000.00, 3800.00, 4800.00, 2300.00, 1700.00, 3300.00, '2022-04-12');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Fiat', 'Argo', 2021, '2021-07-25', '2021-07-25', 0, 62000.00, 35000.60, 13000.00, 7200.00, 10000.00, 3000.00, 4000.00, 1850.00, 1350.00, 2600.00, '2021-07-25');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Jeep', 'Compass', 2023, '2023-02-14', '2023-02-14', 0, 145000.00, 12000.00, 20000.00, 10500.00, 15500.00, 4500.00, 5500.00, 2800.00, 2000.00, 3800.00, '2023-02-14');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('João', 'Silva', 'joao.silva@empresa.com', '(11) 98765-4321');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Maria', 'Santos', 'maria.santos@email.com.br', '(21) 99876-5432');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Carlos', 'Oliveira', 'carlos.oliveira@companhia.com', '(31) 97654-3210');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Ana', 'Costa', 'ana.costa@dominio.com', '(41) 96543-2109');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Pedro', 'Almeida', 'pedro.almeida@negocio.com', '(51) 95432-1098');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Juliana', 'Pereira', 'juliana.pereira@corp.com', '(61) 94321-0987');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Ricardo', 'Rodrigues', 'ricardo.rodrigues@firma.com', '(71) 93210-9876');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Fernanda', 'Lima', 'fernanda.lima@empresarial.com', '(81) 92109-8765');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Lucas', 'Gomes', 'lucas.gomes@comercial.com', '(85) 91098-7654');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Patrícia', 'Martins', 'patricia.martins@business.com', '(98) 90987-6543');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Roberto', 'Ferreira', 'roberto.ferreira@enterprise.com', '(67) 89876-5432');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Amanda', 'Rocha', 'amanda.rocha@group.com', '(27) 88765-4321');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Marcos', 'Barbosa', 'marcos.barbosa@holdings.com', '(19) 87654-3210');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Cristina', 'Nascimento', 'cristina.nascimento@corporation.com', '(47) 86543-2109');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Diego', 'Carvalho', 'diego.carvalho@international.com', '(92) 85432-1098');
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (550000.00, '2021-04-15', 'Venda realizada com sucesso. Cliente satisfeito.', 1, 1);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id ) VALUES (520000.00, '2020-07-25', 'Pagamento à vista. Documentação completa.', 2, 2);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id ) VALUES (580000.00, '2022-02-20', 'Financiamento aprovado. Entrega agendada.', 3, 3);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (480000.00, '2019-12-10', 'Venda com desconto especial. Cliente antigo.', 4, 4);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (570000.00, '2023-09-05', 'Documentação em dia. IPVA pago.', 5, 5);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (530000.00, '2021-06-20', 'Venda para concessionária. Lote fechado.', 6, 6);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (510000.00, '2020-11-15', 'Cliente particular. Pagamento parcelado.', 7, 7);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (560000.00, '2022-05-18', 'Exportação para Paraguai. Documentação internacional.', 8, 8);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (525000.00, '2021-08-30', 'Venda com garantia estendida. Revisão incluída.', 9, 9);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (590000.00, '2023-03-25', 'Cliente corporativo. Frota empresarial.', 10, 10);
INSERT INTO tb_permission (description) VALUES ('ROLE_ADMIN');
INSERT INTO tb_permission (description) VALUES ('ROLE_MANAGER');
INSERT INTO tb_permission (description) VALUES ('ROLE_COMMON_USER');
INSERT INTO tb_users (user_name, full_name, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES ('leandro', 'Leandro Costa', '1e3cdeeaaaeeda173ff6d002e7cb5e3f91ebc354dcff52156c9eaba1793a3a5e5bee306c11099e22', b'1', b'1', b'1', b'1');
INSERT INTO tb_users (user_name, full_name, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES ('flavio', 'Flavio Costa', '362ad02420268beeb22d3a1f0d92749df461d7f4b74c9433d7415bdeef1b2902f4eb1edaecb37cb3', b'1', b'1', b'1', b'1');
INSERT INTO tb_users (user_name, full_name, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES ('caleb', 'Marcelo Caleb', '4f3cf84ad6d14e085071cba3d078fc1c6826cdddaff891cfa305da954eb1302a18322ed92bcbdd0e', b'1', b'1', b'1', b'1');
INSERT INTO user_permission (user_id, permission_id) VALUES (1, 1);
INSERT INTO user_permission (user_id, permission_id) VALUES	(2, 1);
INSERT INTO user_permission (user_id, permission_id) VALUES (1, 2);
INSERT INTO user_permission (user_id, permission_id) VALUES (3, 1);
INSERT INTO user_permission (user_id, permission_id) VALUES (3, 2);

    create table tb_client (
        id bigint not null auto_increment,
        email varchar(255),
        first_name varchar(255),
        last_name varchar(255),
        phone varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_deposit (
        deposit_date date,
        due_date date,
        initial_deposit_value decimal(38,2),
        paid_installments integer,
        remaining_amount decimal(38,2),
        sale_value decimal(38,2),
        status tinyint,
        total_installments integer,
        client_id bigint,
        created_at datetime(6),
        updated_at datetime(6),
        vehicle_id bigint not null,
        observations varchar(255),
        primary key (vehicle_id)
    ) engine=InnoDB;

    create table tb_installment (
        due_date date,
        installment_number integer,
        payment_date date,
        status tinyint,
        value_per_installment decimal(38,2),
        created_at datetime(6),
        deposit_id bigint,
        id bigint not null auto_increment,
        updated_at datetime(6),
        observations varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_permission (
        id bigint not null auto_increment,
        description varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_sale (
        sale_date date,
        sale_value decimal(38,2),
        client_id bigint,
        vehicle_id bigint not null,
        observations varchar(255),
        primary key (vehicle_id)
    ) engine=InnoDB;

    create table tb_users (
        account_non_expired bit,
        account_non_locked bit,
        credentials_non_expired bit,
        enabled bit,
        id bigint not null auto_increment,
        full_name varchar(255),
        password varchar(255),
        user_name varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_vehicle (
        acquisition_date date,
        cp decimal(12,2),
        customs_broker decimal(12,2),
        driver decimal(12,2),
        innater decimal(12,2),
        inspection decimal(12,2),
        license_plate decimal(12,2),
        loading decimal(12,2),
        manufactured_year integer,
        order_date date,
        purchase_value decimal(12,2),
        registration_date date,
        rights decimal(12,2),
        status tinyint,
        travel decimal(12,2),
        id bigint not null auto_increment,
        brand varchar(255),
        model varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table user_permission (
        permission_id bigint not null,
        user_id bigint not null
    ) engine=InnoDB;

    alter table tb_deposit 
       add constraint UKa4iajkufddbjun5h2ftgybmeh unique (client_id);

    alter table tb_users 
       add constraint UKt3xry9rxngbbv1utykxcve9qt unique (user_name);

    alter table tb_deposit 
       add constraint FKher6ysca2kp2b29m84at4eiay 
       foreign key (client_id) 
       references tb_client (id);

    alter table tb_deposit 
       add constraint FKdqnt5455ig2k3ax6ngshh17v3 
       foreign key (vehicle_id) 
       references tb_vehicle (id);

    alter table tb_installment 
       add constraint FKg0ks3o2mthmqk1gx2arx7rv3a 
       foreign key (deposit_id) 
       references tb_deposit (vehicle_id);

    alter table tb_sale 
       add constraint FK43jttra653rb5w78icqgc9fub 
       foreign key (client_id) 
       references tb_client (id);

    alter table tb_sale 
       add constraint FKry4f3m5gy3tkyle92q4csrerb 
       foreign key (vehicle_id) 
       references tb_vehicle (id);

    alter table user_permission 
       add constraint FKdn4bu8iyay2kbab49i14b5sey 
       foreign key (permission_id) 
       references tb_permission (id);

    alter table user_permission 
       add constraint FKow2d74fs3qkvha007sxo1uv7s 
       foreign key (user_id) 
       references tb_users (id);
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Toyota', 'Corolla', 2021, '2021-03-15', '2021-03-15', 0, 85000.00, 25000.50, 15000.00, 8500.50, 12000.00, 3500.00, 4500.00, 2000.00, 1500.00, 3000.00, '2021-03-15');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Honda', 'Civic', 2020, '2020-06-20', '2020-06-20', 0, 78000.00, 32000.75, 13500.00, 7800.00, 11000.00, 3200.00, 4200.00, 1800.00, 1400.00, 2800.00, '2020-06-20');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Ford', 'Ranger', 2022, '2022-01-10', '2022-01-10', 0, 125000.00, 15000.00, 18000.00, 9500.00, 14000.00, 4000.00, 5000.00, 2500.00, 1800.00, 3500.00, '2022-01-10');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Chevrolet', 'Onix', 2019, '2019-11-05', '2019-11-05', 1, 45000.00, 68000.25, 10000.00, 6000.00, 8500.00, 2500.00, 3500.00, 1500.00, 1200.00, 2200.00, '2019-11-05');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Volkswagen', 'Polo', 2023, '2023-08-22', '2023-08-22', 0, 95000.00, 8500.00, 16500.00, 8800.00, 12500.00, 3600.00, 4600.00, 2100.00, 1600.00, 3100.00, '2023-08-22');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Nissan', 'Kicks', 2021, '2021-05-18', '2021-05-18', 0, 72000.00, 42000.50, 14000.00, 7500.00, 10500.00, 3100.00, 4100.00, 1900.00, 1450.00, 2700.00, '2021-05-18');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Hyundai', 'HB20', 2020, '2020-09-30', '2020-09-30', 1, 55000.00, 55000.80, 12000.00, 6800.00, 9500.00, 2800.00, 3800.00, 1700.00, 1300.00, 2500.00, '2020-09-30');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Renault', 'Duster', 2022, '2022-04-12', '2022-04-12', 0, 98000.00, 28000.00, 17000.00, 9000.00, 13000.00, 3800.00, 4800.00, 2300.00, 1700.00, 3300.00, '2022-04-12');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Fiat', 'Argo', 2021, '2021-07-25', '2021-07-25', 0, 62000.00, 35000.60, 13000.00, 7200.00, 10000.00, 3000.00, 4000.00, 1850.00, 1350.00, 2600.00, '2021-07-25');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Jeep', 'Compass', 2023, '2023-02-14', '2023-02-14', 0, 145000.00, 12000.00, 20000.00, 10500.00, 15500.00, 4500.00, 5500.00, 2800.00, 2000.00, 3800.00, '2023-02-14');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('João', 'Silva', 'joao.silva@empresa.com', '(11) 98765-4321');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Maria', 'Santos', 'maria.santos@email.com.br', '(21) 99876-5432');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Carlos', 'Oliveira', 'carlos.oliveira@companhia.com', '(31) 97654-3210');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Ana', 'Costa', 'ana.costa@dominio.com', '(41) 96543-2109');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Pedro', 'Almeida', 'pedro.almeida@negocio.com', '(51) 95432-1098');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Juliana', 'Pereira', 'juliana.pereira@corp.com', '(61) 94321-0987');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Ricardo', 'Rodrigues', 'ricardo.rodrigues@firma.com', '(71) 93210-9876');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Fernanda', 'Lima', 'fernanda.lima@empresarial.com', '(81) 92109-8765');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Lucas', 'Gomes', 'lucas.gomes@comercial.com', '(85) 91098-7654');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Patrícia', 'Martins', 'patricia.martins@business.com', '(98) 90987-6543');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Roberto', 'Ferreira', 'roberto.ferreira@enterprise.com', '(67) 89876-5432');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Amanda', 'Rocha', 'amanda.rocha@group.com', '(27) 88765-4321');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Marcos', 'Barbosa', 'marcos.barbosa@holdings.com', '(19) 87654-3210');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Cristina', 'Nascimento', 'cristina.nascimento@corporation.com', '(47) 86543-2109');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Diego', 'Carvalho', 'diego.carvalho@international.com', '(92) 85432-1098');
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (550000.00, '2021-04-15', 'Venda realizada com sucesso. Cliente satisfeito.', 1, 1);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id ) VALUES (520000.00, '2020-07-25', 'Pagamento à vista. Documentação completa.', 2, 2);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id ) VALUES (580000.00, '2022-02-20', 'Financiamento aprovado. Entrega agendada.', 3, 3);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (480000.00, '2019-12-10', 'Venda com desconto especial. Cliente antigo.', 4, 4);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (570000.00, '2023-09-05', 'Documentação em dia. IPVA pago.', 5, 5);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (530000.00, '2021-06-20', 'Venda para concessionária. Lote fechado.', 6, 6);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (510000.00, '2020-11-15', 'Cliente particular. Pagamento parcelado.', 7, 7);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (560000.00, '2022-05-18', 'Exportação para Paraguai. Documentação internacional.', 8, 8);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (525000.00, '2021-08-30', 'Venda com garantia estendida. Revisão incluída.', 9, 9);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (590000.00, '2023-03-25', 'Cliente corporativo. Frota empresarial.', 10, 10);
INSERT INTO tb_permission (description) VALUES ('ROLE_ADMIN');
INSERT INTO tb_permission (description) VALUES ('ROLE_MANAGER');
INSERT INTO tb_permission (description) VALUES ('ROLE_COMMON_USER');
INSERT INTO tb_users (user_name, full_name, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES ('leandro', 'Leandro Costa', '1e3cdeeaaaeeda173ff6d002e7cb5e3f91ebc354dcff52156c9eaba1793a3a5e5bee306c11099e22', b'1', b'1', b'1', b'1');
INSERT INTO tb_users (user_name, full_name, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES ('flavio', 'Flavio Costa', '362ad02420268beeb22d3a1f0d92749df461d7f4b74c9433d7415bdeef1b2902f4eb1edaecb37cb3', b'1', b'1', b'1', b'1');
INSERT INTO tb_users (user_name, full_name, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES ('caleb', 'Marcelo Caleb', '4f3cf84ad6d14e085071cba3d078fc1c6826cdddaff891cfa305da954eb1302a18322ed92bcbdd0e', b'1', b'1', b'1', b'1');
INSERT INTO user_permission (user_id, permission_id) VALUES (1, 1);
INSERT INTO user_permission (user_id, permission_id) VALUES	(2, 1);
INSERT INTO user_permission (user_id, permission_id) VALUES (1, 2);
INSERT INTO user_permission (user_id, permission_id) VALUES (3, 1);
INSERT INTO user_permission (user_id, permission_id) VALUES (3, 2);

    create table tb_client (
        id bigint not null auto_increment,
        email varchar(255),
        first_name varchar(255),
        last_name varchar(255),
        phone varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_deposit (
        deposit_date date,
        due_date date,
        initial_deposit_value decimal(38,2),
        paid_installments integer,
        remaining_amount decimal(38,2),
        sale_value decimal(38,2),
        status tinyint,
        total_installments integer,
        client_id bigint,
        created_at datetime(6),
        updated_at datetime(6),
        vehicle_id bigint not null,
        observations varchar(255),
        primary key (vehicle_id)
    ) engine=InnoDB;

    create table tb_installment (
        due_date date,
        installment_number integer,
        payment_date date,
        status tinyint,
        value_per_installment decimal(38,2),
        created_at datetime(6),
        deposit_id bigint,
        id bigint not null auto_increment,
        updated_at datetime(6),
        observations varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_permission (
        id bigint not null auto_increment,
        description varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_sale (
        sale_date date,
        sale_value decimal(38,2),
        client_id bigint,
        vehicle_id bigint not null,
        observations varchar(255),
        primary key (vehicle_id)
    ) engine=InnoDB;

    create table tb_users (
        account_non_expired bit,
        account_non_locked bit,
        credentials_non_expired bit,
        enabled bit,
        id bigint not null auto_increment,
        full_name varchar(255),
        password varchar(255),
        user_name varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_vehicle (
        acquisition_date date,
        cp decimal(12,2),
        customs_broker decimal(12,2),
        driver decimal(12,2),
        innater decimal(12,2),
        inspection decimal(12,2),
        license_plate decimal(12,2),
        loading decimal(12,2),
        manufactured_year integer,
        order_date date,
        purchase_value decimal(12,2),
        registration_date date,
        rights decimal(12,2),
        status tinyint,
        travel decimal(12,2),
        id bigint not null auto_increment,
        brand varchar(255),
        model varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table user_permission (
        permission_id bigint not null,
        user_id bigint not null
    ) engine=InnoDB;

    alter table tb_deposit 
       add constraint UKa4iajkufddbjun5h2ftgybmeh unique (client_id);

    alter table tb_users 
       add constraint UKt3xry9rxngbbv1utykxcve9qt unique (user_name);

    alter table tb_deposit 
       add constraint FKher6ysca2kp2b29m84at4eiay 
       foreign key (client_id) 
       references tb_client (id);

    alter table tb_deposit 
       add constraint FKdqnt5455ig2k3ax6ngshh17v3 
       foreign key (vehicle_id) 
       references tb_vehicle (id);

    alter table tb_installment 
       add constraint FKg0ks3o2mthmqk1gx2arx7rv3a 
       foreign key (deposit_id) 
       references tb_deposit (vehicle_id);

    alter table tb_sale 
       add constraint FK43jttra653rb5w78icqgc9fub 
       foreign key (client_id) 
       references tb_client (id);

    alter table tb_sale 
       add constraint FKry4f3m5gy3tkyle92q4csrerb 
       foreign key (vehicle_id) 
       references tb_vehicle (id);

    alter table user_permission 
       add constraint FKdn4bu8iyay2kbab49i14b5sey 
       foreign key (permission_id) 
       references tb_permission (id);

    alter table user_permission 
       add constraint FKow2d74fs3qkvha007sxo1uv7s 
       foreign key (user_id) 
       references tb_users (id);
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Toyota', 'Corolla', 2021, '2021-03-15', '2021-03-15', 0, 85000.00, 25000.50, 15000.00, 8500.50, 12000.00, 3500.00, 4500.00, 2000.00, 1500.00, 3000.00, '2021-03-15');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Honda', 'Civic', 2020, '2020-06-20', '2020-06-20', 0, 78000.00, 32000.75, 13500.00, 7800.00, 11000.00, 3200.00, 4200.00, 1800.00, 1400.00, 2800.00, '2020-06-20');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Ford', 'Ranger', 2022, '2022-01-10', '2022-01-10', 0, 125000.00, 15000.00, 18000.00, 9500.00, 14000.00, 4000.00, 5000.00, 2500.00, 1800.00, 3500.00, '2022-01-10');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Chevrolet', 'Onix', 2019, '2019-11-05', '2019-11-05', 1, 45000.00, 68000.25, 10000.00, 6000.00, 8500.00, 2500.00, 3500.00, 1500.00, 1200.00, 2200.00, '2019-11-05');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Volkswagen', 'Polo', 2023, '2023-08-22', '2023-08-22', 0, 95000.00, 8500.00, 16500.00, 8800.00, 12500.00, 3600.00, 4600.00, 2100.00, 1600.00, 3100.00, '2023-08-22');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Nissan', 'Kicks', 2021, '2021-05-18', '2021-05-18', 0, 72000.00, 42000.50, 14000.00, 7500.00, 10500.00, 3100.00, 4100.00, 1900.00, 1450.00, 2700.00, '2021-05-18');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Hyundai', 'HB20', 2020, '2020-09-30', '2020-09-30', 1, 55000.00, 55000.80, 12000.00, 6800.00, 9500.00, 2800.00, 3800.00, 1700.00, 1300.00, 2500.00, '2020-09-30');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Renault', 'Duster', 2022, '2022-04-12', '2022-04-12', 0, 98000.00, 28000.00, 17000.00, 9000.00, 13000.00, 3800.00, 4800.00, 2300.00, 1700.00, 3300.00, '2022-04-12');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Fiat', 'Argo', 2021, '2021-07-25', '2021-07-25', 0, 62000.00, 35000.60, 13000.00, 7200.00, 10000.00, 3000.00, 4000.00, 1850.00, 1350.00, 2600.00, '2021-07-25');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Jeep', 'Compass', 2023, '2023-02-14', '2023-02-14', 0, 145000.00, 12000.00, 20000.00, 10500.00, 15500.00, 4500.00, 5500.00, 2800.00, 2000.00, 3800.00, '2023-02-14');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('João', 'Silva', 'joao.silva@empresa.com', '(11) 98765-4321');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Maria', 'Santos', 'maria.santos@email.com.br', '(21) 99876-5432');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Carlos', 'Oliveira', 'carlos.oliveira@companhia.com', '(31) 97654-3210');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Ana', 'Costa', 'ana.costa@dominio.com', '(41) 96543-2109');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Pedro', 'Almeida', 'pedro.almeida@negocio.com', '(51) 95432-1098');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Juliana', 'Pereira', 'juliana.pereira@corp.com', '(61) 94321-0987');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Ricardo', 'Rodrigues', 'ricardo.rodrigues@firma.com', '(71) 93210-9876');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Fernanda', 'Lima', 'fernanda.lima@empresarial.com', '(81) 92109-8765');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Lucas', 'Gomes', 'lucas.gomes@comercial.com', '(85) 91098-7654');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Patrícia', 'Martins', 'patricia.martins@business.com', '(98) 90987-6543');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Roberto', 'Ferreira', 'roberto.ferreira@enterprise.com', '(67) 89876-5432');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Amanda', 'Rocha', 'amanda.rocha@group.com', '(27) 88765-4321');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Marcos', 'Barbosa', 'marcos.barbosa@holdings.com', '(19) 87654-3210');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Cristina', 'Nascimento', 'cristina.nascimento@corporation.com', '(47) 86543-2109');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Diego', 'Carvalho', 'diego.carvalho@international.com', '(92) 85432-1098');
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (550000.00, '2021-04-15', 'Venda realizada com sucesso. Cliente satisfeito.', 1, 1);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id ) VALUES (520000.00, '2020-07-25', 'Pagamento à vista. Documentação completa.', 2, 2);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id ) VALUES (580000.00, '2022-02-20', 'Financiamento aprovado. Entrega agendada.', 3, 3);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (480000.00, '2019-12-10', 'Venda com desconto especial. Cliente antigo.', 4, 4);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (570000.00, '2023-09-05', 'Documentação em dia. IPVA pago.', 5, 5);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (530000.00, '2021-06-20', 'Venda para concessionária. Lote fechado.', 6, 6);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (510000.00, '2020-11-15', 'Cliente particular. Pagamento parcelado.', 7, 7);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (560000.00, '2022-05-18', 'Exportação para Paraguai. Documentação internacional.', 8, 8);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (525000.00, '2021-08-30', 'Venda com garantia estendida. Revisão incluída.', 9, 9);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (590000.00, '2023-03-25', 'Cliente corporativo. Frota empresarial.', 10, 10);
INSERT INTO tb_permission (description) VALUES ('ROLE_ADMIN');
INSERT INTO tb_permission (description) VALUES ('ROLE_MANAGER');
INSERT INTO tb_permission (description) VALUES ('ROLE_COMMON_USER');
INSERT INTO tb_users (user_name, full_name, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES ('leandro', 'Leandro Costa', '1e3cdeeaaaeeda173ff6d002e7cb5e3f91ebc354dcff52156c9eaba1793a3a5e5bee306c11099e22', b'1', b'1', b'1', b'1');
INSERT INTO tb_users (user_name, full_name, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES ('flavio', 'Flavio Costa', '362ad02420268beeb22d3a1f0d92749df461d7f4b74c9433d7415bdeef1b2902f4eb1edaecb37cb3', b'1', b'1', b'1', b'1');
INSERT INTO tb_users (user_name, full_name, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES ('caleb', 'Marcelo Caleb', '4f3cf84ad6d14e085071cba3d078fc1c6826cdddaff891cfa305da954eb1302a18322ed92bcbdd0e', b'1', b'1', b'1', b'1');
INSERT INTO user_permission (user_id, permission_id) VALUES (1, 1);
INSERT INTO user_permission (user_id, permission_id) VALUES	(2, 1);
INSERT INTO user_permission (user_id, permission_id) VALUES (1, 2);
INSERT INTO user_permission (user_id, permission_id) VALUES (3, 1);
INSERT INTO user_permission (user_id, permission_id) VALUES (3, 2);

    create table tb_client (
        id bigint not null auto_increment,
        email varchar(255),
        first_name varchar(255),
        last_name varchar(255),
        phone varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_deposit (
        deposit_date date,
        due_date date,
        initial_deposit_value decimal(38,2),
        paid_installments integer,
        remaining_amount decimal(38,2),
        sale_value decimal(38,2),
        status tinyint,
        total_installments integer,
        client_id bigint,
        created_at datetime(6),
        updated_at datetime(6),
        vehicle_id bigint not null,
        observations varchar(255),
        primary key (vehicle_id)
    ) engine=InnoDB;

    create table tb_installment (
        due_date date,
        installment_number integer,
        payment_date date,
        status tinyint,
        value_per_installment decimal(38,2),
        created_at datetime(6),
        deposit_id bigint,
        id bigint not null auto_increment,
        updated_at datetime(6),
        observations varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_permission (
        id bigint not null auto_increment,
        description varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_sale (
        sale_date date,
        sale_value decimal(38,2),
        client_id bigint,
        vehicle_id bigint not null,
        observations varchar(255),
        primary key (vehicle_id)
    ) engine=InnoDB;

    create table tb_users (
        account_non_expired bit,
        account_non_locked bit,
        credentials_non_expired bit,
        enabled bit,
        id bigint not null auto_increment,
        full_name varchar(255),
        password varchar(255),
        user_name varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_vehicle (
        acquisition_date date,
        cp decimal(12,2),
        customs_broker decimal(12,2),
        driver decimal(12,2),
        innater decimal(12,2),
        inspection decimal(12,2),
        license_plate decimal(12,2),
        loading decimal(12,2),
        manufactured_year integer,
        order_date date,
        purchase_value decimal(12,2),
        registration_date date,
        rights decimal(12,2),
        status tinyint,
        travel decimal(12,2),
        id bigint not null auto_increment,
        brand varchar(255),
        model varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table user_permission (
        permission_id bigint not null,
        user_id bigint not null
    ) engine=InnoDB;

    alter table tb_deposit 
       add constraint UKa4iajkufddbjun5h2ftgybmeh unique (client_id);

    alter table tb_users 
       add constraint UKt3xry9rxngbbv1utykxcve9qt unique (user_name);

    alter table tb_deposit 
       add constraint FKher6ysca2kp2b29m84at4eiay 
       foreign key (client_id) 
       references tb_client (id);

    alter table tb_deposit 
       add constraint FKdqnt5455ig2k3ax6ngshh17v3 
       foreign key (vehicle_id) 
       references tb_vehicle (id);

    alter table tb_installment 
       add constraint FKg0ks3o2mthmqk1gx2arx7rv3a 
       foreign key (deposit_id) 
       references tb_deposit (vehicle_id);

    alter table tb_sale 
       add constraint FK43jttra653rb5w78icqgc9fub 
       foreign key (client_id) 
       references tb_client (id);

    alter table tb_sale 
       add constraint FKry4f3m5gy3tkyle92q4csrerb 
       foreign key (vehicle_id) 
       references tb_vehicle (id);

    alter table user_permission 
       add constraint FKdn4bu8iyay2kbab49i14b5sey 
       foreign key (permission_id) 
       references tb_permission (id);

    alter table user_permission 
       add constraint FKow2d74fs3qkvha007sxo1uv7s 
       foreign key (user_id) 
       references tb_users (id);
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Toyota', 'Corolla', 2021, '2021-03-15', '2021-03-15', 0, 85000.00, 25000.50, 15000.00, 8500.50, 12000.00, 3500.00, 4500.00, 2000.00, 1500.00, 3000.00, '2021-03-15');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Honda', 'Civic', 2020, '2020-06-20', '2020-06-20', 0, 78000.00, 32000.75, 13500.00, 7800.00, 11000.00, 3200.00, 4200.00, 1800.00, 1400.00, 2800.00, '2020-06-20');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Ford', 'Ranger', 2022, '2022-01-10', '2022-01-10', 0, 125000.00, 15000.00, 18000.00, 9500.00, 14000.00, 4000.00, 5000.00, 2500.00, 1800.00, 3500.00, '2022-01-10');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Chevrolet', 'Onix', 2019, '2019-11-05', '2019-11-05', 1, 45000.00, 68000.25, 10000.00, 6000.00, 8500.00, 2500.00, 3500.00, 1500.00, 1200.00, 2200.00, '2019-11-05');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Volkswagen', 'Polo', 2023, '2023-08-22', '2023-08-22', 0, 95000.00, 8500.00, 16500.00, 8800.00, 12500.00, 3600.00, 4600.00, 2100.00, 1600.00, 3100.00, '2023-08-22');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Nissan', 'Kicks', 2021, '2021-05-18', '2021-05-18', 0, 72000.00, 42000.50, 14000.00, 7500.00, 10500.00, 3100.00, 4100.00, 1900.00, 1450.00, 2700.00, '2021-05-18');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Hyundai', 'HB20', 2020, '2020-09-30', '2020-09-30', 1, 55000.00, 55000.80, 12000.00, 6800.00, 9500.00, 2800.00, 3800.00, 1700.00, 1300.00, 2500.00, '2020-09-30');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Renault', 'Duster', 2022, '2022-04-12', '2022-04-12', 0, 98000.00, 28000.00, 17000.00, 9000.00, 13000.00, 3800.00, 4800.00, 2300.00, 1700.00, 3300.00, '2022-04-12');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Fiat', 'Argo', 2021, '2021-07-25', '2021-07-25', 0, 62000.00, 35000.60, 13000.00, 7200.00, 10000.00, 3000.00, 4000.00, 1850.00, 1350.00, 2600.00, '2021-07-25');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Jeep', 'Compass', 2023, '2023-02-14', '2023-02-14', 0, 145000.00, 12000.00, 20000.00, 10500.00, 15500.00, 4500.00, 5500.00, 2800.00, 2000.00, 3800.00, '2023-02-14');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('João', 'Silva', 'joao.silva@empresa.com', '(11) 98765-4321');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Maria', 'Santos', 'maria.santos@email.com.br', '(21) 99876-5432');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Carlos', 'Oliveira', 'carlos.oliveira@companhia.com', '(31) 97654-3210');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Ana', 'Costa', 'ana.costa@dominio.com', '(41) 96543-2109');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Pedro', 'Almeida', 'pedro.almeida@negocio.com', '(51) 95432-1098');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Juliana', 'Pereira', 'juliana.pereira@corp.com', '(61) 94321-0987');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Ricardo', 'Rodrigues', 'ricardo.rodrigues@firma.com', '(71) 93210-9876');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Fernanda', 'Lima', 'fernanda.lima@empresarial.com', '(81) 92109-8765');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Lucas', 'Gomes', 'lucas.gomes@comercial.com', '(85) 91098-7654');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Patrícia', 'Martins', 'patricia.martins@business.com', '(98) 90987-6543');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Roberto', 'Ferreira', 'roberto.ferreira@enterprise.com', '(67) 89876-5432');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Amanda', 'Rocha', 'amanda.rocha@group.com', '(27) 88765-4321');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Marcos', 'Barbosa', 'marcos.barbosa@holdings.com', '(19) 87654-3210');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Cristina', 'Nascimento', 'cristina.nascimento@corporation.com', '(47) 86543-2109');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Diego', 'Carvalho', 'diego.carvalho@international.com', '(92) 85432-1098');
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (550000.00, '2021-04-15', 'Venda realizada com sucesso. Cliente satisfeito.', 1, 1);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id ) VALUES (520000.00, '2020-07-25', 'Pagamento à vista. Documentação completa.', 2, 2);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id ) VALUES (580000.00, '2022-02-20', 'Financiamento aprovado. Entrega agendada.', 3, 3);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (480000.00, '2019-12-10', 'Venda com desconto especial. Cliente antigo.', 4, 4);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (570000.00, '2023-09-05', 'Documentação em dia. IPVA pago.', 5, 5);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (530000.00, '2021-06-20', 'Venda para concessionária. Lote fechado.', 6, 6);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (510000.00, '2020-11-15', 'Cliente particular. Pagamento parcelado.', 7, 7);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (560000.00, '2022-05-18', 'Exportação para Paraguai. Documentação internacional.', 8, 8);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (525000.00, '2021-08-30', 'Venda com garantia estendida. Revisão incluída.', 9, 9);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (590000.00, '2023-03-25', 'Cliente corporativo. Frota empresarial.', 10, 10);
INSERT INTO tb_permission (description) VALUES ('ROLE_ADMIN');
INSERT INTO tb_permission (description) VALUES ('ROLE_MANAGER');
INSERT INTO tb_permission (description) VALUES ('ROLE_COMMON_USER');
INSERT INTO tb_users (user_name, full_name, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES ('leandro', 'Leandro Costa', '1e3cdeeaaaeeda173ff6d002e7cb5e3f91ebc354dcff52156c9eaba1793a3a5e5bee306c11099e22', b'1', b'1', b'1', b'1');
INSERT INTO tb_users (user_name, full_name, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES ('flavio', 'Flavio Costa', '362ad02420268beeb22d3a1f0d92749df461d7f4b74c9433d7415bdeef1b2902f4eb1edaecb37cb3', b'1', b'1', b'1', b'1');
INSERT INTO tb_users (user_name, full_name, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES ('caleb', 'Marcelo Caleb', '4f3cf84ad6d14e085071cba3d078fc1c6826cdddaff891cfa305da954eb1302a18322ed92bcbdd0e', b'1', b'1', b'1', b'1');
INSERT INTO user_permission (user_id, permission_id) VALUES (1, 1);
INSERT INTO user_permission (user_id, permission_id) VALUES	(2, 1);
INSERT INTO user_permission (user_id, permission_id) VALUES (1, 2);
INSERT INTO user_permission (user_id, permission_id) VALUES (3, 1);
INSERT INTO user_permission (user_id, permission_id) VALUES (3, 2);

    create table tb_client (
        id bigint not null auto_increment,
        email varchar(255),
        first_name varchar(255),
        last_name varchar(255),
        phone varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_deposit (
        deposit_date date,
        due_date date,
        initial_deposit_value decimal(38,2),
        paid_installments integer,
        remaining_amount decimal(38,2),
        sale_value decimal(38,2),
        status tinyint,
        total_installments integer,
        client_id bigint,
        created_at datetime(6),
        updated_at datetime(6),
        vehicle_id bigint not null,
        observations varchar(255),
        primary key (vehicle_id)
    ) engine=InnoDB;

    create table tb_installment (
        due_date date,
        installment_number integer,
        payment_date date,
        status tinyint,
        value_per_installment decimal(38,2),
        created_at datetime(6),
        deposit_id bigint,
        id bigint not null auto_increment,
        updated_at datetime(6),
        observations varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_permission (
        id bigint not null auto_increment,
        description varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_sale (
        sale_date date,
        sale_value decimal(38,2),
        client_id bigint,
        vehicle_id bigint not null,
        observations varchar(255),
        primary key (vehicle_id)
    ) engine=InnoDB;

    create table tb_users (
        account_non_expired bit,
        account_non_locked bit,
        credentials_non_expired bit,
        enabled bit,
        id bigint not null auto_increment,
        full_name varchar(255),
        password varchar(255),
        user_name varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_vehicle (
        acquisition_date date,
        cp decimal(12,2),
        customs_broker decimal(12,2),
        driver decimal(12,2),
        innater decimal(12,2),
        inspection decimal(12,2),
        license_plate decimal(12,2),
        loading decimal(12,2),
        manufactured_year integer,
        order_date date,
        purchase_value decimal(12,2),
        registration_date date,
        rights decimal(12,2),
        status tinyint,
        travel decimal(12,2),
        id bigint not null auto_increment,
        brand varchar(255),
        model varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table user_permission (
        permission_id bigint not null,
        user_id bigint not null
    ) engine=InnoDB;

    alter table tb_deposit 
       add constraint UKa4iajkufddbjun5h2ftgybmeh unique (client_id);

    alter table tb_users 
       add constraint UKt3xry9rxngbbv1utykxcve9qt unique (user_name);

    alter table tb_deposit 
       add constraint FKher6ysca2kp2b29m84at4eiay 
       foreign key (client_id) 
       references tb_client (id);

    alter table tb_deposit 
       add constraint FKdqnt5455ig2k3ax6ngshh17v3 
       foreign key (vehicle_id) 
       references tb_vehicle (id);

    alter table tb_installment 
       add constraint FKg0ks3o2mthmqk1gx2arx7rv3a 
       foreign key (deposit_id) 
       references tb_deposit (vehicle_id);

    alter table tb_sale 
       add constraint FK43jttra653rb5w78icqgc9fub 
       foreign key (client_id) 
       references tb_client (id);

    alter table tb_sale 
       add constraint FKry4f3m5gy3tkyle92q4csrerb 
       foreign key (vehicle_id) 
       references tb_vehicle (id);

    alter table user_permission 
       add constraint FKdn4bu8iyay2kbab49i14b5sey 
       foreign key (permission_id) 
       references tb_permission (id);

    alter table user_permission 
       add constraint FKow2d74fs3qkvha007sxo1uv7s 
       foreign key (user_id) 
       references tb_users (id);
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Toyota', 'Corolla', 2021, '2021-03-15', '2021-03-15', 0, 85000.00, 25000.50, 15000.00, 8500.50, 12000.00, 3500.00, 4500.00, 2000.00, 1500.00, 3000.00, '2021-03-15');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Honda', 'Civic', 2020, '2020-06-20', '2020-06-20', 0, 78000.00, 32000.75, 13500.00, 7800.00, 11000.00, 3200.00, 4200.00, 1800.00, 1400.00, 2800.00, '2020-06-20');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Ford', 'Ranger', 2022, '2022-01-10', '2022-01-10', 0, 125000.00, 15000.00, 18000.00, 9500.00, 14000.00, 4000.00, 5000.00, 2500.00, 1800.00, 3500.00, '2022-01-10');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Chevrolet', 'Onix', 2019, '2019-11-05', '2019-11-05', 1, 45000.00, 68000.25, 10000.00, 6000.00, 8500.00, 2500.00, 3500.00, 1500.00, 1200.00, 2200.00, '2019-11-05');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Volkswagen', 'Polo', 2023, '2023-08-22', '2023-08-22', 0, 95000.00, 8500.00, 16500.00, 8800.00, 12500.00, 3600.00, 4600.00, 2100.00, 1600.00, 3100.00, '2023-08-22');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Nissan', 'Kicks', 2021, '2021-05-18', '2021-05-18', 0, 72000.00, 42000.50, 14000.00, 7500.00, 10500.00, 3100.00, 4100.00, 1900.00, 1450.00, 2700.00, '2021-05-18');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Hyundai', 'HB20', 2020, '2020-09-30', '2020-09-30', 1, 55000.00, 55000.80, 12000.00, 6800.00, 9500.00, 2800.00, 3800.00, 1700.00, 1300.00, 2500.00, '2020-09-30');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Renault', 'Duster', 2022, '2022-04-12', '2022-04-12', 0, 98000.00, 28000.00, 17000.00, 9000.00, 13000.00, 3800.00, 4800.00, 2300.00, 1700.00, 3300.00, '2022-04-12');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Fiat', 'Argo', 2021, '2021-07-25', '2021-07-25', 0, 62000.00, 35000.60, 13000.00, 7200.00, 10000.00, 3000.00, 4000.00, 1850.00, 1350.00, 2600.00, '2021-07-25');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Jeep', 'Compass', 2023, '2023-02-14', '2023-02-14', 0, 145000.00, 12000.00, 20000.00, 10500.00, 15500.00, 4500.00, 5500.00, 2800.00, 2000.00, 3800.00, '2023-02-14');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('João', 'Silva', 'joao.silva@empresa.com', '(11) 98765-4321');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Maria', 'Santos', 'maria.santos@email.com.br', '(21) 99876-5432');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Carlos', 'Oliveira', 'carlos.oliveira@companhia.com', '(31) 97654-3210');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Ana', 'Costa', 'ana.costa@dominio.com', '(41) 96543-2109');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Pedro', 'Almeida', 'pedro.almeida@negocio.com', '(51) 95432-1098');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Juliana', 'Pereira', 'juliana.pereira@corp.com', '(61) 94321-0987');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Ricardo', 'Rodrigues', 'ricardo.rodrigues@firma.com', '(71) 93210-9876');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Fernanda', 'Lima', 'fernanda.lima@empresarial.com', '(81) 92109-8765');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Lucas', 'Gomes', 'lucas.gomes@comercial.com', '(85) 91098-7654');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Patrícia', 'Martins', 'patricia.martins@business.com', '(98) 90987-6543');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Roberto', 'Ferreira', 'roberto.ferreira@enterprise.com', '(67) 89876-5432');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Amanda', 'Rocha', 'amanda.rocha@group.com', '(27) 88765-4321');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Marcos', 'Barbosa', 'marcos.barbosa@holdings.com', '(19) 87654-3210');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Cristina', 'Nascimento', 'cristina.nascimento@corporation.com', '(47) 86543-2109');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Diego', 'Carvalho', 'diego.carvalho@international.com', '(92) 85432-1098');
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (550000.00, '2021-04-15', 'Venda realizada com sucesso. Cliente satisfeito.', 1, 1);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id ) VALUES (520000.00, '2020-07-25', 'Pagamento à vista. Documentação completa.', 2, 2);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id ) VALUES (580000.00, '2022-02-20', 'Financiamento aprovado. Entrega agendada.', 3, 3);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (480000.00, '2019-12-10', 'Venda com desconto especial. Cliente antigo.', 4, 4);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (570000.00, '2023-09-05', 'Documentação em dia. IPVA pago.', 5, 5);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (530000.00, '2021-06-20', 'Venda para concessionária. Lote fechado.', 6, 6);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (510000.00, '2020-11-15', 'Cliente particular. Pagamento parcelado.', 7, 7);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (560000.00, '2022-05-18', 'Exportação para Paraguai. Documentação internacional.', 8, 8);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (525000.00, '2021-08-30', 'Venda com garantia estendida. Revisão incluída.', 9, 9);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (590000.00, '2023-03-25', 'Cliente corporativo. Frota empresarial.', 10, 10);
INSERT INTO tb_permission (description) VALUES ('ROLE_ADMIN');
INSERT INTO tb_permission (description) VALUES ('ROLE_MANAGER');
INSERT INTO tb_permission (description) VALUES ('ROLE_COMMON_USER');
INSERT INTO tb_users (user_name, full_name, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES ('leandro', 'Leandro Costa', '1e3cdeeaaaeeda173ff6d002e7cb5e3f91ebc354dcff52156c9eaba1793a3a5e5bee306c11099e22', b'1', b'1', b'1', b'1');
INSERT INTO tb_users (user_name, full_name, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES ('flavio', 'Flavio Costa', '362ad02420268beeb22d3a1f0d92749df461d7f4b74c9433d7415bdeef1b2902f4eb1edaecb37cb3', b'1', b'1', b'1', b'1');
INSERT INTO tb_users (user_name, full_name, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES ('caleb', 'Marcelo Caleb', '4f3cf84ad6d14e085071cba3d078fc1c6826cdddaff891cfa305da954eb1302a18322ed92bcbdd0e', b'1', b'1', b'1', b'1');
INSERT INTO user_permission (user_id, permission_id) VALUES (1, 1);
INSERT INTO user_permission (user_id, permission_id) VALUES	(2, 1);
INSERT INTO user_permission (user_id, permission_id) VALUES (1, 2);
INSERT INTO user_permission (user_id, permission_id) VALUES (3, 1);
INSERT INTO user_permission (user_id, permission_id) VALUES (3, 2);

    create table tb_client (
        id bigint not null auto_increment,
        email varchar(255),
        first_name varchar(255),
        last_name varchar(255),
        phone varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_deposit (
        deposit_date date,
        due_date date,
        initial_deposit_value decimal(38,2),
        paid_installments integer,
        remaining_amount decimal(38,2),
        sale_value decimal(38,2),
        status tinyint,
        total_installments integer,
        client_id bigint,
        created_at datetime(6),
        updated_at datetime(6),
        vehicle_id bigint not null,
        observations varchar(255),
        primary key (vehicle_id)
    ) engine=InnoDB;

    create table tb_installment (
        due_date date,
        installment_number integer,
        payment_date date,
        status tinyint,
        value_per_installment decimal(38,2),
        created_at datetime(6),
        deposit_id bigint,
        id bigint not null auto_increment,
        updated_at datetime(6),
        observations varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_permission (
        id bigint not null auto_increment,
        description varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_sale (
        sale_date date,
        sale_value decimal(38,2),
        client_id bigint,
        vehicle_id bigint not null,
        observations varchar(255),
        primary key (vehicle_id)
    ) engine=InnoDB;

    create table tb_users (
        account_non_expired bit,
        account_non_locked bit,
        credentials_non_expired bit,
        enabled bit,
        id bigint not null auto_increment,
        full_name varchar(255),
        password varchar(255),
        user_name varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_vehicle (
        acquisition_date date,
        cp decimal(12,2),
        customs_broker decimal(12,2),
        driver decimal(12,2),
        innater decimal(12,2),
        inspection decimal(12,2),
        license_plate decimal(12,2),
        loading decimal(12,2),
        manufactured_year integer,
        order_date date,
        purchase_value decimal(12,2),
        registration_date date,
        rights decimal(12,2),
        status tinyint,
        travel decimal(12,2),
        id bigint not null auto_increment,
        brand varchar(255),
        model varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table user_permission (
        permission_id bigint not null,
        user_id bigint not null
    ) engine=InnoDB;

    alter table tb_deposit 
       add constraint UKa4iajkufddbjun5h2ftgybmeh unique (client_id);

    alter table tb_users 
       add constraint UKt3xry9rxngbbv1utykxcve9qt unique (user_name);

    alter table tb_deposit 
       add constraint FKher6ysca2kp2b29m84at4eiay 
       foreign key (client_id) 
       references tb_client (id);

    alter table tb_deposit 
       add constraint FKdqnt5455ig2k3ax6ngshh17v3 
       foreign key (vehicle_id) 
       references tb_vehicle (id);

    alter table tb_installment 
       add constraint FKg0ks3o2mthmqk1gx2arx7rv3a 
       foreign key (deposit_id) 
       references tb_deposit (vehicle_id);

    alter table tb_sale 
       add constraint FK43jttra653rb5w78icqgc9fub 
       foreign key (client_id) 
       references tb_client (id);

    alter table tb_sale 
       add constraint FKry4f3m5gy3tkyle92q4csrerb 
       foreign key (vehicle_id) 
       references tb_vehicle (id);

    alter table user_permission 
       add constraint FKdn4bu8iyay2kbab49i14b5sey 
       foreign key (permission_id) 
       references tb_permission (id);

    alter table user_permission 
       add constraint FKow2d74fs3qkvha007sxo1uv7s 
       foreign key (user_id) 
       references tb_users (id);
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Toyota', 'Corolla', 2021, '2021-03-15', '2021-03-15', 0, 85000.00, 25000.50, 15000.00, 8500.50, 12000.00, 3500.00, 4500.00, 2000.00, 1500.00, 3000.00, '2021-03-15');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Honda', 'Civic', 2020, '2020-06-20', '2020-06-20', 0, 78000.00, 32000.75, 13500.00, 7800.00, 11000.00, 3200.00, 4200.00, 1800.00, 1400.00, 2800.00, '2020-06-20');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Ford', 'Ranger', 2022, '2022-01-10', '2022-01-10', 0, 125000.00, 15000.00, 18000.00, 9500.00, 14000.00, 4000.00, 5000.00, 2500.00, 1800.00, 3500.00, '2022-01-10');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Chevrolet', 'Onix', 2019, '2019-11-05', '2019-11-05', 1, 45000.00, 68000.25, 10000.00, 6000.00, 8500.00, 2500.00, 3500.00, 1500.00, 1200.00, 2200.00, '2019-11-05');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Volkswagen', 'Polo', 2023, '2023-08-22', '2023-08-22', 0, 95000.00, 8500.00, 16500.00, 8800.00, 12500.00, 3600.00, 4600.00, 2100.00, 1600.00, 3100.00, '2023-08-22');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Nissan', 'Kicks', 2021, '2021-05-18', '2021-05-18', 0, 72000.00, 42000.50, 14000.00, 7500.00, 10500.00, 3100.00, 4100.00, 1900.00, 1450.00, 2700.00, '2021-05-18');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Hyundai', 'HB20', 2020, '2020-09-30', '2020-09-30', 1, 55000.00, 55000.80, 12000.00, 6800.00, 9500.00, 2800.00, 3800.00, 1700.00, 1300.00, 2500.00, '2020-09-30');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Renault', 'Duster', 2022, '2022-04-12', '2022-04-12', 0, 98000.00, 28000.00, 17000.00, 9000.00, 13000.00, 3800.00, 4800.00, 2300.00, 1700.00, 3300.00, '2022-04-12');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Fiat', 'Argo', 2021, '2021-07-25', '2021-07-25', 0, 62000.00, 35000.60, 13000.00, 7200.00, 10000.00, 3000.00, 4000.00, 1850.00, 1350.00, 2600.00, '2021-07-25');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Jeep', 'Compass', 2023, '2023-02-14', '2023-02-14', 0, 145000.00, 12000.00, 20000.00, 10500.00, 15500.00, 4500.00, 5500.00, 2800.00, 2000.00, 3800.00, '2023-02-14');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('João', 'Silva', 'joao.silva@empresa.com', '(11) 98765-4321');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Maria', 'Santos', 'maria.santos@email.com.br', '(21) 99876-5432');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Carlos', 'Oliveira', 'carlos.oliveira@companhia.com', '(31) 97654-3210');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Ana', 'Costa', 'ana.costa@dominio.com', '(41) 96543-2109');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Pedro', 'Almeida', 'pedro.almeida@negocio.com', '(51) 95432-1098');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Juliana', 'Pereira', 'juliana.pereira@corp.com', '(61) 94321-0987');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Ricardo', 'Rodrigues', 'ricardo.rodrigues@firma.com', '(71) 93210-9876');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Fernanda', 'Lima', 'fernanda.lima@empresarial.com', '(81) 92109-8765');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Lucas', 'Gomes', 'lucas.gomes@comercial.com', '(85) 91098-7654');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Patrícia', 'Martins', 'patricia.martins@business.com', '(98) 90987-6543');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Roberto', 'Ferreira', 'roberto.ferreira@enterprise.com', '(67) 89876-5432');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Amanda', 'Rocha', 'amanda.rocha@group.com', '(27) 88765-4321');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Marcos', 'Barbosa', 'marcos.barbosa@holdings.com', '(19) 87654-3210');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Cristina', 'Nascimento', 'cristina.nascimento@corporation.com', '(47) 86543-2109');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Diego', 'Carvalho', 'diego.carvalho@international.com', '(92) 85432-1098');
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (550000.00, '2021-04-15', 'Venda realizada com sucesso. Cliente satisfeito.', 1, 1);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id ) VALUES (520000.00, '2020-07-25', 'Pagamento à vista. Documentação completa.', 2, 2);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id ) VALUES (580000.00, '2022-02-20', 'Financiamento aprovado. Entrega agendada.', 3, 3);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (480000.00, '2019-12-10', 'Venda com desconto especial. Cliente antigo.', 4, 4);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (570000.00, '2023-09-05', 'Documentação em dia. IPVA pago.', 5, 5);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (530000.00, '2021-06-20', 'Venda para concessionária. Lote fechado.', 6, 6);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (510000.00, '2020-11-15', 'Cliente particular. Pagamento parcelado.', 7, 7);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (560000.00, '2022-05-18', 'Exportação para Paraguai. Documentação internacional.', 8, 8);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (525000.00, '2021-08-30', 'Venda com garantia estendida. Revisão incluída.', 9, 9);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (590000.00, '2023-03-25', 'Cliente corporativo. Frota empresarial.', 10, 10);
INSERT INTO tb_permission (description) VALUES ('ROLE_ADMIN');
INSERT INTO tb_permission (description) VALUES ('ROLE_MANAGER');
INSERT INTO tb_permission (description) VALUES ('ROLE_COMMON_USER');
INSERT INTO tb_users (user_name, full_name, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES ('leandro', 'Leandro Costa', '1e3cdeeaaaeeda173ff6d002e7cb5e3f91ebc354dcff52156c9eaba1793a3a5e5bee306c11099e22', b'1', b'1', b'1', b'1');
INSERT INTO tb_users (user_name, full_name, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES ('flavio', 'Flavio Costa', '362ad02420268beeb22d3a1f0d92749df461d7f4b74c9433d7415bdeef1b2902f4eb1edaecb37cb3', b'1', b'1', b'1', b'1');
INSERT INTO tb_users (user_name, full_name, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES ('caleb', 'Marcelo Caleb', '4f3cf84ad6d14e085071cba3d078fc1c6826cdddaff891cfa305da954eb1302a18322ed92bcbdd0e', b'1', b'1', b'1', b'1');
INSERT INTO user_permission (user_id, permission_id) VALUES (1, 1);
INSERT INTO user_permission (user_id, permission_id) VALUES	(2, 1);
INSERT INTO user_permission (user_id, permission_id) VALUES (1, 2);
INSERT INTO user_permission (user_id, permission_id) VALUES (3, 1);
INSERT INTO user_permission (user_id, permission_id) VALUES (3, 2);

    create table tb_client (
        id bigint not null auto_increment,
        email varchar(255),
        first_name varchar(255),
        last_name varchar(255),
        phone varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_deposit (
        deposit_date date,
        due_date date,
        initial_deposit_value decimal(38,2),
        paid_installments integer,
        remaining_amount decimal(38,2),
        sale_value decimal(38,2),
        status tinyint,
        total_installments integer,
        client_id bigint,
        created_at datetime(6),
        updated_at datetime(6),
        vehicle_id bigint not null,
        observations varchar(255),
        primary key (vehicle_id)
    ) engine=InnoDB;

    create table tb_installment (
        due_date date,
        installment_number integer,
        payment_date date,
        status tinyint,
        value_per_installment decimal(38,2),
        created_at datetime(6),
        deposit_id bigint,
        id bigint not null auto_increment,
        updated_at datetime(6),
        observations varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_permission (
        id bigint not null auto_increment,
        description varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_sale (
        sale_date date,
        sale_value decimal(38,2),
        client_id bigint,
        vehicle_id bigint not null,
        observations varchar(255),
        primary key (vehicle_id)
    ) engine=InnoDB;

    create table tb_users (
        account_non_expired bit,
        account_non_locked bit,
        credentials_non_expired bit,
        enabled bit,
        id bigint not null auto_increment,
        full_name varchar(255),
        password varchar(255),
        user_name varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_vehicle (
        acquisition_date date,
        cp decimal(12,2),
        customs_broker decimal(12,2),
        driver decimal(12,2),
        innater decimal(12,2),
        inspection decimal(12,2),
        license_plate decimal(12,2),
        loading decimal(12,2),
        manufactured_year integer,
        order_date date,
        purchase_value decimal(12,2),
        registration_date date,
        rights decimal(12,2),
        status tinyint,
        travel decimal(12,2),
        id bigint not null auto_increment,
        brand varchar(255),
        model varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table user_permission (
        permission_id bigint not null,
        user_id bigint not null
    ) engine=InnoDB;

    alter table tb_deposit 
       add constraint UKa4iajkufddbjun5h2ftgybmeh unique (client_id);

    alter table tb_users 
       add constraint UKt3xry9rxngbbv1utykxcve9qt unique (user_name);

    alter table tb_deposit 
       add constraint FKher6ysca2kp2b29m84at4eiay 
       foreign key (client_id) 
       references tb_client (id);

    alter table tb_deposit 
       add constraint FKdqnt5455ig2k3ax6ngshh17v3 
       foreign key (vehicle_id) 
       references tb_vehicle (id);

    alter table tb_installment 
       add constraint FKg0ks3o2mthmqk1gx2arx7rv3a 
       foreign key (deposit_id) 
       references tb_deposit (vehicle_id);

    alter table tb_sale 
       add constraint FK43jttra653rb5w78icqgc9fub 
       foreign key (client_id) 
       references tb_client (id);

    alter table tb_sale 
       add constraint FKry4f3m5gy3tkyle92q4csrerb 
       foreign key (vehicle_id) 
       references tb_vehicle (id);

    alter table user_permission 
       add constraint FKdn4bu8iyay2kbab49i14b5sey 
       foreign key (permission_id) 
       references tb_permission (id);

    alter table user_permission 
       add constraint FKow2d74fs3qkvha007sxo1uv7s 
       foreign key (user_id) 
       references tb_users (id);
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Toyota', 'Corolla', 2021, '2021-03-15', '2021-03-15', 0, 85000.00, 25000.50, 15000.00, 8500.50, 12000.00, 3500.00, 4500.00, 2000.00, 1500.00, 3000.00, '2021-03-15');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Honda', 'Civic', 2020, '2020-06-20', '2020-06-20', 0, 78000.00, 32000.75, 13500.00, 7800.00, 11000.00, 3200.00, 4200.00, 1800.00, 1400.00, 2800.00, '2020-06-20');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Ford', 'Ranger', 2022, '2022-01-10', '2022-01-10', 0, 125000.00, 15000.00, 18000.00, 9500.00, 14000.00, 4000.00, 5000.00, 2500.00, 1800.00, 3500.00, '2022-01-10');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Chevrolet', 'Onix', 2019, '2019-11-05', '2019-11-05', 1, 45000.00, 68000.25, 10000.00, 6000.00, 8500.00, 2500.00, 3500.00, 1500.00, 1200.00, 2200.00, '2019-11-05');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Volkswagen', 'Polo', 2023, '2023-08-22', '2023-08-22', 0, 95000.00, 8500.00, 16500.00, 8800.00, 12500.00, 3600.00, 4600.00, 2100.00, 1600.00, 3100.00, '2023-08-22');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Nissan', 'Kicks', 2021, '2021-05-18', '2021-05-18', 0, 72000.00, 42000.50, 14000.00, 7500.00, 10500.00, 3100.00, 4100.00, 1900.00, 1450.00, 2700.00, '2021-05-18');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Hyundai', 'HB20', 2020, '2020-09-30', '2020-09-30', 1, 55000.00, 55000.80, 12000.00, 6800.00, 9500.00, 2800.00, 3800.00, 1700.00, 1300.00, 2500.00, '2020-09-30');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Renault', 'Duster', 2022, '2022-04-12', '2022-04-12', 0, 98000.00, 28000.00, 17000.00, 9000.00, 13000.00, 3800.00, 4800.00, 2300.00, 1700.00, 3300.00, '2022-04-12');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Fiat', 'Argo', 2021, '2021-07-25', '2021-07-25', 0, 62000.00, 35000.60, 13000.00, 7200.00, 10000.00, 3000.00, 4000.00, 1850.00, 1350.00, 2600.00, '2021-07-25');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Jeep', 'Compass', 2023, '2023-02-14', '2023-02-14', 0, 145000.00, 12000.00, 20000.00, 10500.00, 15500.00, 4500.00, 5500.00, 2800.00, 2000.00, 3800.00, '2023-02-14');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('João', 'Silva', 'joao.silva@empresa.com', '(11) 98765-4321');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Maria', 'Santos', 'maria.santos@email.com.br', '(21) 99876-5432');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Carlos', 'Oliveira', 'carlos.oliveira@companhia.com', '(31) 97654-3210');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Ana', 'Costa', 'ana.costa@dominio.com', '(41) 96543-2109');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Pedro', 'Almeida', 'pedro.almeida@negocio.com', '(51) 95432-1098');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Juliana', 'Pereira', 'juliana.pereira@corp.com', '(61) 94321-0987');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Ricardo', 'Rodrigues', 'ricardo.rodrigues@firma.com', '(71) 93210-9876');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Fernanda', 'Lima', 'fernanda.lima@empresarial.com', '(81) 92109-8765');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Lucas', 'Gomes', 'lucas.gomes@comercial.com', '(85) 91098-7654');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Patrícia', 'Martins', 'patricia.martins@business.com', '(98) 90987-6543');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Roberto', 'Ferreira', 'roberto.ferreira@enterprise.com', '(67) 89876-5432');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Amanda', 'Rocha', 'amanda.rocha@group.com', '(27) 88765-4321');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Marcos', 'Barbosa', 'marcos.barbosa@holdings.com', '(19) 87654-3210');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Cristina', 'Nascimento', 'cristina.nascimento@corporation.com', '(47) 86543-2109');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Diego', 'Carvalho', 'diego.carvalho@international.com', '(92) 85432-1098');
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (550000.00, '2021-04-15', 'Venda realizada com sucesso. Cliente satisfeito.', 1, 1);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id ) VALUES (520000.00, '2020-07-25', 'Pagamento à vista. Documentação completa.', 2, 2);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id ) VALUES (580000.00, '2022-02-20', 'Financiamento aprovado. Entrega agendada.', 3, 3);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (480000.00, '2019-12-10', 'Venda com desconto especial. Cliente antigo.', 4, 4);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (570000.00, '2023-09-05', 'Documentação em dia. IPVA pago.', 5, 5);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (530000.00, '2021-06-20', 'Venda para concessionária. Lote fechado.', 6, 6);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (510000.00, '2020-11-15', 'Cliente particular. Pagamento parcelado.', 7, 7);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (560000.00, '2022-05-18', 'Exportação para Paraguai. Documentação internacional.', 8, 8);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (525000.00, '2021-08-30', 'Venda com garantia estendida. Revisão incluída.', 9, 9);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (590000.00, '2023-03-25', 'Cliente corporativo. Frota empresarial.', 10, 10);
INSERT INTO tb_permission (description) VALUES ('ROLE_ADMIN');
INSERT INTO tb_permission (description) VALUES ('ROLE_MANAGER');
INSERT INTO tb_permission (description) VALUES ('ROLE_COMMON_USER');
INSERT INTO tb_users (user_name, full_name, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES ('leandro', 'Leandro Costa', '1e3cdeeaaaeeda173ff6d002e7cb5e3f91ebc354dcff52156c9eaba1793a3a5e5bee306c11099e22', b'1', b'1', b'1', b'1');
INSERT INTO tb_users (user_name, full_name, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES ('flavio', 'Flavio Costa', '362ad02420268beeb22d3a1f0d92749df461d7f4b74c9433d7415bdeef1b2902f4eb1edaecb37cb3', b'1', b'1', b'1', b'1');
INSERT INTO tb_users (user_name, full_name, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES ('caleb', 'Marcelo Caleb', '4f3cf84ad6d14e085071cba3d078fc1c6826cdddaff891cfa305da954eb1302a18322ed92bcbdd0e', b'1', b'1', b'1', b'1');
INSERT INTO user_permission (user_id, permission_id) VALUES (1, 1);
INSERT INTO user_permission (user_id, permission_id) VALUES	(2, 1);
INSERT INTO user_permission (user_id, permission_id) VALUES (1, 2);
INSERT INTO user_permission (user_id, permission_id) VALUES (3, 1);
INSERT INTO user_permission (user_id, permission_id) VALUES (3, 2);

    create table tb_client (
        id bigint not null auto_increment,
        email varchar(255),
        first_name varchar(255),
        last_name varchar(255),
        phone varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_deposit (
        deposit_date date,
        due_date date,
        initial_deposit_value decimal(38,2),
        paid_installments integer,
        remaining_amount decimal(38,2),
        sale_value decimal(38,2),
        status tinyint,
        total_installments integer,
        client_id bigint,
        created_at datetime(6),
        updated_at datetime(6),
        vehicle_id bigint not null,
        observations varchar(255),
        primary key (vehicle_id)
    ) engine=InnoDB;

    create table tb_installment (
        due_date date,
        installment_number integer,
        payment_date date,
        status tinyint,
        value_per_installment decimal(38,2),
        created_at datetime(6),
        deposit_id bigint,
        id bigint not null auto_increment,
        updated_at datetime(6),
        observations varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_permission (
        id bigint not null auto_increment,
        description varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_sale (
        sale_date date,
        sale_value decimal(38,2),
        client_id bigint,
        vehicle_id bigint not null,
        observations varchar(255),
        primary key (vehicle_id)
    ) engine=InnoDB;

    create table tb_users (
        account_non_expired bit,
        account_non_locked bit,
        credentials_non_expired bit,
        enabled bit,
        id bigint not null auto_increment,
        full_name varchar(255),
        password varchar(255),
        user_name varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_vehicle (
        acquisition_date date,
        cp decimal(12,2),
        customs_broker decimal(12,2),
        driver decimal(12,2),
        innater decimal(12,2),
        inspection decimal(12,2),
        license_plate decimal(12,2),
        loading decimal(12,2),
        manufactured_year integer,
        order_date date,
        purchase_value decimal(12,2),
        registration_date date,
        rights decimal(12,2),
        status tinyint,
        travel decimal(12,2),
        id bigint not null auto_increment,
        brand varchar(255),
        model varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table user_permission (
        permission_id bigint not null,
        user_id bigint not null
    ) engine=InnoDB;

    alter table tb_deposit 
       add constraint UKa4iajkufddbjun5h2ftgybmeh unique (client_id);

    alter table tb_users 
       add constraint UKt3xry9rxngbbv1utykxcve9qt unique (user_name);

    alter table tb_deposit 
       add constraint FKher6ysca2kp2b29m84at4eiay 
       foreign key (client_id) 
       references tb_client (id);

    alter table tb_deposit 
       add constraint FKdqnt5455ig2k3ax6ngshh17v3 
       foreign key (vehicle_id) 
       references tb_vehicle (id);

    alter table tb_installment 
       add constraint FKg0ks3o2mthmqk1gx2arx7rv3a 
       foreign key (deposit_id) 
       references tb_deposit (vehicle_id);

    alter table tb_sale 
       add constraint FK43jttra653rb5w78icqgc9fub 
       foreign key (client_id) 
       references tb_client (id);

    alter table tb_sale 
       add constraint FKry4f3m5gy3tkyle92q4csrerb 
       foreign key (vehicle_id) 
       references tb_vehicle (id);

    alter table user_permission 
       add constraint FKdn4bu8iyay2kbab49i14b5sey 
       foreign key (permission_id) 
       references tb_permission (id);

    alter table user_permission 
       add constraint FKow2d74fs3qkvha007sxo1uv7s 
       foreign key (user_id) 
       references tb_users (id);
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Toyota', 'Corolla', 2021, '2021-03-15', '2021-03-15', 0, 85000.00, 25000.50, 15000.00, 8500.50, 12000.00, 3500.00, 4500.00, 2000.00, 1500.00, 3000.00, '2021-03-15');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Honda', 'Civic', 2020, '2020-06-20', '2020-06-20', 0, 78000.00, 32000.75, 13500.00, 7800.00, 11000.00, 3200.00, 4200.00, 1800.00, 1400.00, 2800.00, '2020-06-20');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Ford', 'Ranger', 2022, '2022-01-10', '2022-01-10', 0, 125000.00, 15000.00, 18000.00, 9500.00, 14000.00, 4000.00, 5000.00, 2500.00, 1800.00, 3500.00, '2022-01-10');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Chevrolet', 'Onix', 2019, '2019-11-05', '2019-11-05', 1, 45000.00, 68000.25, 10000.00, 6000.00, 8500.00, 2500.00, 3500.00, 1500.00, 1200.00, 2200.00, '2019-11-05');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Volkswagen', 'Polo', 2023, '2023-08-22', '2023-08-22', 0, 95000.00, 8500.00, 16500.00, 8800.00, 12500.00, 3600.00, 4600.00, 2100.00, 1600.00, 3100.00, '2023-08-22');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Nissan', 'Kicks', 2021, '2021-05-18', '2021-05-18', 0, 72000.00, 42000.50, 14000.00, 7500.00, 10500.00, 3100.00, 4100.00, 1900.00, 1450.00, 2700.00, '2021-05-18');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Hyundai', 'HB20', 2020, '2020-09-30', '2020-09-30', 1, 55000.00, 55000.80, 12000.00, 6800.00, 9500.00, 2800.00, 3800.00, 1700.00, 1300.00, 2500.00, '2020-09-30');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Renault', 'Duster', 2022, '2022-04-12', '2022-04-12', 0, 98000.00, 28000.00, 17000.00, 9000.00, 13000.00, 3800.00, 4800.00, 2300.00, 1700.00, 3300.00, '2022-04-12');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Fiat', 'Argo', 2021, '2021-07-25', '2021-07-25', 0, 62000.00, 35000.60, 13000.00, 7200.00, 10000.00, 3000.00, 4000.00, 1850.00, 1350.00, 2600.00, '2021-07-25');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Jeep', 'Compass', 2023, '2023-02-14', '2023-02-14', 0, 145000.00, 12000.00, 20000.00, 10500.00, 15500.00, 4500.00, 5500.00, 2800.00, 2000.00, 3800.00, '2023-02-14');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('João', 'Silva', 'joao.silva@empresa.com', '(11) 98765-4321');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Maria', 'Santos', 'maria.santos@email.com.br', '(21) 99876-5432');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Carlos', 'Oliveira', 'carlos.oliveira@companhia.com', '(31) 97654-3210');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Ana', 'Costa', 'ana.costa@dominio.com', '(41) 96543-2109');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Pedro', 'Almeida', 'pedro.almeida@negocio.com', '(51) 95432-1098');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Juliana', 'Pereira', 'juliana.pereira@corp.com', '(61) 94321-0987');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Ricardo', 'Rodrigues', 'ricardo.rodrigues@firma.com', '(71) 93210-9876');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Fernanda', 'Lima', 'fernanda.lima@empresarial.com', '(81) 92109-8765');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Lucas', 'Gomes', 'lucas.gomes@comercial.com', '(85) 91098-7654');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Patrícia', 'Martins', 'patricia.martins@business.com', '(98) 90987-6543');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Roberto', 'Ferreira', 'roberto.ferreira@enterprise.com', '(67) 89876-5432');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Amanda', 'Rocha', 'amanda.rocha@group.com', '(27) 88765-4321');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Marcos', 'Barbosa', 'marcos.barbosa@holdings.com', '(19) 87654-3210');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Cristina', 'Nascimento', 'cristina.nascimento@corporation.com', '(47) 86543-2109');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Diego', 'Carvalho', 'diego.carvalho@international.com', '(92) 85432-1098');
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (550000.00, '2021-04-15', 'Venda realizada com sucesso. Cliente satisfeito.', 1, 1);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id ) VALUES (520000.00, '2020-07-25', 'Pagamento à vista. Documentação completa.', 2, 2);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id ) VALUES (580000.00, '2022-02-20', 'Financiamento aprovado. Entrega agendada.', 3, 3);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (480000.00, '2019-12-10', 'Venda com desconto especial. Cliente antigo.', 4, 4);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (570000.00, '2023-09-05', 'Documentação em dia. IPVA pago.', 5, 5);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (530000.00, '2021-06-20', 'Venda para concessionária. Lote fechado.', 6, 6);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (510000.00, '2020-11-15', 'Cliente particular. Pagamento parcelado.', 7, 7);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (560000.00, '2022-05-18', 'Exportação para Paraguai. Documentação internacional.', 8, 8);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (525000.00, '2021-08-30', 'Venda com garantia estendida. Revisão incluída.', 9, 9);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (590000.00, '2023-03-25', 'Cliente corporativo. Frota empresarial.', 10, 10);
INSERT INTO tb_permission (description) VALUES ('ROLE_ADMIN');
INSERT INTO tb_permission (description) VALUES ('ROLE_MANAGER');
INSERT INTO tb_permission (description) VALUES ('ROLE_COMMON_USER');
INSERT INTO tb_users (user_name, full_name, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES ('leandro', 'Leandro Costa', '1e3cdeeaaaeeda173ff6d002e7cb5e3f91ebc354dcff52156c9eaba1793a3a5e5bee306c11099e22', b'1', b'1', b'1', b'1');
INSERT INTO tb_users (user_name, full_name, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES ('flavio', 'Flavio Costa', '362ad02420268beeb22d3a1f0d92749df461d7f4b74c9433d7415bdeef1b2902f4eb1edaecb37cb3', b'1', b'1', b'1', b'1');
INSERT INTO tb_users (user_name, full_name, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES ('caleb', 'Marcelo Caleb', '4f3cf84ad6d14e085071cba3d078fc1c6826cdddaff891cfa305da954eb1302a18322ed92bcbdd0e', b'1', b'1', b'1', b'1');
INSERT INTO user_permission (user_id, permission_id) VALUES (1, 1);
INSERT INTO user_permission (user_id, permission_id) VALUES	(2, 1);
INSERT INTO user_permission (user_id, permission_id) VALUES (1, 2);
INSERT INTO user_permission (user_id, permission_id) VALUES (3, 1);
INSERT INTO user_permission (user_id, permission_id) VALUES (3, 2);

    create table tb_client (
        id bigint not null auto_increment,
        email varchar(255),
        first_name varchar(255),
        last_name varchar(255),
        phone varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_deposit (
        deposit_date date,
        due_date date,
        initial_deposit_value decimal(38,2),
        paid_installments integer,
        remaining_amount decimal(38,2),
        sale_value decimal(38,2),
        status tinyint,
        total_installments integer,
        client_id bigint,
        created_at datetime(6),
        updated_at datetime(6),
        vehicle_id bigint not null,
        observations varchar(255),
        primary key (vehicle_id)
    ) engine=InnoDB;

    create table tb_installment (
        due_date date,
        installment_number integer,
        payment_date date,
        status tinyint,
        value_per_installment decimal(38,2),
        created_at datetime(6),
        deposit_id bigint,
        id bigint not null auto_increment,
        updated_at datetime(6),
        observations varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_permission (
        id bigint not null auto_increment,
        description varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_sale (
        sale_date date,
        sale_value decimal(38,2),
        client_id bigint,
        vehicle_id bigint not null,
        observations varchar(255),
        primary key (vehicle_id)
    ) engine=InnoDB;

    create table tb_users (
        account_non_expired bit,
        account_non_locked bit,
        credentials_non_expired bit,
        enabled bit,
        id bigint not null auto_increment,
        full_name varchar(255),
        password varchar(255),
        user_name varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_vehicle (
        acquisition_date date,
        cp decimal(12,2),
        customs_broker decimal(12,2),
        driver decimal(12,2),
        innater decimal(12,2),
        inspection decimal(12,2),
        license_plate decimal(12,2),
        loading decimal(12,2),
        manufactured_year integer,
        order_date date,
        purchase_value decimal(12,2),
        registration_date date,
        rights decimal(12,2),
        status tinyint,
        travel decimal(12,2),
        id bigint not null auto_increment,
        brand varchar(255),
        model varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table user_permission (
        permission_id bigint not null,
        user_id bigint not null
    ) engine=InnoDB;

    alter table tb_deposit 
       add constraint UKa4iajkufddbjun5h2ftgybmeh unique (client_id);

    alter table tb_users 
       add constraint UKt3xry9rxngbbv1utykxcve9qt unique (user_name);

    alter table tb_deposit 
       add constraint FKher6ysca2kp2b29m84at4eiay 
       foreign key (client_id) 
       references tb_client (id);

    alter table tb_deposit 
       add constraint FKdqnt5455ig2k3ax6ngshh17v3 
       foreign key (vehicle_id) 
       references tb_vehicle (id);

    alter table tb_installment 
       add constraint FKg0ks3o2mthmqk1gx2arx7rv3a 
       foreign key (deposit_id) 
       references tb_deposit (vehicle_id);

    alter table tb_sale 
       add constraint FK43jttra653rb5w78icqgc9fub 
       foreign key (client_id) 
       references tb_client (id);

    alter table tb_sale 
       add constraint FKry4f3m5gy3tkyle92q4csrerb 
       foreign key (vehicle_id) 
       references tb_vehicle (id);

    alter table user_permission 
       add constraint FKdn4bu8iyay2kbab49i14b5sey 
       foreign key (permission_id) 
       references tb_permission (id);

    alter table user_permission 
       add constraint FKow2d74fs3qkvha007sxo1uv7s 
       foreign key (user_id) 
       references tb_users (id);
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Toyota', 'Corolla', 2021, '2021-03-15', '2021-03-15', 0, 85000.00, 25000.50, 15000.00, 8500.50, 12000.00, 3500.00, 4500.00, 2000.00, 1500.00, 3000.00, '2021-03-15');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Honda', 'Civic', 2020, '2020-06-20', '2020-06-20', 0, 78000.00, 32000.75, 13500.00, 7800.00, 11000.00, 3200.00, 4200.00, 1800.00, 1400.00, 2800.00, '2020-06-20');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Ford', 'Ranger', 2022, '2022-01-10', '2022-01-10', 0, 125000.00, 15000.00, 18000.00, 9500.00, 14000.00, 4000.00, 5000.00, 2500.00, 1800.00, 3500.00, '2022-01-10');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Chevrolet', 'Onix', 2019, '2019-11-05', '2019-11-05', 1, 45000.00, 68000.25, 10000.00, 6000.00, 8500.00, 2500.00, 3500.00, 1500.00, 1200.00, 2200.00, '2019-11-05');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Volkswagen', 'Polo', 2023, '2023-08-22', '2023-08-22', 0, 95000.00, 8500.00, 16500.00, 8800.00, 12500.00, 3600.00, 4600.00, 2100.00, 1600.00, 3100.00, '2023-08-22');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Nissan', 'Kicks', 2021, '2021-05-18', '2021-05-18', 0, 72000.00, 42000.50, 14000.00, 7500.00, 10500.00, 3100.00, 4100.00, 1900.00, 1450.00, 2700.00, '2021-05-18');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Hyundai', 'HB20', 2020, '2020-09-30', '2020-09-30', 1, 55000.00, 55000.80, 12000.00, 6800.00, 9500.00, 2800.00, 3800.00, 1700.00, 1300.00, 2500.00, '2020-09-30');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Renault', 'Duster', 2022, '2022-04-12', '2022-04-12', 0, 98000.00, 28000.00, 17000.00, 9000.00, 13000.00, 3800.00, 4800.00, 2300.00, 1700.00, 3300.00, '2022-04-12');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Fiat', 'Argo', 2021, '2021-07-25', '2021-07-25', 0, 62000.00, 35000.60, 13000.00, 7200.00, 10000.00, 3000.00, 4000.00, 1850.00, 1350.00, 2600.00, '2021-07-25');
INSERT INTO tb_vehicle (brand, model, manufactured_year, acquisition_date, registration_date, status, purchase_value, travel, rights, cp, innater, loading, customs_broker, driver, inspection, license_plate, order_date) VALUES ('Jeep', 'Compass', 2023, '2023-02-14', '2023-02-14', 0, 145000.00, 12000.00, 20000.00, 10500.00, 15500.00, 4500.00, 5500.00, 2800.00, 2000.00, 3800.00, '2023-02-14');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('João', 'Silva', 'joao.silva@empresa.com', '(11) 98765-4321');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Maria', 'Santos', 'maria.santos@email.com.br', '(21) 99876-5432');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Carlos', 'Oliveira', 'carlos.oliveira@companhia.com', '(31) 97654-3210');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Ana', 'Costa', 'ana.costa@dominio.com', '(41) 96543-2109');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Pedro', 'Almeida', 'pedro.almeida@negocio.com', '(51) 95432-1098');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Juliana', 'Pereira', 'juliana.pereira@corp.com', '(61) 94321-0987');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Ricardo', 'Rodrigues', 'ricardo.rodrigues@firma.com', '(71) 93210-9876');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Fernanda', 'Lima', 'fernanda.lima@empresarial.com', '(81) 92109-8765');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Lucas', 'Gomes', 'lucas.gomes@comercial.com', '(85) 91098-7654');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Patrícia', 'Martins', 'patricia.martins@business.com', '(98) 90987-6543');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Roberto', 'Ferreira', 'roberto.ferreira@enterprise.com', '(67) 89876-5432');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Amanda', 'Rocha', 'amanda.rocha@group.com', '(27) 88765-4321');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Marcos', 'Barbosa', 'marcos.barbosa@holdings.com', '(19) 87654-3210');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Cristina', 'Nascimento', 'cristina.nascimento@corporation.com', '(47) 86543-2109');
INSERT INTO tb_client (first_name, last_name, email, phone) VALUES ('Diego', 'Carvalho', 'diego.carvalho@international.com', '(92) 85432-1098');
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (550000.00, '2021-04-15', 'Venda realizada com sucesso. Cliente satisfeito.', 1, 1);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id ) VALUES (520000.00, '2020-07-25', 'Pagamento à vista. Documentação completa.', 2, 2);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id ) VALUES (580000.00, '2022-02-20', 'Financiamento aprovado. Entrega agendada.', 3, 3);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (480000.00, '2019-12-10', 'Venda com desconto especial. Cliente antigo.', 4, 4);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (570000.00, '2023-09-05', 'Documentação em dia. IPVA pago.', 5, 5);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (530000.00, '2021-06-20', 'Venda para concessionária. Lote fechado.', 6, 6);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (510000.00, '2020-11-15', 'Cliente particular. Pagamento parcelado.', 7, 7);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (560000.00, '2022-05-18', 'Exportação para Paraguai. Documentação internacional.', 8, 8);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (525000.00, '2021-08-30', 'Venda com garantia estendida. Revisão incluída.', 9, 9);
INSERT INTO tb_sale (sale_value, sale_date, observations, client_id, vehicle_id) VALUES (590000.00, '2023-03-25', 'Cliente corporativo. Frota empresarial.', 10, 10);
INSERT INTO tb_permission (description) VALUES ('ROLE_ADMIN');
INSERT INTO tb_permission (description) VALUES ('ROLE_MANAGER');
INSERT INTO tb_permission (description) VALUES ('ROLE_COMMON_USER');
INSERT INTO tb_users (user_name, full_name, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES ('leandro', 'Leandro Costa', '1e3cdeeaaaeeda173ff6d002e7cb5e3f91ebc354dcff52156c9eaba1793a3a5e5bee306c11099e22', b'1', b'1', b'1', b'1');
INSERT INTO tb_users (user_name, full_name, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES ('flavio', 'Flavio Costa', '362ad02420268beeb22d3a1f0d92749df461d7f4b74c9433d7415bdeef1b2902f4eb1edaecb37cb3', b'1', b'1', b'1', b'1');
INSERT INTO tb_users (user_name, full_name, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES ('caleb', 'Marcelo Caleb', '4f3cf84ad6d14e085071cba3d078fc1c6826cdddaff891cfa305da954eb1302a18322ed92bcbdd0e', b'1', b'1', b'1', b'1');
INSERT INTO user_permission (user_id, permission_id) VALUES (1, 1);
INSERT INTO user_permission (user_id, permission_id) VALUES	(2, 1);
INSERT INTO user_permission (user_id, permission_id) VALUES (1, 2);
INSERT INTO user_permission (user_id, permission_id) VALUES (3, 1);
INSERT INTO user_permission (user_id, permission_id) VALUES (3, 2);
