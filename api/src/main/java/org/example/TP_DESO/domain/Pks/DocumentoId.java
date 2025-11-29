package org.example.TP_DESO.domain.Pks;
import java.io.Serializable;

public class DocumentoId implements Serializable {
    private String tipoDoc;
    private String nroDoc;

    public DocumentoId() {}
    public DocumentoId(String tipoDoc, String nroDoc) {
        this.tipoDoc = tipoDoc;
        this.nroDoc = nroDoc;
    }
}
