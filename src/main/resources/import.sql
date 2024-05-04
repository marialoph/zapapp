-- Archivo de Datos predeterminados de la aplicación
-- Inserciones de categoria:
INSERT INTO `categoria` (`id`, `padre_id`, `descripcion`, `nombre`) VALUES ('1', NULL, 'Calzado de mujer', 'Mujer');
INSERT INTO `categoria` (`id`, `padre_id`, `descripcion`, `nombre`) VALUES ('2', NULL, 'Calzado de hombre', 'Hombre');
INSERT INTO `categoria` (`id`, `padre_id`, `descripcion`, `nombre`) VALUES ('3', NULL, 'Calzado deportivo', 'Deportivo');
INSERT INTO `categoria` (`id`, `padre_id`, `descripcion`, `nombre`) VALUES ('4', '3', 'Calzado senderismo', 'Senderismo');
INSERT INTO `categoria` (`id`, `padre_id`, `descripcion`, `nombre`) VALUES ('5', '3', 'Especial para tenis', 'Tenis');
INSERT INTO `categoria` (`id`, `padre_id`, `descripcion`, `nombre`) VALUES ('6', '3', 'Especial para correr', 'Running');
INSERT INTO `categoria` (`id`, `padre_id`, `descripcion`, `nombre`) VALUES ('7', '6', 'Especial para elite de running', 'Trail running');
-- ALTER TABLE `categoria` CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Inserciones de codigo_postal:
INSERT INTO `codigo_postal` (`cp`, `localidad`, `municipio`, `comunidad`, `pais`) VALUES (23003, 'Jaen', 'Jaen', 'Andalucia', 'España');
INSERT INTO `codigo_postal` (`cp`, `localidad`, `municipio`, `comunidad`, `pais`) VALUES (23009, 'Jaen', 'Jaen', 'Andalucia', 'España');
INSERT INTO `codigo_postal` (`cp`, `localidad`, `municipio`, `comunidad`, `pais`) VALUES (23440, 'Baeza', 'Jaen', 'Andalucia', 'España');
-- ALTER TABLE `codigo_postal` CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Inserciones de usuario:
INSERT INTO `usuario` (`nombre`, `apellido`, `email`, `username`, `password`, `enabled`) VALUES ('admin', 'Administrador', 'leo@sincorreo.com', 'admin', '$2a$10$JdTDI22BygL/kxl4jR4zGeeLAhU2xafTlZrtfWki/4xm5TXg5qc.q', 1);
INSERT INTO `usuario` (`nombre`, `apellido`, `email`, `username`, `password`, `enabled`) VALUES ('operario', 'Operario', 'maria@sincorreo.com', 'operario', '$2a$10$JdTDI22BygL/kxl4jR4zGeeLAhU2xafTlZrtfWki/4xm5TXg5qc.q', 1);
INSERT INTO `usuario` (`nombre`, `apellido`, `email`, `username`, `password`, `enabled`) VALUES ('cliente', 'Cliente', 'pepe@sincorreo.com', 'cliente', '$2a$10$JdTDI22BygL/kxl4jR4zGeeLAhU2xafTlZrtfWki/4xm5TXg5qc.q', 1);
-- ALTER TABLE `usuario` CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Inserciones de direccion:
INSERT INTO `direccion` (`codigo_postal_id`, `usuario_id`, `nombre`, `nombre_via`, `numero`, `planta`, `portal`, `puerta`, `tipo_via`) VALUES ('1', '3', NULL, 'de la Estacion', '44', '0', NULL, NULL, 'Paseo');
INSERT INTO `direccion` (`codigo_postal_id`, `usuario_id`, `nombre`, `nombre_via`, `numero`, `planta`, `portal`, `puerta`, `tipo_via`) VALUES ('1', '3', NULL, 'San Pablo', '12', '0', NULL, NULL, 'Calle');
-- ALTER TABLE `direccion` CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Inserciones de rol:
-- INSERT INTO `rol` (`id`, `nombre`) VALUES (1, 'admin');
-- INSERT INTO `rol` (`id`, `nombre`) VALUES (2, 'operario');
-- INSERT INTO `rol` (`id`, `nombre`) VALUES (3, 'cliente');
-- ALTER TABLE `rol` CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Inserciones de telefono:
INSERT INTO `telefono` (`id`, `numero`, `codigo_pais`, `usuario_id`) VALUES (1, 123456789,	34, 1);
INSERT INTO `telefono` (`id`, `numero`, `codigo_pais`, `usuario_id`) VALUES (2, 987654321,	34, 1);
INSERT INTO `telefono` (`id`, `numero`, `codigo_pais`, `usuario_id`) VALUES (3, 555123456,	34, 2);

