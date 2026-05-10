package com.david.smart_home.exception;

import java.time.LocalDateTime;

public record ExceptionBody(
    String mensaje,
    Integer codigoError,
    LocalDateTime stamp,
    String ruta
) {}
