package dto;

public class DireccionDTOBuilder {
    private String domicilio;
    private String depto;
    private String codigoPostal;
    private String localidad;
    private String provincia;
    private String pais;

    public DireccionDTOBuilder setDomicilio(String domicilio) {
        this.domicilio = domicilio;
        return this;
    }

    public DireccionDTOBuilder setDepto(String depto) {
        this.depto = depto;
        return this;
    }

    public DireccionDTOBuilder setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
        return this;
    }

    public DireccionDTOBuilder setLocalidad(String localidad) {
        this.localidad = localidad;
        return this;
    }

    public DireccionDTOBuilder setProvincia(String provincia) {
        this.provincia = provincia;
        return this;
    }

    public DireccionDTOBuilder setPais(String pais) {
        this.pais = pais;
        return this;
    }

    public DireccionDTO createDireccionDTO() {
        return new DireccionDTO(domicilio, depto, codigoPostal, localidad, provincia, pais);
    }
}