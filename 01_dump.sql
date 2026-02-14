--
-- PostgreSQL database dump
--

-- Dumped from database version 17.5
-- Dumped by pg_dump version 17.5

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: clients; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.clients (
    id bigint NOT NULL,
    name character varying(100) NOT NULL,
    lastname character varying(100) NOT NULL,
    phone_number character varying(20),
    email character varying(255) NOT NULL,
    password_hash character varying(255) NOT NULL,
    password_salt character varying(255) NOT NULL
);


ALTER TABLE public.clients OWNER TO postgres;

--
-- Name: clients_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.clients_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.clients_id_seq OWNER TO postgres;

--
-- Name: clients_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.clients_id_seq OWNED BY public.clients.id;


--
-- Name: employees; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.employees (
    id integer NOT NULL,
    name character varying(100) NOT NULL,
    lastname character varying(100) NOT NULL,
    email character varying(255) NOT NULL,
    password_hash character varying(255) NOT NULL,
    password_salt character varying(255) NOT NULL,
    role character varying(20) NOT NULL,
    "position" character varying(100),
    CONSTRAINT employees_role_check CHECK (((role)::text = ANY ((ARRAY['STAFF'::character varying, 'ADMIN'::character varying])::text[])))
);


ALTER TABLE public.employees OWNER TO postgres;

--
-- Name: employees_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.employees_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.employees_id_seq OWNER TO postgres;

--
-- Name: employees_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.employees_id_seq OWNED BY public.employees.id;


--
-- Name: equipments; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.equipments (
    id bigint NOT NULL,
    client_id bigint NOT NULL,
    type character varying(100),
    brand character varying(100),
    model character varying(100),
    serial_number character varying(255),
    description text
);


ALTER TABLE public.equipments OWNER TO postgres;

--
-- Name: equipments_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.equipments_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.equipments_id_seq OWNER TO postgres;

--
-- Name: equipments_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.equipments_id_seq OWNED BY public.equipments.id;


--
-- Name: order_parts; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.order_parts (
    order_id bigint NOT NULL,
    part_id bigint NOT NULL,
    quantity integer NOT NULL,
    CONSTRAINT order_parts_quantity_check CHECK ((quantity > 0))
);


ALTER TABLE public.order_parts OWNER TO postgres;

--
-- Name: order_services; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.order_services (
    order_id bigint NOT NULL,
    service_id integer NOT NULL
);


ALTER TABLE public.order_services OWNER TO postgres;

--
-- Name: parts; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.parts (
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    quantity_in_stock integer DEFAULT 0 NOT NULL,
    price numeric(10,2) NOT NULL,
    CONSTRAINT parts_price_check CHECK ((price >= (0)::numeric)),
    CONSTRAINT parts_quantity_in_stock_check CHECK ((quantity_in_stock >= 0))
);


ALTER TABLE public.parts OWNER TO postgres;

--
-- Name: parts_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.parts_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.parts_id_seq OWNER TO postgres;

--
-- Name: parts_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.parts_id_seq OWNED BY public.parts.id;


--
-- Name: repair_orders; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.repair_orders (
    id bigint NOT NULL,
    equipment_id bigint NOT NULL,
    technician_id integer,
    status character varying(20) DEFAULT 'NEW'::character varying NOT NULL,
    problem_description text NOT NULL,
    created_at timestamp with time zone DEFAULT now(),
    completed_at timestamp with time zone,
    total_cost numeric(10,2) DEFAULT 0,
    CONSTRAINT repair_orders_status_check CHECK (((status)::text = ANY ((ARRAY['NEW'::character varying, 'IN_PROGRESS'::character varying, 'COMPLETED'::character varying])::text[])))
);


ALTER TABLE public.repair_orders OWNER TO postgres;

--
-- Name: repair_orders_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.repair_orders_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.repair_orders_id_seq OWNER TO postgres;

--
-- Name: repair_orders_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.repair_orders_id_seq OWNED BY public.repair_orders.id;


--
-- Name: services; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.services (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    price numeric(10,2) NOT NULL,
    description text,
    CONSTRAINT services_price_check CHECK ((price >= (0)::numeric))
);


ALTER TABLE public.services OWNER TO postgres;

--
-- Name: services_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.services_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.services_id_seq OWNER TO postgres;

