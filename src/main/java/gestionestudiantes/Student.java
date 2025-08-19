package gestionestudiantes;

/**
 * Clase que representa a un estudiante.
 */
public class Student {
    private String matricula;
    private String nombre;
    private int edad;
    private String carrera;
    private double promedio;

    public Student(String matricula, String nombre, int edad, String carrera, double promedio) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.edad = edad;
        this.carrera = carrera;
        this.promedio = promedio;
    }

    public String getMatricula() { return matricula; }
    public String getNombre() { return nombre; }
    public int getEdad() { return edad; }
    public String getCarrera() { return carrera; }
    public double getPromedio() { return promedio; }

    public void setEdad(int edad) { this.edad = edad; }
    public void setCarrera(String carrera) { this.carrera = carrera; }
    public void setPromedio(double promedio) { this.promedio = promedio; }

    @Override
    public String toString() {
        return String.format("%s,%s,%d,%s,%.2f",
                matricula, nombre.replace(",", " "), edad, carrera.replace(",", " "), promedio);
    }

    public static Student fromLine(String line) {
        String[] parts = line.split(",");
        if (parts.length != 5) {
            throw new IllegalArgumentException("Línea con número incorrecto de campos: " + line);
        }
        String matricula = parts[0].trim();
        String nombre = parts[1].trim();
        int edad;
        String carrera = parts[3].trim();
        double promedio;
        try {
            edad = Integer.parseInt(parts[2].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Edad inválida en línea: " + line);
        }
        try {
            promedio = Double.parseDouble(parts[4].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Promedio inválido en línea: " + line);
        }
        return new Student(matricula, nombre, edad, carrera, promedio);
    }
}
