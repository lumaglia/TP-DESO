package org.example.TP_DESO;

import org.example.TP_DESO.domain.*;
import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.example.TP_DESO.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class PopulateBDD implements CommandLineRunner {
    private final HabitacionRepository habitacionRepository;
    private final DireccionRepository direccionRepository;
    private final HuespedRepository huespedRepository;
    private final EstadiaRepository estadiaRepository;
    private final ReservaRepository reservaRepository;
    private final ConsumoRepository consumoRepository;

    private static final boolean hardReset = false;
    private static final boolean test = true;


    @Autowired
    public PopulateBDD(
            HabitacionRepository habitacionRepository,
            DireccionRepository direccionRepository,
            HuespedRepository huespedRepository,
            EstadiaRepository estadiaRepository,
            ReservaRepository reservaRepository,
            ConsumoRepository consumoRepository
    ) {
        this.habitacionRepository = habitacionRepository;
        this.direccionRepository = direccionRepository;
        this.huespedRepository = huespedRepository;
        this.estadiaRepository = estadiaRepository;
        this.reservaRepository = reservaRepository;
        this.consumoRepository = consumoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        try{
            if(!test){
                System.out.println("-> No se esta poblando la bdd!");
                return;
            }

            if(hardReset) resetearTablas();

            System.out.println("-> Poblando con datos a la bdd");
            poblarHabitaciones();
            poblarDirecciones();
            poblarHuespedes();
            poblarEstadias();
            poblarReservas();
            poblarEstadiasOcupacionTotal(
                    LocalDate.of(2025, 1, 1),
                    LocalDate.of(2025, 1, 20));
            poblarReservasYEstados(
                    LocalDate.of(2025, 2, 1),
                    LocalDate.of(2025, 2, 27),
                    LocalDate.of(2025, 1, 29)
            );

            poblarDefensaDesarrollo();

            System.out.println("-> Finalizada la carga de datos");
        } catch (Exception e) {
            throw new RuntimeException("Error al poblar la bdd: " + e.getMessage());
        }
    }

    private void resetearTablas() {
        reservaRepository.deleteAll();
        estadiaRepository.deleteAll();
        huespedRepository.deleteAll();
        direccionRepository.deleteAll();
        habitacionRepository.deleteAll();
    }

    public void poblarHabitaciones(){
        System.out.println("-> Poblando Tabla de Habitaciones…");
        for(int i=1; i<=10; i++){
            IndividualEstandar h = new IndividualEstandar();

            h.setNroHabitacion(String.valueOf(i));
            h.setPrecioNoche(50800.0F);
            h.setCapacidad(1);
            h.setTamanno("30 m^2");
            h.setCamasInd(1);

            habitacionRepository.save(h);
        }

        for(int i=11; i<=28; i++){
            DobleEstandar h = new DobleEstandar();

            h.setNroHabitacion(String.valueOf(i));
            h.setCapacidad(2);
            h.setPrecioNoche(70230.0F);
            h.setCamasInd(i%2==0?2:0);
            h.setCamasDob(i%2==0?0:1);
            h.setCapacidad(2);
            h.setTamanno("10 m^2");

            habitacionRepository.save(h);
        }

        for(int i=29; i<=36; i++){
            DobleSuperior h = new DobleSuperior();

            h.setNroHabitacion(String.valueOf(i));
            h.setCapacidad(2);
            h.setTamanno("15 m^2");
            h.setPrecioNoche(90560.0F);
            h.setCamasKingInd(i%2==0?2:0);
            h.setCamasKingDob(i%2==0?0:1);
            h.setCapacidad(0);

            habitacionRepository.save(h);
        }

        for(int i=37; i<=46; i++){
            SuperiorFamilyPlan h =  new SuperiorFamilyPlan();

            h.setNroHabitacion(String.valueOf(i));
            h.setCapacidad(5);
            h.setTamanno("40 m^2");
            h.setPrecioNoche(110500.0F);
            h.setCamasInd(i%2==0?3:1);
            h.setCamasDob(i%2==0?1:2);

            habitacionRepository.save(h);
        }

        for(int i=47; i<=48; i++){
            SuiteDoble h = new SuiteDoble();

            h.setCamasKingDob(1);
            h.setNroHabitacion(String.valueOf(i));
            h.setCapacidad(2);
            h.setTamanno("40 m^2");
            h.setPrecioNoche(128600.0F);

            habitacionRepository.save(h);
        }

        System.out.println("-> Finalizada poblacion de tabla de habitaciones…");
    }

    public void poblarDirecciones(){
        System.out.println("-> Poblando Tabla de Direcciones…");
        List<Direccion> d = new ArrayList<>();

        d.add(new Direccion("Av. Boulevard", "15", "3000", "Santa Fe de la Vera Cruz", "Santa Fe", "Argentina"));
        d.add(new Direccion("Washington Street", "1", "10003", "New York City", "New York", "United States"));
        d.add(new Direccion("Belgrano", "3", "2000", "Rosario", "Santa Fe", "Argentina"));
        d.add(new Direccion("Córdoba", "1200", "1000", "CABA", "Buenos Aires", "Argentina"));
        d.add(new Direccion("Av. Libertador", "5500", "1428", "CABA", "Buenos Aires", "Argentina"));
        d.add(new Direccion("San Martín", "250", "5000", "Córdoba", "Córdoba", "Argentina"));
        d.add(new Direccion("Rivadavia", "800", "8300", "Neuquén", "Neuquén", "Argentina"));
        d.add(new Direccion("Mitre", "210", "4400", "Salta", "Salta", "Argentina"));
        d.add(new Direccion("Sarmiento", "456", "5700", "San Luis", "San Luis", "Argentina"));
        d.add(new Direccion("Brown", "789", "3500", "Resistencia", "Chaco", "Argentina"));
        d.add(new Direccion("Rua Augusta", "120", "01304-000", "São Paulo", "São Paulo", "Brazil"));
        d.add(new Direccion("Av. Atlântica", "3000", "22021-001", "Rio de Janeiro", "Rio de Janeiro", "Brazil"));
        d.add(new Direccion("Av. Paulista", "500", "01311-000", "São Paulo", "São Paulo", "Brazil"));
        d.add(new Direccion("Gran Vía", "18", "28013", "Madrid", "Madrid", "Spain"));
        d.add(new Direccion("Calle Mayor", "22", "28013", "Madrid", "Madrid", "Spain"));
        d.add(new Direccion("Diagonal", "600", "08019", "Barcelona", "Catalunya", "Spain"));
        d.add(new Direccion("Rue de Rivoli", "45", "75001", "Paris", "Île-de-France", "France"));
        d.add(new Direccion("Avenue Victor Hugo", "10", "75116", "Paris", "Île-de-France", "France"));
        d.add(new Direccion("Boulevard Saint-Germain", "33", "75005", "Paris", "Île-de-France", "France"));
        d.add(new Direccion("Oxford Street", "200", "W1D 1NU", "London", "Greater London", "United Kingdom"));
        d.add(new Direccion("Baker Street", "221B", "NW1 6XE", "London", "Greater London", "United Kingdom"));
        d.add(new Direccion("King Street", "55", "M2 4LQ", "Manchester", "Greater Manchester", "United Kingdom"));
        d.add(new Direccion("Alexanderplatz", "4", "10178", "Berlin", "Berlin", "Germany"));
        d.add(new Direccion("Königsallee", "28", "40212", "Düsseldorf", "North Rhine-Westphalia", "Germany"));
        d.add(new Direccion("Marienplatz", "7", "80331", "Munich", "Bavaria", "Germany"));
        d.add(new Direccion("Shibuya Crossing", "2-24", "150-0002", "Tokyo", "Tokyo", "Japan"));
        d.add(new Direccion("Ginza", "5-7", "104-0061", "Tokyo", "Tokyo", "Japan"));
        d.add(new Direccion("Namba", "3-8", "542-0076", "Osaka", "Osaka", "Japan"));
        d.add(new Direccion("Nathan Road", "688", "999077", "Hong Kong", "Hong Kong", "China"));
        d.add(new Direccion("Nanjing Road", "300", "200001", "Shanghai", "Shanghai", "China"));
        d.add(new Direccion("Changan Avenue", "100", "100031", "Beijing", "Beijing", "China"));

        direccionRepository.saveAll(d);
        System.out.println("-> Finalizada poblacion de direcciones…");
    }

    public void poblarHuespedes(){
        System.out.println("-> Poblando Tabla de Huespedes…");
        List<Huesped> h = new ArrayList<>();

        h.add(new Huesped(
                "ANDRES",
                "PEREZ",
                "DNI",
                "28000000",
                "20-28000000-4",
                "Consumidor Final",
                LocalDate.of(1970, 5, 20),
                "+543405103030",
                "andresperez@gmail.com",
                "Abogado",
                "Español",
                direccionRepository.findAll().get(0)));

        h.add(new Huesped(
                "LAURA",
                "DOMINGUEZ",
                "DNI",
                "28000001",
                "20-28000001-5",
                "Consumidor Final",
                LocalDate.of(1977, 11, 4),
                "+543405103040",
                "lauradominguez@gmail.com",
                "Albañol",
                "Española",
                direccionRepository.findAll().get(0)));

        h.add(new Huesped(
                "MARIO",
                "GOMEZ",
                "LC",
                "1200000",
                "20-1200000-3",
                "Consumidor Final",
                LocalDate.of(1955, 3, 10),
                "+543405111111",
                "mariogomez@gmail.com",
                "Jubilado",
                "Argentino",
                direccionRepository.findAll().get(1)));

        h.add(new Huesped(
                "ELENA",
                "CABRERA",
                "LE",
                "4500000",
                "20-4500000-8",
                "Consumidor Final",
                LocalDate.of(1962, 8, 18),
                "+543405222222",
                "elenacabrera@gmail.com",
                "Docente",
                "Brasilera",
                direccionRepository.findAll().get(2)));

        h.add(new Huesped(
                "JULIEN",
                "DUPONT",
                "Pasaporte",
                "XK123456",
                "NO TIENE",
                "No Responsable",
                LocalDate.of(1988, 2, 14),
                "+335555555",
                "julien.dupont@gmail.com",
                "Ingeniero",
                "Español",
                direccionRepository.findAll().get(2)));

        h.add(new Huesped(
                "SOFIA",
                "KOVAC",
                "Otro",
                "DOC-998877",
                "NO TIENE",
                "No Responsable",
                LocalDate.of(1993, 7, 29),
                "+54223444444",
                "sofia.kovac@gmail.com",
                "Artista",
                "Español",
                direccionRepository.findAll().get(2)));

        huespedRepository.saveAll(h);
        System.out.println("-> Finalizada poblacion de Huespes…");
    }

    public void poblarEstadias(){
        System.out.println("-> Poblando Tabla de Estadias…");
        List<Estadia> e =  new ArrayList<>();

        List<Huesped> huesped = huespedRepository.findAll();


        Estadia estadia = new Estadia();
        estadia.setFechaInicio(LocalDate.of(2025, 4, 10));
        estadia.setFechaFin(LocalDate.of(2025, 4, 15));
        estadia.setHabitacion(habitacionRepository.findById("11").orElseThrow());
        estadia.setHuespedes(huesped.subList(0,1));
        e.add(estadia);

        Estadia estadia2 = new Estadia();

        estadia2.setFechaInicio(LocalDate.of(2025, 6, 20));
        estadia2.setFechaFin(LocalDate.of(2025, 6, 23));
        estadia2.setHabitacion(habitacionRepository.findById("2").orElseThrow());
        estadia2.setHuespedes(huesped.subList(2,2));
        e.add(estadia2);


        Estadia estadia3 = new Estadia();
        estadia3.setFechaInicio(LocalDate.of(2025, 7, 2));
        estadia3.setFechaFin(LocalDate.of(2025, 7, 17));
        estadia3.setHabitacion(habitacionRepository.findById("39").orElseThrow());
        estadia3.setHuespedes(huesped.subList(2,2));

        e.add(estadia3);


        estadiaRepository.saveAll(e);
        System.out.println("-> Finalizada poblacion de estadias…");
    }

    public void poblarReservas(){
        System.out.println("-> Poblando Tabla de Reservas…");
        List<Reserva> rlist = new ArrayList<>();

        Estadia e = estadiaRepository.findAll().getFirst();
        Huesped hu = huespedRepository.findByTipoDocAndNroDoc("DNI","28000000").get();
        Habitacion h = habitacionRepository.getByNroHabitacion("11");

        Reserva r = new Reserva();
        r.setFechaInicio(LocalDate.of(2025, 3, 1));
        r.setFechaFin(LocalDate.of(2025, 3, 4));
        r.setNombre(hu.getNombre());
        r.setApellido(hu.getApellido());
        r.setCancelada(false);
        r.setTelefono(hu.getTelefono());
        r.setEstadia(e);
        r.setHabitacion(h);

        rlist.add(r);

        reservaRepository.saveAll(rlist);
        System.out.println("-> Finalizada poblacion de reservas…");
    }

    public void poblarEstadiasOcupacionTotal(LocalDate inicio, LocalDate fin) { // FUNCION POR IA PARA PODER PROBAR CU04 Y CU15

        System.out.println("-> Creando estadías para ocupar todas las habitaciones…");

        List<Habitacion> habitaciones = habitacionRepository.findAll();

        Direccion direccionDummy = new Direccion(
                "Testing Street", "2", "9999", "TestCity", "TestProvince", "TestingLand"
        );
        direccionRepository.save(direccionDummy);

        Huesped huespedDummy = new Huesped(
                "TEST", "DUMMY", "DNI", "00000000",
                "20-00000000-0", "Consumidor Final",
                LocalDate.of(1990,1,1),
                "123456789", "test@test.com",
                "Tester", "TestLand",
                direccionRepository.findByPaisAndCodigoPostalAndDomicilioAndDepto(direccionDummy.getPais(), direccionDummy.getCodigoPostal(), direccionDummy.getDomicilio(), direccionDummy.getDepto()).get()
        );
        huespedRepository.save(huespedDummy);

        for (Habitacion hab : habitaciones) {
            Estadia e = new Estadia();
            e.setFechaInicio(inicio);
            e.setFechaFin(fin);
            e.setHabitacion(hab);

            e.getHuespedes().add(huespedRepository.findByTipoDocAndNroDoc(huespedDummy.getTipoDoc(), huespedDummy.getNroDoc()).get());

            estadiaRepository.save(e);
        }

        System.out.println("-> Todas las habitaciones ocupadas para el periodo dado.");
    }

    public void poblarReservasYEstados(LocalDate inicio, LocalDate fin, LocalDate fechaReserva) { // METODO POR IA PARA PROBAR CASOS 04

        System.out.println("-> Generando 24 reservas y 24 estadías…");

        List<Habitacion> habitaciones = habitacionRepository.findAll();

        if (habitaciones.size() < 48) {
            System.out.println("WARNING: No hay 48 habitaciones, se repartirán las disponibles.");
        }

        Direccion dummyDir = new Direccion(
                "Fake Street", "1", "9999",
                "TestCity", "TestProvince", "TestLand"
        );
        direccionRepository.save(dummyDir);

        Huesped dummy = new Huesped(
                "JUAN", "PRUEBA", "DNI", "00000000",
                "20-00000000-0", "Consumidor Final",
                LocalDate.of(1990,1,1),
                "123456789", "dummy@test.com",
                "Tester", "Argentina",
                dummyDir
        );
        huespedRepository.save(dummy);

        int count = habitaciones.size();

        int reservasCount = Math.min(24, count);
        int estadiasCount = Math.min(24, count - reservasCount);

        for (int i = 0; i < reservasCount; i++) {
            Habitacion hab = habitaciones.get(i);

            Reserva r = new Reserva();
            r.setFechaReserva(fechaReserva);
            r.setFechaInicio(inicio);
            r.setFechaFin(fin);
            r.setNombre(dummy.getNombre());
            r.setApellido(dummy.getApellido());
            r.setTelefono(dummy.getTelefono());
            r.setCancelada(false);
            r.setHabitacion(hab);

            reservaRepository.save(r);
        }

        for (int i = reservasCount; i < reservasCount + estadiasCount; i++) {
            Habitacion hab = habitaciones.get(i);

            Estadia e = new Estadia();
            e.setFechaInicio(inicio);
            e.setFechaFin(fin);
            e.setHabitacion(hab);
            e.getHuespedes().add(dummy);

            estadiaRepository.save(e);
        }

        System.out.println("-> Finalizado: " + reservasCount + " reservas y " + estadiasCount + " estadías creadas.");
    }

    public void poblarDefensaDesarrollo() throws FracasoOperacion {
        try {
            System.out.println("-> Generando defensa desarrollo");

            LocalDate inicio = LocalDate.of(2025, 12, 10);
            LocalDate inicioAntes = LocalDate.of(2025, 12, 5);

            Direccion direccion = new Direccion(
                    "San Martin", "1", "3000",
                    "Santa Fe Capital", "Santa Fe", "Argentina"
            );

            Direccion direccion2 = new Direccion(
                    "Belgrano", "2", "3000",
                    "Santa Fe Capital", "Santa Fe", "Argentina"
            );

            Direccion direccion3 = new Direccion(
                    "Roca", "1", "3000",
                    "Santa Fe Capital", "Santa Fe", "Argentina"
            );
            direccionRepository.save(direccion);

            Huesped h1 = crearHuesped(
                    "MARTIN", "PEREZ", "23654978",
                    "martin@gmail.com", direccion
            );

            crearEstadiaDefensa(
                    habitacionRepository.findById("2").orElseThrow(),
                    h1,
                    List.of(h1),
                    inicio
            );

            Huesped h2 = crearHuesped(
                    "MARTINCITO", "PERECITO", "23654977",
                    "martincito@gmail.com", direccion2
            );

            Huesped h3 = crearHuesped(
                    "MARTINCITA", "MONCHA", "23654976",
                    "martincita@gmail.com", direccion2
            );

            crearEstadiaDefensa(
                    habitacionRepository.findById("12").orElseThrow(),
                    h2,
                    List.of(h2, h3),
                    inicio
            );

            Huesped h4 = crearHuesped(
                    "MARTINCHO", "PERENCHO", "23654678",
                    "martincho@gmail.com", direccion3
            );

            Huesped h5 = crearHuesped(
                    "MANOLO", "GARCIA", "23644968",
                    "manolo@gmail.com", direccion3
            );

            crearEstadiaDefensa(
                    habitacionRepository.findById("31").orElseThrow(),
                    h4,
                    List.of(h4, h5),
                    inicio
            );

            Huesped h6 = crearHuesped(
                    "CARLOS", "LOPEZ", "23611111",
                    "carlos@gmail.com", direccion2
            );

            Huesped h7 = crearHuesped(
                    "ANA", "LOPEZ", "23622222",
                    "ana@gmail.com", direccion
            );

            crearEstadiaDefensa(
                    habitacionRepository.findById("32").orElseThrow(),
                    h6,
                    List.of(h6, h7),
                    inicioAntes
            );

            List<Huesped> family = List.of(
                    crearHuesped("JUAN IGNACIO", "DORREGO", "30000001", "j1@gmail.com", direccion),
                    crearHuesped("JUAN PEREZ", "DORREGO", "30000002", "j2@gmail.com", direccion),
                    crearHuesped("JUAN MARIANO", "DORREGO", "30000003", "j3@gmail.com", direccion),
                    crearHuesped("JUAN PEDRO", "DORREGO", "30000004", "j4@gmail.com", direccion),
                    crearHuesped("JUAN FRANCISCO", "DORREGO", "30000005", "j5@gmail.com", direccion)
            );

            crearEstadiaDefensa(
                    habitacionRepository.findById("38").orElseThrow(),
                    family.get(0),
                    family,
                    inicio
            );

            Huesped h8 = crearHuesped(
                    "PEDRO", "SUITE", "40000001",
                    "pedro@gmail.com", direccion3
            );

            Huesped h9 = crearHuesped(
                    "MARIA", "SUITE", "40000002",
                    "maria@gmail.com", direccion3
            );

            crearEstadiaDefensa(
                    habitacionRepository.findById("48").orElseThrow(),
                    h8,
                    List.of(h8, h9),
                    inicio
            );

            System.out.println("-> Finalizando defensa desarrollo");

        } catch (Exception e) {
            throw new FracasoOperacion("Error al poblar: " + e.getMessage());
        }
    }


    public void crearEstadiaDefensa(Habitacion h, Huesped quienReserva, List<Huesped> hs, LocalDate inicio) throws FracasoOperacion {
        LocalDate fechaReserva = LocalDate.of(2025,10,28);
        LocalDate fin = LocalDate.of(2025, 12, 17);
        try{

            Reserva r = new Reserva();
            r.setFechaReserva(fechaReserva);
            r.setFechaInicio(inicio);
            r.setFechaFin(fin);
            r.setNombre(quienReserva.getNombre().toUpperCase());
            r.setApellido(quienReserva.getApellido().toUpperCase());
            r.setTelefono(quienReserva.getTelefono());
            r.setCancelada(false);
            r.setHabitacion(h);
            reservaRepository.save(r);

            Estadia e = new Estadia();
            e.setFechaInicio(inicio);
            e.setFechaFin(fin);
            e.setHabitacion(h);
            e.setHuespedes(hs);
            estadiaRepository.save(e);

            Consumo consumo1 = new Consumo();
            consumo1.setDetalle("Lavado y planchado");
            consumo1.setTipo(Consumo.TipoConsumo.LAVADOPLANCHADO);
            consumo1.setMonto(3000F);
            consumo1.setEstadia(e);
            consumo1.setFactura(null);

            Consumo consumo2 = new Consumo();
            consumo2.setDetalle("Compra Bar");
            consumo2.setTipo(Consumo.TipoConsumo.BAR);
            consumo2.setMonto(2500);
            consumo2.setEstadia(e);
            consumo2.setFactura(null);

            Consumo consumo3 = new Consumo();
            consumo3.setDetalle("Fue al sauna");
            consumo3.setTipo(Consumo.TipoConsumo.SAUNA);
            consumo3.setMonto(5500);
            consumo3.setEstadia(e);
            consumo3.setFactura(null);

            ArrayList<Consumo> consumos = new ArrayList();

            consumos.add(consumo1);
            consumos.add(consumo2);
            consumos.add(consumo3);

            consumoRepository.saveAll(consumos);
        }
        catch (Exception e){
            throw new FracasoOperacion("Error al crear la reserva: " + e.getMessage());
        }
    }

    public Huesped crearHuesped(String nombre, String apellido, String dni, String email, Direccion direccion) throws FracasoOperacion {
        try{
            Huesped h = new Huesped(
                    nombre, apellido, "DNI", dni,
                    "20-" + dni + "-0",
                    "Consumidor Final",
                    LocalDate.of(1990, 1, 1),
                    "123456789",
                    email,
                    "Empleado",
                    "Argentina",
                    direccion
            );
            return huespedRepository.save(h);

        } catch (Exception e) {
            throw new FracasoOperacion("Error al crear el huesped" + e.getMessage());
        }
    }
}