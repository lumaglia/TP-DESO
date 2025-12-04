package org.example.TP_DESO;

import org.example.TP_DESO.domain.*;
import org.example.TP_DESO.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cglib.core.Local;
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

    private static final boolean hardReset = true;


    @Autowired
    public PopulateBDD(
            HabitacionRepository habitacionRepository,
            DireccionRepository direccionRepository,
            HuespedRepository huespedRepository,
            EstadiaRepository estadiaRepository,
            ReservaRepository reservaRepository
    ) {
        this.habitacionRepository = habitacionRepository;
        this.direccionRepository = direccionRepository;
        this.huespedRepository = huespedRepository;
        this.estadiaRepository = estadiaRepository;
        this.reservaRepository = reservaRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(hardReset) resetearTablas();

        System.out.println("-> Poblando con datos a la bdd");
        //HABITACIONES
        if(habitacionRepository.count()==0 && hardReset) poblarHabitaciones();
        //DIRECCIONES
        if(direccionRepository.count()==0) poblarDirecciones();
        //HUESPEDES
        if(huespedRepository.count()==0) poblarHuespedes();
        //ESTADIAS
        if(estadiaRepository.count()==0) poblarEstadias();
        //RESERVAS
        if(reservaRepository.count()==0) poblarReservas();
        //ELEMENTOS DE PRUEBA
        poblarEstadiasOcupacionTotal(
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 1, 20));
        poblarReservasYEstados(
                LocalDate.of(2025, 2, 1),
                LocalDate.of(2025, 2, 27),
                LocalDate.of(2025, 1, 29)
        );

        System.out.println("-> Finalizada la carga de datos");
        //ESPERO QUE ESO SEA TODO POR FAVOR QUE DIFICIL FUE ESTO
    }

    private void resetearTablas() {
        reservaRepository.deleteAll();
        estadiaRepository.deleteAll();
        huespedRepository.deleteAll();
        direccionRepository.deleteAll();
        //habitacionRepository.deleteAll();
    }

    public void poblarHabitaciones(){
        System.out.println("-> Poblando Tabla de Habitaciones…");
        //Habitaciones del tipo individual estandar
        for(int i=1; i<=10; i++){
            IndividualEstandar h = new IndividualEstandar();

            h.setNroHabitacion(String.valueOf(i));
            h.setPrecioNoche(50800.0F);
            h.setCapacidad(1);
            h.setTamanno(String.valueOf(30));
            h.setCamasInd(10);

            habitacionRepository.save(h);
        }

        //Habitaciones del tipo doble estandar
        for(int i=11; i<=28; i++){
            DobleEstandar h = new DobleEstandar();

            h.setNroHabitacion(String.valueOf(i));
            h.setCapacidad(2);
            h.setPrecioNoche(70230.0F);
            h.setCamasInd(2);
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
            h.setCamasKingDob(1);
            h.setCapacidad(0);

            habitacionRepository.save(h);
        }

        //Habitaciones del tipo superior family plan
        for(int i=37; i<=46; i++){
            SuperiorFamilyPlan h =  new SuperiorFamilyPlan();

            h.setNroHabitacion(String.valueOf(i));
            h.setCapacidad(5);
            h.setTamanno("40 m^2");
            h.setPrecioNoche(110500.0F);
            h.setCamasInd(1);
            h.setCamasDob(1);

            habitacionRepository.save(h);
        }

        //Habitaciones del tipo suite doble
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

        huespedRepository.saveAll(h);
        System.out.println("-> Finalizada poblacion de Huespes…");
    }

    public void poblarEstadias(){
        System.out.println("-> Poblando Tabla de Estadias…");
        List<Estadia> e =  new ArrayList<>();

        Habitacion habitacion = habitacionRepository.findById("11").orElseThrow();
        List<Huesped> huesped = huespedRepository.findAll().subList(0, 1);

        Estadia estadia = new Estadia();
        estadia.setFechaInicio(LocalDate.of(2025, 3, 1));
        estadia.setFechaFin(LocalDate.of(2025, 3, 4));
        estadia.setHabitacion(habitacion);
        estadia.setHuespedes(huesped);

        e.add(estadia);

        estadiaRepository.saveAll(e);
        System.out.println("-> Finalizada poblacion de estadias…");
    }

    public void poblarReservas(){
        System.out.println("-> Poblando Tabla de Reservas…");
        List<Reserva> rlist = new ArrayList<>();

        Estadia e = estadiaRepository.findById(0L).orElseThrow();
        Huesped hu = e.getHuespedes().get(0);
        Habitacion h = e.getHabitacion();

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

        // 1. Obtener todas las habitaciones
        List<Habitacion> habitaciones = habitacionRepository.findAll();

        // 2. Crear 1 huésped dummy (puede ser un solo huésped para todas)
        Direccion direccionDummy = new Direccion(
                "Testing Street", "1", "9999", "TestCity", "TestProvince", "TestingLand"
        );
        direccionRepository.save(direccionDummy);

        Huesped huespedDummy = new Huesped(
                "Test", "Dummy", "DNI", "00000000",
                "20-00000000-0", "Consumidor Final",
                LocalDate.of(1990,1,1),
                "123456789", "test@test.com",
                "Tester", "TestLand", direccionDummy
        );
        huespedRepository.save(huespedDummy);

        // 3. Crear una estadía por habitación
        for (Habitacion hab : habitaciones) {
            Estadia e = new Estadia();
            e.setFechaInicio(inicio);
            e.setFechaFin(fin);
            e.setHabitacion(hab);

            // agregamos el huésped dummy
            e.getHuespedes().add(huespedDummy);

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

        // Crear Huesped Dummy
        Direccion dummyDir = new Direccion(
                "Fake Street", "1", "9999",
                "TestCity", "TestProvince", "TestLand"
        );
        direccionRepository.save(dummyDir);

        Huesped dummy = new Huesped(
                "Juan", "Prueba", "DNI", "00000000",
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

        // -----------------------------
        // 1. Crear Reservas (primeras N)
        // -----------------------------
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

        // -----------------------------
        // 2. Crear Estadías (siguientes N)
        // -----------------------------
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

    //LISTO BASTA HASTA ACA LLEGUE
}