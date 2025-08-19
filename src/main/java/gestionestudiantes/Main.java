package gestionestudiantes;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        StudentManager manager = new StudentManager("estudiantes.txt");
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- MENÚ DEL SISTEMA DE ESTUDIANTES ---");
            System.out.println("1. Agregar estudiante");
            System.out.println("2. Actualizar estudiante");
            System.out.println("3. Eliminar estudiante");
            System.out.println("4. Mostrar estudiantes");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            String opcion = sc.nextLine().trim();

            try {
                switch (opcion) {
                    case "1":
                        System.out.print("Matrícula: ");
                        String mat = sc.nextLine().trim();
                        System.out.print("Nombre: ");
                        String nombre = sc.nextLine().trim();
                        System.out.print("Edad: ");
                        int edad = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Carrera: ");
                        String carrera = sc.nextLine().trim();
                        System.out.print("Promedio: ");
                        double promedio = Double.parseDouble(sc.nextLine().trim());
                        Student s = new Student(mat, nombre, edad, carrera, promedio);
                        manager.addStudent(s);
                        break;

                    case "2":
                        System.out.print("Matrícula a actualizar: ");
                        String mAct = sc.nextLine().trim();
                        System.out.print("Nueva edad (enter para omitir): ");
                        String edStr = sc.nextLine().trim();
                        Integer nuevaEdad = edStr.isEmpty() ? null : Integer.parseInt(edStr);
                        System.out.print("Nueva carrera (enter para omitir): ");
                        String newCarr = sc.nextLine().trim();
                        String nuevaCarrera = newCarr.isEmpty() ? null : newCarr;
                        System.out.print("Nuevo promedio (enter para omitir): ");
                        String promStr = sc.nextLine().trim();
                        Double nuevoProm = promStr.isEmpty() ? null : Double.parseDouble(promStr);
                        manager.updateStudent(mAct, nuevaEdad, nuevaCarrera, nuevoProm);
                        break;

                    case "3":
                        System.out.print("Matrícula a eliminar: ");
                        String mDel = sc.nextLine().trim();
                        manager.removeStudent(mDel);
                        break;

                    case "4":
                        manager.listStudents();
                        break;

                    case "5":
                        System.out.println(" Saliendo. ¡Hasta pronto!");
                        sc.close();
                        return;

                    default:
                        System.out.println("⚠️  Opción no válida. Intenta otra vez.");
                }
            } catch (NumberFormatException e) {
                System.err.println(" Error: Entrada numérica inválida. Asegúrate de ingresar números correctos para edad/promedio.");
            } catch (Exception e) {
                System.err.println(" Ocurrió un error inesperado: " + e.getMessage());
            }
        }
    }
}