--
-- Name: services_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.services_id_seq OWNED BY public.services.id;


--
-- Name: clients id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clients ALTER COLUMN id SET DEFAULT nextval('public.clients_id_seq'::regclass);


--
-- Name: employees id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employees ALTER COLUMN id SET DEFAULT nextval('public.employees_id_seq'::regclass);


--
-- Name: equipments id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.equipments ALTER COLUMN id SET DEFAULT nextval('public.equipments_id_seq'::regclass);


--
-- Name: parts id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.parts ALTER COLUMN id SET DEFAULT nextval('public.parts_id_seq'::regclass);


--
-- Name: repair_orders id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.repair_orders ALTER COLUMN id SET DEFAULT nextval('public.repair_orders_id_seq'::regclass);


--
-- Name: services id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.services ALTER COLUMN id SET DEFAULT nextval('public.services_id_seq'::regclass);


--
-- Data for Name: clients; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.clients (id, name, lastname, phone_number, email, password_hash, password_salt) FROM stdin;
1	Отчимус	Прайм	+79991112233	ivanov@example.com	hash1	salt1
2	Ольга	Петрова	+79992223344	petrova@example.com	hash2	salt2
\.


--
-- Data for Name: employees; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.employees (id, name, lastname, email, password_hash, password_salt, role, "position") FROM stdin;
1	Роман	Мукминов	admin@example.com	AfJpO/NulXexcR+ml5ohzTIwHLl3hZv+E5z308UYtw8=	qxmUaOq4BUshU/V5Ri4w4A==	ADMIN	Главный администратор системы
2	Сергей	Сидоров	sidorov@example.com	hash3	salt3	STAFF	Мастер
3	Мария	Лебедева	lebedeva@example.com	hash4	salt4	STAFF	Инженер
4	Сын	Маминой подруги	qwe@qwe.qwe	/ffszDNiPCqp/mgw7wzPGEVWOWwxFR0FYsXFfXqi/oQ=	hrumICT4kTGljzH8F6sxgg==	STAFF	Работник
\.


--
-- Data for Name: equipments; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.equipments (id, client_id, type, brand, model, serial_number, description) FROM stdin;
1	1	Ноутбук	ASUS	VivoBook	SNASUS12345	Зависает при загрузке
2	2	Смартфон	Samsung	Galaxy A52	SMSNG54321	Разбит экран
\.


--
-- Data for Name: order_parts; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.order_parts (order_id, part_id, quantity) FROM stdin;
2	1	1
\.


--
-- Data for Name: order_services; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.order_services (order_id, service_id) FROM stdin;
2	2
2	1
\.


--
-- Data for Name: parts; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.parts (id, name, quantity_in_stock, price) FROM stdin;
5	Блок питания Dell 65W	6	2000.00
6	ARCTIC MX-4 термопаста	20	350.00
7	Антенный кабель Wi-Fi	10	300.00
8	Корпус крышка MacBook Air	2	6500.00
10	Микросхема управления зарядом	12	1400.00
11	Вентилятор (кулер) Asus X550	5	950.00
12	Шлейф матрицы HP	4	600.00
13	Петли для ноутбука Acer	8	450.00
14	Топкейс Sony Vaio	3	3500.00
15	Экран Samsung A52	5	3000.00
16	Батарея ASUS VivoBook	3	1100.00
17	Термопаста	10	150.00
1	Клавиатура HP Pavilion	5	1700.00
9	Разъем питания Asus	15	300.00
3	SSD Kingston 240GB	7	2900.00
2	Экран Acer Aspire 5	1	4500.00
4	АКБ Lenovo	4	2700.00
\.


--
-- Data for Name: repair_orders; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.repair_orders (id, equipment_id, technician_id, status, problem_description, created_at, completed_at, total_cost) FROM stdin;
2	2	\N	NEW	Разбит экран, не реагирует на касание	2025-10-31 22:33:13.893554+00	\N	6900.00
1	1	4	NEW	Зависает ноутбук после включения	2025-10-31 22:33:13.893554+00	\N	0.00
\.


