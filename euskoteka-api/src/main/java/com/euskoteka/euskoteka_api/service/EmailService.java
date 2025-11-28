package com.euskoteka.euskoteka_api.service;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class EmailService {

    @Value("${spring.sendgrid.api-key}")
    private String sendGridApiKey;

    private void enviarCorreo(String destinatario, String asunto, String contenidoHtml) {
        Email from = new Email("mikelsierram@gmail.com");
        Email to = new Email(destinatario);
        Content content = new Content("text/html", contenidoHtml);
        Mail mail = new Mail(from, asunto, to, content);

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);

            System.out.println("📧 Correo enviado a " + destinatario + " - Estado: " + response.getStatusCode());
        } catch (IOException ex) {
            System.err.println("⚠️ Error al enviar correo: " + ex.getMessage());
        }
    }

    // Correo para registrar
    public void enviarCorreoVerificacion(String destinatario, String username, String token) {
        String enlaceVerificacion = "https://euskoteka-front.vercel.app/verificacionRegistroExitosa?token=" + token;
        String contenidoHtml = """
            <div style="font-family: Arial, sans-serif; color: #333; padding: 20px;">
                <h2 style="color: #1976d2;">¡Bienvenido a Euskoteka, %s!</h2>
                <p>Gracias por registrarte. Para activar tu cuenta, haz clic en el siguiente botón:</p>
                <a href="%s"
                   style="display: inline-block; background-color: #1976d2; color: white;
                          padding: 10px 20px; border-radius: 5px; text-decoration: none;">
                   Verificar mi cuenta
                </a>
                <p style="margin-top: 20px; color: #777;">
                    Si no solicitaste esta cuenta, simplemente ignora este correo.
                </p>
            </div>
        """.formatted(username, enlaceVerificacion);

        enviarCorreo(destinatario, "Verifica tu cuenta en Euskoteka", contenidoHtml);
    }

    // Correo para login
    public void enviarCorreoLogin(String destinatario, String username, String token) {
        String enlaceVerificacion = "https://euskoteka-front.vercel.app/verificarLogin?token=" + token;
        String contenidoHtml = """
            <div style="font-family: Arial, sans-serif; color: #333; padding: 20px;">
                <h2 style="color: #1976d2;">Hola, %s 👋</h2>
                <p>Has solicitado iniciar sesión en <strong>Euskoteka</strong>.</p>
                <p>Para confirmar que eres tú, haz clic en el siguiente botón:</p>
                <a href="%s"
                   style="display: inline-block; background-color: #1976d2; color: white;
                          padding: 10px 20px; border-radius: 5px; text-decoration: none;">
                   Confirmar inicio de sesión
                </a>
                <p style="margin-top: 20px; color: #777;">
                    Si no solicitaste este inicio de sesión, ignora este mensaje.
                </p>
            </div>
        """.formatted(username, enlaceVerificacion);

        enviarCorreo(destinatario, "Confirma tu inicio de sesión en Euskoteka", contenidoHtml);
    }
}