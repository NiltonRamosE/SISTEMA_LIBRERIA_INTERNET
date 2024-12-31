-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 12-02-2024 a las 21:03:28
-- Versión del servidor: 8.0.35
-- Versión de PHP: 8.1.10

CREATE DATABASE db_libreria_internet;
USE db_libreria_internet;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `db_libreria_internet`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `compra`
--

CREATE TABLE `compra` (
  `idCompra` int NOT NULL,
  `idProveedor` int DEFAULT NULL,
  `idRegistroCompra` int DEFAULT NULL,
  `idUsuario` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `lineacompra`
--

CREATE TABLE `lineacompra` (
  `idLineaCompra` int NOT NULL,
  `idCompra` int DEFAULT NULL,
  `idProducto` int DEFAULT NULL,
  `precioUnitario` decimal(10,2) DEFAULT NULL,
  `cantidad` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `lineaventa`
--

CREATE TABLE `lineaventa` (
  `idLineaVenta` int NOT NULL,
  `idVenta` int DEFAULT NULL,
  `idProducto` int DEFAULT NULL,
  `precioUnitario` decimal(10,2) DEFAULT NULL,
  `cantidad` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `id` int NOT NULL,
  `codigo` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `titulo` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `ISBN` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `autor` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `stock` int DEFAULT NULL,
  `precioVenta` decimal(10,2) DEFAULT NULL,
  `precioCompra` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`id`, `codigo`, `titulo`, `ISBN`, `autor`, `stock`, `precioVenta`, `precioCompra`) VALUES
(1, 'P001', 'Cien años de soledad', '978-84-376-0494-7', 'Gabriel García Márquez', 158, 15.99, 10.50),
(2, 'P002', 'El código Da Vinci', '978-84-9759-061-9', 'Dan Brown', 36, 12.50, 8.75),
(3, 'P003', 'Harry Potter y la piedra filosofal', '978-84-9838-600-0', 'J.K. Rowling', 69, 10.75, 7.25),
(4, 'P004', 'El alquimista', '978-84-322-0741-9', 'Paulo Coelho', 40, 9.99, 6.75),
(5, 'P005', 'Orgullo y prejuicio', '978-84-15633-89-6', 'Jane Austen', 24, 11.25, 7.99),
(6, 'P006', '1984', '978-84-376-0894-5', 'George Orwell', 33, 13.50, 9.25),
(7, 'P007', 'La sombra del viento', '978-84-9759-088-6', 'Carlos Ruiz Zafón', 140, 14.75, 10.99),
(8, 'P008', 'Don Quijote de la Mancha', '978-84-670-3156-4', 'Miguel de Cervantes', 48, 16.25, 11.50),
(9, 'P009', 'El señor de los anillos: La comunidad del anillo', '978-84-450-7013-6', 'J.R.R. Tolkien', 45, 17.99, 12.75),
(10, 'P010', 'Crónica de una muerte anunciada', '978-84-233-0691-0', 'Gabriel García Márquez', 124, 12.99, 8.50);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proveedor`
--

CREATE TABLE `proveedor` (
  `id` int NOT NULL,
  `razonSocial` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `ruc` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `proveedor`
--

INSERT INTO `proveedor` (`id`, `razonSocial`, `ruc`) VALUES
(1, 'Distribuidora ABC S.A.', '1234567890123'),
(2, 'Librería XYZ', '9876543210987'),
(3, 'Editorial Tres Ríos', '4567890123456'),
(4, 'Mayorista de Libros S.A.C.', '7890123456789'),
(5, 'Importadora Libros E.I.R.L.', '2345678901234');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `registrocompra`
--

CREATE TABLE `registrocompra` (
  `idRegistroCompra` int NOT NULL,
  `Fecha` date NOT NULL,
  `idTurno` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `registroventa`
--

CREATE TABLE `registroventa` (
  `idRegistroVenta` int NOT NULL,
  `fecha` date DEFAULT NULL,
  `liquidacion` double DEFAULT NULL,
  `idTurno` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `statetransporte`
--

CREATE TABLE `statetransporte` (
  `id_transporte` int NOT NULL,
  `descripcion` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `statetransporte`
--

INSERT INTO `statetransporte` (`id_transporte`, `descripcion`) VALUES
(1, 'ACTIVO'),
(2, 'MANTENIMIENTO'),
(3, 'INACTIVO');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tarjetacredito`
--

CREATE TABLE `tarjetacredito` (
  `idTarjetaCredito` int NOT NULL,
  `numero_cuenta` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `fechalimite` date DEFAULT NULL,
  `saldo` decimal(10,2) DEFAULT NULL,
  `clave` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `idUsuario` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `tarjetacredito`
--

INSERT INTO `tarjetacredito` (`idTarjetaCredito`, `numero_cuenta`, `fechalimite`, `saldo`, `clave`, `idUsuario`) VALUES
(1, '1234567890123456', '2024-12-31', 1500.00, '1234', 2),
(2, '1357924680135792', '2024-08-31', 1750.00, '7890', 6),
(3, '2468013579246801', '2024-09-30', 2500.00, '3456', 5),
(4, '5432167890123456', '2024-10-31', 1000.00, '9012', 4),
(5, '9876543210987654', '2024-11-30', 2000.00, '5678', 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipousuario`
--

CREATE TABLE `tipousuario` (
  `id_tipoUsuario` int NOT NULL,
  `descripcion` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `tipousuario`
--

INSERT INTO `tipousuario` (`id_tipoUsuario`, `descripcion`) VALUES
(1, 'CLIENTE'),
(2, 'ADMINISTRADOR'),
(3, 'EMPLEADO');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `transporte`
--

CREATE TABLE `transporte` (
  `transporte_id` int NOT NULL,
  `placa` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `modelo` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `marca` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `estadoTransporte` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `transporte`
--

INSERT INTO `transporte` (`transporte_id`, `placa`, `modelo`, `marca`, `estadoTransporte`) VALUES
(1, 'ABC123', 'Sedán', 'Toyota', 1),
(2, 'DEF456', 'Camioneta', 'Ford', 2),
(3, 'GHI789', 'Autobús', 'Mercedes-Benz', 1),
(4, 'JKL012', 'Motocicleta', 'Honda', 3),
(5, 'MNO345', 'Camión', 'Volvo', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `turno`
--

CREATE TABLE `turno` (
  `id` int NOT NULL,
  `descripcion` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `turno`
--

INSERT INTO `turno` (`id`, `descripcion`) VALUES
(1, 'MANANA'),
(2, 'TARDE'),
(3, 'NOCHE');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `user_id` int NOT NULL,
  `correo` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `clave` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `nombre` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `apellido` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `tipo_usuario` int DEFAULT NULL,
  `direccion` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `localidad` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `codPostal` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `pais` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `dni` varchar(8) COLLATE utf8mb4_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`user_id`, `correo`, `clave`, `nombre`, `apellido`, `tipo_usuario`, `direccion`, `localidad`, `codPostal`, `pais`, `dni`) VALUES
(1, 'admin', '123', 'Nilton', 'Ramos', 2, NULL, NULL, NULL, NULL, '75412099'),
(2, 'maria', '124', 'María', 'García', 1, 'Calle Primavera #123', 'CHIMBOTE', '02711', 'PERÚ', '12364879'),
(3, 'pedro.cliente2@example.com', 'cliente2123', 'Pedro', 'López', 1, 'Avenida del Mar #456', 'CHIMBOTE', '02711', 'PERÚ', '19486729'),
(4, 'laura.cliente3@example.com', 'cliente3123', 'Laura', 'Martínez', 1, 'Camino de los Pájaros #789', 'CHIMBOTE', '02711', 'PERÚ', '72153864'),
(5, 'carlos.cliente4@example.com', 'cliente4123', 'Carlos', 'Rodríguez', 1, 'Calle de la Luna #101', 'CHIMBOTE', '02711', 'PERÚ', '82347962'),
(6, 'ana.cliente5@example.com', 'cliente5123', 'Ana', 'Sánchez', 1, 'Avenida Colorida #222', 'CHIMBOTE', '02711', 'PERÚ', '27845631'),
(7, 'marcos', '147', 'Marcos', 'Lopez', 3, NULL, NULL, NULL, NULL, '25974862');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `venta`
--

CREATE TABLE `venta` (
  `idVenta` int NOT NULL,
  `idRegistroVenta` int DEFAULT NULL,
  `idCliente` int DEFAULT NULL,
  `preferenciaEnvio` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `opcionEmpaquetado` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `estado_venta` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `transporte_id` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `compra`
--
ALTER TABLE `compra`
  ADD PRIMARY KEY (`idCompra`),
  ADD KEY `idProveedor` (`idProveedor`),
  ADD KEY `idUsuario` (`idUsuario`),
  ADD KEY `idRegistroCompra` (`idRegistroCompra`);

--
-- Indices de la tabla `lineacompra`
--
ALTER TABLE `lineacompra`
  ADD PRIMARY KEY (`idLineaCompra`),
  ADD KEY `idProducto` (`idProducto`);

--
-- Indices de la tabla `lineaventa`
--
ALTER TABLE `lineaventa`
  ADD PRIMARY KEY (`idLineaVenta`),
  ADD KEY `idProducto` (`idProducto`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `proveedor`
--
ALTER TABLE `proveedor`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `registrocompra`
--
ALTER TABLE `registrocompra`
  ADD PRIMARY KEY (`idRegistroCompra`),
  ADD KEY `idTurno` (`idTurno`);

--
-- Indices de la tabla `registroventa`
--
ALTER TABLE `registroventa`
  ADD PRIMARY KEY (`idRegistroVenta`),
  ADD KEY `registroventa_ibfk_1` (`idTurno`);

--
-- Indices de la tabla `statetransporte`
--
ALTER TABLE `statetransporte`
  ADD PRIMARY KEY (`id_transporte`);

--
-- Indices de la tabla `tarjetacredito`
--
ALTER TABLE `tarjetacredito`
  ADD PRIMARY KEY (`idTarjetaCredito`),
  ADD KEY `fk_tarjetacredito_usuario` (`idUsuario`);

--
-- Indices de la tabla `tipousuario`
--
ALTER TABLE `tipousuario`
  ADD PRIMARY KEY (`id_tipoUsuario`);

--
-- Indices de la tabla `transporte`
--
ALTER TABLE `transporte`
  ADD PRIMARY KEY (`transporte_id`),
  ADD KEY `estadoTransporte` (`estadoTransporte`);

--
-- Indices de la tabla `turno`
--
ALTER TABLE `turno`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`user_id`),
  ADD KEY `tipo_usuario` (`tipo_usuario`);

--
-- Indices de la tabla `venta`
--
ALTER TABLE `venta`
  ADD PRIMARY KEY (`idVenta`),
  ADD KEY `idCliente` (`idCliente`),
  ADD KEY `transporte_id` (`transporte_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `compra`
--
ALTER TABLE `compra`
  MODIFY `idCompra` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `lineacompra`
--
ALTER TABLE `lineacompra`
  MODIFY `idLineaCompra` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de la tabla `lineaventa`
--
ALTER TABLE `lineaventa`
  MODIFY `idLineaVenta` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de la tabla `proveedor`
--
ALTER TABLE `proveedor`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `registrocompra`
--
ALTER TABLE `registrocompra`
  MODIFY `idRegistroCompra` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT de la tabla `registroventa`
--
ALTER TABLE `registroventa`
  MODIFY `idRegistroVenta` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `statetransporte`
--
ALTER TABLE `statetransporte`
  MODIFY `id_transporte` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `tarjetacredito`
--
ALTER TABLE `tarjetacredito`
  MODIFY `idTarjetaCredito` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `tipousuario`
--
ALTER TABLE `tipousuario`
  MODIFY `id_tipoUsuario` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `transporte`
--
ALTER TABLE `transporte`
  MODIFY `transporte_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `turno`
--
ALTER TABLE `turno`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `user_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT de la tabla `venta`
--
ALTER TABLE `venta`
  MODIFY `idVenta` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `compra`
--
ALTER TABLE `compra`
  ADD CONSTRAINT `fk_compra_proveedor` FOREIGN KEY (`idProveedor`) REFERENCES `proveedor` (`id`),
  ADD CONSTRAINT `fk_compra_registrocompra` FOREIGN KEY (`idRegistroCompra`) REFERENCES `registrocompra` (`idRegistroCompra`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `fk_compra_usuario` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`user_id`);

--
-- Filtros para la tabla `lineacompra`
--
ALTER TABLE `lineacompra`
  ADD CONSTRAINT `fk_lineacompra_producto` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`id`);

--
-- Filtros para la tabla `lineaventa`
--
ALTER TABLE `lineaventa`
  ADD CONSTRAINT `lineaventa_ibfk_1` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`id`);

--
-- Filtros para la tabla `registrocompra`
--
ALTER TABLE `registrocompra`
  ADD CONSTRAINT `registrocompra_ibfk_3` FOREIGN KEY (`idTurno`) REFERENCES `turno` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `registroventa`
--
ALTER TABLE `registroventa`
  ADD CONSTRAINT `fk_registroventa_turno` FOREIGN KEY (`idTurno`) REFERENCES `turno` (`id`);

--
-- Filtros para la tabla `tarjetacredito`
--
ALTER TABLE `tarjetacredito`
  ADD CONSTRAINT `fk_tarjetacredito_usuario` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `transporte`
--
ALTER TABLE `transporte`
  ADD CONSTRAINT `transporte_ibfk_1` FOREIGN KEY (`estadoTransporte`) REFERENCES `statetransporte` (`id_transporte`);

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`tipo_usuario`) REFERENCES `tipousuario` (`id_tipoUsuario`);

--
-- Filtros para la tabla `venta`
--
ALTER TABLE `venta`
  ADD CONSTRAINT `transporte_ibfk_2` FOREIGN KEY (`transporte_id`) REFERENCES `transporte` (`transporte_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `venta_ibfk_1` FOREIGN KEY (`idCliente`) REFERENCES `usuario` (`user_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
