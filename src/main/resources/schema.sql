-- Script de creación inicial de esquema para Roller Speed (MySQL)
-- Ejecutar sobre la base de datos `rollerspeed`

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- =========================
--  TABLAS DE SEGURIDAD
-- =========================

CREATE TABLE IF NOT EXISTS roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(200) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    enabled TINYINT(1) NOT NULL DEFAULT 1,
    fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS usuarios_roles (
    usuario_id BIGINT NOT NULL,
    rol_id BIGINT NOT NULL,
    PRIMARY KEY (usuario_id, rol_id),
    CONSTRAINT fk_usuarios_roles_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    CONSTRAINT fk_usuarios_roles_rol FOREIGN KEY (rol_id) REFERENCES roles(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =========================
--  PERSONAS: ASPIRANTES / ALUMNOS / INSTRUCTORES
-- =========================

CREATE TABLE IF NOT EXISTS aspirantes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_completo VARCHAR(150) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    genero VARCHAR(20) NOT NULL,
    correo VARCHAR(150) NOT NULL,
    telefono VARCHAR(30) NOT NULL,
    metodo_pago_preferido VARCHAR(50) NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE', -- PENDIENTE, ACEPTADO, RECHAZADO
    fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS alumnos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT UNIQUE,
    nombre_completo VARCHAR(150) NOT NULL,
    fecha_nacimiento DATE,
    genero VARCHAR(20),
    telefono VARCHAR(30),
    nivel_patinaje VARCHAR(50),
    fecha_ingreso DATE,
    CONSTRAINT fk_alumno_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS instructores (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT UNIQUE,
    nombre_completo VARCHAR(150) NOT NULL,
    especialidad VARCHAR(100),
    telefono VARCHAR(30),
    email VARCHAR(150),
    CONSTRAINT fk_instructor_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =========================
--  CLASES Y RELACIONES
-- =========================

CREATE TABLE IF NOT EXISTS clases (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    descripcion TEXT,
    nivel VARCHAR(50) NOT NULL,
    dia_semana VARCHAR(20) NOT NULL,   -- LUNES, MARTES, etc.
    hora_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    instructor_id BIGINT,
    CONSTRAINT fk_clase_instructor FOREIGN KEY (instructor_id) REFERENCES instructores(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS clases_alumnos (
    clase_id BIGINT NOT NULL,
    alumno_id BIGINT NOT NULL,
    PRIMARY KEY (clase_id, alumno_id),
    CONSTRAINT fk_clase_alumno_clase FOREIGN KEY (clase_id) REFERENCES clases(id),
    CONSTRAINT fk_clase_alumno_alumno FOREIGN KEY (alumno_id) REFERENCES alumnos(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =========================
--  PAGOS
-- =========================

CREATE TABLE IF NOT EXISTS pagos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    alumno_id BIGINT NOT NULL,
    monto DECIMAL(10,2) NOT NULL,
    fecha_pago DATE NOT NULL,
    periodo VARCHAR(20) NOT NULL,   -- ej: 2025-01, 2025-02
    metodo_pago VARCHAR(50) NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'PAGADO', -- PENDIENTE, PAGADO, VENCIDO
    observaciones VARCHAR(255),
    CONSTRAINT fk_pago_alumno FOREIGN KEY (alumno_id) REFERENCES alumnos(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =========================
--  ASISTENCIAS
-- =========================

CREATE TABLE IF NOT EXISTS asistencias (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    clase_id BIGINT NOT NULL,
    alumno_id BIGINT NOT NULL,
    fecha DATE NOT NULL,
    presente TINYINT(1) NOT NULL DEFAULT 1,
    CONSTRAINT fk_asistencia_clase FOREIGN KEY (clase_id) REFERENCES clases(id),
    CONSTRAINT fk_asistencia_alumno FOREIGN KEY (alumno_id) REFERENCES alumnos(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =========================
--  DIVULGACIÓN INSTITUCIONAL
-- =========================

CREATE TABLE IF NOT EXISTS contenido_institucional (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    seccion VARCHAR(50) NOT NULL,   -- MISION, VISION, VALORES, SERVICIOS, etc.
    titulo VARCHAR(150) NOT NULL,
    contenido TEXT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS noticias (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    resumen VARCHAR(300),
    contenido TEXT,
    fecha_publicacion DATE NOT NULL,
    imagen_url VARCHAR(300)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS eventos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    descripcion TEXT,
    fecha_evento DATE NOT NULL,
    lugar VARCHAR(200),
    imagen_url VARCHAR(300)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =========================
--  DATOS INICIALES (SEMILLA)
-- =========================

-- Roles
INSERT INTO roles (nombre) VALUES
('ROLE_ADMIN'),
('ROLE_INSTRUCTOR'),
('ROLE_ALUMNO')
ON DUPLICATE KEY UPDATE nombre = VALUES(nombre);

-- Usuarios base (NOTA: las contraseñas aquí están en texto plano solo como ejemplo;
-- en producción se deben guardar encriptadas con BCrypt desde la aplicación)

INSERT INTO usuarios (username, password, email, enabled)
VALUES
('admin', 'admin123', 'admin@rollerspeed.com', 1),
('instructor1', 'instr123', 'instructor1@rollerspeed.com', 1),
('alumno1', 'alum123', 'alumno1@rollerspeed.com', 1)
ON DUPLICATE KEY UPDATE email = VALUES(email);

-- Asignación de roles a usuarios
INSERT INTO usuarios_roles (usuario_id, rol_id)
SELECT u.id, r.id
FROM usuarios u
JOIN roles r ON (
	(u.username = 'admin' AND r.nombre = 'ROLE_ADMIN') OR
	(u.username = 'instructor1' AND r.nombre = 'ROLE_INSTRUCTOR') OR
	(u.username = 'alumno1' AND r.nombre = 'ROLE_ALUMNO')
)
ON DUPLICATE KEY UPDATE usuario_id = usuarios_roles.usuario_id;

-- Aspirantes de ejemplo
INSERT INTO aspirantes (nombre_completo, fecha_nacimiento, genero, correo, telefono, metodo_pago_preferido, estado)
VALUES
('Juan Pérez', '2010-05-10', 'MASCULINO', 'juan@example.com', '3001112233', 'EFECTIVO', 'PENDIENTE'),
('María Gómez', '2011-08-22', 'FEMENINO', 'maria@example.com', '3002223344', 'TRANSFERENCIA', 'PENDIENTE')
ON DUPLICATE KEY UPDATE correo = VALUES(correo);

-- Alumno ejemplo vinculado a usuario alumno1
INSERT INTO alumnos (usuario_id, nombre_completo, fecha_nacimiento, genero, telefono, nivel_patinaje, fecha_ingreso)
SELECT u.id, 'Carlos Niño', '2012-03-01', 'MASCULINO', '3003334455', 'BASICO', '2025-01-10'
FROM usuarios u
WHERE u.username = 'alumno1'
ON DUPLICATE KEY UPDATE nombre_completo = VALUES(nombre_completo);

-- Instructor ejemplo vinculado a usuario instructor1
INSERT INTO instructores (usuario_id, nombre_completo, especialidad, telefono, email)
SELECT u.id, 'Laura Rodríguez', 'Velocidad', '3004445566', 'laura.rodriguez@rollerspeed.com'
FROM usuarios u
WHERE u.username = 'instructor1'
ON DUPLICATE KEY UPDATE nombre_completo = VALUES(nombre_completo);

-- Clases de ejemplo
INSERT INTO clases (nombre, descripcion, nivel, dia_semana, hora_inicio, hora_fin, instructor_id)
SELECT 'Clase Básica Mañana', 'Introducción al patinaje para principiantes', 'BASICO', 'SABADO', '08:00:00', '10:00:00', i.id
FROM instructores i
WHERE i.nombre_completo = 'Laura Rodríguez'
ON DUPLICATE KEY UPDATE descripcion = VALUES(descripcion);

-- Contenido institucional básico
INSERT INTO contenido_institucional (seccion, titulo, contenido) VALUES
('MISION', 'Misión', 'Formar patinadores integrales en la ciudad de Santa Marta, promoviendo disciplina, respeto y trabajo en equipo.'),
('VISION', 'Visión', 'Ser la escuela de patinaje líder en la región Caribe, reconocida por su excelencia deportiva y humana.'),
('VALORES', 'Valores', 'Respeto, disciplina, responsabilidad, compañerismo y pasión por el deporte.'),
('SERVICIOS', 'Servicios', 'Clases de patinaje recreativo y competitivo para niños, jóvenes y adultos.')
ON DUPLICATE KEY UPDATE contenido = VALUES(contenido);

-- Noticias de ejemplo
INSERT INTO noticias (titulo, resumen, contenido, fecha_publicacion, imagen_url) VALUES
('Inicio de temporada 2025', 'Abrimos inscripciones para la temporada 2025.', 'La escuela Roller Speed abre inscripciones para nuevos alumnos en todos los niveles.', '2025-01-05', NULL),
('Torneo local de patinaje', 'Participación en torneo local de Santa Marta.', 'Nuestros alumnos participarán en el torneo local de patinaje que se realizará en el malecón.', '2025-02-01', NULL)
ON DUPLICATE KEY UPDATE resumen = VALUES(resumen);

-- Eventos de ejemplo
INSERT INTO eventos (titulo, descripcion, fecha_evento, lugar, imagen_url) VALUES
('Clínica de patinaje con entrenador invitado', 'Sesión especial con entrenador nacional invitado.', '2025-03-15', 'Pista central Roller Speed', NULL),
('Jornada familiar Roller Speed', 'Actividad recreativa para alumnos y sus familias.', '2025-04-20', 'Parque de los Novios, Santa Marta', NULL)
ON DUPLICATE KEY UPDATE descripcion = VALUES(descripcion);

SET FOREIGN_KEY_CHECKS = 1;
