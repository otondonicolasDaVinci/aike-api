package com.tesis.aike; // Puedes usar cualquier paquete

import com.tesis.aike.service.impl.AikeAIServiceImpl; // Asegúrate que la ruta sea correcta
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component; // Para que Spring lo detecte como un Bean

import java.util.Scanner;

@Component // Marca esta clase como un componente de Spring para que sea detectada y ejecutada
public class ChatConsoleTester implements CommandLineRunner {

    private final AikeAIServiceImpl chatGptService;

    // Inyecta tu servicio igual que lo harías en un controlador
    public ChatConsoleTester(AikeAIServiceImpl chatGptService) {
        this.chatGptService = chatGptService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("**********************************************");
        System.out.println("* Bienvenido al Chat de Consola ChatGPT    *");
        System.out.println("* Escribe tu mensaje y presiona Enter.       *");
        System.out.println("* Escribe 'salir' o 'exit' para terminar.   *");
        System.out.println("**********************************************");

        // Usamos try-with-resources para asegurar que el Scanner se cierre
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("\nTú: ");
                String userInput = scanner.nextLine();

                // Condición para salir del bucle
                if (userInput == null || userInput.trim().equalsIgnoreCase("salir") || userInput.trim().equalsIgnoreCase("exit")) {
                    break;
                }

                // Evitar enviar prompts vacíos
                if (userInput.trim().isEmpty()) {
                    continue;
                }

                System.out.println("ChatGPT: Procesando..."); // Indicador visual

                try {
                    // Llama a tu servicio para obtener la respuesta
                    String respuesta = chatGptService.obtenerRespuestaChat(userInput);
                    System.out.println("ChatGPT: " + respuesta);
                } catch (Exception e) {
                    // Manejo básico de errores si el servicio falla
                    System.err.println("ChatGPT Error: No se pudo obtener respuesta. " + e.getMessage());
                    // Podrías querer imprimir más detalles o loggear el error:
                    // e.printStackTrace();
                }
            }
        } // El Scanner se cierra automáticamente aquí

        System.out.println("\n--- Chat finalizado. ¡Hasta luego! ---");
    }
}