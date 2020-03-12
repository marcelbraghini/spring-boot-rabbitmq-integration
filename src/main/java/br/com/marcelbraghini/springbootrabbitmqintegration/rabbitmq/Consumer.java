package br.com.marcelbraghini.springbootrabbitmqintegration.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Service
public class Consumer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * Consumidor da fila: FILA
     */
    @RabbitListener(queues = "FILA")
    public void receiveMessage(final Message message) throws JSONException {
        log.debug("<MENSAGEM>: {}", message.toString());
        final JSONObject json = getJsonObject(message.getBody());

        /**
         A partir daqui é possível trabalhar com o objeto da maneira que for necessária
         json.get("nome");
         json.get("idade");
         json.get("habilidades");
         */
    }

    /**
     * Faz a leitura da mensagem e devolve um JSONObject
     */
    private JSONObject getJsonObject(final byte[] msg) throws JSONException {
        JSONObject objReceived = null;
        String payload = new String(msg, UTF_8);
        try {
            final String mensagem = new String(msg, UTF_8);
            objReceived = new JSONObject(mensagem);

            if (!isValidObject(objReceived)){
                throw new Exception("Objeto inválido, é necessário conferir os atributos inseridos...");
            }
            log.info("<MENSAGEM RECEBIDA>: {}", objReceived.toString());
        } catch (final Exception e) {
            JSONObject objPush = new JSONObject();
            log.error("<JSON ERROR> String -> JsonObject [{}]>", e.getMessage());
            objPush.put("queue", "FILA")
                    .put("message", payload.replaceAll("\\n","").replaceAll("\\r","").replaceAll(" ",""))
                    .put("error", e.getMessage().replaceAll("\\n","").replaceAll("\\r",""));
            rabbitTemplate.convertAndSend("E_ERROS", "erro", objPush.toString());
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
