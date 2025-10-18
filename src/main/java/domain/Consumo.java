package domain;

public class Consumo {
    private float monto;
    public enum TipoConsumo{
        LAVADOPLANCHADO,
        SAUNA,
        BAR
    }
    private TipoConsumo tipo;
    private String detalle;
}
