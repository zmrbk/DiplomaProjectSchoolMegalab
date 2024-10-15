begin transaction;

-- Пользователи
create table if not exists users (
    id bigserial primary key,
    username varchar unique not null,
    first_name varchar not null,
    last_name varchar not null,
    middle_name varchar,
    phone varchar,
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
    author varchar not null,
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
    lesson_id bigint references lessons(id),
    student_id bigint references students(id),
    is_done boolean default false,
    teacher_review text,
    mark int,
    creation_date timestamp not null default now()
);

-- Заполнение таблиц
-- Пользователи
insert into users (username, first_name, last_name, middle_name, phone, email, password, creation_date)
values
    ('user1', 'Иван', 'Иванов', 'Иванович', '1234567890', 'ivanov@example.com', 'password1', NOW()),
    ('user2', 'Петр', 'Петров', 'Петрович', '1234567891', 'petrov@example.com', 'password2', NOW()),
    ('user3', 'Сергей', 'Сергеев', 'Сергеевич', '1234567892', 'sergeev@example.com', 'password3', NOW()),
    ('user4', 'Ольга', 'Смирнова', 'Александровна', '1234567893', 'smirnova@example.com', 'password4', NOW()),
    ('user5', 'Екатерина', 'Васильева', 'Николаевна', '1234567894', 'vasilyeva@example.com', 'password5', NOW());

-- Роли
insert into roles (role_name)
values
    ('ADMIN'),
    ('DIRECTOR'),
    ('SECRETARY'),
    ('HEAD_TEACHER'),
    ('CLASS_TEACHER'),
    ('SUBJECT_TEACHER'),
    ('STUDENTS'),
    ('PARENTS');

-- Связь пользователей и ролей
insert into m2m_users_roles (user_id, role_id)
values
    (1, 1),
    (2, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5),
    (5, 6),
    (5, 8);

-- Сотрудники
insert into employees (position, salary, user_id)
values
    ('CLASS_TEACHER', 50000, 2),
    ('CLASS_TEACHER', 55000, 3),
    ('HEAD_TEACHER', 60000, 4),
    ('DIRECTOR', 70000, 5),
    ('SECRETARY', 40000, 1);

-- Родители
insert into parents (user_id)
values
    (4),
    (5),
    (1),
    (2),
    (3);

-- Классы
insert into student_classes (class_title, teacher_id)
values
    ('10А', 2),
    ('10Б', 3),
    ('11А', 4),
    ('11Б', 5),
    ('9А', 2);

-- Ученики
insert into students (birthday, class_id, user_id, parent_id, parent_status)
values
    ('2007-05-10', 1, 3, 1, 'Отец'),
    ('2007-08-20', 1, 4, 2, 'Мать'),
    ('2008-12-15', 2, 5, 3, 'Отец'),
    ('2007-01-18', 3, 1, 4, 'Мать'),
    ('2009-09-22', 4, 2, 5, 'Отец');

-- Предметы
insert into subjects (subject_title, description)
values
    ('Математика', 'Математические дисциплины'),
    ('Русский язык', 'Грамматика и литература'),
    ('Физика', 'Законы природы и явления'),
    ('Химия', 'Химические элементы и реакции'),
    ('Биология', 'Наука о жизни');

-- Объявления
insert into announcements (title, description, employee_id)
values
    ('Начало учебного года', 'Учебный год начнется 1 сентября', 4),
    ('Субботник', 'Все классы участвуют в субботнике 5 октября', 3),
    ('Экзамены', 'Завтра контрольная по математике', 2),
    ('Родительское собрание', 'Собрание родителей 10 октября', 1),
    ('Каникулы', 'Осенние каникулы с 25 октября по 1 ноября', 5);

-- Расписание
insert into schedules (day_of_week, quarter, due_time, year, subject_id, teacher_id, class_id)
values
    ('Понедельник', 1, '08:00', '2024', 1, 2, 1),
    ('Вторник', 1, '09:00', '2024', 2, 3, 2),
    ('Среда', 1, '10:00', '2024', 3, 4, 3),
    ('Четверг', 1, '11:00', '2024', 4, 5, 4),
    ('Пятница', 1, '12:00', '2024', 5, 2, 5);

-- Уроки
insert into lessons (topic, homework, schedule_id)
values
    ('Тема 1', 'Задание 1', 1),
    ('Тема 2', 'Задание 2', 2),
    ('Тема 3', 'Задание 3', 3),
    ('Тема 4', 'Задание 4', 4),
    ('Тема 5', 'Задание 5', 5);

-- Оценки
insert into marks (mark, student_id, lesson_id)
values
    (5, 1, 1),
    (4, 2, 2),
    (3, 3, 3),
    (2, 4, 4),
    (5, 5, 5);

-- Посещаемость
insert into attendances (attended, student_id, lesson_id)
values
    (TRUE, 1, 1),
    (TRUE, 2, 2),
    (FALSE, 3, 3),
    (TRUE, 4, 4),
    (FALSE, 5, 5);

-- Связь предметов и учителей
insert into m2m_subjects_teachers (subject_id, teacher_id)
values
    (1, 2),
    (2, 3),
    (3, 4),
    (4, 5),
    (5, 2);

-- Отзывы
insert into reviews (review, student_id, author_id)
values
    ('Отлично выполнено', 1, 2),
    ('Хороший результат', 2, 3),
    ('Мог бы лучше', 3, 4),
    ('Неудовлетворительно', 4, 5),
    ('Прекрасная работа', 5, 1);

-- Поручения
insert into assignments (assignment, author_id, receiver_id)
values
    ('Подготовить отчет', 4, 1),
    ('Организовать собрание', 5, 2),
    ('Провести экзамен', 2, 3),
    ('Проверить задания', 3, 4),
    ('Составить расписание', 1, 5);

-- Сообщения
insert into messages (message, author_id, receiver_id)
values
    ('Напоминаю про собрание', 1, 2),
    ('Не забудьте о совещании', 2, 3),
    ('Прошу подтвердить участие', 3, 4),
    ('Уточните детали мероприятия', 4, 5),
    ('Документы готовы', 5, 1);

-- Уставы
insert into charters (title, description, employee_id)
values
    ('Устав школы', 'Основные правила школы', 4),
    ('Правила поведения', 'Этические нормы для учеников', 3),
    ('Устав дисциплины', 'Порядок дисциплины в классах', 2),
    ('Трудовой распорядок', 'График работы сотрудников', 5),
    ('Безопасность в школе', 'Правила безопасности для всех участников', 1);

-- Домашние задания
insert into homeworks (lesson_id, student_id, is_done, teacher_review, mark)
values
    (1, 1, TRUE, 'Хорошая работа', 5),
    (2, 2, TRUE, 'Молодец', 4),
    (3, 3, FALSE, 'Не выполнено', 2),
    (4, 4, TRUE, 'Неплохо', 3),
    (5, 5, FALSE, 'Задание не завершено', 0);

commit;
rollback;

