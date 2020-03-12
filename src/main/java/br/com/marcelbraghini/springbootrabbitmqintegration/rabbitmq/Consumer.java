package br.com.marcelbraghini.springbootrabbitmqintegration.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Service
public class Consumer {

    /**
     * Consumidor da fila: FILA
     */
    @RabbitListener(queues = "FILA")
    public void receiveMessage(final Message message) {
        log.info("<MENSAGEM>: {}", message.toString());
        final JSONObject json = getJsonObject(message.getBody());

        try {
            if (!isValidObject(json)){
                throw new Exception();
            }
        } catch (final Exception e){
            log.error("Houve um erro ao procesar a mensagem recebida!");

            //TODO Terminar
            log.error("Encaminhar para a FILA de ERROS!");
        }
    }

    /**
     * Faz a leitura da mensagem e devolve um JSONObject
     */
    private JSONObject getJsonObject(final byte[] msg) {
        JSONObject objReceived = null;
        try {
            final String mensagem = new String(msg, UTF_8);
            objReceived = new JSONObject(mensagem);

            log.info("<MENSAGEM RECEBIDA>: {}", objReceived.toString());
        } catch (final Exception e) {
            log.info("<MENSAGEM RECEBIDA>: {}", new String(msg, UTF_8));
            log.error("<JSON ERROR> String -> JsonObject [{}]>", e.getMessage());
        }
        return objReceived;
    }

    /**
     * Validar os campos que são indispensáveis
     */
    private boolean isValidObject(final JSONObject json) {
        if (json.isNull("nome") || json.isNull("idade") || json.isNull("habilidades")){
            return false;
        }
        return true;
    }

}
