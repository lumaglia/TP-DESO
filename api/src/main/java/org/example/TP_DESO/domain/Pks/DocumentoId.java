package org.example.TP_DESO.domain.Pks;
import java.io.Serializable;
import java.util.Objects;

public class DocumentoId implements Serializable {
    private String tipoDoc;
    private String nroDoc;

    public DocumentoId() {}
    public DocumentoId(String tipoDoc, String nroDoc) {
        this.tipoDoc = tipoDoc;
        this.nroDoc = nroDoc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentoId that = (DocumentoId) o;
        return Objects.equals(tipoDoc, that.tipoDoc) &&
               Objects.equals(nroDoc, that.nroDoc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipoDoc, nroDoc);
    }
}
