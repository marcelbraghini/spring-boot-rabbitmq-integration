package br.com.marcelbraghini.springbootrabbitmqintegration.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Service
public class Consumer {

    @RabbitListener(queues = "FILA")
    public void receiveMessage(final Message message) throws JSONException {
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

    private JSONObject getJsonObject(final byte[] msg) {
        JSONObject objReceived = null;
        try {
            final String mensagem = new String(msg, UTF_8);
            objReceived = new JSONObject(mensagem);

            log.info("<MENSAGEM RECEBIDA>: {}", objReceived.toString());
        } catch (final Exception e) {
            log.info("<MENSAGEM RECEBIDA>: {}", objReceived.toString());
            log.error("<JSON ERROR> String -> JsonObject [{}]>", e.getMessage());
        }
        return objReceived;
    }

    private boolean isValidObject(final JSONObject json) {
        if (json.isNull("nome") || json.isNull("idade") || json.isNull("habilidades")){
            return false;
        }
        return true;
    }

}
