package io.github.lucaskalell.oficinaconectada.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EnvioEmailService {

    private final ObjectProvider<JavaMailSender> mailSender;
    private final boolean habilitado;
    private final String remetente;

    public EnvioEmailService(ObjectProvider<JavaMailSender> mailSender,
                             @Value("${app.email.habilitado}") boolean habilitado,
                             @Value("${app.email.remetente}") String remetente) {
        this.mailSender = mailSender;
        this.habilitado = habilitado;
        this.remetente = remetente;
    }

    public void enviarTokenRedefinicaoSenha(String destinatario, String token) {
        if (!habilitado) {
            log.info("Envio de e-mail desabilitado. Token de redefinição para {}: {}", destinatario, token);
            return;
        }

        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setFrom(remetente);
        mensagem.setTo(destinatario);
        mensagem.setSubject("Oficina Conectada - Redefinição de senha");
        mensagem.setText("""
                Recebemos um pedido para redefinir a sua senha.

                Código de redefinição: %s

                O código vale por 1 hora e só pode ser usado uma vez.
                Se você não pediu a redefinição, ignore este e-mail.""".formatted(token));

        mailSender.getObject().send(mensagem);
    }
}