-- Inserciones de rol_usuario:
INSERT INTO `rol_usuario` (`usuario_id`, `rol`) VALUES (1, 'GESTOR');
INSERT INTO `rol_usuario` (`usuario_id`, `rol`) VALUES (2, 'OPERARIO');
INSERT INTO `rol_usuario` (`usuario_id`, `rol`) VALUES (3, 'CLIENTE');
-- ALTER TABLE `rol_usuario` CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Inserciones de producto:
INSERT INTO `producto` (`id`, `descripcion`, `nombre`, `categoria_id`, `precio`) VALUES (1,	'Con suela reforzada y gel para amortiguar la zancada',	'Asics Gel-Venture 9',	'2', 80);
INSERT INTO `producto` (`id`, `descripcion`, `nombre`, `categoria_id`, `precio`) VALUES (2,	'Suela ultracomoda de foam.',	'Skechers Mark Nason',	'1', 100);
INSERT INTO `producto` (`id`, `descripcion`, `nombre`, `categoria_id`, `precio`) VALUES (3, 'Modelo ligero y transpirable', 'Nike Air Max',	'2', 120);
INSERT INTO `producto` (`id`, `descripcion`, `nombre`, `categoria_id`, `precio`) VALUES (4, 'Soporte de arco y amortiguacion', 'Adidas Ultraboost',	'6', 150);
INSERT INTO `producto` (`id`, `descripcion`, `nombre`, `categoria_id`, `precio`) VALUES (5, 'Comodidad duradera', 'New Balance 990v5',	'3', 175);
INSERT INTO `producto` (`id`, `descripcion`, `nombre`, `categoria_id`, `precio`) VALUES (6, 'Modelo versatil', 'Puma Ignite',	'5', 90);
INSERT INTO `producto` (`id`, `descripcion`, `nombre`, `categoria_id`, `precio`) VALUES (7, 'Amortiguacion suave', 'Reebok Floatride',	'4', 130);
INSERT INTO `producto` (`id`, `descripcion`, `nombre`, `categoria_id`, `precio`) VALUES (8, 'Estabilidad y soporte', 'Under Armour HOVR',	'6', 110);
INSERT INTO `producto` (`id`, `descripcion`, `nombre`, `categoria_id`, `precio`) VALUES (9, 'Modelo clasico', 'Vans Old Skool',	'4', 65);
INSERT INTO `producto` (`id`, `descripcion`, `nombre`, `categoria_id`, `precio`) VALUES (10, 'Comodidad y estilo', 'Converse Chuck Taylor',	'7', 50);
-- ALTER TABLE `producto` CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Inserciones de producto_categorias:
-- INSERT INTO `producto_categorias` (`producto_id`, `categorias_id`) VALUES (1,	3);
-- INSERT INTO `producto_categorias` (`producto_id`, `categorias_id`) VALUES (1,	6);
-- INSERT INTO `producto_categorias` (`producto_id`, `categorias_id`) VALUES (1,	7);
-- INSERT INTO `producto_categorias` (`producto_id`, `categorias_id`) VALUES (2,	2);
-- ALTER TABLE `producto_categorias` CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Inserciones de pedido:
INSERT INTO `pedido` (`descuento`, `fecha`, `asignado_operario_id`, `direccion_id`, `realiza_cliente_id`, `observaciones`, `estado`) VALUES ('0', '2024-02-08', '2', '1', '3', 'Entrega por la tarde', 2);
INSERT INTO `pedido` (`descuento`, `fecha`, `asignado_operario_id`, `direccion_id`, `realiza_cliente_id`, `observaciones`, `estado`) VALUES ('0', '2024-02-09', '2', '1', '3', 'Entrega al mediodia', 2);
-- ALTER TABLE `pedido` CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Inserciones de producto_pedido:
-- INSERT INTO `producto_pedido` (`id`, `cantidad`, `precio`, `pedido_id`, `producto_id`) VALUES (2,	1,	80,	2,	1);
-- ALTER TABLE `producto_pedido` CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;