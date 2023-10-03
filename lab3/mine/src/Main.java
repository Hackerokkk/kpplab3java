import java.io.*;
import java.util.*;

class RouteStation implements Serializable {
    private String stationName;
    private String arrivalTime;
    private String departureTime;
    private int availableSeats;

    public RouteStation(String stationName, String arrivalTime, String departureTime, int availableSeats) {
        this.stationName = stationName;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.availableSeats = availableSeats;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    @Override
    public String toString() {
        return "Station: " + stationName + " (Arrival: " + arrivalTime + ", Departure: " + departureTime + ", Available Seats: " + availableSeats + ")";
    }
}

class Route implements Serializable {
    private int routeNumber;
    private String daysOfWeek;
    private int totalSeats;
    private List<RouteStation> stations;

    public Route(int routeNumber, String daysOfWeek, int totalSeats) {
        this.routeNumber = routeNumber;
        this.daysOfWeek = daysOfWeek;
        this.totalSeats = totalSeats;
        this.stations = new ArrayList<>();
    }

    public int getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(int routeNumber) {
        this.routeNumber = routeNumber;
    }

    public String getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(String daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public List<RouteStation> getStations() {
        return stations;
    }

    public void addStation(RouteStation station) {
        stations.add(station);
    }

    @Override
    public String toString() {
        return "Route: " + routeNumber + " (Days: " + daysOfWeek + ", Total Seats: " + totalSeats + ")";
    }
}

class TicketSystem implements Serializable {
    private List<Route> routes;

    public TicketSystem() {
        routes = new ArrayList<>();
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void addRoute(Route route) {
        routes.add(route);
    }
}

public class Main {
    public static void main(String[] args) {
        TicketSystem ticketSystem = new TicketSystem();
        Scanner scanner = new Scanner(System.in);

        int choice;
        do {
            System.out.println("Меню:");
            System.out.println("1. Додати новий маршрут");
            System.out.println("2. Переглянути існуючі маршрути");
            System.out.println("0. Вихід");
            System.out.print("Ваш вибір: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Введіть номер маршруту: ");
                    int routeNumber = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Введіть дні тижня: ");
                    String daysOfWeek = scanner.nextLine();

                    System.out.print("Введіть загальну кількість місць: ");
                    int totalSeats = scanner.nextInt();
                    scanner.nextLine();

                    Route newRoute = new Route(routeNumber, daysOfWeek, totalSeats);

                    System.out.println("Додавання станцій (для завершення введіть 'done'):");
                    while (true) {
                        System.out.print("Назва станції: ");
                        String stationName = scanner.nextLine();
                        if (stationName.equals("done")) {
                            break;
                        }
                        System.out.print("Час прибуття: ");
                        String arrivalTime = scanner.nextLine();
                        System.out.print("Час відправлення: ");
                        String departureTime = scanner.nextLine();
                        System.out.print("Кількість вільних місць: ");
                        int availableSeats = scanner.nextInt();
                        scanner.nextLine();

                        RouteStation station = new RouteStation(stationName, arrivalTime, departureTime, availableSeats);
                        newRoute.addStation(station);
                    }

                    ticketSystem.addRoute(newRoute);
                    break;
                case 2:
                    List<Route> existingRoutes = ticketSystem.getRoutes();
                    for (Route route : existingRoutes) {
                        System.out.println(route);
                        for (RouteStation station : route.getStations()) {
                            System.out.println("   " + station);
                        }
                    }
                    break;
                case 0:
                    System.out.println("Дякую за використання програми!");
                    break;
                default:
                    System.out.println("Невірний вибір, спробуйте ще раз.");
            }
        } while (choice != 0);

        // Серіалізація даних
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("ticket_system.ser"))) {
            outputStream.writeObject(ticketSystem);
            System.out.println("Дані збережено в ticket_system.ser");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Десеріалізація даних
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("ticket_system.ser"))) {
            ticketSystem = (TicketSystem) inputStream.readObject();
            System.out.println("Дані відновлено з ticket_system.ser");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