--
-- Data for Name: services; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.services (id, name, price, description) FROM stdin;
2	Чистка от пыли	1200.00	Удаление грязи и пыли, термопрокладка
3	Замена клавиатуры	2500.00	Полная замена клавиатуры на ноутбуке
4	Установка ОС	1500.00	Полная переустановка Windows, драйверы
5	Восстановление данных	3500.00	Ремонт или извлечение информации с HDD/SSD
6	Замена разъема зарядки	1800.00	Ремонт разъема питания ноутбука
7	Смена корпуса	2000.00	Замена корпуса, крышки или топкейса
8	Ремонт материнской платы	8000.00	Пайка, восстановление дорожек, замена чипов
9	Замена Wi-Fi модуля	900.00	Ремонт или замена беспроводного модуля
10	Замена экрана	5000.00	Снятие и установка нового LCD/Touchscreen
11	Диагностика	1000.00	Выявление неисправности
12	Замена батареи	1500.00	Установка оригинальной батареи
1	Замена матрицы	4000.00	Установка нового дисплея/экрана ноутбука
\.


--
-- Name: clients_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.clients_id_seq', 2, true);


--
-- Name: employees_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.employees_id_seq', 4, true);


--
-- Name: equipments_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.equipments_id_seq', 2, true);


--
-- Name: parts_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.parts_id_seq', 17, true);


--
-- Name: repair_orders_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.repair_orders_id_seq', 2, true);


--
-- Name: services_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.services_id_seq', 12, true);


--
-- Name: clients clients_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clients
    ADD CONSTRAINT clients_email_key UNIQUE (email);


--
-- Name: clients clients_phone_number_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clients
    ADD CONSTRAINT clients_phone_number_key UNIQUE (phone_number);


--
-- Name: clients clients_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clients
    ADD CONSTRAINT clients_pkey PRIMARY KEY (id);


--
-- Name: employees employees_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employees
    ADD CONSTRAINT employees_email_key UNIQUE (email);


--
-- Name: employees employees_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employees
    ADD CONSTRAINT employees_pkey PRIMARY KEY (id);


--
-- Name: equipments equipments_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.equipments
    ADD CONSTRAINT equipments_pkey PRIMARY KEY (id);


--
-- Name: equipments equipments_serial_number_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.equipments
    ADD CONSTRAINT equipments_serial_number_key UNIQUE (serial_number);


--
-- Name: order_parts order_parts_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_parts
    ADD CONSTRAINT order_parts_pkey PRIMARY KEY (order_id, part_id);


--
-- Name: order_services order_services_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_services
    ADD CONSTRAINT order_services_pkey PRIMARY KEY (order_id, service_id);


--
-- Name: parts parts_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.parts
    ADD CONSTRAINT parts_pkey PRIMARY KEY (id);


--
-- Name: repair_orders repair_orders_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.repair_orders
    ADD CONSTRAINT repair_orders_pkey PRIMARY KEY (id);


--
-- Name: services services_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.services
    ADD CONSTRAINT services_name_key UNIQUE (name);


--
-- Name: services services_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.services
    ADD CONSTRAINT services_pkey PRIMARY KEY (id);


--
-- Name: equipments equipments_client_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.equipments
    ADD CONSTRAINT equipments_client_id_fkey FOREIGN KEY (client_id) REFERENCES public.clients(id);


--
-- Name: order_parts order_parts_order_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_parts
    ADD CONSTRAINT order_parts_order_id_fkey FOREIGN KEY (order_id) REFERENCES public.repair_orders(id);


--
-- Name: order_parts order_parts_part_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_parts
    ADD CONSTRAINT order_parts_part_id_fkey FOREIGN KEY (part_id) REFERENCES public.parts(id);


--
-- Name: order_services order_services_order_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_services
    ADD CONSTRAINT order_services_order_id_fkey FOREIGN KEY (order_id) REFERENCES public.repair_orders(id);


--
-- Name: order_services order_services_service_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_services
    ADD CONSTRAINT order_services_service_id_fkey FOREIGN KEY (service_id) REFERENCES public.services(id);


--
-- Name: repair_orders repair_orders_equipment_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.repair_orders
    ADD CONSTRAINT repair_orders_equipment_id_fkey FOREIGN KEY (equipment_id) REFERENCES public.equipments(id);


--
-- Name: repair_orders repair_orders_technician_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.repair_orders
    ADD CONSTRAINT repair_orders_technician_id_fkey FOREIGN KEY (technician_id) REFERENCES public.employees(id);


--
-- PostgreSQL database dump complete
--

