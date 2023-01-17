create table resume
(
    uuid      char(36) not null primary key,
    full_name text
);

create table contact
(
    id          serial,
    resume_uuid char(36) not null references resume (uuid) on delete cascade,
    contact_type        text     not null,
    contact_value       text     not null

);

create unique index contact_uuid_type_index on contact (resume_uuid, contact_type);

create table section
(
    id          serial,
    resume_uuid char(36) not null references resume (uuid) on delete cascade,
    section_type        text     not null,
    section_value       text     not null

);


create unique index section_uuid_type_index on section (resume_uuid, section_type)

