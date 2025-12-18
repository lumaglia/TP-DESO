CREATE TABLE IF NOT EXISTS estadia_huesped (
                                               id_estadia BIGINT NOT NULL,
                                               nro_doc VARCHAR(255) NOT NULL,
    tipo_doc VARCHAR(255) NOT NULL,

    PRIMARY KEY (id_estadia, nro_doc, tipo_doc),

    FOREIGN KEY (id_estadia)
    REFERENCES estadia(id_estadia)
    ON DELETE CASCADE,

    FOREIGN KEY (nro_doc, tipo_doc)
    REFERENCES huesped(nro_doc, tipo_doc)
    ON DELETE CASCADE
    );