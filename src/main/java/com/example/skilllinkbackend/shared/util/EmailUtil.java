package com.example.skilllinkbackend.shared.util;

public class EmailUtil {
    public static String htmlMessageVerification(String verificationCode) {
        return "<html>" +
                "<body style=\"margin:0; padding:0; font-family: 'Arial', sans-serif; background-color:#f4f4f4;\">" +
                "<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"max-width:600px; background:#ffffff; border-radius:8px; overflow:hidden; box-shadow:0 2px 8px rgba(0,0,0,0.1);\">" +
                "<tr>" +
                "<td style=\"background:#0d6efd; padding:20px; text-align:center; color:#ffffff;\">" +
                "<h1 style=\"margin:0; font-size:24px;\">Bienvenido a tu App</h1>" +
                "</td>" +
                "</tr>" +

                "<tr>" +
                "<td style=\"padding:30px; color:#333333;\">" +
                "<p style=\"font-size:16px; margin-top:0;\">Hola,</p>" +
                "<p style=\"font-size:16px; line-height:1.6;\">" +
                "Gracias por registrarte. Para continuar, ingresa el siguiente código de verificación:" +
                "</p>" +

                "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin:20px 0;\">" +
                "<tr>" +
                "<td style=\"background:#f1f3f5; padding:20px; border-radius:6px; text-align:center;\">" +
                "<span style=\"display:inline-block; font-size:28px; font-weight:bold; color:#0d6efd; letter-spacing:2px;\">" +
                verificationCode +
                "</span>" +
                "</td>" +
                "</tr>" +
                "</table>" +

                "<p style=\"font-size:14px; color:#555; line-height:1.5;\">" +
                "Si no solicitaste este registro, puedes ignorar este mensaje." +
                "</p>" +
                "</td>" +
                "</tr>" +

                "<tr>" +
                "<td style=\"background:#f8f9fa; padding:15px; text-align:center; font-size:12px; color:#888;\">" +
                "© 2025 Tu App. Todos los derechos reservados." +
                "</td>" +
                "</tr>" +
                "</table>" +
                "</body>" +
                "</html>";
    }

    public static String htmlMessageRenewPassword(String resetLink) {
        return "<html>" +
                "<body style=\"margin:0; padding:0; font-family:'Arial', sans-serif; background-color:#f4f4f4;\">" +

                "<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" " +
                "style=\"max-width:600px; background:#ffffff; border-radius:8px; overflow:hidden; " +
                "box-shadow:0 2px 8px rgba(0,0,0,0.1);\">" +

                "<tr>" +
                "<td style=\"background:#0d6efd; padding:20px; text-align:center; color:#ffffff;\">" +
                "<h1 style=\"margin:0; font-size:24px;\">Renovar tu Contraseña</h1>" +
                "</td>" +
                "</tr>" +

                "<tr>" +
                "<td style=\"padding:30px; color:#333333;\">" +
                "<p style=\"font-size:16px; margin-top:0;\">Hola,</p>" +

                "<p style=\"font-size:16px; line-height:1.6;\">" +
                "Recibimos una solicitud para renovar tu contraseña. " +
                "Para continuar, haz clic en el siguiente botón:" +
                "</p>" +

                "<div style=\"text-align:center; margin:30px 0;\">" +
                "<a href=\"" + resetLink + "\" " +
                "style=\"display:inline-block; background:#0d6efd; color:#ffffff; " +
                "padding:14px 28px; font-size:16px; font-weight:bold; " +
                "text-decoration:none; border-radius:6px;\">" +
                "Renovar Contraseña" +
                "</a>" +
                "</div>" +

                "<p style=\"font-size:14px; color:#555555; line-height:1.5;\">" +
                "Si el botón no funciona, copia y pega el siguiente enlace en tu navegador:" +
                "</p>" +

                "<p style=\"font-size:14px; color:#0d6efd; word-break:break-all;\">" +
                resetLink +
                "</p>" +

                "<p style=\"font-size:14px; color:#555555; line-height:1.5;\">" +
                "Por motivos de seguridad, este enlace expirará en 30 minutos. " +
                "Si no solicitaste un cambio de contraseña, puedes ignorar este mensaje." +
                "</p>" +
                "</td>" +
                "</tr>" +

                "<tr>" +
                "<td style=\"background:#f8f9fa; padding:15px; text-align:center; " +
                "font-size:12px; color:#888888;\">" +
                "© 2025 Tu App. Seguridad y soporte al usuario." +
                "</td>" +
                "</tr>" +

                "</table>" +
                "</body>" +
                "</html>";
    }
}
