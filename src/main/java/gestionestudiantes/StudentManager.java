package gestionestudiantes;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

/**
 * Clase encargada de manejar la colección de estudiantes y la persistencia en archivo.
 */
public class StudentManager {
    private final Path filePath;
    private final Map<String, Student> students = new LinkedHashMap<>();

    public StudentManager(String filename) {
        this.filePath = Paths.get(filename);
        loadFromFile();
    }

    private void loadFromFile() {
        if (Files.notExists(filePath)) {
            System.out.println("️  Archivo de estudiantes no encontrado. Se creará: " + filePath.toString());
            try {
                Files.createFile(filePath);
                System.out.println(" Archivo creado: " + filePath.toString());
            } catch (IOException | SecurityException e) {
                System.err.println(" No se pudo crear el archivo: " + e.getMessage());
            }
            return;
        }

        try (BufferedReader br = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            String line;
            int lineNum = 0;
            while ((line = br.readLine()) != null) {
                lineNum++;
                line = line.trim();
                if (line.isEmpty()) continue;
                try {
                    Student s = Student.fromLine(line);
                    students.put(s.getMatricula(), s);
                } catch (IllegalArgumentException ex) {
                    System.err.printf("️ Línea %d corrupta ignorada: %s%n", lineNum, ex.getMessage());
                }
            }
            System.out.println(" Carga de archivo completada. Estudiantes cargados: " + students.size());
        } catch (IOException e) {
            System.err.println(" Error al leer el archivo: " + e.getMessage());
        }
    }

    private void saveToFile() {
        try (BufferedWriter bw = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (Student s : students.values()) {
                bw.write(s.toString());
                bw.newLine();
            }
            bw.flush();
            System.out.println(" Cambios guardados en archivo.");
        } catch (IOException | SecurityException e) {
            System.err.println(" Error al escribir en el archivo: " + e.getMessage());
        }
    }

    public void addStudent(Student s) {
        if (students.containsKey(s.getMatricula())) {
            System.out.println("️  Ya existe un estudiante con esa matrícula.");
            return;
        }
        students.put(s.getMatricula(), s);
        saveToFile();
        System.out.printf(" Estudiante '%s' agregado.%n", s.getNombre());
    }

    public void updateStudent(String matricula, Integer nuevaEdad, String nuevaCarrera, Double nuevoPromedio) {
        Student s = students.get(matricula);
        if (s == null) {
            System.out.println("️  Estudiante no encontrado.");
            return;
        }
        if (nuevaEdad != null) s.setEdad(nuevaEdad);
        if (nuevaCarrera != null) s.setCarrera(nuevaCarrera);
        if (nuevoPromedio != null) s.setPromedio(nuevoPromedio);
        saveToFile();
        System.out.printf(" Estudiante '%s' actualizado.%n", s.getNombre());
    }

    public void removeStudent(String matricula) {
        Student s = students.remove(matricula);
        if (s == null) {
            System.out.println("️  Estudiante no encontrado.");
            return;
        }
        saveToFile();
        System.out.printf("️  Estudiante '%s' eliminado.%n", s.getNombre());
    }

    public void listStudents() {
        if (students.isEmpty()) {
            System.out.println(" No hay estudiantes registrados.");
            return;
        }
        System.out.println("\n Estudiantes registrados:");
        for (Student s : students.values()) {
            System.out.printf("- Matrícula: %s | Nombre: %s | Edad: %d | Carrera: %s | Promedio: %.2f%n",
                    s.getMatricula(), s.getNombre(), s.getEdad(), s.getCarrera(), s.getPromedio());
        }
    }
}
