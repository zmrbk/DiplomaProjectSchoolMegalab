-- Пользователи
create table if not exists users (
    id bigserial primary key,
    username varchar unique not null,
    first_name varchar not null,
    last_name varchar not null,
    middle_name varchar,
    phone varchar not null,
    email varchar not null unique,
    password varchar not null,
    creation_date timestamp with time zone,
    is_active boolean default true
);

-- Роли
create table if not exists roles (
    id bigserial primary key,
    role_name varchar not null unique
);

-- Связь пользователей и ролей многое ко многому
create table m2m_users_roles (
    user_id bigint references users(id),
    role_id bigint references roles(id),
    constraint some_user_role unique(user_id, role_id)
);

-- Сотрудники
create table if not exists employees (
    id bigserial primary key,
    position varchar not null,
    salary int not null,
    user_id bigserial references users(id) not null unique
);

-- Родители
create table if not exists parents (
    id bigserial primary key,
    user_id bigserial references users(id) not null
);

-- Классы
create table student_classes (
    id bigserial primary key,
    class_title varchar not null,
    teacher_id bigint references employees(id),
    creation_date timestamp with time zone not null default now()
);

-- Ученики
create table if not exists students (
    id bigserial primary key,
    birthday date not null,
    class_id bigserial references student_classes(id) not null,
    user_id bigserial references users(id) not null,
    parent_id bigint references parents(id) not null,
    parent_status varchar not null
);

-- Предметы
create table if not exists subjects (
    id bigserial primary key,
    subject_title varchar not null unique,
    description text
);

-- Объявления
create table if not exists announcements (
    id bigserial primary key,
    title varchar not null,
    description text not null,
    employee_id bigint references employees(id),
    creation_date timestamp not null default now()
);

-- Расписание
create table if not exists schedules (
    id bigserial primary key,
    day_of_week varchar not null,
    quarter smallint not null,
    due_time varchar not null,
    year varchar not null,
    subject_id bigint references subjects(id),
    teacher_id bigint references employees(id),
    class_id bigint references student_classes(id),
    is_approve boolean default false
);

-- Уроки
create table if not exists lessons (
    id bigserial primary key,
    topic varchar,
    homework varchar,
    schedule_id bigint references schedules(id),
    creation_date timestamp not null default now()
);

-- Оценки
create table if not exists marks (
    id bigserial primary key,
    mark int not null,
    student_id bigint references students(id),
    lesson_id bigint references lessons(id)
);

-- Посещаемость
create table if not exists attendances (
    id bigserial primary key,
    attended boolean not null,
    student_id bigint references students(id),
    lesson_id bigint references lessons(id)
);

-- Связь предметов и учителей многое ко многому
create table if not exists m2m_subjects_teachers (
    subject_id bigint references subjects(id),
    teacher_id bigint references employees(id)
);

-- Отзывы (учитель-ученик, староста-ученик)
create table if not exists reviews (
    id bigserial primary key,
    review varchar not null,
    student_id bigint references students(id),
    author_id bigint references users(id),
    creation_date timestamp not null default now()
);

-- Поручения (директор-секретарь, класс.рук.-староста, завуч-класс.рук.)
create table if not exists assignments (
    id bigserial primary key,
    assignment varchar not null,
    author_id bigint references employees(id),
    receiver_id bigint references users(id),
    creation_date timestamp not null default now(),
    is_done boolean default false
);

-- Сообщения
create table if not exists messages (
    id bigserial primary key,
    message varchar not null,
    author_id bigint references employees(id),
    receiver_id bigint references users(id),
    creation_date timestamp not null default now(),
    is_read boolean default false
);

-- Уставы
create table if not exists charters (
    id bigserial primary key,
    title varchar not null,
    description text not null,
    employee_id bigint references employees(id),
    creation_date timestamp not null default now()
);

-- Домашние задания
create table if not exists homeworks (
    id bigserial primary key,
    is_done boolean default false,
    teacher_review text,
    mark int,
    creation_date timestamp not null default now(),
    student_id bigint references students(id),
    lesson_id bigint references lessons(id)
);